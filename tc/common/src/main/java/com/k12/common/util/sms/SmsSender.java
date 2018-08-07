package com.k12.common.util.sms;

import java.util.List;

import com.k12.common.base.rest.RestClient;
import com.k12.common.base.rest.RestException;
import com.k12.common.util.common.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 项目名称：greenpass-webapp 
 * 类名称：SmsSender 
 * 类描述：Jsk12，短信发送。
 * Copyright: Copyright (c) 2015 by 江苏宏坤供应链管理有限公司 
 * Company: k12 Supply Chain MGT
 * 创建人：徐洪杰 
 * 创建时间：2016年3月15日 下午4:07:32 
 * 修改人：徐洪杰 
 * 修改时间：2016年3月15日 下午4:07:32 
 * 修改备注：
 * @version 1.0*
 */
public class SmsSender {
    
    private static final Logger LOG = LoggerFactory.getLogger(SmsSender.class);
    /**
     * authCodeSmsSend(注册发送短信验证码) 
     * @param authCode
     * @param phoneNum
     * @return 
     * @since xuhongjie
     */
    @SuppressWarnings("unchecked")
    public static boolean authCodeSmsSend(String requestUrl){
        boolean sendResult = true;
        RestClient restClient = new RestClient(requestUrl);
        String resultXmlText;
        try {
            resultXmlText = restClient.httpExecute();
            LOG.info("短信发送校验码返回结果" + resultXmlText);
            Document doc = DocumentHelper.parseText(resultXmlText);
            Element root = doc.getRootElement();
            System.out.println(root.getName());
            List<Element> sms = root.elements();
            for(Element mt : sms){
                List<Element> attrs = mt.elements();
                for(Element attre : attrs){
                    if(attre.getName().equals("status")){
                        String status = attre.getData().toString();
                        if(!status.equals("0")){
                            sendResult = false;
                            LOG.error("ERROR IN SmsSender.authCodeSmsSend:resultCode:{}", getSendResultMsg(status));
                        }
                    }else{
                        //msgid
                    }
                }
            }
        } catch (RestException e) {
            LOG.error("ERROR IN SmsSender.authCodeSmsSend:{} ", e);
        } catch (DocumentException e) {
            LOG.error("ERROR IN SmsSender.authCodeSmsSend:{} ", e);
        }
        return sendResult;
    }
    /**
     * getSendResultMsg(获取发送状态码说明) 
     * @param status 
     * @since xuhongjie
     */
    private static String getSendResultMsg(String status){
        String sendResultMsg = "";
        if(!StringUtils.isEmpty(status)){
            sendResultMsg = SmsStatusEnum.getNameByCode(status);
        }
        return sendResultMsg;
    }
}
