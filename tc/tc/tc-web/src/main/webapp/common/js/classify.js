// 中文字两个字节         
/**jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {         
    var length = value.length;         
    for(var i = 0; i < value.length; i++){         
        if(value.charCodeAt(i) > 127){         
        length++;         
        }         
    }         
    return this.optional(element) || ( length >= param[0] && length <= param[1] );         
}, "请确保输入的值在3-15个字节之间(一个中文字算2个字节)");   
//乱码验证         
jQuery.validator.addMethod("isNoLuan", function(value, element) {         
    var reg =/[!@$^*~\'\"]/;        
    return this.optional(element) || !(reg.test(value));         
}, "请正确填写");    
jQuery.validator.addMethod("isNoCH", function(value, element) {         
    var reg =/^.*[\u4E00-\u9FA5]+.*$/;     
    return this.optional(element) || !(reg.test(value));         
}, "不能包含中文");   */
//确认
function confirmClassify(){
	var tarriNumer =$("#tariffNo").val();
	if(tarriNumer==''){
		alert("请先填写税则号！");
		return;
	}else{
		//验证税则号是否存在
		    var prdCode= $("#prdCode").val();
		    var orgCode= $("#orgCode").val();
			ly.ajax({
				async : false, //请勿改成异步，下面有些程序依赖此请数据
				type : "POST",
				data : {'tarrifNo':tarriNumer,'isConfirm':'1','prdCode':prdCode,'orgCode':orgCode},
				url : rootPath + '/classify/getModuleInfo.html',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
					        	parent.layer.closeAll();
					        	$("#editButtons").show();
					        	if(data.certificate !=""){
					        		parent.layer.alert(data.certificate,{icon: 1});
					        	}
					        	//$("#moduleInfos").show();
					        	return false;
					} else {
						parent.layer.alert(data.message,{icon: 2});
					};
				}
			});
	}
}

//确认
function confirmClassify1(){
	var tarriNumer =$("#tariffNo").val();
	if(tarriNumer==''){
		alert("请先填写税则号！");
		return;
	}else{
		//验证税则号是否存在
		    var prdCode= $("#prdCode").val();
		    var orgCode= $("#orgCode").val();
			ly.ajax({
				async : false, //请勿改成异步，下面有些程序依赖此请数据
				type : "POST",
				data : {'tarrifNo':tarriNumer,'isConfirm':'0','prdCode':prdCode,'orgCode':orgCode},
				url : rootPath + '/classify/getModuleInfo.html',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
					        	parent.layer.closeAll();
					        	//填充规范要素
					        	var str="";	
					        	str+='<colgroup><col width="20%"><col width="80%"></colgroup>';
					        	str+=" <tr><th>必填描述</th><th>必填内容</th></tr>";
					        	for ( var i = 0; i < data.moduleDicts.length; i++) {
					        		var item=data.moduleDicts[i];
					        		str+="<tr ><td>"+item.criterionName+"</td>";
					        		str+='<td><input name="moduleValue" id="moduleValue'+i+'"/></td>';
					        		str+='</tr>';
					        	}
					        	$("#moduleInfo").html(str);
					        	$("#editButtons").show();
					        	return false;
					} else {
						parent.layer.alert(data.message,{icon: 2});
					};
				}
			});
	}
}

//新增要素审核
function addModule(){
	confirmClassify1();
	$("input[name='moduleValue']").val("");
	$("#moduleCode").val("");
	$("#moduleId").val("");
	$("#saveModule").removeAttr("disabled");
	$("#cacelModule").removeAttr("disabled");
	$("#addModule").attr("disabled",true);
	$("#moduleInfos").show();
}
//取消
function cacelModule(){
	$("input[name='moduleValue']").val("");
	$("#moduleInfos").hide();
	$("#saveModule").attr("disabled",true);
	$("#cacelModule").attr("disabled",true);
	$("#addModule").removeAttr("disabled");
}

