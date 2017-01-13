package com.myutils.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.myutils.R;
import com.myutils.base.AppConfig;
import com.myutils.base.AppFactory;
import com.myutils.base.L;
import com.myutils.core.form.Form;
import com.myutils.core.form.ViewFilter;
import com.myutils.core.form.ViewUtils;
import com.myutils.core.http.UrlInvoker;
import com.myutils.core.http.callback.StringCallBack;
import com.myutils.utils.JsonUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2017-01-05  15:29
 * @Descrition 数据综合处理器,表单，http和数据库模块的综合封装
 */

public class DataHandler extends ViewGroup {

    private static AppConfig appConfig = AppFactory.getAppConfig();
    //action类名
    String actionClass;
    //方法名
    String actionName;
    //设置这个地址会覆盖掉框架的请求地址
    String url;

    //获取数据成功后提示
    String successMsg;
    //获取数据失败后提示
    String failMsg;
    //获取数据中提示
    String loadingMsg;

    //默认为true
    boolean atuoInvoke;
    //默认为false
    boolean isDataFromSql;
    //默认为false
    boolean isGetParamFromThis;
    //默认为false
    boolean isFillThis;
    //指定获取表单的区域的View的id
    int paramArea;
    //指定填充数据的区域的View的id
    int fillArea;
    //id 通过点击当前view的某个控件去调用数据请求
    int invokeByClick;


    Form formFill;

    Form formGetParam;

    ResultCallBack resultCallBack;


    UrlInvoker uik;
    Map<String, Object> mapParam=new LinkedHashMap<>();

    OnInvokeLisener onInvokeLisener;

    public DataHandler(Context context) {
        super(context);
    }

    public DataHandler(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttr(context,attrs);

    }

    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray typedarray=context.obtainStyledAttributes(attrs, R.styleable.DataHandler);
        actionClass = typedarray.getString(R.styleable.DataHandler_actionClass);
        actionName = typedarray.getString(R.styleable.DataHandler_actionName);
        url = typedarray.getString(R.styleable.DataHandler_url);
        successMsg = typedarray.getString(R.styleable.DataHandler_successMsg);
        failMsg = typedarray.getString(R.styleable.DataHandler_failMsg);
        loadingMsg = typedarray.getString(R.styleable.DataHandler_loadingMsg);
        String param= typedarray.getString(R.styleable.DataHandler_param);
        readParam(param);
        atuoInvoke=typedarray.getBoolean(R.styleable.DataHandler_atuoInvoke,true);
        isDataFromSql=typedarray.getBoolean(R.styleable.DataHandler_isDataFromSql,false);
        isGetParamFromThis=typedarray.getBoolean(R.styleable.DataHandler_isGetParamFromThis,false);
        isFillThis=typedarray.getBoolean(R.styleable.DataHandler_isFillThis,false);

        paramArea = typedarray.getResourceId(R.styleable.DataHandler_paramArea, -1);
        fillArea = typedarray.getResourceId(R.styleable.DataHandler_fillArea, -1);
        invokeByClick = typedarray.getResourceId(R.styleable.DataHandler_invokeByClick, -1);

