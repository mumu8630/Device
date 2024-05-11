package com.nuc.device.maintenance.controller;

import java.util.Date;
import java.util.List;

import com.nuc.device.common.annotation.DataScope;
import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.record.domain.DeviceBorrowRecord;
import com.nuc.device.record.service.IDeviceRecordService;
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
import com.nuc.device.maintenance.domain.DeviceMaintenance;
import com.nuc.device.maintenance.service.IDeviceMaintenanceService;
import com.nuc.device.common.core.controller.BaseController;
import com.nuc.device.common.core.domain.AjaxResult;
import com.nuc.device.common.utils.poi.ExcelUtil;
import com.nuc.device.common.core.page.TableDataInfo;

/**
 * 设备维护Controller
 * 
 * @author mumu
 * @date 2024-05-07
 */
@Controller
@RequestMapping("/device/maintenance")
public class DeviceMaintenanceController extends BaseController
{
    private String prefix = "device/maintenance";

    @Autowired
    private IDeviceMaintenanceService deviceMaintenanceService;

    @RequiresPermissions("device:maintenance:view")
    @GetMapping()
    public String maintenance()
    {
        return prefix + "/maintenance";
    }

    /**
     * 查询设备维护列表
     */
    @RequiresPermissions("device:maintenance:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DeviceMaintenance deviceMaintenance)
    {
        startPage();
        List<DeviceMaintenance> list = deviceMaintenanceService.selectDeviceMaintenanceList(deviceMaintenance);
        return getDataTable(list);
    }

    /**
     * 导出设备维护列表
     */
    @RequiresPermissions("device:maintenance:export")
    @Log(title = "设备维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DeviceMaintenance deviceMaintenance)
    {
        List<DeviceMaintenance> list = deviceMaintenanceService.selectDeviceMaintenanceList(deviceMaintenance);
        ExcelUtil<DeviceMaintenance> util = new ExcelUtil<DeviceMaintenance>(DeviceMaintenance.class);
        return util.exportExcel(list, "设备维护数据");
    }

    /**
     * 新增设备维护
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存设备维护
     */
    @RequiresPermissions("device:maintenance:add")
    @Log(title = "设备维护", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(DeviceMaintenance deviceMaintenance)
    {
        return toAjax(deviceMaintenanceService.insertDeviceMaintenance(deviceMaintenance));
    }

    /**
     * 修改设备维护
     */
    @RequiresPermissions("device:maintenance:edit")
    @GetMapping("/edit/{workId}")
    public String edit(@PathVariable("workId") Long workId, ModelMap mmap)
    {
        DeviceMaintenance deviceMaintenance = deviceMaintenanceService.selectDeviceMaintenanceByWorkId(workId);
        mmap.put("deviceMaintenance", deviceMaintenance);
        return prefix + "/edit";
    }

    /**
     * 修改保存设备维护
     */
    @RequiresPermissions("device:maintenance:edit")
    @Log(title = "设备维护", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(DeviceMaintenance deviceMaintenance)
    {
        return toAjax(deviceMaintenanceService.updateDeviceMaintenance(deviceMaintenance));
    }

    /**
     * 删除设备维护
     */
    @RequiresPermissions("device:maintenance:remove")
    @Log(title = "设备维护", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(deviceMaintenanceService.deleteDeviceMaintenanceByWorkIds(ids));
    }

    /**
     * 设备维护处理
     */
    @RequiresPermissions("device:maintenance:solve")
    @GetMapping("/solve/{workId}")
    @DataScope(deptAlias = "d", userAlias = "u")
    public String solve(@PathVariable("workId") Long workId, ModelMap mmap)
    {
        DeviceMaintenance deviceMaintenance = deviceMaintenanceService.selectDeviceMaintenanceByWorkId(workId);
        mmap.put("deviceMaintenance", deviceMaintenance);
        return prefix + "/solve";
    }
    /**
     * 审核修改设备维护
     */
    @RequiresPermissions("device:maintenance:solve")
    @Log(title = "审批设备维护", businessType = BusinessType.UPDATE)
    @PostMapping("/solve")
    @ResponseBody
    @DataScope(deptAlias = "d", userAlias = "u")
    public AjaxResult solve(DeviceMaintenance deviceMaintenance)
    {

        return toAjax(deviceMaintenanceService.updateSolve(deviceMaintenance));
    }
    /**
     * workspace正在维护设备列表
     */
    @RequiresPermissions("device:maintenance:list")
    @PostMapping("/underMaintenanceList")
    @ResponseBody
    public TableDataInfo underMaintenanceList(DeviceMaintenance DeviceMaintenance)
    {
        startPage();
        DeviceMaintenance.setMaintenanceStatus("待处理");
        List<DeviceMaintenance> deviceMaintenances = deviceMaintenanceService.selectDeviceMaintenanceList(DeviceMaintenance);
        return getDataTable(deviceMaintenances);
    }

    /**
     * workspace已维护设备列表
     */
    @RequiresPermissions("device:maintenance:list")
    @PostMapping("/alreadyMaintenanceList")
    @ResponseBody
    public TableDataInfo alreadyMaintenanceList(DeviceMaintenance DeviceMaintenance)
    {
        startPage();
        DeviceMaintenance.setMaintenanceStatus("已处理");
        List<DeviceMaintenance> deviceMaintenances = deviceMaintenanceService.selectDeviceMaintenanceList(DeviceMaintenance);
        return getDataTable(deviceMaintenances);
    }
}
