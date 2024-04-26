package com.nuc.device.equipment.mapper;

import com.nuc.device.equipment.domain.DeviceEquipmentType;
import org.apache.ibatis.annotations.Mapper;


/**
 * 设备类型Mapper
 * @author mumu
 * @date 2024/4/26 13:08
 */

@Mapper
public interface DeviceEquipmentTypeMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(DeviceEquipmentType record);

    int insertSelective(DeviceEquipmentType record);

    DeviceEquipmentType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(DeviceEquipmentType record);

    int updateByPrimaryKey(DeviceEquipmentType record);
}