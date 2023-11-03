package com.lystudy.chatbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.lystudy.chatbot.api.domain.ai.IOpenAI;
import com.lystudy.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.lystudy.chatbot.api.domain.ai.model.vo.Choices;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author luyoung
 * @since 2023-11-03 9:14
 * description
 */
@Service
public class OpenAI implements IOpenAI {
    private Logger logger = LoggerFactory.getLogger(OpenAI.class);

    @Value("${chatbot-api.openAiKey}")
    private String openAiKey;

    @Override
    public String doChatGpt(String question) throws IOException {
        String proxyHost = "127.0.0.1";//本机地址
        int proxyPort = 7890; //代理端口号
        //创建一个 HttpHost 实例，这样就设置了代理服务器的主机和端口。
        HttpHost proxy = new HttpHost(proxyHost, proxyPort);
        //创建一个 RequestConfig 对象，然后使用 setProxy() 方法将代理 httpHost 设置进去。
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 添加请求日志
        logger.info("Sending request to OpenAI API");

// 构建Authorization头
        String authorization = "Bearer " + openAiKey;

// 打印Authorization头部值
        logger.info("Authorization: " + authorization);

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + openAiKey);
        post.setConfig(requestConfig);
        String paramJson = "{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"" + question + "\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);


        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AIAnswer aiAnswer = JSON.parseObject(jsonStr, AIAnswer.class);
            StringBuilder answers = new StringBuilder();
            List<Choices> choices = aiAnswer.getChoices();
            for (Choices choice : choices) {
                answers.append(choice.getMessage().getContent());
            }
            return answers.toString();
        } else {
            throw new RuntimeException("api.openai.com Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
