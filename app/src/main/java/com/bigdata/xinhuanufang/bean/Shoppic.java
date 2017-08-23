package com.bigdata.xinhuanufang.bean;
/**
 */
public class Shoppic {

    private String shoppicId;//图片id
    private String shoppicPic;//图片链接
    public void setShoppicId(String shoppicId) {
        this.shoppicId = shoppicId;
    }
    public String getShoppicId() {
        return shoppicId;
    }

    public void setShoppicPic(String shoppicPic) {
        this.shoppicPic = shoppicPic;
    }
    public String getShoppicPic() {
        return shoppicPic;
    }
    public Shoppic(String shoppicId, String shoppicPic) {
        this.shoppicId = shoppicId;
        this.shoppicPic = shoppicPic;
    }

}