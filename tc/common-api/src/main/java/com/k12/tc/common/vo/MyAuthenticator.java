package com.k12.tc.common.vo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * 项目名称：greenpass 
 * 类名称：MyAuthenticator 
 * 类描述：JsHuaZhao，处理页面提交的请求。 
 * 类描述：MyAuthenticator。 
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：孙海滨
 * 创建时间：2015年11月16日 下午12:01:37 
 * 修改人：孙海滨 
 * 修改时间：2015年11月16日 下午12:01:37 
 * 修改备注：
 * @version 1.0*
 */
public class MyAuthenticator extends Authenticator{

  String userName = null;
  String password = null;

  public MyAuthenticator() {
  }

  public MyAuthenticator(String username, String password) {
      this.userName = username;
      this.password = password;
  }

  protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(userName, password);
  }
}
