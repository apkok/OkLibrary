package com.jssonok.library.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jssonok.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志视图打印器（OkViewPrinter）：将日志显示在界面上
 */
public class OkViewPrinter implements OkLogPrinter {

    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private OkViewPrinterProvider viewProvider;

    public OkViewPrinter(Activity activity) {
        // 为了在界面上显示出来，而不影响其他布局，则使用FrameLayout布局（该布局一定存在Activity布局结构里面，id叫content）
        FrameLayout rootView = activity.findViewById(android.R.id.content);

        // 创建RecyclerView
        recyclerView = new RecyclerView(activity);
        // 创建适配器
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        // 创建线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        // 给RecyclerView设置线性布局管理器
        recyclerView.setLayoutManager(layoutManager);
        // 给RecyclerView设置适配器
        recyclerView.setAdapter(adapter);
        // 创建日志视图打印器
        viewProvider = new OkViewPrinterProvider(rootView, recyclerView);
    }

    /**
     * 获取日志视图提供器（OkViewPrinterProvider），通过日志视图提供器（OkViewPrinterProvider）可以控制日志视图的展示和隐藏
     * @return ViewProvider
     */
    @NonNull
    public OkViewPrinterProvider getViewProvider() {
        return viewProvider;
    }

    @Override
    public void print(@NonNull OkLogConfig config, int level, String tag, @NonNull String printString) {
        // 将log展示添加到recycleView中
        adapter.addItem(new OkLogMo(System.currentTimeMillis(), level, tag, printString));
        // 滚动到对应的位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private LayoutInflater inflater;
        private List<OkLogMo> logs = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        void addItem(OkLogMo logItem) {
            logs.add(logItem);
            notifyItemInserted(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.ok_log_item, parent, false);
            return new LogViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            OkLogMo logItem = logs.get(position);
            int color = getHighlightColor(logItem.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);

            holder.tagView.setText(logItem.getFlattened());
            holder.messageView.setText(logItem.log);
        }

        /**
         * 根据日志级别获取不同的高亮颜色
         * @param logLevel 日志级别
         * @return 高亮的颜色
         */
        private int getHighlightColor(int logLevel) {
            int highlight;
            switch (logLevel) {
                case OkLogType.V:
                    highlight = 0xffbbbbbb;
                    break;
                case OkLogType.D:
                    highlight = 0xffffffff;
                    break;
                case OkLogType.I:
                    highlight = 0xff6a8759;
                    break;
                case OkLogType.W:
                    highlight = 0xffbbb529;
                    break;
                case OkLogType.E:
                    highlight = 0xffff6b68;
                    break;
                default:
                    highlight = 0xffffff00;
                    break;
            }
            return highlight;
        }

        @Override
        public int getItemCount() {
            return logs != null ? logs.size() : 0;
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tagView;
        TextView messageView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }
}
