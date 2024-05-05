package com.nuc.device.task.controller;

import com.nuc.device.order.domain.DeviceOrder;
import com.nuc.device.order.mapper.DeviceOrderMapper;
import com.nuc.device.order.service.IDeviceOrderService;
import com.nuc.device.record.domain.DeviceBorrowRecord;
import com.nuc.device.record.domain.DeviceBorrowRecordDTO;
import com.nuc.device.record.service.IDeviceRecordService;
import com.nuc.device.task.domin.DeviceUserTaskList;
import com.nuc.device.task.enums.TaskStatusEnum;
import com.nuc.device.task.mapper.DeviceUserTaskListMapper;
import com.nuc.device.task.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;

/**
 *
 * @author mumu
 * @date 2024/4/22 16:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TaskControllerTest {

    @Autowired
    DeviceUserTaskListMapper deviceUserTaskListMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private DeviceOrderMapper deviceOrderMapper;
    @Autowired
    IDeviceOrderService deviceOrderService;

    @Test
    void getTaskList() {
        List<DeviceUserTaskList> deviceUserTaskList = deviceUserTaskListMapper.selectByUserId(1l);
        System.out.println(deviceUserTaskList);
    }

    @Test
    void deleteTask() {
        int i = deviceUserTaskListMapper.deleteByPrimaryKey(11);
        System.out.println("**************删除结果"+i);
    }
    @Test
    void update(){
        //获取taskId的对应状态
        DeviceUserTaskList task = deviceUserTaskListMapper.selectByPrimaryKey(41);
        //若为已完成
        System.out.println(task.getTaskStatus());
        System.out.println("**************"+TaskStatusEnum.COMPLETED.getStatus()+"********");
        System.out.println(task.getTaskStatus().equals(TaskStatusEnum.COMPLETED.getStatus()));
        System.out.println("***********************************");
        System.out.println(task.getTaskStatus().toString());
        System.out.println(TaskStatusEnum.COMPLETED);
        System.out.println("枚举的tostring"+TaskStatusEnum.COMPLETED.toString());
        //if( task.getTaskStatus().equals(TaskStatusEnum.COMPLETED.getStatus())){
        //    task.setTaskStatus(TaskStatusEnum.INCOMPLETE.getStatus());
        //}else {
        //    //若为未完成
        //    task.setTaskStatus(TaskStatusEnum.COMPLETED.getStatus());
        //}
        //int i = deviceUserTaskListMapper.updateByPrimaryKey(task);
    }

    /**
     * redis数据库同步
     * device:hot:borrow   ---->  borrowed_quantity  借用量
     * device:hot:idle  -------> idle_quantity  闲置量
     */
    @Test
    void addRedis(){
            // 连接到本地的 Redis 服务
           Jedis jedis = new Jedis("localhost");

            // 从数据库中查询数据
            List<Map<String, Object>> dataList = jdbcTemplate.queryForList("SELECT * FROM device_equipment");

            // 遍历数据并存储到Redis中
            for (Map<String, Object> data : dataList) {
                String member = String.valueOf(data.get("equipment_id")); // 适当地替换成你的数据键
                String score = String.valueOf(data.get("borrowed_quantity")); // 适当地替换成你的数据值
                jedis.zadd("device:hot:borrow", Double.parseDouble(score),member); // 示例使用了Redis的String类型
            }
            // 关闭Redis连接
            jedis.close();
        }

        @Test
    void redisList(){
            String key ="device_hot_borrow";
            //倒序获取前5个借阅数量的设备 zrevrangeByScoreWithScores 意味着获取key 和value
            //Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisUtil.zrevrangeByScoreWithScores(key, 0, 4);
            Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, Double.MIN_VALUE, Double.MAX_VALUE, 0, 4);
            set.iterator().forEachRemaining(typedTuple -> {
                System.out.println("设备id："+typedTuple.getValue()+"借阅数量："+typedTuple.getScore());
            });
        }




    @Autowired
    IDeviceRecordService deviceRecordService;
    //list之间的复制
    @Test
    void recordList() {
        List<DeviceBorrowRecord> recordList = deviceRecordService.findRecentRecordList();
        List<DeviceBorrowRecordDTO> recordDTOList = new ArrayList<>();
        recordList.forEach(record -> {
            DeviceBorrowRecordDTO recordDTO = new DeviceBorrowRecordDTO();
            BeanUtils.copyProperties(record, recordDTO);
            recordDTOList.add(recordDTO);
        });
        recordList.forEach(System.out::println);
        System.out.println("=====================================");
        recordDTOList.forEach(System.out::println);
    }


}

