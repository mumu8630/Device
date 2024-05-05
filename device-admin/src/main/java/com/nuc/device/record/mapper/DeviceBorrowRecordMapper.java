package com.nuc.device.record.mapper;

import java.util.List;
import com.nuc.device.record.domain.DeviceBorrowRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 历史借用Mapper接口
 *
 * @author ruoyi
 * @date 2024-05-03
 */
@Mapper
public interface DeviceBorrowRecordMapper
{
    /**
     * 查询历史借用
     *
     * @param recordId 历史借用主键
     * @return 历史借用
     */
    public DeviceBorrowRecord selectDeviceBorrowRecordByRecordId(Long recordId);

    /**
     * 查询历史借用列表
     *
     * @param deviceBorrowRecord 历史借用
     * @return 历史借用集合
     */
    public List<DeviceBorrowRecord> selectDeviceBorrowRecordList(DeviceBorrowRecord deviceBorrowRecord);

    /**
     * 新增历史借用
     *
     * @param deviceBorrowRecord 历史借用
     * @return 结果
     */
    public int insertDeviceBorrowRecord(DeviceBorrowRecord deviceBorrowRecord);

    /**
     * 修改历史借用
     *
     * @param deviceBorrowRecord 历史借用
     * @return 结果
     */
    public int updateDeviceBorrowRecord(DeviceBorrowRecord deviceBorrowRecord);

    /**
     * 删除历史借用
     *
     * @param recordId 历史借用主键
     * @return 结果
     */
    public int deleteDeviceBorrowRecordByRecordId(Long recordId);

    /**
     * 批量删除历史借用
     *
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDeviceBorrowRecordByRecordIds(String[] recordIds);

    List<DeviceBorrowRecord> selectList();

    List<DeviceBorrowRecord> selectRecentList();

    int updateDeviceBorrowRecordByOrderId(Long orderId);
}
