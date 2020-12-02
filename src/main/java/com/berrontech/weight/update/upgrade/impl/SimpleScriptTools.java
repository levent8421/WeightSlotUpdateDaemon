package com.berrontech.weight.update.upgrade.impl;

import com.berrontech.weight.update.script.ScriptEngineException;
import com.berrontech.weight.update.upgrade.ScriptTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Create By Levent8421
 * Create Time: 2020/12/2 13:46
 * Class Name: SimpleScriptTools
 * Author: Levent8421
 * Description:
 * Script Tools
 *
 * @author Levent8421
 */
@Component
@Slf4j
public class SimpleScriptTools implements ScriptTools {
    private static final Runtime RUNTIME = Runtime.getRuntime();

    @Override
    public void mv(String source, String target) throws IOException {
        final File sFile = new File(source);
        if (!sFile.exists()) {
            throw new IOException("File not find:" + sFile.getAbsolutePath());
        }
        if (!sFile.renameTo(new File(target))) {
            throw new IOException("Error on rename file [" + source + "] to [" + target + "]");
        }
        log.debug("Rename file [{}] to [{}]", source, target);
    }

    @Override
    public void chmod(String file, boolean readable, boolean writable, boolean executable) throws IOException {
        final File f = new File(file);
        if (!f.exists()) {
            throw new FileNotFoundException("Can not find file:" + f.getAbsolutePath());
        }
        if (!f.setReadable(readable)) {
            throw new IOException(String.format("Can not set readable [%s] for file [%s]", readable, f.getAbsoluteFile()));
        }
        if (!f.setWritable(writable)) {
            throw new IOException(String.format("Can not set writable [%s] for file [%s]", writable, f.getAbsoluteFile()));
        }
        if (!f.setExecutable(executable)) {
            throw new IOException(String.format("Can not set executable [%s] for file [%s]", executable, f.getAbsoluteFile()));
        }

        log.debug("chmod file [{}] [{}, {}, {}]", file, readable, writable, executable);
    }

    @Override
    public void delete(String file, boolean recursion) throws IOException {
        final File f = new File(file);
        if (f.isDirectory() && recursion) {
            recursionDelete(f);
        } else {
            if (!f.delete()) {
                throw new IOException("Error on delete file:" + f.getAbsolutePath());
            }
        }
        log.debug("Remove file:[{}]", f.getAbsolutePath());
    }

    private void recursionDelete(File file) throws IOException {
        log.debug("Delete file:[{}]", file.getAbsolutePath());
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File f : files) {
                recursionDelete(f);
            }
            if (!file.delete()) {
                throw new IOException("Error on delete file:" + file.getAbsolutePath());
            }
        } else {
            if (!file.delete()) {
                throw new IOException("Error on delete file:" + file.getAbsolutePath());
            }
        }
    }

    @Override
    public void exec(String cmd) throws ScriptEngineException {
        log.debug("Run cmd: [{}]", cmd);
        try {
            final Process p = RUNTIME.exec(cmd);
            p.waitFor();
            p.destroy();
        } catch (IOException e) {
            throw new ScriptEngineException("Error on run cmd:" + cmd, e);
        } catch (InterruptedException e) {
            throw new ScriptEngineException("Error on wait cmd exit:" + cmd, e);
        }
    }

    @Override
    public void mkdirs(String dir) throws IOException {
        log.debug("Mkdirs : [{}]", dir);
        final File file = new File(dir);
        if (!file.mkdirs()) {
            throw new IOException("Error on mkdir:" + file.getAbsolutePath());
        }
    }
}
