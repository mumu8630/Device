package com.nuc.device.record.mapper;

import com.nuc.device.record.domin.DeviceBorrowRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 借用历史记录对象 device_borrow_record
 * @author mumu
 * @date 2024/5/1 23:45
 */

@Mapper
public interface DeviceBorrowRecordMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(DeviceBorrowRecord record);

    int insertSelective(DeviceBorrowRecord record);

    DeviceBorrowRecord selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(DeviceBorrowRecord record);

    int updateByPrimaryKey(DeviceBorrowRecord record);

    List<DeviceBorrowRecord> selectList();

    List<DeviceBorrowRecord> selectRecentList();
}