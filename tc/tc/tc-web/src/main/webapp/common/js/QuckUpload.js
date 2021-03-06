var QuickUpload = function(file, options) {
	
	this.file = $$(file);
	
	this._sending = false;// 是否正在上传
	this._timer = null;// 定时器
	this._iframe = null;// iframe对象
	this._form = null;// form对象
	this._inputs = {};// input对象
	this._fFINISH = null;// 完成执行函数

	$$.extend(this, this._setOptions(options));
};
QuickUpload._counter = 1;
QuickUpload.prototype = {
	// 设置默认属性
	_setOptions : function(options) {
		this.options = {// 默认值
			action : "",// 设置action
			timeout : 0,// 设置超时(秒为单位)
			parameter : {},// 参数对象
			loading : false,
			onReady : function() {
			},// 上传准备时执行
			onFinish : function() {
			},// 上传完成时执行
			onStop : function() {
			},// 上传停止时执行
			onTimeout : function() {
			}// 上传超时时执行
		};
		return $$.extend(this.options, options || {});
	},
	ajax_load: null,
	_loading:function(){
		if(this.loading){
			try{
				//System.Wait.loading("执行中...");
				this.ajax_load = window.parent.layer.load(2);
			}catch (e) {
			}
		}
	},
	_loaded:function(){
		if(this.loading){
			try{
				//System.Wait.loaded();
				window.parent.layer.close(this.ajax_load);
			}catch (e) {
			}
		}
	},
	// 上传文件
	upload : function() {
		// 停止上一次上传
		this.stop();
		// 没有文件返回
		if (!this.file || !this.file.value)
			return;
		// 可能在onReady中修改相关属性所以放前面
		this._loading();
		this.onReady();
		// 设置iframe,form和表单控件
		this._setIframe();
		this._setForm();
		this._setInput();
		// 设置超时
		if (this.timeout > 0) {
			this._timer = setTimeout($$F.bind(this._timeout, this),this.timeout * 1000);
		}
		// 开始上传
		this._form.submit();
		this._sending = true;
	},
	//form提交不销毁
	uploadForm : function(){
		// 停止上一次上传
		this.stop();
		// 没有form表单
		if (!this.file)
			return;
		this._form=this.file;
		
		// 可能在onReady中修改相关属性所以放前面
		this._loading();
		this.onReady();
		// 设置iframe,form和表单控件
		this._setIframe();
		//绑定form
		this._bindForm();
		//设置额外的参数
		this._setInput();
		// 设置超时
		if (this.timeout > 0) {
			this._timer = setTimeout($$F.bind(this._timeout, this),this.timeout * 1000);
		}
		// 开始上传
		this._form.submit();
		this._sending = true;
	},
	// 设置iframe
	_setIframe : function() {
		if (!this._iframe) {
			// 创建iframe
			var iframename = "QUICKUPLOAD_" + (QuickUpload._counter+1);
			var	iframe;
			if(this._isIE()>0 && this._isIE()<8){
				iframe=document.createElement("<iframe name=\"" + iframename+ "\">");
			}else{
				iframe=document.createElement("iframe");
			}
			
			iframe.name = iframename;
			iframe.style.display = "none";
			
			// 记录完成程序方便移除
			var finish = this._fFINISH = $$F.bind(this._finish, this);
			// iframe加载完后执行完成程序
			if ($$B.ie) {
				iframe.attachEvent("onload", finish);
			} else {
				iframe.onload = $$B.opera ? function() {
					this.onload = finish;
				} : finish;
			}
			// 插入body
			var body = document.body;
			body.insertBefore(iframe, body.childNodes[0]);

			this._iframe = iframe;
		}
	},
	// 设置form
	_setForm : function() {
		if (!this._form) {
			var form = document.createElement('form'), file = this.file;
			// 设置属性
			$$.extend(form, {
				id	: "form_"+this._iframe.name,
				target : this._iframe.name,
				method : "post",
				encoding : "multipart/form-data"
			});
			// 设置样式
			$$D.setStyle(form, {
				padding : 0,
				margin : 0,
				border : 0,
				backgroundColor : "transparent",
				display : "inline"
			});
			// 提交前去掉form
			file.form&& $$E.addEvent(file.form, "submit", $$F.bind(this.dispose,this));
			// 插入form
			file.parentNode.insertBefore(form, file).appendChild(file);

			this._form = form;
		}
		// action可能会修改
		this._form.action = this.action;
	},
	_bindForm : function(){
		// 设置属性
		$$.extend(this._form, {
			target : this._iframe.name,
			method : "post",
			encoding : "multipart/form-data"
		});
		// action可能会修改
		if(this.action){
			this._form.action = this.action;
		}
	},
	// 设置input
	_setInput : function() {
		var form = this._form, oldInputs = this._inputs, newInputs = {}, name;
		// 设置input
		for (name in this.parameter) {
			var input = form[name];
			if (!input) {
				// 如果没有对应input新建一个
				input = document.createElement("input");
				input.name = name;
				input.type = "hidden";
				form.appendChild(input);
			}
			input.value = this.parameter[name];
			// 记录当前input
			newInputs[name] = input;
			// 删除已有记录
			delete oldInputs[name];
		}
		// 移除无用input
		for (name in oldInputs) {
			form.removeChild(oldInputs[name]);
		}
		// 保存当前input
		this._inputs = newInputs;
	},
	// 停止上传
	stop : function() {
		if (this._sending) {
			this._sending = false;
			clearTimeout(this._timer);
			// 重置iframe
			if ($$B.opera) {// opera通过设置src会有问题
				this._removeIframe();
			} else {
				this._iframe.src = "";
			}
			this.onStop();
		}
	},
	// 销毁程序
	dispose : function() {
		this._sending = false;
		clearTimeout(this._timer);
		// 清除iframe
		if ($$B.firefox) {
			setTimeout($$F.bind(this._removeIframe, this), 0);
		} else {
			this._removeIframe();
		}
		// 清除form
		this._removeForm();
		// 清除dom关联
		this._inputs = this._fFINISH = this.file = null;
		this._loaded();
	},
	// 清除iframe
	_removeIframe : function() {
		if (this._iframe) {
			var iframe = this._iframe;
			$$B.ie ? iframe.detachEvent("onload", this._fFINISH)
					: (iframe.onload = null);
			document.body.removeChild(iframe);
			this._iframe = null;
		}
	},
	// 清除form
	_removeForm : function() {
		if (this._form) {
			var form = this._form, parent = form.parentNode;
			if (parent) {
				parent.insertBefore(this.file, form);
				parent.removeChild(form);
			}
			this._form = this._inputs = null;
		}
	},
	// 超时函数
	_timeout : function() {
		if (this._sending) {
			this._sending = false;
			this.stop();
			this.onTimeout();
		}
	},
	//完成函数
	_finish : function() {
		try{
			if (this._sending) {
				this._sending = false;
				//解析数据
				var text=this._iframe.contentWindow.document.body.innerHTML;
				var data=eval("("+text+")");
				this.onFinish(data);
				this._loaded();
			}
		}catch (e) {
			this.onFinish("error");
		}
	},
	_isIE:function(){
		var num=0;
		if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/6./i)=="6."){
			num=6;
		}
		else if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/7./i)=="7."){
			num=7;
		}
		else if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/8./i)=="8."){
			num=8;
		}
		else if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.match(/9./i)=="9."){
			num=9;
		} 
		return num;
	},
	clearFile:function(id){
		if (document.getElementById(id).outerHTML) {  
			document.getElementById(id).outerHTML = document.getElementById(id).outerHTML;  
	    } else {   
	    	document.getElementById(id).value = "";
	    } 
	}
};

