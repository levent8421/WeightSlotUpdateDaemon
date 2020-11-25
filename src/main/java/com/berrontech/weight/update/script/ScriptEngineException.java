package com.berrontech.weight.update.script;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 17:13
 * Class Name: ScriptEngineException
 * Author: Levent8421
 * Description:
 * Script Engine Exception
 *
 * @author Levent8421
 */
public class ScriptEngineException extends Exception {
    public ScriptEngineException() {
    }

    public ScriptEngineException(String message) {
        super(message);
    }

    public ScriptEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptEngineException(Throwable cause) {
        super(cause);
    }

    public ScriptEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
