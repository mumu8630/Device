package com.nuc.device.task.controller;

import com.nuc.device.common.core.domain.entity.SysUser;
import com.nuc.device.task.domin.DeviceUserTaskList;
import com.nuc.device.task.service.IDeviceUserTaskListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.nuc.device.common.utils.ShiroUtils.getSysUser;

/**
 * 此类中编写用户首页的任务列表
 *
 * @author mumu
 * @date 2024/4/22 14:17
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TaskController {

    @Autowired
    IDeviceUserTaskListService deviceUserTaskListService;

    @GetMapping ("/tasks")
    public List<DeviceUserTaskList> getTaskList(Model model) {
        SysUser user = getSysUser();
        Long userId = user.getUserId();
        List<DeviceUserTaskList> tasks = deviceUserTaskListService.selectByUserId(userId);
        return tasks;
    }
    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskId") Integer taskId) {
        int res = deviceUserTaskListService.deleteByPrimaryKey(taskId);
        //删除结果
        if (res == 1) {
            return "删除成功!";
        } else {
            return "删除失败!";
        }
    }


    @PostMapping("/addTask")
    public String addTask(@RequestBody DeviceUserTaskList task) {
        task.setTaskCreateTime(new Date());
        task.setUserId(getSysUser().getUserId());
        int res = deviceUserTaskListService.insert(task);
        //查看结果
        if (res == 1) {
            return "添加成功!";
        } else {
            return "添加失败";
        }

    }
    @PostMapping("/updateTaskStatus")
    public String updateTaskStatus(@RequestParam("taskId") Integer taskId) {
        int res = deviceUserTaskListService.updateByTaskId(taskId);
        //查看结果
        if (res == 1) {
           return "更新成功!";
        } else {
            return "更新失败";
        }
    }

}
