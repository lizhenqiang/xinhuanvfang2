package com.bigdata.xinhuanufang.bean;

/**
 */
public class Childs {

    private String attrId;
    private String attrName;
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
	public Childs(String attrId, String attrName) {
		this.attrId = attrId;
		this.attrName = attrName;
	}

}