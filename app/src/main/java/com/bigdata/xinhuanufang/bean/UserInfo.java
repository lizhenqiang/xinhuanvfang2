package com.bigdata.xinhuanufang.bean;

/**
 * Created by weiyu$ on 2017/4/4.
 */

public class UserInfo {
    /**
     "user_id":"1",
     "user_delete":"0",
     "user_tel":"18519011617",
     "user_gloves":"6440",
     "user_head":"/system/media/Pre-loaded/Pictures/Picture_01_Greenery",
     "user_username":"vjv",
     "user_pwd":"F379EAF3C831B04DE153469D1BEC345E",
     "user_sex":"男",
     "user_sign":"vhg",
     "user_date":"1487571902"
     */
    private String user_id;
    private String user_delete;
    private String user_tel;
    private String user_gloves;
    private String user_head;
    private String user_username;
    private String user_pwd;
    private String user_sex;
    private String user_sign;
    private String user_date;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_delete() {
        return user_delete;
    }

    public void setUser_delete(String user_delete) {
        this.user_delete = user_delete;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getUser_gloves() {
        return user_gloves;
    }

    public void setUser_gloves(String user_gloves) {
        this.user_gloves = user_gloves;
    }

    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_sign() {
        return user_sign;
    }

    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign;
    }

    public String getUser_date() {
        return user_date;
    }

    public void setUser_date(String user_date) {
        this.user_date = user_date;
    }

    public UserInfo(String user_id, String user_delete, String user_tel, String user_gloves, String user_head, String user_username, String user_pwd, String user_sex, String user_sign, String user_date) {
        this.user_id = user_id;
        this.user_delete = user_delete;
        this.user_tel = user_tel;
        this.user_gloves = user_gloves;
        this.user_head = user_head;
        this.user_username = user_username;
        this.user_pwd = user_pwd;
        this.user_sex = user_sex;
        this.user_sign = user_sign;
        this.user_date = user_date;
    }
}
