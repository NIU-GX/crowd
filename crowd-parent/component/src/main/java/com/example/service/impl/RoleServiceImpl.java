package com.example.service.impl;

import com.example.entity.Role;
import com.example.entity.RoleExample;
import com.example.mapper.RoleMapper;
import com.example.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author NGX
 * @Date 2020/5/5 15:46
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 1.开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        List<Role> roles = roleMapper.selectRoleByKeywork(keyword);
        PageInfo<Role> pageInfo = PageInfo.of(roles);

        return pageInfo;
    }

    @Override
    public void insertRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void deleteRoles(List<Integer> roleList) {
        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        // delete from t_role where id in roleList
        criteria.andIdIn(roleList);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        List<Role> list = roleMapper.selectAssignRole(adminId);
        return list;
    }

    @Override
    public List<Role> getUnassignedRole(Integer adminId) {
        List<Role> list = roleMapper.selectUnassignRole(adminId);
        return list;
    }
}
