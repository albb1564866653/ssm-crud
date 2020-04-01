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
	 * У���û����Ƿ����
	 * @param empName
	 * @return true���û������� false���û���������
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
	 * Ա������
	 */
	@Override
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	/**
	 * Ա��ɾ��
	 */
	@Override
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
		
	}
	
	/**
	 * ����ɾ��
	 */
	@Override
	public void deleteBatch(List<Integer> ids) {
		System.out.println("��������������list��"+ids);
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1,2,3,...)
		criteria.andEmpIdIn(ids);
		
		employeeMapper.deleteByExample(example);
		
	}
}
