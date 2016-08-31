package com.crm.cachecite.refreshcache;


import java.util.ArrayList;
import java.util.List;

import com.crm.cache.RefreshCallBack;

public class Dictionary_Refresh implements RefreshCallBack{

	/**
	 * 告诉缓存系统 如何取得对象数据
	 */
	@SuppressWarnings("unchecked")
	public List setSourceList() {
		//因为是注解的bean，所以默认bean的ID是class名，首字母小写dictionaryDaoImpl
		/*DictionaryDaoImpl dictionaryDao = (DictionaryDaoImpl)Tool.getBean("dictionaryDaoImpl");
		
		List<Cache> result = new ArrayList();
		try {
			result = dictionaryDao.findDictionaryTree(new Cache());
			System.out.println("Dictionary_Refresh___>>>> "+result.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		List result = new ArrayList();
		 
		return result;
	}

}
