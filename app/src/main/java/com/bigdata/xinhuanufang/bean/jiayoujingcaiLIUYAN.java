package com.bigdata.xinhuanufang.bean;

/**
 * Created by weiyu$ on 2017/4/25.
 */

public class jiayoujingcaiLIUYAN {
    /**
     * "user_username":null,
     "user_head":null,
     "message_content":"拳王就是拳王，永远支持拳王",
     "message_date":"1487583592"
     */

    private String user_username;
    private String user_head;
    private String message_content;
    private String message_date;

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public jiayoujingcaiLIUYAN(String user_username, String user_head, String message_content, String message_date) {
        this.user_username = user_username;
        this.user_head = user_head;
        this.message_content = message_content;
        this.message_date = message_date;
    }
}
