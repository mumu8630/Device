package com.nuc.device.maintenance.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.nuc.device.common.annotation.Excel;
import com.nuc.device.common.core.domain.BaseEntity;

/**
 * 设备维护对象 device_maintenance
 * 
 * @author mumu
 * @date 2024-05-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMaintenance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工单编号 */
    private Long workId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private Long orderId;

    /** 设备号 */
    private Long equipmentId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 提交人员 */
    @Excel(name = "提交人员")
    private String uploadUser;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadDate;

    /** 损耗信息 */
    private String lossInfo;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date manageDate;

    /** 归还数量 */
    private Integer returnNum;

    /** 维修数量 */
    @Excel(name = "维修数量")
    private Integer maintenanceNum;

    /** 维修状态 */
    @Excel(name = "维修状态")
    private String maintenanceStatus;

    /** 审批人员 */
    @Excel(name = "审批人员")
    private String manageName;


}
