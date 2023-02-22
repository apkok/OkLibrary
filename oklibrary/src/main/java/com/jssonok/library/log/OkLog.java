package com.jssonok.library.log;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * Tips:
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 */
public class OkLog {

    // 获取包名前缀
    private static final String OK_LOG_PACKAGE;
    static {
        String className = OkLog.class.getName();
        OK_LOG_PACKAGE = className.substring(0, className.lastIndexOf(".")+1);
    }

    public static void v(Object... contents) {
        log(OkLogType.V, contents);
    }
    public static void vt(String tag, Object... contents) {
        log(tag, OkLogType.V, contents);
    }

    public static void d(Object... contents) {
        log(OkLogType.D, contents);
    }
    public static void dt(@NonNull String tag, Object... contents) {
        log(tag, OkLogType.D, contents);
    }

    public static void i(Object... contents) {
        log(OkLogType.I, contents);
    }
    public static void it(@NonNull String tag, Object... contents) {
        log(tag, OkLogType.I, contents);
    }

    public static void a(Object... contents) {
        log(OkLogType.A, contents);
    }
    public static void at(@NonNull String tag, Object... contents) {
        log(tag, OkLogType.A, contents);
    }

    public static void w(Object... contents) {
        log(OkLogType.W, contents);
    }
    public static void wt(@NonNull String tag, Object... contents) {
        log(tag, OkLogType.W, contents);
    }

    public static void e(Object... contents) {
        log(OkLogType.E, contents);
    }
    public static void et(@NonNull String tag, Object... contents) {
        log(tag, OkLogType.E, contents);
    }

    /**
     * @param type 必须是OkLogType.TYPE类型
     * @param contents 日志内容
     */
    public static void log(@OkLogType.TYPE int type, Object... contents) {
        log(OkLogManager.getInstance().getConfig().getGlobalTag(), type, contents);
    }

    /**
     * @param tag 日志标志不能为空
     * @param type 必须是OkLogType.TYPE类型
     * @param contents 日志内容
     */
    public static void log(@NonNull String tag, @OkLogType.TYPE int type, Object... contents) {
        log(OkLogManager.getInstance().getConfig(), tag, type, contents);
    }

    /**
     *
     * @param config 日志配置类不能为空
     * @param tag 日志标志不能为空
     * @param type 必须是OkLogType.TYPE类型
     * @param contents 日志内容
     */
    public static void log(@NonNull OkLogConfig config, @NonNull String tag, @OkLogType.TYPE int type, Object... contents) {
        //判断OkLog是否启用
        if(!config.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        // 判断日志信息里是否需要包含线程信息
        if(config.includeThread()) {
            // 返回正在被执行的线程的信息
            String threadInfo = OkLogConfig.OK_LOG_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }
        // 如果堆栈信息的深度大于0，说明用户需要打印堆栈信息
        if(config.stackTraceDepth() > 0) {
            // 返回一个StackTraceElement数组，并且数组中的每个元素都表示一个堆栈帧
            String stackTrace = OkLogConfig.OK_LOG_STACK_TRACE_FORMATTER.format(OkStackTraceUtil.getCroppedRealStackTrace(new Throwable().getStackTrace(), OK_LOG_PACKAGE, config.stackTraceDepth()));
//            String stackTrace = OkLogConfig.OK_LOG_STACK_TRACE_FORMATTER.format(new Throwable().getStackTrace());
            sb.append(stackTrace).append("\n");
        }
        String body = parseBody(contents, config);
        sb.append(body);

        List<OkLogPrinter> printers = config.getPrinters() != null ? Arrays.asList(config.getPrinters()) : OkLogManager.getInstance().getPrinters();
        if(printers == null) {
            return;
        }
        // 打印日志
        for (OkLogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }
    }

    /**
     * 解析日志内容
     * @param contents
     * @return
     */
    public static String parseBody(@NonNull Object[] contents, @NonNull OkLogConfig config) {
        // 不仅能实现String类型的数据信息的打印，还要能实现任何数据类型的数据信息的打印，这就需要进行对象的序列化操作
        if(config.injectJsonParser() != null) {
            config.injectJsonParser().toJson(contents);
        }

        StringBuilder sb = new StringBuilder();
        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }
        // 如果日志内容长度为0，则删除日志内容的最后一个分号
        if(sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
