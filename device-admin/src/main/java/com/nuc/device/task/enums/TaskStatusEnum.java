package com.nuc.device.task.enums;

/**
 * 小附件 task的枚举
 *
 * @author mumu
 * @date 2024/4/22 22:17
 */
public enum TaskStatusEnum {
    COMPLETED("已完成"),
    INCOMPLETE("未完成");

    private final String status;

    TaskStatusEnum(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
