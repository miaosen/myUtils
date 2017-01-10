package com.gzpykj.vtch.base;

import com.myutils.core.JSONResult;
import com.myutils.core.RowObject;
import com.myutils.core.json.JSONSerializer;
import com.myutils.utils.JsonUtils;

import java.util.List;

public class MResult implements JSONResult {
        String responseJsonText;
        List<RowObject> rows;
        private RowObject row;

       public MResult(){}
        public MResult(String responseJsonText) {
            this.responseJsonText=responseJsonText;
        }

        @Override
        public RowObject getAsRow() {
            if (row != null) {
                return row;
            } else {
                if (JsonUtils.isValidateJson(responseJsonText)) {
                    row = JSONSerializer.getRow(responseJsonText);
                }
                return row;
            }
        }

        @Override
        public List<RowObject> getAsRows() {
            if (rows != null) {
                return rows;
            } else {
                if (JsonUtils.isValidateJson(responseJsonText)) {
                    rows = JSONSerializer.getRows(responseJsonText);
                }
                return rows;
            }
        }

        @Override
        public boolean isSuccess() {
            if (JsonUtils.isValidateJson(responseJsonText)) {
                String reason = getAsRow().getString("reason");
                if (reason != null && reason.equals("Succes")) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Object getMainData() {
            return getAsRow().getRows("result");
        }

        @Override
        public String getMessage() {
            return null;
        }

    }
