package com.epam.esm;

import com.epam.esm.config.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class}, loader = AnnotationConfigContextLoader.class)
public class TagControllerIT {

//    showTagByIdShouldReturn
//    showAllTags
//    deleteTagShouldBeRemoved

    @Test
    public void whenSaveTagShouldSaveAndReturnTagWithId() {

    }

//        String json = "{\"name\": \"tag1\"};";
//        HttpUriRequest request = new HttpPost("https://localhost:8080/tag");
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "tag1");
//        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString());
//        ResponseEntity<String> responseEntityStr = restTemplate.
//                postForEntity(createPersonUrl, request, String.class);
//        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());
//        assertNotNull(responseEntityStr.getBody());
//        assertNotNull(root.path("name").asText());

//    @Test
//    public void testGetCodeResponse() throws IOException {
//        HttpUriRequest request1 = new HttpGet("http://localhost:8080/tag/list");
//        HttpResponse response = HttpClientBuilder.create().build().execute(request1);
//        assertEquals(HttpStatus.OK.value(), response.getStatusLine().getStatusCode());
//    }
}
