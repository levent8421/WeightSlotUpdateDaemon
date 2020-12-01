package com.berrontech.weight.update.upgrade.dto;

import lombok.Data;

import java.util.Date;

/**
 * Create By Levent8421
 * Create Time: 2020/11/28 17:08
 * Class Name: AppVersion
 * Author: Levent8421
 * Description:
 * Application version entity
 *
 * @author Levent8421
 */
@Data
public class AppVersion {
    public static final int STATE_NOT_AVAILABLE = 0x00;
    public static final int STATE_AVAILABLE = 0x01;

    private Integer id;
    private Date createTime;
    private Date updateTime;
    /**
     * App id
     */
    private Integer appId;
    /**
     * APP
     */
    private AppPackage app;
    /**
     * Publisher id
     */
    private Integer publisherId;
    /**
     * version code
     */
    private Integer versionCode;
    /**
     * Version name
     */
    private String versionName;
    /**
     * Release note
     */
    private String releaseNote;
    /**
     * Description
     */
    private String description;
    /**
     * FileName
     */
    private String filename;
    /**
     * State
     */
    private Integer state;
}
