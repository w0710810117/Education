package com.fzw.education.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fzw.education.R;
import com.fzw.education.R.raw;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class ImportDatabase {
	private int SIZE=2048;
	private final String DB_NAME="education_db.db";
	private String PACKAGE_NAME="com.fzw.education";
	private String DATABASES="databases";
	private String DB_PATH="/data"+Environment.getDataDirectory().getAbsolutePath()+"/"+PACKAGE_NAME;
	private Context context;
	private SQLiteDatabase db=null;
	private File file;
	public ImportDatabase(Context context){
		this.context=context;
	}
	private void improt(){
		System.out.println(DB_PATH+"/"+DATABASES);
		File dir=new File(DB_PATH+"/"+DATABASES);
		if(!dir.exists()){
			dir.mkdir();
		}
		file=new File(dir, DB_NAME);
		
		try {
			if(!file.exists()){
				//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
				InputStream is=this.context.getResources().openRawResource(R.raw.education_db);
				FileOutputStream fos=new FileOutputStream(file);
				byte[] buffer=new byte[SIZE];
				int buffercount=0;
				while((buffercount=is.read(buffer))>0){
					fos.write(buffer, 0, buffercount);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("file not exist");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ioexception");
			e.printStackTrace();
		}
	}
	public SQLiteDatabase getReadDB(){
		File dir=new File(DB_PATH+"/"+DATABASES);
		if(!dir.exists()){
			dir.mkdir();
		}
		File file=new File(dir, DB_NAME);
		
		try {
			if(!file.exists()){
				//判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
				InputStream is=this.context.getResources().openRawResource(R.raw.education_db);
				FileOutputStream fos=new FileOutputStream(file);
				byte[] buffer=new byte[SIZE];
				int buffercount=0;
				while((buffercount=is.read(buffer))>0){
					fos.write(buffer, 0, buffercount);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("file not exist");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ioexception");
			e.printStackTrace();
		}
		DatabaseHelper databaseHelper=new DatabaseHelper(context, DB_NAME);
		db=databaseHelper.getReadableDatabase();
		System.out.println("return readable db");
		//db=SQLiteDatabase.openOrCreateDatabase(DB_PATH+"/"+DATABASES+"/"+DB_NAME,null);
		return db;
	}
	public SQLiteDatabase getWriteDB(){
		improt();
		DatabaseHelper databaseHelper=new DatabaseHelper(context, DB_NAME);
		db=databaseHelper.getWritableDatabase();
		System.out.println("return writeable db");
		return db;
	}
	public SQLiteDatabase getDB(){
		improt();
		DatabaseHelper databaseHelper=new DatabaseHelper(context, DB_NAME);
		db=SQLiteDatabase.openOrCreateDatabase(file, null);
		return db;
	}
}