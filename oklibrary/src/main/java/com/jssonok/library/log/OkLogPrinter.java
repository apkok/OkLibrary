package com.jssonok.library.log;

import androidx.annotation.NonNull;

public interface OkLogPrinter {
    void print(@NonNull OkLogConfig config, int level, String tag, @NonNull String printString);
}
