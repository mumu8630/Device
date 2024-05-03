package com.nuc.device.record.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 借用历史记录对象 device_borrow_record
 * @author mumu
 * @date 2024/5/1 21:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceBorrowRecordDTO {
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
    private Date borrowDate;

    /**
    * 归还状态
    */
    private Object borrowStatus;


}