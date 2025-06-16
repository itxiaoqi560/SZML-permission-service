package com.itxiaoqi.permissionservice.controller;

import com.itxiaoqi.permissionservice.anno.Loggable;
import com.itxiaoqi.permissionservice.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 绑定默认角色
     *
     * @param userId 用户id
     */
    @PostMapping("/bindDefaultRole")
    @Loggable(value = "绑定默认角色")
    public void bindDefaultRole(@RequestParam Long userId) {
        log.info("绑定默认角色：{}", userId);
        permissionService.bindDefaultRole(userId);
    }


    /**
     * 查询用户角色码
     *
     * @param userId 用户id
     * @return 角色码
     */
    @GetMapping("/getUserRoleCode")
    @Loggable(value = "查询用户角色码")
    public String getUserRoleCode(@RequestParam Long userId) {
        log.info("查询用户角色码：{}", userId);
        return permissionService.getUserRoleCode(userId);
    }

    /**
     * 超管调用：升级用户为管理员
     *
     * @param userId 用户id
     */
    @PutMapping("/upgradeToAdmin")
    @Loggable(value = "超管调用：升级用户为管理员")
    public void upgradeToAdmin(@RequestParam Long userId) {
        log.info("超管调用：升级用户为管理员：{}", userId);
        permissionService.upgradeToAdmin(userId);
    }

    /**
     * 超管调用：降级用户为普通角色
     *
     * @param userId 用户id
     */
    @PutMapping("/downgradeToUser")
    @Loggable(value = "超管调用：降级用户为普通角色")
    public void downgradeToUser(@RequestParam Long userId) {
        log.info("超管调用：降级用户为普通角色：{}", userId);
        permissionService.downgradeToUser(userId);
    }
}
