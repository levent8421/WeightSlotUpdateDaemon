package com.berrontech.weight.update.upgrade.zip;

import com.berrontech.weight.update.upgrade.ExtractListener;
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
        final List<String> res = zipResourceHandler.extractFiles(resource, target, new ExtractListener() {
            @Override
            public void onStart() {
                System.out.println("Start");
            }

            @Override
            public void onExtract(String filename) {
                System.out.println("Extra:" + filename);
            }

            @Override
            public void onComplete() {
                System.out.println("Complete");
            }

            @Override
            public void onError(Throwable err) {
                System.out.println("Error");
                err.printStackTrace();
            }
        });
        System.out.println(res);
    }
}