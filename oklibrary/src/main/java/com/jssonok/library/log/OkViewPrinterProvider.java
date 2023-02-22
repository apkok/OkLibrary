package com.jssonok.library.log;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jssonok.library.util.OkDisplayUtil;

public class OkViewPrinterProvider {
    private FrameLayout rootView;
    private View floatingView;
    private boolean isOpen;
    private FrameLayout logView;
    private RecyclerView recyclerView;

    public OkViewPrinterProvider(FrameLayout rootView, RecyclerView recyclerView) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
    }

    // 悬浮框标识
    private static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    // 日志视图标识
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    public void showFloatingView() {
        // 界面里已经添加了悬浮框，则不需要任何操作，防止重复的添加
        if(rootView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            return;
        }

        // 显示在屏幕上的位置
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 屏幕上的右下角
        params.gravity = Gravity.BOTTOM | Gravity.END;
        View floatingView = genFloatingView();
        // 为悬浮框设置Tag
        floatingView.setTag(TAG_FLOATING_VIEW);
        // 为悬浮框设置背景颜色
        floatingView.setBackgroundColor(Color.BLACK);
        // 为悬浮框设置透明度
        floatingView.setAlpha(0.8f);
        // 设置布局底部距离
        params.bottomMargin = OkDisplayUtil.dp2px(100, recyclerView.getResources());
        // 将悬浮框添加至rootView中
        rootView.addView(genFloatingView(), params);
    }

    /**
     * 创建悬浮窗
     * @return
     */
    private View genFloatingView() {
        // 如果悬浮窗已创建，则直接返回悬浮窗
        if(floatingView != null) {
            return floatingView;
        }
        // 创建TextView
        TextView textView = new TextView(rootView.getContext());
        // 设置TextView的点击事件
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断日志视图是否已打开
                if(!isOpen) {
                    // 显示日志视图
                    showLogView();
                }
            }
        });
        textView.setText("OkLog");
        return floatingView = textView;
    }

    /**
     * 显示日志视图
     */
    private void showLogView() {
        if(rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }
        // 显示在屏幕上的位置
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, OkDisplayUtil.dp2px(160, rootView.getResources()));
        // 在底部显示
        params.gravity = Gravity.BOTTOM;
        View logView = genLogView();
        logView.setTag(TAG_LOG_VIEW);
        rootView.addView(genLogView(), params);
        isOpen = true;
    }

    /**
     * 创建日志视图
     * @return
     */
    private View genLogView() {
        if(logView != null) {
            return logView;
        }
        // 创建日志视图
        FrameLayout logView = new FrameLayout(rootView.getContext());
        logView.setBackgroundColor(Color.BLACK);
        logView.addView(recyclerView);
        // 关闭按钮显示的位置
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;
        // 创建关闭按钮
        TextView closeView = new TextView(rootView.getContext());
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLogView();
            }
        });
        closeView.setText("Close");
        // 将关闭按钮添加到日志视图中
        logView.addView(closeView, params);
        return this.logView = logView;
    }

    /**
     * 关闭日志视图
     */
    private void closeLogView() {
        isOpen = false;
        rootView.removeView(genLogView());
    }
}
