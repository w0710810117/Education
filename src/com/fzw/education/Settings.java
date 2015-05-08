package com.fzw.education;

import com.fzw.education.db.ImportDatabase;

import android.R.integer;
import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.MenuItem;
import android.widget.Toast;

public class Settings extends PreferenceActivity{
	
	PreferenceScreen clear,update,about_me,about_soft,share_soft; 
	private ImportDatabase importDatabase=null;
	private SQLiteDatabase wrongdb=null;
	private SQLiteDatabase collectdb=null;
	private Cursor cursor1=null,cursor2=null;
	private String questionString=null;
	private ActionBar aBar=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		
		clear=(PreferenceScreen)findPreference("clear");
		update=(PreferenceScreen)findPreference("update");
		about_me=(PreferenceScreen)findPreference("about_me");
		share_soft=(PreferenceScreen)findPreference("share_soft");
		about_soft=(PreferenceScreen)findPreference("about_soft");
		
		aBar=this.getActionBar();
		aBar.setDisplayHomeAsUpEnabled(true);
		aBar.setDisplayUseLogoEnabled(false);
		importDatabase=new ImportDatabase(this);
		wrongdb=importDatabase.getDB();
		collectdb=importDatabase.getDB();
		clear.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				try {
					cursor1=wrongdb.rawQuery("select * from wrong", null);
					cursor2=collectdb.rawQuery("select * from collect", null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Setting--->数据库读取失败");
				}
				if(cursor1!=null&&cursor1.getCount()!=0){
					cursor1.moveToFirst();
					for(int i=0;i<cursor1.getCount();++i){
						questionString=cursor1.getString(cursor1.getColumnIndex("question"));
						wrongdb.execSQL("delete from wrong where question='"+questionString+"'");
						cursor1.moveToNext();
					}
					
				}
				if(cursor2!=null&&cursor2.getCount()!=0){
					cursor2.moveToFirst();
					for(int i=0;i<cursor2.getCount();++i){
						questionString=cursor2.getString(cursor2.getColumnIndex("question"));
						wrongdb.execSQL("delete from collect where question='"+questionString+"'");
						cursor2.moveToNext();
					}
				}
				Toast.makeText(Settings.this, "数据已清除", Toast.LENGTH_SHORT).show();
				return true;
			}
		
		});
		update.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				Toast.makeText(Settings.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
				
				return false;
			}
		});
		
		share_soft.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				System.out.println("share");
                Intent email = new Intent(
                        android.content.Intent.ACTION_SEND);
                // 反馈至
                String aEmailList[] = {"fzwwzf@163.com",
                        "guanghezhang@163.com"};

              //设置邮件默认地址
                email.putExtra(android.content.Intent.EXTRA_EMAIL,
                        aEmailList);
                //设置邮件默认标题
               // email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
                //设置要默认发送的内容
                //email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
                email.setType("plain/text");

                //startActivity(email);
                startActivity(Intent.createChooser(email, "请选择分享方式"));
				return false;
			}
		});
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
}
