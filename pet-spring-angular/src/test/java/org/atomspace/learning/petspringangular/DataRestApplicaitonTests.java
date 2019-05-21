package org.atomspace.learning.petspringangular;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.atomspace.learning.petspringangular.dbmodel.Topic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sergey.derevyanko on 21.05.19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DataRestApplicaitonTests {


    @LocalServerPort
    int randomServerPort;

    private TestRestTemplate restTemplate =  new TestRestTemplate();

    @Before
    public void setup(){
        //Setting apache httpClient to enable patch method
        HttpClient httpClient = HttpClientBuilder.create().build();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @Test
    public void contextLoads(){

    }

    @Test
    public void testBasicPost(){
        Topic topic = new Topic("The New Topic", "New topic Description",
                "Text F 1", "Text F2");
        Topic createdTopic = restTemplate.postForObject(buildFullUrl("/topics"), topic, Topic.class);
        assertNotNull(createdTopic.getId());
        assertTrue(createdTopic.getName().equals(topic.getName()));
        //TODO add asserts for all other fields
    }

    private URI buildFullUrl(String relativeUrl) {
        if(!relativeUrl.startsWith("/")){
            relativeUrl = "/" + relativeUrl;
        }
        final String searchUrl = "http://localhost:" + randomServerPort + "/api" + relativeUrl;
        URI searchURI;
        try {
            searchURI = new URI(searchUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
        return searchURI;
    }

    //TODO: Add tests to RestRepository
}
