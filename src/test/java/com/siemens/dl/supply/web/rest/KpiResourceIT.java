package com.siemens.dl.supply.web.rest;

import com.siemens.dl.supply.SupplySampleApplicationApp;
import com.siemens.dl.supply.domain.Kpi;
import com.siemens.dl.supply.domain.Factory;
import com.siemens.dl.supply.domain.Product;
import com.siemens.dl.supply.repository.KpiRepository;
import com.siemens.dl.supply.service.KpiService;
import com.siemens.dl.supply.service.dto.KpiDTO;
import com.siemens.dl.supply.service.mapper.KpiMapper;
import com.siemens.dl.supply.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.siemens.dl.supply.web.rest.TestUtil.sameInstant;
import static com.siemens.dl.supply.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KpiResource} REST controller.
 */
@SpringBootTest(classes = SupplySampleApplicationApp.class)
public class KpiResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_PHENOMENON_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PHENOMENON_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RESULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESULT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_INTERVAL_TIME = 1L;
    private static final Long UPDATED_INTERVAL_TIME = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_OF_MEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_OF_MEASUREMENT = "BBBBBBBBBB";

    private static final String DEFAULT_RESERVE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVE = "BBBBBBBBBB";

    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private KpiMapper kpiMapper;

    @Autowired
    private KpiService kpiService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restKpiMockMvc;

    private Kpi kpi;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KpiResource kpiResource = new KpiResource(kpiService);
        this.restKpiMockMvc = MockMvcBuilders.standaloneSetup(kpiResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpi createEntity(EntityManager em) {
        Kpi kpi = new Kpi()
            .name(DEFAULT_NAME)
            .phenomenonTime(DEFAULT_PHENOMENON_TIME)
            .result(DEFAULT_RESULT)
            .resultTime(DEFAULT_RESULT_TIME)
            .description(DEFAULT_DESCRIPTION)
            .intervalTime(DEFAULT_INTERVAL_TIME)
            .type(DEFAULT_TYPE)
            .unitOfMeasurement(DEFAULT_UNIT_OF_MEASUREMENT)
            .reserve(DEFAULT_RESERVE);
        // Add required entity
        Factory factory;
        if (TestUtil.findAll(em, Factory.class).isEmpty()) {
            factory = FactoryResourceIT.createEntity(em);
            em.persist(factory);
            em.flush();
        } else {
            factory = TestUtil.findAll(em, Factory.class).get(0);
        }
        kpi.setFactory(factory);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        kpi.setProduct(product);
        return kpi;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kpi createUpdatedEntity(EntityManager em) {
        Kpi kpi = new Kpi()
            .name(UPDATED_NAME)
            .phenomenonTime(UPDATED_PHENOMENON_TIME)
            .result(UPDATED_RESULT)
            .resultTime(UPDATED_RESULT_TIME)
            .description(UPDATED_DESCRIPTION)
            .intervalTime(UPDATED_INTERVAL_TIME)
            .type(UPDATED_TYPE)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .reserve(UPDATED_RESERVE);
        // Add required entity
        Factory factory;
        if (TestUtil.findAll(em, Factory.class).isEmpty()) {
            factory = FactoryResourceIT.createUpdatedEntity(em);
            em.persist(factory);
            em.flush();
        } else {
            factory = TestUtil.findAll(em, Factory.class).get(0);
        }
        kpi.setFactory(factory);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        kpi.setProduct(product);
        return kpi;
    }

    @BeforeEach
    public void initTest() {
        kpi = createEntity(em);
    }

    @Test
    @Transactional
    public void createKpi() throws Exception {
        int databaseSizeBeforeCreate = kpiRepository.findAll().size();

        // Create the Kpi
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);
        restKpiMockMvc.perform(post("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isCreated());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeCreate + 1);
        Kpi testKpi = kpiList.get(kpiList.size() - 1);
        assertThat(testKpi.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKpi.getPhenomenonTime()).isEqualTo(DEFAULT_PHENOMENON_TIME);
        assertThat(testKpi.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testKpi.getResultTime()).isEqualTo(DEFAULT_RESULT_TIME);
        assertThat(testKpi.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testKpi.getIntervalTime()).isEqualTo(DEFAULT_INTERVAL_TIME);
        assertThat(testKpi.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testKpi.getUnitOfMeasurement()).isEqualTo(DEFAULT_UNIT_OF_MEASUREMENT);
        assertThat(testKpi.getReserve()).isEqualTo(DEFAULT_RESERVE);
    }

    @Test
    @Transactional
    public void createKpiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kpiRepository.findAll().size();

        // Create the Kpi with an existing ID
        kpi.setId(1L);
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKpiMockMvc.perform(post("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setResult(null);

        // Create the Kpi, which fails.
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        restKpiMockMvc.perform(post("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setResultTime(null);

        // Create the Kpi, which fails.
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        restKpiMockMvc.perform(post("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kpiRepository.findAll().size();
        // set the field null
        kpi.setType(null);

        // Create the Kpi, which fails.
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        restKpiMockMvc.perform(post("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKpis() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get all the kpiList
        restKpiMockMvc.perform(get("/api/kpis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kpi.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phenomenonTime").value(hasItem(sameInstant(DEFAULT_PHENOMENON_TIME))))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].resultTime").value(hasItem(sameInstant(DEFAULT_RESULT_TIME))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].intervalTime").value(hasItem(DEFAULT_INTERVAL_TIME.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].unitOfMeasurement").value(hasItem(DEFAULT_UNIT_OF_MEASUREMENT)))
            .andExpect(jsonPath("$.[*].reserve").value(hasItem(DEFAULT_RESERVE)));
    }
    
    @Test
    @Transactional
    public void getKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        // Get the kpi
        restKpiMockMvc.perform(get("/api/kpis/{id}", kpi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kpi.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phenomenonTime").value(sameInstant(DEFAULT_PHENOMENON_TIME)))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.resultTime").value(sameInstant(DEFAULT_RESULT_TIME)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.intervalTime").value(DEFAULT_INTERVAL_TIME.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.unitOfMeasurement").value(DEFAULT_UNIT_OF_MEASUREMENT))
            .andExpect(jsonPath("$.reserve").value(DEFAULT_RESERVE));
    }

    @Test
    @Transactional
    public void getNonExistingKpi() throws Exception {
        // Get the kpi
        restKpiMockMvc.perform(get("/api/kpis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();

        // Update the kpi
        Kpi updatedKpi = kpiRepository.findById(kpi.getId()).get();
        // Disconnect from session so that the updates on updatedKpi are not directly saved in db
        em.detach(updatedKpi);
        updatedKpi
            .name(UPDATED_NAME)
            .phenomenonTime(UPDATED_PHENOMENON_TIME)
            .result(UPDATED_RESULT)
            .resultTime(UPDATED_RESULT_TIME)
            .description(UPDATED_DESCRIPTION)
            .intervalTime(UPDATED_INTERVAL_TIME)
            .type(UPDATED_TYPE)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .reserve(UPDATED_RESERVE);
        KpiDTO kpiDTO = kpiMapper.toDto(updatedKpi);

        restKpiMockMvc.perform(put("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isOk());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
        Kpi testKpi = kpiList.get(kpiList.size() - 1);
        assertThat(testKpi.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKpi.getPhenomenonTime()).isEqualTo(UPDATED_PHENOMENON_TIME);
        assertThat(testKpi.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testKpi.getResultTime()).isEqualTo(UPDATED_RESULT_TIME);
        assertThat(testKpi.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testKpi.getIntervalTime()).isEqualTo(UPDATED_INTERVAL_TIME);
        assertThat(testKpi.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testKpi.getUnitOfMeasurement()).isEqualTo(UPDATED_UNIT_OF_MEASUREMENT);
        assertThat(testKpi.getReserve()).isEqualTo(UPDATED_RESERVE);
    }

    @Test
    @Transactional
    public void updateNonExistingKpi() throws Exception {
        int databaseSizeBeforeUpdate = kpiRepository.findAll().size();

        // Create the Kpi
        KpiDTO kpiDTO = kpiMapper.toDto(kpi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKpiMockMvc.perform(put("/api/kpis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kpiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kpi in the database
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKpi() throws Exception {
        // Initialize the database
        kpiRepository.saveAndFlush(kpi);

        int databaseSizeBeforeDelete = kpiRepository.findAll().size();

        // Delete the kpi
        restKpiMockMvc.perform(delete("/api/kpis/{id}", kpi.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kpi> kpiList = kpiRepository.findAll();
        assertThat(kpiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
