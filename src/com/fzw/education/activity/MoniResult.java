package com.fzw.education.activity;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;
import com.fzw.education.db.ImportDatabase;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MoniResult extends Activity{
	private int moni_id=1;
	private TextView resultView;
	private ListView resultlist;
	private ArrayAdapter<String> wrongAdapter=null;
	private ImportDatabase importDatabase=null;
	private SQLiteDatabase sqldb=null;
	private Cursor cursor=null;
	private List<String> list=null;
	private ActionBar ab=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moniresult);
		ab=this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
		resultView=(TextView)findViewById(R.id.resulttext);
		int wrongnum=getIntent().getExtras().getInt("wrongnum");
		System.out.println(""+wrongnum);
		moni_id=getIntent().getExtras().getInt("id");
//		String.format("%10.2ff%%", ((float)(20-wrongnum)/20)*100);
		resultView.setText(String.format("%10.2f%%", ((float)(20-wrongnum)/20)*100));
		resultlist=(ListView)findViewById(R.id.resultlist);
		getData();
		wrongAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		
		resultlist.setAdapter(wrongAdapter);
		resultlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(moni_id==1){
					
					Intent intent=new Intent();
					intent.putExtra("QUESTION", list.get(position).toString());
					System.out.println("??"+list.get(position).toString());
					intent.setClass(MoniResult.this, WrongActivity.class);
					startActivity(intent);
				}
				else if(moni_id==2){
					Intent intent=new Intent();
					intent.putExtra("QUESTION", list.get(position).toString());
					System.out.println(list.get(position).toString());
					intent.setClass(MoniResult.this, WrongActivity2.class);
					startActivity(intent);
				}
				

			}
		});
	}
	private void getData(){
		list=new ArrayList<String>();
		importDatabase=new ImportDatabase(this);
		sqldb=importDatabase.getDB();
		try {
			cursor=sqldb.rawQuery("select * from monikaoshi where isWrong=1", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("MoniResult--->数据库读取失败");
		}
		if(cursor!=null){
			if(cursor.getCount()!=0){
				cursor.moveToFirst();
				for(;!cursor.isAfterLast();cursor.moveToNext()){
					list.add(cursor.getString(cursor.getColumnIndex("question")));
					System.out.println(cursor.getString(cursor.getColumnIndex("question")));
				}
			}
		}
		return;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			ContentValues cv=new ContentValues();
			cv.put("isWrong", 0);
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
				
				try {
					sqldb.update("monikaoshi", cv, "isWrong=1", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("MoniResult--->更新失败");
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:

			ContentValues cv=new ContentValues();
			cv.put("isWrong", 0);
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
				
				try {
					sqldb.update("monikaoshi", cv, "isWrong=1", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("MoniResult--->更新失败");
				}
			}
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		ContentValues cv=new ContentValues();
		cv.put("isWrong", 0);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			
			try {
				sqldb.update("monikaoshi", cv, "isWrong=1", null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("MoniResult--->更新失败");
			}
		}
	
	}
	
	
}
