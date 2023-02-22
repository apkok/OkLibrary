package com.jssonok.library.log;

/**
 * 日志配置类
 */
public abstract class OkLogConfig {

    // 定义日志格式化的时候，每一行所显示的最大的长度（512个字符）
    static int MAX_LEN = 512;
    // 日志格式化器会被多次使用，因此创建一个单例模式（懒汉）的实例
    static OkLogThreadFormatter OK_LOG_THREAD_FORMATTER = new OkLogThreadFormatter();
    static OkLogStackTraceFormatter OK_LOG_STACK_TRACE_FORMATTER = new OkLogStackTraceFormatter();

    /**
     * 不仅能实现String类型的数据信息的打印，还要能实现任何数据类型的数据信息的打印，这就需要进行对象的序列化操作
     */
    public interface JsonParser {
        // 将任何数据类型的数据转换成String类型的数据
        String toJson(Object content);
    }

    /**
     * 注入序列化器
     * @return
     */
    public JsonParser injectJsonParser() {
        return null;
    }

    /**
     * 获取堆栈信息的深度
     * @return 默认深度为5
     */
    public int stackTraceDepth() {
        return 5;
    }

    /**
     * 获取日志打印器
     * @return
     */
    public OkLogPrinter[] getPrinters() {
        return null;
    }

    /**
     * 设置日志信息里面是否包含线程信息
     * @return 默认返回false（不包含）
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 获取全局日志标志
     * 如果用户不设置Tag，则默认使用全局的日志标志：OkLog
     * @return
     */
    public String getGlobalTag() {
        return "OkLog";
    }

    /**
     * 是否启用OkLog
     * 默认启用OkLog
     * @return
     */
    public boolean enable() {
        return true;
    }
}
