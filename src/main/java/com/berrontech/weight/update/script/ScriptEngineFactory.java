package com.berrontech.weight.update.script;

import com.berrontech.weight.update.script.groovy.GroovyScriptEngineImpl;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 16:44
 * Class Name: ScriptEngineFactory
 * Author: Levent8421
 * Description:
 * ScriptEngineFactory
 *
 * @author Levent8421
 */
public class ScriptEngineFactory {
    public static final String GROOVY = "groovy";
    public static final String PYTHON = "python";
    public static final String JAVA_SCRIPT = "javascript";


    public static ScriptEngine getScriptEngine(String engineName, String workspace) throws ScriptEngineException {
        switch (engineName.toLowerCase()) {
            case GROOVY:
                return buildGroovyScriptEngine(workspace);
            case PYTHON:
                return buildPythonScriptEngine(workspace);
            case JAVA_SCRIPT:
                return buildJavaScriptEngine(workspace);
            default:
                throw new IllegalArgumentException("Can not find engine for name " + engineName);
        }
    }

    private static ScriptEngine buildPythonScriptEngine(String workspace) {
        throw new IllegalArgumentException("Unsupported python script engine");
    }

    private static ScriptEngine buildJavaScriptEngine(String workspace) {
        throw new IllegalArgumentException("Unsupported java script engine");
    }

    private static ScriptEngine buildGroovyScriptEngine(String workspace) throws ScriptEngineException {
        return new GroovyScriptEngineImpl(workspace);
    }
}
