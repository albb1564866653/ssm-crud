package cn.ny.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ny.entity.Employee;
import cn.ny.entity.EmployeeExample;
import cn.ny.entity.EmployeeExample.Criteria;
import cn.ny.mapper.EmployeeMapper;
import cn.ny.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeMapper employeeMapper;
	
	public List<Employee> getAll() {
		// TODO Auto-generated method stub  
		return employeeMapper.selectByExampleWithDept(null);
	}

	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
		
	}

	/**
	 * 校验用户名是否可用
	 * @param empName
	 * @return true：用户名可用 false：用户名不可用
	 */
	public boolean checkUser(String empName) {
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count=employeeMapper.countByExample(example);
		return count==0;
	}

	@Override
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	/**
	 * 员工更新
	 */
	@Override
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	/**
	 * 员工删除
	 */
	@Override
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
		
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public void deleteBatch(List<Integer> ids) {
		System.out.println("控制器传过来的list："+ids);
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1,2,3,...)
		criteria.andEmpIdIn(ids);
		
		employeeMapper.deleteByExample(example);
		
	}
}
