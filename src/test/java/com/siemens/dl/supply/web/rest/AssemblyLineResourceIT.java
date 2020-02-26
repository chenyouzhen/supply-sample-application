package com.siemens.dl.supply.web.rest;

import com.siemens.dl.supply.SupplySampleApplicationApp;
import com.siemens.dl.supply.domain.AssemblyLine;
import com.siemens.dl.supply.domain.Product;
import com.siemens.dl.supply.repository.AssemblyLineRepository;
import com.siemens.dl.supply.service.AssemblyLineService;
import com.siemens.dl.supply.service.dto.AssemblyLineDTO;
import com.siemens.dl.supply.service.mapper.AssemblyLineMapper;
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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.siemens.dl.supply.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AssemblyLineResource} REST controller.
 */
@SpringBootTest(classes = SupplySampleApplicationApp.class)
public class AssemblyLineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CAPACITY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CAPACITY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PLAN_CAPACITY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PLAN_CAPACITY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RESERVE = "AAAAAAAAAA";
    private static final String UPDATED_RESERVE = "BBBBBBBBBB";

    @Autowired
    private AssemblyLineRepository assemblyLineRepository;

    @Autowired
    private AssemblyLineMapper assemblyLineMapper;

    @Autowired
    private AssemblyLineService assemblyLineService;

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

    private MockMvc restAssemblyLineMockMvc;

    private AssemblyLine assemblyLine;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssemblyLineResource assemblyLineResource = new AssemblyLineResource(assemblyLineService);
        this.restAssemblyLineMockMvc = MockMvcBuilders.standaloneSetup(assemblyLineResource)
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
    public static AssemblyLine createEntity(EntityManager em) {
        AssemblyLine assemblyLine = new AssemblyLine()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .capacity(DEFAULT_CAPACITY)
            .planCapacity(DEFAULT_PLAN_CAPACITY)
            .reserve(DEFAULT_RESERVE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        assemblyLine.setProduct(product);
        return assemblyLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssemblyLine createUpdatedEntity(EntityManager em) {
        AssemblyLine assemblyLine = new AssemblyLine()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .capacity(UPDATED_CAPACITY)
            .planCapacity(UPDATED_PLAN_CAPACITY)
            .reserve(UPDATED_RESERVE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        assemblyLine.setProduct(product);
        return assemblyLine;
    }

    @BeforeEach
    public void initTest() {
        assemblyLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssemblyLine() throws Exception {
        int databaseSizeBeforeCreate = assemblyLineRepository.findAll().size();

        // Create the AssemblyLine
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);
        restAssemblyLineMockMvc.perform(post("/api/assembly-lines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isCreated());

        // Validate the AssemblyLine in the database
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeCreate + 1);
        AssemblyLine testAssemblyLine = assemblyLineList.get(assemblyLineList.size() - 1);
        assertThat(testAssemblyLine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssemblyLine.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAssemblyLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssemblyLine.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testAssemblyLine.getPlanCapacity()).isEqualTo(DEFAULT_PLAN_CAPACITY);
        assertThat(testAssemblyLine.getReserve()).isEqualTo(DEFAULT_RESERVE);
    }

    @Test
    @Transactional
    public void createAssemblyLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assemblyLineRepository.findAll().size();

        // Create the AssemblyLine with an existing ID
        assemblyLine.setId(1L);
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssemblyLineMockMvc.perform(post("/api/assembly-lines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssemblyLine in the database
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assemblyLineRepository.findAll().size();
        // set the field null
        assemblyLine.setName(null);

        // Create the AssemblyLine, which fails.
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        restAssemblyLineMockMvc.perform(post("/api/assembly-lines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assemblyLineRepository.findAll().size();
        // set the field null
        assemblyLine.setType(null);

        // Create the AssemblyLine, which fails.
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        restAssemblyLineMockMvc.perform(post("/api/assembly-lines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = assemblyLineRepository.findAll().size();
        // set the field null
        assemblyLine.setCapacity(null);

        // Create the AssemblyLine, which fails.
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        restAssemblyLineMockMvc.perform(post("/api/assembly-lines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlanCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = assemblyLineRepository.findAll().size();
        // set the field null
        assemblyLine.setPlanCapacity(null);

        // Create the AssemblyLine, which fails.
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        restAssemblyLineMockMvc.perform(post("/api/assembly-lines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssemblyLines() throws Exception {
        // Initialize the database
        assemblyLineRepository.saveAndFlush(assemblyLine);

        // Get all the assemblyLineList
        restAssemblyLineMockMvc.perform(get("/api/assembly-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assemblyLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.toString())))
            .andExpect(jsonPath("$.[*].planCapacity").value(hasItem(DEFAULT_PLAN_CAPACITY.toString())))
            .andExpect(jsonPath("$.[*].reserve").value(hasItem(DEFAULT_RESERVE)));
    }
    
    @Test
    @Transactional
    public void getAssemblyLine() throws Exception {
        // Initialize the database
        assemblyLineRepository.saveAndFlush(assemblyLine);

        // Get the assemblyLine
        restAssemblyLineMockMvc.perform(get("/api/assembly-lines/{id}", assemblyLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assemblyLine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.toString()))
            .andExpect(jsonPath("$.planCapacity").value(DEFAULT_PLAN_CAPACITY.toString()))
            .andExpect(jsonPath("$.reserve").value(DEFAULT_RESERVE));
    }

    @Test
    @Transactional
    public void getNonExistingAssemblyLine() throws Exception {
        // Get the assemblyLine
        restAssemblyLineMockMvc.perform(get("/api/assembly-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssemblyLine() throws Exception {
        // Initialize the database
        assemblyLineRepository.saveAndFlush(assemblyLine);

        int databaseSizeBeforeUpdate = assemblyLineRepository.findAll().size();

        // Update the assemblyLine
        AssemblyLine updatedAssemblyLine = assemblyLineRepository.findById(assemblyLine.getId()).get();
        // Disconnect from session so that the updates on updatedAssemblyLine are not directly saved in db
        em.detach(updatedAssemblyLine);
        updatedAssemblyLine
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .capacity(UPDATED_CAPACITY)
            .planCapacity(UPDATED_PLAN_CAPACITY)
            .reserve(UPDATED_RESERVE);
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(updatedAssemblyLine);

        restAssemblyLineMockMvc.perform(put("/api/assembly-lines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isOk());

        // Validate the AssemblyLine in the database
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeUpdate);
        AssemblyLine testAssemblyLine = assemblyLineList.get(assemblyLineList.size() - 1);
        assertThat(testAssemblyLine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssemblyLine.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAssemblyLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssemblyLine.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testAssemblyLine.getPlanCapacity()).isEqualTo(UPDATED_PLAN_CAPACITY);
        assertThat(testAssemblyLine.getReserve()).isEqualTo(UPDATED_RESERVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssemblyLine() throws Exception {
        int databaseSizeBeforeUpdate = assemblyLineRepository.findAll().size();

        // Create the AssemblyLine
        AssemblyLineDTO assemblyLineDTO = assemblyLineMapper.toDto(assemblyLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssemblyLineMockMvc.perform(put("/api/assembly-lines")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assemblyLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssemblyLine in the database
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssemblyLine() throws Exception {
        // Initialize the database
        assemblyLineRepository.saveAndFlush(assemblyLine);

        int databaseSizeBeforeDelete = assemblyLineRepository.findAll().size();

        // Delete the assemblyLine
        restAssemblyLineMockMvc.perform(delete("/api/assembly-lines/{id}", assemblyLine.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssemblyLine> assemblyLineList = assemblyLineRepository.findAll();
        assertThat(assemblyLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
