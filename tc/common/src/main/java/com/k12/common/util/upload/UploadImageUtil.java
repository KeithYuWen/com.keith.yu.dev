package com.k12.common.util.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.k12.common.base.ftp.FtpClient;
import com.k12.common.exception.BusinessException;
import com.k12.common.exception.ErrorCode;
import com.k12.common.init.SystemParamInit;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;

public class UploadImageUtil {
	
	private static int maxIndex=0;
	
	/**
	 * 缓存目录
	 */
	public static String tempDirectory="images/temp/";
		
	/**
	 * 将字节流转为文件
	 * @author LiuDZ
	 * @param image
	 * @return
	 * @date 2014-12-30
	 */
	public static File toFile(String path, HttpServletRequest request) {
		String filepath = request.getSession().getServletContext().getRealPath("/")+ tempDirectory + path;
		InputStream in = null;
		FileOutputStream fos = null;
		File f = null;
		try {
			in = (InputStream) request.getAttribute("InputStream");
			BufferedInputStream buf = new BufferedInputStream(in);
			fos = new FileOutputStream(filepath);
			byte[] buffer = new byte[1024];
			int iRead;
			while ((iRead = buf.read(buffer)) != -1) {
				fos.write(buffer, 0, iRead);
			}
			f = new File(filepath);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return f;
	}

	/**
	 * 删除文件
	 * 
	 * @author LiuDZ
	 * @return
	 * @date 2014-12-30
	 */
	public static boolean delFile(String path, HttpServletRequest request) {
		String filepath = request.getSession().getServletContext().getRealPath("/")+ tempDirectory + path;
		File file = new File(filepath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * fileForFtpTemp(将字节流上传到ftp，作为暂存文件) 
	 * @param in 字节流
	 * @param fileDirectory 当前根目录
	 * @param path	目录
	 * @param index	文件名标识 ： 例子，1 >>> temp_1
	 * @param suffix 文件后缀 如（jpg、png）
	 * @return 返回暂存文件名
	 * @author LDz
	 */
	public static String fileForFtpTemp(InputStream in,String fileDirectory, String path,String index,String suffix) {
		if (in == null) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件不能为空");
		}
		try {
			// 创建ftp连接
			FtpClient t = new FtpClient();
			t.connect(SystemParamInit.getDirPath() + fileDirectory,
					SystemParamInit.getFtpHost(),
					SystemParamInit.getFtpPort(),
					SystemParamInit.getFtpUserName(),
					SystemParamInit.getFtpPassword());
			//创建文件夹
			t.makeDirectory(path);
			t.getFtp().changeWorkingDirectory(path);
			String name="temp_"+index+suffix;
			t.upload(in, name);
			t.logout();
			return name;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.VERIFY_ERROR,"文件上传失败");
		}
	}
	
	/**
	 * fileForFtpTemp(将字节流上传到ftp，作为暂存文件) 
	 * @param in 字节流
	 * @param fileDirectory 当前根目录
	 * @param path	目录
	 * @param index	文件名标识 ： 例子，1 >>> temp_1
	 * @param suffix 文件后缀 如（jpg、png）
	 * @return 返回暂存文件名
	 * @author LDz
	 */
	public static String uploadFile(InputStream in, String subDirectory, String fileName) {
		if (in == null || subDirectory == null || fileName == null) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件不能为空");
		}
		try {
			// 创建ftp连接
			FtpClient t = new FtpClient();
			t.connect(SystemParamInit.getDirPath(),
					SystemParamInit.getFtpHost(),
					SystemParamInit.getFtpPort(),
					SystemParamInit.getFtpUserName(),
					SystemParamInit.getFtpPassword());
			//创建文件夹
			t.makeDirectory(subDirectory);
			t.getFtp().changeWorkingDirectory(subDirectory);
			t.upload(in, fileName);
			t.logout();
			return fileName;
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR,"文件上传失败", e);
		}
	}
	
	/**
	 * toImageForFtp(将暂存文件变更为有效文件) 
	 * @param indexArr 文件名标识数组
	 * @param fileDirectory 当前根目录
	 * @param path 目录
	 * @return 返回 对应的有效文件名
	 * @author LDz
	 */
	public static String[] tempToValidForFtp(String[] indexArr,String  fileDirectory, String path ) {
		if (indexArr == null || indexArr.length <= 0) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件不能为空");
		}
		String[] nameArr=new String[indexArr.length];
		try {
			// 创建ftp连接
			FtpClient t = new FtpClient();
			t.connect(SystemParamInit.getDirPath() + fileDirectory,
					SystemParamInit.getFtpHost(),
					SystemParamInit.getFtpPort(),
					SystemParamInit.getFtpUserName(),
					SystemParamInit.getFtpPassword());
			//切换目录
			t.getFtp().changeWorkingDirectory(path);
			//获取文件夹下的文件名
			String[] fileNames = t.getFtp().listNames();
			System.out.println(JSONArray.toJSON(fileNames));
			//重命名
			for (int i = 0; i < indexArr.length; i++) {
				String index = indexArr[i];
				try {
					String tempName = getTempName(index, fileNames);
					String suffix = tempName.substring(tempName.indexOf("."));
					String validName = arrContainRetName(fileNames);
					t.getFtp().rename(tempName, validName + suffix);
					nameArr[i]=validName + suffix;
				} catch (Exception e) {
					e.printStackTrace();
					throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件发生错误,请重新选择上传");
				}
			}
			//删除掉其他的暂存文件
			String[] fileNamesA = t.getFtp().listNames();
			for (int i = 0; i < fileNamesA.length; i++) {
				if(fileNamesA[i].indexOf("temp_")>=0){
					t.getFtp().deleteFile(fileNamesA[i]);
				}
			}
			t.logout();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.VERIFY_ERROR,"文件上传失败");
		}
		return nameArr;
	}
	
	/**
	 * arrContainRetName(获取不重复的有效文件名) 
	 * @param arr
	 * @param path
	 * @return
	 * @author LDz
	 */
	private static String arrContainRetName(String[] arr){
		maxIndex++;
		if(arr != null && arr.length > 0){
			int bigIndex=maxIndex;
			do{
				bigIndex=maxIndex;
				for (int i = 0; i < arr.length; i++) {
					if((arr[i].substring(0,arr[i].indexOf("."))).equals(String.valueOf(maxIndex))){
						maxIndex++;
						break;
					}
				}
			}while(bigIndex!=maxIndex);
		}
		return String.valueOf(maxIndex);
	}
	
	/**
	 * arrContainRetName(获取暂存文件名) 
	 * @param index
	 * @param arr
	 * @return
	 * @author LDz
	 */
	private static String getTempName(String index,String[] arr){
		if(arr==null || arr.length<=0){
			throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件丢失，请重新上传");
		}
		for (int i = 0; i < arr.length; i++) {
			if((arr[i].substring(0,arr[i].indexOf("."))).equals("temp_"+index)){
				return arr[i];
			}
		}
		throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件丢失，请重新上传");
	}

	/**
	 * fileForFtpTemp(将字节流上传到ftp，作为暂存文件) 
	 */
	public static void uploadImgFile(MultipartHttpServletRequest multiRequest ,List<String> fileNames) {
		if (multiRequest == null || fileNames == null) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件不能为空");
		}
		String  year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		// 创建ftp连接
		FtpClient t = new FtpClient();
		try {
			t.connect(SystemParamInit.getDirPath() + SystemParamInit.getDownloadPicUrl() , 
					SystemParamInit.getFtpHost(),
                    SystemParamInit.getFtpPort(), 
                    SystemParamInit.getFtpUserName(),
                    SystemParamInit.getFtpPassword());
			//创建目录
			t.makeDirectory(year);
			//切换新创建目录
			t.getFtp().changeWorkingDirectory(year);
			
			Iterator<String> iter = multiRequest.getFileNames();
			int i = 0;
			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file.getSize() != 0) {
					String path = SystemParamInit.getLocalFileTempPath();
		            File localFile = new File(path);
		            if (!localFile.exists()) {
		                localFile.mkdirs();
		            }
		            // 写文件到本地
		            File upFile = new File(localFile + "/" + fileNames.get(i));
		            file.transferTo(upFile);
		            // 上传到图片服务器
		            t.upload(upFile);
		            // 删除临时文件
		            if (upFile.isFile() && upFile.exists()) {
		                upFile.delete();
		            }
				}
	            i++;
			}
			t.logout();
		} catch (Exception e) {
			try {
				t.logout();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw new BusinessException(ErrorCode.VERIFY_ERROR,"文件上传失败", e);
		}
	}
	
	/***
	 * 从FTP服务器下载文件 
	 * @param ftpPath
	 * @param localPath
	 * @param fileName
	 */
    public static File downloadFtpFile(String ftpPath, String localPath,String fileName) { 
        FileOutputStream fos = null;
        File f = null;
        /*// 创建ftp连接
        FtpClient t = new FtpClient();
        try {
            t.connect(SystemParamInit.getDirPath() + SystemParamInit.getDownloadPicUrl() , 
                    SystemParamInit.getFtpHost(),
                    SystemParamInit.getFtpPort(), 
                    SystemParamInit.getFtpUserName(),
                    SystemParamInit.getFtpPassword()); 
            t.getFtp().changeWorkingDirectory(ftpPath);
            ByteArrayOutputStream out = t.download(ftpPath, fileName);
            byte[] content = out.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            BufferedInputStream buf = new BufferedInputStream(is);
            fos = new FileOutputStream(localPath);
            byte[] buffer = new byte[1024];
            int iRead;
            while ((iRead = buf.read(buffer)) != -1) {
                fos.write(buffer, 0, iRead);
            }
            f = new File(localPath); 
            t.logout(); 
            
            
            t.getFtp().setControlEncoding("UTF-8"); // 中文支持
            t.getFtp().setFileType(FTPClient.BINARY_FILE_TYPE);
            t.getFtp().enterLocalPassiveMode();
            t.getFtp().changeWorkingDirectory(ftpPath);

            f = new File(localPath + File.separatorChar + fileName);
            OutputStream os = new FileOutputStream(f);
            t.getFtp().retrieveFile(fileName, os);
            os.close();
            t.logout();
            
            
        } catch (Exception e) { 
            System.out.println("文件下载错误。");
            e.printStackTrace();
            return null;
        }finally{
            try {
                //fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/  
        return f;
    }
    /**
	 * uploadFileToFtp(将字节流上传到ftp，作为暂存文件) 
	 * @param in 字节流
	 * @param fileDirectory 当前根目录
	 * @param path	目录
	 * @param index	文件名标识 ： 例子，1 >>> temp_1
	 * @param suffix 文件后缀 如（jpg、png）
	 * @return 返回暂存文件名
	 * @author LDz
	 */
	public static String uploadFileToFtp(InputStream in, String subDirectory,String fileName, String orgCode, String prdCode) {
		if (in == null || subDirectory == null || fileName == null) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件不能为空");
		}
		try {
			// 创建ftp连接
			FtpClient t = new FtpClient();
			t.connect(SystemParamInit.getDirPath() + subDirectory,
					SystemParamInit.getFtpHost(),
					SystemParamInit.getFtpPort(),
					SystemParamInit.getFtpUserName(),
					SystemParamInit.getFtpPassword());
			//创建文件夹
			t.makeDirectory(orgCode);
			t.getFtp().changeWorkingDirectory(orgCode);
			t.makeDirectory(prdCode);
			t.getFtp().changeWorkingDirectory(prdCode);
			t.upload(in, fileName);
			t.logout();
			return fileName;
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR,"文件上传失败", e);
		}
		
	}
	
	/**
	 * 删除文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static void delFileFromFtp(String path,String fileName) {
		if ( path == null || fileName == null) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR, "文件不能为空");
		}
		try {
			// 创建ftp连接
			FtpClient t = new FtpClient();
			t.connect(SystemParamInit.getDirPath() + path,
					SystemParamInit.getFtpHost(),
					SystemParamInit.getFtpPort(),
					SystemParamInit.getFtpUserName(),
					SystemParamInit.getFtpPassword());
			//删除
			t.deleteFile(fileName);
			t.logout();
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.VERIFY_ERROR,"文件删除失败", e);
		}
		
	}
}
