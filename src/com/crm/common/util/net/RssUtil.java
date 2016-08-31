package com.crm.common.util.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.horrabin.horrorss.RssFeed;
import org.horrabin.horrorss.RssItemBean;
import org.horrabin.horrorss.RssParser;

/**
 * 工具类-》网络相关工具类-》RSS消息聚合工具类
 * <p>
 * [依赖 horrorss.jar]
 * </p>
 */
public final class RssUtil {
	private RssUtil() {
		throw new Error("工具类不能实例化！");
	}

	/**
	 * 获取rss
	 */
	public List<Map<String, Object>> getRss(final String url) {
		RssParser rss = new RssParser();
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		Map<String, Object> itemMap;
		List<RssItemBean> items = new ArrayList<RssItemBean>();
		try {
			RssFeed feed = rss.load(url);
			// RssChannelBean channel = feed.getChannel();
			// RssImageBean image = feed.getImage();
			items = feed.getItems();
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		for (RssItemBean item : items) {
			itemMap = new HashMap<String, Object>();
			itemMap.put("author", item.getAuthor());
			itemMap.put("category", item.getCategory());
			itemMap.put("title", item.getTitle());
			itemMap.put("link", item.getLink());
			itemMap.put("pubDate", item.getPubDate());
			itemMap.put("desc", item.getDescription());
			results.add(itemMap);
		}
		return results;
	}
}
