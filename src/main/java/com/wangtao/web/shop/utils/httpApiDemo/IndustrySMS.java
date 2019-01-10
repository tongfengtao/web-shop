package com.wangtao.web.shop.utils.httpApiDemo;

import com.wangtao.web.shop.utils.httpApiDemo.common.Config;
import com.wangtao.web.shop.utils.httpApiDemo.common.HttpUtil;

import java.net.URLEncoder;

/**
 * 验证码通知短信接口
 * 
 * @ClassName: IndustrySMS
 * @Description: 验证码通知短信接口
 *
 */
public class IndustrySMS
{
	private static String operation = "/industrySMS/sendSMS";

	private static String accountSid = Config.ACCOUNT_SID;
	//private static String to = "1361305****";
	//private static String smsContent = "【秒嘀科技】您的验证码是345678，30分钟输入有效。";

	/**
	 * 验证码通知短信
	 */
	public static String execute(String to,String smsContent)
	{
		String tmpSmsContent = null;
	    try{
	      tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
	    }catch(Exception e){
	      
	    }
	    String url = Config.BASE_URL + operation;
	    String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + tmpSmsContent
	        + HttpUtil.createCommonParam();

	    // 提交请求
	    String result = HttpUtil.post(url, body);
	    return result;
	}

	public static void main(String[] args) {
		execute("15223383017",
				"【网淘】尊敬的用户，您的验证码为1234，请于2分钟内正确输入，如非本人操作，请忽略此短信。");
	}
}
