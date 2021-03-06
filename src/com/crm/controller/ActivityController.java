package com.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.domain.Activity;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.service.ActivityService;
import com.crm.util.HtmlUtil;
import com.crm.util.Tool;
import com.crm.util.common.Const;

/**
 * @ClassName ActivityController.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年7月5日 下午3:41:33
 * @Version V1.0
 */
@Controller
public class ActivityController {

	@Resource
	private ActivityService activityService;

	//////////////////////////////////////////////////////////////////////

	/**
	 * 跳转到用户表格页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/aty/list", method = RequestMethod.GET)
	public String atyList(Model model, HttpServletRequest request, 
			@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		Page<Activity> page = new Page<Activity>();
		page.setPage(pageNumber);

		page = activityService.atyPages(page, " and a.publisherId = '" + user.getId() + "'");

		model.addAttribute("page", page);
		model.addAttribute("atys", page.getContent());

		return "activity/list";
	}

	/**
	 * @Title: prepareAddAty
	 * @Description: 跳转到活动新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/aty/prepareAdd", method = RequestMethod.GET)
	public String prepareAddAty(Model model) {
		return "activity/add";
	}

	/**
	 * @Title: addAty
	 * @Description: 添加厂商
	 * @param model
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/aty/add", method = RequestMethod.POST)
	public String addAty(Model model, Activity activity, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);

		try {
			activity.setPublisherId(user.getId());
			activity.setPublisherName(user.getUsername());

			activityService.addActivity(activity);

		} catch (Exception e) {

		}

		Page<Activity> page = new Page<Activity>();
		page = activityService.atyPages(page, " and a.publisherId = '" + user.getId() + "'");

		model.addAttribute("page", page);
		model.addAttribute("atys", page.getContent());

		return "activity/list";
	}

	@RequestMapping(value = "/aty/delete", method = RequestMethod.POST)
	public String deleteAtys(Model model, HttpServletRequest request, @RequestParam("deleteIds[]") String[] deleteIds) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		String ids = Tool.stringArrayToString(deleteIds, true, ",");
		activityService.deleteActivity("(" + ids + ")");

		Page<Activity> page = new Page<Activity>();
		page = activityService.atyPages(page, " and a.publisherId = '" + user.getId() + "'");

		model.addAttribute("page", page);
		model.addAttribute("atys", page.getContent());
		return "activity/list";
	}

	@RequestMapping(value = "/aty/detail/{id}", method = RequestMethod.GET)
	public String detailAty(Model model, @PathVariable("id") String id) {
		Activity aty = activityService.findById(id);
		model.addAttribute("aty", aty);

		return "activity/detail";
	}

	@RequestMapping(value = "/aty/edit/{id}", method = RequestMethod.GET)
	public String prepareUpdateUser(Model model, @PathVariable("id") String id) {
		Activity aty = activityService.findById(id);
		model.addAttribute("aty", aty);
		return "activity/edit";
	}

	@RequestMapping(value = "/aty/update", method = RequestMethod.POST)
	public String updateAty(Model model, HttpServletRequest request, Activity activity) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		activityService.updateActivity(activity);

		Page<Activity> page = new Page<Activity>();
		page = activityService.atyPages(page, " and a.publisherId = '" + user.getId() + "'");

		model.addAttribute("page", page);
		model.addAttribute("atys", page.getContent());
		return "activity/list";
	}

	/**
	 * 获取活动展示页面
	 * 
	 * @Title: atyInfo
	 * @Description: 获取活动展示页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/aty/info/{id}", method = RequestMethod.GET)
	public String atyInfo(Model model, @PathVariable("id") String id) {
		Activity aty = activityService.findById(id);
		model.addAttribute("aty", aty);

		return "activity/info";
	}

	@RequestMapping(value = "/aty/makeAtyInfo/{id}", method = RequestMethod.GET)
	public void makeAtyInfo(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		Activity aty = activityService.findById(id);

		String outPath = Const.HTML_OUTPUT_PATH + aty.getPublicCode() + ".html";
		if (aty != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			HtmlUtil.title = aty.getTitle();
			HtmlUtil.context = aty.getContent();
			HtmlUtil.img = Const.HTML_LEVEL + aty.getImage();
			HtmlUtil.startDate = sdf.format(aty.getStartTime());
			HtmlUtil.endDate = sdf.format(aty.getEndTime());

			HtmlUtil.JspToHtmlFile(HtmlUtil.getClassesPath() + Const.HTML_TEMPLATE_FILE,
					HtmlUtil.getWebInfoPath() + outPath);
		}

		PrintWriter out = null;
		try {
			out = response.getWriter();
			System.out.println(outPath);
			out.write(Const.ROOT_HTML_URL + aty.getPublicCode() + ".html");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 活动检索
	 * @Title:			atySearch
	 * @Description:	活动检索
	 * @param model
	 * @param request
	 * @param deleteIds
	 * @return
	 */
	@RequestMapping(value = "/aty/search", method = RequestMethod.POST)
	public String atySearch(Model model, HttpServletRequest request, @RequestParam("atyInfo") String atyInfo) {
		Page<Activity> page = new Page<Activity>();
		StringBuffer conditionSql = new StringBuffer();
		if (Tool.isNotNullOrEmpty(atyInfo)) {
			conditionSql.append(" and (a.title like '%").append(atyInfo).append("%'");
			conditionSql.append(" or a.content like '%").append(atyInfo).append("%'");
			conditionSql.append(" or a.publicCode like '%").append(atyInfo).append("%')");
			model.addAttribute("atyInfo", atyInfo);
		}
		
		page = activityService.atyPages(page, conditionSql.toString());

		model.addAttribute("page", page);
		model.addAttribute("atys", page.getContent());
		return "activity/list";
	}
	

}
