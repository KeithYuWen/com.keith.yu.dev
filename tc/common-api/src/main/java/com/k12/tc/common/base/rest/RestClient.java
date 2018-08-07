package com.k12.tc.common.base.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import com.k12.common.exception.BusinessException;
import com.k12.tc.common.aes.AesException;
import com.k12.tc.common.init.SystemParamInit;
import com.k12.tc.common.utils.JsonUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.k12.tc.common.aes.MsgCrypt;

public class RestClient {
	private final static Logger logger = LoggerFactory.getLogger(RestClient.class);
    private String serverUrl;
    private String httpMethod;
    private String clientData;
    private int connectTimeout;
    private int socketTimeout;

    public RestClient(String url, String method, String data) {
        this.serverUrl = url;
        this.httpMethod = method;
        this.clientData = data;
        this.connectTimeout = -1;
        this.socketTimeout = -1;
    }

    public RestClient(String url) {
        this.serverUrl = url;
        this.httpMethod = "GET";
        this.clientData = null;
        this.connectTimeout = -1;
        this.socketTimeout = -1;
    }

    public RestClient(String url, String data) {
        this.serverUrl = url;
        this.httpMethod = "GET";
        this.clientData = data;
        this.connectTimeout = -1;
        this.socketTimeout = -1;
    }

    public void setURL(String url) {
        this.serverUrl = url;
    }

    public void setMethod(String method) {
        this.httpMethod = method;
    }

    public void setData(String data) {
        this.clientData = data;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String execute() throws RestException {
        if (null == this.serverUrl) {
            return null;
        }

//        this.clientData = RestCodec.encodeData(this.clientData);
        String ret = httpExecute();
        /*if (null != ret) {
            logger.info(ret);
            ret = RestCodec.decodeData(ret);
        }*/

        return ret;
    }
    
    public String executeNotDecode() throws RestException {
        if (null == this.serverUrl) {
            return null;
        }

        this.clientData = RestCodec.encodeData(this.clientData);
        String ret = httpExecute();
        return ret;
    }
    
    //先加密后base64转码后发送
    public String encryptExecute() throws RestException, AesException {
        if (null == this.serverUrl) {
            return null;
        }
        MsgCrypt pc = new MsgCrypt(SystemParamInit.getToken(), SystemParamInit.getEncodingAesKey(),
                SystemParamInit.getAppId());
        String timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String nonce = String.valueOf(GetRandom.randomNumSix());
        
        logger.debug("restclient: origin data: " + this.clientData);
        
        this.clientData = pc.encryptMsg(this.clientData, timeStamp, nonce);
        logger.debug("restclient: weixin data: " + this.clientData);
        
        this.clientData = RestCodec.encodeData(this.clientData);
        logger.debug("restclient: base64 data: " + this.clientData);
        
        String ret = httpExecute();
        if (null != ret) {
            ret = RestCodec.decodeData(ret);
        }

        return ret;
    }

    /**
     * executeTwo(不加密)
     * 
     * @return
     * @throws RestException
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public String executeNonEncode() throws RestException {
        if (null == this.serverUrl) {
            return null;
        }

        //this.clientData = RestCodec.encodeData(this.clientData);
        String ret = httpExecute();
        return ret;
    }

    private DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        httpClient.getParams().setParameter("http.protocol.content-charset", "utf-8");
        if (0 < this.connectTimeout) {
            httpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(this.connectTimeout * 1000));
        }

        if (0 < this.socketTimeout) {
            httpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(this.socketTimeout * 1000));
        }

        return httpClient;
    }

    private String getURLWithData() {
        if (this.clientData != null) {
            if (this.serverUrl.endsWith("?")) {
                return this.serverUrl + this.clientData;
            }
            return this.serverUrl + "?" + this.clientData;
        }

        return this.serverUrl;
    }

    public String httpExecute() throws RestException {
        String ret = null;
        DefaultHttpClient httpclient = getHttpClient();
        try {
            ResponseHandler responseHandler = new BasicResponseHandler();
            if (this.httpMethod.equalsIgnoreCase("GET")) {
                HttpGet req = new HttpGet(getURLWithData());
                ret = (String) httpclient.execute(req, responseHandler);
            } else if (this.httpMethod.equalsIgnoreCase("POST")) {
                ret = doPost(this.serverUrl, this.clientData);
            } else if (this.httpMethod.equalsIgnoreCase("PUT")) {
                HttpPut req = new HttpPut(this.serverUrl);
                if (null != this.clientData) {
                    req.setEntity(new StringEntity(this.clientData));
                }
                ret = (String) httpclient.execute(req, responseHandler);
            } else if (this.httpMethod.equalsIgnoreCase("DELETE")) {
                HttpDelete req = new HttpDelete(getURLWithData());
                ret = (String) httpclient.execute(req, responseHandler);
            }
            logger.info("ResponseVo" + ret);
        } catch (ClientProtocolException e) {
            throw new RestException(e.getMessage(), e.getCause());
        } catch (IOException e) {
            throw new RestException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new RestException(e.getMessage(), e.getCause());
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return ret;
    }

    
    /**
     * encryptPost(先加密后base64转码后发送) 
     * @return
     * @throws RestException
     * @throws AesException 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public String encryptPost() throws RestException, AesException {
    	return encryptPost(true);
    }
    
    /**
     * encryptExecute(先加密后base64转码后发送) 
     * @return
     * @throws RestException
     * @throws AesException 
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    public String encryptPost(boolean base64) throws RestException, AesException {
        if (null == this.serverUrl) {
            return null;
        }
        MsgCrypt pc = new MsgCrypt(SystemParamInit.getToken(), SystemParamInit.getEncodingAesKey(),SystemParamInit.getAppId());
        String timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String nonce = String.valueOf(GetRandom.randomNumSix());
        System.out.println("加密前数据："+this.clientData);
        String afterEncrpt = pc.encryptMsg(JsonUtil.toString(this.clientData), timeStamp, nonce);
        System.out.println("加密后数据："+afterEncrpt);
        this.clientData = RestCodec.encodeData(afterEncrpt);
        System.out.println("加密后base64："+this.clientData);
        String ret = doPost(this.serverUrl, this.clientData);
        if (null != ret && base64) {
            ret = RestCodec.decodeData(ret);
        }
        return ret;
    }
    
    /**
     * doPost(post请求) 
     *  
     * @since CodingExample　Ver(编码范例查看) 1.1
     */
    private String doPost(String path,String param){
    	try {
            //创建连接
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
            connection.connect();

            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());

            out.writeBytes(param);
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
            reader.close();
            // 断开连接
            connection.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
    	    throw new BusinessException(e.getMessage(), e.getCause());
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e.getCause());
        }
    }
    

}
