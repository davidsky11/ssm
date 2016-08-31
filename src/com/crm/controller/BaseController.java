package com.crm.controller;

import javax.servlet.http.HttpServletResponse;

public abstract class BaseController {
	
	/*SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-mm-dd hh:mm:ss");*/
	
	//@InitBinder
	//protected void initBinder(WebDataBinder binder) {
		//binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));    
		//binder.registerCustomEditor(java.sql.Timestamp.class, new CustomTimestampEditor(datetimeFormat, true));
		/*binder.registerCustomEditor(int.class, new CustomNumberEditor(int.class, true));    
        binder.registerCustomEditor(int.class, new IntegerEditor());    
        binder.registerCustomEditor(long.class, new CustomNumberEditor(long.class, true));  
        binder.registerCustomEditor(long.class, new LongEditor());    
        binder.registerCustomEditor(double.class, new DoubleEditor());    
        binder.registerCustomEditor(float.class, new FloatEditor());*/
   //}   
	
	/**
	 * 输出json
	 * @param response
	 * @param json
	 */
	public void write(HttpServletResponse response,String json) {
	    try {
	      response.setCharacterEncoding("UTF-8");
	      response.setContentType("text/json");
	      response.getWriter().write(json);
	      response.getWriter().flush();
	      response.getWriter().close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	
	  /**
	   * 返回结果消息
	   * @param response
	   * @param bool
	   */
	  public void write(HttpServletResponse response,boolean bool) {
	    try {
	      String json = "{\"success\":\"" + bool + "\"}";
	      response.getWriter().write(json);
	      response.getWriter().flush();
	      response.getWriter().close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
}