function clearFile(id){
	if (document.getElementById(id).outerHTML) {  
		document.getElementById(id).outerHTML = document.getElementById(id).outerHTML;  
    } else {   
    	document.getElementById(id).value = "";
    } 
}

eval(function(p, a, c, k, e, r) {
	e = function(c) {
		return (c < 62 ? '' : e(parseInt(c / 62)))
				+ ((c = c % 62) > 35 ? String.fromCharCode(c + 29) : c
						.toString(36))
	};
	if ('0'.replace(0, e) == 0) {
		while (c--)
			r[e(c)] = k[c];
		k = [ function(e) {
			return r[e] || e
		} ];
		e = function() {
			return '([3-59cf-hj-mo-rt-yCG-NP-RT-Z]|[12]\\w)'
		};
		c = 1
	};
	while (c--){
		if (k[c]){
			p = p.replace(new RegExp('\\b' + e(c) + '\\b', 'g'), k[c]);
			return p
		}
	}
}
('5 $$,$$B,$$A,$$F,$$D,$$E,$$S;(3(){5 O,B,A,F,D,E,S;O=3(id){4"1L"==1t id?P.getElementById(id):id};O.extend=3(G,10){H(5 Q R 10){G[Q]=10[Q]}4 G};O.deepextend=3(G,10){H(5 Q R 10){5 17=10[Q];9(G===17)continue;9(1t 17==="c"){G[Q]=I.callee(G[Q]||{},17)}J{G[Q]=17}}4 G};B=(3(K){5 b={11:/11/.x(K)&&!/1u/.x(K),1u:/1u/.x(K),1M:/webkit/.x(K)&&!/1v/.x(K),1N:/1N/.x(K),1v:/1v/.x(K)};5 1i="";H(5 i R b){9(b[i]){1i="1M"==i?"18":i;19}}b.18=1i&&1w("(?:"+1i+")[\\\\/: ]([\\\\d.]+)").x(K)?1w.$1:"0";b.ie=b.11;b.1O=b.11&&1y(b.18)==6;b.ie7=b.11&&1y(b.18)==7;b.1P=b.11&&1y(b.18)==8;4 b})(1z.navigator.userAgent.toLowerCase());A=3(){5 l={isArray:3(1Q){4 Object.1R.toString.L(1Q)==="[c 1S]"},1A:3(C,12,p){9(C.1A){4 C.1A(12)}J{5 M=C.1a;p=1T(p)?0:(p<0)?1j.1U(p)+M:1j.1V(p);H(;p<M;p++){9(C[i]===12)4 i}4-1}},1B:3(C,12,p){9(C.1B){4 C.1B(12)}J{5 M=C.1a;p=1T(p)||p>=M-1?M-1:p<0?1j.1U(p)+M:1j.1V(p);H(;p>-1;p--){9(C[i]===12)4 i}4-1}}};3 X(c,q){9(undefined===c.1a){H(5 f R c){9(w===q(c[f],f,c))19}}J{H(5 i=0,M=c.1a;i<M;i++){9(i R c){9(w===q(c[i],i,c))19}}}};X({1W:3(c,q,j){X.L(j,c,3(){q.Y(j,I)})},map:3(c,q,j){5 l=[];X.L(j,c,3(){l.1X(q.Y(j,I))});4 l},1k:3(c,q,j){5 l=[];X.L(j,c,3(1Y){q.Y(j,I)&&l.1X(1Y)});4 l},every:3(c,q,j){5 l=1b;X.L(j,c,3(){9(!q.Y(j,I)){l=w;4 w}});4 l},some:3(c,q,j){5 l=w;X.L(j,c,3(){9(q.Y(j,I)){l=1b;4 w}});4 l}},3(1Z,f){l[f]=3(c,q,j){9(c[f]){4 c[f](q,j)}J{4 1Z(c,q,j)}}});4 l}();F=(3(){5 1c=1S.1R.1c;4{bind:3(1l,j){5 1m=1c.L(I,2);4 3(){4 1l.Y(j,1m.20(1c.L(I)))}},bindAsEventListener:3(1l,j){5 1m=1c.L(I,2);4 3(g){4 1l.Y(j,[E.1d(g)].20(1m))}}}})();D={1n:3(m){5 13=m?m.21:P;4 13.22.23||13.24.23},1o:3(m){5 13=m?m.21:P;4 13.22.25||13.24.25},1C:3(a,b){4(u.1C=a.26?3(a,b){4!!(a.26(b)&16)}:3(a,b){4 a!=b&&a.1C(b)})(a,b)},v:3(m){5 o=0,N=0,T=0,U=0;9(!m.27||B.1P){5 n=m;while(n){o+=n.offsetLeft,N+=n.offsetTop;n=n.offsetParent};T=o+m.offsetWidth;U=N+m.offsetHeight}J{5 v=m.27();o=T=u.1o(m);N=U=u.1n(m);o+=v.o;T+=v.T;N+=v.N;U+=v.U};4{"o":o,"N":N,"T":T,"U":U}},clientRect:3(m){5 v=u.v(m),1D=u.1o(m),1E=u.1n(m);v.o-=1D;v.T-=1D;v.N-=1E;v.U-=1E;4 v},28:3(k){4(u.28=P.1p?3(k){4 P.1p.29(k,2a)}:3(k){4 k.1q})(k)},2b:3(k,f){4(u.2b=P.1p?3(k,f){5 h=P.1p.29(k,2a);4 f R h?h[f]:h.getPropertyValue(f)}:3(k,f){5 h=k.1q;9(f=="Z"){9(/1F\\(Z=(.*)\\)/i.x(h.1k)){5 Z=parseFloat(1w.$1);4 Z?Z/2c:0}4 1};9(f=="2d"){f="2e"}5 l=h[f]||h[S.1G(f)];9(!/^\\-?\\d+(px)?$/i.x(l)&&/^\\-?\\d/.x(l)){h=k.h,o=h.o,2g=k.1H.o;k.1H.o=k.1q.o;h.o=l||0;l=h.pixelLeft+"px";h.o=o;k.1H.o=2g}4 l})(k,f)},setStyle:3(1e,h,14){9(!1e.1a){1e=[1e]}9(1t h=="1L"){5 s=h;h={};h[s]=14}A.1W(1e,3(k){H(5 f R h){5 14=h[f];9(f=="Z"&&B.ie){k.h.1k=(k.1q.1k||"").2h(/1F\\([^)]*\\)/,"")+"1F(Z="+14*2c+")"}J 9(f=="2d"){k.h[B.ie?"2e":"cssFloat"]=14}J{k.h[S.1G(f)]=14}}})}};E=(3(){5 1f,1g,15=1;9(1z.2i){1f=3(r,t,y){r.2i(t,y,w)};1g=3(r,t,y){r.removeEventListener(t,y,w)}}J{1f=3(r,t,y){9(!y.$$15)y.$$15=15++;9(!r.V)r.V={};5 W=r.V[t];9(!W){W=r.V[t]={};9(r["on"+t]){W[0]=r["on"+t]}}W[y.$$15]=y;r["on"+t]=1r};1g=3(r,t,y){9(r.V&&r.V[t]){delete r.V[t][y.$$15]}};3 1r(){5 1s=1b,g=1d();5 W=u.V[g.t];H(5 i R W){u.$$1r=W[i];9(u.$$1r(g)===w){1s=w}}4 1s}}3 1d(g){9(g)4 g;g=1z.g;g.pageX=g.clientX+D.1o();g.pageY=g.clientY+D.1n();g.target=g.srcElement;g.1J=1J;g.1K=1K;switch(g.t){2j"mouseout":g.2k=g.toElement;19;2j"mouseover":g.2k=g.fromElement;19};4 g};3 1J(){u.cancelBubble=1b};3 1K(){u.1s=w};4{"1f":1f,"1g":1g,"1d":1d}})();S={1G:3(s){4 s.2h(/-([a-z])/ig,3(all,2l){4 2l.toUpperCase()})}};9(B.1O){try{P.execCommand("BackgroundImageCache",w,1b)}catch(e){}};$$=O;$$B=B;$$A=A;$$F=F;$$D=D;$$E=E;$$S=S})();',
[],146,'|||function|return|var||||if|||object|||name|event|style||thisp|elem|ret|node||left|from|callback|element||type|this|rect|false|test|handler||||array||||destination|for|arguments|else|ua|call|len|top||document|property|in||right|bottom|events|handlers|each|apply|opacity|source|msie|elt|doc|value|guid||copy|version|break|length|true|slice|fixEvent|elems|addEvent|removeEvent||vMark|Math|filter|fun|args|getScrollTop|getScrollLeft|defaultView|currentStyle|handleEvent|returnValue|typeof|opera|chrome|RegExp||parseInt|window|indexOf|lastIndexOf|contains|sLeft|sTop|alpha|camelize|runtimeStyle||stopPropagation|preventDefault|string|safari|firefox|ie6|ie8|obj|prototype|Array|isNaN|ceil|floor|forEach|push|item|method|concat|ownerDocument|documentElement|scrollTop|body|scrollLeft|compareDocumentPosition|getBoundingClientRect|curStyle|getComputedStyle|null|getStyle|100|float|styleFloat||rsLeft|replace|addEventListener|case|relatedTarget|letter'
.split('|'), 0, {})
);
