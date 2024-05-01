package com.nuc.device.task.exception;

/**
 * redis操作异常
 * @author mumu
 * @date 2024/5/1 23:04
 */
public class RedisOperationException extends RuntimeException{
    public RedisOperationException(String message) {
        super(message);
    }

    public RedisOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
