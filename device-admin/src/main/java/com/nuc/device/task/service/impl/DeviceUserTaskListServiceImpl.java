package com.nuc.device.task.service.impl;

import com.nuc.device.task.domin.DeviceUserTaskList;
import com.nuc.device.task.enums.TaskStatusEnum;
import com.nuc.device.task.mapper.DeviceUserTaskListMapper;
import com.nuc.device.task.service.IDeviceUserTaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 接口IDeviceUserTaskListService的实现类
 * @author mumu
 * @date 2024/4/22 14:40
 */
@Service
public class DeviceUserTaskListServiceImpl implements IDeviceUserTaskListService {
    @Autowired
    private DeviceUserTaskListMapper deviceUserTaskListMapper;

    @Override
    public List<DeviceUserTaskList> selectByUserId(Long userId) {
        return deviceUserTaskListMapper.selectByUserId(userId);
    }

    @Override
    public int deleteByPrimaryKey(Integer taskId) {
        return deviceUserTaskListMapper.deleteByPrimaryKey(taskId);
    }

    //新增任务
    @Override
    public int insert(DeviceUserTaskList record) {
        return deviceUserTaskListMapper.insert(record);
    }

    //更新任务状态
    @Override
    public int updateByTaskId(Integer teskId) {
        //获取taskId的对应状态
        DeviceUserTaskList task = deviceUserTaskListMapper.selectByPrimaryKey(teskId);
        //若为已完成
        if(task.getTaskStatus().equals(TaskStatusEnum.COMPLETED.getStatus())){
            task.setTaskStatus(TaskStatusEnum.INCOMPLETE.getStatus());
        }else {
            //若为未完成
            task.setTaskStatus(TaskStatusEnum.COMPLETED.getStatus());
        }
        return deviceUserTaskListMapper.updateByPrimaryKey(task);
    }


}
