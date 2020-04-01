package cn.ny.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * ����Ա��CRUD����
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ny.entity.Employee;
import cn.ny.entity.Msg;
import cn.ny.service.EmployeeService;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * ����������ɾ������
	 * ������1
	 * ������1-2-3
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids") String ids) {
		if(ids.contains("-")) {//����ɾ��
			System.out.println("ǰ�˴�������id:"+ids);
			List<Integer> del_ids=new ArrayList<Integer>();
			String[] str_ids = ids.split("-");
			//��װid�ļ���
			for (String temp : str_ids) {
				del_ids.add(Integer.parseInt(temp));
			}
			System.out.println("�����������list:"+del_ids);
			employeeService.deleteBatch(del_ids);
		}else {//����ɾ��
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		
		return Msg.success();
	}
	
	/**
	 * ���ֱ�ӷ���ajaxΪput��ʽ������
	 * ��װ������Employee
	 * ���⣺
	 * �������������ݣ�����Employee�����װ����
	 * ԭ��
	 * Tomcat��
	 * 		1.���������е����ݣ���װ��һ��Map
	 * 		2.request.getParameter("empName")���ͻ�����Map��ȡֵ
	 * 		3.SpringMvc��װPOJO�����ʱ�򣬻��POJO�е�ÿ������ֵ��request.getParameter("empName")��
	 * ajaxֱ����put�����������е����ݣ�request.getParameter("empName") �ò�������
	 * Tomcatһ����put�Ͳ��Ὣ�������е����ݷ�װΪMap��ֻ��post��ʽ����Ż��װΪMap
	 * 
	 * Ҫ��֧��ֱ�ӷ���put֮�������Ҫ��װ�������е�����
	 * 1.��Ҫ��wel.xml����HttpPutFormContentFilter
	 * 2.���ã��������������ݽ��� ��װ��һ��Map��
	 * 3.request�����°�װ��request.getParameter("empName")����д���ͻ���Լ���װ��Map��ȡ����
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
	@ResponseBody
	public Msg saveEmp(Employee employee) {
		System.out.println(employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	/**
	 * ����id��ѯԱ��
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer id) {
		Employee employee=employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * ����û����Ƿ����
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		//���ж��û����Ƿ�Ϸ�
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.error().add("va_msg", "�û���������2-5λ���Ļ���6-16λӢ�ĺ��������");
		}
		boolean flag=employeeService.checkUser(empName);
		if(flag) {
			return Msg.success();
		}else {
			return Msg.error().add("va_msg", "�û��������� ");
		}
	}
	
	/**
	 * ���� ������Ա����Ϣ
	 * 1.֧��JSR303У��
	 * 2.����Hibernate-Validator
	 * @return
	 */
	@RequestMapping(value = "/emp",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			//У��ʧ�ܣ�Ӧ�÷���ʧ�ܣ���ģ̬������ʾУ��ʧ�ܵĴ�����Ϣ
			Map<String,Object> map=new HashMap<>();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println("������ֶ�����"+fieldError.getField());
				System.out.println("�������Ϣ��"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.error().add("errorFields",map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
		
	}
	
	/**
	 * ��ѯԱ�����ݣ���ҳ��ѯ��
	 * @return
	 */
	
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
			Model model) {
		//����PageHelper��ҳ���
		//�ڲ�ѯ֮ǰֻ��Ҫ���ã����� ҳ�롢ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		//startPage��������������ѯ����һ����ҳ��ѯ
		List<Employee> emps=employeeService.getAll();	
		//ʹ��pageinfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ�������
		//��װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ������ ���ݡ�����������ʾ��ҳ��
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("pageInfo", page);
	}
	
//	@RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
			Model model) {
		//����PageHelper��ҳ���
		//�ڲ�ѯ֮ǰֻ��Ҫ���ã����� ҳ�롢ÿҳ�Ĵ�С
		PageHelper.startPage(pn, 5);
		//startPage��������������ѯ����һ����ҳ��ѯ
		List<Employee> emps=employeeService.getAll();	
		//ʹ��pageinfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ�������
		//��װ����ϸ�ķ�ҳ��Ϣ�����������ǲ�ѯ������ ���ݡ�����������ʾ��ҳ��
		PageInfo page = new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		
		return "list";
	}
	
}
