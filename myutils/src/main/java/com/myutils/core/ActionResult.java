package com.myutils.core;

import java.util.List;

import com.myutils.core.gson.JSONSerializer;
import com.myutils.utils.JsonUtils;

import okhttp3.Response;

public class ActionResult {


	private Response response;
	/**
	 * 返回的字符串内容
	 */
	private String text;
	/**
	 * 主要内容
	 */
	private String data;
	/**
	 * 主要内容转化的RowObject
	 */
	private RowObject row;
	/**
	 * 主要内容转化的List<RowObject>
	 */
	private List<RowObject> rows;
	/**
	 * 获取数据成功标志
	 */
	private boolean success = false;
	/**
	 * 附加信息、错误信息
	 */
	private String message;


	public ActionResult(Response response) {
		this.response = response;
	}

	public ActionResult(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public RowObject getRow() {
		if (JsonUtils.isValidateJson(getText())) {
			row = JSONSerializer.getRow(text);
		}
		return row;
	}

	public List<RowObject> getRows() {
		return JSONSerializer.getRows(text);
	}

	public boolean isSuccess() {
		if (JsonUtils.isValidateJson(getText())) {
			if (JsonUtils.isSuccess(getText())) {
				success = true;
			}
		}
		return success;
	}

	public String getMessage() {
		if(row!=null){
			message=row.getString("message");
		}
		return message;
	}



}
