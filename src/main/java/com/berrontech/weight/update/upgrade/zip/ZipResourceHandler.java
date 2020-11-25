package com.berrontech.weight.update.upgrade.zip;

import com.berrontech.weight.update.upgrade.ExtractListener;
import com.berrontech.weight.update.upgrade.ResourceHandler;
import com.berrontech.weight.update.upgrade.UpgradeException;
import com.berrontech.weight.update.upgrade.UpgradeResource;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 18:22
 * Class Name: ZipResourceHandler
 * Author: Levent8421
 * Description:
 * zip资源处理器
 *
 * @author Levent8421
 */
public class ZipResourceHandler implements ResourceHandler {

    private String unzipEntry(ZipInputStream zipInput, ZipEntry entry, File targetPath) throws IOException {
        final String name = entry.getName();
        final File targetFile = new File(targetPath, name);
        if (entry.isDirectory()) {
            if (!targetFile.exists()) {
                tryMkDir(targetFile);
            }
        } else {
            copyFile(zipInput, targetFile);
        }
        return name;
    }

    private void copyFile(InputStream inputStream, File targetFile) throws IOException {
        try (final OutputStream outputStream = new FileOutputStream(targetFile)) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    private void tryMkDir(File target) throws IOException {
        if (!target.mkdirs()) {
            throw new IOException("Can not make target dir [" + target.getAbsolutePath() + "]");
        }
    }

    private void checkTarget(File target) throws UpgradeException {
        if (target.exists()) {
            throw new UpgradeException(String.format("Extract target path [%s] already exist!", target.getAbsoluteFile()));
        }
    }

    @Override
    public List<String> extractFiles(UpgradeResource resource, File target, ExtractListener listener) throws UpgradeException {
        listener.onStart();
        try {
            checkTarget(target);
        } catch (UpgradeException e) {
            listener.onError(e);
            throw e;
        }
        try {
            tryMkDir(target);
        } catch (IOException e) {
            listener.onError(e);
            throw new UpgradeException("Error on mkdir tmp dir!", e);
        }
        final InputStream input;
        try {
            input = resource.getInputStream();
        } catch (IOException e) {
            listener.onError(e);
            throw new UpgradeException("Error on get InputStream!", e);
        }
        final ZipInputStream zipInput = new ZipInputStream(input);
        ZipEntry entry;
        final List<String> extractFiles = new ArrayList<>();
        try {
            while ((entry = zipInput.getNextEntry()) != null) {
                final String file = unzipEntry(zipInput, entry, target);
                extractFiles.add(file);
                listener.onExtract(file);
            }
        } catch (IOException e) {
            listener.onError(e);
            throw new UpgradeException("Error on extract!", e);
        } finally {
            try {
                zipInput.close();
            } catch (IOException e) {
                e.printStackTrace();
                // Ignore
            }
        }
        listener.onComplete();
        return extractFiles;
    }
}
