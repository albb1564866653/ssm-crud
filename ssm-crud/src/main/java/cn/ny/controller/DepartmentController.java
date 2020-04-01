package cn.ny.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ny.entity.Department;
import cn.ny.entity.Msg;
import cn.ny.service.DepartmentService;

/**
 * 处理跟部门信息有关的请求
 * @author Administrator
 *
 */
@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 返回所有的部门信息
	 */
	@ResponseBody
	@RequestMapping("/depts")
	public Msg getDepts() {
		List<Department> depts = departmentService.getDepts();
		return Msg.success().add("depts",depts);
	}
}
