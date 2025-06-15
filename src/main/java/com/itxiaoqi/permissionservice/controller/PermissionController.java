package com.itxiaoqi.permissionservice.controller;

import com.itxiaoqi.permissionservice.anno.Loggable;
import com.itxiaoqi.permissionservice.entity.result.Result;
import com.itxiaoqi.permissionservice.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 绑定默认角色（普通用户）
     *
     * @param userId
     */
    @PostMapping("/bindDefaultRole")
    @Loggable(value = "bindDefaultRole")
    public Result bindDefaultRole(@RequestParam Long userId) {
        permissionService.bindDefaultRole(userId);
        return Result.success();
    }


    /**
     * 查询用户角色码（返回role_code）
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUserRoleCode")
    @Loggable(value = "getUserRoleCode")
    public Result getUserRoleCode(@RequestParam Long userId) {
        String roleCode = permissionService.getUserRoleCode(userId);
        return Result.success(roleCode);
    }

    /**
     * 超管调用：升级用户为管理员
     *
     * @param userId
     */
    @PutMapping("/upgradeToAdmin")
    @Loggable(value = "upgradeToAdmin")
    public Result upgradeToAdmin(@RequestParam Long userId) {
        permissionService.upgradeToAdmin(userId);
        return Result.success();
    }

    /**
     * 超管调用：降级用户为普通角色
     *
     * @param userId
     */
    @PutMapping("/downgradeToUser")
    @Loggable(value = "downgradeToUser")
    public Result downgradeToUser(@RequestParam Long userId) {
        permissionService.downgradeToUser(userId);
        return Result.success();
    }
}
