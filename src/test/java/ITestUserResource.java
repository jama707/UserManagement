import com.comcast.coding.config.ApplicationConfig;
import com.comcast.coding.entity.User;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.Assert.assertThat;

/**
 * Created by Jama Asatillayev on 5/27/2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@IntegrationTest
public class ITestUserResource {
    final String BASE_URL = "http://localhost:8080/users/";

    final String USER_NAME = "jama707@outlook.com";
    final String USER_ADDRESS = "Jama";
    User user = new User();

    RestTemplate restTemplate = new TestRestTemplate();



    @Test
    public void shouldCreateNewUser() {
        user.setEmail(USER_NAME);
        user.setUserName(USER_ADDRESS);

        ResponseEntity<User> response =
                restTemplate.postForEntity(BASE_URL, user, User.class);
        assertThat(response.getStatusCode(), IsEqual.equalTo(HttpStatus.CREATED));
        user = response.getBody();
    }

    @Test
    public void shouldRetrieveUser() {

        ResponseEntity<User> response =
                restTemplate.getForEntity(BASE_URL + "/" + user.getId(), User.class);

        assertThat(response.getStatusCode(), IsEqual.equalTo(HttpStatus.OK));
    }


}
