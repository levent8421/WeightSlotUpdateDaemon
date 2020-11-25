package com.berrontech.weight.update.script.groovy;

import com.berrontech.weight.update.script.ScriptEngine;
import com.berrontech.weight.update.script.ScriptEngineException;
import com.berrontech.weight.update.script.ScriptEngineFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 17:43
 * Class Name: GroovyScriptEngineImplTest
 * Author: Levent8421
 * Description:
 * Test Groovy Script Engine
 *
 * @author Levent8421
 */
public class GroovyScriptEngineImplTest {
    private ScriptEngine scriptEngine;

    @BeforeEach
    public void setup() throws ScriptEngineException {
        scriptEngine = ScriptEngineFactory.getScriptEngine(ScriptEngineFactory.GROOVY, "D:\\monolith\\workspace\\groovy");
    }

    @Test
    public void test() throws ScriptEngineException {
        scriptEngine.putValue("hello", this);
        final Object res = scriptEngine.execScriptFile("test.groovy");
        System.out.println(res);
        final Object res2 = scriptEngine.execScriptFile("test.groovy");
        System.out.println(res2);
        final Object num = scriptEngine.findValue("num");
        System.out.println("Find value = " + num + "/type=" + num.getClass().getName());
    }

    public void printTest(String arg) {
        System.out.println("Hello from java! arg=" + arg);
    }
}