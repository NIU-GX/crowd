package com.example.service;

import com.example.entity.Admin;

import java.util.List;

/**
 * @author NGX
 * @Date 2020/4/29 21:45
 * @Description
 */
public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin selectByLoginAccount(String loginAccount, String password);
}
