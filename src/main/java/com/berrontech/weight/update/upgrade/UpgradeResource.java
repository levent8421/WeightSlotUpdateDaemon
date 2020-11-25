package com.berrontech.weight.update.upgrade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 18:17
 * Class Name: UpgradeResource
 * Author: Levent8421
 * Description:
 * 升级资源包
 *
 * @author Levent8421
 */
public class UpgradeResource {
    /**
     * 文件路径
     */
    private final File file;

    public UpgradeResource(File file) {
        this.file = file;
    }

    /**
     * Get input stream
     *
     * @return stream
     * @throws IOException IOE
     */
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }
}
