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
import org.springframework.web.bind.annotation.PathVariable;
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
        modelMap.addAttribute(ConstantUtil.ATTR_NAME_PAGEINFO, adminPageInfo);

        return "/admin-page";
    }

    @RequestMapping("/admin/delete/{id}/{pageNum}/{keyword}")
    public String deleteAdmin(@PathVariable("id") Integer id,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable(value = "keyword") String keyword) {
        adminService.deleteAdminByPrimaryKey(id);

        return "redirect:/admin/getpage?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/admin/delete/{id}/{pageNum}")
    public String deleteAdminWithoutKeyword(@PathVariable("id") Integer id,
                                            @PathVariable("pageNum") Integer pageNum) {
        adminService.deleteAdminByPrimaryKey(id);

        return "redirect:/admin/getpage?pageNum=" + pageNum;
    }

    @RequestMapping("/admin/add")
    public String saveAdmin(Admin admin) {
        adminService.saveAdmin(admin);
        // 为了让用户第一眼就看到新增加的用户，直接跳转到最后一页
        return "redirect:/admin/getpage?pageNum=" + Integer.MAX_VALUE;
    }

    @RequestMapping("/admin/to/edit")
    public String toAdminEdit(@RequestParam("adminId") Integer adminId, ModelMap map) {

        Admin admin = adminService.selectAdminById(adminId);
        map.addAttribute("admin", admin);
        return "/edit";
    }

    @RequestMapping("/admin/edit")
    public String editAdmin(Admin admin,
                            @RequestParam("pageNum") Integer pageNum,
                            @RequestParam("keyword") String keyword,
                            ModelMap map) {

        adminService.updateAdmin(admin);
        return "redirect:/admin/getpage?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
