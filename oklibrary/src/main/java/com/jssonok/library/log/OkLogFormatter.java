package com.jssonok.library.log;

/**
 * 日志格式化接口
 */
public interface OkLogFormatter<T> {
    String format(T data);
}
