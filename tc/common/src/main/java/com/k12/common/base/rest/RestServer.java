package com.k12.common.base.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.k12.common.aes.AesException;
import com.k12.common.aes.MsgCrypt;
import com.k12.common.init.SystemParamInit;


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
	 * request接收到的数据，包含加密报文和解密报文
	 * @author arlen
	 */
	public static class RestData {
		private String encodeData;
		private String weixinDecodeData;
		private String base64DecodeData;
		public String getEncodeData() {
			return encodeData;
		}
		public void setEncodeData(String encodeData) {
			this.encodeData = encodeData;
		}
		public String getWeixinDecodeData() {
			return weixinDecodeData;
		}
		public void setWeixinDecodeData(String weixinDecodeData) {
			this.weixinDecodeData = weixinDecodeData;
		}
		public String getBase64DecodeData() {
			return base64DecodeData;
		}
		public void setBase64DecodeData(String base64DecodeData) {
			this.base64DecodeData = base64DecodeData;
		}
	}
	// added by renyarong, 2016.4.8
	public RestData getAllRestData() throws RestException, AesException {
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
        LOG.debug("content-type："+this.request.getContentType());
        LOG.debug("解密前数据："+ret);
        String decodeData = RestCodec.decodeData(ret);
        LOG.debug("解密前base64解码数据："+decodeData);
        Map<String, String> data = new HashMap<String, String>();
        data = JSONObject.parseObject(decodeData, Map.class);
        LOG.debug(SystemParamInit.getToken()+"\n"+SystemParamInit.getEncodingAesKey()+"\n"+ SystemParamInit.getAppId());
        MsgCrypt pc = new MsgCrypt(SystemParamInit.getToken(), SystemParamInit.getEncodingAesKey(), SystemParamInit.getAppId());
        String retS=pc.decryptMsg(data.get("signature"), data.get("timeStamp"), data.get("nonce"), decodeData);
        LOG.info("解密后数据："+retS);
        
        RestData restData = new RestData();
        restData.setEncodeData(ret);
        restData.setBase64DecodeData(decodeData);
        restData.setWeixinDecodeData(retS.replaceAll("\n",""));
        return restData;
    }


	/**
	 * getDecryptRestData(base64转码后解密)
	 * @return
	 * @throws RestException
	 * @throws AesException
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static String decryptData(EncryptDataVo encryptData) throws RestException, AesException {
		if (null == encryptData) {
			return null;
		}
		String encryptString = JSON.toJSONString(encryptData);
		LOG.info("解密前数据："+ encryptString);
		LOG.info(SystemParamInit.getToken()+"\n"+SystemParamInit.getEncodingAesKey()+"\n"+ SystemParamInit.getAppId());
		MsgCrypt pc = new MsgCrypt(SystemParamInit.getToken(), SystemParamInit.getEncodingAesKey(), SystemParamInit.getAppId());
		String retS= pc.decryptMsg(encryptData.getSignature(), encryptData.getTimeStamp(), encryptData.getNonce(), encryptString);
		LOG.info("解密后数据："+retS);
		return retS.replaceAll("\n","");
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
        if (StringUtils.isEmpty(ret)) {
            return null;
        }
        LOG.info("content-type："+this.request.getContentType());
        LOG.info("解密前数据："+ret);
        String decodeData = RestCodec.decodeData(ret);
        LOG.info("解密前base64解码数据："+decodeData);
        Map<String, String> data = new HashMap<String, String>();
        data = JSONObject.parseObject(decodeData, Map.class);
        LOG.info(SystemParamInit.getToken()+"\n"+SystemParamInit.getEncodingAesKey()+"\n"+ SystemParamInit.getAppId());
        MsgCrypt pc = new MsgCrypt(SystemParamInit.getToken(), SystemParamInit.getEncodingAesKey(), SystemParamInit.getAppId());
        String retS=pc.decryptMsg(data.get("signature"), data.get("timeStamp"), data.get("nonce"), decodeData);
        LOG.info("解密后数据："+retS);
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
		this.response.setCharacterEncoding("utf-8");
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
			System.out.println(reader.toString());
			while (null != (line = reader.readLine()))
				data.append(line);
		} catch (IOException e) {
			throw new RestException(e.getMessage(), e.getCause());
		} finally {
		}
		return data.toString();
	}
}
