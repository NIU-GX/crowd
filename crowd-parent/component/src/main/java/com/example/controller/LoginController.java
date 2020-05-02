package com.example.controller;

import com.example.entity.Admin;
import com.example.service.AdminService;
import com.example.util.ConstantUtil;
import com.example.util.CrowdUtil;
import com.example.util.MD5Util;
import com.example.util.ResultEntity;
import com.example.util.exception.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author NGX
 * @Date 2020/4/30 16:32
 * @Description
 */
@Controller()
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/loging")
    public String login(ModelMap modelMap) {
        int i = 1 / 0;
        List<Admin> result = adminService.getAll();
        modelMap.addAttribute("result", result);
        return "success";
    }

    @RequestMapping("/send")
    @ResponseBody
    public ResultEntity<Object> send(HttpServletRequest request) {
        System.out.println(CrowdUtil.isAjaxRequest(request));
        int i = 1 / 0;
        ResultEntity<Object> resultEntity = ResultEntity.successWithoutData();
        return resultEntity;
    }


}
