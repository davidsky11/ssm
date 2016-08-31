package com.crm.cachecite.keydef;

import com.crm.cache.AbstractCachedObjectKeyDef;

public class Dictionary_ParendId_Key extends AbstractCachedObjectKeyDef{
		/**
		 * 
		 * @param isUnique 表示从缓存当中查询返回结果是单一对象 还是对象的集合
		 */
		public Dictionary_ParendId_Key(boolean isUnique) {
			super(isUnique);
		}
		
		/**
		 *创建键的方法  用于通知缓存系统
		 */
		public Object createKey(Object object) {
			/*SysDictionary obj = (SysDictionary) object;
			return  obj.getpGuid();*/
			return "";
		}
}
