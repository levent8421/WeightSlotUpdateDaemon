package com.berrontech.weight.update.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Create By Levent8421
 * Create Time: 2020/12/1 21:02
 * Class Name: ZipUtils
 * Author: Levent8421
 * Description:
 * Zip Utils
 *
 * @author Levent8421
 */
@Slf4j
public class ZipUtils {
    public static List<String> unzip(File zipFile, File targetPath) throws IOException {
        final FileInputStream fis = new FileInputStream(zipFile);
        final ZipInputStream zipInput = new ZipInputStream(fis);
        ZipEntry entry;
        final List<String> extractFiles = new ArrayList<>();
        try {
            while ((entry = zipInput.getNextEntry()) != null) {
                final String file = unzipEntry(zipInput, entry, targetPath);
                extractFiles.add(file);
            }
        } finally {
            try {
                zipInput.close();
            } catch (IOException e) {
                e.printStackTrace();
                // Ignore
            }
        }
        return extractFiles;
    }

    private static void copyFile(InputStream inputStream, File targetFile) throws IOException {
        try (final OutputStream outputStream = new FileOutputStream(targetFile)) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    private static void tryMkDir(File target) throws IOException {
        if (!target.mkdirs()) {
            throw new IOException("Can not make target dir [" + target.getAbsolutePath() + "]");
        }
    }

    private static String unzipEntry(ZipInputStream zipInput, ZipEntry entry, File targetPath) throws IOException {
        final String name = entry.getName();
        final File targetFile = new File(targetPath, name);
        if (entry.isDirectory()) {
            if (!targetFile.exists()) {
                tryMkDir(targetFile);
                log.info("Unzip dir: [{}]", targetFile.getAbsolutePath());
            }
        } else {
            copyFile(zipInput, targetFile);
            log.info("Unzip file: [{}]", targetFile.getAbsolutePath());
        }
        return name;
    }
}
