package com.crm.wechat.pay.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crm.wechat.aes.AesException;
import com.crm.wechat.aes.WXBizMsgCrypt;
import com.crm.wechat.pay.domain.WxConfig;

/** 
 * @ClassName	TokenAction.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月30日 下午6:35:24
 * @Version 	V1.0    
 */
public class TokenAction extends HttpServlet {

	private static final long serialVersionUID = 8923677244036434508L;
	
	String token = WxConfig.TOKEN; // 这个Token必须跟企业号上的相同
	String mchId = WxConfig.MCH_ID;  // 商户号
	String encodingAESKey = WxConfig.KEY;  
	
	/**
	 * 确认请求来自微信服务器
	 * <p>Title: doGet</p>
	 * <p>Description: </p>
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名 
        String sVerifyMsgSig = request.getParameter("msg_signature");
        // 时间戳
        String sVerifyTimeStamp = request.getParameter("timestamp");
        // 随机数
        String sVerifyNonce = request.getParameter("nonce");
        // 随机字符串
        String sVerifyEchoStr = request.getParameter("echostr");
        String sEchoStr; //需要返回的明文
        PrintWriter out = response.getWriter();  
        WXBizMsgCrypt wxcpt;
        try {
            wxcpt = new WXBizMsgCrypt(token, encodingAESKey, mchId);
            sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp,sVerifyNonce, sVerifyEchoStr);
            // 验证URL成功，将sEchoStr返回
            out.print(sEchoStr);  
            out.flush();
            out.close();
        } catch (AesException e1) {
            e1.printStackTrace();
        }
	}
	

    /**
     * 处理微信服务器发来的消息
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO 消息的接收、处理、响应
    }

}
 