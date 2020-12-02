package com.berrontech.weight.update.upgrade;

import com.berrontech.weight.update.script.ScriptEngineException;

import java.io.IOException;

/**
 * Create By Levent8421
 * Create Time: 2020/12/2 13:36
 * Class Name: ScriptTools
 * Author: Levent8421
 * Description:
 * Script Tools
 *
 * @author Levent8421
 */
public interface ScriptTools {
    /**
     * 重命名、移动文件
     *
     * @param source 源文件名
     * @param target 目标文件名
     * @throws IOException e
     */
    void mv(String source, String target) throws IOException;

    /**
     * 修改文件权限
     *
     * @param file       file
     * @param readable   可读
     * @param writable   可写
     * @param executable 可执行
     */
    void chmod(String file, boolean readable, boolean writable, boolean executable) throws IOException;

    /**
     * 删除文件、目录
     *
     * @param file      文件
     * @param recursion 递归删除子文件
     */
    void delete(String file, boolean recursion) throws IOException;

    /**
     * 执行命令
     *
     * @param cmd 命令
     * @throws ScriptEngineException e
     */
    void exec(String cmd) throws ScriptEngineException;

    /**
     * Make dirs
     *
     * @param dir dir
     * @throws IOException e
     */
    void mkdirs(String dir) throws IOException;
}
