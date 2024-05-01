package com.nuc.device.task.controller;

import com.nuc.device.equipment.domain.DeviceEquipment;
import com.nuc.device.equipment.service.IDeviceEquipmentService;
import com.nuc.device.record.domin.DeviceBorrowRecord;
import com.nuc.device.record.domin.DeviceBorrowRecordDTO;
import com.nuc.device.record.service.IDeviceRecordService;
import com.nuc.device.task.utils.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

/**
 * 工作台控制
 * @author mumu
 * @date 2024/4/30 22:51
 */
@Controller
@RequestMapping("/api/workspace")
public class WorkSpaceController {

    @Autowired
    private IDeviceEquipmentService deviceEquipmentService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private IDeviceRecordService deviceRecordService;

    @GetMapping()
    public String workSpace() {
        return "device/workspace/workSpace";
    }

    /**
     * 设备借用数量排行
     *
     * @return
     */
    @GetMapping("/hotBorrow")
    @ResponseBody
    public List<Map<String, Object>> rankBorrowQuantity() {
        String key = "device:hot:borrow";
        //倒序获取前5个借阅数量的设备 zrevrangeByScoreWithScores 意味着获取key 和value
        Set<ZSetOperations.TypedTuple<Object>> devices = redisUtil.zrevrangeByScoreWithScores(key, 0, 5);
        Iterator<ZSetOperations.TypedTuple<Object>> device = devices.iterator();
        List<Map<String, Object>> chartData = new ArrayList<>();
        while (device.hasNext()) {
            //value为设备id score为数量
            ZSetOperations.TypedTuple<Object> e = device.next();
            DeviceEquipment deviceEquipment = deviceEquipmentService.selectDeviceEquipmentByEquipmentId(Long.valueOf(e.getValue().toString()));
            Map<String, Object> data = new HashMap<>();
            data.put("name", deviceEquipment.getName());
            data.put("value", e.getScore());
            chartData.add(data);
        }
        return chartData;
    }

    /**
     * 设备空闲数量排行
     *
     * @return
     */
    @GetMapping("/hotIdle")
    @ResponseBody
    public List<Map<String, Object>> rankIdleQuantity() {
        String key = "device:hot:idle";
        Set<ZSetOperations.TypedTuple<Object>> hotIdles = redisUtil.zrevrangeByScoreWithScores(key, 0, 5);
        Iterator<ZSetOperations.TypedTuple<Object>> hotIdle = hotIdles.iterator();
        List<Map<String, Object>> chartData = new ArrayList<>();
        while (hotIdle.hasNext()) {
            ZSetOperations.TypedTuple<Object> e = hotIdle.next();
            DeviceEquipment deviceEquipment = deviceEquipmentService.selectDeviceEquipmentByEquipmentId(Long.valueOf(e.getValue().toString()));
            Map<String, Object> data = new HashMap<>();
            data.put("name", deviceEquipment.getName());
            data.put("value", e.getScore());
            chartData.add(data);
        }
        return chartData;
    }

    /**
     * 最近设备借用情况
     */
    @GetMapping("/recentBorrow")
    @ResponseBody
    public List<DeviceBorrowRecordDTO> recordList() {
        List<DeviceBorrowRecord> recordList = deviceRecordService.findRecentRecordList();
        List<DeviceBorrowRecordDTO> recordDTOList = new ArrayList<>();
        recordList.forEach(record -> {
            DeviceBorrowRecordDTO recordDTO = new DeviceBorrowRecordDTO();
            BeanUtils.copyProperties(record, recordDTO);
            recordDTOList.add(recordDTO);
        });
        recordDTOList.forEach(System.out::println);
        return recordDTOList;
    }
}
