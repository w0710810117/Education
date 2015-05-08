package com.fzw.education.fragment;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;
import com.fzw.education.db.ImportDatabase;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DagangDetailFragment extends Fragment{
	
	private int id=1;
	private TextView textView=null;
	private Cursor cursor=null;
	private ImportDatabase importDatabase=null;
	private SQLiteDatabase sqldb=null;
	private String textString=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		id=getArguments().getInt("DAGANG_ID");//id从1开始
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView=inflater.inflate(R.layout.dagangdetailtext, container, false);
		textView=(TextView)rootView.findViewById(R.id.dagangdetail_text);
		textView.setMovementMethod(new ScrollingMovementMethod());
		getData();
		textView.setText(textString);
		return rootView;
	}
	private void getData(){
		importDatabase=new ImportDatabase(getActivity());
		sqldb=importDatabase.getReadDB();
		try {
			cursor=sqldb.rawQuery("select * from dagang where Sections='"+id+"'", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DagangDeatilFragment--->数据库读取失败");
		}
		if(cursor!=null){
			if(cursor.getCount()!=0){
				cursor.moveToFirst();
				textString=cursor.getString(cursor.getColumnIndex("content"));
			}
			else {
				System.out.println("DagangDetailFragment--->数据库为空");
			}
			cursor.close();
		}
		
		sqldb.close();
	}
	
}
