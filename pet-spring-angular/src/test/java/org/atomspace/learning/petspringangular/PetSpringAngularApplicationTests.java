package org.atomspace.learning.petspringangular;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.atomspace.learning.petspringangular.dbmodel.Topic;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PetSpringAngularApplicationTests {

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
	public void contextLoads() {
	}

	@Test
	public void testBasicSearch() throws URISyntaxException {
		String relativeUrl = "/topics?searchString=tEst";
		Topic[] topics = this.restTemplate.getForObject(buildFullUrl(relativeUrl), Topic[].class);
		assertTrue(topics.length >= 2);
		for(int i=0; i<topics.length; i++){
			assertTrue(topics[i].getName() != null);
		}
	}

	@Test
	public void testBasicGet(){
		Topic topic = doGetTopic(2L);
		assertTrue(topic.getName().contains("spring"));
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

	@Test
	public void testBasicPut(){
		Topic topic = new Topic("A New Topic for update", "A topic-for-update Description",
				"Text for a topic-for-update  F 1", "Text for a topic-for-update  F 2");
		Topic createdTopic = restTemplate.postForObject(buildFullUrl("/topics"), topic, Topic.class);
		topic = new Topic();
		topic.setId(createdTopic.getId());
		String updatedName = "Updated Topic Name";
		topic.setName(updatedName);

		//There is no putForObject
		HttpEntity<Topic> topicHttpEntity = new HttpEntity<>(topic);
		ResponseEntity<Topic> updatedTopic = this.restTemplate.exchange(buildFullUrl("/topic/"+createdTopic.getId()),
				HttpMethod.PUT, topicHttpEntity, Topic.class);
		assertTrue(updatedTopic.getBody().getName().equals(updatedName));
		topic = doGetTopic(createdTopic.getId());
		assertTrue(topic.getName().equals(updatedName));
		assertNull(topic.getDescription());
		assertNull(topic.getTextField1());
		assertNull(topic.getTextField2());
	}

	@Test
	public void testBasicPatch(){
		Topic topic = new Topic("A New Topic for patch", "A topic-for-patch Description",
				"Text for a topic-for-patch  F 1", "Text for a topic-for-patch  F 2");
		Topic createdTopic = restTemplate.postForObject(buildFullUrl("/topics"), topic, Topic.class);
		topic = new Topic();
		topic.setId(createdTopic.getId());
		String updatedName = "Patched Topic Name";
		topic.setName(updatedName);

		Topic patchedTopic = this.restTemplate.patchForObject(buildFullUrl("/topic/" + createdTopic.getId()),
				topic, Topic.class);
		assertTrue(patchedTopic.getName().equals(updatedName));
		topic = doGetTopic(createdTopic.getId());
		assertTrue(topic.getName().equals(updatedName));
		assertNotNull(topic.getDescription());
		assertNotNull(topic.getTextField1());
		assertNotNull(topic.getTextField2());
	}

	private URI buildFullUrl(String relativeUrl) {
		if(!relativeUrl.startsWith("/")){
			relativeUrl = "/" + relativeUrl;
		}
		final String searchUrl = "http://localhost:" + randomServerPort + relativeUrl;
		URI searchURI;
		try {
			searchURI = new URI(searchUrl);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage());
		}
		return searchURI;
	}

	private Topic doGetTopic(Long id){
		Topic topic = restTemplate.getForObject(buildFullUrl("/topic/"+id), Topic.class);
		assertNotNull(topic);
		assertTrue(topic.getId().equals(id));
		return topic;
	}
}
