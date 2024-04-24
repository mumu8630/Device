package com.nuc.device.device.mapper;

import com.nuc.device.device.domin.DeviceUserTaskList;
import com.nuc.device.device.enums.TaskStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * TODO 类描述
 * @author mumu
 * @date 2024/4/22 18:16
 */

@Mapper
public interface DeviceUserTaskListMapper {
    int deleteByPrimaryKey(Integer taskId);

    int insert(DeviceUserTaskList record);

    int insertSelective(DeviceUserTaskList record);

    DeviceUserTaskList selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(DeviceUserTaskList record);

    int updateByPrimaryKey(DeviceUserTaskList record);

    List<DeviceUserTaskList> selectByUserId(@Param("userId") Long userId);

}