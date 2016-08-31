package com.crm.wechat.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import com.crm.util.common.MD5;

/** 
 * @ClassName	Sign.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:18:55
 * @Version 	V1.0    
 */
public class Sign {

	public static String getSign(Object o) throws IllegalAccessException {  
        ArrayList<String> list = new ArrayList<String>();  
        Class cls = o.getClass();  
        Field[] fields = cls.getDeclaredFields();  
        for (Field f : fields) {  
            f.setAccessible(true);  
            if (f.get(o) != null && f.get(o) != "") {  
                list.add(f.getName() + "=" + f.get(o) + "&");  
            }  
        }  
        int size = list.size();  
        String [] arrayToSort = list.toArray(new String[size]);  
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);  
        StringBuilder sb = new StringBuilder();  
        for(int i = 0; i < size; i ++) {  
            sb.append(arrayToSort[i]);  
        }  
        String result = sb.toString();  
        result += "key=" + Configure.getKey();  
        result = MD5.MD5Encode(result).toUpperCase();  
        return result;  
    }  
}
 