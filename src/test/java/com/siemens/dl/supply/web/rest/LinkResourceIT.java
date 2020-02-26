package com.siemens.dl.supply.web.rest;

import com.siemens.dl.supply.SupplySampleApplicationApp;
import com.siemens.dl.supply.domain.Link;
import com.siemens.dl.supply.domain.AssemblyLine;
import com.siemens.dl.supply.repository.LinkRepository;
import com.siemens.dl.supply.service.LinkService;
import com.siemens.dl.supply.service.dto.LinkDTO;
import com.siemens.dl.supply.service.mapper.LinkMapper;
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
 * Integration tests for the {@link LinkResource} REST controller.
 */
@SpringBootTest(classes = SupplySampleApplicationApp.class)
public class LinkResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkMapper linkMapper;

    @Autowired
    private LinkService linkService;

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

    private MockMvc restLinkMockMvc;

    private Link link;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LinkResource linkResource = new LinkResource(linkService);
        this.restLinkMockMvc = MockMvcBuilders.standaloneSetup(linkResource)
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
    public static Link createEntity(EntityManager em) {
        Link link = new Link()
            .name(DEFAULT_NAME)
            .deviceId(DEFAULT_DEVICE_ID)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        AssemblyLine assemblyLine;
        if (TestUtil.findAll(em, AssemblyLine.class).isEmpty()) {
            assemblyLine = AssemblyLineResourceIT.createEntity(em);
            em.persist(assemblyLine);
            em.flush();
        } else {
            assemblyLine = TestUtil.findAll(em, AssemblyLine.class).get(0);
        }
        link.setAssemblyline(assemblyLine);
        return link;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Link createUpdatedEntity(EntityManager em) {
        Link link = new Link()
            .name(UPDATED_NAME)
            .deviceId(UPDATED_DEVICE_ID)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        AssemblyLine assemblyLine;
        if (TestUtil.findAll(em, AssemblyLine.class).isEmpty()) {
            assemblyLine = AssemblyLineResourceIT.createUpdatedEntity(em);
            em.persist(assemblyLine);
            em.flush();
        } else {
            assemblyLine = TestUtil.findAll(em, AssemblyLine.class).get(0);
        }
        link.setAssemblyline(assemblyLine);
        return link;
    }

    @BeforeEach
    public void initTest() {
        link = createEntity(em);
    }

    @Test
    @Transactional
    public void createLink() throws Exception {
        int databaseSizeBeforeCreate = linkRepository.findAll().size();

        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);
        restLinkMockMvc.perform(post("/api/links")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isCreated());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeCreate + 1);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLink.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testLink.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createLinkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = linkRepository.findAll().size();

        // Create the Link with an existing ID
        link.setId(1L);
        LinkDTO linkDTO = linkMapper.toDto(link);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinkMockMvc.perform(post("/api/links")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDeviceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = linkRepository.findAll().size();
        // set the field null
        link.setDeviceId(null);

        // Create the Link, which fails.
        LinkDTO linkDTO = linkMapper.toDto(link);

        restLinkMockMvc.perform(post("/api/links")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isBadRequest());

        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLinks() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        // Get all the linkList
        restLinkMockMvc.perform(get("/api/links?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(link.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        // Get the link
        restLinkMockMvc.perform(get("/api/links/{id}", link.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(link.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingLink() throws Exception {
        // Get the link
        restLinkMockMvc.perform(get("/api/links/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Update the link
        Link updatedLink = linkRepository.findById(link.getId()).get();
        // Disconnect from session so that the updates on updatedLink are not directly saved in db
        em.detach(updatedLink);
        updatedLink
            .name(UPDATED_NAME)
            .deviceId(UPDATED_DEVICE_ID)
            .description(UPDATED_DESCRIPTION);
        LinkDTO linkDTO = linkMapper.toDto(updatedLink);

        restLinkMockMvc.perform(put("/api/links")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isOk());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
        Link testLink = linkList.get(linkList.size() - 1);
        assertThat(testLink.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLink.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testLink.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingLink() throws Exception {
        int databaseSizeBeforeUpdate = linkRepository.findAll().size();

        // Create the Link
        LinkDTO linkDTO = linkMapper.toDto(link);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinkMockMvc.perform(put("/api/links")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Link in the database
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLink() throws Exception {
        // Initialize the database
        linkRepository.saveAndFlush(link);

        int databaseSizeBeforeDelete = linkRepository.findAll().size();

        // Delete the link
        restLinkMockMvc.perform(delete("/api/links/{id}", link.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Link> linkList = linkRepository.findAll();
        assertThat(linkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
