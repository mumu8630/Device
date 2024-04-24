package com.nuc.device.device.mapper;

import com.nuc.device.device.domin.DeviceEquipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * TODO 类描述
 * @author mumu
 * @date 2024/4/24 11:20
 */

@Mapper
public interface DeviceEquipmentMapper {
    int deleteByPrimaryKey(Integer equipmentId);

    int insert(DeviceEquipment record);

    int insertSelective(DeviceEquipment record);

    DeviceEquipment selectByPrimaryKey(Integer equipmentId);

    int updateByPrimaryKeySelective(DeviceEquipment record);

    int updateByPrimaryKey(DeviceEquipment record);

    List<DeviceEquipment> selectALL();

}