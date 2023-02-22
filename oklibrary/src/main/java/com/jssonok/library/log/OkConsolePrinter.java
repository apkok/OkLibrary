package com.jssonok.library.log;

import static com.jssonok.library.log.OkLogConfig.MAX_LEN;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * 控制台打印器
 */
public class OkConsolePrinter implements OkLogPrinter {

    @Override
    public void print(@NonNull OkLogConfig config, int level, String tag, @NonNull String printString) {
        // 获取长度
        int len = printString.length();
        // 获取行数
        int countOfSub = len / MAX_LEN;
        // 多行信息打印
        if(countOfSub > 0) {
            // 标识位
            int index = 0;
            // 行数循环
            for(int i = 0; i < countOfSub; i++) {
                // 完整一行的数据打印
                Log.println(level, tag, printString.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            // 多余行数的数据打印
            if(index != len) {
                Log.println(level, tag, printString.substring(index, len));
            }
        }else {
            // 不足一行信息打印
            Log.println(level, tag, printString);
        }
    }
}
