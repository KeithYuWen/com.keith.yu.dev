package com.k12.common.base.ftp;

import java.util.Vector;

import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * 项目名称：greenpass-api 
 * 类名称：SFTPClient 
 * 类描述：Jsk12，sftp连接客户端。
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：徐洪杰 
 * 创建时间：2016年7月22日 下午3:21:13 
 * 修改人：徐洪杰 
 * 修改时间：2016年7月22日 下午3:21:13 
 * 修改备注：
 * @version 1.0*
 */
public class SFTPClient extends SFTPHandler{
    public static SFTPClient sftpClient = null;  
    
    /** 
     * 
     * @param host 
     * @param port 
     * @param userName 
     * @param passWord 
     * @param timeOut 
     */  
  
    public SFTPClient(String host, int port, String userName, String passWord, int timeOut) {  
        this.jschHost = host;  
        this.jschPort = port;  
        this.jschUserName = userName;  
        this.jschPassWord = passWord;  
        this.jschTimeOut = timeOut;  
    }  
  
  
    /** 
     * 
     * @param host 
     * @param port 
     * @param userName 
     * @param passWord 
     * @param timeOut 
     * @return 
     */  
    public static SFTPClient getJschUtilInstance(String host,int port,String userName,String passWord,int timeOut) {  
        if(sftpClient == null) {  
            synchronized (SFTPClient.class) {  
                sftpClient = new SFTPClient(host,port,userName,passWord,timeOut);  
            }  
        }  
       return  sftpClient;  
    }  
  
    public static void main(String[] args) {
        SFTPClient sftpUtil =  SFTPClient.getJschUtilInstance("192.168.226.70", 22, "root", "123456", 0);
//        sftpUtil.upload("/ftp/ftpuser/osm-file", "E:\\temp\\k120725161555.csv");
            Vector<LsEntry> v = sftpUtil.listFiles("/ftp/ftpuser/osm-file/k1207261614*.csv");
//            File csvFile = (File)v.get(0);
//            sftpUtil.download(directory, v.get(0));
            System.out.println(v.get(0).getFilename());
//            sftpUtil.download("E:\\temp\\tracking\\", "/ftp/ftpuser/osm-file/" + v.get(0).getFilename());
            sftpUtil.delete("/ftp/ftpuser/osm-file/" + v.get(0).getFilename());
            
    }
}
