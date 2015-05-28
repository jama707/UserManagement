package com.comcast.coding.resource;

import com.comcast.coding.config.ApplicationConfig;
import com.comcast.coding.entity.User;
import com.comcast.coding.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@SpringApplicationConfiguration(classes = {ApplicationConfig.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Transactional
public class UserResourceIT {
    private static final String JSON_APPLICATION = "application/json;charset=UTF-8";
    private static final String FIRST_EMAIL = "first@mail.ru";
    private static final String SECOND_EMAIL = "second@mail.ru";
    private static final String THIRD_EMAIL = "third@mail.ru";
    private static final String FIRST_USER_NAME = "First User";
    private static final String SECOND_USER_NAME = "Second User";
    private static final String THIRD_USER_NAME = "Third User";

    private static final String USERS_RESOURCE = "/users";
    private static final String USER_RESOURCE = "/users/{id}";
    private static final int NON_EXISTING_ID = 999;

    private static final User FIRST_USER = new UserBuilder()
            .setUserName(FIRST_USER_NAME)
            .setEmail(FIRST_EMAIL)
            .build();
    private static final User SECOND_USER = new UserBuilder()
            .setUserName(SECOND_USER_NAME)
            .setEmail(SECOND_EMAIL)
            .build();
    private static final User THIRD_USER = new UserBuilder()
            .setUserName(THIRD_USER_NAME)
            .setEmail(THIRD_EMAIL)
            .build();

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository repository;

    @Value("${local.server.port}")
    private int serverPort;
    private User firstUser;
    private User secondUser;
    RestTemplate restTemplate;
    MockMvc mockMvc;


    @Before
    public void setUp() {
        firstUser = repository.save(FIRST_USER);
        secondUser = repository.save(SECOND_USER);
        mockMvc = webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void getUserShouldBeOKandReturnJSON() throws Exception {
        mockMvc.perform(get(USER_RESOURCE, firstUser.getId()).
                header("Accept", MediaType.APPLICATION_JSON_VALUE).
                header("Content-Type", MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).
                andExpect(content().string(asJsonString(firstUser)));
    }

    @Test
    public void getUserShouldBe404andReturnJSON() throws Exception {
        mockMvc.perform(get(USER_RESOURCE, NON_EXISTING_ID).
                header("Accept", MediaType.APPLICATION_JSON_VALUE).
                header("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }


    @Test
    public void addUserShouldReturnCreatedStatus() throws Exception {
        User user = new UserBuilder()
                .setUserName(THIRD_USER_NAME)
                .setEmail(THIRD_EMAIL)
                .build();
        mockMvc.perform(post(USERS_RESOURCE).content(asJsonString(user)).
                header("Accept", MediaType.APPLICATION_JSON_VALUE).
                header("Content-Type", MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isCreated());

    }

    @Test
    public void addUserShouldReturnBadRequestWithoutBody() throws Exception {
        mockMvc.perform(post(USERS_RESOURCE, new UserBuilder().build()).
                header("Accept", MediaType.APPLICATION_JSON_VALUE).
                header("Content-Type", MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().is4xxClientError());
    }


    //    @Test
    public void updateUserShouldReturnUpdatedUser() throws Exception {
        User user = new UserBuilder()
                .setUserName(FIRST_USER_NAME)
                .setEmail(FIRST_EMAIL)
                .build();
        mockMvc.perform(put(USER_RESOURCE, firstUser.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(user))).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON));

        user.setId(firstUser.getId());

        mockMvc.perform(get(USER_RESOURCE, firstUser.getId()).
                header("Accept", MediaType.APPLICATION_JSON_VALUE).
                header("Content-Type", MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).
                andExpect(content().string(asJsonString(user))).
                andExpect(content().contentType(JSON_APPLICATION));

    }



    @Test
    public void deleteUserShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete(USER_RESOURCE, firstUser.getId()).contentType(MediaType.APPLICATION_JSON).
                header("Accept", MediaType.APPLICATION_JSON_VALUE).
                header("Content-Type", MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isNoContent());
    }

    @Test
    public void deleteUserShouldBeBadRequestIfNonExistingID() throws Exception {
        mockMvc.perform(delete(USER_RESOURCE, NON_EXISTING_ID).contentType(MediaType.APPLICATION_JSON).
                header("Accept", MediaType.APPLICATION_JSON_VALUE).
                header("Content-Type", MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}