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
 * ����dao��
 * @author Administrator
 *�Ƽ�spring��Ŀʹ��spring��Ԫ���ԣ������Զ�ע����Ҫ�����
 *1.����springtestģ��
 *2.@ContextConfigurationָ��Spring�����ļ���λ��
 *3.ֱ��autowiredҪʹ�õ��������
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
		//���벿����Ϣ
//		departmentMapper.insertSelective(new Department(null, "������"));
//		departmentMapper.insertSelective(new Department(null, "���Բ�"));
		
//		employeeMapper.insertSelective(new Employee(null, "zs", "��", "dasdaw", 1001));
		

		//2.��������Ա�����ݣ�ʹ�ÿ���ִ������������sqlSession
//		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//		for(int i=0;i<1000;i++) {
//			String uid = UUID.randomUUID().toString().substring(0, 5)+i;
//			mapper.insertSelective(new Employee(null, uid, "M", uid+"@qwer,com", 1001));
//		}
		
		//��������ɾ��
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
