package com.siemens.dl.supply.web.rest;

import com.siemens.dl.supply.SupplySampleApplicationApp;
import com.siemens.dl.supply.domain.Factory;
import com.siemens.dl.supply.repository.FactoryRepository;
import com.siemens.dl.supply.service.FactoryService;
import com.siemens.dl.supply.service.dto.FactoryDTO;
import com.siemens.dl.supply.service.mapper.FactoryMapper;
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
import java.util.List;

import static com.siemens.dl.supply.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FactoryResource} REST controller.
 */
@SpringBootTest(classes = SupplySampleApplicationApp.class)
public class FactoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SCALE = "AAAAAAAAAA";
    private static final String UPDATED_SCALE = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private FactoryRepository factoryRepository;

    @Autowired
    private FactoryMapper factoryMapper;

    @Autowired
    private FactoryService factoryService;

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

    private MockMvc restFactoryMockMvc;

    private Factory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FactoryResource factoryResource = new FactoryResource(factoryService);
        this.restFactoryMockMvc = MockMvcBuilders.standaloneSetup(factoryResource)
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
    public static Factory createEntity(EntityManager em) {
        Factory factory = new Factory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .scale(DEFAULT_SCALE)
            .location(DEFAULT_LOCATION)
            .status(DEFAULT_STATUS);
        return factory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Factory createUpdatedEntity(EntityManager em) {
        Factory factory = new Factory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .scale(UPDATED_SCALE)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS);
        return factory;
    }

    @BeforeEach
    public void initTest() {
        factory = createEntity(em);
    }

    @Test
    @Transactional
    public void createFactory() throws Exception {
        int databaseSizeBeforeCreate = factoryRepository.findAll().size();

        // Create the Factory
        FactoryDTO factoryDTO = factoryMapper.toDto(factory);
        restFactoryMockMvc.perform(post("/api/factories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factoryDTO)))
            .andExpect(status().isCreated());

        // Validate the Factory in the database
        List<Factory> factoryList = factoryRepository.findAll();
        assertThat(factoryList).hasSize(databaseSizeBeforeCreate + 1);
        Factory testFactory = factoryList.get(factoryList.size() - 1);
        assertThat(testFactory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFactory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFactory.getScale()).isEqualTo(DEFAULT_SCALE);
        assertThat(testFactory.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testFactory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createFactoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = factoryRepository.findAll().size();

        // Create the Factory with an existing ID
        factory.setId(1L);
        FactoryDTO factoryDTO = factoryMapper.toDto(factory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFactoryMockMvc.perform(post("/api/factories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Factory in the database
        List<Factory> factoryList = factoryRepository.findAll();
        assertThat(factoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = factoryRepository.findAll().size();
        // set the field null
        factory.setName(null);

        // Create the Factory, which fails.
        FactoryDTO factoryDTO = factoryMapper.toDto(factory);

        restFactoryMockMvc.perform(post("/api/factories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factoryDTO)))
            .andExpect(status().isBadRequest());

        List<Factory> factoryList = factoryRepository.findAll();
        assertThat(factoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = factoryRepository.findAll().size();
        // set the field null
        factory.setLocation(null);

        // Create the Factory, which fails.
        FactoryDTO factoryDTO = factoryMapper.toDto(factory);

        restFactoryMockMvc.perform(post("/api/factories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factoryDTO)))
            .andExpect(status().isBadRequest());

        List<Factory> factoryList = factoryRepository.findAll();
        assertThat(factoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = factoryRepository.findAll().size();
        // set the field null
        factory.setStatus(null);

        // Create the Factory, which fails.
        FactoryDTO factoryDTO = factoryMapper.toDto(factory);

        restFactoryMockMvc.perform(post("/api/factories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factoryDTO)))
            .andExpect(status().isBadRequest());

        List<Factory> factoryList = factoryRepository.findAll();
        assertThat(factoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFactories() throws Exception {
        // Initialize the database
        factoryRepository.saveAndFlush(factory);

        // Get all the factoryList
        restFactoryMockMvc.perform(get("/api/factories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(factory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].scale").value(hasItem(DEFAULT_SCALE)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getFactory() throws Exception {
        // Initialize the database
        factoryRepository.saveAndFlush(factory);

        // Get the factory
        restFactoryMockMvc.perform(get("/api/factories/{id}", factory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(factory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.scale").value(DEFAULT_SCALE))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingFactory() throws Exception {
        // Get the factory
        restFactoryMockMvc.perform(get("/api/factories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFactory() throws Exception {
        // Initialize the database
        factoryRepository.saveAndFlush(factory);

        int databaseSizeBeforeUpdate = factoryRepository.findAll().size();

        // Update the factory
        Factory updatedFactory = factoryRepository.findById(factory.getId()).get();
        // Disconnect from session so that the updates on updatedFactory are not directly saved in db
        em.detach(updatedFactory);
        updatedFactory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .scale(UPDATED_SCALE)
            .location(UPDATED_LOCATION)
            .status(UPDATED_STATUS);
        FactoryDTO factoryDTO = factoryMapper.toDto(updatedFactory);

        restFactoryMockMvc.perform(put("/api/factories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factoryDTO)))
            .andExpect(status().isOk());

        // Validate the Factory in the database
        List<Factory> factoryList = factoryRepository.findAll();
        assertThat(factoryList).hasSize(databaseSizeBeforeUpdate);
        Factory testFactory = factoryList.get(factoryList.size() - 1);
        assertThat(testFactory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFactory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFactory.getScale()).isEqualTo(UPDATED_SCALE);
        assertThat(testFactory.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testFactory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingFactory() throws Exception {
        int databaseSizeBeforeUpdate = factoryRepository.findAll().size();

        // Create the Factory
        FactoryDTO factoryDTO = factoryMapper.toDto(factory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFactoryMockMvc.perform(put("/api/factories")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(factoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Factory in the database
        List<Factory> factoryList = factoryRepository.findAll();
        assertThat(factoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFactory() throws Exception {
        // Initialize the database
        factoryRepository.saveAndFlush(factory);

        int databaseSizeBeforeDelete = factoryRepository.findAll().size();

        // Delete the factory
        restFactoryMockMvc.perform(delete("/api/factories/{id}", factory.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Factory> factoryList = factoryRepository.findAll();
        assertThat(factoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
