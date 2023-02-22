package com.jssonok.library.util;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

public class OkViewUtil {

    /**
     * 获取指定类型的子View
     * @param group 需要查找的ViewGroup
     * @param cls 需要查找的子View的类型，如：RecyclerView.class
     * @return
     * @param <T> 指定类型的View
     */
    public static <T> T findTypeView(@Nullable ViewGroup group, Class<T> cls) {
        if(group == null) {
            return null;
        }
        // 双向队列
        Deque<View> deque = new ArrayDeque<>();
        deque.add(group);
        while (!deque.isEmpty()) {
            // 取出第一个元素
            View node = deque.removeFirst();
            // 判断该元素是否就是需要查找的子View类的实例
            if(cls.isInstance(node)) {
                // 将元素转换成子View类的实例
                return cls.cast(node);
            }else if (node instanceof ViewGroup) {
                // 如果该元素不是需要查找的子View类的实例，但该元素还是ViewGroup，则继续往下查找
                // 遍历ViewGroup所有的child，将child添加到双向队列的队尾，通过一遍遍循环遍历，直到查找到为止
                ViewGroup container = (ViewGroup) node;
                for (int i = 0, count = container.getChildCount(); i < count; i++) {
                    deque.add(container.getChildAt(i));
                }
            }
        }
        // 查找不到，则返回null
        return null;
    }
}
