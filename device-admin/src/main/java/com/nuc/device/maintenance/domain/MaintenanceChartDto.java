package com.nuc.device.maintenance.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * 折线图数据DTO
 *
 * @author mumu
 * @date 2024/5/11 13:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceChartDto {
    private Date mainDate;
    private String typeName;
    private Integer totalMaintenanceCount;
}
