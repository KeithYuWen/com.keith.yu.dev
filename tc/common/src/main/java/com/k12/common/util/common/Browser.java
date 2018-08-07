package com.k12.common.util.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 类描述： GET 和 POST 请求数据
 * 类名称：Browser 
 * 项目名称：greenpass 
 * Copyright: Copyright (c) 2014 by 江苏宏坤供应链有限公司 
 * Company: k12 Supply Chain MGT.
 * 创建人：刘大周 
 * 创建时间：2015-10-9 下午4:52:07 
 * 修改人：刘大周 
 * 修改时间：2015-10-9 下午4:52:07 
 * 修改备注：
 * @version 1.0*
 */
public class Browser {

	public static final int BUFFER_SIZE = 8192;
	public static final int CONN_TIME_OUT = 20 * 1000;
	public static final int READ_TIME_OUT = 20 * 1000;

	/**
	 * POST提交方式
	 * 
	 * @year 2011-3-22
	 * @author guiwenqing
	 * @param url
	 *            提交地址url
	 * @param param
	 *            提交参数，用Map封装 key 为参数名 value 为参数值
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return String
	 * 
	 */
	public static String doPost(String url, Map<String, String> param)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		// 创建表单参数列表
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// 循环map放入参数
		if (param != null) {
			Set<String> setKey = param.keySet();
			for (String key : setKey) {
				qparams.add(new BasicNameValuePair(key, param.get(key)));
			}
		}
		// 填充表单
		post.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));
		
		HttpResponse response2 = client.execute(post);
		int statusCode = response2.getStatusLine().getStatusCode();
		
		System.out.println("statusCode:" + statusCode);
		StringBuffer buffer = new StringBuffer();
		String temp = null;
		if (statusCode == 200) {
			HttpEntity entity2 = response2.getEntity();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(
					entity2.getContent(),"UTF-8"));
			while ((temp = reader2.readLine()) != null) {
				buffer.append(temp + "\r\n");
			}
			return buffer.toString();
		} else if (statusCode == 302) {// 302表示重定向
			Header[] hs = response2.getHeaders("Location");
			if (hs.length > 0) {
				String url1 = hs[0].getValue();
				post.abort();
				return get(url1);
			}
		}
		return null;

	}

	public static String doPost(String url, Map<String, String> param,String encode) 
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		// 创建表单参数列表
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// 循环map放入参数
		if (param != null) {
			Set<String> setKey = param.keySet();
			for (String key : setKey) {
				qparams.add(new BasicNameValuePair(key, param.get(key)));
			}
		}
		// 填充表单
		post.setEntity(new UrlEncodedFormEntity(qparams, encode));

		HttpResponse response2 = client.execute(post);
		int statusCode = response2.getStatusLine().getStatusCode();
		System.out.println("statusCode:" + statusCode);
		StringBuffer buffer = new StringBuffer();
		String temp = null;
		if (statusCode == 200) {
			HttpEntity entity2 = response2.getEntity();
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(
					entity2.getContent(), encode));
			while ((temp = reader2.readLine()) != null) {
				buffer.append(temp + "\r\n");
			}
			return buffer.toString();
		} else if (statusCode == 302) {// 302表示重定向
			Header[] hs = response2.getHeaders("Location");
			if (hs.length > 0) {
				String url1 = hs[0].getValue();
				post.abort();
				return get(url1);
			}
		}
		return null;
	}

	/**
	 * Get提交方式
	 * 
	 * @year 2011-3-22
	 * @author guiwenqing
	 * @param url
	 *            提交地址，url后面直接带提交参数
	 * @return
	 * @return String
	 * 
	 */
	public static String get(String url) {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
		HttpGet get = new HttpGet(url);
		get.setHeader("Connection", "close");

		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), "UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				buffer.append(temp + "\r\n");
			}
			get.abort();
			return buffer.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			client.getConnectionManager().shutdown();
		}
		return "";

	}
	
	
	/**
	 * Get提交方式
	 * @year 2011-3-22
	 * @author guiwenqing
	 * @param url
	 *            提交地址，url后面直接带提交参数
	 * @return
	 * @return String
	 * 
	 */
	public static String get(String url,Map<String, String> param) {
		if(param!=null){
			url+="?";
			for (String s : param.keySet()) {
				url+=s+"="+param.get(s)+"&";
			}
			url=url.substring(0, url.length()-1);
		}
		return get(url);
	}
	
	public static String getHttpContent(String url) {

		if (url == null) {
			return "";
		}

		String responseBody = "";
		HttpClient httpclient = new DefaultHttpClient();

		// Create a response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			HttpGet httpget = new HttpGet(url);
			System.out.println("executing request " + httpget.getURI());

			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();

		return responseBody;
	}

	
	/**
	 * getJsonObject(绿道 接口调用) 
	 * @param url
	 * @param param
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static JSONObject getJsonObject(String url,Map<String, String> param){
		//调用接口
		JSONObject retObj=null;
		try {
			String ret=Browser.get(url,param);
			ret=ret.replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\"").replace("\\\\\"","\\\"");
			retObj=JSON.parseObject(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retObj;
	}
	
	 public static void appadd(String path,String data){
	        try {
	            //创建连接
	            URL url = new URL(path);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setDoInput(true);
	            connection.setRequestMethod("POST");
	            connection.setUseCaches(false);
	            connection.setInstanceFollowRedirects(true);
	            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	            connection.connect();

	            //POST请求
	            DataOutputStream out = new DataOutputStream(
	                    connection.getOutputStream());

	            out.writeBytes(data);
	            out.flush();
	            out.close();
	            
	            //读取响应
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String lines;
	            StringBuffer sb = new StringBuffer("");
	            while ((lines = reader.readLine()) != null) {
	                lines = new String(lines.getBytes(), "utf-8");
	                sb.append(lines);
	            }
	            System.out.println(sb);
	            reader.close();
	            // 断开连接
	            connection.disconnect();
	        } catch (MalformedURLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	    }
	//获取ip地址
	    public static String getIpAddr(HttpServletRequest request) {
	        String ipAddress = null;
	        // ipAddress = this.getRequest().getRemoteAddr();
	        ipAddress = request.getHeader("x-forwarded-for");
	        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	            ipAddress = request.getHeader("Proxy-Client-IP");
	        }
	        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	            ipAddress = request.getHeader("WL-Proxy-Client-IP");
	        }
	        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	            ipAddress = request.getRemoteAddr();
	            if (ipAddress.equals("127.0.0.1")) {
	                // 根据网卡取本机配置的IP
	                InetAddress inet = null;
	                try {
	                    inet = InetAddress.getLocalHost();
	                } catch (UnknownHostException e) {
	                    e.printStackTrace();
	                }
	                ipAddress = inet.getHostAddress();
	            }
	        }

	        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	        if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
	                                                            // = 15
	            if (ipAddress.indexOf(",") > 0) {
	                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
	            }
	        }
	        return ipAddress;
	    }
}

