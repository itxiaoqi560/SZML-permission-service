package com.itxiaoqi.permissionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.itxiaoqi.permissionservice.constant.Constant;
import com.itxiaoqi.permissionservice.entity.po.UserRole;
import com.itxiaoqi.permissionservice.mapper.UserRoleMapper;
import com.itxiaoqi.permissionservice.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 超管调用：降级用户为普通角色
     * @param userId 用户id
     */
    @Override
    public int downgradeToUser(Long userId) {
        //根据用户id降级用户为普通用户，这里采用乐观锁的思想，用户当前角色为管理员才修改
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<UserRole>()
                .eq(UserRole::getRoleId,Constant.ADMIN)
                .eq(UserRole::getUserId,userId)
                .set(UserRole::getRoleId,Constant.USER);
        return userRoleMapper.update(wrapper);
    }

    /**
     * 超管调用：升级用户为管理员
     * @param userId 用户id
     */
    @Override
    public int upgradeToAdmin(Long userId) {
        //根据用户id升级用户为管理员，这里采用乐观锁的思想，用户当前角色为普通成员才修改
        LambdaUpdateWrapper<UserRole> wrapper = new LambdaUpdateWrapper<UserRole>()
                .eq(UserRole::getRoleId,Constant.USER)
                .eq(UserRole::getUserId,userId)
                .set(UserRole::getRoleId,Constant.ADMIN);
        return userRoleMapper.update(wrapper);
    }

    /**
     * 查询用户角色码
     * @param userId 用户id
     * @return 角色码
     */
    @Override
    public String getUserRoleCode(Long userId) {
        //查询角色码
        return userRoleMapper.getRoleCodeByUserId(userId);
    }

    /**
     * 绑定默认角色
     * @param userId 用户id
     */
    @Override
    public void bindDefaultRole(Long userId) {
        //封装用户角色信息
        UserRole userRole = UserRole.builder()
                .roleId(Constant.DEFAULT_ROLE)
                .userId(userId)
                .build();
        //落库用户角色信息
        userRoleMapper.insert(userRole);
    }
}
