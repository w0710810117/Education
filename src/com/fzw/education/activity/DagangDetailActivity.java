package com.fzw.education.activity;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;
import com.fzw.education.fragment.DagangDetailFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

public class DagangDetailActivity extends Activity{
	private ActionBar ab=null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dagangdetail);
		ab=this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayUseLogoEnabled(false);
		Bundle bundle=this.getIntent().getExtras();
		DagangDetailFragment fragment=new DagangDetailFragment();
		fragment.setArguments(bundle);
		getFragmentManager().beginTransaction()
		.replace(R.id.dagang_detail, fragment).commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void openOptionsMenu() {
		// TODO Auto-generated method stub
		super.openOptionsMenu();
	}
	
	
}
