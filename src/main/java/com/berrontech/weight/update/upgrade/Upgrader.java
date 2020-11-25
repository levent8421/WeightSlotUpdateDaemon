package com.berrontech.weight.update.upgrade;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 19:14
 * Class Name: Upgrader
 * Author: Levent8421
 * Description:
 * 软件升级组件
 *
 * @author Levent8421
 */
public interface Upgrader {
    /**
     * 执行升级
     *
     * @param currentAppVersion AppVersion
     * @param currentDbVersion  db Version
     * @param listener          Upgrade Listener
     * @throws UpgradeException Error
     */
    void upgrade(String currentAppVersion, String currentDbVersion, UpgradeListener listener) throws UpgradeException;
}
