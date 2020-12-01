package com.berrontech.weight.update.upgrade.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.berrontech.weight.update.script.ScriptEngine;
import com.berrontech.weight.update.script.ScriptEngineException;
import com.berrontech.weight.update.script.ScriptEngineFactory;
import com.berrontech.weight.update.upgrade.UpgradeException;
import com.berrontech.weight.update.upgrade.Upgrader;
import com.berrontech.weight.update.upgrade.dto.AppVersion;
import com.berrontech.weight.update.util.ZipUtils;
import com.berrontech.weight.update.util.http.HttpUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Create By Levent8421
 * Create Time: 2020/12/1 20:33
 * Class Name: SimpleUpgrader
 * Author: Levent8421
 * Description:
 * Simple Upgrader implementation
 *
 * @author Levent8421
 */
@Component
@ConfigurationProperties(prefix = "app")
@Slf4j
public class SimpleUpgrader implements Upgrader {
    private static final int CODE_SUCCESS = 200;
    private static final String CODE_NAME = "code";
    private static final String MSG_NAME = "msg";
    private static final String DATA_NAME = "data";

    private static final String SCRIPT_LOG = "log";

    private static final String SETUP_SCRIPT_NAME = "setup.groovy";
    private static final String LAST_VERSION_URL = "http://app.berrontech.com/api/open/version/_last?appKey=%s";

    @Setter
    private String appKey = "weight_app_linux";
    @Setter
    private String downloadTmpDir = "/tmp/upgrade";

    @Override
    public AppVersion getLastVersion() throws UpgradeException {
        final String apiUrl = String.format(LAST_VERSION_URL, appKey);
        final String resp;
        try {
            resp = HttpUtils.get(apiUrl);
        } catch (IOException e) {
            throw new UpgradeException("Error on get last version from [" + apiUrl + "]", e);
        }
        try {
            final JSONObject res = JSON.parseObject(resp);
            final Integer code = res.getInteger(CODE_NAME);
            if (code != CODE_SUCCESS) {
                throw new UpgradeException("Error on get last version from server, res=[" + code + "/" + res.getString(MSG_NAME) + "]");
            }
            return res.getObject(DATA_NAME, AppVersion.class);
        } catch (Exception e) {
            throw new UpgradeException("Recv a invalidate resp: " + resp, e);
        }
    }

    @Override
    public boolean shouldUpgrade(AppVersion version, Integer currentVersionCode) throws UpgradeException {
        final Integer versionCode = version.getVersionCode();
        if (versionCode == null) {
            throw new UpgradeException("Read a null versionCode from versionInfo!");
        }
        return versionCode > currentVersionCode;
    }

    @Override
    public File downloadUpgradePackage(AppVersion version) throws UpgradeException {
        final File downloadPath = new File(downloadTmpDir);
        if (!downloadPath.isDirectory()) {
            if (!downloadPath.mkdirs()) {
                throw new UpgradeException("Error on create download tmp dir:" + downloadTmpDir);
            }
        }
        final File downloadFile = new File(downloadPath, String.format("upgrade-%s.zip", version.getVersionCode()));
        if (downloadFile.isFile()) {
            if (!downloadFile.delete()) {
                throw new UpgradeException("Error on delete tmp file:" + downloadFile.getAbsolutePath());
            }
        }
        try {
            HttpUtils.download(version.getFilename(), downloadFile);
        } catch (IOException e) {
            throw new UpgradeException("Error on download upgrade file:" + version.getFilename(), e);
        }
        return downloadFile;
    }

    @Override
    public void doUpgrade(File file) throws UpgradeException {
        final String extractPathName = file.getName() + "-extract";
        final File unzipPath = new File(downloadTmpDir, extractPathName);
        if (unzipPath.exists()) {
            throw new UpgradeException("Upgrade extract path already exists:" + unzipPath.getAbsolutePath());
        }
        if (!unzipPath.mkdirs()) {
            throw new UpgradeException("Error on create unzip dir:" + unzipPath.getAbsolutePath());
        }
        try {
            ZipUtils.unzip(file, unzipPath);
        } catch (IOException e) {
            throw new UpgradeException("Error on unzip file:" + file.getAbsolutePath(), e);
        }
        final ScriptEngine scriptEngine;
        try {
            scriptEngine = ScriptEngineFactory.getScriptEngine(ScriptEngineFactory.GROOVY, unzipPath.getAbsolutePath());
            scriptEngine.putValue(SCRIPT_LOG, log);
        } catch (ScriptEngineException e) {
            throw new UpgradeException("Error on create ScriptEngine!", e);
        }
        try {
            final Object scriptRes = scriptEngine.execScriptFile(SETUP_SCRIPT_NAME);
            log.info("Script result=[{}]", scriptRes);
        } catch (ScriptEngineException e) {
            throw new UpgradeException("Error on run setup script!", e);
        }
    }
}
