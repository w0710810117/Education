package com.fzw.education.activity;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;
import com.fzw.education.db.ImportDatabase;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class WrongActivity extends Activity{
	//单选
	private int id=1;
	private TextView zhangjiequestion=null;
	private RadioGroup btnGrp=null;
	private RadioButton btnA,btnB,btnC,btnD;
	private TextView zhangjiejiexi=null;
	private String questionString=null;
	private ImportDatabase importDatabase=null;
	private SQLiteDatabase sqldb=null;
	private Cursor cursor=null;
	private String ansString=new String("");
	private ActionBar ab=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monidanxuan);
		init();
		questionString=getIntent().getExtras().getString("QUESTION");
		importDatabase=new ImportDatabase(this);
		sqldb=importDatabase.getDB();
		try {
			cursor=sqldb.rawQuery("select * from monikaoshi where Sections='"+id+"' and question='"+questionString+"'", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("WrongActivity---->数据库读取失败");
		}
		if(cursor!=null){
			if(cursor.getCount()!=0){
				cursor.moveToFirst();
				zhangjiequestion.setText(questionString);
				btnA.setText("A"+cursor.getString(cursor.getColumnIndex("A")));
				btnB.setText("B"+cursor.getString(cursor.getColumnIndex("B")));
				btnC.setText("C"+cursor.getString(cursor.getColumnIndex("C")));
				btnD.setText("D"+cursor.getString(cursor.getColumnIndex("D")));
				ansString=cursor.getString(cursor.getColumnIndex("answer"));
				if(ansString.contains("A")){
					btnA.setChecked(true);
				}
				else if(ansString.contains("B")){
					btnB.setChecked(true);
				}
				else if(ansString.contains("C")){
					btnC.setChecked(true);
				}
				else if(ansString.contains("D")){
					btnD.setChecked(true);
				}
				btnA.setClickable(false);
				btnB.setClickable(false);
				btnC.setClickable(false);
				btnD.setClickable(false);
				zhangjiejiexi.setText("解析：\n"+cursor.getString(cursor.getColumnIndex("jiexi")));
			}
		}
		
	}
	private void init(){
		zhangjiequestion=(TextView)findViewById(R.id.zhangjie_question);
		btnGrp=(RadioGroup)findViewById(R.id.BtnGrp);
		btnA=(RadioButton)findViewById(R.id.btnA);
		btnB=(RadioButton)findViewById(R.id.btnB);
		btnC=(RadioButton)findViewById(R.id.btnC);
		btnD=(RadioButton)findViewById(R.id.btnD);
		zhangjiejiexi=(TextView)findViewById(R.id.zhangjie_jiexi);
		ab=getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			/*ScrollView scrollView=(ScrollView)getLayoutInflater()
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
			abBuilder.show();*/
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
