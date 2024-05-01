package com.nuc.device.record.domin;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * TODO 类描述
 * @author mumu
 * @date 2024/5/1 23:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceBorrowRecord {
    /**
    * 序号
    */
    private Integer recordId;

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

    /**
    * 截至时间
    */
    private Date deadLine;

    /**
    * 归还时间
    */
    private Date returnDate;

    /**
    * 借用原因
    */
    private String borrowReason;

    /**
    * 借用数量
    */
    private Integer borrowNum;
}