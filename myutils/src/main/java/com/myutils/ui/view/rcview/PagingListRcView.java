package com.myutils.ui.view.rcview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.myutils.R;
import com.myutils.base.AppFactory;
import com.myutils.base.L;
import com.myutils.core.JSONResult;
import com.myutils.core.RowObject;
import com.myutils.core.annotation.InjectReader;
import com.myutils.core.annotation.ViewInject;
import com.myutils.core.form.FormViewAdpater;
import com.myutils.core.form.ViewUtils;
import com.myutils.core.http.UrlInvoker;
import com.myutils.core.http.callback.StringCallBack;
import com.myutils.ui.view.LoadingTipLayout;

import java.util.LinkedList;
import java.util.List;


/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2017-01-06  17:07
 * @Descrition
 */

public class PagingListRcView extends LinearLayout implements FormViewAdpater {

    @ViewInject
    RecyclerView recycler_view;
    @ViewInject
    RefreshRcView refreshRcView;

    int itemlayoutId;

    public RcAdapterWithFooter adpRc;

    public List<RowObject> rows = new LinkedList<RowObject>();

    //页码参数
    public String pageIndexText = "pageNum";
    //页数参数
    public String pageSizeText = "pageSize";
    //页码
    public int pageIndex = 1;
    //分页大小
    public int pageSize = 10;

    //数据源
    public UrlInvoker uik;
    String actionClass;
    //方法名
    String actionName;
    //设置这个地址会覆盖掉框架的请求地址
    String url;
    //ResultCallBack resultCallBack;

    public interface LoadingType{
        String REFRESH="refresh";
        String LOAD="load";
    }


    OnDataAnalysisListener onDataAnalysisListener;

    public PagingListRcView(Context context, int itemlayout) {
        super(context);
        this.itemlayoutId = itemlayout;
        init();
    }

