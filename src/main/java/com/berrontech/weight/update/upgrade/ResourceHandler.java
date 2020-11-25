package com.berrontech.weight.update.upgrade;

import java.io.File;
import java.util.List;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 18:14
 * Class Name: ResourceHandler
 * Author: Levent8421
 * Description:
 * 资源处理器
 *
 * @author Levent8421
 */
public interface ResourceHandler {
    /**
     * 提取文件
     *
     * @param resource 资源
     * @param target   提取目标目录
     * @param listener 监听器
     * @return 提取到的文件列表
     */
    List<String> extractFiles(UpgradeResource resource, File target, ExtractListener listener) throws UpgradeException;
}