//编辑规范要素
function editModule(id,applyName,t){
	confirmClassify1();
	var moduleCode=$(t).parent().parent().find('td:eq(1)').text();
	if(applyName==null || applyName !='NULL'){
		applyName="";
	}
	$("#applyName").val(applyName);
	$("#moduleCode").val(moduleCode);
	$("#moduleId").val(id);
	var moduleList  =moduleCode.split("|");
	var length= $("input[name='moduleValue']").length;
	for(var i=0;i<length;i++){
		$("#moduleValue"+i).val(moduleList[i]);
	}
	$("#saveModule").removeAttr("disabled");
	$("#cacelModule").removeAttr("disabled");
	$("#moduleInfos").show();
}
//保存规范要素
function saveModule(){
	var length= $("input[name='moduleValue']").length;
	var moduleCode=new Array();
	for(var i=0;i<length;i++){
		var mv = $.trim($("#moduleValue"+i).val());
//		if(mv == ''){
//			alert("规范要素不能为空!");
//			return;
//		}
		moduleCode[i]=mv;
	}
	var moduleCodes = moduleCode.join(",");
	var applyName = $("#applyName").val();
    var str='';
    for(var i=0;i<length;i++){
    	str+=$("#moduleValue"+i).val();
	}
    if(str==''){
    	//规格要素一个都没有填
    	alert("请填写规格要素！");
    	return;
    }
    var tarrifNo= $("#tariffNo").val();
    var prdCode= $("#prdCode").val();
	var orgCode= $("#orgCode").val();
	var recordOrgCode = $("#recordOrgCode").val();
//    layer.confirm('是否保存要素？', function(index) {
    	
		ly.ajax({
			async : false, //请勿改成异步，下面有些程序依赖此请数据
			type : "POST",
			data : {'moduleId':$("#moduleId").val(),'orgCode':orgCode,'prdCode':prdCode,'recordOrgCode':recordOrgCode,'applyName':applyName,'moduleCode':moduleCodes,'tarrifNo':tarrifNo},
			url : rootPath + '/classify/save-moduleInfo.html',
			dataType : 'json', 
			success : function(data) {
				if (data.success) {
							//画LIST
				        	var str=' <colgroup><col width="20%" /><col width="30%" /><col width="30%" /></colgroup>';
				        	str+='<tr><td>供应商</td><td>规格型号</td><td>品牌</td><td>操作</td></tr>';
				        	for ( var i = 0; i < data.moduleList.length; i++) {
				        		var item=data.moduleList[i];
				        		if(item.applyName==null || item.applyName=='NULL'){
				        			str+='<tr><td>'+'</td>';
				        		}else{
				        			str+='<tr><td>'+item.applyName+'</td>';
				        		}
				        		str+='<td>'+item.moduleCode+'</td>';
				        		if(item.brand==null || item.brand=='NULL'){
				        			str+='<td>'+'</td>';
				        		}else{
				        			str+='<td>'+item.brand+'</td>';
				        		}
				        		str+='<td>';
				        		if(item.isCheck=='0'){
				        			str+='<a href="#" class="lnk-green" id="editModule'+item.id+'"onclick=\'editModule('+'"'+item.id+'"'+','+'"'+item.applyName+'"'+',this)\' >【编辑】</a>';
				        			str+='<a href="#" class="lnk-red" id="delete'+item.id+'" onclick=\'deleteModule('+'"'+item.id+'"'+')\'>【删除】</a>';
				        		}else{
				        			str+='<a href="#" class="lnk-green" id="editModule'+item.id+'"onclick=\'editModule('+'"'+item.id+'"'+','+'"'+item.applyName+'"'+',this'+')\' style="display:none;">【编辑】</a>';
				        			str+='<a href="#" class="lnk-red" id="delete'+item.id+'" onclick=\'deleteModule('+'"'+item.id+'"'+')\' style="display:none;">【删除】</a>';
				        		}
				        		if(item.isCheck=='0'){
				        			str+='<a href="#" class="lnk-blue" id="check'+item.id+'" onclick=\'checkModule('+'"'+item.id+'"'+',this'+')\'>【要素审核】</a>';
				        			str+='<a href="#" class="lnk-blue" id= "cancelcheck'+item.id+'" onclick=\'checkCancelModule('+'"'+item.id+'",this'+')\' style="display:none;">【取消要素审核】</a>';
				        		}else{
				        			str+='<a href="#" class="lnk-blue" id="check'+item.id+'" onclick=\'checkModule('+'"'+item.id+'"'+',this'+')\' style="display:none;">【要素审核】</a>';
				        			str+='<a href="#" class="lnk-blue" id= "cancelcheck'+item.id+'" onclick=\'checkCancelModule('+'"'+item.id+'",this'+')\'>【取消要素审核】</a>';
				        		}
				        		str+='<a href="#" class="lnk-green" onclick=\'copyModule('+'"'+item.applyName+'",this'+')\'>【复制】</a>';
			       		        str+='</td></tr>';
				        	}
				        	$("#moduleList").html(str);
				        	$("input[name='moduleValue']").val("");
				        	$("#moduleInfos").hide();
				        	$("#saveModule").attr("disabled",true);
				        	$("#addModule").removeAttr("disabled");
				        	$("#cacelModule").attr("disabled",true);
				        	$("#moduleList").show();
				        	parent.layer.closeAll();
				        	return false;
				} else {
					parent.layer.alert(data.message,{icon: 2});
				};
			}
		});
//	});
    
}

