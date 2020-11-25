package com.berrontech.weight.update.upgrade.dto;

import lombok.Data;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 19:41
 * Class Name: VersionInfo
 * Author: Levent8421
 * Description:
 * 版本信息
 *
 * @author Levent8421
 */
@Data
public class VersionInfo {
    /**
     * 是否需要升级
     */
    private Boolean upgradeRequired;
    /**
     * 应用版本
     */
    private String appVersion;
    /**
     * 数据库版本
     */
    private String dbVersion;
    /**
     * 文件下载路径
     */
    private String resourceFileUrl;
}
