package com.nuc.device.task.controller;

import static com.nuc.device.common.utils.ShiroUtils.getSysUser;

import com.nuc.device.common.utils.DateUtils;
import com.nuc.device.equipment.domain.DeviceEquipment;
import com.nuc.device.equipment.service.IDeviceEquipmentService;
import com.nuc.device.maintenance.domain.DeviceMaintenance;
import com.nuc.device.maintenance.domain.DeviceMaintenanceDTO;
import com.nuc.device.maintenance.domain.MaintenanceChartDto;
import com.nuc.device.maintenance.service.IDeviceMaintenanceService;
import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.record.domain.DeviceBorrowRecord;
import com.nuc.device.record.domain.DeviceBorrowRecordDTO;
import com.nuc.device.record.service.IDeviceRecordService;
import com.nuc.device.task.domin.BorrowDateTimes;
import com.nuc.device.task.utils.RedisUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工作台控制
 * @author mumu
 * @date 2024/4/30 22:51
 */
@Controller
@RequestMapping("/api/workspace")
public class WorkSpaceController {

    private String prefix = "device/workspace/ordertab";

    @Autowired
    private IDeviceEquipmentService deviceEquipmentService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private IDeviceRecordService deviceRecordService;
    @Autowired
    IDeviceOrderService deviceOrderService;

    @Autowired
    IDeviceMaintenanceService deviceMaintenanceService;

    @GetMapping()
    public String workSpace(Model model) {
        //借用数量
        Integer borrow = deviceEquipmentService.selectBorrowQuantity();
        model.addAttribute("borrowQuantity", borrow);
        model.addAttribute("borrowProp",percent(borrow));
        //空闲数量
        Integer idle = deviceEquipmentService.selectIdleQuantity();
        model.addAttribute("idleQuantity",idle);
        model.addAttribute("idleProp",percent(idle));
        //维护数量
        Integer maintenance = deviceEquipmentService.selectMaintenanceQuantity();
        model.addAttribute("maintenanceQuantity",maintenance);
        model.addAttribute("maintenanceProp",percent(maintenance));
        //用户借用数量
        model.addAttribute("userBorrow",deviceOrderService.sumBorrowQuantity(getSysUser().getUserId()));
        //用户归还数量
        model.addAttribute("userReturn",deviceOrderService.sumReturnQuantity(getSysUser().getUserId()));
        //用户逾期数量
        model.addAttribute("userOverdue",deviceOrderService.sumOverdueQuantity(getSysUser().getUserId()));
        //用户即将到期
        model.addAttribute("userWillOverdue",deviceOrderService.sumWillOverdueQuantity(getSysUser().getUserId()));
        //用户最新借用信息
        DeviceOrder order = deviceOrderService.selectNewBorrowOrder(getSysUser().getUserId());
        //if(order ==null){
        //   order = deviceOrderService.initOrder(getSysUser().getUserId());
        //}
        String borrowDate = new SimpleDateFormat("yyyy-MM-dd").format(order.getBorrowDate());
        model.addAttribute("order",order);
        model.addAttribute("borrowDate",borrowDate);
        //借用时长
        int diffDays = DateUtils.differentDaysByMillisecond(order.getDeadDate(), order.getBorrowDate());
        model.addAttribute("diffDays",diffDays);
        //未归还数量
        model.addAttribute("userUnReturn",deviceOrderService.sumUnReturnQuantity(getSysUser().getUserId()));
        //用户维护记录
        model.addAttribute("userMaintenance",deviceOrderService.sumMaintenanceQuantity(getSysUser().getUserId()));
        //维修统计数量 --->维修中的设备数量 工单
        model.addAttribute("workMaintenanceQuantity",deviceMaintenanceService.sumMaintenanceQuantity());
        //维修中的工单数
        model.addAttribute("workQuantity",deviceMaintenanceService.sumWorkQuantity());

        return "device/workspace/workSpace";
    }

    private Object percent(Integer num) {
        // 获取总数量
        Integer total = deviceEquipmentService.selectTotalQuantity();
        // 计算百分比
        int percentage = Math.round((float)num /total * 100);
        return percentage;
    }

