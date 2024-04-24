package com.nuc.device.device.controller;

import com.nuc.device.common.core.domain.entity.SysUser;
import com.nuc.device.device.domin.DeviceUserTaskList;
import com.nuc.device.device.service.IDeviceUserTaskListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@Controller
@RequestMapping("/api")
@Slf4j
public class TaskController {

    @Autowired
    IDeviceUserTaskListService deviceUserTaskListService;

    @GetMapping ("/task")
    public String getTaskList(Model model) {
        SysUser user = getSysUser();
        Long userId = user.getUserId();
        List<DeviceUserTaskList> tasks = deviceUserTaskListService.selectByUserId(userId);
        model.addAttribute("tasks", tasks);
        return "/task";
    }
    @PostMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskId") Integer taskId,Model model) {
        int res = deviceUserTaskListService.deleteByPrimaryKey(taskId);
        //删除结果
        if (res == 1) {
            log.info("添加成功!");
        } else {
            log.info("添加失败!");
        }
        // 重定向到主页面
        return "redirect:/main";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestBody DeviceUserTaskList task) {
        task.setTaskCreateTime(new Date());
        int res = deviceUserTaskListService.insert(task);
        //查看结果
        if (res == 1) {
            log.info("添加成功!");
        } else {
            log.info("添加失败!");
        }
        // 重定向到主页面
        return "redirect:/main";
    }
    @PostMapping("/updateTaskStatus")
    public String updateTaskStatus(@RequestParam("taskId") Integer taskId) {
        int res = deviceUserTaskListService.updateByTaskId(taskId);
        //查看结果
        if (res == 1) {
            log.info("添加成功!");
        } else {
            log.info("添加失败!");
        }
        return "redirect:/main";
    }

}
