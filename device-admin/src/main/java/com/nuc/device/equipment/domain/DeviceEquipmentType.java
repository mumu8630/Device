package com.nuc.device.equipment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 设备类型
 * @author mumu
 * @date 2024/4/26 13:08
 */

/**
    * 表：设备类型表，用于存储实验室设备的类型信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEquipmentType {
    /**
    * 类型ID
    */
    private Integer typeId;

    private String typeName;
}