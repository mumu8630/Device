package com.nuc.device.record.service.impl;

import com.nuc.device.record.domain.DeviceBorrowRecord;
import com.nuc.device.record.mapper.DeviceBorrowRecordMapper;
import com.nuc.device.record.service.IDeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 历史记录表的服务实现类
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
        return deviceBorrowRecordMapper.insertDeviceBorrowRecord(record);
    }

    @Override
    public List<DeviceBorrowRecord> findRecentRecordList() {
        return deviceBorrowRecordMapper.selectRecentList();
    }

    @Override
    public int returnDeviceByOrderId(Long orderId) {
        return deviceBorrowRecordMapper.updateDeviceBorrowRecordByOrderId(orderId);
    }

    @Override
    public int updateRecordStatus(Long orderId, String status) {
        DeviceBorrowRecord record = new DeviceBorrowRecord();
        record.setOrderId(orderId);
        List<DeviceBorrowRecord> records = deviceBorrowRecordMapper.selectDeviceBorrowRecordList(record);
        for (DeviceBorrowRecord deviceBorrowRecord : records) {
            deviceBorrowRecord.setBorrowStatus(status);
            return deviceBorrowRecordMapper.updateDeviceBorrowRecord(deviceBorrowRecord);
        }
        return 1;
    }



    @Override
    public int updateDeviceBorrowRecord(DeviceBorrowRecord record) {
        return deviceBorrowRecordMapper.updateDeviceBorrowRecord(record);
    }
}
