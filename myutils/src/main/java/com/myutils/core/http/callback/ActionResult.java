package com.myutils.core.http.callback;

import com.myutils.core.JSONResult;
import com.myutils.core.RowObject;
import com.myutils.core.json.JSONSerializer;
import com.myutils.utils.JsonUtils;

import java.util.List;


/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/12/19 17:07
 * @Descrition 返回的json数据格式规范，方便统一解析
 *  web端统一返回的json格式:
 *  {"data":{"id":"xxx","name":"xxx","worklist":[{"workId":1},{"workId":2}]},"success":true,"message":"登陆成功！"}
 */
public class ActionResult implements JSONResult{
	/**
	 * 返回的字符串内容
	 */
	private String responseJsonText;
	/**
	 * 主要内容转化的RowObject
	 */
	private RowObject row;

    List<RowObject> rows;


	public ActionResult(String responseJsonText) {
		this.responseJsonText = responseJsonText;
	}


	@Override
	public String getAsText() {
		return responseJsonText;
	}

	@Override
	public RowObject getAsRow() {
		if(row!=null){
			return row;
		}else{
			if (JsonUtils.isValidateJson(responseJsonText)) {
				row = JSONSerializer.getRow(responseJsonText);
			}
			return row;
		}
	}
    @Override
	public List<RowObject> getAsRows() {
        if(rows!=null){
            return rows;
        }else{
            if (JsonUtils.isValidateJson(responseJsonText)) {
                rows =JSONSerializer.getRows(responseJsonText);
            }
            return rows;
        }
	}


    @Override
	public boolean isSuccess() {
		if (JsonUtils.isValidateJson(responseJsonText)) {
			if (JsonUtils.isSuccess(responseJsonText)) {
				return true;
			}
		}
		return false;
	}

    @Override
    public Object getMainData() {
        return getAsRow().get("data");
    }

    public String getMessage() {
		if(getAsRow()!=null){
			return getAsRow().getString("message");
		}else{
			return responseJsonText;
		}
	}


}