        typedarray.recycle();
    }

    private void readParam(String param) {
        if(param!=null){
            //转成json
            param= param.replaceAll(",","\",\"");
            param= param.replaceAll("\\{","{\"");
            param= param.replaceAll(":","\":\"");
            param= param.replaceAll("\\}","\"}");
            if(JsonUtils.isValidateJson(param)){
                RowObject rowObject = JsonUtils.jsonToRow(param);
                for(String key: rowObject.keySet()){
                    mapParam.put(key,rowObject.get(key));
                }
            }
        }
    }



    private void init() {
        setVisibility(View.GONE);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        if(isDataFromSql){
        }else{
            setUrlinvoke();
        }
        relateToDocoreView();
    }

    /**
     * 关联activity布局操作
     */
    private void relateToDocoreView() {
        View decorView = ViewUtils.getDecorView(getContext());
        Map<String, View> viewWithIdName = ViewUtils.getViewWithIdName(decorView, ViewFilter.getInstance());
        if(isHasFillArea()){
            if(isFillThis){
                getFormFill(viewWithIdName);
            }else if(paramArea>0){
                View viewById = decorView.findViewById(fillArea);
                getFormFill(viewById);
            }
        }
        if(isHasParamArea()){
            if(isGetParamFromThis){
                getFormGetParam(viewWithIdName);
            }else if(paramArea>0){
                View viewById = decorView.findViewById(paramArea);
                getFormGetParam(viewById);
            }
        }

        if(invokeByClick>0){
            View viewById = decorView.findViewById(invokeByClick);
            viewById.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    invoke();
                }
            });
        }
    }

    public Form getFormGetParam(View view){
        if(formGetParam==null){
            formGetParam=new Form(view);
        }
        return formGetParam;
    }


    public Form getFormGetParam(Map<String, View> view){
        if(formGetParam==null){
            formGetParam=new Form(view);
        }
        return formGetParam;
    }



    public Form getFormFill(View view){
        if(formFill==null){
            formFill=new Form(view);
        }
        return formFill;
    }

    public Form getFormFill(Map<String, View> view){
        if(formFill==null){
            formFill=new Form(view);
        }
        return formFill;
    }


    /**
     * 配置地址请求器
     */
    private void setUrlinvoke() {
        if (url == null) {
            url = appConfig.getHttpRootPath()+ "/" + actionClass + "/" + actionName;
        }
        uik=new UrlInvoker(url);
        if(onInvokeLisener!=null){
            onInvokeLisener.onBefore();
        }
        if(resultCallBack!=null){
            uik.setCallback(resultCallBack);
        }else{
            uik.setCallback(new StringCallBack() {
                @Override
                public void onSuccess(JSONResult result) {
                    if(result.isSuccess()){
                        if(onInvokeLisener!=null){
                            onInvokeLisener.onSuccess(result);
                        }
                        if(isHasFillArea()){
                            formFill.fill((RowObject) result.getMainData());
                        }
                    }
                }
                @Override
                protected void onFail(Exception e) {
                    super.onFail(e);
                    if(onInvokeLisener!=null){
                        onInvokeLisener.onFail(e);
                    }
                }
            });
        }
        uik.addParam(mapParam);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
        if(atuoInvoke) {
            invoke();
        }
    }

    public void invoke() {
        if(isHasParamArea()){
            mapParam.putAll(formGetParam.getContentValue());
            L.i("isHasParamArea=========invoke=============="+mapParam);
        }
        if(isDataFromSql){

        }else{
            uik.addParam(mapParam);
            uik.invoke();
        }

    }


    public interface OnInvokeLisener {
        void onBefore();
        void onSuccess(JSONResult result);
        void onFail(Exception e);
    }

    /**
     * 是否需要填充
     * @return
     */
    public boolean isHasFillArea(){
        if(isFillThis||fillArea>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 是否需要从页面获取参数
     * @return
     */
    public boolean isHasParamArea(){
        if(isGetParamFromThis||paramArea>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public static AppConfig getAppConfig() {
        return appConfig;
    }

    public static void setAppConfig(AppConfig appConfig) {
        DataHandler.appConfig = appConfig;
    }

    public String getActionClass() {
        return actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    public String getLoadingMsg() {
        return loadingMsg;
    }

    public void setLoadingMsg(String loadingMsg) {
        this.loadingMsg = loadingMsg;
    }

    public boolean isAtuoInvoke() {
        return atuoInvoke;
    }

    public void setAtuoInvoke(boolean atuoInvoke) {
        this.atuoInvoke = atuoInvoke;
    }

    public boolean isDataFromSql() {
        return isDataFromSql;
    }

    public void setDataFromSql(boolean dataFromSql) {
        isDataFromSql = dataFromSql;
    }

    public boolean isGetParamFromThis() {
        return isGetParamFromThis;
    }

    public void setGetParamFromThis(boolean getParamFromThis) {
        isGetParamFromThis = getParamFromThis;
    }

    public boolean isFillThis() {
        return isFillThis;
    }

    public void setFillThis(boolean fillThis) {
        isFillThis = fillThis;
    }

    public int getParamArea() {
        return paramArea;
    }

    public void setParamArea(int paramArea) {
        this.paramArea = paramArea;
    }

    public int getFillArea() {
        return fillArea;
    }

    public void setFillArea(int fillArea) {
        this.fillArea = fillArea;
    }

    public int getInvokeByClick() {
        return invokeByClick;
    }

    public void setInvokeByClick(int invokeByClick) {
        this.invokeByClick = invokeByClick;
    }

    public Form getFormFill() {
        return formFill;
    }

    public void setFormFill(Form formFill) {
        this.formFill = formFill;
    }

    public Form getFormGetParam() {
        return formGetParam;
    }

    public void setFormGetParam(Form formGetParam) {
        this.formGetParam = formGetParam;
    }

    public UrlInvoker getUik() {
        return uik;
    }

    public void setUik(UrlInvoker uik) {
        this.uik = uik;
    }

    public Map<String, Object> getMapParam() {
        return mapParam;
    }

    public void setMapParam(Map<String, Object> mapParam) {
        this.mapParam = mapParam;
    }

    public OnInvokeLisener getOnInvokeLisener() {
        return onInvokeLisener;
    }

    public void setOnInvokeLisener(OnInvokeLisener onInvokeLisener) {
        this.onInvokeLisener = onInvokeLisener;
    }
}