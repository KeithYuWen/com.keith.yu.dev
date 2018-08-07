package com.k12.common.util.rtx;


import com.k12.common.vo.ResponseVo;

/**
 * 项目名称：oss-common 
 * 类名称：RtxUtil 
 * 类描述：处理页面提交的请求。 
 * 类描述：Bean,响应Action请求，对数据库进行操作，并把结果返回到Action。 
 * Copyright: Copyright (c) 2016 by  k12
 * Company: k12
 * 创建人：sunhaibin
 * 创建时间：2016年11月23日 上午10:20:47 
 * 修改人：sunhaibin 
 * 修改时间：2016年11月23日 上午10:20:47 
 * 修改备注：
 * @version 1.0*
 */
public class RtxUtil {
	/**
	 * sendNotify(发送rtx通知) 
	 * @return 
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public static ResponseVo sendNotify(RtxSenderInfoVo info){
		ResponseVo responseVo = new ResponseVo();
		/*try{
			RestClient restClient = new RestClient(SystemParamInit.getRtxSendNotifyUrl());
			restClient.setMethod("POST");
			restClient.setData(JSON.toJSONString(info));
			;
			String result="";
			result = restClient.encryptExecute();
			if("".equals(result)){
				responseVo.setSuccess(false);
				responseVo.setMessage("调用发送接口失败");
				responseVo.setErrorCode(ErrorCode.API_ERROR);
			}else{
				responseVo = JSONObject.parseObject(result, ResponseVo.class);
			}
		}catch(Exception e){
			e.printStackTrace();
			responseVo.setSuccess(false);
			responseVo.setMessage("调用发送接口失败");
			responseVo.setErrorCode(ErrorCode.API_ERROR);
		}*/
		return responseVo;
	}
	
}