    /**
     * 设备借用数量排行
     *
     * @return
     */
    @GetMapping("/hotBorrow")
    @ResponseBody
    public List<Map<String, Object>> rankBorrowQuantity() {
        DeviceOrder order = deviceOrderService.selectNewBorrowOrder(getSysUser().getUserId());
        if (order == null) {
            //初始化用户借用信息
            deviceOrderService.initOrder(getSysUser().getUserId());
        }
        String key = "device:hot:borrow";
        //倒序获取前5个借阅数量的设备 zrevrangeByScoreWithScores 意味着获取key 和value
        Set<ZSetOperations.TypedTuple<Object>> devices = redisUtil.zrevrangeByScoreWithScores(key, 0, 5);
        Iterator<ZSetOperations.TypedTuple<Object>> device = devices.iterator();
        List<Map<String, Object>> chartData = new ArrayList<>();
        while (device.hasNext()) {
            //value为设备id score为数量
            ZSetOperations.TypedTuple<Object> e = device.next();
            DeviceEquipment deviceEquipment = deviceEquipmentService.selectDeviceEquipmentByEquipmentId(Long.valueOf(e.getValue().toString()));
            Map<String, Object> data = new HashMap<>();
            data.put("name", deviceEquipment.getName());
            data.put("value", e.getScore());
            chartData.add(data);
        }
        return chartData;
    }

    /**
     * 设备空闲数量排行
     *
     * @return
     */
    @GetMapping("/hotIdle")
    @ResponseBody
    public List<Map<String, Object>> rankIdleQuantity() {
        String key = "device:hot:idle";
        Set<ZSetOperations.TypedTuple<Object>> hotIdles = redisUtil.zrevrangeByScoreWithScores(key, 0, 5);
        Iterator<ZSetOperations.TypedTuple<Object>> hotIdle = hotIdles.iterator();
        List<Map<String, Object>> chartData = new ArrayList<>();
        while (hotIdle.hasNext()) {
            ZSetOperations.TypedTuple<Object> e = hotIdle.next();
            Long equipmentId = Long.valueOf(e.getValue().toString());
            DeviceEquipment deviceEquipment = deviceEquipmentService.selectDeviceEquipmentByEquipmentId(equipmentId);
            Map<String, Object> data = new HashMap<>();
            data.put("name", deviceEquipment.getName());
            data.put("value", e.getScore());
            chartData.add(data);
        }
        return chartData;
    }

    /**
     * 最近设备借用情况
     */
    @GetMapping("/recentBorrow")
    @ResponseBody
    public List<DeviceBorrowRecordDTO> recordList() {
        List<DeviceBorrowRecord> recordList = deviceRecordService.findRecentRecordList();
        List<DeviceBorrowRecordDTO> recordDTOList = new ArrayList<>();
        recordList.forEach(record -> {
            DeviceBorrowRecordDTO recordDTO = new DeviceBorrowRecordDTO();
            BeanUtils.copyProperties(record, recordDTO);
            recordDTOList.add(recordDTO);
        });
        return recordDTOList;
    }
    /**
     * 设备最近维护情况
     */
    @GetMapping("/recentWork")
    @ResponseBody
    public List<DeviceMaintenanceDTO> recentWork() {
        List<DeviceMaintenance> recentWorkList = deviceMaintenanceService.findRecentWork();
        List<DeviceMaintenanceDTO> maintenanceDTOList = new ArrayList<>();
        recentWorkList.forEach(work -> {
            DeviceMaintenanceDTO maintenanceDTO = new DeviceMaintenanceDTO();
            BeanUtils.copyProperties(work, maintenanceDTO);
            maintenanceDTOList.add(maintenanceDTO);
        });
        return maintenanceDTOList;
    }
    /**
     * 柱状图数据
     * 借用 维护 闲置数量
     */
    @GetMapping("/barChart")
    @ResponseBody
    public Map<String,Map<String, Object>> barChart() {
        //借用数量
        List<Map<String, Object>> borrowMapList = deviceEquipmentService.sumBorrowQuantity();

        List<Map<String,Object>> maintenanceMapList = deviceEquipmentService.sumMaintenanceQuantity();

        List<Map<String,Object>> idleMapList = deviceEquipmentService.sumIdleQuantity();

        // 创建一个用于记录结果的Map
        Map<String, Map<String, Object>> resultMap = new HashMap<>();

        // 合并数据
        mergeData(borrowMapList, "借用", resultMap);
        mergeData(maintenanceMapList, "维护", resultMap);
        mergeData(idleMapList, "闲置", resultMap);

    return resultMap;
}
    /**
     * 折线图数据
     * 借用 维护 闲置数量
     */
    @GetMapping("/lineChart")
    @ResponseBody
    public List<MaintenanceChartDto> lineChart() {
        List<MaintenanceChartDto> maintenanceChartDtos =deviceMaintenanceService.findLineMaintenanceChart();
        return maintenanceChartDtos;
    }
    /**
     * 易损坏设备种类排行
     * @return
     */
    @GetMapping("/hotMaintenance")
    @ResponseBody
    public List<Map<String, Object>> rankMaintenanceQuantity() {
        String key = "device:hot:maintenance";
        List<Map<String, Object>> chartData = new ArrayList<>();
        // 使用zrevrangeWithScores获取前5个设备
        Set<ZSetOperations.TypedTuple<Object>> maintenances = redisUtil.zrevrangeByScoreWithScores(key, 0, 5);
        for (ZSetOperations.TypedTuple<Object> maintenance : maintenances) {
            Map data = new HashMap();
            String typeName = deviceEquipmentService.selectTypeByTypeId(Long.valueOf(maintenance.getValue().toString()));
            data.put("name", typeName);
            data.put("value", maintenance.getScore());
            chartData.add(data);
        }
        return chartData;
    }

