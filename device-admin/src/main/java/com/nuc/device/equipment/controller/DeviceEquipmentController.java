package com.nuc.device.equipment.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequiresPermissions("device:equipment:view")
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
    @Log(title = "设备查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DeviceEquipment deviceEquipment)
    {
        List<DeviceEquipment> list = deviceEquipmentService.selectDeviceEquipmentList(deviceEquipment);
        ExcelUtil<DeviceEquipment> util = new ExcelUtil<DeviceEquipment>(DeviceEquipment.class);
        return util.exportExcel(list, "设备查询数据");
    }

    /**
     * 新增设备查询
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备查询
     */
    @RequiresPermissions("device:equipment:add")
    @Log(title = "设备查询", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DeviceEquipment deviceEquipment)
    {
        return toAjax(deviceEquipmentService.insertDeviceEquipment(deviceEquipment));
    }

    /**
     * 修改设备查询
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
     * 修改保存设备查询
     */
    @RequiresPermissions("device:equipment:edit")
    @Log(title = "设备查询", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DeviceEquipment deviceEquipment)
    {
        return toAjax(deviceEquipmentService.updateDeviceEquipment(deviceEquipment));
    }

    /**
     * 删除设备查询
     */
    @RequiresPermissions("device:equipment:remove")
    @Log(title = "设备查询", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(deviceEquipmentService.deleteDeviceEquipmentByEquipmentIds(ids));
    }

    @GetMapping("/findAllDevice")
    @ResponseBody
    public List<Map<String, Object>> findAllDevice() {
        List<DeviceEquipment> allDevice = deviceEquipmentService.findAllDevice();
        List<Map<String, Object>> chartData = Date2Echarts(allDevice);
        return chartData;
    }

    private List<Map<String, Object>> Date2Echarts(List<DeviceEquipment> allDevice) {
        List<Map<String, Object>> chartData = new ArrayList<>();
        for (DeviceEquipment equipment : allDevice) {
            // 提取需要的信息
            Integer borrowedQuantity = equipment.getBorrowedQuantity();
            String name = equipment.getName();

            // 创建适合 ECharts 使用的格式
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("value", borrowedQuantity);
            dataPoint.put("name", name);

            // 添加到列表中
            chartData.add(dataPoint);
        }
        return chartData;
    }
}
