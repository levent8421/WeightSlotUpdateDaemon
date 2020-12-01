package com.berrontech.weight.update.upgrade.impl;

import com.berrontech.weight.update.upgrade.dto.AppVersion;
import org.junit.jupiter.api.Test;

import java.io.File;

public class SimpleUpgraderTest {
    @Test
    public void test() throws Exception {
        final SimpleUpgrader simpleUpgrader = new SimpleUpgrader();
        simpleUpgrader.setAppKey("weight_app_linux");
        simpleUpgrader.setDownloadTmpDir("D:/tmp/upgrade");
        final AppVersion lastVersion = simpleUpgrader.getLastVersion();
        if (simpleUpgrader.shouldUpgrade(lastVersion, -1)) {
            final File file = simpleUpgrader.downloadUpgradePackage(lastVersion);
            simpleUpgrader.doUpgrade(file);
        }
    }
}