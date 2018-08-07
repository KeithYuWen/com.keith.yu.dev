package com.k12.tc.common.base.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.k12.tc.common.aes.AesException;
import com.k12.tc.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k12.tc.common.aes.MsgCrypt;
import com.k12.tc.common.init.SystemParamInit;


public class RestServer {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private static final Logger LOG = LoggerFactory.getLogger(RestServer.class);

	public RestServer(HttpServletRequest req, HttpServletResponse resp) {
		this.request = req;
		this.response = resp;
	}
	/**
	 * getRestData(获取请求base64加密的body数据) 
	 * @return
	 * @throws RestException 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getRestData() throws RestException {
		if (null == this.request) {
			return null;
		}

		String method = this.request.getMethod();
		String ret = null;
		if ((method.equalsIgnoreCase("GET"))
				|| (method.equalsIgnoreCase("DELETE"))) {
			ret = this.request.getQueryString();
		} else
			ret = getBodyData();

		if (null == ret) {
			return null;
		}
		LOG.info("接收到的信息为{}",ret);
		return RestCodec.decodeData(ret);
	}
	/**
	 * getDecryptRestData(base64转码后解密) 
	 * @return
	 * @throws RestException
	 * @throws AesException
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings("unchecked")
    public String getDecryptRestData() throws RestException, AesException {
        if (null == this.request) {
            return null;
        }

        String method = this.request.getMethod();
        String ret = null;
        if ((method.equalsIgnoreCase("GET"))
                || (method.equalsIgnoreCase("DELETE"))) {
            ret = this.request.getQueryString();
        } else
            ret = getBodyData();

        if (null == ret) {
            return null;
        }
        System.out.println("解密前数据："+ret);
        String decodeData = RestCodec.decodeData(ret);
        System.out.println("解密前base64解码数据："+decodeData);
        Map<String, String> data = new HashMap<String, String>();
        data = JsonUtil.toBean(decodeData, Map.class);
        System.out.println(SystemParamInit.getToken()+"\n"+SystemParamInit.getEncodingAesKey()+"\n"+ SystemParamInit.getAppId());
        MsgCrypt pc = new MsgCrypt(SystemParamInit.getToken(), SystemParamInit.getEncodingAesKey(), SystemParamInit.getAppId());
        String retS=pc.decryptMsg(data.get("signature"), data.get("timeStamp"), data.get("nonce"), decodeData);
        System.out.println("解密后数据："+retS);
        return retS.replaceAll("\n","");
    }
	/**
	 * getRestData(获取请求的body数据) 
	 * @return
	 * @throws RestException 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String getRestDataTwo() throws RestException {
		if (null == this.request) {
			return null;
		}
		String method = this.request.getMethod();
		String ret = null;
		if ((method.equalsIgnoreCase("GET"))
				|| (method.equalsIgnoreCase("DELETE"))) {
			ret = this.request.getQueryString();
		} else
			ret = getBodyData();
		if (null == ret) {
			return null;
		}
		return ret;
	}
	
	public boolean sendRestData(String data) throws RestException {
		if (null == this.response) {
			return false;
		}

		this.response.setContentType("application/json");
//		this.response.setCharacterEncoding("utf-8");
		PrintWriter writer = null;
		try {
			writer = this.response.getWriter();
			writer.print(RestCodec.encodeData(data));
		} catch (IOException e) {
			throw new RestException(e.getMessage(), e.getCause());
		} finally {
		}
		return true;
	}

	private String getBodyData() throws RestException {
		StringBuffer data = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			reader = this.request.getReader();
			while (null != (line = reader.readLine()))
				data.append(line);
		} catch (IOException e) {
			throw new RestException(e.getMessage(), e.getCause());
		} finally {
		}
		return data.toString();
	}
}
