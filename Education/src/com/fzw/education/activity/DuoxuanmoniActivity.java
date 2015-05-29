package com.fzw.education.activity;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;
import com.fzw.education.R.menu;
import com.fzw.education.db.ImportDatabase;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
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

public class DuoxuanmoniActivity extends Activity implements OnGestureListener, OnClickListener{
	private int id=2;
	private TextView zhangjiequestion=null;
	private CheckBox checkA,checkB,checkC,checkD,checkE;
	private Button tijiaoButton=null;
	private ImportDatabase impDB=null;
	private SQLiteDatabase sqldb=null;
	private ActionBar ab;
	private Cursor cursor=null,cursor2=null;
	private String questionString=null;
	private GestureDetector gdDetector=null;
	private int wrongNum=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moniduoxuan);
		id=getIntent().getExtras().getInt("MONI_ID");
		init();
		impDB=new ImportDatabase(this);
		sqldb=impDB.getDB();
		try {
			cursor=sqldb.rawQuery("select * from monikaoshi where Sections='"+id+"'", null);
			cursor2=sqldb.rawQuery("select * from collect", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DuoxuanmoniActivity--->读取数据库失败");
		}
		if(cursor!=null){
			System.out.println(cursor.getCount());
			if(cursor.getCount()!=0){
				cursor.moveToFirst();
				questionString=cursor.getString(cursor.getColumnIndex("question"));
				zhangjiequestion.setText(questionString);
				checkA.setText("A."+cursor.getString(cursor.getColumnIndex("A")));
				checkB.setText("B."+cursor.getString(cursor.getColumnIndex("B")));
				checkC.setText("C."+cursor.getString(cursor.getColumnIndex("C")));
				checkD.setText("D."+cursor.getString(cursor.getColumnIndex("D")));
				checkE.setText("E."+cursor.getString(cursor.getColumnIndex("E")));
				
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
			//向右划
			cursor.moveToNext();
			System.out.println("下一题");
			//如果没有选，默认为错
			++wrongNum;
			ContentValues cv=new ContentValues();
			cv.put("isWrong", 1);
			sqldb.update("monikaoshi", cv, "question='"+questionString+"'", null);
			if(cursor.isAfterLast()){
				//Toast.makeText(this, "后面已经每题了", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				intent.putExtra("id", id);
				intent.putExtra("wrongnum", wrongNum);
				intent.setClass(DuoxuanmoniActivity.this, MoniResult.class);
				startActivity(intent);
			}
			else {
				questionString=cursor.getString(cursor.getColumnIndex("question"));
				zhangjiequestion.setText(questionString);
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
				checkA.setClickable(true);
				checkB.setClickable(true);
				checkC.setClickable(true);
				checkD.setClickable(true);
				checkE.setClickable(true);
				checkA.setText("A."+cursor.getString(cursor.getColumnIndex("A")));
				checkB.setText("B."+cursor.getString(cursor.getColumnIndex("B")));
				checkC.setText("C."+cursor.getString(cursor.getColumnIndex("C")));
				checkD.setText("D."+cursor.getString(cursor.getColumnIndex("D")));
				checkE.setText("E."+cursor.getString(cursor.getColumnIndex("E")));
					
				
			}
		}
		else if(e1.getX()-e2.getX()<-100){
			
			//向前滑动
/*			System.out.println("前一题");
			if(cursor.isFirst()){
				Toast.makeText(this, "前面没题了", Toast.LENGTH_SHORT).show();
			}
			else {
				cursor.moveToPrevious();
				System.out.println("cursor 向前移动一格");

				questionString=cursor.getString(cursor.getColumnIndex("question"));
				zhangjiequestion.setText(questionString);
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
				checkA.setClickable(true);
				checkB.setClickable(true);
				checkC.setClickable(true);
				checkD.setClickable(true);
				checkE.setClickable(true);
				System.out.println(cursor.getString(cursor.getColumnIndex("A")));
				checkA.setText(cursor.getString(cursor.getColumnIndex("A")));
				checkB.setText(cursor.getString(cursor.getColumnIndex("B")));
				checkC.setText(cursor.getString(cursor.getColumnIndex("C")));
				checkD.setText(cursor.getString(cursor.getColumnIndex("D")));
				checkE.setText(cursor.getString(cursor.getColumnIndex("E")));
			
				
			}*/
		}
		return true;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		super.dispatchTouchEvent(ev);
		return gdDetector.onTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return this.gdDetector.onTouchEvent(event);
	}
	private void Action(){
		System.out.println("提交");
		String ansString=cursor.getString(cursor.getColumnIndex("answer"));
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
			Toast.makeText(DuoxuanmoniActivity.this, "请选择答案", Toast.LENGTH_SHORT).show();
			return;
		}
		else if(ansString.equals(checkString)){
			//答对了
		}else {
			//答错了
			ContentValues cv=new ContentValues();
			cv.put("isWrong", 1);
			sqldb.update("monikaoshi", cv, "question='"+questionString+"'", null);
			
		}
		cursor.moveToNext();
		if(cursor.isAfterLast()){
			for(int i=0;i<1500;++i){
				//延迟1.5s
			}
			Intent intent=new Intent();
			intent.putExtra("wrongnum", wrongNum);
			intent.putExtra("id", id);
			intent.setClass(DuoxuanmoniActivity.this, MoniResult.class);
			startActivity(intent);
			
		}
		else {
			for(int i=0;i<1500;++i){
				//延迟1.5s
			}
			
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
			checkA.setText("A."+cursor.getString(cursor.getColumnIndex("A")));
			checkB.setText("B."+cursor.getString(cursor.getColumnIndex("B")));
			checkC.setText("C."+cursor.getString(cursor.getColumnIndex("C")));
			checkD.setText("D."+cursor.getString(cursor.getColumnIndex("D")));
			checkE.setText("E."+cursor.getString(cursor.getColumnIndex("E")));

			checkA.setClickable(true);
			checkB.setClickable(true);
			checkC.setClickable(true);
			checkD.setClickable(true);
			checkE.setClickable(true);
		}
	}
	@Override
	public void onClick(View v) {

		if(v.getId()==R.id.tijiao){
			Action();
		}
		
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
			abBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
					
				}
		});
			abBuilder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			
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
				cv.put("type", 1);
				sqldb.insert("collect", null, cv);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
	

}
