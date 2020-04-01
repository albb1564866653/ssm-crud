package cn.ny.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ny.entity.Department;
import cn.ny.mapper.DepartmentMapper;
import cn.ny.service.DepartmentService;
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentMapper departmentMapper;
	public List<Department> getDepts() {
		List<Department> depts=departmentMapper.selectByExample(null);
		return depts;
	}
	
}
