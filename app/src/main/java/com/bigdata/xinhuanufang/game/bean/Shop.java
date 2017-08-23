package com.bigdata.xinhuanufang.game.bean;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
/**
 * 
 */
public class Shop {

    private String shopId;
    private String shopAttrid;//��Ʒ����
    private String shopTitle;//��Ʒ����
    private String shopPic;//��ƷͼƬ
    private String shopPrice;//��Ʒ�۸�
    private List<Shoppic> shoppic; //��ƷͼƬ
    private List<ShopAttr> shopAttr;	//��Ʒ����ѡ��
    public void setShopId(String shopId) {
         this.shopId = shopId;
     }
     public String getShopId() {
         return shopId;
     }

    public void setShopAttrid(String shopAttrid) {
         this.shopAttrid = shopAttrid;
     }
     public String getShopAttrid() {
         return shopAttrid;
     }

    public void setShopTitle(String shopTitle) {
         this.shopTitle = shopTitle;
     }
     public String getShopTitle() {
         return shopTitle;
     }

    public void setShopPic(String shopPic) {
         this.shopPic = shopPic;
     }
     public String getShopPic() {
         return shopPic;
     }

    public void setShopPrice(String shopPrice) {
         this.shopPrice = shopPrice;
     }
     public String getShopPrice() {
         return shopPrice;
     }

    public void setShoppic(List<Shoppic> shoppic) {
         this.shoppic = shoppic;
     }
     public List<Shoppic> getShoppic() {
         return shoppic;
     }

    public void setShopAttr(List<ShopAttr> shopAttr) {
         this.shopAttr = shopAttr;
     }
     public List<ShopAttr> getShopAttr() {
         return shopAttr;
     }
	public Shop(String shopId, String shopAttrid, String shopTitle,
			String shopPic, String shopPrice, List<Shoppic> shoppic,
			List<ShopAttr> shopAttr) {
		super();
		this.shopId = shopId;
		this.shopAttrid = shopAttrid;
		this.shopTitle = shopTitle;
		this.shopPic = shopPic;
		this.shopPrice = shopPrice;
		this.shoppic = shoppic;
		this.shopAttr = shopAttr;
	}
	
}