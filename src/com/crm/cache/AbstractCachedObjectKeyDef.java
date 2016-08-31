package com.crm.cache;



import java.util.Comparator;

/**
 * 
 * @author
 *
 */
public abstract class AbstractCachedObjectKeyDef implements CachedObjectKeyDef
{   
	/**
	 * 
	 * @param isUnique-设定key对应的值是否唯一（true是，false不是）
	 * 
	 */
	public AbstractCachedObjectKeyDef( boolean isUnique ){
		this.isUnique = isUnique;
	}
	 
    private Comparator comparator;
    boolean isUnique = true;
    /**
     * 抽象类默认key对应的值是唯一的（取值true）
     * 如果key对应多个值那么 isUnique属性为false
     */
    public boolean isUnique()
    {
        return isUnique;
    }

    public Comparator getComparator()
    {
        return comparator;
    }

    public void setComparator(Comparator comparator)
    {
        this.comparator = comparator;
    }
    public boolean equals(Object object)
    {
        if (object == null)
            return false;
        if (object == this || getClass().equals(object.getClass()))
            return true;
        return false;
    }

    public int hashCode()
    {
        return getClass().hashCode();
    }

}

