package com.nuc.device.task.controller;

import com.nuc.device.common.core.controller.BaseController;
import com.nuc.device.common.core.domain.entity.SysUser;
import com.nuc.device.system.domain.SysOperLog;
import com.nuc.device.system.service.ISysDictDataService;
import com.nuc.device.system.service.ISysOperLogService;
import com.nuc.device.task.domin.DeviceUserTaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nuc.device.common.utils.ShiroUtils.getSysUser;

/**
 * 用户历史查询
 * @author mumu
 * @date 2024/4/26 20:02
 */
@Controller
@RequestMapping("/device/history")
public class HistoryController   {

    @Autowired
    ISysOperLogService sysOperLogService;
    @Autowired
    ISysDictDataService    sysDictDataService;
    @GetMapping
    public String history() {
        return "/history";
    }


    @GetMapping("/list")
    @ResponseBody
    public List<Map<String,Object>> list() {
        List<SysOperLog> sysOperLogs  = sysOperLogService.selectOperLogByOperName(getSysUser().getLoginName());
        List<Map<String,Object>> historyData = LogData2HistoryData(sysOperLogs);
        return historyData;
    }

    private List<Map<String, Object>> LogData2HistoryData(List<SysOperLog> sysOperLogs) {
        List<Map<String, Object>> historyData = new ArrayList<>();

        for (SysOperLog sysOperLog : sysOperLogs) {
            String businessType =sysDictDataService.selectLogType(sysOperLog.getBusinessType());
            //提取需要的数据
            Map<String, Object> map = new HashMap<>();
            map.put("type",sysOperLog.getBusinessType());
            map.put("titlr",businessType );
            map.put("description", "用户"+sysOperLog.getOperName()+"进行了"+businessType+"操作");
            map.put("status", sysOperLog.getStatus() == 0 ? "成功" : "失败");
            map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysOperLog.getOperTime()));
            //添加到列表
            historyData.add(map);

        }
        return historyData;
    }

}
