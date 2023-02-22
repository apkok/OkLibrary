package com.jssonok.library.log;

/**
 * 线程信息的格式化器
 */
public class OkLogThreadFormatter implements OkLogFormatter<Thread> {

    @Override
    public String format(Thread data) {
        return "Thread:" + data.getName();
    }
}
