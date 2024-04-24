package com.nuc.device.equipment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.nuc.device.common.annotation.Excel;
import com.nuc.device.common.core.domain.BaseEntity;

/**
 * 设备查询对象 device_equipment
 * 
 * @author mumu
 * @date 2024-04-24
 */
public class DeviceEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long equipmentId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 设备类型ID，外键关联equipment_type表 */
    private Long typeId;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String typeName;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String model;

    /** 制造商 */
    @Excel(name = "制造商")
    private String manufacturer;

    /** 购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购买日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 保修期限 */
    @Excel(name = "保修期限")
    private Long warrantyPeriod;

    /** 总数量 */
    @Excel(name = "总数量")
    private Integer totalQuantity;

    /** 借出数量 */
    @Excel(name = "借出数量")
    private Integer borrowedQuantity;

    /** 维护数量 */
    @Excel(name = "维护数量")
    private Integer maintenanceQuantity;

    /** 闲置数量 */
    @Excel(name = "闲置数量")
    private Integer idleQuantity;

    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }
    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }
    public void setWarrantyPeriod(Long warrantyPeriod) 
    {
        this.warrantyPeriod = warrantyPeriod;
    }

    public Long getWarrantyPeriod() 
    {
        return warrantyPeriod;
    }
    public void setTotalQuantity(Integer totalQuantity) 
    {
        this.totalQuantity = totalQuantity;
    }

    public Integer getTotalQuantity() 
    {
        return totalQuantity;
    }
    public void setBorrowedQuantity(Integer borrowedQuantity) 
    {
        this.borrowedQuantity = borrowedQuantity;
    }

    public Integer getBorrowedQuantity() 
    {
        return borrowedQuantity;
    }
    public void setMaintenanceQuantity(Integer maintenanceQuantity) 
    {
        this.maintenanceQuantity = maintenanceQuantity;
    }

    public Integer getMaintenanceQuantity() 
    {
        return maintenanceQuantity;
    }
    public void setIdleQuantity(Integer idleQuantity) 
    {
        this.idleQuantity = idleQuantity;
    }

    public Integer getIdleQuantity() 
    {
        return idleQuantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("equipmentId", getEquipmentId())
            .append("name", getName())
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .append("model", getModel())
            .append("manufacturer", getManufacturer())
            .append("purchaseDate", getPurchaseDate())
            .append("warrantyPeriod", getWarrantyPeriod())
            .append("totalQuantity", getTotalQuantity())
            .append("borrowedQuantity", getBorrowedQuantity())
            .append("maintenanceQuantity", getMaintenanceQuantity())
            .append("idleQuantity", getIdleQuantity())
            .toString();
    }
}
