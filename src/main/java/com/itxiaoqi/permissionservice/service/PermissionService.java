package com.itxiaoqi.permissionservice.service;


public interface PermissionService{
    void downgradeToUser(Long userId);

    void upgradeToAdmin(Long userId);

    String getUserRoleCode(Long userId);

    void bindDefaultRole(Long userId);
}
