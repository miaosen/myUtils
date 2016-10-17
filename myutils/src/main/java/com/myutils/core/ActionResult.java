package com.myutils.core;

import com.myutils.core.gson.JSONSerializer;
import com.myutils.utils.JsonUtils;

import java.util.List;

public class ActionResult {


	/**
	 * 返回的字符串内容
	 */
	private String responseText;
	/**
	 * 主要内容转化的RowObject
	 */
	private RowObject row;

	public ActionResult(String text) {
		this.responseText = text;
	}

	public String getResponseText() {
		return responseText;
	}

	public RowObject getRow() {
		if(row!=null){
			return row;
		}else{
			if (JsonUtils.isValidateJson(getResponseText())) {
				row = JSONSerializer.getRow(responseText);
			}
			return row;
		}
	}

	public List<RowObject> getRows() {
		return JSONSerializer.getRows(responseText);
	}

	public boolean isSuccess() {

		if (JsonUtils.isValidateJson(getResponseText())) {
			if (JsonUtils.isSuccess(getResponseText())) {
				return true;
			}
		}
		return false;
	}

	public String getMessage() {
		if(getRow()!=null){
			return getRow().getString("message");
		}
		return null;
	}



}
