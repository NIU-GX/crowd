package com.example.controller;

import com.example.entity.Admin;
import com.example.service.AdminService;
import com.example.util.ConstantUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author NGX
 * @Date 2020/5/2 9:29
 * @Description
 */

@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/admin/login")
    public String adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        Logger logger = LoggerFactory.getLogger(LoginController.class);
        logger.info(username + " " + password);

        Admin admin = adminService.selectByLoginAccount(username, password);
        session.setAttribute(ConstantUtil.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main";
    }

    @RequestMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        return "redirect:/admin/to/logout";
    }

    @RequestMapping("/admin/getpage")
    public String getPageInfo(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                              ModelMap modelMap) {
        // 1. 获取pageInfo
        PageInfo<Admin> adminPageInfo = adminService.selectAdminByKeyWord(keyword, pageNum, pageSize);
        // 2. 将pageInfo存入Model
        modelMap.addAttribute(ConstantUtil.ATTR_NAME_PAGEINFO,adminPageInfo);

        return "/admin-page";
    }
}
