package com.berrontech.weight.update.script;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 16:33
 * Class Name: ScriptEngine
 * Author: Levent8421
 * Description:
 * 脚本引擎
 *
 * @author Levent8421
 */
public interface ScriptEngine {
    /**
     * 执行脚本文件
     *
     * @param filename 脚本文件
     * @return script file
     * @throws ScriptEngineException e
     */
    Object execScriptFile(String filename) throws ScriptEngineException;

    /**
     * 重置脚本
     *
     * @throws ScriptEngineException e
     */
    void reset() throws ScriptEngineException;

    /**
     * 查找引擎中的值
     *
     * @param name 名称
     * @return 值
     * @throws ScriptEngineException e
     */
    Object findValue(String name) throws ScriptEngineException;

    /**
     * 查找引擎中的值
     *
     * @param name  名称
     * @param klass 类型
     * @param <T>   类型
     * @return 值
     * @throws ScriptEngineException e
     */
    <T> T findValue(String name, Class<T> klass) throws ScriptEngineException;

    /**
     * Put value into engine
     *
     * @param name  value name
     * @param value value
     */
    void putValue(String name, Object value);

    /**
     * 执行脚本字符串
     *
     * @param script script string
     * @return result
     * @throws ScriptEngineException e
     */
    Object execScript(String script) throws ScriptEngineException;
}
