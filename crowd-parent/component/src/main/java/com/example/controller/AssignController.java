package com.example.controller;

import com.example.entity.Role;
import com.example.service.AdminService;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author NGX
 * @Date 2020/5/8 9:21
 */

@Controller
public class AssignController {

    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;

    @RequestMapping("assign/to/page")
    public String toAssignPage(@RequestParam("adminId") Integer adminId,
                               ModelMap modelMap) {
        // 查询已分配的角色
        List<Role> assignRoleList = roleService.getAssignedRole(adminId);
        // 查询未分配的角色
        List<Role> unassignRoleLis = roleService.getUnassignedRole(adminId);

        // 存入模型
        modelMap.addAttribute("assignRoleList",assignRoleList);
        modelMap.addAttribute("unassignRoleLis",unassignRoleLis);

        return "assign";
    }
}
