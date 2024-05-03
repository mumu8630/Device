package com.nuc.device.order.domain;

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
 * 订单信息对象 device_order
 * 
 * @author mumu
 * @date 2024-04-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 用户ID */
    private Long userId;

    /** 设备ID */
    private Long equipmentId;
    /** 设备名称 */
    private String equipmentName;
    /** 设备类型名称 */
    private String typeName;
    /** 借用缘由 */
    private String reason;
    /** 借用数量 */
    private Integer borrowNum;
    /** 截至日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截至日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadDate;

    /** 借用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date borrowDate;

    /** 归还日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "归还日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /** 状态 */
    @Excel(name = "状态")
    private String status;


}
