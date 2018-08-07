/**
 * 工具组件 对原有的工具进行封装，自定义某方法统一处理
 * 
 * @author lanyuan 2014-12-12
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */
;
(function() {
	ly = {};
	ly.ajax = (function(params) {
		var pp = {
			loading : true,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.open({
					type : 1,
					title : "出错啦！",
					area : [ '300', '200' ],
					content : "<div id='layerError' style='color:red'>"
							+ XMLHttpRequest.responseText + "</div>"
				});
			}
		};
		$.extend(pp, params);
		$.ajax(pp);
	});
	ly.ajaxSubmit = (function(form, params) {// form 表单ID. params ajax参数
		var pp = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.open({
					type : 1,
					title : "出错啦！",
					area : [ '300', '200' ],
					content : "<div id='layerError' style='color:red'>"
							+ XMLHttpRequest.responseText + "</div>"
				});
			}
		};
		$.extend(pp, params);
		$(form).ajaxSubmit(pp);
	});
	CommnUtil = {
		/**
		 * ajax同步请求 返回一个html内容 dataType=html. 默认为html格式 如果想返回json.
		 * CommnUtil.ajax(url, data,"json")
		 * 
		 */
		ajax : function(url, data, dataType) {
			if (!CommnUtil.notNull(dataType)) {
				dataType = "html";
			}
			var html = '没有结果!';
			// 所以AJAX都必须使用ly.ajax..这里经过再次封装,统一处理..同时继承ajax所有属性
			if (url.indexOf("?") > -1) {
				url = url + "&_t=" + new Date();
			} else {
				url = url + "?_t=" + new Date();
			}
			ly.ajax({
				type : "post",
				data : data,
				url : url,
				dataType : dataType,// 这里的dataType就是返回回来的数据格式了html,xml,json
				async : false,
				cache : false,// 设置是否缓存，默认设置成为true，当需要每次刷新都需要执行数据库操作的话，需要设置成为false
				success : function(data) {
					html = data;
				}
			});
			return html;
		},
		/**
		 * 判断某对象不为空..返回true 否则 false
		 */
		notNull : function(obj) {
			if (obj === null) {
				return false;
			} else if (obj === undefined) {
				return false;
			} else if (obj === "undefined") {
				return false;
			} else if (obj === "") {
				return false;
			} else if (obj === "[]") {
				return false;
			} else if (obj === "{}") {
				return false;
			} else {
				return true;
			}

		},

		/**
		 * 判断某对象不为空..返回obj 否则 ""
		 */
		notEmpty : function(obj) {
			if (obj === null) {
				return "";
			} else if (obj === undefined) {
				return "";
			} else if (obj === "undefined") {
				return "";
			} else if (obj === "") {
				return "";
			} else if (obj === "[]") {
				return "";
			} else if (obj === "{}") {
				return "";
			} else {
				return obj;
			}

		},
		loadingImg : function() {
			var html = '<div class="alert alert-warning">'
					+ '<button type="button" class="close" data-dismiss="alert">'
					+ '<i class="ace-icon fa fa-times"></i></button><div style="text-align:center">'
					+ '<img src="' + rootPath
					+ '/common/images/loading.gif"/><div>' + '</div>';
			return html;
		},
		/**
		 * html标签转义
		 */
		htmlspecialchars : function(str) {
			var s = "";
			if (str.length == 0)
				return "";
			for ( var i = 0; i < str.length; i++) {
				switch (str.substr(i, 1)) {
				case "<":
					s += "&lt;";
					break;
				case ">":
					s += "&gt;";
					break;
				case "&":
					s += "&amp;";
					break;
				case " ":
					if (str.substr(i + 1, 1) == " ") {
						s += " &nbsp;";
						i++;
					} else
						s += " ";
					break;
				case "\"":
					s += "&quot;";
					break;
				case "\n":
					s += "";
					break;
				default:
					s += str.substr(i, 1);
					break;
				}
			}
		},
		/**
		 * in_array判断一个值是否在数组中
		 */
		in_array : function(array, string) {
			for ( var s = 0; s < array.length; s++) {
				thisEntry = array[s].toString();
				if (thisEntry == string) {
					return true;
				}
			}
			return false;
		},
		/**
		 * [{id:"",name:""}]
		 */
		getNameInArray:function(array, id){
			for ( var i = 0; i < array.length; i++) {
				if(array[i].id == id){
					return array[i].name;
				}
			}
		},
		ajaxGetJson:function(url, dataType){
			var data;
			$.ajax({
			  type: 'GET',
			  url: url,
			  dataType: dataType || 'json',
			  async:false,
			  success: function(result){
			  		if(result){
			  			data= result;
			  		}
			  }
			});
			return data;
		},
		//加载css和js文件
		IncludeFile_css:function(css_file) {
		    var html_doc = document.getElementsByTagName('head')[0];
		    css = document.createElement('link');
		    css.setAttribute('rel', 'stylesheet');
		    css.setAttribute('type', 'text/css');
		    css.setAttribute('href', css_file);
		    html_doc.appendChild(css);
		},
		IncludeFile_js:function(file) {
		    var html_doc = document.getElementsByTagName('head')[0];
		    js = document.createElement('script');
		    js.setAttribute('type', 'text/javascript');
		    js.setAttribute('src', file);
		    html_doc.appendChild(js);
		},
		//json 格式化
		format:function(msg) {
            var text = msg.split("\n").join(" ");
            var t = [];
            var tab = 0;
            var inString = false;
            for (var i = 0, len = text.length; i < len; i++) {
                var c = text.charAt(i);
                if (inString && c === inString) {
                    if (text.charAt(i - 1) !== '\\') {
                        inString = false;
                    }
                } else if (!inString && (c === '"' || c === "'")) {
                    inString = c;
                } else if (!inString && (c === ' ' || c === "\t")) {
                    c = '';
                } else if (!inString && c === ':') {
                    c += ' ';
                } else if (!inString && c === ',') {
                    c += "\n" + CommnUtil.space(tab * 2);
                } else if (!inString && (c === '[' || c === '{')) {
                    tab++;
                    c += "\n" + CommnUtil.space(tab * 2);
                } else if (!inString && (c === ']' || c === '}')) {
                    tab--;
                    c = "\n" + CommnUtil.space(tab * 2) + c;
                }
                t.push(c);
            }
            return t.join('');
        },
        space : function (len) {
            var t = [], i;
            for (i = 0; i < len; i++) {
                t.push(' ');
            }
            return t.join('');
        }
	};
})();
// 表单json格式化方法……不使用&拼接
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()
		// millisecond
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}
})(jQuery);
//重写ajax方法
(function($){  
    // 备份jquery的ajax方法
    var _ajax=$.ajax;  
    // 重写jquery的ajax方法
    $.ajax=function(opt){ 
        // 备份opt中error和success方法
    	opt.loading=true;
        var fn = {  
            error:function(XMLHttpRequest, textStatus, errorThrown){},  
            success:function(data, textStatus){},
            beforeSend:function(XMLHttpRequest){
            	if(opt.loading){
            		try{
            			opt.ajax_load =window.parent.layer.load(2);
            		}catch (e) {
            			//console.info(2);
            		}
            	}
            },
            complete:function(XMLHttpRequest,textStatus){
            	if(opt.loading){
	            	try{
	            		window.parent.layer.close(opt.ajax_load);
	            	}catch (e) {
					}
            	}
            }
        }
        if(opt.error){
            fn.error=opt.error;  
        }  
        if(opt.success){  
            fn.success=opt.success;  
        }
        // 扩展增强处理
        var _opt = $.extend(opt,{  
            error:function(XMLHttpRequest, textStatus, errorThrown){  
                // 错误方法增强处理
                fn.error(XMLHttpRequest, textStatus, errorThrown);  
            },
            success:function(data, textStatus){  
                // 成功回调方法增强处理
                fn.success(data, textStatus);
            },
            beforeSend:function(XMLHttpRequest){
            	fn.beforeSend(XMLHttpRequest);
            },
            complete:function(XMLHttpRequest,textStatus){
            	fn.complete(XMLHttpRequest, textStatus);
            }
        });
        _ajax(_opt); 
    };  
})(jQuery); 