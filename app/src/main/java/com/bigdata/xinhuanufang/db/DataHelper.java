package com.bigdata.xinhuanufang.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bigdata.xinhuanufang.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {
	public static int DB_VERSION = 1;
	private static  SQLiteDatabase db = null;
	private static  SqliteHelper dbHelper;
	private static  DataHelper dh ;

	public DataHelper() {
	}
	public static DataHelper getInstance(Context  context){
		if(null == dh){
			dbHelper = new SqliteHelper(context,"claim.db", null, DB_VERSION);
			db = dbHelper.getWritableDatabase();
			dh = new DataHelper();
		}
		return dh;
	}
	public void Closee() {
			return;
	}
	public void dataClosee(){
		 db.close();
		 dbHelper.close();
		 dh = null;
	}

	// 获取users表中的USERNUM、PASSWORD
	public List<UserInfo> GetUserList() {
		List<UserInfo> userList = new ArrayList<UserInfo>();
		Cursor cursor = db.query(SqliteHelper.TB_Name, null, null, null, null,null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast() && (cursor.getString(1) != null)) {
			UserInfo user = new UserInfo();
			user.setUserTel(cursor.getString(cursor
					.getColumnIndex(UserInfo.USERNUM)));
			user.setUserPwd(cursor.getString(cursor
					.getColumnIndex(UserInfo.PASSWORD)));
			userList.add(user);
			cursor.moveToNext();
		}
		cursor.close();
		return userList;
	}

	// 添加users表的记录
	public Long SaveUserInfo(UserInfo user) {
		ContentValues values = new ContentValues();
		values.put(UserInfo.PASSWORD, user.getUserPwd());
		values.put(UserInfo.USERNUM, user.getuserTel());
		
		Long uid = db.insert(SqliteHelper.TB_Name, null, values);
		Log.e("SaveUserInfo", uid + "");
		return uid;
	}

	// 删除users表的记录
	public int delUserByCode(String userCode) {
		int id = db.delete(SqliteHelper.TB_Name, "userCode ='"+userCode+"'", null);
		Log.e("DelUserInfo", id + "");
		return id;
	}
	
}