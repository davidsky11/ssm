package com.crm.cache;

import java.util.List;
/**
 * 缓存刷新的回调接口
 * 通过该接口缓存可以获得到要缓存的数据集合
 * @author comm
 *
 */
public interface RefreshCallBack {
	   
	  /**
	    * 提示涉及db操作不要忘记关闭连接
	    * 注意： 如返回值 List建议不要返回null
	    * @return
	    */
       public List setSourceList();
}
