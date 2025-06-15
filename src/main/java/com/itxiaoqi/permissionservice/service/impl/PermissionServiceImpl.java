package com.itxiaoqi.permissionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.itxiaoqi.permissionservice.constant.Constant;
import com.itxiaoqi.permissionservice.entity.po.UserRole;
import com.itxiaoqi.permissionservice.mapper.UserRoleMapper;
import com.itxiaoqi.permissionservice.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void downgradeToUser(Long userId) {
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<UserRole>()
                .eq(UserRole::getRoleId,Constant.ADMIN)
                .eq(UserRole::getUserId,userId)
                .set(UserRole::getRoleId,Constant.USER);
        userRoleMapper.update(wrapper);
    }

    @Override
    public void upgradeToAdmin(Long userId) {
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<UserRole>()
                .eq(UserRole::getRoleId,Constant.USER)
                .eq(UserRole::getUserId,userId)
                .set(UserRole::getRoleId,Constant.ADMIN);
        userRoleMapper.update(wrapper);
    }

    @Override
    public String getUserRoleCode(Long userId) {
        return userRoleMapper.getRoleCodeByUserId(userId);
    }

    @Override
    public void bindDefaultRole(Long userId) {
        UserRole userRole = UserRole.builder()
                .roleId(Constant.DEFAULT_ROLE)
                .userId(userId)
                .build();
        userRoleMapper.insert(userRole);
    }
}
