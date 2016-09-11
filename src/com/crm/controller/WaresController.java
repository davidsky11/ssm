package com.crm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crm.common.util.lang.DateUtil;
import com.crm.common.util.math.RandomUtil;
import com.crm.common.util.office.ExcelUtil;
import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.User;
import com.crm.domain.Wares;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ActivityService;
import com.crm.service.AwardService;
import com.crm.service.WaresService;
import com.crm.util.Tool;
import com.crm.util.common.Const;

/** 
 * @ClassName	WaresController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:41:33
 * @Version 	V1.0    
 */
@Controller
public class WaresController {
	
	private final Logger log = LoggerFactory.getLogger(WaresController.class);
	
	@Autowired
	private WaresService waresService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private AwardService awardService;

	/**
	 * @Title:			accountList
	 * @Description:	跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/wares/list", method = RequestMethod.GET)
	public String waresList(Model model) throws IOException { 
		return "wares/list";
	}
	
	
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/addWares",method = RequestMethod.POST)
    public Json addWares(Wares wares) {
		Json j = new Json();
		
		try {
            waresService.addWares(wares);
            j.setSuccess(true);
            j.setMsg("用户新增成功！");
            j.setObj(wares);
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
    @RequestMapping(value = "/wares/editWares",method = RequestMethod.POST)
    public Json editWares(Wares wares) {
        Json j = new Json();
        log.debug("穿过来的用户ID为：" + wares.getId());
        try {
            waresService.updateWares(wares);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
            j.setObj(wares);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/deleteWares",method = RequestMethod.POST)
	public Json deleteWares(Wares wares) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+wares.getId());
        try {
			waresService.deleteWares(wares.getId());
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
	@RequestMapping(value = "/wares/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page) {
		DataGrid dg = new DataGrid();
		
		Wares wares = new Wares();
		dg.setTotal(waresService.getDatagridTotal(wares));
		List<Wares> waresList = waresService.datagridWares(page, wares);
		dg.setRows(waresList);
		return dg;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @Title:			waresConfig
	 * @Description:	为活动配置编码
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/wares/config", method = RequestMethod.GET)
	public ModelAndView waresConfig(Model model) throws IOException { 
		ModelAndView mv = new ModelAndView("wares/config");
		
		List<Activity> atyList = this.activityService.getActivityList("");
		mv.addObject("atyList", atyList);
		
		return mv;
	}
	
	/**
	 * @Title:			configList
	 * @Description:	活动配置列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/configList", method = RequestMethod.POST)
	public List<Activity> configList(HttpServletRequest request) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		//List<ConfigDto> list = new ArrayList<ConfigDto>();
		
		String activityId = request.getParameter("activityId");
		
		List<Activity> atyList = new ArrayList<Activity>();
		if (activityId != null && !activityId.equals("")) {
			atyList = activityService.getActivityList(" and id = '" + activityId + "'");
		} else {
			atyList = activityService.getActivityList(" and publisherId = '" + user.getId() + "'");
		}
		
		/*atyList = this.activityService.getActivityList(" and t.count is not null");
		for (Activity aty : atyList) {
			ConfigDto cd = new ConfigDto();
			cd.setActivity(aty);
			cd.setActivityId(aty.getId());
			cd.setActivityName(aty.getTitle());
			cd.setCount(aty.getCount());
			cd.setMoney(aty.getAmount());
		}*/
		
		return atyList;
	}
	
	/**
	 * @Title:			addConfig
	 * @Description:	添加编码配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/addConfig", method = RequestMethod.POST)
	public Json addConfig(HttpServletRequest request) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		Json j = new Json();
		
		String activityId = request.getParameter("activityIdA");
		if (activityId == null || activityId.equals("")) {
			j.setSuccess(false);
			j.setMsg("请选择正确的活动");
			return j;
		}
		
		Activity activity = activityService.findById(activityId);
		
		String tmp = request.getParameter("count");
		int count = Integer.parseInt(tmp);  // 编码数目
		
		double totalMoney = 0.0d;  // 总共的奖项预算经费
		
		/**
		 * 0、获取该活动对应的奖项信息
		 */
		List<Award> awardList = awardService.getDatagrid(" and activityId = '" + activityId + "' order by total asc");
		/*Collections.sort(awardList, new Comparator<Award>() {

			@Override
			public int compare(Award o1, Award o2) {
				return o2.getTotal() - o1.getTotal();
			}
			
		});*/
		
		Map<Integer, String> countMap = new HashMap<Integer, String>();
		int[] countArr = new int[awardList.size()];
		
		int number = 0;  // 获取奖项的总个数
		int p = 0;
		for (Award aw : awardList) {
			number += aw.getTotal();
			countMap.put(number, aw.getId());  // 2 5 8 11 递增序列
			countArr[p++] = number;
			
			totalMoney = aw.getAmount() * aw.getTotal();
		}
		
		int step = (number == 0 ? count : count/number);
		step = (step <= 0 ? 1 : step);
		
		/**
		 * 1、根据编码数目 生成商品 码
		 */
		List<Wares> waresList = new ArrayList<Wares>();
		
		for (int i=0; i<count; i++) {
			Wares w = new Wares();
			w.setId(Tool.generateMajorKey());
			w.setPublicCode(activity.getPublicCode());
			w.setPrivateCode(RandomUtil.generateMixString(6));
			w.setInsideCode(RandomUtil.generateMixString(3));
			w.setCreater(user.getUsername());
			w.setCreateTime(DateUtil.formatDate(new Date()));
			w.setStatus("0");
			
			if (i%step == 0) {
				for (int m=0; m<countArr.length; m++) {
					if (i/step < countArr[m]) {
						w.setAwardId(countMap.get(countArr[m]));
						continue;
					}
				}
			} else {
				w.setAwardId("");
			}
			
			waresList.add(w);
		}
		
		/**
		 * 2、批量插入商品编码信息
		 */
		boolean isSuccess = waresService.addWaresBatch(waresList) > 0;
		
		/**
		 * 3、修改活动的奖项经费数据
		 */
		activity.setAmount(totalMoney);
		int result = activityService.updateActivity(activity);
		
		if (isSuccess && result > 0) {
			j.setSuccess(true);
			j.setMsg("成功生成商品编码" + waresList.size()+"条");
		} else {
			j.setSuccess(false);
			j.setMsg("生成商品编码失败");
		}
		
		return j;
		
	}
	
	/**
	 * @Title:			downloadConfig
	 * @Description:	根据活动下载编码信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/downloadConfig", method = RequestMethod.POST)
	public Json downloadConfig(HttpServletRequest request, HttpServletResponse response) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		Json j = new Json();
		
		String activityId = request.getParameter("activityId");
		Activity activity = activityService.findById(activityId);
		
		String publicCode = activity.getPublicCode();
		
		// TODO 根据活动导出对应的编码数据 Excel形式
		
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("test", "1");
		beans.put("try", "2");
		//ExcelUtil.exportAndDownload("c:/temp", "wares", beans, response);
		
		j.setMsg("导出成功!");
		return j;
	}
	
}
 