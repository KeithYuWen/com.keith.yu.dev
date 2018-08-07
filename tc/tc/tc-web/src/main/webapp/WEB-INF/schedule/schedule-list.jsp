<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<%@include file="common.jsp" %>
<body>
	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm" name="searchForm">
			<div class="form-group">
				<label class="control-label"> 
				  <span class="h4 font-thin v-middle">任务名称:</span>
				</label> 
				<input class="input-medium ui-autocomplete-input" id="jobName"	name="scheduleJob.jobName" />
				<label class="control-label">  
				  <span class="h4 font-thin v-middle">任务组名:</span>
				</label> 
				<input class="input-medium ui-autocomplete-input" id="jobGroup"	name="scheduleJob.jobGroup" />
			</div>
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<a href="javascript:void(0)" class="btn btn-default" id="create">新建</a>
		</form> 
	</div>

	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
	
	<div class="table-responsive">
		<div id="paging2" class="pagclass"></div>
	</div>
</body>
<script type="text/javascript" src="/common/js/schedule/list.js"></script>
</html>