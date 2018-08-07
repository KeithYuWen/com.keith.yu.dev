/** 
 * 文件名：IOpenOfficeConverter.java <br>
 * 版本信息： V1.0<br>
 * 日期：2015年10月27日 <br>
 * Copyright: Corporation 2015 <br>
 * 版权所有 江苏宏坤供应链有限公司<br>
 */
package com.k12.common.util.oppenoffice;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/** 
 * 项目名称：greenpass <br>
 * 类名称：IOpenOfficeConverter <br>
 * 类描述： <br>
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链有限公司<br> 
 * 创建人：arlen<br>
 * 创建时间：2015年10月27日 下午5:40:54 <br>
 * 修改人：arlen<br>
 * 修改时间：2015年10月27日 下午5:40:54 <br>
 * 修改备注：<br>
 * @version 1.0<br>
 * @author arlen<br>
 */
public interface IOpenOfficeConverter {

	void convertFile(String inputFileName, String outputFileName);
	void convertFile(InputStream inputStream,  OutputStream outputStream);
}
