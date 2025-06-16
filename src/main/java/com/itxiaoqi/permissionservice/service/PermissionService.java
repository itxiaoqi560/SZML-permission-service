package com.itxiaoqi.permissionservice.service;


public interface PermissionService{
    /**
     * 超管调用：降级用户为普通角色
     * @param userId 用户id
     */
    void downgradeToUser(Long userId);

    /**
     * 超管调用：升级用户为管理员
     * @param userId 用户id
     */
    void upgradeToAdmin(Long userId);

    /**
     * 查询用户角色码
     * @param userId 用户id
     * @return 角色码
     */
    String getUserRoleCode(Long userId);

    /**
     * 绑定默认角色
     * @param userId 用户id
     */
    void bindDefaultRole(Long userId);
}
