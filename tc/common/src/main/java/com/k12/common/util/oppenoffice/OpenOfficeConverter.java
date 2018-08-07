/** 
 * 文件名：OpenOfficeConverter.java <br>
 * 版本信息： V1.0<br>
 * 日期：2015年10月27日 <br>
 * Copyright: Corporation 2015 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.util.oppenoffice;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.spi.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.k12.common.exception.SystemException;

/** 
 * 项目名称：greenpass <br>
 * 类名称：OpenOfficeConverter <br>
 * 类描述： <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：arlen<br>
 * 创建时间：2015年10月27日 下午5:41:27 <br>
 * 修改人：arlen<br>
 * 修改时间：2015年10月27日 下午5:41:27 <br>
 * 修改备注：<br>
 * @version 1.0<br>
 * @author arlen<br>
 */
@Service("openOfficeConverter")
public class OpenOfficeConverter implements IOpenOfficeConverter {

	private static final Logger logger = LoggerFactory.getLogger(OpenOfficeConverter.class);
	
	@Value("${openoffice.ip}")
	private String openOfficeIp;
	
	@Value("${openoffice.port}")
	private Integer openOfficePort;
	
	private OpenOfficeConnection connection; 
	 

    @PostConstruct
	private void init() {
		try {
			logger.info("init openoffice start...");
			if (connection == null || !connection.isConnected()) { 
				connection = new SocketOpenOfficeConnection(openOfficeIp, openOfficePort);
				connection.connect();
			}
			logger.info("init openoffice end...");
		} catch (Exception e) {
			logger.error("openoffice init failed", e);
		} 
	}
	
	@PreDestroy
	private void destroy() {
		try {
			if (connection != null && connection.isConnected()) {
				connection.disconnect();
				connection = null;
			}
		} catch (Exception e) {
			logger.error("close openoffice failed", e);
		} finally {
			connection = null;
		}
	}
	
	@Override
	public synchronized void convertFile(String inputFileName, String outputFileName) {
		File inputFile = new File(inputFileName);
		if (!inputFile.exists()) {
			logger.error("转换excel到pdf失败，原始excel不存在，file：" + inputFileName);
			throw new SystemException(ErrorCode.FILE_OPEN_FAILURE, "转换excel到pdf失败，原始excel不存在");
		}
		File outputFile = new File(outputFileName);
		
		if (null == connection || !connection.isConnected()) {
			logger.info("转换excel到pdf，officeManager 未建立连接，重新建立");
			init();
		}
		
		long start = System.currentTimeMillis();
        try {
        	DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        	converter.convert(inputFile, outputFile);
		} catch (Exception e) {
			logger.error("转换excel到pdf失败，未知异常", e);
			throw new SystemException(ErrorCode.FILE_OPEN_FAILURE, "转换excel到pdf失败", e);
		}
		
        logger.info("文件转换耗时：[" + (System.currentTimeMillis() - start) + "]ms");
	}

	public synchronized void convertFile(InputStream inputStream,  OutputStream outputStream) {
	    DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);

	    final DocumentFormat pdf = new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
        pdf.setExportFilter(DocumentFamily.DRAWING, "draw_pdf_Export");
        pdf.setExportFilter(DocumentFamily.PRESENTATION, "impress_pdf_Export");
        pdf.setExportFilter(DocumentFamily.SPREADSHEET, "calc_pdf_Export");
        pdf.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");
        
        final DocumentFormat xls = new DocumentFormat("Microsoft Excel", DocumentFamily.SPREADSHEET, "application/vnd.ms-excel", "xls");
        xls.setExportFilter(DocumentFamily.SPREADSHEET, "MS Excel 97");
        
	    converter.convert(inputStream, xls, outputStream, pdf);
	}
}
