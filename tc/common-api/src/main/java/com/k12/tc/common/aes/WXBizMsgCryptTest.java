package com.k12.tc.common.aes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.k12.tc.common.base.rest.RestException;
import com.k12.tc.common.utils.JsonUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.k12.tc.common.base.rest.RestCodec;

public class WXBizMsgCryptTest {
	String encodingAesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
	String token = "pamtest";
	String appId = "wxb11529c136998cb6";
	
	String timestamp = "1409304348";
	String nonce = "xxxxxx";
	String replyMsg ="{\"phoneNumber\":\"15261118316\",\"email\":\"1003884008@qq.com\",\"toUrl\":\"http://www.baidu.com\",\"website\":\"www.baidu.com\"}";

	
	String xmlFormat = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
	String afterAesEncrypt = "jn1L23DB+6ELqJ+6bruv21Y6MD7KeIfP82D6gU39rmkgczbWwt5+3bnyg5K55bgVtVzd832WzZGMhkP72vVOfg==";
	String randomStr = "aaaabbbbccccdddd";

	String replyMsg2 = "<xml><ToUserName><![CDATA[oia2Tj我是中文jewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
	String afterAesEncrypt2 = "jn1L23DB+6ELqJ+6bruv23M2GmYfkv0xBh2h+XTBOKVKcgDFHle6gqcZ1cZrk3e1qjPQ1F4RsLWzQRG9udbKWesxlkupqcEcW7ZQweImX9+wLMa0GaUzpkycA8+IamDBxn5loLgZpnS7fVAbExOkK5DYHBmv5tptA9tklE/fTIILHR8HLXa5nQvFb3tYPKAlHF3rtTeayNf0QuM+UW/wM9enGIDIJHF7CLHiDNAYxr+r+OrJCmPQyTy8cVWlu9iSvOHPT/77bZqJucQHQ04sq7KZI27OcqpQNSto2OdHCoTccjggX5Z9Mma0nMJBU+jLKJ38YB1fBIz+vBzsYjrTmFQ44YfeEuZ+xRTQwr92vhA9OxchWVINGC50qE/6lmkwWTwGX9wtQpsJKhP+oS7rvTY8+VdzETdfakjkwQ5/Xka042OlUb1/slTwo4RscuQ+RdxSGvDahxAJ6+EAjLt9d8igHngxIbf6YyqqROxuxqIeIch3CssH/LqRs+iAcILvApYZckqmA7FNERspKA5f8GoJ9sv8xmGvZ9Yrf57cExWtnX8aCMMaBropU/1k+hKP5LVdzbWCG0hGwx/dQudYR/eXp3P0XxjlFiy+9DMlaFExWUZQDajPkdPrEeOwofJb";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testNormal() throws ParserConfigurationException, SAXException, IOException {
		try {
			MsgCrypt pc = new MsgCrypt(token, encodingAesKey, appId);
			String afterEncrpt = pc.encryptMsg(replyMsg, timestamp, nonce);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(afterEncrpt);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
			NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

			String encrypt = nodelist1.item(0).getTextContent();
			String msgSignature = nodelist2.item(0).getTextContent();
			String fromXML = String.format(xmlFormat, encrypt);

			// 第三方收到公众号平台发送的消息
			String afterDecrpt = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
			assertEquals(replyMsg, afterDecrpt);
		} catch (AesException e) {
			fail("正常流程，怎么就抛出异常了？？？？？？");
		}
	}
	@Test
	public void test() throws AesException, RestException {
		MsgCrypt pc = new MsgCrypt(token, encodingAesKey, appId);
		String afterEncrpt = pc.encryptMsg("", timestamp, nonce);
		System.out.println(afterEncrpt);
		System.out.println(RestCodec.encodeData(afterEncrpt));
	}
	@Test
	public void decryptTest() throws RestException, AesException{
//	    String ret ="eyJlbmNyeXB0IjoiNEZTZmNBdXpTcXVDQ3BJZE5PRkUzbXhwSncrV21kdlpmWGlqZEhtSDlTaW16WWw2bmIrUkF0UUNnazZMbDAxeEpwK0VpV0xhd1ptRXY5MFVJMmhFVWc9PSIsInNpZ25hdHVyZSI6IjMxOTU5MDFlZjZlZjVmYzJhYzJkNzMzNDRhZTA3NDNkYTUxMzRjNzEiLCJ0aW1lU3RhbXAiOiIxNDU2NTY1MjI1Iiwibm9uY2UiOiJ4eHh4eHgifQ==";
	    String ret ="eyJlbmNyeXB0IjoiV2JEdHB5WjhObnVPbFVNSzlZWS9QVFdYMDVFdXBiNW9meWR3TjdrSzVQYWJ0U2dwRUV6WFNWRHdGM3ArU0xJaEE3NFI2c2l2N1JxcS9iVS82MTE0NE13VWlRS0sxV0w3cktRNzFmNFVCTmNmaFV2dUNZVko5Z1lBMmpPaGxRd2tLV0NhaUpaWWRmQUx6SnVhbXpTVG0zVG1YckgzN2VKRytNQ2pWSkdQVDZHYVJmY1B4WUxZYXV2V2hISmNWNHRVczRsZG1VYWY5RFZwdnM1T3FVczhuWFR0dVdoa1JwYUd1RmFNb1NaWlk0SGpoaEZBLzBLeXRqUWYvZFRLQ3h1QUw1ZnRTaTRsRXZxRTFnR2dVMW5jOFhId2YvaUZXeGhCQ1ZVZnVMbTV2UGxlL0JIRUpCVjlGMkV4VHN0dnFmdUppZ3hMTWlYMDJIOU8zM1kxR0Z2WmYvRHBOSjhPOTU1RWtHV09TdjVsUHNHeUZwMHl1KzV4bUFLT1BCTFBUYUt4VG9sZXhnVmJkUlhtTUdUakdGb1J2cTNEN0NLb1NWNDFrS3l0K252Y0tFczNUanhnUUdZZVRLYzl0UkRBSjNPSzdZNTl2c3NncHJqcUdaM3M0Q2JFRlR4MUE0YzNVbFoxVGpDd0NlQXhqdVFqL0xXV2xXTWtRcWNOcklnRG5iaTR3c05TVmJWZkI3eUZQQTNYaGwvQ2NWUU5QS2NaR1crSFJ0NUdLRGxCKzFZRmplU2xOMHpUYTJqUDNaNm1uSTA1cm9DdkovNWtGbVhsRUxVS093aDF4Rm9KZHQwbi9ZQXpCWUdXeDZVUkZkWHVVWmtrMkdtVGFqUjR5dkpQL1lOeSIsInNpZ25hdHVyZSI6IjA1ZmIyNTJlOGI2ODNjMGQ5YTRkZjQzZmJiMTRlNTc2Mzg1MjA4YzEiLCJ0aW1lU3RhbXAiOiIxNDU2NTY1MjYxIiwibm9uY2UiOiJ4eHh4eHgifQ==";
	    String decodeData = RestCodec.decodeData(ret);
	    System.out.println("解密前base64解码数据："+decodeData);
	    Map<String, String> data = new HashMap<String, String>();
        data = JsonUtil.toBean(decodeData, Map.class);
        System.out.println(token+"\n"+encodingAesKey+"\n"+ appId);
        MsgCrypt pc = new MsgCrypt(token, encodingAesKey, appId);
        String retS=pc.decryptMsg(data.get("signature"), data.get("timeStamp"), data.get("nonce"), decodeData);
        System.out.println("解密后数据："+retS);
	}
	@Test
	public void testIllegalAesKey() {
		try {
			new MsgCrypt(token, "abcde", appId);
		} catch (AesException e) {
			assertEquals(AesException.IllegalAesKey, e.getCode());
			return;
		}
		fail("错误流程不抛出异常？？？");
	}

