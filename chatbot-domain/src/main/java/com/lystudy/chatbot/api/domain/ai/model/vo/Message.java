package com.lystudy.chatbot.api.domain.ai.model.vo;

/**
 * @author luyoung
 * @since 2023-11-03 11:47
 * description
 */
public class Message
{
    private String role;

    private String content;

    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return this.role;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
}
