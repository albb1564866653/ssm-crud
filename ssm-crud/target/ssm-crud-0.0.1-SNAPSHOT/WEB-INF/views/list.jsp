<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工列表</title>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- web路径：
	不以/开始的相对路径，找资源，以当前资源的路径为准，容易出现问题；
	以/开始的相对路径，找资源，以服务器的路径为标准（http://localhost:8888），需要加上项目名
	http://localhost:8888/ssm-crud
 -->
<!-- Bootstrap -->
    <link href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/jquery-1.11.1.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
<!-- 	搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 新增、删除按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<td>#</td>
						<td>empName</td>
						<td>gender</td>
						<td>email</td>
						<td>deptName</td>
						<td>操作</td>
					</tr>
					<c:forEach items="${pageInfo.list }" var="emp">
						<tr>
<!-- 						Integer empId, String empName, String gender, String email, Integer dId -->
							<td>${emp.empId}</td>
							<td>${emp.empName}</td>
							<td>${emp.gender}</td>
							<td>${emp.email}</td>
							<td>${emp.department.deptName}</td>
							<td>
								<button class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									编辑
								</button>
								<button class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									删除
								</button>
							</td>
						</tr>
					</c:forEach>
					
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">

			<!-- 分页文字信息 -->
			<div class="col-md-6">
				当前第${pageInfo.pageNum}页，总共${pageInfo.pages}页，总共${pageInfo.total}条记录
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<li><a href="${APP_PATH}/emps?pn=1">首页</a></li>
						<c:if test="${pageInfo.hasPreviousPage}">
							<li>
								<a href="${APP_PATH}/emps?pn=${pageInfo.pageNum-1 }" aria-label="Previous"> <span
									aria-hidden="true">&laquo;</span>
								</a>
							</li>
						</c:if>
						
						<c:forEach items="${pageInfo.navigatepageNums }" var="page_Num">
							<c:if test="${page_Num==pageInfo.pageNum }">
								<li class="active"><a href="#">${page_Num }</a></li>
							</c:if>
							<c:if test="${page_Num!=pageInfo.pageNum }">
								<li><a href="${APP_PATH}/emps?pn=${page_Num }">${page_Num }</a></li>
							</c:if>
						</c:forEach>
						
						<c:if test="${pageInfo.hasNextPage }">
							<li>
								<a href="${APP_PATH}/emps?pn=${pageInfo.pageNum+1 }" aria-label="Next"> <span
									aria-hidden="true">&raquo;</span>
								</a>
							</li>
						</c:if>
						
						
						<li><a href="${APP_PATH}/emps?pn=${pageInfo.pages }">尾页</a></li>
					</ul>
				</nav>
			</div>
		</div>
		
	</div>
</body>
</html>