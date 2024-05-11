package com.nuc.device.record.controller;

import java.util.List;

import com.nuc.device.common.core.controller.BaseController;
import com.nuc.device.record.domain.DeviceBorrowRecordDTO;
import com.nuc.device.record.service.IDeviceRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nuc.device.common.annotation.Log;
import com.nuc.device.common.enums.BusinessType;
import com.nuc.device.record.domain.DeviceBorrowRecord;
import com.nuc.device.common.core.domain.AjaxResult;
import com.nuc.device.common.utils.poi.ExcelUtil;
import com.nuc.device.common.core.page.TableDataInfo;
import com.nuc.device.common.core.page.TableDataInfo;
import java.util.ArrayList;

import static com.nuc.device.common.utils.PageUtils.startPage;

/**
 * 借用历史的控制器
 *
 * @author mumu
 * @date 2024/5/1 21:59
 */
@Controller
@RequestMapping("api/record")
public class DeviceRecordController  extends BaseController {

    @Autowired
    IDeviceRecordService deviceRecordService;

    /**
     * 最近借用记录
     * @return
     */
   @PostMapping("/list")
    public List<DeviceBorrowRecordDTO> recordList() {
       List<DeviceBorrowRecord> recordList = deviceRecordService.findRecordList();
       List<DeviceBorrowRecordDTO> recordDTOList = new ArrayList<>();
       recordList.forEach(record -> {
           DeviceBorrowRecordDTO recordDTO = new DeviceBorrowRecordDTO();
           BeanUtils.copyProperties(record, recordDTOList);
           recordDTOList.add(recordDTO);
       });
       return recordDTOList;
   }

    @RequiresPermissions("device:record:view")
    @GetMapping()
    public String record()
    {
        return  "/device/equipment/record";
    }

    /**
     * 查询历史借用列表
     */
    @RequiresPermissions("device:record:list")
    @PostMapping("/recordList")
    @ResponseBody
    public TableDataInfo list(DeviceBorrowRecord deviceBorrowRecord)
    {
        startPage();
        List<DeviceBorrowRecord> list = deviceRecordService.selectDeviceBorrowRecordList(deviceBorrowRecord);
        return getDataTable(list);
    }

    /**
     * 导出历史借用列表
     */
    @RequiresPermissions("device:record:export")
    @Log(title = "历史借用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(DeviceBorrowRecord deviceBorrowRecord)
    {
        List<DeviceBorrowRecord> list = deviceRecordService.selectDeviceBorrowRecordList(deviceBorrowRecord);
        ExcelUtil<DeviceBorrowRecord> util = new ExcelUtil<DeviceBorrowRecord>(DeviceBorrowRecord.class);
        return util.exportExcel(list, "历史借用数据");
    }

}
