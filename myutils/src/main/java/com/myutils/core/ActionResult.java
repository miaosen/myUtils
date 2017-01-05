package com.myutils.core;

import com.myutils.core.json.JSONSerializer;
import com.myutils.utils.JsonUtils;

import java.util.List;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/12/19 17:07
 * @Descrition 标准json数据解析模型
 *
 *  格式: {"data":[{"id":"xxx","xx":"xxx"……},{"id":"xxx",……}],"success":true,"message":"xxx"}
 */
public class ActionResult {
	/**
	 * 返回的字符串内容
	 */
	private String responseText;
	/**
	 * 主要内容转化的RowObject
	 */
	private RowObject row;

	public ActionResult(String jsonText) {
		this.responseText = jsonText;
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
		}else{
			return getResponseText();
		}
	}



}
