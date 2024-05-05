package com.nuc.device.task.exception;

/**
 * TODO 类描述
 *
 * @author mumu
 * @date 2024/5/5 16:56
 */
public class DBException extends RuntimeException{
    public DBException(String message) {
        super(message);
    }
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
