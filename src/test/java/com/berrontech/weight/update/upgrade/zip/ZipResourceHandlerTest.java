package com.berrontech.weight.update.upgrade.zip;

import com.berrontech.weight.update.upgrade.UpgradeException;
import com.berrontech.weight.update.upgrade.UpgradeResource;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class ZipResourceHandlerTest {
    private final ZipResourceHandler zipResourceHandler = new ZipResourceHandler();

    @Test
    public void testExtractFiles() throws UpgradeException {
        final UpgradeResource resource = new UpgradeResource(new File("D:\\monolith\\星巴克重力货道\\server\\upgrade-0.1.25\\upgrade-0.1.25.zip"));
        final File target = new File("D:\\monolith\\星巴克重力货道\\server\\upgrade-0.1.25\\target");
        final List<String> res = zipResourceHandler.extractFiles(resource, target);
        System.out.println(res);
    }
}