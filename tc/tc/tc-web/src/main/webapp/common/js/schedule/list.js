var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [{
			colkey : "jobId",
			name :"jobId",
			hide:true,
		},{
			colkey : "jobName",
			name :"任务名",
			isSort:true,
			renderData : function(rowindex,data,rowdata,column) {
				return '<a href="/tc/schedule-detail.html?id='+rowdata.jobId+'">'+rowdata.jobName+'</a>';
			}
		}, {
			colkey : "aliasName",
			name : "任务别名",
			isSort:true,
		}, {
			colkey : "jobGroup",
			name : "任务组名",
			isSort:true,
		},  {
			colkey : "cronExpression",
			name : "时间表达式",
			isSort:true,
		}, {
			colkey : "jobStatus",
			name : "任务状态",
			isSort:false,
			renderData : function(rowindex,data,rowdata,column) {
				if(rowdata.jobStatus=='1'){
					return "启用";
				}else{
					return "禁用";
				}
			}
		},{
			colkey : "",
			name : "操作",
			isSort:false,
			renderData : function(rowindex,data,rowdata,column) {
				if(rowdata.jobStatus=='1'){
					return "<a href='#' class='lnk-green' onclick='pause("+rowdata.jobId+")'>【暂停】</a><a href='#' class='lnk-green' onclick='deleteJob("+rowdata.jobId+")'>【删除】</a>";
				}else{
					return "<a href='#' class='lnk-green' onclick='resume("+rowdata.jobId+")'>【恢复】</a><a href='#' class='lnk-green' onclick='deleteJob("+rowdata.jobId+")'>【删除】</a><a href='#' class='lnk-green' onclick='runonce("+rowdata.jobId+")'>【运行一次】</a><a class='lnk-green' href='/tc/edit-schedule.html?id="+rowdata.jobId+"'>【编辑】</a>";
				}
			}
		}],
		jsonUrl : '/tc/show-page-data.html',
		checkbox : true,
		serNumber : true
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	
	$("#create").click("click",function(){
		window.location.href= "/tc/schedule-input.html";
	});
});	


//暂停
function pause(jobId){
	var pauseUrl= "/tc/pause-schedule.html";
	$.ajax({
		url: pauseUrl,
		type: "POST",
		data:{"id":jobId},
		async:false,
		success: function(data){
			var jsonObj = JSON.parse(data);
			if (jsonObj.success) {
				alert(jsonObj.message);
				window.location.href= "/tc/to-schedule-list.html";
			}else{
				alert(jsonObj.message);
			}
		}
	});
}

//删除
function deleteJob(jobId){
	var deleteUrl="/tc/delete-schedule.html";
	$.ajax({
		url: deleteUrl,
		type: "POST",
		data:{"id":jobId},
		async:false,
		success: function(data){
			var jsonObj = JSON.parse(data);
			if(jsonObj.success){
				alert(jsonObj.message);
				window.location.href="/tc/to-schedule-list.html";
			}else{
				alert(jsonObj.message);
			}
		}
	});
}
//编辑
function editJob(jobId){
	var editUrl="/tc/edit-schedule.html";
	$.ajax({
		url: editUrl,
		type: "POST",
		data:{"id":jobId},
		async:false,
		success: function(data){
			if(data.success){
				window.location.href="/tc/to-schedule-list.html";
			}else{
				alert(data.message);
			}
		}
	});
}
//恢复
function  resume(jobId){
	var resumeUrl= "/tc/resume-schedule.html";
	$.ajax({
		url: resumeUrl,
		type: "POST",
		data:{"id":jobId},
		async:false,
		success: function(data){
			var jsonObj = JSON.parse(data);
			if (jsonObj.success) {
				alert(jsonObj.message);
				window.location.href= "/tc/to-schedule-list.html";
			}else{
				alert(jsonObj.message);
			}
		}
	});
}
//运行一次
function runonce(jobId){
	var runonceUrl= "/tc/runonce-schedule.html";
	$.ajax({
		url: runonceUrl,
		type: "POST",
		data:{"id":jobId},
		async:false,
		success: function(data){
			var jsonObj = JSON.parse(data);
			if (jsonObj.success) {
				alert(jsonObj.message);
				window.location.href= "/tc/to-schedule-list.html";
			}else{
				alert(jsonObj.message);
			}
		}
	});
}

