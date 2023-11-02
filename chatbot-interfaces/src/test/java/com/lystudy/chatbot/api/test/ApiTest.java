package com.lystudy.chatbot.api.test;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
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
import java.nio.charset.StandardCharsets;

/**
 * @author luyoung
 * @since 2023-11-01 21:26
 * description 知识星球api调用
 */

//@SpringBootApplication
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

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/211222245115241/answer");
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

//    @Test
//    public void test_chatGPT() throws IOException {
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "7890");
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
//        post.addHeader("Content-Type", "application/json");
//        post.addHeader("Authorization", "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");
//
//        String paramJson = "{\n" +
//                "     \"model\": \"gpt-3.5-turbo\",\n" +
//                "     \"messages\": [{\"role\": \"user\", \"帮我写一个java冒泡排序\": \"Say this is a test!\"}],\n" +
//                "     \"temperature\": 0.7\n" +
//                "   }";
//
//        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
//        post.setEntity(stringEntity);
//
//        CloseableHttpResponse response = httpClient.execute(post);
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            String res = EntityUtils.toString(response.getEntity());
//            System.out.println(res);
//        } else {
//            System.out.println(response.getStatusLine().getStatusCode());
//        }
//
//    }

    @Test
    public void test_chatGPT() {
        String proxyHost  = "127.0.0.1";//本机地址
        int proxyPort = 7890; //代理端口号
        //创建一个 HttpHost 实例，这样就设置了代理服务器的主机和端口。
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        //创建一个 RequestConfig 对象，然后使用 setProxy() 方法将代理 httpHost 设置进去。
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();



        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
            post.addHeader("Content-Type", "application/json");
            post.addHeader("Authorization", "Bearer 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");



            // 新增代码：将代理类放入配置中
            post.setConfig(requestConfig);

//
            String paramJson = "{\n" +
                    "     \"model\": \"gpt-3.5-turbo\",\n" +
                    "     \"messages\": [{\"role\": \"user\", \"content\": \"帮我写一个java冒泡排序\"}],\n" +
                    "     \"temperature\": 0.7\n" +
                    "   }";

//            String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";
            StringEntity stringEntity = new StringEntity(paramJson, StandardCharsets.UTF_8);
            post.setEntity(stringEntity);

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String res = EntityUtils.toString(response.getEntity());
                    System.out.println(res);
                } else {
                    System.out.println(response.getStatusLine().getStatusCode());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
