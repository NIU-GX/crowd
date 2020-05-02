package com.example.service.impl;

import com.example.entity.AdminExample;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import com.example.entity.Admin;
import com.example.util.ConstantUtil;
import com.example.util.MD5Util;
import com.example.util.exception.LoginFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author NGX
 * @Date 2020/4/29 21:46
 * @Description
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin selectByLoginAccount(String loginAccount, String password) {
        Admin admin = adminMapper.selectByLoginAccount(loginAccount);
        if (admin == null) {
            throw new LoginFailedException(ConstantUtil.MESSAGE_LOGIN_ERROR);
        }
        System.out.println(admin);
        // 略去账号重复的操作

        String md5Password = MD5Util.md5(password);
        if (!Objects.equals(admin.getUserPassword(), md5Password)) {
            throw new LoginFailedException(ConstantUtil.MESSAGE_PASSWORD_ERROR);
        }
        return admin;
    }
}

