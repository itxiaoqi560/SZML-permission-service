package com.itxiaoqi.permissionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itxiaoqi.permissionservice.entity.po.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    String getRoleCodeByUserId(Long userId);
}
