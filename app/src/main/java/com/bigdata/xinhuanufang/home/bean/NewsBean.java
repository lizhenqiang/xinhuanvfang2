package com.bigdata.xinhuanufang.home.bean;

import java.util.List;

/**
 * Created by SEELE on 2017/8/18.
 */

public class NewsBean {

    private List<List1Bean> list1;
    private List<List2Bean> list2;

    public List<List1Bean> getList1() {
        return list1;
    }

    public void setList1(List<List1Bean> list1) {
        this.list1 = list1;
    }

    public List<List2Bean> getList2() {
        return list2;
    }

    public void setList2(List<List2Bean> list2) {
        this.list2 = list2;
    }

    public static class List1Bean {
        /**
         * news_id : 115
         * news_pic : /uploads/20170817112846868136.jpg
         * news_title : 我就是拳王”下月登陆广州
         * news_title2 : 深圳武术高手可就近参赛
         * news_date : 1502940526
         */

        private String news_id;
        private String news_pic;
        private String news_title;
        private String news_title2;
        private String news_date;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getNews_pic() {
            return news_pic;
        }

        public void setNews_pic(String news_pic) {
            this.news_pic = news_pic;
        }

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getNews_title2() {
            return news_title2;
        }

        public void setNews_title2(String news_title2) {
            this.news_title2 = news_title2;
        }

        public String getNews_date() {
            return news_date;
        }

        public void setNews_date(String news_date) {
            this.news_date = news_date;
        }
    }

    public static class List2Bean {
        /**
         * news_id : 109
         * news_pic : /uploads/20170728170022165448.jpg
         * news_title : 我就是拳王功夫训练营
         * news_title2 : 小钢炮带队主攻拳法腿法
         * news_date : 1501232422
         */

        private String news_id;
        private String news_pic;
        private String news_title;
        private String news_title2;
        private String news_date;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getNews_pic() {
            return news_pic;
        }

        public void setNews_pic(String news_pic) {
            this.news_pic = news_pic;
        }

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getNews_title2() {
            return news_title2;
        }

        public void setNews_title2(String news_title2) {
            this.news_title2 = news_title2;
        }

        public String getNews_date() {
            return news_date;
        }

        public void setNews_date(String news_date) {
            this.news_date = news_date;
        }
    }
}
