package com.berrontech.weight.update.upgrade;

import com.berrontech.weight.update.upgrade.dto.VersionInfo;

import java.io.File;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 19:25
 * Class Name: AbstractUpgrader
 * Author: Levent8421
 * Description:
 * 升级组件基类
 *
 * @author Levent8421
 */
public abstract class AbstractUpgrader implements Upgrader {
    private static final String EXTRACT_FILE_DIR_NAME = "extra_target";

    @Override
    public void upgrade(String currentAppVersion, String currentDbVersion, UpgradeListener listener) throws UpgradeException {
        final VersionInfo versionInfo = fetchLastVersion(currentAppVersion, currentDbVersion);
        if (!versionInfo.getUpgradeRequired()) {
            listener.onUpgradeCancel(UpgradeListener.CANCEL_REASON_NO_UPGRADE_REQUIRED, "NO UPGRADE REQUIRED!");
            return;
        }
        final File tmpPath = buildTargetDir(versionInfo.getAppVersion(), versionInfo.getDbVersion());
        final UpgradeResource resource = downloadResource(versionInfo, tmpPath, listener);
        final File extractTargetPath = new File(tmpPath, EXTRACT_FILE_DIR_NAME);
        extractFiles(resource, extractTargetPath, listener);
        listener.beforeScriptRunning();
        runScript(extractTargetPath, listener);
        listener.afterScriptRunning();
    }

    private File buildTargetDir(String appVersion, String dbVersion) throws UpgradeException {
        final File root = getTmpDir();
        final String subDir = String.format("%s-%s", appVersion, dbVersion);
        final File file = new File(root, subDir);
        if (file.exists()) {
            throw new UpgradeException("Tmp Dir [" + file.getAbsolutePath() + "] already exist!");
        }
        return file;
    }

    /**
     * 拉取最后一个版本信息
     *
     * @param currentAppVersion 当前应用版本
     * @param currentDbVersion  当前数据库版本
     * @return version info
     * @throws UpgradeException error
     */
    protected abstract VersionInfo fetchLastVersion(String currentAppVersion, String currentDbVersion) throws UpgradeException;

    /**
     * 下载升级资源
     *
     * @param versionInfo 版本信息
     * @param listener    监听器
     * @param targetPath  目标路径
     * @return resource
     * @throws UpgradeException error
     */
    protected abstract UpgradeResource downloadResource(VersionInfo versionInfo, File targetPath, UpgradeListener listener) throws UpgradeException;

    /**
     * 提取升级文件
     *
     * @param resource 资源
     * @param listener Listener
     * @param target   文件提取目录
     * @throws UpgradeException error
     */
    protected abstract void extractFiles(UpgradeResource resource, File target, UpgradeListener listener) throws UpgradeException;

    /**
     * 获取临时文件目录
     *
     * @return file
     */
    protected abstract File getTmpDir();

    /**
     * 执行升级脚本
     *
     * @param workspace 工作目录
     * @param listener  监听器
     * @throws UpgradeException error
     */
    protected abstract void runScript(File workspace, UpgradeListener listener) throws UpgradeException;
}
