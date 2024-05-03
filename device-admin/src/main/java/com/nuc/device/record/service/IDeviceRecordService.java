package com.nuc.device.record.service;

import com.nuc.device.record.domain.DeviceBorrowRecord;

import java.util.List;

/**
 * 借用记录的服务接口
 * @author mumu
 * @date 2024/5/1 22:04
 */
public interface IDeviceRecordService {

    List<DeviceBorrowRecord> findRecordList();
    int addRecord(DeviceBorrowRecord record);
    List<DeviceBorrowRecord> findRecentRecordList();

}
