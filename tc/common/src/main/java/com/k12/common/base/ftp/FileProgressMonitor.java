package com.k12.common.base.ftp;

import com.jcraft.jsch.SftpProgressMonitor;
/**
 * 项目名称：greenpass-api 
 * 类名称：FileProgressMonitor 
 * 类描述：Jsk12，sftp进度监控。
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：徐洪杰 
 * 创建时间：2016年7月22日 下午3:17:18 
 * 修改人：徐洪杰 
 * 修改时间：2016年7月22日 下午3:17:18 
 * 修改备注：
 * @version 1.0*
 */
public class FileProgressMonitor implements SftpProgressMonitor{
    private long transfered;

    public boolean count(long count) {
        transfered = transfered + count;
        System.out.println("Currently transferred total size: " + transfered + " bytes");
        return true;
    }

    public void end() {
        System.out.println("Transferring done.");
    }

    public void init(int op, String src, String dest, long max) {
        System.out.println("Transferring begin.");
    }
}
