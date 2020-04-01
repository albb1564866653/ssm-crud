package cn.ny.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * 处理员工CRUD请求
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
	 * 单个、批量删除共用
	 * 单个：1
	 * 批量：1-2-3
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids") String ids) {
		if(ids.contains("-")) {//批量删除
			System.out.println("前端传过来的id:"+ids);
			List<Integer> del_ids=new ArrayList<Integer>();
			String[] str_ids = ids.split("-");
			//组装id的集合
			for (String temp : str_ids) {
				del_ids.add(Integer.parseInt(temp));
			}
			System.out.println("控制器里面的list:"+del_ids);
			employeeService.deleteBatch(del_ids);
		}else {//单个删除
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		
		return Msg.success();
	}
	
	/**
	 * 如果直接发送ajax为put形式的请求
	 * 封装的数据Employee
	 * 问题：
	 * 请求体中有数据；但是Employee对象封装不上
	 * 原因：
	 * Tomcat：
	 * 		1.将请求体中的数据，封装成一个Map
	 * 		2.request.getParameter("empName")；就会从这个Map中取值
	 * 		3.SpringMvc封装POJO对象的时候，会把POJO中的每个属性值，request.getParameter("empName")；
	 * ajax直接用put请求：请求体中的数据，request.getParameter("empName") 拿不到数据
	 * Tomcat一看是put就不会将请求体中的数据封装为Map，只有post形式请求才会封装为Map
	 * 
	 * 要能支持直接发送put之类的请求还要封装请求体中的数据
	 * 1.需要在wel.xml配置HttpPutFormContentFilter
	 * 2.作用：将请求体中数据解析 包装成一个Map；
	 * 3.request被重新包装，request.getParameter("empName")被重写，就会从自己封装的Map中取数据
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
	 * 根据id查询员工
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
	 * 检查用户名是否可用
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		//先判断用户名是否合法
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.error().add("va_msg", "用户名必须是2-5位中文或者6-16位英文和数字组合");
		}
		boolean flag=employeeService.checkUser(empName);
		if(flag) {
			return Msg.success();
		}else {
			return Msg.error().add("va_msg", "用户名不可用 ");
		}
	}
	
	/**
	 * 保存 新增的员工信息
	 * 1.支持JSR303校验
	 * 2.导入Hibernate-Validator
	 * @return
	 */
	@RequestMapping(value = "/emp",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String,Object> map=new HashMap<>();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				System.out.println("错误的字段名："+fieldError.getField());
				System.out.println("错误的信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.error().add("errorFields",map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
		
	}
	
	/**
	 * 查询员工数据（分页查询）
	 * @return
	 */
	
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
			Model model) {
		//引入PageHelper分页插件
		//在查询之前只需要调用，传入 页码、每页的大小
		PageHelper.startPage(pn, 5);
		//startPage后面紧跟的这个查询就是一个分页查询
		List<Employee> emps=employeeService.getAll();	
		//使用pageinfo包装查询后的结果，只需要将pageInfo交给页面就行了
		//封装了详细的分页信息，包括有我们查询出来的 数据、传入连续显示的页数
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("pageInfo", page);
	}
	
//	@RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
			Model model) {
		//引入PageHelper分页插件
		//在查询之前只需要调用，传入 页码、每页的大小
		PageHelper.startPage(pn, 5);
		//startPage后面紧跟的这个查询就是一个分页查询
		List<Employee> emps=employeeService.getAll();	
		//使用pageinfo包装查询后的结果，只需要将pageInfo交给页面就行了
		//封装了详细的分页信息，包括有我们查询出来的 数据、传入连续显示的页数
		PageInfo page = new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		
		return "list";
	}
	
}
