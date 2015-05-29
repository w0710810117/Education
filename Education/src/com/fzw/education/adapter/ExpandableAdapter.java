package com.fzw.education.adapter;

import java.util.ArrayList;
import java.util.List;

import com.fzw.education.R;
import com.fzw.education.R.id;
import com.fzw.education.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandableAdapter extends BaseExpandableListAdapter{

	private List<String> group;
	private List<List<String>> child;
	private Context context;
	public ExpandableAdapter(Context context){
		super();
		this.context=context;
		initializeData();
	}
	private void initializeData(){
    	group=new ArrayList<String>();
    	child=new ArrayList<List<String>>();
    	addInfo("教育学",new String[]{"第一章  教育与教育学","第二章  教育与社会发展","第三章  教育与个人发展",
    			"第四章  教育目的","第五章  学生与教师","第六章  课程","第七章  教学上","第八章 教学下","第九章 德育","第十章 班级管理"});
    	addInfo("教育心理学",new String[]{"第一章 教育心理学概述","第二章  中学生的心理发展与教育","第三章  学习的基本理论",
    			"第四章  学习动机","第五章  学习的迁移","第六章 知识的学习","第七章 技能的形成","第八章 学习的策略"
    			,"第九章 问题解决与创造性","第十章 态度与品德的形成","第十一章 心理健康教育","第十二章 教学设计",
    			"第十三章 课堂管理","第十四章 教学与测量的评测","第十五章 教师心理 "});
    }
    private void addInfo(String g,String[] c){
    	group.add(g);
    	List<String> childitem=new ArrayList<String>();
    	for(int i=0;i<c.length;i++){
    		childitem.add(c[i]);
    	}
    	child.add(childitem);
    }
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return child.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return group.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout relativeLayout1=(RelativeLayout)layoutInflater.inflate(R.layout.jiaoyugroup, null);
		TextView textView=(TextView)relativeLayout1.findViewById(R.id.jiaoyugroup_buju);
		textView.setText(group.get(groupPosition));
		
		return relativeLayout1;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater=(LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout relativeLayout2=(RelativeLayout)layoutInflater.inflate(R.layout.jiaoyuchild, null);
		TextView textView=(TextView)relativeLayout2.findViewById(R.id.jiaoyuchild_buju);
		textView.setText(child.get(groupPosition).get(childPosition));
		
		return relativeLayout2;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	

}