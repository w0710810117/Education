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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class WangnianzhentiActivity extends Activity implements
		OnGestureListener, android.view.View.OnClickListener {
	private int id = 1;
	private TextView zhangjiequestion = null;
	private RadioGroup btnGrp = null;
	private RadioButton btnA, btnB, btnC, btnD;
	private CheckBox checkA, checkB, checkC, checkD, checkE;
	private Button tijiaoButton = null;
	private TextView zhangjiejiexi = null;
	private ImageView img_ROW;

	private ImportDatabase imdb = null;
	private SQLiteDatabase sqldb = null;
	private Cursor cursor = null, cursor2 = null, cursor3 = null;
	private String questionString = null;
	private GestureDetector gdDetector = null;
	private ActionBar actionBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhangjielianxi);
		init();
		Bundle bundle = getIntent().getExtras();
		id = bundle.getInt("YEAR_ID");
		imdb = new ImportDatabase(this);
		sqldb = imdb.getDB();
		actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		try {
			cursor = sqldb.rawQuery(
					"select * from wangnianzhenti where Sections='" + id + "'",
					null);
			cursor2 = sqldb.rawQuery("select * from wrong", null);
			cursor3 = sqldb.rawQuery("select * from collect", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("WannianzhentiActivity--->读取数据库失败");
		}
		if (cursor != null && cursor.getCount() != 0) {
			System.out.println("count-->" + cursor.getCount());

			cursor.moveToFirst();
			// 把多选的按钮隐藏
			System.out.println("隐藏控件");
			checkA.setVisibility(View.INVISIBLE);
			checkB.setVisibility(View.INVISIBLE);
			checkC.setVisibility(View.INVISIBLE);
			checkD.setVisibility(View.INVISIBLE);
			checkE.setVisibility(View.INVISIBLE);
			tijiaoButton.setVisibility(View.INVISIBLE);
			questionString = cursor
					.getString(cursor.getColumnIndex("question"));
			zhangjiequestion.setText(questionString);
			btnA.setText(cursor.getString(cursor.getColumnIndex("A")));
			btnB.setText(cursor.getString(cursor.getColumnIndex("B")));
			btnC.setText(cursor.getString(cursor.getColumnIndex("C")));
			btnD.setText(cursor.getString(cursor.getColumnIndex("D")));
			btnA.setTextColor(Color.BLACK);
			btnB.setTextColor(Color.BLACK);
			btnC.setTextColor(Color.BLACK);
			btnD.setTextColor(Color.BLACK);
			btnGrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					String answer = cursor.getString(cursor
							.getColumnIndex("answer"));
					btnA.setClickable(false);
					btnB.setClickable(false);
					btnC.setClickable(false);
					btnD.setClickable(false);
					img_ROW.setVisibility(View.INVISIBLE);
					switch (checkedId) {
					case R.id.btnA:
						if (answer.equals("A")) {
							// Toast.makeText(WangnianzhentiActivity.this,
							// "答对啦", Toast.LENGTH_SHORT).show();
							zhangjiejiexi.setText(cursor.getString(cursor
									.getColumnIndex("jiexi")));
							img_ROW.setVisibility(View.VISIBLE);
							img_ROW.setImageResource(R.drawable.dui);
						} else {
							// Toast.makeText(WangnianzhentiActivity.this,
							// "答错啦", Toast.LENGTH_SHORT).show();
							int flag = 1;
							if (cursor2 != null && cursor2.getCount() != 0) {
								for (cursor2.moveToFirst(); !cursor2
										.isAfterLast(); cursor2.moveToNext()) {
									if (cursor2.getString(
											cursor2.getColumnIndex("question"))
											.equals(questionString))
										;
									{
										flag = 0;
									}
								}
							}
							if (1 == flag)
								InsertWrongtable();
							btnA.setTextColor(Color.RED);
							img_ROW.setVisibility(View.VISIBLE);
							img_ROW.setImageResource(R.drawable.cuo);
							zhangjiejiexi.setText(cursor.getString(cursor
									.getColumnIndex("jiexi")));
						}
						break;

					case R.id.btnB:
						if (answer.equals("B")) {
							// Toast.makeText(WangnianzhentiActivity.this,
							// "答对啦", Toast.LENGTH_SHORT).show();
							zhangjiejiexi.setText(cursor.getString(cursor
									.getColumnIndex("jiexi")));
							img_ROW.setVisibility(View.VISIBLE);
							img_ROW.setImageResource(R.drawable.dui);
						} else {
							int flag = 1;
							if (cursor2 != null && cursor2.getCount() != 0) {
								for (cursor2.moveToFirst(); !cursor2
										.isAfterLast(); cursor2.moveToNext()) {
									if (cursor2.getString(
											cursor2.getColumnIndex("question"))
											.equals(questionString))
										;
									{
										flag = 0;
									}
								}
							}
							if (1 == flag)
								InsertWrongtable();
							// Toast.makeText(WangnianzhentiActivity.this,
							// "答错啦B", Toast.LENGTH_SHORT).show();
							btnB.setTextColor(Color.RED);
							img_ROW.setVisibility(View.VISIBLE);
							img_ROW.setImageResource(R.drawable.cuo);
							zhangjiejiexi.setText(cursor.getString(cursor
									.getColumnIndex("jiexi")));
						}
						break;
					case R.id.btnC:
						if (answer.equals("C")) {
							// Toast.makeText(WangnianzhentiActivity.this,
							// "答对啦", Toast.LENGTH_SHORT).show();
							zhangjiejiexi.setText(cursor.getString(cursor
									.getColumnIndex("jiexi")));
							img_ROW.setVisibility(View.VISIBLE);
							img_ROW.setImageResource(R.drawable.dui);
						} else {
							int flag = 1;
							if (cursor2 != null && cursor2.getCount() != 0) {
								for (cursor2.moveToFirst(); !cursor2
										.isAfterLast(); cursor2.moveToNext()) {
									if (cursor2.getString(
											cursor2.getColumnIndex("question"))
											.equals(questionString))
										;
									{
										flag = 0;
									}
								}
							}
							if (1 == flag)
								InsertWrongtable();
							btnC.setTextColor(Color.RED);
							// Toast.makeText(WangnianzhentiActivity.this,
							// "答错啦", Toast.LENGTH_SHORT).show();
							img_ROW.setVisibility(View.VISIBLE);
							img_ROW.setImageResource(R.drawable.cuo);
							zhangjiejiexi.setText(cursor.getString(cursor
									.getColumnIndex("jiexi")));
						}
						break;
					case R.id.btnD:
						if (answer.equals("D")) {
							// Toast.makeText(WangnianzhentiActivity.this,
							// "答对啦", Toast.LENGTH_SHORT).show();
							zhangjiejiexi.setText(cursor.getString(cursor
									.getColumnIndex("jiexi")));
							img_ROW.setVisibility(View.VISIBLE);
							img_ROW.setImageResource(R.drawable.dui);
						} else {
							int flag = 1;
							if (cursor2 != null && cursor2.getCount() != 0) {
								for (cursor2.moveToFirst(); !cursor2
										.isAfterLast(); cursor2.moveToNext()) {
									if (cursor2.getString(
											cursor2.getColumnIndex("question"))
											.equals(questionString))
										;
									{
										flag = 0;
									}
								}
							}
							if (1 == flag)
								InsertWrongtable();
							btnD.setTextColor(Color.RED);
							// Toast.makeText(WangnianzhentiActivity.this,
							// "答错啦", Toast.LENGTH_SHORT).show();
							img_ROW.setVisibility(View.VISIBLE);
							img_ROW.setImageResource(R.drawable.cuo);
							zhangjiejiexi.setText(cursor.getString(cursor
									.getColumnIndex("jiexi")));
						}
						break;
					}
				}
			});
			gdDetector = new GestureDetector(this);

		} else {
			// 隐藏控件
			zhangjiequestion.setVisibility(View.INVISIBLE);
			checkA.setVisibility(View.INVISIBLE);
			checkB.setVisibility(View.INVISIBLE);
			checkC.setVisibility(View.INVISIBLE);
			checkD.setVisibility(View.INVISIBLE);
			checkE.setVisibility(View.INVISIBLE);
			tijiaoButton.setVisibility(View.INVISIBLE);
			btnGrp.setVisibility(View.INVISIBLE);
			img_ROW.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "还木有数据", Toast.LENGTH_SHORT).show();
		}
		tijiaoButton.setOnClickListener(this);
	}

	private void init() {
		zhangjiequestion = (TextView) findViewById(R.id.zhangjie_question);
		btnGrp = (RadioGroup) findViewById(R.id.BtnGrp);
		btnA = (RadioButton) findViewById(R.id.btnA);
		btnB = (RadioButton) findViewById(R.id.btnB);
		btnC = (RadioButton) findViewById(R.id.btnC);
		btnD = (RadioButton) findViewById(R.id.btnD);
		checkA = (CheckBox) findViewById(R.id.checkA);
		checkB = (CheckBox) findViewById(R.id.checkB);
		checkC = (CheckBox) findViewById(R.id.checkC);
		checkD = (CheckBox) findViewById(R.id.checkD);
		checkE = (CheckBox) findViewById(R.id.checkE);
		tijiaoButton = (Button) findViewById(R.id.tijiao);
		zhangjiejiexi = (TextView) findViewById(R.id.zhangjie_jiexi);
		img_ROW = (ImageView) findViewById(R.id.zhangjie_YON);
	}

	private void InsertWrongtable() {
		zhangjiejiexi.setText("解析\n"
				+ cursor.getString(cursor.getColumnIndex("jiexi")));
		ContentValues cv = new ContentValues();
		cv.put("question", cursor.getString(cursor.getColumnIndex("question")));
		cv.put("A", cursor.getString(cursor.getColumnIndex("A")));
		cv.put("B", cursor.getString(cursor.getColumnIndex("B")));
		cv.put("C", cursor.getString(cursor.getColumnIndex("C")));
		cv.put("D", cursor.getString(cursor.getColumnIndex("D")));
		cv.put("E", cursor.getString(cursor.getColumnIndex("E")));
		cv.put("answer", cursor.getString(cursor.getColumnIndex("answer")));
		cv.put("jiexi", cursor.getString(cursor.getColumnIndex("jiexi")));
		cv.put("type", cursor.getString(cursor.getColumnIndex("type")));
		sqldb.insert("wrong", null, cv);
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
		if (e1.getX() - e2.getX() > 80) {
			// 向右划
			if (cursor.isLast()) {
				Toast.makeText(this, "后面已经每题了", Toast.LENGTH_SHORT).show();
			} else {

				cursor.moveToNext();
				if (cursor.getInt(cursor.getColumnIndex("type")) == 0) {
					// 单选题
					// 隐藏控件
					btnGrp.setVisibility(View.VISIBLE);
					checkA.setVisibility(View.INVISIBLE);
					checkB.setVisibility(View.INVISIBLE);
					checkC.setVisibility(View.INVISIBLE);
					checkD.setVisibility(View.INVISIBLE);
					checkE.setVisibility(View.INVISIBLE);
					tijiaoButton.setVisibility(View.INVISIBLE);
					btnGrp.clearCheck();
					btnA.setChecked(false);
					btnB.setChecked(false);
					btnC.setChecked(false);
					btnD.setChecked(false);
					questionString = cursor.getString(cursor
							.getColumnIndex("question"));
					zhangjiequestion.setText(questionString);
					btnA.setText(cursor.getString(cursor.getColumnIndex("A")));
					btnB.setText(cursor.getString(cursor.getColumnIndex("B")));
					btnC.setText(cursor.getString(cursor.getColumnIndex("C")));
					btnD.setText(cursor.getString(cursor.getColumnIndex("D")));
					btnA.setTextColor(Color.BLACK);
					btnB.setTextColor(Color.BLACK);
					btnC.setTextColor(Color.BLACK);
					btnD.setTextColor(Color.BLACK);
					btnA.setClickable(true);
					btnB.setClickable(true);
					btnC.setClickable(true);
					btnD.setClickable(true);
					zhangjiejiexi.setText("");
				} else {
					// 多选题
					btnGrp.setVisibility(View.INVISIBLE);
					checkA.setVisibility(View.VISIBLE);

					checkB.setVisibility(View.VISIBLE);
					checkC.setVisibility(View.VISIBLE);
					checkD.setVisibility(View.VISIBLE);
					checkE.setVisibility(View.VISIBLE);
					tijiaoButton.setVisibility(View.VISIBLE);
					questionString = cursor.getString(cursor
							.getColumnIndex("question"));
					zhangjiequestion.setText(questionString);
					// 如果选中，则去掉
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
			img_ROW.setVisibility(View.INVISIBLE);
		} else if (e1.getX() - e2.getX() < -80) {

			// 向前滑动
			System.out.println("前一题");
			if (cursor.isFirst()) {
				Toast.makeText(this, "前面没题了", Toast.LENGTH_SHORT).show();
			} else {
				cursor.moveToPrevious();
				System.out.println("cursor 向前移动一格");
				if (cursor.getInt(cursor.getColumnIndex("type")) == 0) {
					// 单选题
					System.out.println("单选题");
					btnGrp.setVisibility(View.VISIBLE);
					checkA.setVisibility(View.INVISIBLE);
					checkB.setVisibility(View.INVISIBLE);
					checkC.setVisibility(View.INVISIBLE);
					checkD.setVisibility(View.INVISIBLE);
					checkE.setVisibility(View.INVISIBLE);
					tijiaoButton.setVisibility(View.INVISIBLE);
					// btnGrp.clearCheck();
					btnA.setChecked(false);
					btnB.setChecked(false);
					btnC.setChecked(false);
					btnD.setChecked(false);
					questionString = cursor.getString(cursor
							.getColumnIndex("question"));
					zhangjiequestion.setText(questionString);
					btnA.setText(cursor.getString(cursor.getColumnIndex("A")));
					btnB.setText(cursor.getString(cursor.getColumnIndex("B")));
					btnC.setText(cursor.getString(cursor.getColumnIndex("C")));
					btnD.setText(cursor.getString(cursor.getColumnIndex("D")));
					btnA.setTextColor(Color.BLACK);
					btnB.setTextColor(Color.BLACK);
					btnC.setTextColor(Color.BLACK);
					btnD.setTextColor(Color.BLACK);
					btnA.setClickable(true);
					btnB.setClickable(true);
					btnC.setClickable(true);
					btnD.setClickable(true);
					zhangjiejiexi.setText("");
				} else {
					System.out.println("多选题");
					btnGrp.setVisibility(View.INVISIBLE);
					checkA.setVisibility(View.VISIBLE);

					checkB.setVisibility(View.VISIBLE);
					checkC.setVisibility(View.VISIBLE);
					checkD.setVisibility(View.VISIBLE);
					checkE.setVisibility(View.VISIBLE);
					tijiaoButton.setVisibility(View.VISIBLE);
					questionString = cursor.getString(cursor
							.getColumnIndex("question"));
					zhangjiequestion.setText(questionString);
					// 如果选中，则去掉
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

			img_ROW.setVisibility(View.INVISIBLE);
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
		super.onTouchEvent(event);
		return this.gdDetector.onTouchEvent(event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tijiao:
			Action();
			break;

		default:
			break;
		}
	}

	private void Action() {
		System.out.println("提交");
		checkA.setClickable(false);
		checkB.setClickable(false);
		checkC.setClickable(false);
		checkD.setClickable(false);
		checkE.setClickable(false);
		String ansString = cursor.getString(cursor.getColumnIndex("answer"));
		String checkString = new String("");
		if (checkA.isChecked())
			checkString = checkString + "A";
		if (checkB.isChecked())
			checkString = checkString + "B";
		if (checkC.isChecked())
			checkString = checkString + "C";
		if (checkD.isChecked())
			checkString = checkString + "D";
		if (checkE.isChecked())
			checkString = checkString + "E";
		if (ansString.equals(checkString)) {
			/*Toast.makeText(WangnianzhentiActivity.this, "答对啦",
					Toast.LENGTH_SHORT).show();*/
			zhangjiejiexi.setText(cursor.getString(cursor
					.getColumnIndex("jiexi")));
			img_ROW.setVisibility(View.VISIBLE);
			img_ROW.setImageResource(R.drawable.dui);
		} else {
			/*Toast.makeText(WangnianzhentiActivity.this, "答错了",
					Toast.LENGTH_SHORT).show();*/
			int flag = 1;
			if (cursor2 != null && cursor2.getCount() != 0) {
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
			if (1 == flag)
				InsertWrongtable();
			img_ROW.setVisibility(View.VISIBLE);
			img_ROW.setImageResource(R.drawable.cuo);
			zhangjiejiexi.setText(cursor.getString(cursor
					.getColumnIndex("jiexi")));
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
			ScrollView scrollView = (ScrollView) getLayoutInflater().inflate(
					R.layout.dialog_zhangjielianxi, null);
			AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
			aBuilder.setTitle("温馨提示");
			aBuilder.setView(scrollView);
			aBuilder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}

					});
			aBuilder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}
					});
			aBuilder.show();
			break;

		case R.id.collect:
			int flag = 1;
			if (cursor3 != null && cursor3.getCount() != 0) {
				for (cursor3.moveToFirst(); !cursor3.isAfterLast(); cursor3
						.moveToNext()) {
					if (cursor3.getString(cursor3.getColumnIndex("question"))
							.equals(questionString))
						;
					{
						flag = 0;
					}
				}
			}
			if (1 == flag) {
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
				cv.put("type", cursor.getString(cursor.getColumnIndex("type")));
				sqldb.insert("collect", null, cv);
			}
			break;
		}

		return super.onOptionsItemSelected(item);
	}

}
