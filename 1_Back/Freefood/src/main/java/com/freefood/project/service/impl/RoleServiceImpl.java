package com.freefood.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freefood.project.dao.RoleDao;
import com.freefood.project.model.Role;
import com.freefood.project.service.RoleService;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public Role findByName(String name) {
		return roleDao.findRoleByName(name);
	}

}
