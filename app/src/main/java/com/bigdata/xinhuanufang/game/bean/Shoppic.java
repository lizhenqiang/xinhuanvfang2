package com.bigdata.xinhuanufang.game.bean;
/**
 */
public class Shoppic {

    private String shoppicId;//ͼƬid
    private String shoppicPic;//ͼƬ����
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