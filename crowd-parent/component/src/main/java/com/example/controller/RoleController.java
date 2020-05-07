package com.example.controller;

import com.example.entity.Role;
import com.example.service.RoleService;
import com.example.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author NGX
 * @Date 2020/5/5 15:46
 */
@Controller
public class RoleController {
    @Autowired
    RoleService roleService;

    @ResponseBody
    @RequestMapping(value = "/role/getpage")
    public ResultEntity<PageInfo<Role>> getPage(@RequestParam(value = "keyword", defaultValue = "") String keword,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {

        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keword);
        // 直接返回成功，出现异常有异常处理机制处理
        return ResultEntity.sucessWithData(pageInfo);
    }

    @ResponseBody
    @RequestMapping("/role/add")
    public ResultEntity<String > addRole(Role role) {
        roleService.insertRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/role/update")
    public ResultEntity<String > editRole(Role role) {
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/role/delete")
    public ResultEntity<String > deleteRole(@RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return ResultEntity.successWithoutData();
    }
}