    // 合并数据的方法
    private static void mergeData(List<Map<String, Object>> dataList, String status, Map<String, Map<String, Object>> resultMap) {
        for (Map<String, Object> map : dataList) {
            String typeName = (String) map.get("type_name");
            Object count =  map.get("count");
            // 如果结果集中已经有该type_name的记录，则更新对应状态的数量，否则新增记录
            if (resultMap.containsKey(typeName)) {
                Map<String, Object> statusMap = resultMap.get(typeName);
                statusMap.put(status, count);
            } else {
                Map<String, Object> statusMap = new HashMap<>();
                statusMap.put(status, count);
                resultMap.put(typeName, statusMap);
            }
        }
    }

    /**
     * 截至日期仪表盘
     * 获取从现在起到最小截至日期的剩余天数
     */
    @GetMapping("/deadLine")
    @ResponseBody
    public DeadlineResponse  getDeadLine(){
        //获取借用记录
        Long userId = getSysUser().getUserId();
        Long orderId = deviceOrderService.findMinDeadLine(userId);
        DeviceOrder deviceOrder = deviceOrderService.selectDeviceOrderByOrderId(orderId);
        //获取当前时间
        Date now = new Date();
        //获取截至日期
        Date deadDate = deviceOrder.getDeadDate();
        //计算剩余天数
        Long days = deadDate.getTime() - now.getTime();
        //转换为分钟 四舍五入
        int min = Math.round(days / 60000);
        return new DeadlineResponse(min);
    }
    static class DeadlineResponse {
        private final int minutes;

        public DeadlineResponse(int minutes) {
            this.minutes = minutes;
        }

        public int getMinutes() {
            return minutes;
        }
    }

    /**
     * 用户借用次数 统计图
     */
    @GetMapping("/borrowTimes")
    @ResponseBody
    public  List<BorrowDateTimes> borrowTimes() {
        List<BorrowDateTimes> borrowTimes = deviceOrderService.selectBorrowTimes(getSysUser().getUserId());
        return borrowTimes;
    }

    /**
     * 借用订单
     */
    @GetMapping("/borrowTab")
    public String borrowTab() {
        return prefix+"/borrowTab";
    }
    /**
     * tab2标签页卡片归还订单
     */
    @GetMapping("returnTab")
    public String returnTab() {
        return prefix+"/returnTab";
    }
    /**
     * tab2标签页卡片逾期订单
     */
    @GetMapping("overdueTab")
    @RequiresPermissions("device:order:list")
    public String overdue() {
        return prefix+"/overdueTab";
    }

    /**
     * tab2标签页卡片临期订单
     */

    @GetMapping("willOverdueTab")
    @RequiresPermissions("device:order:list")
    public String willOverdueTab() {
        return prefix+"/willOverdueTab";
    }


    @GetMapping("maintenanceTab")
    @RequiresPermissions("device:order:list")
    public String maintenanceTab() {
        return prefix+"/maintenanceTab";
    }

    @GetMapping("underMaintenanceTab")
    public String underMaintenanceTab() {
        return prefix+"/underMaintenanceTab";
    }

    @GetMapping("alreadyMaintenanceTab")
    public String alreadyMaintenanceTab() {
        return prefix+"/alreadyMaintenanceTab";
    }

}
