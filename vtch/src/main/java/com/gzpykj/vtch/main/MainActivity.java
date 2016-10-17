//package com.gzpykj.vtch.main;
//
//import android.os.Bundle;
//import android.view.View;
//
//import com.gzpykj.vtch.R;
//import com.gzpykj.vtch.base.BaseAct;
//import com.myutils.base.AppFactory;
//import com.myutils.core.ActionResult;
//import com.myutils.core.RowObject;
//import com.myutils.core.logger.L;
//import com.myutils.core.okhttp.UrlInvoker;
//import com.myutils.core.okhttp.callback.StringCallBack;
//
//import java.util.List;
//
//public class MainActivity extends BaseAct {
//
//    public static String aa;
//
//    @Override
//    public int initConfig(Bundle savedInstanceState) {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    public void initView(View decorView) {
//        initHeader();
//        baseHeader.leftFinish();
//        baseHeader.setTitle("主页");
//    }
//
//    @Override
//    public void initData() {
//        L.i("aa==========="+aa);
//        aa = "bbbbbbbbbbbbbb";
//    }
//
//
//
//    @Override
//    public void click(View v) {
//
//
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//}
