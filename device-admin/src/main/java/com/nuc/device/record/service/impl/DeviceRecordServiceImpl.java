package com.nuc.device.record.service.impl;

import com.nuc.device.record.domin.DeviceBorrowRecord;
import com.nuc.device.record.mapper.DeviceBorrowRecordMapper;
import com.nuc.device.record.service.IDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO 类描述
 *
 * @author mumu
 * @date 2024/5/1 22:04
 */
@Service
public class DeviceRecordServiceImpl implements IDeviceRecordService {
    @Autowired
    DeviceBorrowRecordMapper deviceBorrowRecordMapper;
    @Override
    public List<DeviceBorrowRecord> findRecordList() {
        return  deviceBorrowRecordMapper.selectList();
    }

    @Override
    public int addRecord(DeviceBorrowRecord record) {
        return deviceBorrowRecordMapper.insert(record);
    }

    @Override
    public List<DeviceBorrowRecord> findRecentRecordList() {
        return deviceBorrowRecordMapper.selectRecentList();
    }
}
