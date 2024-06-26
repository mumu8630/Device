package com.nuc.device.task.service;

import com.nuc.device.task.domin.DeviceUserTaskList;

import java.util.List;

/**
 * 用户任务列表的service
 * @author mumu
 * @date 2024/4/22 14:37
 */
public interface IDeviceUserTaskListService {
//    查询所有任务
    List<DeviceUserTaskList> selectByUserId(Long userId);
    //删除任务
    int deleteByPrimaryKey(Integer taskId);
    //新增任务
    int insert(DeviceUserTaskList record);
    //更新任务状态
    int updateByTaskId(Integer teskId);
}
