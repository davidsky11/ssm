package com.crm.util.recharge; 

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.crm.util.RandomUtil;
import com.crm.util.common.Const;
import com.crm.wechat.utils.MD5Util;

import net.sf.json.JSONObject;

/** 
 * @ClassName	QbRecharge.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月5日 上午12:15:52
 * @Version 	V1.0    
 */
public class QbRecharge {

	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	    
	//配置您申请的KEY
    public static final String APPKEY = Const.JUHE_QB_APPKEY;
 
    //1.商品小类列表
    public static void getRequest1(){
        String result =null;
        String url ="http://op.juhe.cn/ofpay/game/cardlist";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //2.商品信息
    public static void getRequest2(String cardId) {
        String result =null;
        String url ="http://op.juhe.cn/ofpay/game/cardinfo";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("cardid", cardId);//对应接口1的cardid
            params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //3.商品价格查询
    public static void getRequest3(String cardId) {
        String result =null;
        String url ="http://op.juhe.cn/ofpay/game/cardprice";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("cardid", cardId);//对应接口2的cardid
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //4.游戏直充区服查询
    public static void getRequest4(String cardId){
        String result =null;
        String url ="http://op.juhe.cn/ofpay/game/areaserver";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("cardid", cardId);//对应接口3的cardid
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //5.游戏直充
    public static void getRequest5(){
        String result =null;
        String url ="http://op.juhe.cn/ofpay/game/order";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("cardid","");//商品编码，对应接口3的cardid
            params.put("cardnum","");//购买数量
            params.put("orderid","");//订单号，8-32位数字字母组合
            params.put("game_userid","");//游戏玩家账号(game_userid=xxx@162.com$xxx001 xxx@162.com是通行证xxx001是玩家账号)
            params.put("game_area","");//游戏所在区域，没有则不填，具体参照接口4返回，URLEncode UTF8
            params.put("game_srv","");//游戏所在服务器，没有则不填，具体参照接口4返回，URLEncode UTF8
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("sign","");//校验值，md5(<b>OpenID</b>+key+cardid+cardnum+orderid+game_userid+game_area+game_srv)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //6.订单状态查询
    public static void getRequest6(String orderId){
        String result =null;
        String url ="http://op.juhe.cn/ofpay/game/ordersta";//请求接口地址
        Map params = new HashMap();//请求参数
            params.put("orderid", orderId);//商家订单号，8-32位字母数字组合
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                        out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }
 
    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
    	String result =null;
        String url ="http://op.juhe.cn/ofpay/game/order";//请求接口地址
        String cardId = "220612";
        cardId = "2272901";
        int cardnum = 1;
        String orderId = RandomUtil.getRandomNumber(8);
        String game_userid = "1024329292";
        String key = APPKEY;
        
        Map params = new HashMap();//请求参数
            params.put("cardid", cardId);//商品编码，对应接口3的cardid
            params.put("cardnum", cardnum);//购买数量
            params.put("orderid", orderId);//订单号，8-32位数字字母组合
            params.put("game_userid", game_userid);//游戏玩家账号(game_userid=xxx@162.com$xxx001 xxx@162.com是通行证xxx001是玩家账号)
            params.put("game_area","");//游戏所在区域，没有则不填，具体参照接口4返回，URLEncode UTF8
            params.put("game_srv","");//游戏所在服务器，没有则不填，具体参照接口4返回，URLEncode UTF8
            params.put("key", key);//应用APPKEY(应用详细页查询)
            
            String sign = MD5Util.MD5(Const.JUHE_OPENID + key + cardId + cardnum + orderId + game_userid + "" + "");
            params.put("sign", sign);//校验值，md5(<b>OpenID</b>+key+cardid+cardnum+orderid+game_userid+game_area+game_srv)
 
        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 