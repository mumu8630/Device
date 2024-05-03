package com.nuc.device.record.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuc.device.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * TODO 类描述
 * @author mumu
 * @date 2024/5/3 20:35
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceBorrowRecord {
    /**
    * 序号
    */
    private Long recordId;

    /**
    * 设备号
    */
    private Long equipmentId;

    /**
    * 设备名称
    */
    private String equipmentName;

    /**
    * 借用人
    */
    private String borrowUser;

    /**
    * 借用时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date borrowDate;

    /**
    * 归还状态
    */
    private Object borrowStatus;

    /**
    * 截至时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截至日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadLine;

    /**
    * 归还时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "归还日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /**
    * 借用原因
    */
    private String borrowReason;

    /**
    * 借用数量
    */
    private Integer borrowNum;

    /**
    * 订单号
    */
    private Long orderId;
}