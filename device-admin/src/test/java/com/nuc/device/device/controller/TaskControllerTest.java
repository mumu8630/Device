package com.nuc.device.device.controller;

import com.nuc.device.device.domin.DeviceUserTaskList;
import com.nuc.device.device.enums.TaskStatusEnum;
import com.nuc.device.device.mapper.DeviceUserTaskListMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * TODO 类描述
 *
 * @author mumu
 * @date 2024/4/22 16:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TaskControllerTest {

    @Autowired
    DeviceUserTaskListMapper deviceUserTaskListMapper;
    @Test
    void getTaskList() {
        List<DeviceUserTaskList> deviceUserTaskList = deviceUserTaskListMapper.selectByUserId(1);
        System.out.println(deviceUserTaskList);
    }

    @Test
    void deleteTask() {
        int i = deviceUserTaskListMapper.deleteByPrimaryKey(11);
        System.out.println("**************删除结果"+i);
    }
    @Test
    void update(){
        //获取taskId的对应状态
        DeviceUserTaskList task = deviceUserTaskListMapper.selectByPrimaryKey(41);
        //若为已完成
        System.out.println(task.getTaskStatus());
        System.out.println("**************"+TaskStatusEnum.COMPLETED.getStatus()+"********");
        System.out.println(task.getTaskStatus().equals(TaskStatusEnum.COMPLETED.getStatus()));
        System.out.println("***********************************");
        System.out.println(task.getTaskStatus().toString());
        System.out.println(TaskStatusEnum.COMPLETED);
        System.out.println("枚举的tostring"+TaskStatusEnum.COMPLETED.toString());
        //if( task.getTaskStatus().equals(TaskStatusEnum.COMPLETED.getStatus())){
        //    task.setTaskStatus(TaskStatusEnum.INCOMPLETE.getStatus());
        //}else {
        //    //若为未完成
        //    task.setTaskStatus(TaskStatusEnum.COMPLETED.getStatus());
        //}
        //int i = deviceUserTaskListMapper.updateByPrimaryKey(task);
    }

    }