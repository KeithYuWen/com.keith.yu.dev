package com.k12.tc.common.vo;

import java.util.Date;
import java.util.Properties;

/**
 * 邮件发送对象
 * @author sunhaibin
 *
 */
public class MailSenderInfo {
  //发送邮件的服务器的IP和端口
  private String mailServerHost = "njhk.com.cn";
  private String mailServerPort = "25";
  // 邮件发送者的地址
  private String fromAddress = "services@greenpass-cn.com";
  
  //The personal name.
  private String personal = "跨易达";
  
  // 邮件接收者的地址
  private String toAddress;
  // 登陆邮件发送服务器的用户名和密码
  private String userName = "services";
  private String password = "ser.com";
  // 是否需要身份验证
  private boolean validate = true;
  // 邮件主题
  private String subject;
  // 邮件的文本内容
  private String content;
  // 邮件附件的文件名，从服务器上取到的名字
  private String[] attachFileNames;
  // 发送邮件附件的别名，即发送时的名字
  private String[] attachFileAlias;
  //发送时间
  private Date sendTime;
  //发送状态  0发送成功 1 发送失败
  private int sendStatus;
  //是否删除 0 未删除 1 删除
  private int mark;

  /**
   * 获得邮件会话属性
   */
  public Properties getProperties() {
      Properties p = new Properties();
      p.put("mail.smtp.host", mailServerHost);
      p.put("mail.smtp.auth", "true");
      return p;
  }

  public String getMailServerHost() {
      return mailServerHost;
  }

  public void setMailServerHost(String mailServerHost) {
      this.mailServerHost = mailServerHost;
  }

  public String getMailServerPort() {
      return mailServerPort;
  }

  public void setMailServerPort(String mailServerPort) {
      this.mailServerPort = mailServerPort;
  }

  public boolean isValidate() {
      return validate;
  }

  public void setValidate(boolean validate) {
      this.validate = validate;
  }

  public String[] getAttachFileNames() {
      return attachFileNames;
  }

  public void setAttachFileNames(String[] fileNames) {
      this.attachFileNames = fileNames;
  }

  public String[] getAttachFileAlias() {
      return attachFileAlias;
  }

  public void setAttachFileAlias(String[] attachFileAlias) {
      this.attachFileAlias = attachFileAlias;
  }

  public String getFromAddress() {
      return fromAddress;
  }

  public void setFromAddress(String fromAddress) {
      this.fromAddress = fromAddress;
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  public String getToAddress() {
      return toAddress;
  }

  public void setToAddress(String toAddress) {
      this.toAddress = toAddress;
  }

  public String getUserName() {
      return userName;
  }

  public void setUserName(String userName) {
      this.userName = userName;
  }

  public String getSubject() {
      return subject;
  }

  public void setSubject(String subject) {
      this.subject = subject;
  }

  public String getContent() {
      return content;
  }

  public void setContent(String textContent) {
      this.content = textContent;
  }

  public String getPersonal() {
      return personal;
  }

  public void setPersonal(String personal) {
      this.personal = personal;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  public int getSendStatus() {
    return sendStatus;
  }

  public void setSendStatus(int sendStatus) {
    this.sendStatus = sendStatus;
  }

  public int getMark() {
    return mark;
  }

  public void setMark(int mark) {
    this.mark = mark;
  }
  
  
}
