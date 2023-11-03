package com.lystudy.chatbot.api.domain.ai.model.vo;

/**
 * @author luyoung
 * @since 2023-11-03 9:18
 * description
 */
public class Choices
{
    private int index;

    private Message message;

    private String finish_reason;

    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return this.index;
    }
    public void setMessage(Message message){
        this.message = message;
    }
    public Message getMessage(){
        return this.message;
    }
    public void setFinish_reason(String finish_reason){
        this.finish_reason = finish_reason;
    }
    public String getFinish_reason(){
        return this.finish_reason;
    }
}