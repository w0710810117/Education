package com.fzw.education.activity;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;
import com.fzw.education.R.menu;
import com.fzw.education.db.ImportDatabase;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DanxuanmoniActivity extends Activity implements OnGestureListener{
	private int id=1;
	private TextView zhangjiequestion=null;
	private RadioGroup btnGrp=null;
	private RadioButton btnA,btnB,btnC,btnD;
	private TextView zhangjiejiexi=null;
	private String questionString=null;
	private ImportDatabase impd=null;
	private SQLiteDatabase sqldb=null;
	private SQLiteDatabase mysqldb=null;
	private Cursor cursor=null,cursor2=null;
	private GestureDetector gDetector=null;
	private ActionBar ab=null;
	//总题数
	public static final int totalNum=25;
	//错题数
	private int wrongNum=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monidanxuan);
		init();
		id=getIntent().getExtras().getInt("MONI_ID");
		impd=new ImportDatabase(this);
		sqldb=impd.getDB();
		mysqldb=impd.getDB();
		try {
			cursor=sqldb.rawQuery("select * from monikaoshi where Sections='"+id+"'", null);
			cursor2=mysqldb.rawQuery("select * from collect", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DanxuanmoniActivity--->数据库读取失败");
		}
		if(cursor!=null){
			System.out.println("cursor-->"+cursor.getCount());
			if(cursor.getCount()!=0){
				cursor.moveToFirst();
				System.out.println("cursor---->"+cursor.getPosition());
				questionString=cursor.getString(cursor.getColumnIndex("question"));
				zhangjiequestion.setText(questionString);
				btnA.setText("A."+cursor.getString(cursor.getColumnIndex("A")));
				btnB.setText("B."+cursor.getString(cursor.getColumnIndex("B")));
				btnC.setText("C."+cursor.getString(cursor.getColumnIndex("C")));
				btnD.setText("D."+cursor.getString(cursor.getColumnIndex("D")));
				
				btnGrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						int flag=1;
						String answer=cursor.getString(cursor.getColumnIndex("answer"));
						System.out.println("answer-->"+answer);
						btnA.setClickable(false);
						btnB.setClickable(false);
						btnC.setClickable(false);
						btnD.setClickable(false);
						switch (checkedId) {
						case R.id.btnA:
							System.out.println("A");
							if(answer.equals("A")){
								//Toast.makeText(DanxuanmoniActivity.this, "回答正确", Toast.LENGTH_SHORT).show();
							}
							else {
								System.out.println("错了");
								++wrongNum;
								ContentValues cv=new ContentValues();
								cv.put("isWrong", 1);
								sqldb.update("monikaoshi", cv, "question='"+questionString+"'", null);
							}
							break;

						case R.id.btnB:
							System.out.println("B");
							if(answer.equals("B")){
								//Toast.makeText(DanxuanmoniActivity.this, "回答正确", Toast.LENGTH_SHORT).show();
							}else {
								++wrongNum;
								ContentValues cv=new ContentValues();
								cv.put("isWrong", 1);
								sqldb.update("monikaoshi", cv, "question='"+questionString+"'", null);
								
							}
							break;
						case R.id.btnC:
							System.out.println("C");
							if(answer.equals("C")){
								//Toast.makeText(DanxuanmoniActivity.this, "回答正确", Toast.LENGTH_SHORT).show();
							}else {
								++wrongNum;
								ContentValues cv=new ContentValues();
								cv.put("isWrong", 1);
								sqldb.update("monikaoshi", cv, "question='"+questionString+"'", null);
								
							}
							break;
						case R.id.btnD:
							System.out.println("D");
							if(answer.equals("D")){
								//Toast.makeText(DanxuanmoniActivity.this, "回答正确", Toast.LENGTH_SHORT).show();
							}else {
								++wrongNum;
								ContentValues cv=new ContentValues();
								cv.put("isWrong", 1);
								sqldb.update("monikaoshi", cv, "question='"+questionString+"'", null);
								
							}
							break;
						default:
							System.out.println("what happend");
							flag=0;
							break;
						}
						if(flag==1)
							Next();
					}
					//调向下题
					
				});
				gDetector=new GestureDetector(this);
				
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
		ab=this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(e1.getX()-e2.getX()>100){
			cursor.moveToNext();
			System.out.println("cursor---->"+cursor.getPosition());
			System.out.println("下一题2");
			//向右划
			//如果没有选，默认为错
			++wrongNum;
			ContentValues cv=new ContentValues();
			cv.put("isWrong", 1);
			sqldb.update("monikaoshi", cv, "question='"+questionString+"'", null);
			if(cursor.isAfterLast()){

				Intent intent=new Intent();
				intent.putExtra("wrongnum", wrongNum);
				intent.putExtra("id", id);
				intent.setClass(DanxuanmoniActivity.this, MoniResult.class);
				startActivity(intent);//已经没有题了
				//Toast.makeText(this, "后面已经没有题了", Toast.LENGTH_SHORT).show();
			}else {
				
				btnGrp.clearCheck();
				questionString=cursor.getString(cursor.getColumnIndex("question"));
				zhangjiequestion.setText(questionString);
				btnA.setText("A."+cursor.getString(cursor.getColumnIndex("A")));
				btnB.setText("B."+cursor.getString(cursor.getColumnIndex("B")));
				btnC.setText("C."+cursor.getString(cursor.getColumnIndex("C")));
				btnD.setText("D."+cursor.getString(cursor.getColumnIndex("D")));
				btnA.setClickable(true);
				btnB.setClickable(true);
				btnC.setClickable(true);
				btnD.setClickable(true);

			}
			
		}
		else if(e1.getX()-e2.getX()<-100){
			//向前滑动
			System.out.println("前一题");
			/*if(cursor.isFirst()){
				Toast.makeText(this, "前面没题了", Toast.LENGTH_SHORT).show();
			}
			else {
				cursor.moveToPrevious();
				System.out.println("cursor 向前移动一格");

				btnGrp.clearCheck();
				questionString=cursor.getString(cursor.getColumnIndex("question"));
				zhangjiequestion.setText(questionString);
				btnA.setText(cursor.getString(cursor.getColumnIndex("A")));
				btnB.setText(cursor.getString(cursor.getColumnIndex("B")));
				btnC.setText(cursor.getString(cursor.getColumnIndex("C")));
				btnD.setText(cursor.getString(cursor.getColumnIndex("D")));
				btnA.setClickable(true);
				btnB.setClickable(true);
				btnC.setClickable(true);
				btnD.setClickable(true);
				
				
			}
*/		}
		return true;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		super.dispatchTouchEvent(ev);
		return gDetector.onTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.gDetector.onTouchEvent(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.zhangjie_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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

		case R.id.collect:
			int flag=1;
			if (cursor2!=null&&cursor2.getCount()!=0) {
				for (cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2
						.moveToNext()) {
					if (cursor2.getString(cursor2.getColumnIndex("question"))
							.equals(questionString))
						;
					{
						flag = 0;
					}
				}
			}
			if (cursor2!=null&&cursor2.getCount()!=0) {
				Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
				ContentValues cv = new ContentValues();
				cv.put("question",
						cursor.getString(cursor.getColumnIndex("question")));
				cv.put("A", cursor.getString(cursor.getColumnIndex("A")));
				cv.put("B", cursor.getString(cursor.getColumnIndex("B")));
				cv.put("C", cursor.getString(cursor.getColumnIndex("C")));
				cv.put("D", cursor.getString(cursor.getColumnIndex("D")));
				cv.put("E", cursor.getString(cursor.getColumnIndex("E")));
				cv.put("answer",
						cursor.getString(cursor.getColumnIndex("answer")));
				cv.put("jiexi",
						cursor.getString(cursor.getColumnIndex("jiexi")));
				cv.put("type", 0);
				mysqldb.insert("collect", null, cv);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void Next(){
		for(int i=0;i<1500;++i){
			
		}
		cursor.moveToNext();
		System.out.println("cursor---->"+cursor.getPosition());
		if(!cursor.isAfterLast()){
			System.out.println("自动下一题");
			btnGrp.clearCheck();
			try {
				questionString=cursor.getString(cursor.getColumnIndex("question"));
				zhangjiequestion.setText(questionString);
				btnA.setText("A."+cursor.getString(cursor.getColumnIndex("A")));
				btnB.setText("B."+cursor.getString(cursor.getColumnIndex("B")));
				btnC.setText("C."+cursor.getString(cursor.getColumnIndex("C")));
				btnD.setText("D."+cursor.getString(cursor.getColumnIndex("D")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("读取失败");
			}
			btnA.setClickable(true);
			btnB.setClickable(true);
			btnC.setClickable(true);
			btnD.setClickable(true);
		}
		else {
			System.out.println("没题了");
			Intent intent=new Intent();
			intent.putExtra("wrongnum", wrongNum);
			intent.putExtra("id", id);
			intent.setClass(DanxuanmoniActivity.this, MoniResult.class);
			startActivity(intent);
		}
			
		
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
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
						ContentValues cv = new ContentValues();
						cv.put("isWrong", 0);
						for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
							sqldb.update("monikaoshi", cv, "isWrong=1", null);
						}
						finish();
					}
				});
					abBuilder.show();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
