package com.nuc.device.maintenance.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 设备维护对象 device_maintenance
 * @author mumu
 * @date 2024/5/11 11:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMaintenanceDTO {

    /**
     * 序号
     */
    private Long workId;
    /**
     * 设备号
     */
    private Long equipmentId;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 上传者
     */
    private String uploadUser;
    /**
     * 上传时间
     */
    private Date uploadDate;
    /**
     * 维护状态
     */
    private String maintenanceStatus;
}

