package bel.tnp.resources;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Dmitry on 2/26/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ResourceApplication.class)
@Slf4j
public class ResourceApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate template;

    @Test
    public void resourceLoads() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/resource/", String.class, port);
        log.info("-------------------------- resourceLoads = " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String responseBodyString = response.getBody();
        //JSON from String to Object
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(responseBodyString, Message.class);
            String messageId = message.getId();
            assertNotNull(messageId);
            String messageContentString = message.getContent();
            assertEquals("Hello World", messageContentString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void resourceSamplestringLoads() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:{port}/resource/samplestring/", String.class, port);
        log.info("+++++++++++++++++++++++++++ resourceSamplestringLoads = " + response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        String auth = response.getHeaders().getFirst("WWW-Authenticate");
        assertTrue("Wrong header: " + auth , auth.startsWith("Bearer"));
    }
}