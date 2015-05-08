package com.fzw.education.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;
import com.fzw.education.R.menu;
import com.fzw.education.db.ImportDatabase;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MyZone_Multiple extends Activity implements OnGestureListener, OnClickListener{
	private int id=2;
	private TextView zhangjiequestion=null;
	private CheckBox checkA,checkB,checkC,checkD,checkE;
	private Button tijiaoButton=null;
	private TextView zhangjiejiexi=null;
	private ImportDatabase impDB=null;
	private SQLiteDatabase sqldb=null;
	private ActionBar ab;
	private Cursor cursor=null;
	private String questionString=null;
	private GestureDetector gdDetector=null;
	private List<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
	private int Length=0;
	private int Pointer=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moniduoxuan);
		init();
		id=getIntent().getExtras().getInt("MYZONE_ID");
		impDB=new ImportDatabase(this);
		sqldb=impDB.getDB();
		if(id==2){
			//收藏多选
			cursor=sqldb.rawQuery("select * from collect where type=1", null);
		}
		else if(id==4){
			//错题多选
			cursor=sqldb.rawQuery("select * from wrong where type=1", null);
		}
		if(cursor!=null&&cursor.getCount()!=0){
			Length=cursor.getCount();
			cursor.moveToFirst();
			for(int i=0;i<cursor.getCount();++i){
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("question", cursor.getString(cursor.getColumnIndex("question")));
				map.put("A", cursor.getString(cursor.getColumnIndex("A")));
				map.put("B", cursor.getString(cursor.getColumnIndex("B")));
				map.put("C", cursor.getString(cursor.getColumnIndex("C")));
				map.put("C", cursor.getString(cursor.getColumnIndex("D")));
				map.put("E", cursor.getString(cursor.getColumnIndex("E")));
				map.put("answer", cursor.getString(cursor.getColumnIndex("answer")));
				map.put("jiexi", cursor.getString(cursor.getColumnIndex("jiexi")));
				
				list.add(map);
				cursor.moveToNext();
			}
			
			//questionString=list.get(Pointer).getString(list.get(Pointer).getColumnIndex("question"));
			questionString=list.get(Pointer).get("question");
			zhangjiequestion.setText(questionString);
			checkA.setText(list.get(Pointer).get("A"));
			checkB.setText(list.get(Pointer).get("A"));
			checkC.setText(list.get(Pointer).get("A"));
			checkD.setText(list.get(Pointer).get("A"));
			checkE.setText(list.get(Pointer).get("A"));
		}
		else {
			checkA.setVisibility(View.INVISIBLE);
			checkB.setVisibility(View.INVISIBLE);
			checkC.setVisibility(View.INVISIBLE);
			checkD.setVisibility(View.INVISIBLE);
			checkE.setVisibility(View.INVISIBLE);
			tijiaoButton.setVisibility(View.INVISIBLE);
			if(id==2){
				Toast.makeText(this, "还木有收藏", Toast.LENGTH_LONG).show();
			}
			else if(id==4){
				Toast.makeText(this, "还木有错题", Toast.LENGTH_LONG).show();
			}
		}
		gdDetector=new GestureDetector(this);
		tijiaoButton.setOnClickListener(this);
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
		if(e1.getX()-e2.getX()>100){/*
			//向右划
			
			System.out.println("下一题");
			if(cursor.isLast()){
				//Toast.makeText(this, "后面已经每题了", Toast.LENGTH_SHORT).show();
		
			}
			else {
				cursor.moveToNext();
				
				if (remain[cursor.getPosition()]==1) {
					questionString = cursor.getString(cursor
							.getColumnIndex("question"));
					zhangjiequestion.setText(questionString);
					//如果选中，则去掉
					if (checkA.isChecked()) {
						checkA.toggle();
					}
					if (checkB.isChecked()) {
						checkB.toggle();
					}
					if (checkC.isChecked()) {
						checkC.toggle();
					}
					if (checkD.isChecked()) {
						checkD.toggle();
					}
					if (checkE.isChecked()) {
						checkE.toggle();
					}
					checkA.setEnabled(true);
					checkB.setEnabled(true);
					checkC.setEnabled(true);
					checkD.setEnabled(true);
					checkE.setEnabled(true);
					checkA.setClickable(true);
					checkB.setClickable(true);
					checkC.setClickable(true);
					checkD.setClickable(true);
					checkE.setClickable(true);
					checkA.setText(cursor.getString(cursor.getColumnIndex("A")));
					checkB.setText(cursor.getString(cursor.getColumnIndex("B")));
					checkC.setText(cursor.getString(cursor.getColumnIndex("C")));
					checkD.setText(cursor.getString(cursor.getColumnIndex("D")));
					checkE.setText(cursor.getString(cursor.getColumnIndex("E")));
					zhangjiejiexi.setText("");
				}
					
				
			}
		*/	if(Pointer+1>=Length){
			Toast.makeText(this, "后面已经每题了", Toast.LENGTH_SHORT).show();
			}
		else {
			++Pointer;
			questionString = list.get(Pointer).get("question");
			zhangjiequestion.setText(questionString);
			//如果选中，则去掉
			if (checkA.isChecked()) {
				checkA.toggle();
			}
			if (checkB.isChecked()) {
				checkB.toggle();
			}
			if (checkC.isChecked()) {
				checkC.toggle();
			}
			if (checkD.isChecked()) {
				checkD.toggle();
			}
			if (checkE.isChecked()) {
				checkE.toggle();
			}
			checkA.setEnabled(true);
			checkB.setEnabled(true);
			checkC.setEnabled(true);
			checkD.setEnabled(true);
			checkE.setEnabled(true);
			checkA.setClickable(true);
			checkB.setClickable(true);
			checkC.setClickable(true);
			checkD.setClickable(true);
			checkE.setClickable(true);
			checkA.setText(list.get(Pointer).get("A"));
			checkB.setText(list.get(Pointer).get("B"));
			checkC.setText(list.get(Pointer).get("C"));
			checkD.setText(list.get(Pointer).get("D"));
			checkE.setText(list.get(Pointer).get("E"));
			zhangjiejiexi.setText("");
		}
			}
		else if(e1.getX()-e2.getX()<-100){/*
			
			//向前滑动
		System.out.println("前一题");
			if(cursor.isFirst()){
				Toast.makeText(this, "前面没题了", Toast.LENGTH_SHORT).show();
			}
			else {
				cursor.moveToPrevious();
				System.out.println("cursor 向前移动一格");

				if (remain[cursor.getPosition()]==1) {
					//没被删除
					questionString = cursor.getString(cursor
							.getColumnIndex("question"));
					zhangjiequestion.setText(questionString);
					//如果选中，则去掉
					if (checkA.isChecked()) {
						checkA.toggle();
					}
					if (checkB.isChecked()) {
						checkB.toggle();
					}
					if (checkC.isChecked()) {
						checkC.toggle();
					}
					if (checkD.isChecked()) {
						checkD.toggle();
					}
					if (checkE.isChecked()) {
						checkE.toggle();
					}
					checkA.setEnabled(true);
					checkB.setEnabled(true);
					checkC.setEnabled(true);
					checkD.setEnabled(true);
					checkE.setEnabled(true);
					checkA.setClickable(true);
					checkB.setClickable(true);
					checkC.setClickable(true);
					checkD.setClickable(true);
					checkE.setClickable(true);
					System.out.println(cursor.getString(cursor
							.getColumnIndex("A")));
					checkA.setText(cursor.getString(cursor.getColumnIndex("A")));
					checkB.setText(cursor.getString(cursor.getColumnIndex("B")));
					checkC.setText(cursor.getString(cursor.getColumnIndex("C")));
					checkD.setText(cursor.getString(cursor.getColumnIndex("D")));
					checkE.setText(cursor.getString(cursor.getColumnIndex("E")));
					zhangjiejiexi.setText("");
				}
				
			}
		*/	if(Pointer-1<0){
			Toast.makeText(this, "前面没题了", Toast.LENGTH_SHORT).show();
		}
		else {
			--Pointer;
			questionString = list.get(Pointer).get("question");
			zhangjiequestion.setText(questionString);
			//如果选中，则去掉
			if (checkA.isChecked()) {
				checkA.toggle();
			}
			if (checkB.isChecked()) {
				checkB.toggle();
			}
			if (checkC.isChecked()) {
				checkC.toggle();
			}
			if (checkD.isChecked()) {
				checkD.toggle();
			}
			if (checkE.isChecked()) {
				checkE.toggle();
			}
			checkA.setEnabled(true);
			checkB.setEnabled(true);
			checkC.setEnabled(true);
			checkD.setEnabled(true);
			checkE.setEnabled(true);
			checkA.setClickable(true);
			checkB.setClickable(true);
			checkC.setClickable(true);
			checkD.setClickable(true);
			checkE.setClickable(true);
			checkA.setText(list.get(Pointer).get("A"));
			checkB.setText(list.get(Pointer).get("B"));
			checkC.setText(list.get(Pointer).get("C"));
			checkD.setText(list.get(Pointer).get("D"));
			checkE.setText(list.get(Pointer).get("E"));
			zhangjiejiexi.setText("");
		}
			}
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.gdDetector.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		super.dispatchTouchEvent(ev);
		return gdDetector.onTouchEvent(ev);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.tijiao){
			Action();
		}
	}
	private void Action(){
		System.out.println("提交");
		String ansString=list.get(Pointer).get("question");
		String checkString=new String("");
		if(checkA.isChecked())
			checkString=checkString+"A";
		if(checkB.isChecked())
			checkString=checkString+"B";
		if(checkC.isChecked())
			checkString=checkString+"C";
		if(checkD.isChecked())
			checkString=checkString+"D";
		if(checkE.isChecked())
			checkString=checkString+"E";
		if(checkString.equals("")){
			Toast.makeText(MyZone_Multiple.this, "请选择答案", Toast.LENGTH_SHORT).show();
		}
		else if(ansString.equals(checkString)){
			//答对了
			Toast.makeText(MyZone_Multiple.this, "答对啦", Toast.LENGTH_SHORT).show();
			zhangjiejiexi.setText(list.get(Pointer).get("jiexi"));
		}else {
			//答错了
			Toast.makeText(MyZone_Multiple.this, "答错啦", Toast.LENGTH_SHORT).show();
			zhangjiejiexi.setText(list.get(Pointer).get("jiexi"));
			
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			ScrollView scrollView=(ScrollView)getLayoutInflater()
					.inflate(R.layout.dialog_zhangjielianxi, null);
			AlertDialog.Builder aBuilder=new AlertDialog.Builder(this);
			aBuilder.setTitle("温馨提示");
			aBuilder.setView(scrollView);
			aBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
				
			});
			aBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.my_zone, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			ScrollView scrollView=(ScrollView)getLayoutInflater()
			.inflate(R.layout.dialog_zhangjielianxi, null);
			AlertDialog.Builder aBuilder=new AlertDialog.Builder(this);
			aBuilder.setTitle("温馨提示");
			aBuilder.setView(scrollView);
			aBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
		
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
				
			});
			aBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			aBuilder.show();
			break;

		case R.id.delete:
			
			if (Length!=0) {
				if (id == 2) {
					if (cursor != null && cursor.getCount() != 0) {
						sqldb.execSQL("delete from collect where question='"
								+ questionString + "'");
						//删除链表某项
						list.remove(Pointer);
						--Length;
						Next();
					} else {
						Toast.makeText(this, "还木有数据", Toast.LENGTH_SHORT)
								.show();
					}
				} else if (id == 4) {
					if (cursor != null && cursor.getCount() != 0) {
						sqldb.execSQL("delete from wrong where question='"
								+ questionString + "'");

						list.remove(Pointer);
						--Length;
						Next();
					} else {
						Toast.makeText(this, "还木有数据", Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void Next(){/*
		int flag=0;
		for(int i=0;i<cursor.getCount();++i){
			if(remain[i]==1){
				flag=1;
			}
			if(flag==0){
				//隐藏控件
				System.out.println("隐藏控件");
				zhangjiequestion.setVisibility(View.INVISIBLE);
				checkA.setVisibility(View.INVISIBLE);
				checkB.setVisibility(View.INVISIBLE);
				checkC.setVisibility(View.INVISIBLE);
				checkD.setVisibility(View.INVISIBLE);
				checkE.setVisibility(View.INVISIBLE);
				tijiaoButton.setVisibility(View.INVISIBLE);
				return;
			}
				
		}
		if(cursor.isLast()){
			cursor.moveToFirst();
		}
		else {
			cursor.moveToNext();
			if(remain[cursor.getPosition()]==1){
				//如果选中，则去掉
				if(checkA.isChecked()){
					checkA.toggle();
				}
				if(checkB.isChecked()){
					checkB.toggle();
				}
				if(checkC.isChecked()){
					checkC.toggle();
				}
				if(checkD.isChecked()){
					checkD.toggle();
				}
				if(checkE.isChecked()){
					checkE.toggle();
				}
				checkA.setEnabled(true);
				checkB.setEnabled(true);
				checkC.setEnabled(true);
				checkD.setEnabled(true);
				checkE.setEnabled(true);
				
				questionString=cursor.getString(cursor.getColumnIndex("question"));
				zhangjiequestion.setText(questionString);
				checkA.setText("A"+cursor.getString(cursor.getColumnIndex("A")));
				checkB.setText("B"+cursor.getString(cursor.getColumnIndex("B")));
				checkC.setText("C"+cursor.getString(cursor.getColumnIndex("C")));
				checkD.setText("D"+cursor.getString(cursor.getColumnIndex("D")));
				checkE.setText("E"+cursor.getString(cursor.getColumnIndex("E")));

				checkA.setClickable(true);
				checkB.setClickable(true);
				checkC.setClickable(true);
				checkD.setClickable(true);
				checkE.setClickable(true);
			}
			else {
				
				Next();
			}
		}
	*/	if(Length==0){
		//隐藏控件
		System.out.println("隐藏控件");
		zhangjiequestion.setVisibility(View.INVISIBLE);
		checkA.setVisibility(View.INVISIBLE);
		checkB.setVisibility(View.INVISIBLE);
		checkC.setVisibility(View.INVISIBLE);
		checkD.setVisibility(View.INVISIBLE);
		checkE.setVisibility(View.INVISIBLE);
		tijiaoButton.setVisibility(View.INVISIBLE);
		zhangjiejiexi.setVisibility(View.INVISIBLE);
	}
	else {

		//如果选中，则去掉
	
		if(Pointer==Length){
			--Pointer;
		}
		if(checkA.isChecked()){
			checkA.toggle();
		}
		if(checkB.isChecked()){
			checkB.toggle();
		}
		if(checkC.isChecked()){
			checkC.toggle();
		}
		if(checkD.isChecked()){
			checkD.toggle();
		}
		if(checkE.isChecked()){
			checkE.toggle();
		}
		checkA.setEnabled(true);
		checkB.setEnabled(true);
		checkC.setEnabled(true);
		checkD.setEnabled(true);
		checkE.setEnabled(true);
		
		questionString=list.get(Pointer).get("question");
		zhangjiequestion.setText(questionString);
		checkA.setText(list.get(Pointer).get("A"));
		checkB.setText(list.get(Pointer).get("B"));
		checkC.setText(list.get(Pointer).get("C"));
		checkD.setText(list.get(Pointer).get("D"));
		checkE.setText(list.get(Pointer).get("E"));

		checkA.setClickable(true);
		checkB.setClickable(true);
		checkC.setClickable(true);
		checkD.setClickable(true);
		checkE.setClickable(true);
		zhangjiejiexi.setText("");
	}
		}
	
}
