package com.bigdata.xinhuanufang.game.bean;
import java.util.List;
/**
 *��Ʒ����ѡ�������
 */
public class ShopAttr {

    private String attrId; 
    private String attrName; //������ɫ
    private List<Childs> childs; //������ɫ������ѡ��
    public void setAttrId(String attrId) {
         this.attrId = attrId;
     }
     public String getAttrId() {
         return attrId;
     }

    public void setAttrName(String attrName) {
         this.attrName = attrName;
     }
     public String getAttrName() {
         return attrName;
     }

    public void setChilds(List<Childs> childs) {
         this.childs = childs;
     }
     public List<Childs> getChilds() {
         return childs;
     }
	public ShopAttr(String attrId, String attrName, List<Childs> childs) {
		this.attrId = attrId;
		this.attrName = attrName;
		this.childs = childs;
	}

}