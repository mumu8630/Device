package com.nuc.device.order.controller;

import java.util.List;

import com.nuc.device.common.core.domain.entity.SysUser;
import com.nuc.device.order.service.IDeviceOrderService;
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
import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.common.core.controller.BaseController;
import com.nuc.device.common.core.domain.AjaxResult;
import com.nuc.device.common.utils.poi.ExcelUtil;
import com.nuc.device.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 * 
 * @author mumu
 * @date 2024-04-26
 */
@Controller
@RequestMapping("/device/order")
public class DeviceOrderController extends BaseController
{
    private String prefix = "device/order";

    @Autowired
    private IDeviceOrderService deviceOrderService;

    @RequiresPermissions("device:order:view")
    @Log(title = "查询订单信息", businessType = BusinessType.QUERY)
    @GetMapping()
    public String order()
    {
        return prefix + "/order";
    }

    /**
     * 查询订单信息列表
     */
    @RequiresPermissions("device:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(DeviceOrder deviceOrder)
    {
        startPage();
        deviceOrder.setUserId(getSysUser().getUserId());
        List<DeviceOrder> list = deviceOrderService.selectDeviceOrderList(deviceOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单信息列表
     */
    @RequiresPermissions("device:order:export")
    @Log(title = "导出订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DeviceOrder deviceOrder)
    {
        List<DeviceOrder> list = deviceOrderService.selectDeviceOrderList(deviceOrder);
        ExcelUtil<DeviceOrder> util = new ExcelUtil<DeviceOrder>(DeviceOrder.class);
        return util.exportExcel(list, "订单信息数据");
    }

    /**
     * 删除订单信息
     */
    @RequiresPermissions("device:order:remove")
    @Log(title = "删除订单信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(deviceOrderService.deleteDeviceOrderByOrderIds(ids));
    }
}
