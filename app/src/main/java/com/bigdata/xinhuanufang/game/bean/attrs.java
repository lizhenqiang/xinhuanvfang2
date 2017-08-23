package com.bigdata.xinhuanufang.game.bean;

import java.util.List;


public class attrs {
	/**
	 * "attr_id":"1", "attr_pid":"0", "attr_name":"机身颜色", "childs"
	 */

	private String attr_id;
	private String attr_pid;
	private String attr_name;
	private List<shopingCarchilds> childs;

	public String getAttr_id() {
		return attr_id;
	}

	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}

	public String getAttr_pid() {
		return attr_pid;
	}

	public void setAttr_pid(String attr_pid) {
		this.attr_pid = attr_pid;
	}

	public String getAttr_name() {
		return attr_name;
	}

	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}

	public List<shopingCarchilds> getChilds() {
		return childs;
	}

	public void setChilds(List<shopingCarchilds> childs) {
		this.childs = childs;
	}


	public attrs(
			String attr_id,
			String attr_pid,
			String attr_name,
			List<shopingCarchilds> childs) {
		this.attr_id = attr_id;
		this.attr_pid = attr_pid;
		this.attr_name = attr_name;
		this.childs = childs;
	}



}