package com.bigdata.xinhuanufang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bigdata.xinhuanufang.model.UserInfo;

public class SqliteHelper extends SQLiteOpenHelper {
	// 表名
	public static final String TB_Name = "users"; // 用户表
	public SqliteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("database  database", "execute execute");
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TB_Name + "("
				+ UserInfo.USERNUM + " varchar primary key,"
				+ UserInfo.PASSWORD + ")");
		Log.e("Database", "onCreate");
	}

	// 更新表
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
	}
 
}