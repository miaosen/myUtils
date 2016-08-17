package com.myutils.core.db;

import java.io.Serializable;
import java.util.List;

import android.content.ContentValues;


/**
 * @Created by gzpykj.com
 * @author zms
 * @Date 2016-1-4
 * @Descrition 表格实体类
 */
public class TablePty implements Serializable {
	
	private static final long serialVersionUID = -2011640088351651370L;
	
	private String name;
	
	private List<ContentValues> listVc;
	
	private ContentValues value;
	
	private String whereClause;
	
	private String[] whereArgs;
	
	
	public TablePty(String name,ContentValues value){
		this.name=name;
		this.value=value;
	}
	
	
	public TablePty(String name,List<ContentValues> listVc){
		this.name=name;
		this.listVc=listVc;
	}
	
	public TablePty(String name,String whereClause, String[] whereArgs){
		this.name=name;
		this.whereClause=whereClause;
		this.whereArgs=whereArgs;
	}
	
	
	public TablePty(String name,ContentValues value,String whereClause, String[] whereArgs){
		this.name=name;
		this.value=value;
		this.whereClause=whereClause;
		this.whereArgs=whereArgs;
	}
	
	public TablePty(String name,List<ContentValues> listVc,String whereClause, String[] whereArgs){
		this.name=name;
		this.listVc=listVc;
		this.whereClause=whereClause;
		this.whereArgs=whereArgs;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContentValues> getListVc() {
		return listVc;
	}

	public void setListVc(List<ContentValues> listVc) {
		this.listVc = listVc;
	}

	public ContentValues getValue() {
		return value;
	}

	public void setValue(ContentValues value) {
		this.value = value;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public String[] getWhereArgs() {
		return whereArgs;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public void setWhereArgs(String[] whereArgs) {
		this.whereArgs = whereArgs;
	}
	
	

}
