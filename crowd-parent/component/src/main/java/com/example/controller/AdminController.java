package com.example.controller;

import com.example.entity.Admin;
import com.example.service.AdminService;
import com.example.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

        Admin admin = adminService.selectByLoginAccount(username,password);
        session.setAttribute(ConstantUtil.ATTR_NAME_LOGIN_ADMIN,admin);

        return "/main";
    }
}
