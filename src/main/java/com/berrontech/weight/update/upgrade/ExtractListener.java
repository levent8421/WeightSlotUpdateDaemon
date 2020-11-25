package com.berrontech.weight.update.upgrade;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 20:31
 * Class Name: ExtractListener
 * Author: Levent8421
 * Description:
 * 文件提取监听器
 *
 * @author Levent8421
 */
public interface ExtractListener {
    /**
     * 开始提起回调
     */
    void onStart();

    /**
     * 提取回调
     *
     * @param filename 文件名称
     */
    void onExtract(String filename);

    /**
     * 提取完成回调
     */
    void onComplete();

    /**
     * 提取错误回调
     *
     * @param err 错误信息
     */
    void onError(Throwable err);
}
