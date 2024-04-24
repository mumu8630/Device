package com.nuc.device.device.domin;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * TODO 类描述
 * @author mumu
 * @date 2024/4/24 11:20
 */

/**
    * 表：设备表，用于存储实验室设备的基本信息
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEquipment {
    /**
    * 设备ID
    */
    private Integer equipmentId;

    /**
    * 设备名称
    */
    private String name;

    /**
    * 设备类型ID，外键关联equipment_type表
    */
    private Integer typeId;

    /**
    * 设备型号
    */
    private String model;

    /**
    * 制造商
    */
    private String manufacturer;

    /**
    * 购买日期
    */
    private Date purchaseDate;

    /**
    * 保修期限
    */
    private Integer warrantyPeriod;

    /**
    * 设备总数量，默认为0
    */
    private Integer totalQuantity;

    /**
    * 已借出数量，默认为0
    */
    private Integer borrowedQuantity;

    /**
    * 维护数量，默认为0
    */
    private Integer maintenanceQuantity;

    private Integer idleQuantity;
}