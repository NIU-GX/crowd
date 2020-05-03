package com.example.service.impl;

import com.example.entity.AdminExample;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import com.example.entity.Admin;
import com.example.util.ConstantUtil;
import com.example.util.MD5Util;
import com.example.util.exception.LoginFailedException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Override
    public PageInfo<Admin> selectAdminByKeyWord(String keyWord, Integer pageNum, Integer pageSize) {
        // 1. 开启pageHelper的静态方法，开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        // 2. 执行查询
        List<Admin> admins = adminMapper.selectAdminByKeyWord(keyWord);

        // 3. 疯转到pageInfo对象中
        PageInfo<Admin> pageInfo = PageInfo.of(admins);

        return pageInfo;
    }
}

