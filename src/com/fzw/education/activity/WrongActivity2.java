package com.fzw.education.activity;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;
import com.fzw.education.db.ImportDatabase;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

public class WrongActivity2 extends Activity{
	//多选
	private int id=2;
	private TextView zhangjiequestion=null;
	private CheckBox checkA,checkB,checkC,checkD,checkE;
	private TextView zhangjiejiexi=null;
	private Button tijiaoButton=null;
	private ImportDatabase impDB=null;
	private SQLiteDatabase sqldb=null;
	private ActionBar ab=null;
	private Cursor cursor=null;
	private String questionString=null;
	private String ansString=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moniduoxuan);
		init();
		questionString=getIntent().getExtras().getString("QUESTION");
		impDB=new ImportDatabase(this);
		sqldb=impDB.getDB();
		try {
			cursor=sqldb.rawQuery("select * from monikaoshi where question='"+questionString+"'", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("WrongActivity--->数据库读取失败");
		}
		if(cursor!=null&&cursor.getCount()!=0){
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
					if(cursor.getString(cursor.getColumnIndex("question")).equals(questionString)){
						break;
					}
				}
				zhangjiequestion.setText(questionString);
				checkA.setText(cursor.getString(cursor.getColumnIndex("A")));
				checkB.setText(cursor.getString(cursor.getColumnIndex("B")));
				checkC.setText(cursor.getString(cursor.getColumnIndex("C")));
				checkD.setText(cursor.getString(cursor.getColumnIndex("D")));
				checkE.setText(cursor.getString(cursor.getColumnIndex("E")));
				ansString=cursor.getString(cursor.getColumnIndex("answer"));
				if(ansString.contains("A")){
					checkA.toggle();
				}
				if(ansString.contains("B"));{
					checkB.toggle();
				}
				if(ansString.contains("C")){
					checkC.toggle();
				}
				if(ansString.contains("D")){
					checkD.toggle();
				}
				if(ansString.contains("E")){
					checkE.toggle();
				}
				checkA.setClickable(false);
				checkB.setClickable(false);
				checkC.setClickable(false);
				checkD.setClickable(false);
				checkE.setClickable(false);
				zhangjiejiexi.setText("解析:\n"+cursor.getString(cursor.getColumnIndex("answer")));
		
		}
		else {
			checkA.setVisibility(View.INVISIBLE);
			checkB.setVisibility(View.INVISIBLE);
			checkC.setVisibility(View.INVISIBLE);
			checkD.setVisibility(View.INVISIBLE);
			checkE.setVisibility(View.INVISIBLE);
			tijiaoButton.setVisibility(View.INVISIBLE);
			ab=getActionBar();
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setDisplayUseLogoEnabled(false);
		}
		
	}
	private void init(){
		zhangjiequestion=(TextView)findViewById(R.id.zhangjie_question);
		checkA=(CheckBox)findViewById(R.id.checkA);
		checkB=(CheckBox)findViewById(R.id.checkB);
		checkC=(CheckBox)findViewById(R.id.checkC);
		checkD=(CheckBox)findViewById(R.id.checkD);
		checkE=(CheckBox)findViewById(R.id.checkE);
		tijiaoButton=(Button)findViewById(R.id.tijiao);
		zhangjiejiexi=(TextView)findViewById(R.id.zhangjie_jiexi);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			ScrollView scrollView=(ScrollView)getLayoutInflater()
			.inflate(R.layout.dialog_zhangjielianxi, null);
			AlertDialog.Builder abBuilder=new AlertDialog.Builder(this);
			abBuilder.setTitle("温馨提示");
			abBuilder.setView(scrollView);
			abBuilder.setNegativeButton("取消", new OnClickListener() {
	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
					
				}
		});
			abBuilder.setPositiveButton("确认", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
			abBuilder.show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
