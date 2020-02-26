package com.siemens.dl.supply.web.rest;

import com.siemens.dl.supply.SupplySampleApplicationApp;
import com.siemens.dl.supply.domain.Observation;
import com.siemens.dl.supply.domain.AssemblyLine;
import com.siemens.dl.supply.domain.Link;
import com.siemens.dl.supply.repository.ObservationRepository;
import com.siemens.dl.supply.service.ObservationService;
import com.siemens.dl.supply.service.dto.ObservationDTO;
import com.siemens.dl.supply.service.mapper.ObservationMapper;
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
 * Integration tests for the {@link ObservationResource} REST controller.
 */
@SpringBootTest(classes = SupplySampleApplicationApp.class)
public class ObservationResourceIT {

    private static final ZonedDateTime DEFAULT_PHENOMENON_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PHENOMENON_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_RESULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RESULT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PROPERTY = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_OF_MEASUREMENT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_OF_MEASUREMENT = "BBBBBBBBBB";

    private static final Long DEFAULT_INTERVAL_TIME = 1L;
    private static final Long UPDATED_INTERVAL_TIME = 2L;

    private static final String DEFAULT_RESERVE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVE = "BBBBBBBBBB";

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private ObservationMapper observationMapper;

    @Autowired
    private ObservationService observationService;

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

    private MockMvc restObservationMockMvc;

