package com.nuc.device.device.domin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;


/**
 * 表：任务列表表，用于存储用户的任务信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceUserTaskList {
    /**
     * 任务id
     */
    private Integer taskId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * 任务状态 默认未完成
     */
    private Object taskStatus;


    private Date taskCreateTime;

    //@JsonIgnore // 该注解表示不返回该字段
    @JsonInclude(JsonInclude.Include.NON_NULL) // 该注解表示不返回null字段
    private List<DeviceUserTaskList> deviceUserTaskLists;


}