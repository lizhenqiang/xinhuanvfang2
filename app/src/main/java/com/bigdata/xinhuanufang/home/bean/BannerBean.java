package com.bigdata.xinhuanufang.home.bean;

import java.util.List;

/**
 * Created by SEELE on 2017/8/11.
 */

public class BannerBean {
    /**
     * code : 1
     * layer : [{"layer_id":"89","layer_flag":"0","layer_pic":"/uploads/20170605091644473539.png","layer_status":"0"},{"layer_id":"93","layer_flag":"0","layer_pic":"/uploads/20170605092323304266.png","layer_status":"0"},{"layer_id":"94","layer_flag":"0","layer_pic":"/uploads/20170605093524618907.png","layer_status":"0"},{"layer_id":"91","layer_flag":"0","layer_pic":"/uploads/20170605092010539754.png","layer_status":"0"},{"layer_id":"92","layer_flag":"0","layer_pic":"/uploads/20170605092129322085.png","layer_status":"0"}]
     */

    private int code;
    private List<LayerBean> layer;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LayerBean> getLayer() {
        return layer;
    }

    public void setLayer(List<LayerBean> layer) {
        this.layer = layer;
    }

    public static class LayerBean {
        /**
         * layer_id : 89
         * layer_flag : 0
         * layer_pic : /uploads/20170605091644473539.png
         * layer_status : 0
         */

        private String layer_id;
        private String layer_flag;
        private String layer_pic;
        private String layer_status;

        public String getLayer_id() {
            return layer_id;
        }

        public void setLayer_id(String layer_id) {
            this.layer_id = layer_id;
        }

        public String getLayer_flag() {
            return layer_flag;
        }

        public void setLayer_flag(String layer_flag) {
            this.layer_flag = layer_flag;
        }

        public String getLayer_pic() {
            return layer_pic;
        }

        public void setLayer_pic(String layer_pic) {
            this.layer_pic = layer_pic;
        }

        public String getLayer_status() {
            return layer_status;
        }

        public void setLayer_status(String layer_status) {
            this.layer_status = layer_status;
        }
    }
}
