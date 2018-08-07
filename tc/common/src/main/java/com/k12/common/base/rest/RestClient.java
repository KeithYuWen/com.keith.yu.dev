package com.k12.common.base.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;

import com.k12.common.aes.AesException;
import com.k12.common.aes.MsgCrypt;
import com.k12.common.init.SystemParamInit;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

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

        this.clientData = RestCodec.encodeData(this.clientData);
        String ret = httpExecute();
        if (null != ret) {
            System.out.println(ret);
            ret = RestCodec.decodeData(ret);
        }

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
    
    //先加密后base64转码后发送,返回明文
    public String encryptExecuteReturnNoBase() throws RestException, AesException {
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

        return ret;
    }

    //先加密后base64转码后发送
      public String encryptExecute(Map<String, String> headMap) throws RestException, AesException {
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
          
          String ret = httpExecute(headMap);
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
    public String executeTwo() throws RestException {
        if (null == this.serverUrl) {
            return null;
        }

        this.clientData = RestCodec.encodeData(this.clientData);
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
                System.out.println("------" + ret);
            } else if (this.httpMethod.equalsIgnoreCase("POST")) {
                HttpPost req = new HttpPost(this.serverUrl);
                if (null != this.clientData) {
                    req.setEntity(new StringEntity(this.clientData));
                }

                ret = (String) httpclient.execute(req, responseHandler);
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
     * basic 认证请求，如果是https的，请自定义自己的SSLSocketFactory
     * @param domain
     * @param userName
     * @param password
     * @return 返回字符串
     * @throws RestException 
     * @since CodingExample　Ver(编码范例查看) 1.1
     * @author arlen
     */
	public String httpExecute(String domain,String userName,String password) throws RestException {
		String ret = null;
		DefaultHttpClient httpclient = getHttpClient();
		try {
			ResponseHandler<?> responseHandler = new BasicResponseHandler();
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
		    credsProvider.setCredentials(
		                new AuthScope(domain, 80),
		                new UsernamePasswordCredentials(userName,password));
			httpclient.setCredentialsProvider(credsProvider);
			if (this.httpMethod.equalsIgnoreCase("GET")) {
				HttpGet req = new HttpGet(getURLWithData());
				ret = (String) httpclient.execute(req, responseHandler);
			} else if (this.httpMethod.equalsIgnoreCase("POST")) {
				HttpPost req = new HttpPost(this.serverUrl);
				if (null != this.clientData) {
					req.setEntity(new StringEntity(this.clientData));
				}
				ret = (String) httpclient.execute(req, responseHandler);
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
		} catch (Exception e) {
			throw new RestException(e.getMessage(), e.getCause());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return ret;
	}
	
	/**
	 * 带指定http头的请求 
	 * @param headerMap
	 * @return result
	 * @throws RestException 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 * @author arlen
	 */
	public String httpExecute(Map<String, String> headerMap) throws RestException {
		String ret = null;
		DefaultHttpClient httpclient = getHttpClient();
		try {
			ResponseHandler<?> responseHandler = new BasicResponseHandler();
			if (this.httpMethod.equalsIgnoreCase("GET")) {
				HttpGet req = new HttpGet(getURLWithData());
				if(null != headerMap && !headerMap.isEmpty()) {
					for (String key : headerMap.keySet()) {
						req.addHeader(new BasicHeader(key, headerMap.get(key)));
					}
				}
				ret = (String) httpclient.execute(req, responseHandler);
			} else if (this.httpMethod.equalsIgnoreCase("POST")) {
				HttpPost req = new HttpPost(this.serverUrl);
				if(null != headerMap && !headerMap.isEmpty()) {
					for (String key : headerMap.keySet()) {
						req.addHeader(new BasicHeader(key, headerMap.get(key)));
					}
				}
				if (null != this.clientData) {
					req.setEntity(new StringEntity(this.clientData,"utf-8"));
				}
				ret = (String) httpclient.execute(req, responseHandler);
			} else if (this.httpMethod.equalsIgnoreCase("PUT")) {
				HttpPut req = new HttpPut(this.serverUrl);
				if(null != headerMap && !headerMap.isEmpty()) {
					for (String key : headerMap.keySet()) {
						req.addHeader(new BasicHeader(key, headerMap.get(key)));
					}
				}
				if (null != this.clientData) {
					req.setEntity(new StringEntity(this.clientData));
				}
				ret = (String) httpclient.execute(req, responseHandler);
			} else if (this.httpMethod.equalsIgnoreCase("DELETE")) {
				HttpDelete req = new HttpDelete(getURLWithData());
				if(null != headerMap && !headerMap.isEmpty()) {
					for (String key : headerMap.keySet()) {
						req.addHeader(new BasicHeader(key, headerMap.get(key)));
					}
				}
				ret = (String) httpclient.execute(req, responseHandler);
			}
		} catch (Exception e) {
			throw new RestException(e.getMessage(), e.getCause());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return ret;
	}
    
    /**
     * encryptPost(先加密后base64转码后发送) 
     * @param base64
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
        String afterEncrpt = pc.encryptMsg(JSON.toJSONString(this.clientData), timeStamp, nonce);
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
    public String doPost(String path,String param){
    	try {
            //创建连接
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type","application/json");
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
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    	return null;
    }
    
    public static void main(String[] args) {
        //String str = "eyJlbmNyeXB0IjoiVHZwVWppdEpteDU1VnNGempIVXhuUnM4alZwZkF3ZXhKRnVaUGNiTk9wRkQ5QUJ3ckllbmY0a0cyVzEwb2k4V0pLUkFHUklMamNBQVArbUVGeWRVT01pZXhFZUpnS0trQ3FYZjBFanIxVmR6TGpTUlZTa3NOK0VsamQ3MzZMZVUvNStwSW1mbUZmbGxSaHRyMEtJM1FUVndEaGlpbk0vTE5zamNjTjNwUzZ4dHN3amRDZTlEY0VpdVFMUERZL0R4N0psSzVEdHJ0a3ViZ3pPbU5BNHZrWWVjUGxKZkZDSmkxM094d0dCdkwzTytkWGdubW1VTXdLNHN5WGZFVDB5K2VFTlAxSW9MajhUSmhUaVZ6dEUyck01SFRzOEZKR3VGdmdTc1FuTXZKN1U9Iiwic2lnbmF0dXJlIjoiZWQ0Zjg3MGRiZjRlZjc3MzJlYWUzOTMzOWM4YzgzZDNiMjU5ZTkxYiIsInRpbWVTdGFtcCI6IjE0NTYzOTA0NDEiLCJub25jZSI6Inh4eHh4eCJ9";
        String str ="eyJlbmNyeXB0IjoiMDVVbUZyYmJRS2Y5dGRZR0RIOW5BY0sxT2JaL1VSb29oR2NKRTVpelNFSUx2NU9aMlBoK2o1bDVPU3FqTTZUakRvZ1M1K0VobVRoY3REUVM3aU1zeXAzSEVGU3J1T29GalNkTVhDS21OaS8wU0t3Q25vK3pWMFU2bER5cHNOSmtXYTE1QW1VRlpvbFRVNitQOTMxN1Bielg1bnBvRXZ4M3kwZHliZXdnZjVBOXpXajVoWTY3NTJpVDhZSElyUnV2ajRRZVM3SHpiTU96Rmp3VHRRRnpNRHNnNU9helFjWGI1ODUyWFNqYWR2YVFRdTAwUjc2M2RjVHhBWFRsa1ovSlVoUlVzVmFDbnZ4UURERXRzczRQcXJXWFFhUXBDc2NaRUNSWGx0Q2M2ZzNIS3NJVHhtZldkdXM5d2Jnc3JqWG5raUJYY29LdzdadzE4VmpVTjNURW9zZFlyWEpVZzZVMEFQLzdlUWplZ1l6VGVxVHd4N2JMT2VnenU1ZmVrZW1NL3hvRStiYzVINi92dW5yeHE2QmdKdEdEZ25jdjFYS2xoNGpaQkN5TXVxdURBUExmQVFJdWl1U2VDeHB0Y3U3eUo1SHlzV0FaVUhPRW53QVlFQnFhZ3c9PSIsInNpZ25hdHVyZSI6IjZjYjg2NmFmNDI0YWRhYzdjM2Y2NjZmMjMwMjYzNDYzOTY3YWQ2ODIiLCJ0aW1lU3RhbXAiOiIxNDU2NTc2ODkxIiwibm9uY2UiOiJ4eHh4eHgifQ==";
        RestClient orderExpenseItemRestClient = new RestClient("");
        orderExpenseItemRestClient.doPost("http://localhost:9080/order-api-sp/mainTransBack.html", str);
        //orderExpenseItemRestClient.doPost("http://localhost:9080/order-api-sp/saveOrderCase.html", str);
    }
    
    public String executeNotDecode() throws RestException {
        if (null == this.serverUrl) {
            return null;
        }

        this.clientData = RestCodec.encodeData(this.clientData);
        String ret = httpExecute();
        return ret;
    }

}
