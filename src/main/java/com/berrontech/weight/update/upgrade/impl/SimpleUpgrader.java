package com.berrontech.weight.update.upgrade.impl;

import com.berrontech.weight.update.script.ScriptEngine;
import com.berrontech.weight.update.script.ScriptEngineException;
import com.berrontech.weight.update.script.ScriptEngineFactory;
import com.berrontech.weight.update.upgrade.*;
import com.berrontech.weight.update.upgrade.dto.VersionInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 20:28
 * Class Name: SimpleUpgrader
 * Author: Levent8421
 * Description:
 * 升级组件实现
 *
 * @author Levent8421
 */
@Slf4j
public class SimpleUpgrader extends AbstractUpgrader {
    private static final String SETUP_SCRIPT_NAME = "setup.groovy";
    private static final Integer SCRIPT_RES_SUCCESS = 0;
    private final ResourceHandler resourceHandler;
    private final String scriptEngineName;
    private final File tmpRootPath;

    public SimpleUpgrader(ResourceHandler resourceHandler, String scriptEngineName, File tmpRootPath) {
        this.resourceHandler = resourceHandler;
        this.scriptEngineName = scriptEngineName;
        this.tmpRootPath = tmpRootPath;
    }

    @Override
    protected VersionInfo fetchLastVersion(String currentAppVersion, String currentDbVersion) throws UpgradeException {
        return null;
    }

    @Override
    protected UpgradeResource downloadResource(VersionInfo versionInfo, File targetPath, UpgradeListener listener) throws UpgradeException {
        return null;
    }

    @Override
    protected void extractFiles(UpgradeResource resource, File target, UpgradeListener listener) throws UpgradeException {
        resourceHandler.extractFiles(resource, target, new ExtractListener() {
            @Override
            public void onStart() {
                listener.onResourceExtractBegin();
            }

            @Override
            public void onExtract(String filename) {
                listener.onResourceExtract(filename);
            }

            @Override
            public void onComplete() {
                listener.onResourceExtractCompmete();
            }

            @Override
            public void onError(Throwable err) {
                listener.onError(err, "Extract error!");
            }
        });
    }

    @Override
    protected File getTmpDir() {
        return tmpRootPath;
    }

    @Override
    protected void runScript(File workspace, UpgradeListener listener) throws UpgradeException {
        final ScriptEngine engine;
        final String workspacePath = workspace.getAbsolutePath();
        try {
            engine = ScriptEngineFactory.getScriptEngine(scriptEngineName, workspacePath);
        } catch (ScriptEngineException e) {
            listener.onError(e, "Error on create ScriptEngine with name " + scriptEngineName);
            throw new UpgradeException("Error on create ScriptEngine with name " + scriptEngineName);
        }
        engine.putValue("workspace", workspace);
        try {
            final Object scriptRes = engine.execScriptFile(SETUP_SCRIPT_NAME);
            if (!Objects.equals(scriptRes, SCRIPT_RES_SUCCESS)) {
                final UpgradeException err = new UpgradeException("Script return " + scriptRes);
                listener.onError(err, "Script return value error!");
                throw err;
            }
        } catch (ScriptEngineException e) {
            listener.onError(e, "Error on run script!");
            throw new UpgradeException("Error on run script!", e);
        }
    }
}
