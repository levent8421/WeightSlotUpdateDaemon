package com.berrontech.weight.update.upgrade.dto;

import lombok.Data;

import java.util.Date;

/**
 * Create By Levent8421
 * Create Time: 2020/11/28 11:37
 * Class Name: AppPackage
 * Author: Levent8421
 * Description:
 * Application package definition
 *
 * @author Levent8421
 */
@Data
public class AppPackage {
    private Integer id;
    private Date createTime;
    private Date updateTime;
    /**
     * Application name
     */
    private String name;
    /**
     * Application package file storage dir name
     */
    private String dirName;
    /**
     * Description text
     */
    private String description;
    /**
     * Creator Id ;
     */
    private Integer creatorId;
    /**
     * Application platform
     */
    private Integer platform;
}