	@Test
	public void testValidateSignatureError() throws ParserConfigurationException, SAXException,
			IOException {
		MsgCrypt pc;
		try {
			pc = new MsgCrypt(token, encodingAesKey, appId);
			String afterEncrpt = pc.encryptMsg(replyMsg, timestamp, nonce);
			System.out.println(afterEncrpt);
			Map<String, String> localResBaseInfo = JsonUtil.toBean(afterEncrpt, Map.class);
			String string = pc.decryptMsg(localResBaseInfo.get("signature"), timestamp, nonce, afterEncrpt); // 这里签名错误
			System.out.println(string);
			System.out.println(RestCodec.encodeData(string));
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void testVerifyUrl() throws AesException {
		MsgCrypt wxcpt = new MsgCrypt("QDG6eK",
				"jWmYm7qr5nMoAUwZRjGtBxmz3KA1tkAj3ykkR6q2B2C", "wx5823bf96d3bd56c7");
		String verifyMsgSig = "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3";
		String timeStamp = "1409659589";
		String nonce = "263014780";
		String echoStr = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ==";
		wxcpt.verifyUrl(verifyMsgSig, timeStamp, nonce, echoStr);
		// 只要不抛出异常就好
	}
	@Test
    public void getSHA1() throws NoSuchAlgorithmException{
            String str = "123456";
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            System.out.println(hexstr.toString().toUpperCase());
        
    }
	
}
