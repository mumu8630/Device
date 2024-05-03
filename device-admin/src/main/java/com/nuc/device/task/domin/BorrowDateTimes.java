package com.nuc.device.task.domin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 借用时间统计
 *折线图使用属性
 * @author mumu
 * @date 2024/5/4 12:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDateTimes {

    @JsonFormat(pattern = "MM-dd")
    private Date borrowDate;

    private Integer borrowCount;
}
