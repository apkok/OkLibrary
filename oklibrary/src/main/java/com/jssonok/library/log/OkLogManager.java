package com.jssonok.library.log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 日志管理类
 */
public class OkLogManager {
    // 实现OkLogManager单例模式
    private OkLogConfig config;
    public static OkLogManager instance;
    // 打印器集合
    public List<OkLogPrinter> printers = new ArrayList<>();

    public OkLogManager(OkLogConfig config, OkLogPrinter[] printers) {
        this.config = config;
        // asList: 将数组转换成List集合
        this.printers.addAll(Arrays.asList(printers));
    }
    /**
     * 获取OkLogManager单例
     * @return
     */
    public static OkLogManager getInstance() {
        return instance;
    }

    /**
     * 初始化日志管理类
     * @param config 日志配置类不可为空
     */
    public static void init(@NonNull OkLogConfig config, OkLogPrinter... printers) {
        instance = new OkLogManager(config, printers);
    }

    /**
     * 获取日志配置类
     * @return
     */
    public OkLogConfig getConfig() {
        return config;
    }

    /**
     * 获取打印器
     * @return 打印器集合
     */
    public List<OkLogPrinter> getPrinters() {
        return printers;
    }

    /**
     * 添加打印器
     * @param printer 需要添加的打印器
     */
    public void addPrinter(OkLogPrinter printer) {
        printers.add(printer);
    }

    /**
     * 移除打印器
     * @param printer 需要移除的打印器
     */
    public void removePrinter(OkLogPrinter printer) {
        if(printers != null) {
            printers.remove(printer);
        }
    }
}
