package com.nuc.device.order.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.nuc.device.common.core.domain.entity.SysUser;
import com.nuc.device.equipment.domain.DeviceEquipment;
import com.nuc.device.equipment.service.IDeviceEquipmentService;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.record.domain.OrderSummary;
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

import static com.nuc.device.common.utils.ShiroUtils.getSysUser;

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
    @Autowired
    IDeviceEquipmentService deviceEquipmentService;

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
     * 归还订单信息
     */
    @RequiresPermissions("device:order:return")
    @Log(title = "归还设备", businessType = BusinessType.UPDATE)
    @PostMapping( "/return")
    @ResponseBody
    public AjaxResult returnDevice(String ids)
    {
        return  toAjax(deviceOrderService.returnDeviceByOrderIds(ids));
    }

    //TODO: 完成订单的归还 续期 以及维护
    /**
     * workspace 归还卡片相关查询
     * @param deviceOrder
     * @return
     */
    @RequiresPermissions("device:order:list")
    @PostMapping("/returnTabList")
    @ResponseBody
    public TableDataInfo returnTabList(DeviceOrder deviceOrder)
    {
        startPage();
        deviceOrder.setUserId(getSysUser().getUserId());
        deviceOrder.setStatus("已归还");
        List<DeviceOrder> list = deviceOrderService.selectDeviceOrderList(deviceOrder);
        return getDataTable(list);
    }

    /**
     * workspace 逾期卡片相关查询
     * @param deviceOrder
     * @return
     */
    @RequiresPermissions("device:order:list")
    @PostMapping("/overdueTabList")
    @ResponseBody
    public TableDataInfo overdueTabList(DeviceOrder deviceOrder)
    {
        startPage();
        deviceOrder.setUserId(getSysUser().getUserId());
        deviceOrder.setStatus("已逾期");
        List<DeviceOrder> list = deviceOrderService.selectDeviceOrderList(deviceOrder);
        return getDataTable(list);
    }
    /**
     * workspace 临期卡片相关查询
     * @param deviceOrder
     * @return
     */
    @RequiresPermissions("device:order:list")
    @PostMapping("/willOverdueTabList")
    @ResponseBody
    public TableDataInfo willOverdueTabList(DeviceOrder deviceOrder)
    {
        startPage();
        List<DeviceOrder> list = new ArrayList<>();
        OrderSummary order = deviceOrderService.sumWillOverdueQuantity(getSysUser().getUserId());
        String orderIdList = order.getOrderList();
        List<Long> orderList = Arrays.stream(orderIdList.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        orderList.forEach(item -> {
            deviceOrder.setOrderId(item);
            List<DeviceOrder> deviceOrders = deviceOrderService.selectDeviceOrderList(deviceOrder);
            list.addAll(deviceOrders);
        });

        return getDataTable(list);
    }


    /**
     * 再次借用设备
     */
    @RequiresPermissions("device:equipment:borrow")
    @GetMapping("/borrowAgain/{orderId}")
    public String borrow(@PathVariable("orderId") Long orderId, ModelMap mmap)
    {
        DeviceOrder deviceOrder = deviceOrderService.selectDeviceOrderByOrderId(orderId);
        DeviceEquipment deviceEquipment = deviceEquipmentService.selectDeviceEquipmentByEquipmentId(deviceOrder.getEquipmentId());
        mmap.put("deviceEquipment", deviceEquipment);
        return "device/equipment/borrow";
    }
    /**
     * 续期订单设备
     */
    @RequiresPermissions("device:order:renew")
    @GetMapping("/renew/{orderId}")
    public String renew(@PathVariable("orderId") Long orderId, ModelMap mmap)
    {
        DeviceOrder deviceOrder = deviceOrderService.selectDeviceOrderByOrderId(orderId);
        mmap.put("deviceOrder", deviceOrder);
        return prefix+"/renew";
    }

    /**
     * 续期订单设备 提交订单
     */
    @RequiresPermissions("device:order:renew")
    @Log(title = "订单续期", businessType = BusinessType.UPDATE)
    @PostMapping("/renew")
    @ResponseBody
    public AjaxResult renewOrder(DeviceOrder deviceOrder, Date newDeadDate)
    {
        deviceOrder.setDeadDate(newDeadDate);
        return toAjax(deviceOrderService.renewOrder(deviceOrder));
    }

}
