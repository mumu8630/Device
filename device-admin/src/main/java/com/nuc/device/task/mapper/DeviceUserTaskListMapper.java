package com.nuc.device.task.mapper;

import com.nuc.device.task.domin.DeviceUserTaskList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 小附件 task的mapper
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