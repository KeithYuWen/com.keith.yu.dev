<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="common.jsp">
	<jsp:param value="1" name="include"/>
</jsp:include>
<script type="text/javascript">
$(function(){
 $("#scheduleForm").validate({
	submitHandler : function(form) {// 必须写在验证前面，否则无法ajax提交
		var scheduleParams = $("#scheduleForm").serializeJson();
		ly.ajaxSubmit(form, {
			async : false, //请勿改成异步，下面有些程序依赖此请数据
			type : "POST",
			data : {'scheduleJob':JSON.stringify(scheduleParams)},
			url : '/tc/save-schedule.html',
			dataType : 'json',
			success : function(data) {
				var jsonObj = JSON.parse(data);
				if (jsonObj.success) {
						parent.layer.confirm('保存成功！是否关闭窗口？',{icon: 3}, function(index) {
				        	parent.layer.closeAll();
				        	window.location.href="${ctx}/tc/to-schedule-list.html";
				        	return false;
				        	
						});
				} else {
					if(jsonObj.message){
						parent.layer.alert(jsonObj.message,{icon: 2});
					}else{
						parent.layer.alert("保存失败，请确认信息后再试！",{icon: 2});
					}
				};
			}
		});
	},
	rules : {
		"jobName" : {
			required : true
		},
		"aliasName" : {
			required : true
		},
		"jobGroup" : {
			required : true
		},
		"cronExpression" : {
			required : true
		},
		"isSync" : {
			required : true
		},
		"serviceUrl" : {
			required : true
		},
		"submitMethod" : {
			required :true
		}
	},
	messages : {
		"jobName" : {
			required : "任务名称必填"
		},
		"aliasName" : {
			required : "任务别名必填"
		},
		"jobGroup" : {
			required : "任务组名必填"
		},
		"cronExpression" : {
			required : "时间表达式必填"
		},
		"isSync" : {
			required : "是否异步必填"
		},
		"serviceUrl" : {
			required : "访问地址URL必填"
		},
		"submitMethod" : {
			required :"请求方式必填"
		}
	},
	errorPlacement : function(error, element) {// 自定义提示错误位置
		$(".l_err").css('display', 'block');
		$(".l_err").html(error.html());
	},
	success : function(label) {// 验证通过后
		$(".l_err").css('display', 'none');
	}
 });
});

function cancel(){
	window.location.href="${ctx}/tc/to-schedule-list.html";
}
</script>
</head>
<body>
<div class="main ly-clearFix">
	<div id="rightSite">
		<div >
			<div class="l_err" style="width: 100%; margin-top: 2px;"></div>
			<form class="form-inline"  id="scheduleForm" name="scheduleForm"> 
				<table width="98%" border="0" cellspacing="0" cellpadding="0" class="tableList mb20">
					<tr>
						 <th>任务名称</th>
						 <td>
						 <input name="jobName" type="text" class="input" id="jobName" value="${scheduleJob.jobName}"/>
						 </td>
						 <th>任务别名</th>
						 <td>
						 <input name="aliasName" type="text" class="input" id="aliasName" value="${scheduleJob.aliasName}"/>
						 </td>
						 <th>任务组名</th>
						 <td>
						 <input name="jobGroup" type="text" class="input" id="jobGroup" value="${scheduleJob.jobGroup}"/>
						 </td>
					</tr>
					<tr>
						 <th>时间表达式</th>
						 <td>
						 <input name="cronExpression" type="text" class="input" id="cronExpression" value="${scheduleJob.cronExpression}"/>
						 </td>
						 <th>是否异步</th>
						 <td>
						 <select name="isSync" id="isSync" class="input">
		        				<option value="">请选择</option>
		        			    <option value="1" <c:if test="${scheduleJob.isSync=='1'}">selected</c:if>>是</option>
		        			    <option value="0" <c:if test="${scheduleJob.isSync=='0'}">selected</c:if>>否</option>
		      			 </select>
						 </td>
						 <th>访问地址URL</th>
						 <td>
						 <input name="serviceUrl" type="text" class="input" id="serviceUrl" value="${scheduleJob.serviceUrl}"/>
						 </td>
					 </tr> 
					<tr>
						<th>请求方式</th>
						 <td>
							 <select name="submitMethod" id="submitMethod" class="input">
			        				<option value="">请选择</option>
			        			    <option value="POST" <c:if test="${scheduleJob.submitMethod=='POST'}">selected</c:if>>POST</option>
			        			    <option value="GET" <c:if test="${scheduleJob.submitMethod=='GET'}">selected</c:if>>GET</option>
			      			 </select>
						 </td>
						 <th>是否包含参数</th>
						 <td>
							 <select name="isParam" id="isParam" class="input">
			        				<option value="">请选择</option>
			        			    <option value="0" <c:if test="${scheduleJob.isParam=='0'}">selected</c:if>>否</option>
			        			    <option value="1" <c:if test="${scheduleJob.isParam=='1'}">selected</c:if>>是</option>
			      			 </select>
						 </td>
					</tr>
					<tr>
						 <th>商品描述</th>
						 <td>
						 <input name="description" type="text" class="input" id="description" value="${scheduleJob.description}"/>
						 </td>	
						 
						 <th>请求参数</th>
						 <td>
						 <input name="serviceParam" type="text" class="input" id="serviceParam" value="${scheduleJob.serviceParam}"/>
						 </td>						
					</tr>
				</table>
				<input name="jobId" type="text" id="jobId" value="${scheduleJob.jobId}" style="display:none;" />
				<div class="tc mb20" id="editButtons" >
				 <c:if test="${isDetail ==null}">
				  <button class="button button-red mr20" id="saveJob" type="submit" >保存</button>
				 </c:if>
				 <a class="button button-gray mr20" id="cacel" onclick="cancel()" >取消</a>
				</div> 
			</form>
		</div>
	</div>
</div>	
</body>
</html>