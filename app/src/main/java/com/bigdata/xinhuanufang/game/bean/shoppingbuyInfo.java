package com.bigdata.xinhuanufang.game.bean;

import java.util.List;

public class shoppingbuyInfo {
	/**
	 * "orders_id":"2",
            "orders_outtradeno":"20170224175211834",
            "orders_money":"9999.99",
            "orders_message":"˵ʵ��̫��",
            "orders_addressid":"1",
            "orders_date":"1487929931",
            "cart":
            �ҵ�ģ����̳Ƕ���--->��֧��
	 */
private String orders_id;
private String orders_outtradeno;
private String orders_money;
private String orders_message;
private String orders_addressid;
private String orders_date;
private List<shoppingmall_yizhifu_cart> cart;
private List<shoppingmall_yizhifu_address> address;
public String getOrders_id() {
	return orders_id;
}
public void setOrders_id(String orders_id) {
	this.orders_id = orders_id;
}
public String getOrders_outtradeno() {
	return orders_outtradeno;
}
public void setOrders_outtradeno(String orders_outtradeno) {
	this.orders_outtradeno = orders_outtradeno;
}
public String getOrders_money() {
	return orders_money;
}
public void setOrders_money(String orders_money) {
	this.orders_money = orders_money;
}
public String getOrders_message() {
	return orders_message;
}
public void setOrders_message(String orders_message) {
	this.orders_message = orders_message;
}
public String getOrders_addressid() {
	return orders_addressid;
}
public void setOrders_addressid(String orders_addressid) {
	this.orders_addressid = orders_addressid;
}
public String getOrders_date() {
	return orders_date;
}
public void setOrders_date(String orders_date) {
	this.orders_date = orders_date;
}
public List<shoppingmall_yizhifu_cart> getCart() {
	return cart;
}
public void setCart(List<shoppingmall_yizhifu_cart> cart) {
	this.cart = cart;
}
public List<shoppingmall_yizhifu_address> getAddress() {
	return address;
}
public void setAddress(List<shoppingmall_yizhifu_address> address) {
	this.address = address;
}
public shoppingbuyInfo(String orders_id, String orders_outtradeno,
		String orders_money, String orders_message, String orders_addressid,
		String orders_date, List<shoppingmall_yizhifu_cart> cart,
		List<shoppingmall_yizhifu_address> address) {
	this.orders_id = orders_id;
	this.orders_outtradeno = orders_outtradeno;
	this.orders_money = orders_money;
	this.orders_message = orders_message;
	this.orders_addressid = orders_addressid;
	this.orders_date = orders_date;
	this.cart = cart;
	this.address = address;
}


}
