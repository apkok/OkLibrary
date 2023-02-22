package com.jssonok.library.log;

/**
 * 堆栈信息的格式化器
 */
public class OkLogStackTraceFormatter implements OkLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] stackTrace) {
        // 在使用StringBuilder实例的时候，你不需要关心它为其存储的字符串分配了多大的内存，它会自动为字符串创建足够的内存。
        //
        // 其Capacity属性表明了一个StringBuilder实例最多可以存储多少个字符，当存储的字符所需的空间大于这个数的时候，StringBuilder会自动增大内存，增加Capacity。
        //
        // 而StringBuilder实例的length属性则表示当前存储的字符串的长度。
        //
        // 默认情况下，Capacity属性初始值为16，当需要更多空间时它就会翻倍。

        //创建初始容量为128字符大小的StringBuilder
        StringBuilder sb = new StringBuilder(128);
        if(stackTrace == null || stackTrace.length == 0) {
            return null;
        }else if(stackTrace.length == 1) {
            return "\t- " + stackTrace[0].toString();
        }else {
            for (int i = 0, len = stackTrace.length; i < len; i++) {
                if(i == 0) {
                    sb.append("stackTrace: \n");
                }
                if(i != len - 1) {
                    sb.append("\t├ ");
                    sb.append(stackTrace[i].toString());
                    sb.append("\n");
                }else {
                    sb.append("\t└ ");
                    sb.append(stackTrace[i].toString());
                }
            }
            return sb.toString();
        }
    }
}
