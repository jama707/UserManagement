package com.comcast.coding.resource;

import com.comcast.coding.config.ApplicationConfig;
import com.comcast.coding.entity.User;
import com.comcast.coding.repository.UserRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@SpringApplicationConfiguration(classes = {ApplicationConfig.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Transactional
public class UserResourceIT {
    private static final String FIRST_EMAIL = "first@mail.ru";
    private static final String SECOND_EMAIL = "second@mail.ru";
    private static final String THIRD_EMAIL = "third@mail.ru";
    private static final String FIRST_USER_NAME = "First User";
    private static final String SECOND_USER_NAME = "Second User";
    private static final String THIRD_USER_NAME = "Third User";

    private static final String EMAIL_FIELD = "email";
    private static final String USERNAME_FIELD = "userName";
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

    @Autowired
    private UserRepository repository;

    @Value("${local.server.port}")
    private int serverPort;
    private User firstUser;
    private User secondUser;

    @Before
    public void setUp() {
//        repository.deleteAll();
        firstUser = repository.save(FIRST_USER);
        secondUser = repository.save(SECOND_USER);
        RestAssured.port = serverPort;
    }

    @Test
    public void addUserShouldReturnSavedUser() {
        given()
                .body(THIRD_USER)
                .contentType(ContentType.JSON)
                .when()
                .post(USERS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(USERNAME_FIELD, is(THIRD_USER_NAME))
                .body(EMAIL_FIELD, is(THIRD_EMAIL));
    }

    @Test
    public void addUserShouldReturnBadRequestWithoutBody() {
        when()
                .post(USERS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void addUserShouldReturnNotSupportedMediaTypeIfNonJSON() {
        given()
                .body(THIRD_USER)
                .when()
                .post(USERS_RESOURCE)
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

//    @Test
    public void updateUserShouldReturnUpdatedUser() {
        User user = new UserBuilder()
                .setUserName(FIRST_USER_NAME)
                .setEmail(FIRST_EMAIL)
                .build();
        given()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .put(USER_RESOURCE, firstUser.getId())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(USERNAME_FIELD, is(FIRST_USER_NAME))
                .body(FIRST_EMAIL, is(FIRST_EMAIL));
    }

    @Test
    public void updateUserShouldReturnBadRequestWithoutBody() {
        when()
                .put(USER_RESOURCE, firstUser.getId())
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void updateUserShouldReturnNotSupportedMediaTypeIfNonJSON() {
        given()
                .body(FIRST_USER)
                .when()
                .put(USER_RESOURCE, firstUser.getId())
                .then()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    public void updateUserShouldBeBadRequestIfNonExistingID() {
        given()
                .body(FIRST_USER)
                .contentType(ContentType.JSON)
                .when()
                .put(USER_RESOURCE, NON_EXISTING_ID)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void deleteUserShouldReturnNoContent() {
        when()
                .delete(USER_RESOURCE, secondUser.getId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void deleteUserShouldBeBadRequestIfNonExistingID() {
        when()
                .delete(USER_RESOURCE, NON_EXISTING_ID)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}