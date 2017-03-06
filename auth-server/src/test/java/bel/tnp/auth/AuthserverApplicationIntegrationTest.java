package bel.tnp.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by Dmitry on 2/25/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes = AuthserverApplication.class)
@Slf4j
public class AuthserverApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate template;

    @Test
    public void homePageProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/uaa/", String.class);
        log.info("++++++++++++++++++++" + response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        String auth = response.getHeaders().getFirst("WWW-Authenticate");
        assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""));
    }

    @Test
    public void userEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/uaa/user", String.class);
        log.info("----------------------" + response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        String auth = response.getHeaders().getFirst("WWW-Authenticate");
        assertTrue("Wrong header: " + auth, auth.startsWith("Bearer realm=\""));
    }

//    @Test
//    public void authorizationRedirects() {
//        ResponseEntity<String> response = template.getForEntity("http://localhost:"
//                + port + "/uaa/oauth/authorize", String.class);
//        log.info("**********************" + response);
//        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//        String auth = response.getHeaders().getFirst("WWW-Authenticate");
//        assertTrue("Wrong header: " + auth, auth.startsWith("Basic realm=\""));
//    }

}
