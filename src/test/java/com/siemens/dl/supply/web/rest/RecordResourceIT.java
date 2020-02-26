package com.siemens.dl.supply.web.rest;

import com.siemens.dl.supply.SupplySampleApplicationApp;
import com.siemens.dl.supply.domain.Record;
import com.siemens.dl.supply.domain.AssemblyLine;
import com.siemens.dl.supply.repository.RecordRepository;
import com.siemens.dl.supply.service.RecordService;
import com.siemens.dl.supply.service.dto.RecordDTO;
import com.siemens.dl.supply.service.mapper.RecordMapper;
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
 * Integration tests for the {@link RecordResource} REST controller.
 */
@SpringBootTest(classes = SupplySampleApplicationApp.class)
public class RecordResourceIT {

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
    private RecordRepository recordRepository;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private RecordService recordService;

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

    private MockMvc restRecordMockMvc;

    private Record record;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecordResource recordResource = new RecordResource(recordService);
        this.restRecordMockMvc = MockMvcBuilders.standaloneSetup(recordResource)
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
    public static Record createEntity(EntityManager em) {
        Record record = new Record()
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
        AssemblyLine assemblyLine;
        if (TestUtil.findAll(em, AssemblyLine.class).isEmpty()) {
            assemblyLine = AssemblyLineResourceIT.createEntity(em);
            em.persist(assemblyLine);
            em.flush();
        } else {
            assemblyLine = TestUtil.findAll(em, AssemblyLine.class).get(0);
        }
        record.setAssemblyline(assemblyLine);
        return record;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Record createUpdatedEntity(EntityManager em) {
        Record record = new Record()
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
        AssemblyLine assemblyLine;
        if (TestUtil.findAll(em, AssemblyLine.class).isEmpty()) {
            assemblyLine = AssemblyLineResourceIT.createUpdatedEntity(em);
            em.persist(assemblyLine);
            em.flush();
        } else {
            assemblyLine = TestUtil.findAll(em, AssemblyLine.class).get(0);
        }
        record.setAssemblyline(assemblyLine);
        return record;
    }

    @BeforeEach
    public void initTest() {
        record = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecord() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();

        // Create the Record
        RecordDTO recordDTO = recordMapper.toDto(record);
        restRecordMockMvc.perform(post("/api/records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isCreated());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeCreate + 1);
        Record testRecord = recordList.get(recordList.size() - 1);
        assertThat(testRecord.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRecord.getPhenomenonTime()).isEqualTo(DEFAULT_PHENOMENON_TIME);
        assertThat(testRecord.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testRecord.getResultTime()).isEqualTo(DEFAULT_RESULT_TIME);
        assertThat(testRecord.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecord.getIntervalTime()).isEqualTo(DEFAULT_INTERVAL_TIME);
        assertThat(testRecord.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRecord.getUnitOfMeasurement()).isEqualTo(DEFAULT_UNIT_OF_MEASUREMENT);
        assertThat(testRecord.getReserve()).isEqualTo(DEFAULT_RESERVE);
    }

    @Test
    @Transactional
    public void createRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recordRepository.findAll().size();

        // Create the Record with an existing ID
        record.setId(1L);
        RecordDTO recordDTO = recordMapper.toDto(record);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecordMockMvc.perform(post("/api/records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordRepository.findAll().size();
        // set the field null
        record.setResult(null);

        // Create the Record, which fails.
        RecordDTO recordDTO = recordMapper.toDto(record);

        restRecordMockMvc.perform(post("/api/records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isBadRequest());

        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResultTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordRepository.findAll().size();
        // set the field null
        record.setResultTime(null);

        // Create the Record, which fails.
        RecordDTO recordDTO = recordMapper.toDto(record);

        restRecordMockMvc.perform(post("/api/records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isBadRequest());

        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = recordRepository.findAll().size();
        // set the field null
        record.setType(null);

        // Create the Record, which fails.
        RecordDTO recordDTO = recordMapper.toDto(record);

        restRecordMockMvc.perform(post("/api/records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isBadRequest());

        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecords() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get all the recordList
        restRecordMockMvc.perform(get("/api/records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(record.getId().intValue())))
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
    public void getRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", record.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(record.getId().intValue()))
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
    public void getNonExistingRecord() throws Exception {
        // Get the record
        restRecordMockMvc.perform(get("/api/records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // Update the record
        Record updatedRecord = recordRepository.findById(record.getId()).get();
        // Disconnect from session so that the updates on updatedRecord are not directly saved in db
        em.detach(updatedRecord);
        updatedRecord
            .name(UPDATED_NAME)
            .phenomenonTime(UPDATED_PHENOMENON_TIME)
            .result(UPDATED_RESULT)
            .resultTime(UPDATED_RESULT_TIME)
            .description(UPDATED_DESCRIPTION)
            .intervalTime(UPDATED_INTERVAL_TIME)
            .type(UPDATED_TYPE)
            .unitOfMeasurement(UPDATED_UNIT_OF_MEASUREMENT)
            .reserve(UPDATED_RESERVE);
        RecordDTO recordDTO = recordMapper.toDto(updatedRecord);

        restRecordMockMvc.perform(put("/api/records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isOk());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeUpdate);
        Record testRecord = recordList.get(recordList.size() - 1);
        assertThat(testRecord.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRecord.getPhenomenonTime()).isEqualTo(UPDATED_PHENOMENON_TIME);
        assertThat(testRecord.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testRecord.getResultTime()).isEqualTo(UPDATED_RESULT_TIME);
        assertThat(testRecord.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecord.getIntervalTime()).isEqualTo(UPDATED_INTERVAL_TIME);
        assertThat(testRecord.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRecord.getUnitOfMeasurement()).isEqualTo(UPDATED_UNIT_OF_MEASUREMENT);
        assertThat(testRecord.getReserve()).isEqualTo(UPDATED_RESERVE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecord() throws Exception {
        int databaseSizeBeforeUpdate = recordRepository.findAll().size();

        // Create the Record
        RecordDTO recordDTO = recordMapper.toDto(record);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecordMockMvc.perform(put("/api/records")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Record in the database
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecord() throws Exception {
        // Initialize the database
        recordRepository.saveAndFlush(record);

        int databaseSizeBeforeDelete = recordRepository.findAll().size();

        // Delete the record
        restRecordMockMvc.perform(delete("/api/records/{id}", record.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Record> recordList = recordRepository.findAll();
        assertThat(recordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
