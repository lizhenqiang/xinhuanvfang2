package com.bigdata.xinhuanufang.game.bean;

import java.util.List;

/**
 * list1��ͷ����Ѷ��list2����������
news_id���
news_pic�б�Сͼ
news_title����
news_title2������
news_date�������ڡ�ʱ���

 * @author weiyu$
 *
  */
public class NewsBean {
private List<List1> mList1;
	
	private List<List2> mList2;
	public NewsBean(List<List1> mList1,List<List2> mList2){
		this.mList1 = mList1;
		this.mList2 = mList2;
	}
	public List<List1> getmList1() {
		return mList1;
	}
	public void setmList1(List<List1> mList1) {
		this.mList1 = mList1;
	}
	public List<List2> getmList2() {
		return mList2;
	}
	public void setmList2(List<List2> mList2) {
		this.mList2 = mList2;
	}
	
	

	
}
