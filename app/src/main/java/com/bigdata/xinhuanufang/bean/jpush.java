package com.bigdata.xinhuanufang.bean;

/**
 * Created by weiyu$ on 2017/4/27.
 */

public class jpush {
    /**
     * "jpush_id":"1",
     "jpush_userid":"0",
     "jpush_content":"心花怒放。花心怒放。",
     "jpush_date":"1490813787"
     */

    private String jpush_id;
    private String jpush_userid;
    private String jpush_content;
    private String jpush_date;

    public String getJpush_id() {
        return jpush_id;
    }

    public void setJpush_id(String jpush_id) {
        this.jpush_id = jpush_id;
    }

    public String getJpush_userid() {
        return jpush_userid;
    }

    public void setJpush_userid(String jpush_userid) {
        this.jpush_userid = jpush_userid;
    }

    public String getJpush_content() {
        return jpush_content;
    }

    public void setJpush_content(String jpush_content) {
        this.jpush_content = jpush_content;
    }

    public String getJpush_date() {
        return jpush_date;
    }

    public void setJpush_date(String jpush_date) {
        this.jpush_date = jpush_date;
    }

    public jpush(String jpush_id, String jpush_userid, String jpush_content, String jpush_date) {
        this.jpush_id = jpush_id;
        this.jpush_userid = jpush_userid;
        this.jpush_content = jpush_content;
        this.jpush_date = jpush_date;
    }
}
