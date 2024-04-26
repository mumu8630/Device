//package com.nuc.device.task.controller;
//
//import com.nuc.device.common.core.controller.BaseController;
//import com.nuc.device.common.core.domain.entity.SysUser;
//import com.nuc.device.system.domain.SysOperLog;
//import com.nuc.device.system.service.ISysOperLogService;
//import com.nuc.device.task.domin.DeviceUserTaskList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
//import static com.nuc.device.common.utils.ShiroUtils.getSysUser;
//
///**
// * 用户历史查询
// * @author mumu
// * @date 2024/4/26 20:02
// */
//@Controller
//@RequestMapping("/device/history")
//public class HistoryController   {
//
//    @Autowired
//    ISysOperLogService sysOperLogService;
//    @GetMapping()
//    public String history() {
//        return "/history";
//    }
//
//    @PostMapping("/list")
//    @ResponseBody
//    public List<SysOperLog> list() {
//        SysUser user = getSysUser();
//        Long userId = user.getUserId();
//        return sysOperLogService.selectOperLogList(operLog);
//    }
//
//}