    private Observation observation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ObservationResource observationResource = new ObservationResource(observationService);
        this.restObservationMockMvc = MockMvcBuilders.standaloneSetup(observationResource)
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
    public static Observation createEntity(EntityManager em) {
        Observation observation = new Observation()
            .phenomenonTime(DEFAULT_PHENOMENON_TIME)
            .result(DEFAULT_RESULT)
            .resultTime(DEFAULT_RESULT_TIME)
            .property(DEFAULT_PROPERTY)
            .unitOfMeasurement(DEFAULT_UNIT_OF_MEASUREMENT)
            .intervalTime(DEFAULT_INTERVAL_TIME)
            .reserve(DEFAULT_RESERVE);
        // Add required entity
        AssemblyLine assemblyLine;
        if (TestUtil.findAll(em, AssemblyLine.class).isEmpty()) {
            assemblyLine = AssemblyLineResourceIT.createEntity(em);
            em.persist(assemblyLine);
            em.flush();
        } else {
            assemblyLine = TestUtil.findAll(em, AssemblyLine.class).get(0);
        }
        observation.setAssemblyline(assemblyLine);
        // Add required entity
        Link link;
        if (TestUtil.findAll(em, Link.class).isEmpty()) {
            link = LinkResourceIT.createEntity(em);
            em.persist(link);
            em.flush();
        } else {
            link = TestUtil.findAll(em, Link.class).get(0);
        }
        observation.setLink(link);
        return observation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Observation createUpdatedEntity(EntityManager em) {
        Observation observation = new Observation()
            .phenomenonTime(UPDATED_PHENOMENON_TIME)
            .result(UPDATED_RESULT)
            .resultTime(UPDATED_RESULT_TIME)
            .property(UPDATED_PROPERTY)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .intervalTime(UPDATED_INTERVAL_TIME)
            .reserve(UPDATED_RESERVE);
        // Add required entity
        AssemblyLine assemblyLine;
        if (TestUtil.findAll(em, AssemblyLine.class).isEmpty()) {
            assemblyLine = AssemblyLineResourceIT.createUpdatedEntity(em);
            em.persist(assemblyLine);
            em.flush();
        } else {
            assemblyLine = TestUtil.findAll(em, AssemblyLine.class).get(0);
        }
        observation.setAssemblyline(assemblyLine);
        // Add required entity
        Link link;
        if (TestUtil.findAll(em, Link.class).isEmpty()) {
            link = LinkResourceIT.createUpdatedEntity(em);
            em.persist(link);
            em.flush();
        } else {
            link = TestUtil.findAll(em, Link.class).get(0);
        }
        observation.setLink(link);
        return observation;
    }

    @BeforeEach
    public void initTest() {
        observation = createEntity(em);
    }

    @Test
    @Transactional
    public void createObservation() throws Exception {
        int databaseSizeBeforeCreate = observationRepository.findAll().size();

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);
        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isCreated());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate + 1);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getPhenomenonTime()).isEqualTo(DEFAULT_PHENOMENON_TIME);
        assertThat(testObservation.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testObservation.getResultTime()).isEqualTo(DEFAULT_RESULT_TIME);
        assertThat(testObservation.getProperty()).isEqualTo(DEFAULT_PROPERTY);
        assertThat(testObservation.getUnitOfMeasurement()).isEqualTo(DEFAULT_UNIT_OF_MEASUREMENT);
        assertThat(testObservation.getIntervalTime()).isEqualTo(DEFAULT_INTERVAL_TIME);
        assertThat(testObservation.getReserve()).isEqualTo(DEFAULT_RESERVE);
    }

    @Test
    @Transactional
    public void createObservationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = observationRepository.findAll().size();

        // Create the Observation with an existing ID
        observation.setId(1L);
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationRepository.findAll().size();
        // set the field null
        observation.setResult(null);

        // Create the Observation, which fails.
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationRepository.findAll().size();
        // set the field null
        observation.setResultTime(null);

        // Create the Observation, which fails.
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropertyIsRequired() throws Exception {
        int databaseSizeBeforeTest = observationRepository.findAll().size();
        // set the field null
        observation.setProperty(null);

        // Create the Observation, which fails.
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        restObservationMockMvc.perform(post("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllObservations() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get all the observationList
        restObservationMockMvc.perform(get("/api/observations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(observation.getId().intValue())))
            .andExpect(jsonPath("$.[*].phenomenonTime").value(hasItem(sameInstant(DEFAULT_PHENOMENON_TIME))))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].resultTime").value(hasItem(sameInstant(DEFAULT_RESULT_TIME))))
            .andExpect(jsonPath("$.[*].property").value(hasItem(DEFAULT_PROPERTY)))
            .andExpect(jsonPath("$.[*].unitOfMeasurement").value(hasItem(DEFAULT_UNIT_OF_MEASUREMENT)))
            .andExpect(jsonPath("$.[*].intervalTime").value(hasItem(DEFAULT_INTERVAL_TIME.intValue())))
            .andExpect(jsonPath("$.[*].reserve").value(hasItem(DEFAULT_RESERVE)));
    }
    
    @Test
    @Transactional
    public void getObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        // Get the observation
        restObservationMockMvc.perform(get("/api/observations/{id}", observation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(observation.getId().intValue()))
            .andExpect(jsonPath("$.phenomenonTime").value(sameInstant(DEFAULT_PHENOMENON_TIME)))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.resultTime").value(sameInstant(DEFAULT_RESULT_TIME)))
            .andExpect(jsonPath("$.property").value(DEFAULT_PROPERTY))
            .andExpect(jsonPath("$.unitOfMeasurement").value(DEFAULT_UNIT_OF_MEASUREMENT))
            .andExpect(jsonPath("$.intervalTime").value(DEFAULT_INTERVAL_TIME.intValue()))
            .andExpect(jsonPath("$.reserve").value(DEFAULT_RESERVE));
    }

    @Test
    @Transactional
    public void getNonExistingObservation() throws Exception {
        // Get the observation
        restObservationMockMvc.perform(get("/api/observations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Update the observation
        Observation updatedObservation = observationRepository.findById(observation.getId()).get();
        // Disconnect from session so that the updates on updatedObservation are not directly saved in db
        em.detach(updatedObservation);
        updatedObservation
            .phenomenonTime(UPDATED_PHENOMENON_TIME)
            .result(UPDATED_RESULT)
            .resultTime(UPDATED_RESULT_TIME)
            .property(UPDATED_PROPERTY)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .intervalTime(UPDATED_INTERVAL_TIME)
            .reserve(UPDATED_RESERVE);
        ObservationDTO observationDTO = observationMapper.toDto(updatedObservation);

        restObservationMockMvc.perform(put("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isOk());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
        Observation testObservation = observationList.get(observationList.size() - 1);
        assertThat(testObservation.getPhenomenonTime()).isEqualTo(UPDATED_PHENOMENON_TIME);
        assertThat(testObservation.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testObservation.getResultTime()).isEqualTo(UPDATED_RESULT_TIME);
        assertThat(testObservation.getProperty()).isEqualTo(UPDATED_PROPERTY);
        assertThat(testObservation.getUnitOfMeasurement()).isEqualTo(UPDATED_UNIT_OF_MEASUREMENT);
        assertThat(testObservation.getIntervalTime()).isEqualTo(UPDATED_INTERVAL_TIME);
        assertThat(testObservation.getReserve()).isEqualTo(UPDATED_RESERVE);
    }

    @Test
    @Transactional
    public void updateNonExistingObservation() throws Exception {
        int databaseSizeBeforeUpdate = observationRepository.findAll().size();

        // Create the Observation
        ObservationDTO observationDTO = observationMapper.toDto(observation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObservationMockMvc.perform(put("/api/observations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(observationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Observation in the database
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteObservation() throws Exception {
        // Initialize the database
        observationRepository.saveAndFlush(observation);

        int databaseSizeBeforeDelete = observationRepository.findAll().size();

        // Delete the observation
        restObservationMockMvc.perform(delete("/api/observations/{id}", observation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Observation> observationList = observationRepository.findAll();
        assertThat(observationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
