package com.k12.common.base.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.StringUtils;

public class FtpClient {
    private FTPClient ftp;

    /**
     * 
     * @param path
     *            上传到ftp服务器哪个路径下
     * @param addr
     *            地址
     * @param port
     *            端口号
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return
     * @throws Exception
     */
    public boolean connect(String path, String addr, int port, String username, String password) throws Exception {
        boolean result = false;
        ftp = new FTPClient();
        // 保存FTP控制连接使用的字符集，必须在连接前设置  
        ftp.setControlEncoding("UTF-8"); 
        int reply;
        ftp.connect(addr, port);
        ftp.login(username, password);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return result;
        }
        ftp.changeWorkingDirectory(path);
        result = true;
        return result;
    }

    public boolean logout() throws IOException {
    	return ftp.logout(); 
    }
    
    public boolean deleteFile(String fileName) throws IOException {
    	return ftp.deleteFile(fileName); 
    }
    
    /**
     * 
     * @param file
     *            上传的文件或文件夹
     * @throws Exception
     */
    public void upload(File file) throws Exception {
        if (file.isDirectory()) {
            ftp.makeDirectory(file.getName());
            ftp.changeWorkingDirectory(file.getName());
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                File file1 = new File(file.getPath() + "\\" + files[i]);
                if (file1.isDirectory()) {
                    upload(file1);
                    ftp.changeToParentDirectory();
                } else {
                    File file2 = new File(file.getPath() + "\\" + files[i]);
                    FileInputStream input = new FileInputStream(file2);
                    ftp.storeFile(file2.getName(), input);
                    input.close();
                }
            }
        } else {
            File file2 = new File(file.getPath());
            FileInputStream input = new FileInputStream(file2);
            ftp.storeFile(file2.getName(), input);
            input.close();
        }
    }
    
    /**
     * 上传文件至ftp
     * @param in 文件输入流
     * @param fileName 文件名
     * @throws Exception
     */
    public boolean upload(InputStream in, String fileName) throws Exception {
    	System.out.println("remote file name:"+fileName);
    	System.out.println(in.toString());
    	boolean result = ftp.storeFile(fileName, in);
        return result;
    }
    
    public boolean exist(String path, final String fileName) throws IOException {
    	if (StringUtils.isEmpty(path) || StringUtils.isEmpty(fileName)) {
    		return false;
    	}
    	FTPFile[] files = ftp.listFiles(path, new FTPFileFilter() {
			@Override
			public boolean accept(FTPFile arg0) {
				if (fileName.equals(arg0.getName())) {
					return true;
				}
				return false;
			}
		});
    	if (null == files || files.length == 0) {
    		return false;
    	}
    	return true;
    }
    
    public ByteArrayOutputStream download(String remotePath, String fileName) throws IOException { 
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
        ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录 
        FTPFile[] fs = ftp.listFiles(); 
        for (FTPFile ff : fs){ 
            if (ff.getName().equals(fileName)) { 
                if (ftp.retrieveFile(fileName, out)) {
                	return out;
                }
            }
        }
        return null; 
    }
    
    public void close() throws IOException {
		ftp.logout();
		if (ftp.isConnected()) {
			ftp.disconnect();
		}
    }
    
    /**
     * makeDirectory(创建目录) 
     * @param pathname
     * @return
     * @author LDz
     */
    public boolean makeDirectory(String pathname){
    	try {
			ftp.makeDirectory(pathname);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public FTPClient getFtp() {
		return ftp;
	}

	public static void main(String[] args) throws Exception {
        FtpClient t = new FtpClient();
        t.connect("/", "192.168.226.78", 21, "ftpuser", "123456");
        File file = new File("E:\\xianxia_ui_kaishi_kaishi1.png");
        t.upload(file);
        System.out.println(t.logout());
        
    }
}
