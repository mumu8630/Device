package com.nuc.device.equipment.controller;

import java.util.*;

import com.nuc.device.order.service.IDeviceOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.nuc.device.common.annotation.Log;
import com.nuc.device.common.enums.BusinessType;
import com.nuc.device.equipment.domain.DeviceEquipment;
import com.nuc.device.equipment.service.IDeviceEquipmentService;
import com.nuc.device.common.core.controller.BaseController;
import com.nuc.device.common.core.domain.AjaxResult;
import com.nuc.device.common.utils.poi.ExcelUtil;
import com.nuc.device.common.core.page.TableDataInfo;

/**
 * 设备查询Controller
 *
 * @author mumu
 * @date 2024-04-24
 */
@Controller
@RequestMapping("/device/equipment")
public class DeviceEquipmentController extends BaseController
{
    private String prefix = "device/equipment";

    @Autowired
    private IDeviceEquipmentService deviceEquipmentService;
    @Autowired
    IDeviceOrderService deviceOrderService;

    @RequiresPermissions("device:equipment:view")
    @Log(title = "查询设备信息", businessType = BusinessType.QUERY)
    @GetMapping()
    public String equipment()
    {
            return prefix + "/equipment";
    }

    /**
     * 查询设备查询列表
     */
    @RequiresPermissions("device:equipment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DeviceEquipment deviceEquipment)
    {
        startPage();
        List<DeviceEquipment> list = deviceEquipmentService.selectDeviceEquipmentList(deviceEquipment);
        return getDataTable(list);
    }

    /**
     * 导出设备查询列表
     */
    @RequiresPermissions("device:equipment:export")
    @Log(title = "导出设备信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DeviceEquipment deviceEquipment)
    {
        List<DeviceEquipment> list = deviceEquipmentService.selectDeviceEquipmentList(deviceEquipment);
        ExcelUtil<DeviceEquipment> util = new ExcelUtil<DeviceEquipment>(DeviceEquipment.class);
        return util.exportExcel(list, "设备查询数据");
    }

    /**
     * 新增设备
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备
     */
    @RequiresPermissions("device:equipment:add")
    @Log(title = "新增设备信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DeviceEquipment deviceEquipment)
    {
        return toAjax(deviceEquipmentService.insertDeviceEquipment(deviceEquipment));
    }

    /**
     * 修改设备
     */
    @RequiresPermissions("device:equipment:edit")
    @GetMapping("/edit/{equipmentId}")
    public String edit(@PathVariable("equipmentId") Long equipmentId, ModelMap mmap)
    {
        DeviceEquipment deviceEquipment = deviceEquipmentService.selectDeviceEquipmentByEquipmentId(equipmentId);
        mmap.put("deviceEquipment", deviceEquipment);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备
     */
    @RequiresPermissions("device:equipment:edit")
    @Log(title = "修改并保存设备信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DeviceEquipment deviceEquipment)
    {
        return toAjax(deviceEquipmentService.updateDeviceEquipment(deviceEquipment));
    }

    /**
     * 删除设备
     */
    @RequiresPermissions("device:equipment:remove")
    @Log(title = "删除设备信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(deviceEquipmentService.deleteDeviceEquipmentByEquipmentIds(ids));
    }

    /**
     * 借用设备
     */
    @RequiresPermissions("device:equipment:borrow")
    @GetMapping("/borrow/{equipmentId}")
    public String borrow(@PathVariable("equipmentId") Long equipmentId, ModelMap mmap)
    {
        DeviceEquipment deviceEquipment = deviceEquipmentService.selectDeviceEquipmentByEquipmentId(equipmentId);
        mmap.put("deviceEquipment", deviceEquipment);
        return prefix + "/borrow";
    }
    /**
     * 借用设备保存 主要！！！
     * 扣库存 加订单 扣缓存 加缓存 加历史订单
     */
    @RequiresPermissions("device:equipment:borrow")
    @Log(title = "借用设备", businessType = BusinessType.BORROW)
    @PostMapping("/borrow")
    @ResponseBody
    public AjaxResult borrowSave(Integer needQuantity,
                                 DeviceEquipment deviceEquipment,
                                 String needReason,
                                 Date deadDate)
    {
        return toAjax(deviceEquipmentService.borrowDevice(deviceEquipment, needQuantity, needReason,deadDate));
    }
}
