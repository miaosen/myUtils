package com.myutils.ui.view.rcview;

import android.view.View;

/**
 * @email 1510809124@qq.com
 * @author zengmiaosen
 * @git http://git.oschina.net/miaosen/MyUtils
 * @CreateDate 2016/9/1 10:20
 * @Descrition ItemTouchCallback和recycleView的连接器，操作移动，拖拽的方法
 */
public interface ItemTouchApdater {

    /**
     * Item被选中时触发
     */
    void onItemSelected(View itemView);


    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish(View itemView);

    /**
     * Item在拖拽
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * 删除
     * @param position
     */
    void onItemDelete(int position);


}