//保存归类信息
function saveClassify(){
	$("input[name='moduleValue']").val("");
	$("#moduleInfos").hide();
	$("#addModule").removeAttr("disabled");
	var tarriNumer =$("#tariffNo").val();
	if(tarriNumer==''){
		alert("请先填写税则号！");
		return;
	}
	var classifyParams = $("#classifyForm").serializeJson();
		ly.ajax({
			async : false, //请勿改成异步，下面有些程序依赖此请数据
			type : "POST",
			data : {'classifyInfo':JSON.stringify(classifyParams),'oldPrdCode':$("#oldPrdCode").val(),'oldOrgCode':$("#oldOrgCode").val()},
			url : rootPath + '/classify/save-classifyInfo.html',
			dataType : 'json',
			success : function(data) {
				if (data.success) {
						$("#editButtons").show();
						if(data.deleteModules){//如果之前存在规格要素 且税则号发生变更
			        		var str=' <colgroup><col width="20%" /><col width="30%" /><col width="30%" /></colgroup>';
				        	str+='<tr><td>供应商</td><td>规格型号</td><td>操作</td></tr>';
				        	$("#moduleList").html(str);
			        	}
				} else {
					if(data.message){
						parent.layer.alert(data.message,{icon: 2});
					}else{
						parent.layer.alert("保存失败，请确认信息后再试！",{icon: 2});
					}
				};
			}
		});
}
//取消归类审核
function classifyCancelCheck(){
	$("input[name='moduleValue']").val("");
	$("#moduleInfos").hide();
	$("#addModule").removeAttr("disabled");
	var prdCode= $("#prdCode").val();
	var orgCode= $("#orgCode").val();
	var tarrifNo= $("#tariffNo").val();
	var recordOrgCode = $("#recordOrgCode").val();
	if(tarrifNo==''){
		alert("请先填写税则号！");
		return;
	}else{
			ly.ajax({
				async : false, //请勿改成异步，下面有些程序依赖此请数据
				type : "POST",
				data : {'prdCode':prdCode,'orgCode':orgCode,'tarrifNo':tarrifNo,'recordOrgCode':recordOrgCode,'prdStatus':'1'},
				url : rootPath + '/classify/check-classifyInfo.html',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
							$("#classifyCheck").show();
				    		$("#classifyCancelCheck").hide();
				    		$("#tariffNo").removeAttr("disabled");
				    		$("#saveClassify").removeAttr("disabled");
				    		$("#classifyCheck").removeAttr("disabled");;
					} else {
						parent.layer.alert(data.message,{icon: 2});
					};
				}
			});
	}	
	
}
//归类信息审核
function classifyCheck(){
	var prdCode= $("#prdCode").val();
	var orgCode= $("#orgCode").val();
	var tarrifNo= $("#tariffNo").val();
	var recordOrgCode = $("#recordOrgCode").val();
	if(tarrifNo==''){
		alert("请先填写税则号！");
		return;
	}else{
			ly.ajax({
				async : false, //请勿改成异步，下面有些程序依赖此请数据
				type : "POST",
				data : {'prdCode':prdCode,'orgCode':orgCode,'tarrifNo':tarrifNo,'recordOrgCode':recordOrgCode,'prdStatus':'3'},
				url : rootPath + '/classify/check-classifyInfo.html',
				dataType : 'json',
				success : function(data) {
					if (data.success) {
							if(data.isSend){
								alert("风控确认进行中");
								$("#tariffNo").attr("disabled",true);
								$("#saveClassify").attr("disabled",true);
								//$("#classifyCheck").attr("disabled",true);
								$("#classifyCheck").removeAttr("onclick");
							}else{
								$("#classifyCheck").hide();
					    		$("#classifyCancelCheck").show();
					    		$("#editButtons").show();
					    		$("#tariffNo").attr("disabled",true);
					    		$("#saveClassify").attr("disabled",true);
							}
					} else {
						parent.layer.alert(data.message,{icon: 2});
					};
				}
			});
	}	
}
//删除规范要素
function deleteModule(id){
	layer.confirm('是否删除该要素？', function(index) {
		ly.ajax({
			async : false, //请勿改成异步，下面有些程序依赖此请数据
			type : "POST",
			data : {'moduleId':id},
			url : rootPath + '/classify/delete-moduleInfo.html',
			dataType : 'json',
			success : function(data) {
				if (data.success) {
//						parent.layer.confirm('删除成功！是否关闭窗口？',{icon: 3}, function(index) {
							var str=' <colgroup><col width="20%" /><col width="30%" /><col width="30%" /></colgroup>';
				        	str+='<tr><td>供应商</td><td>规格型号</td><td>品牌</td><td>操作</td></tr>';
				        	for ( var i = 0; i < data.moduleList.length; i++) {
				        		var item=data.moduleList[i];
				        		if(item.applyName==null || item.applyName=='NULL'){
				        			str+='<tr><td>'+'</td>';
				        		}else{
				        			str+='<tr><td>'+item.applyName+'</td>';
				        		}
				        		str+='<td>'+item.moduleCode+'</td>';
				        		if(item.brand==null || item.brand=='NULL'){
				        			str+='<td>'+'</td>';
				        		}else{
				        			str+='<td>'+item.brand+'</td>';
				        		}
				        		str+='<td>';
				        		if(item.isCheck=='0'){
				        			str+='<a href="#" class="lnk-green" id="editModule'+item.id+'"onclick=\'editModule('+'"'+item.id+'"'+','+'"'+item.applyName+'"'+',this'+')\'>【编辑】</a>';
				        			str+='<a href="#" class="lnk-red" id="delete'+item.id+'" onclick=\'deleteModule('+'"'+item.id+'"'+')\'>【删除】</a>';
				        		}else{
				        			str+='<a href="#" class="lnk-green" id="editModule'+item.id+'"onclick=\'editModule('+'"'+item.id+'"'+','+'"'+item.applyName+'"'+',this'+')\' style="display:none;">【编辑】</a>';
				        			str+='<a href="#" class="lnk-red" id="delete'+item.id+'" onclick=\'deleteModule('+'"'+item.id+'"'+')\' style="display:none;">【删除】</a>';
				        		}
				        		if(item.isCheck=='0'){
				        			str+='<a href="#" class="lnk-blue" id="check'+item.id+'" onclick=\'checkModule('+'"'+item.id+'"'+',this'+')\'>【要素审核】</a>';
				        			str+='<a href="#" class="lnk-blue" id= "cancelcheck'+item.id+'" onclick=\'checkCancelModule('+'"'+item.id+'",this'+')\' style="display:none;">【取消要素审核】</a>';
				        		}else{
				        			str+='<a href="#" class="lnk-blue" id="check'+item.id+'" onclick=\'checkModule('+'"'+item.id+'"'+',this'+')\' style="display:none;">【要素审核】</a>';
				        			str+='<a href="#" class="lnk-blue" id= "cancelcheck'+item.id+'" onclick=\'checkCancelModule('+'"'+item.id+'",this'+')\'>【取消要素审核】</a>';
				        		}
				        		str+='<a href="#" class="lnk-green" onclick=\'copyModule('+'"'+item.applyName+'",'+this+')\'>【复制】</a>';
			       		        str+='</td></tr>';
				        	}
				        	$("#moduleList").html(str);
				        	$("input[name='moduleValue']").val("");
				        	$("#moduleInfos").hide();
				        	$("#moduleList").show();
				        	parent.layer.closeAll();
				        	return false;
//						});
				} else {
					parent.layer.alert(data.message,{icon: 2});
				};
			}
		});
	});
}
//审核规范要素
function checkModule(id,t){
	var moduleCode=$(t).parent().parent().find('td:eq(1)').text();
	var prdCode= $("#prdCode").val();
	var orgCode= $("#orgCode").val();
	var recordOrgCode=$("#recordOrgCode").val();
		ly.ajax({
			async : false, //请勿改成异步，下面有些程序依赖此请数据
			type : "POST",
			data : {'moduleId':id,'isCheck':'1','moduleCode':moduleCode,'orgCode':orgCode,'recordOrgCode':recordOrgCode,'prdCode':prdCode},
			url : rootPath + '/classify/check-moduleInfo.html',
			dataType : 'json',
			success : function(data) {
				if (data.success) {
						$("#cancelcheck"+id).show();
			        	$("#check"+id).hide();
			        	$("#editModule"+id).hide();
			        	$("#delete"+id).hide();
				} else if(data.cancel){
					var moduleCode = data.moduleCode;
					var orgCode = data.orgCode;
					var prdCode = data.prdCode;
					var isCheck = data.isCheck;
					var moduleId = data.moduleId;
					parent.layer.confirm(data.message,{icon: 3},function(){
						ly.ajax({
							async:false,
							type:"post",
							data:{'moduleCode':moduleCode,'orgCode':orgCode,'prdCode':prdCode,'recordOrgCode':recordOrgCode,'isCheck':'1','moduleId':moduleId},
							url:rootPath + '/classify/check-moduleInfo-isconfirm.html',
							dataType:'json',
							success:function(data){
								
							}
						});
					});
				} else {
					parent.layer.alert(data.message,{icon: 2});
				};
			}
		});
}
//取消审核规范要素
function checkCancelModule(id,t){
	var moduleCode=$(t).parent().parent().find('td:eq(1)').text();
	var prdCode= $("#prdCode").val();
	var orgCode= $("#orgCode").val();
	var recordOrgCode=$("#recordOrgCode").val();
		ly.ajax({
			async : false, //请勿改成异步，下面有些程序依赖此请数据
			type : "POST",
			data : {'moduleId':id,'isCheck':'0','moduleCode':moduleCode,'orgCode':orgCode,'recordOrgCode':recordOrgCode,'prdCode':prdCode},
			url : rootPath + '/classify/check-moduleInfo.html',
			dataType : 'json',
			success : function(data) {
				if (data.success) {
						$("#cancelcheck"+id).hide();
			        	$("#check"+id).show();
			        	$("#delete"+id).show();
			        	$("#editModule"+id).show();
				} else {
					parent.layer.alert(data.message,{icon: 2});
				};
			}
		});
}
//复制规范要素
function copyModule(applyName,t){
	confirmClassify1();
	var moduleCode=$(t).parent().parent().find('td:eq(1)').text();
	$("#moduleId").val("");
	if(applyName==null || applyName !='NULL'){
		applyName="";
	}
	$("#applyName").val(applyName);
	$("#moduleCode").val(moduleCode);
	var moduleList  =moduleCode.split("|");
	var length= $("input[name='moduleValue']").length;
	for(var i=0;i<length;i++){
		$("#moduleValue"+i).val(moduleList[i]);
	}
	$("#saveModule").removeAttr("disabled");
	$("#cacelModule").removeAttr("disabled");
	$("#moduleInfos").show();
}


