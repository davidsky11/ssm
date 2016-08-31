package com.crm.cache;

import java.util.Comparator;

/**
 * 缓存key定义接口 描述了缓存key特定的方法或属性
 * 缓存将根据key的实现类创建索引，和检索数据，所有key的实现类必须重写equals和hashcode方法以保证
 * 两个不同的对象但拥有相同的key被认为相等。
 * 
 * 如果一个key允许不同的属性，那么这些属性也需要重写equals和hashcode方法
 * 
 * 如果一个key继承自 AbstractCachedObjectKeyDef类 ，并且key允许配置和属性确定，那么需要重写缺省的
 * 
 * 每个key的实现类应当有特定的创建key的方法，例如，要创建一个根据id属性 ，使用如下方法
 * Object key = CachedObjectIdKeyDef.createKeyFromId(id);
 */
public interface CachedObjectKeyDef
{
	/**true表示key只对应一个value反之对应多个*/
    public boolean isUnique();
    /**
     * 创建key根据该方法
     * @param object
     * @return
     */
    public Object createKey(Object object);
    /**
     * 该方法能用于描述对象索引根据key应当排序的情况
     * 如果    该对象是 null 则不排序
     * 如果    当前key是缓存中的第一个唯一key,它将控制排序
     * 如果    当前key不唯一，它将控制所有的对象的顺序(也就是key对应多个值时（集合也按照该规则排序)
     *
     * @return Comparator
     */
    public Comparator getComparator();
}
