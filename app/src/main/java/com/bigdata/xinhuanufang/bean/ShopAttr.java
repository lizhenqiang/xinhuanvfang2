package com.bigdata.xinhuanufang.bean;
import java.util.List;
/**
 *商品参数选择的数据
 */
public class ShopAttr {

    private String attrId;
    private String attrName; //机身颜色
    private List<Childs> childs; //机身颜色的属性选择
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