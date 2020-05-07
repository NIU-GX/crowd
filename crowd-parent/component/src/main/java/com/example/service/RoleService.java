package com.example.service;

import com.example.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NGX
 * @Date 2020/5/5 15:45
 */
public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void insertRole(Role role);

    void updateRole(Role role);

    void deleteRoles(List<Integer> roleList);
}
