package com.k12.common.util.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.k12.common.init.SystemParamInit;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {  
    
/* Description: 从FTP服务器下载文件  
* @param url FTP服务器hostname 
* @param port FTP服务器端口 
* @param username FTP登录账号 
* @param password FTP登录密码 
* @param remotePath FTP服务器上的相对路径 
* @param fileName 要下载的文件名 
* @param localPath 下载后保存到本地的路径 
* @return 
*/  
public static boolean downFile(String remotePath,String fileName,String localPath) {
   String url = SystemParamInit.getFtpHost() ;
   int port = SystemParamInit.getFtpPort();
   String username = SystemParamInit.getFtpUserName();
   String password =  SystemParamInit.getFtpPassword();
   boolean success = false;  
   FTPClient ftp = new FTPClient();  
   try {  
       int reply;  
       ftp.connect(url, port);  
       //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
       ftp.login(username, password);//登录  
       reply = ftp.getReplyCode();  
       if (!FTPReply.isPositiveCompletion(reply)) {  
           ftp.disconnect();  
           return success;  
       }  
       ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录  
       FTPFile[] fs = ftp.listFiles();  
       for(FTPFile ff:fs){  
           if(ff.getName().equals(fileName)){  
               File localFile = new File(localPath+"/"+ff.getName());   
               OutputStream is = new FileOutputStream(localFile);   
               ftp.retrieveFile(ff.getName(), is);  
               is.close();  
           }  
       }  
         
       ftp.logout();  
       success = true;  
   } catch (IOException e) {  
       e.printStackTrace();  
   } finally {  
       if (ftp.isConnected()) {  
           try {  
               ftp.disconnect();  
           } catch (IOException ioe) {  
           }  
       }  
   }  
   return success;  
}

    
    /** 
     * FTP下载单个文件测试 
     */  
    public static void fileDownloadByFtp(String remotePath,String fileName,String localPath) {  
        String url = SystemParamInit.getFtpHost() ; 
        int port = SystemParamInit.getFtpPort();
        String username = SystemParamInit.getFtpUserName();
        String password =  SystemParamInit.getFtpPassword();
        
        FTPClient ftpClient = new FTPClient();  
        FileOutputStream fos = null;  
    
        try {  
            ftpClient.connect(url,port);  
            ftpClient.login(username, password);  
    
            String remoteFileName = remotePath+fileName;   
            fos = new FileOutputStream(localPath+File.separator+fileName);  
            ftpClient.setBufferSize(1024);  
            // 设置文件类型（二进制）  
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
            ftpClient.retrieveFile(remoteFileName, fos);  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw new RuntimeException("FTP客户端出错！", e);  
        } finally {  
            IOUtils.closeQuietly(fos);  
            try {  
                ftpClient.disconnect();  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException("关闭FTP连接发生异常！", e);  
            }  
        }  
    }  
}  