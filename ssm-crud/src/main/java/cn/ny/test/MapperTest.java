package cn.ny.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 测试dao层
 * @author Administrator
 *推荐spring项目使用spring单元测试，可以自动注入需要的组件
 *1.导入springtest模块
 *2.@ContextConfiguration指定Spring配置文件的位置
 *3.直接autowired要使用的组件即可
 */

import cn.ny.entity.Employee;
import cn.ny.entity.EmployeeExample;
import cn.ny.entity.EmployeeExample.Criteria;
import cn.ny.mapper.DepartmentMapper;
import cn.ny.mapper.EmployeeMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	@Test
	public void testCRUD() {
//		System.out.println(departmentMapper);
		//插入部门信息
//		departmentMapper.insertSelective(new Department(null, "开发部"));
//		departmentMapper.insertSelective(new Department(null, "测试部"));
		
//		employeeMapper.insertSelective(new Employee(null, "zs", "男", "dasdaw", 1001));
		

		//2.批量插入员工数据，使用可以执行批量操作的sqlSession
//		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//		for(int i=0;i<1000;i++) {
//			String uid = UUID.randomUUID().toString().substring(0, 5)+i;
//			mapper.insertSelective(new Employee(null, uid, "M", uid+"@qwer,com", 1001));
//		}
		
		//测试批量删除
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(1007);
		ids.add(1008);
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1007,1008)
		criteria.andEmpIdIn(ids);
		
		employeeMapper.deleteByExample(example);
	}
	
}
