package com.crm.common.util.sensitive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName WordsFilter.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年7月10日 下午8:42:57
 * @Version V1.0
 */
public class WordsFilter implements Filter {

	private List<String> banWords = new ArrayList(); // 禁用词
	private List<String> auditWords = new ArrayList(); // 审核词
	private List<String> replaceWords = new ArrayList(); // 替换词

	// 初始化将敏感词库全部加载到集合中
	public void init(FilterConfig filterConfig) throws ServletException {

		try {
			String path = WordsFilter.class.getClassLoader().getResource("").getPath(); // 得到类目录
			File files[] = new File(path).listFiles(); // 得到目录下的所有文件
			for (File file : files) {
				// 如果不是文件返回
				if (!file.isFile()) {
					continue;
				}
				// 如果不是txt文件返回
				if (!file.getName().endsWith(".txt")) {
					continue;
				}
				// 读txt文件
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = br.readLine()) != null) {
					String s[] = line.split("\\|");
					if (s.length != 2) {
						continue;
					}
					String level = s[1].trim();
					if ("1".equals(level)) {
						banWords.add(s[0]);
					}
					if ("2".equals(level)) {
						auditWords.add(s[0]);
					}
					if ("3".equals(level)) {
						replaceWords.add(s[0]);
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// 用户提交的数据是否包含禁用词
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String value = request.getParameter((String) e.nextElement());
			for (String regex : banWords) {
				// 正则表达式判断用户输入的内如是否存在
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(value);
				if (m.find()) {
					request.setAttribute("message", "你发表的文章中含有非法词汇，你懂的！！");
					request.getRequestDispatcher("/message.jsp").forward(request, response);
					return;
				}
			}
		}

		// 用户提交的数据是否包含审核词和替换词，自定义包装类
		Myrequest myrequest = new Myrequest(request);
		// 放行
		chain.doFilter(myrequest, response);
	}

	class Myrequest extends HttpServletRequestWrapper {

		private HttpServletRequest request;

		public Myrequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}

		@Override
		public String getParameter(String name) {

			String value = this.request.getParameter(name);
			if (value == null) {
				return null;
			}

			// 检查数据中是否包含审核词
			for (String regex : auditWords) {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(value);
				while (m.find()) {
					String word = m.group(); // 得到找到的那个词
					value = value.replaceAll(regex, "<font color='red'>" + word + "</font>");
				}
			}

			// 检查数据中是否包含替换词
			for (String regex : replaceWords) {
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(value);
				while (m.find()) {
					value = value.replaceAll(regex, "****");
				}
			}
			return value;
		}
	}

	public void destroy() {

	}

}
