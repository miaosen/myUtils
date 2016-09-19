package com.myutils.ui.view.rcview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myutils.core.RowObject;
import com.myutils.core.logger.L;
import com.myutils.ui.view.LoadingTipLayout;
import com.myutils.ui.view.rcview.BaseRcAdapter;
import com.myutils.utils.DPUtils;

import java.util.List;

/**
 * Created by OAIM on 2016/9/18.
 */
public class RcAdapterWithFooter extends BaseRcAdapter{

    //底部--往往是loading_more
    public final static int TYPE_FOOTER = 2;

    public boolean isShowFooter=true;

    LoadingTipLayout tipLayout;

    public RcAdapterWithFooter(List<RowObject> rows, int layout) {
        super(rows, layout);
    }

    @Override
    public void setItem(ViewHolder viewHolder, RowObject row, int position) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_FOOTER){
          // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ui_view_loadingtip, parent, false);
            LoadingTipLayout view=new LoadingTipLayout(parent.getContext());
            view.getLn_loading().setOrientation(LinearLayout.HORIZONTAL);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DPUtils.dip2px(40)));
            return new FootHolder(view);
        }else{
            return super.onCreateViewHolder(parent,viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getRows().size()) {
            return TYPE_FOOTER;
        } else {
            return 0;
        }

    }

    @Override
    public int getItemCount() {
        if(isShowFooter){
            return getRows().size()+1;
        }
        return getRows().size();

    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position==getRows().size()){
            setFooter(holder);
            //holder.itemView.setVisibility(View.GONE);
        }else{
            super.onBindViewHolder(holder, position);
        }
    }

    private void setFooter(ViewHolder holder) {
        FootHolder footHolder= (FootHolder) holder;
        tipLayout= (LoadingTipLayout) footHolder.itemView;
    }

    /**
     * ItemView初始化
     */
    public class FootHolder extends ViewHolder {
        public FootHolder(View itemView) {
            super(itemView);
        }
        public void setText(int id, String text) {
            View view = itemView.findViewById(id);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setText(text);
            } else {
                L.i(view.getClass().getName() + "  不是TextView子类");
            }
        }
    }

    public void showFooter() {
        if(getItemCount()==getRows().size()){
            notifyItemInserted(getItemCount());
        }
        if(tipLayout!=null){
            tipLayout.loading();
        }
        isShowFooter=true;
    }

    public void hideFooter() {
        if(getItemCount()>getRows().size()){
            notifyItemRemoved(getRows().size()-1);
        }
        isShowFooter=false;
    }

    public boolean isShowFooter() {
        return isShowFooter;
    }


    public LoadingTipLayout getTipLayout() {
        return tipLayout;
    }

    public void setTipLayout(LoadingTipLayout tipLayout) {
        this.tipLayout = tipLayout;
    }
}
