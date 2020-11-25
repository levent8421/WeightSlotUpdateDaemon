package com.berrontech.weight.update.upgrade;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 19:29
 * Class Name: UpgradeListener
 * Author: Levent8421
 * Description:
 * 升级进度监听
 *
 * @author Levent8421
 */
public interface UpgradeListener {
    /**
     * 取消原因：无需升级
     */
    int CANCEL_REASON_NO_UPGRADE_REQUIRED = 0x01;
    /**
     * 取消原因：用户取消
     */
    int CANCEL_REASON_USER_CANCEL = 0x01;

    /**
     * 升级开始回调
     */
    void onUpgradeStart();

    /**
     * 升级取消回调
     *
     * @param reason  取消原因
     * @param message 取消信息
     */
    void onUpgradeCancel(int reason, String message);

    /**
     * 下载进度回调
     *
     * @param state 下载状态
     * @param total 资源总字节数
     * @param pos   已下载字节数
     */
    void onDownloadProgress(int state, int total, int pos);

    /**
     * 解压开始回调
     */
    void onResourceExtractBegin();

    /**
     * 资源提取进度回调
     *
     * @param filename 文件名
     */
    void onResourceExtract(String filename);

    /**
     * 解压完成回调
     */
    void onResourceExtractCompmete();

    /**
     * 运行脚本前回调
     */
    void beforeScriptRunning();

    /**
     * 运行脚本后回调
     */
    void afterScriptRunning();

    /**
     * 升级错误回调
     *
     * @param err     错误信息
     * @param message 错误信息
     */
    void onError(Throwable err, String message);
}
