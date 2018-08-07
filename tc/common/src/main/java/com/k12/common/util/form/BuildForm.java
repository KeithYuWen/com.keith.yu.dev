package com.k12.common.util.form;

import com.alibaba.fastjson.JSONObject;

public class BuildForm {
	
	 /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(JSONObject sParaTemp,String url, String strMethod) {
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8;\"/></head><body>");
        sbHtml.append("<form id=\"alipaysubmit\" name=\"MySubmit\" action=\"" + url
                      + "\" method=\"" + strMethod
                      + "\">");
        for (String key:sParaTemp.keySet()) {
            String value =sParaTemp.getString(key);
            sbHtml.append(buildInputHiddenFieldInForm(key, value));
        }
        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"提交\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['MySubmit'].submit();</script>");
        sbHtml.append("</body></html>");
        return sbHtml.toString();
    }
    
    /**
	 * 生成 隐藏文本框
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	private static String buildInputHiddenFieldInForm(String paramName,String paramValue) {
		String line = "<input type=\"hidden\" name=\"\" value=\"\">";
		if (paramName == null || paramValue == null) {
			return "";
		} else {
			paramValue = paramValue.replaceAll("\"", "&quot;");
			int nameSize = "name=".length() + 1;
			int valueSize = "value=".length() + 1;
			line = line.substring(0, line.indexOf("name=") + nameSize)
					+ paramName
					+ line.substring(line.indexOf("name") + nameSize);
			line = line.substring(0, line.indexOf("value=") + valueSize)
					+ paramValue
					+ line.substring(line.indexOf("value=") + valueSize);
			return line;
		}
	}
}
