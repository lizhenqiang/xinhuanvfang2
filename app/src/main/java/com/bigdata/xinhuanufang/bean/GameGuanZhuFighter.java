package com.bigdata.xinhuanufang.bean;

import java.util.List;

public class GameGuanZhuFighter {
	/**
	 * "player_id":"3",
	 "is_delete":"0",
	 "player_head":"/uploads/83232323233.jpg",
	 "player_name":"邹市明",
	 "player_ageid":"1",
	 "player_areaid":"3",
	 "player_weightid":"3",
	 "player_age":"34",
	 "player_sex":"男",
	 "player_area":"中国",
	 "player_level":"男子200公斤",
	 "player_height":"177CM",
	 "player_cm":"76CM",
	 "player_special":"猛击",
	 "player_group":"广东战队",
	 "player_date":"1487330781",
	 "win":"1",
	 "drew":"1",
	 "ko":"0"
	 */
	private String player_id;
	private String is_delete;
	private String player_head;
	private String player_name;
	private String player_ageid;
	private String player_areaid;
	private String player_weightid;
	private String player_age;
	private String player_sex;
	private String player_area;
	private String player_level;
	private String player_height;
	private String player_cm;
	private String player_special;
	private String player_group;
	private String player_date;
	private List<GameGuanZhuFighterHonors> honors;
	private String win;
	private String drew;
	private String ko;
	public String getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(String player_id) {
		this.player_id = player_id;
	}
	public String getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(String is_delete) {
		this.is_delete = is_delete;
	}
	public String getPlayer_head() {
		return player_head;
	}
	public void setPlayer_head(String player_head) {
		this.player_head = player_head;
	}
	public String getPlayer_name() {
		return player_name;
	}
	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}
	public String getPlayer_ageid() {
		return player_ageid;
	}
	public void setPlayer_ageid(String player_ageid) {
		this.player_ageid = player_ageid;
	}
	public String getPlayer_areaid() {
		return player_areaid;
	}
	public void setPlayer_areaid(String player_areaid) {
		this.player_areaid = player_areaid;
	}
	public String getPlayer_weightid() {
		return player_weightid;
	}
	public void setPlayer_weightid(String player_weightid) {
		this.player_weightid = player_weightid;
	}
	public String getPlayer_age() {
		return player_age;
	}
	public void setPlayer_age(String player_age) {
		this.player_age = player_age;
	}
	public String getPlayer_sex() {
		return player_sex;
	}
	public void setPlayer_sex(String player_sex) {
		this.player_sex = player_sex;
	}
	public String getPlayer_area() {
		return player_area;
	}
	public void setPlayer_area(String player_area) {
		this.player_area = player_area;
	}
	public String getPlayer_level() {
		return player_level;
	}
	public void setPlayer_level(String player_level) {
		this.player_level = player_level;
	}
	public String getPlayer_height() {
		return player_height;
	}
	public void setPlayer_height(String player_height) {
		this.player_height = player_height;
	}
	public String getPlayer_cm() {
		return player_cm;
	}
	public void setPlayer_cm(String player_cm) {
		this.player_cm = player_cm;
	}
	public String getPlayer_special() {
		return player_special;
	}
	public void setPlayer_special(String player_special) {
		this.player_special = player_special;
	}
	public String getPlayer_group() {
		return player_group;
	}
	public void setPlayer_group(String player_group) {
		this.player_group = player_group;
	}
	public String getPlayer_date() {
		return player_date;
	}
	public void setPlayer_date(String player_date) {
		this.player_date = player_date;
	}
	public List<GameGuanZhuFighterHonors> getHonors() {
		return honors;
	}
	public void setHonors(List<GameGuanZhuFighterHonors> honors) {
		this.honors = honors;
	}
	public String getWin() {
		return win;
	}
	public void setWin(String win) {
		this.win = win;
	}
	public String getDrew() {
		return drew;
	}
	public void setDrew(String drew) {
		this.drew = drew;
	}
	public String getKo() {
		return ko;
	}
	public void setKo(String ko) {
		this.ko = ko;
	}
	public GameGuanZhuFighter(String player_id, String is_delete,
							  String player_head, String player_name, String player_ageid,
							  String player_areaid, String player_weightid, String player_age,
							  String player_sex, String player_area, String player_level,
							  String player_height, String player_cm, String player_special,
							  String player_group, String player_date,
							  List<GameGuanZhuFighterHonors> honors, String win, String drew,
							  String ko) {
		this.player_id = player_id;
		this.is_delete = is_delete;
		this.player_head = player_head;
		this.player_name = player_name;
		this.player_ageid = player_ageid;
		this.player_areaid = player_areaid;
		this.player_weightid = player_weightid;
		this.player_age = player_age;
		this.player_sex = player_sex;
		this.player_area = player_area;
		this.player_level = player_level;
		this.player_height = player_height;
		this.player_cm = player_cm;
		this.player_special = player_special;
		this.player_group = player_group;
		this.player_date = player_date;
		this.honors = honors;
		this.win = win;
		this.drew = drew;
		this.ko = ko;
	}

}
