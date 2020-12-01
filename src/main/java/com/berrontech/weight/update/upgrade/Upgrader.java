package com.berrontech.weight.update.upgrade;

import com.berrontech.weight.update.upgrade.dto.AppVersion;

import java.io.File;

/**
 * Create By Levent8421
 * Create Time: 2020/12/1 19:07
 * Class Name: Upgrader
 * Author: Levent8421
 * Description:
 * 升级组件
 *
 * @author Levent8421
 */
public interface Upgrader {
    /**
     * 获取最新版本
     *
     * @return app version dto
     * @throws UpgradeException e
     */
    AppVersion getLastVersion() throws UpgradeException;

    /**
     * 判断当前是否需要升级
     *
     * @param version            version dto
     * @param currentVersionCode current version
     * @return should upgrade
     * @throws UpgradeException e
     */
    boolean shouldUpgrade(AppVersion version, Integer currentVersionCode) throws UpgradeException;

    /**
     * 下载升级文件
     *
     * @param version version dto
     * @return file
     * @throws UpgradeException e
     */
    File downloadUpgradePackage(AppVersion version) throws UpgradeException;

    /**
     * 执行升级操作
     *
     * @param file 文件
     * @throws UpgradeException e
     */
    void doUpgrade(File file) throws UpgradeException;
}
