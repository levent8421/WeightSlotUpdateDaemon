package com.berrontech.weight.update.upgrade;

/**
 * Create By Levent8421
 * Create Time: 2020/11/25 18:29
 * Class Name: UpgradeException
 * Author: Levent8421
 * Description:
 * 升级错误
 *
 * @author Levent8421
 */
public class UpgradeException extends Exception {
    public UpgradeException() {
    }

    public UpgradeException(String message) {
        super(message);
    }

    public UpgradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpgradeException(Throwable cause) {
        super(cause);
    }

    public UpgradeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