    public PagingListRcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttr(context, attrs);
        init();
    }


    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray typedarray = context.obtainStyledAttributes(attrs, R.styleable.PagingListRcView);
        itemlayoutId = typedarray.getResourceId(R.styleable.PagingListRcView_itemLayout, -1);
        actionClass = typedarray.getString(R.styleable.DataHandler_actionClass);
        actionName = typedarray.getString(R.styleable.DataHandler_actionName);
        url = typedarray.getString(R.styleable.DataHandler_url);
        typedarray.recycle();
    }

    private void init() {
        View view = ViewUtils.inflatView(getContext(), R.layout.ui_view_paging);
        addView(view);
        InjectReader.injectAllFields(this);
        initRcView();
        uik = new UrlInvoker(url);
        buildUrl();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    public void load() {
        uik.setCallback(new StringCallBack() {
            @Override
            public void onSuccess(JSONResult result) {
                if (onDataAnalysisListener != null) {
                    LinkedList<RowObject> rowObjects = onDataAnalysisListener.onAnalysis(result);
                    if (rowObjects != null) {
                        addList(rowObjects);
                    }
                } else {
                    onResultCallBack(result);
                }
            }

            @Override
            protected void onFail(Exception e) {
                super.onFail(e);
                enRefresh();
            }
        });
        getData(LoadingType.REFRESH);
    }


    public void enRefresh(){
        refreshRcView.setRefreshing(false);
        refreshRcView.setLoading(false);
    }

    /**
     * 请求地址拼接
     */
    private void buildUrl() {
        String httpRootPath = AppFactory.getAppConfig().getHttpRootPath();
        if (url == null || url.startsWith(httpRootPath)) {
            url = httpRootPath;
        }
        if (actionClass != null) {
            url = url + "/" + actionClass;
        }
        if (actionName != null) {
            url = url + "/" + actionName;
        }
        uik.setUrl(url);
    }

    /**
     * 获取数据
     *
     * @param type
     */
    public void getData(final String type) {
        if (type.equals(LoadingType.REFRESH)) {
            adpRc.hideFooter();
        } else {
            adpRc.showFooter();
        }
        uik.addParam(pageIndexText, pageIndex);
        uik.addParam(pageSizeText, pageSize);
        uik.invoke();
    }

    /**
     * 默认结构解析
     *
     * @param result
     */
    public void onResultCallBack(JSONResult result) {
        if (result.isSuccess()) {
            List<RowObject> resultRows = (List<RowObject>) result.getMainData();
            addList(resultRows);
        } else {
            if (result.getMessage() != null) {
                ViewUtils.toast(result.getMessage());
            }
        }
    }

    public void addList(List<RowObject> resultRows) {
        if (resultRows != null && resultRows.size() > 0) {
            adpRc.hideFooter();
            rows.addAll(resultRows);
            adpRc.notifyDataSetChanged();
            pageIndex = pageIndex + 1;
        } else {
            LoadingTipLayout tipLayout = adpRc.getTipLayout();
            if (tipLayout != null) {
                adpRc.getTipLayout().notData("没有更多数据！");
                adpRc.hideFooter(1);
            }
        }
        enRefresh();
    }

    public void refresh() {
        rows.clear();
        getData(LoadingType.REFRESH);
    }

    public void initRcView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        recycler_view.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        adpRc = new RcAdapterWithFooter(rows, itemlayoutId);
        recycler_view.setAdapter(adpRc);

        refreshRcView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rows.clear();
                adpRc.notifyDataSetChanged();
                pageIndex = 1;
                getData(LoadingType.REFRESH);
            }
        });
        refreshRcView.setOnLoadListener(new RefreshRcView.OnLoadListener() {
            @Override
            public void onLoad() {
                L.i("=========onLoad==============");
                getData(LoadingType.LOAD);
            }
        });
        refreshRcView.setRefreshing(true);
        refreshRcView.setLoading(false);
    }


    @Override
    public void setValue(Object object) {
        if (object.getClass().isInstance(rows.getClass())) {
            rows.addAll(((List<RowObject>) object));
        }
        adpRc.notifyDataSetChanged();
    }

    @Override
    public Object getValue() {
        return rows;
    }

    @Override
    public boolean isScanAsOne() {
        return true;
    }


    public interface OnDataAnalysisListener {
        LinkedList<RowObject> onAnalysis(JSONResult jsonResult);
    }

    public String getPageIndexText() {
        return pageIndexText;
    }

    public void setPageIndexText(String pageIndexText) {
        this.pageIndexText = pageIndexText;
    }

    public String getPageSizeText() {
        return pageSizeText;
    }

    public void setPageSizeText(String pageSizeText) {
        this.pageSizeText = pageSizeText;
    }

    public int getItemLayout() {
        return itemlayoutId;
    }

    public void setItemLayout(int itemlayout) {
        this.itemlayoutId = itemlayout;
        adpRc.setLayout(itemlayout);
    }

    public RcAdapterWithFooter getAdpRc() {
        return adpRc;
    }

    public void setAdpRc(RcAdapterWithFooter adpRc) {
        this.adpRc = adpRc;
    }

    public List<RowObject> getRows() {
        return rows;
    }

    public void setRows(List<RowObject> rows) {
        this.rows = rows;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public UrlInvoker getUik() {
        return uik;
    }

    public void setUik(UrlInvoker uik) {
        this.uik = uik;
    }

    public String getActionClass() {
        return actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
        buildUrl();
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
        buildUrl();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        uik.setUrl(url);
    }

    //public ResultCallBack getResultCallBack() {
    //    return resultCallBack;
    //}
    //
    //public void setResultCallBack(ResultCallBack resultCallBack) {
    //    this.resultCallBack = resultCallBack;
    //}

    public RecyclerView getRecycler_view() {
        return recycler_view;
    }

    public void setRecycler_view(RecyclerView recycler_view) {
        this.recycler_view = recycler_view;
    }

    public RefreshRcView getRefreshRcView() {
        return refreshRcView;
    }

    public void setRefreshRcView(RefreshRcView refreshRcView) {
        this.refreshRcView = refreshRcView;
    }

    public OnDataAnalysisListener getOnDataAnalysisListener() {
        return onDataAnalysisListener;
    }

    public void setOnDataAnalysisListener(OnDataAnalysisListener onDataAnalysisListener) {
        this.onDataAnalysisListener = onDataAnalysisListener;
    }
}
