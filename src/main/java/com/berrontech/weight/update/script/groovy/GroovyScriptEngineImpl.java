package com.berrontech.weight.update.script.groovy;

import com.berrontech.weight.update.script.ScriptEngine;
import com.berrontech.weight.update.script.ScriptEngineException;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.IOException;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 16:43
 * Class Name: GroovyScriptEngine
 * Author: Levent8421
 * Description:
 * GroovyScriptEngine
 *
 * @author Levent8421
 */
public class GroovyScriptEngineImpl implements ScriptEngine {
    private GroovyScriptEngine engine;
    private final String workspace;
    private final Binding globalBinding;

    public GroovyScriptEngineImpl(String workspace) throws ScriptEngineException {
        globalBinding = new Binding();
        this.workspace = workspace;
        setupEngine();
    }

    private void setupEngine() throws ScriptEngineException {
        try {
            engine = new GroovyScriptEngine(this.workspace);
        } catch (IOException e) {
            throw new ScriptEngineException("Error on setup GroovyScriptEngine!", e);
        }
    }

    @Override
    public Object execScriptFile(String filename) throws ScriptEngineException {
        try {
            return engine.run(filename, globalBinding);
        } catch (ResourceException e) {
            throw new ScriptEngineException("Error on load script resource!", e);
        } catch (ScriptException e) {
            throw new ScriptEngineException(String.format("Error on run script [%s]", filename), e);
        }
    }

    @Override
    public void reset() throws ScriptEngineException {
        if (engine == null) {
            return;
        }
        globalBinding.getVariables().clear();
        setupEngine();
    }

    @Override
    public Object findValue(String name) {
        final Object var = globalBinding.getVariable(name);
        return var == null ? globalBinding.getProperty(name) : var;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T findValue(String name, Class<T> klass) throws ScriptEngineException {
        final Object var = findValue(name);
        if (var == null) {
            return null;
        }
        if (klass.isInstance(var)) {
            return (T) var;
        }
        throw new ScriptEngineException(String.format("Can not cast value [%s/%s] ro [%s]", var, var.getClass().getName(), klass.getName()));
    }

    @Override
    public void putValue(String name, Object value) {
        globalBinding.setVariable(name, value);
    }

    @Override
    public Object execScript(String script) {
        throw new UnsupportedOperationException("Unsupported exec script string!");
    }
}
