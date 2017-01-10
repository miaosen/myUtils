package com.myutils.ui.view.rcview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myutils.core.RowObject;
import com.myutils.core.form.Form;
import com.myutils.base.L;

import java.util.List;

/**
 * @author zengmiaosen
 * @email 1510809124@qq.com
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/1 14:36
 * @Descrition RecyclerView数据自动填充适配器
 */
public class BaseRcAdapter extends RecyclerView.Adapter<BaseRcAdapter.ViewHolder> implements ItemTouchApdater {

    private List<RowObject> rows;
    private int layout;


    // item监听
    private OnItemClickListener onItemClickListener;
    OnItemDetailListener onItemDetailListener;
    int num=0;

    public BaseRcAdapter(List<RowObject> rows, int layout) {
        this.rows = rows;
        this.layout = layout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        //L.i("onCreateViewHolder====================="+num);
        num=num+1;
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RowObject row = rows.get(position);
        holder.fillUnit.fill(row);
        if(onItemDetailListener!=null){
            onItemDetailListener.setItem(holder, row, position);
        }
        holder.itemView.setOnClickListener(new mClick(holder, row));
    }


    /**
     * ItemView初始化
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public Form fillUnit;

        public ViewHolder(View itemView) {
            super(itemView);
            if(fillUnit==null){
                fillUnit = new Form(itemView);

            }
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



    /**
     * item监听
     */
    class mClick implements View.OnClickListener {
        private ViewHolder viewHolder;
        private RowObject row;
        private int position;

        public mClick(ViewHolder viewHolder, RowObject row) {
            this.viewHolder = viewHolder;
            this.row = row;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(viewHolder, row, position);
            }
        }
    }





    @Override
    public int getItemCount() {
        return rows.size();
    }

    /**
     * item 监听接口
     */
    public interface OnItemClickListener {
        void onItemClick(ViewHolder viewHolder, RowObject row, int position);
    }


    //==================移动拖拽方法区=====================

    @Override
    public void onItemSelected(View itemView) {
        itemView.setBackgroundColor(Color.parseColor("#eeeeee"));
    }

    @Override
    public void onItemFinish(View itemView) {
        itemView.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        RowObject row = rows.get(fromPosition);
        rows.remove(fromPosition);
        rows.add(toPosition, row);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDelete(int position) {
        rows.remove(position);
        notifyItemRemoved(position);
    }


    public List<RowObject> getRows() {
        return rows;
    }

    public void setRows(List<RowObject> rows) {
        this.rows = rows;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemDetailListener{
        void setItem(ViewHolder viewHolder, RowObject row, int position);
    }

    public OnItemDetailListener getOnItemDetailListener() {
        return onItemDetailListener;
    }

    public void setOnItemDetailListener(OnItemDetailListener onItemDetailListener) {
        this.onItemDetailListener = onItemDetailListener;
    }
}
