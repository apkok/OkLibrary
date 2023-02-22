package com.jssonok.library.log;

/**
 * 裁剪堆栈信息的工具类
 */
public class OkStackTraceUtil {

    /**
     * 获取忽略包名并裁剪之后的堆栈信息
     * @param callStack
     * @param ignorePackage
     * @param maxDepth
     * @return
     */
    public static StackTraceElement[] getCroppedRealStackTrace(StackTraceElement[] callStack, String ignorePackage, int maxDepth) {
        return cropStackTrace(getRealStackTrace(callStack, ignorePackage), maxDepth);
    }

    /**
     * 获取除忽略包名之外的堆栈信息
     * @param callStack 原始堆栈信息
     * @param ignorePackage 需要忽略的包名
     * @return
     */
    private static StackTraceElement[] getRealStackTrace(StackTraceElement[] callStack, String ignorePackage) {
        // 默认忽略的深度为0
        int ignoreDepth = 0;
        // 原始堆栈信息的长度
        int allDepth = callStack.length;
        // 类名
        String className;
        // 遍历堆栈（不要堆栈信息中前一部分的com.jssonok.library.log内容）
        for(int i = allDepth - 1; i >= 0; i--) {
            // 获取堆栈信息中的包名
            className = callStack[i].getClassName();
            // 判断是否属于忽略的部分
            if(ignorePackage != null && className.startsWith(ignorePackage)) {
                // 计算需要忽略堆栈信息的深度
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }

    /**
     * 裁剪堆栈信息
     * @param callStack 原始堆栈信息
     * @param maxDepth 最大深度
     * @return
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callStack, int maxDepth) {
        // 获取堆栈信息实际的长度
        int realDepth = callStack.length;
        if(maxDepth > 0) {
            // 如果堆栈信息实际的长度大于最大深度，则取最大深度；反之取堆栈信息实际的长度
            realDepth = Math.min(realDepth, maxDepth);
        }
        // 存放最终需要返回的堆栈信息的数组
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, 0, realStack, 0, realDepth);
        return realStack;
    }
}
