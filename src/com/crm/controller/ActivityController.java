package com.crm.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.crm.domain.Activity;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
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

	private final Logger log = LoggerFactory.getLogger(ActivityController.class);

	@Resource
	private ActivityService activityService;

	/**
	 * @Title: accountList
	 * @Description: 跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/activity/list", method = RequestMethod.GET)
	public String activityList(Model model) throws IOException {
		return "activity/list";
	}

	/**
	 * 新增用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/activity/addActivity", method = RequestMethod.POST)
	public Json addActivity(HttpServletRequest request, HttpServletResponse response, Activity activity) {
		Json j = new Json();
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);

		try {
			if (activity != null) {
				activity.setPublisherId(user.getId());
				activity.setPublisherName(user.getUsername());
				activityService.addActivity(activity);
				j.setSuccess(true);
				j.setMsg("活动新增成功！");
				j.setObj(activity);
			} else {
				j.setSuccess(false);
				j.setMsg("活动新增失败!");
			}

		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/activity/editActivity", method = RequestMethod.POST)
	public Json editActivity(HttpServletRequest request, HttpServletResponse response, Activity activity) {
		Json j = new Json();
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);

		try {
			if (activity != null) {
				log.debug("穿过来的用户ID为：" + activity.getId());

				activity.setPublisherId(user.getId());
				activity.setPublisherName(user.getUsername());
				activityService.updateActivity(activity);
				j.setSuccess(true);
				j.setMsg("活动编辑成功！");
				j.setObj(activity);
			} else {
				j.setSuccess(false);
				j.setMsg("活动信息有误!");
			}
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 删除某个用户
	 * 
	 * @param userId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/activity/deleteActivity", method = RequestMethod.POST)
	public Json deleteActivity(Activity activity) {
		Json j = new Json();
		log.debug("穿过来的用户ID为：" + activity.getId());
		try {
			activityService.deleteActivity(activity.getId());
			j.setSuccess(true);
			j.setMsg("删除成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 前端用户表格
	 */
	@ResponseBody
	@RequestMapping(value = "/activity/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page) {
		DataGrid dg = new DataGrid();

		Activity activity = new Activity();
		dg.setTotal(activityService.getDatagridTotal(activity));
		List<Activity> activityList = activityService.datagridActivity(page, activity);
		dg.setRows(activityList);
		return dg;
	}

	//////////////////////////////////////////////////////////////////////

	/**
	 * 跳转到用户表格页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/aty/list", method = RequestMethod.GET)
	public String atyList(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
		Page<Activity> page = new Page<Activity>();
		page.setPage(pageNumber);

		page = activityService.atyPages(page, "");

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
	public String addAty(Model model, Activity activity, HttpServletRequest request,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		User curUser = (User) request.getSession().getAttribute(Const.SESSION_USER);

		if (!image.isEmpty()) {
			String realPath = request.getSession().getServletContext().getRealPath("/upload");

			try {
				FileUtils.copyInputStreamToFile(image.getInputStream(), new File(realPath, image.getOriginalFilename()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			activity.setImage(image.getName());
		}

		try {
			activity.setPublisherId(curUser.getId());
			activity.setPublisherName(curUser.getUsername());

			activityService.addActivity(activity);

		} catch (Exception e) {

		}

		Page<Activity> page = new Page<Activity>();
		page = activityService.atyPages(page, "");

		model.addAttribute("page", page);
		model.addAttribute("atys", page.getContent());

		return "activity/list";
	}

	@RequestMapping(value = "/aty/delete", method = RequestMethod.POST)
	public String deleteAtys(Model model, @RequestParam("deleteIds[]") String[] deleteIds) {

		String ids = Tool.stringArrayToString(deleteIds, true, ",");
		/*
		 * StringBuffer sql = new StringBuffer();
		 * sql.append("and id in (").append(ids).append(")");
		 */

		activityService.deleteActivity("(" + ids + ")");

		Page<Activity> page = new Page<Activity>();
		page = activityService.atyPages(page, "");

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
	public String updateAty(Model model, Activity activity) {
		activityService.updateActivity(activity);

		Page<Activity> page = new Page<Activity>();
		page = activityService.atyPages(page, "");

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
			HtmlUtil.title = aty.getTitle();
			HtmlUtil.context = aty.getContent();
			HtmlUtil.img = Const.HTML_LEVEL + aty.getImage();

			HtmlUtil.JspToHtmlFile(HtmlUtil.getClassesPath() + Const.HTML_TEMPLATE_FILE,
					HtmlUtil.getWebInfoPath() + outPath);
		}

		PrintWriter out = null;
		try {
			out = response.getWriter();
			System.out.println(outPath);
			out.write("<a href='" + Const.ROOT_HTML_URL + aty.getPublicCode() + ".html'>");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
