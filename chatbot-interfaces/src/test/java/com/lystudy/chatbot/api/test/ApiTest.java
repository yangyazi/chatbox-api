package com.lystudy.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author luyoung
 * @since 2023-11-01 21:26
 */

@SpringBootApplication
public class ApiTest {

    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/51112814882884/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", "zsxq_access_token=14C42BC6-DF6F-1ED8-1B7B-E2195C0F8ED3_C1F7E9FFC7665CC8; abtest_env=product; b-user-id=b8d21ad9-2926-7b71-6246-23300e8e80e2; zsxqsessionid=5a177a8dd9e1207372de22c79c6c3009");
        get.addHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }



    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/211222245152541/answer");
        post.addHeader("cookie", "zsxq_access_token=14C42BC6-DF6F-1ED8-1B7B-E2195C0F8ED3_C1F7E9FFC7665CC8; abtest_env=product; b-user-id=b8d21ad9-2926-7b71-6246-23300e8e80e2; zsxqsessionid=5a177a8dd9e1207372de22c79c6c3009");
        post.addHeader("Content-Type", "application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"爱是你我\\n\",\n" +
                "    \"image_ids\": []\n" +
                "  }\n" +
                "}";


        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
