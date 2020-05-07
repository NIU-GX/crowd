package com.example.service.impl;

import com.example.entity.AdminExample;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import com.example.entity.Admin;
import com.example.util.ConstantUtil;
import com.example.util.MD5Util;
import com.example.util.exception.LoginAccountAlreadlyInUse;
import com.example.util.exception.LoginAccountAlreadlyInUseForUpdate;
import com.example.util.exception.LoginFailedException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        // 1. 密码加密
        String userPassword = admin.getUserPassword();
        String md5 = MD5Util.md5(userPassword);
        admin.setUserPassword(md5);
        // 2. 生成创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        admin.setCreateTime(format);
        System.out.println(admin);
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAccountAlreadlyInUse("不允许重复的账户");
            }
        }
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

    @Override
    public void deleteAdminByPrimaryKey(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Admin selectAdminById(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        return admin;
    }

    @Override
    public void updateAdmin(Admin admin) {

        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAccountAlreadlyInUseForUpdate("不允许重复的账户");
            }
        }
    }
}

