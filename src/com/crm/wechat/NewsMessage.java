package com.crm.wechat;

import java.util.List;

/** 
 * @ClassName	NewsMessage.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月25日 上午1:44:53
 * @Version 	V1.0    
 */
public class NewsMessage extends BaseMessage {

    //	 图文消息个数，限制为10条以内  
    private int ArticleCount;  
    // 多条图文消息信息，默认第一个item为大图  
    private List<Article> Articles;  
  
    public int getArticleCount() {  
        return ArticleCount;  
    }  
  
    public void setArticleCount(int articleCount) {  
        ArticleCount = articleCount;  
    }  
  
    public List<Article> getArticles() {  
        return Articles;  
    }  
  
    public void setArticles(List<Article> articles) {  
        Articles = articles;  
    }
    
}
 