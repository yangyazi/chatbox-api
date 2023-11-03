package com.lystudy.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * @author luyoung
 * @since 2023-11-03 9:13
 * description chatgpt 接口
 */
public interface IOpenAI {

    String doChatGpt(String question) throws IOException;
}
