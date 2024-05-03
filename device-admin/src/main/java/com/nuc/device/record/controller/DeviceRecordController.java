package com.nuc.device.record.controller;

import com.nuc.device.record.domain.DeviceBorrowRecord;
import com.nuc.device.record.domain.DeviceBorrowRecordDTO;
import com.nuc.device.record.service.IDeviceRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 借用历史的控制器
 *
 * @author mumu
 * @date 2024/5/1 21:59
 */
@Controller
@RequestMapping("api/record")
public class DeviceRecordController {

    @Autowired
    IDeviceRecordService deviceRecordService;


   @PostMapping("/list")
    public List<DeviceBorrowRecordDTO> recordList() {
       List<DeviceBorrowRecord> recordList = deviceRecordService.findRecordList();
       List<DeviceBorrowRecordDTO> recordDTOList = new ArrayList<>();
       recordList.forEach(record -> {
           DeviceBorrowRecordDTO recordDTO = new DeviceBorrowRecordDTO();
           BeanUtils.copyProperties(record, recordDTOList);
           recordDTOList.add(recordDTO);
       });
       return recordDTOList;
   }
}
