package com.crm.wechat.pay; 

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.wechat.pay.message.resp.TextMessage;
import com.crm.wechat.pay.util.MessageUtil;
import com.crm.wechat.utils.DecriptUtil;

/** 
 * @ClassName	WeixinResource.java
 * @Description REST controller for managing WorkOrder.
 * @Author		kevin 
 * @CreateTime  2016年9月26日 上午11:50:39
 * @Version 	V1.0    
 */
public class WeixinResource {

	private final Logger log = LoggerFactory.getLogger(WeixinResource.class);
    
//  @Resource(name = "memberServiceImpl")
//  public MemberService memberService;

  private static Map<String,String> TIME_OPENID_MAP = new HashMap<String,String>();

  /**
   * POST  /menus -> Create weixin menus.
   */
  
  //http://localhost:8080/api/wechat/gateway?signature=123432&timestamp=12343&nonce=1234&echostr=1223434
  @RequestMapping(value = "/gateway",
          method = RequestMethod.GET,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String weChatValidate(HttpServletRequest request,HttpServletResponse response) throws Exception {
      System.out.println("enter weChatValidate!");
      String _token = "mylng";
      String outPut = "error";
      String signature = request.getParameter("signature");// 微信加密签名
      String timestamp = request.getParameter("timestamp");// 时间戳
      TIME_OPENID_MAP.put(timestamp, "");
      String nonce = request.getParameter("nonce");// 随机数 1413789908
      String echostr = request.getParameter("echostr");//
      System.out.println("in get nonce: "+nonce);
      String[] str = { _token, timestamp, nonce };
      Arrays.sort(str); // 字典序排序
      String bigStr = str[0] + str[1] + str[2]; // SHA1加密
//      String digest = SHA1.encode(bigStr);
      String digest = DecriptUtil.SHA1(bigStr);
      if (digest.equals(signature)) {
          outPut = echostr;
          System.out.println("check success!");
      }
      System.out.println("write response:"+outPut);
      PrintWriter writer = response.getWriter();
      writer.write(outPut);
      writer.flush();
      writer.close();
      return null;
  }
  
  @RequestMapping(value = "/gateway",
          method = RequestMethod.POST,
          produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public void handleWechatMessage(HttpServletRequest request,HttpServletResponse response) throws Exception {
      System.out.println("received wechat event!");  
      request.setCharacterEncoding("UTF-8");
      response.setCharacterEncoding("UTF-8");
      String nonce = request.getParameter("nonce");
      System.out.println("in post nonce: "+nonce);
      request.getSession().setAttribute("nonce", nonce);
      // 调用核心业务类接收消息、处理消息
      String respMessage = processRequest(request,response);
      // 响应消息
      PrintWriter out = response.getWriter();
      out.print(respMessage);
      out.close();
}
  
  /**
   * 处理微信发来的请求
   * 
   * @param request
   * @return
   */
  public String processRequest(HttpServletRequest request,HttpServletResponse response) {
   String respMessage = null;
   try {
    // 默认返回的文本消息内容
    String respContent = "请求处理异常，请稍候尝试！";
    // xml请求解析
    Map<String, String> requestMap = MessageUtil.parseXml(request);
    // 发送方帐号（open_id）
    String fromUserName = requestMap.get("FromUserName");
      request.setAttribute("openId", fromUserName);
      HttpSession session=request.getSession();
      session.setAttribute("openId", fromUserName);
      
    System.out.println("from:"+fromUserName);
    // 公众帐号
    String toUserName = requestMap.get("ToUserName");
    // 消息类型
    String msgType = requestMap.get("MsgType");
    System.out.println("MsgType:"+msgType);
    // 回复文本消息
    TextMessage textMessage = new TextMessage();
    textMessage.setToUserName(fromUserName);
    textMessage.setFromUserName(toUserName);
    textMessage.setCreateTime(new Date().getTime());
    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
    textMessage.setFuncFlag(0);
    // 文本消息
    if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
     respContent = "您发送的是文本消息！";
    }
    // 图片消息
    else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
     respContent = "您发送的是图片消息！";
    }
    // 地理位置消息
    else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
     respContent = "您发送的是地理位置消息！";
    }
    // 链接消息
    else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
     respContent = "您发送的是链接消息！"+requestMap.get("EventKey");
    }
    // 音频消息
    else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
     respContent = "您发送的是音频消息！";
    }
    // 事件推送
    else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
     // 事件类型
     String eventType = requestMap.get("Event");
     // 订阅
     if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
      respContent = "谢谢您的关注！";
     }
     // 取消订阅
     else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
      // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
     }
     // 自定义菜单点击事件
     else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
         // TODO 自定义菜单权没有开放，暂不处理该类消息
         String key = requestMap.get("EventKey");
         if(key.equals("MAINTAIN")){
             respContent = "服务热线：400-008-7070";
         }
         if(key.equals("INFO")){
             respContent = "我是爱车，我为主人服务！";
         }
         System.out.println("event key:"+key);
     }
     // 自定义view界面
     else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)){
         String url = requestMap.get("EventKey");
         System.out.println("url:"+url);
//         String[] urls = url.split("&redirect_uri=");
//         String lastUrl = "";
//         String lastUrl2 = "";
//         Member user = memberService.findByWeixinOpenId(fromUserName);
         /*if (user == null) {
             lastUrl = urls[1];
             String[] urls1 = lastUrl.split("&response_type=");
             String str1 = urls1[0] + "?openId=" + fromUserName;
             String url0 = URLEncoder.encode(str1,"UTF-8");
             lastUrl2 = urls[0] + "&redirect_uri=" + url0 + "&response_type=" + urls1[1];
         } else {
             
//              JsonConfig config = new JsonConfig();    
//              config.setIgnoreDefaultExcludes(false);       
//              config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                  //这里是核心，过滤掉不想使用的属性
//              config .setExcludes(filterNames) ;
             
//             JsonConfig config = new JsonConfig();
//             config.setJsonPropertyFilter(new PropertyFilter(){
//                 public boolean apply(Object source, String name, Object value) {
//                     if(name.equals("lockedDate") || name.equals("loginDate") || name.equals("loginPhoneDate") || name.equals("birth") || name.equals("loginDate")) {  // loginPhoneDate checkDate birth
//                         return true;
//                     } else {
//                         return false;
//                     }
//                 }
//             });
             
             
//             JSONObject json = JSONObject.fromObject(user, config);//将java对象转换为json对象
//             String userJson = json.toString();//将json对象转换为字符串
             
             
//             System.out.println("userJson:"+userJson);
             lastUrl = urls[0] + "&member=" + user.toString() + "#w" + urls[1];
         }*/
     }
    }
    textMessage.setContent(respContent);
    respMessage = MessageUtil.textMessageToXml(textMessage);
   } catch (Exception e) {
    e.printStackTrace();
   }
   return respMessage;
  
 }
  
}
 