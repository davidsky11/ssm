package com.crm.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crm.common.util.math.RandomUtil;
import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.Page;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.Wares;
import com.crm.domain.dto.WaresDto;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ActivityService;
import com.crm.service.AwardService;
import com.crm.service.ScanRecordService;
import com.crm.service.WaresService;
import com.crm.util.ExportExcel;
import com.crm.util.Tool;
import com.crm.util.common.Const;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @ClassName WaresController.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年7月5日 下午3:41:33
 * @Version V1.0
 */
@Controller
public class WaresController {

	private final Logger log = LoggerFactory.getLogger(WaresController.class);

	@Resource
	private WaresService waresService;
	@Resource
	private ActivityService activityService;
	@Resource
	private AwardService awardService;
	@Resource
	private WaresExcelService waresExcelService;
	@Resource
	private ScanRecordService scanRecordService;

	/**
	 * @Title: accountList
	 * @Description: 跳转前端用户列表
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
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/addWares", method = RequestMethod.POST)
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
	@RequestMapping(value = "/wares/editWares", method = RequestMethod.POST)
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
	 * 
	 * @param userId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/deleteWares", method = RequestMethod.POST)
	public Json deleteWares(Wares wares) {
		Json j = new Json();
		log.debug("穿过来的用户ID为：" + wares.getId());
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
	 * @Title: configList
	 * @Description: 活动配置列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/configList", method = RequestMethod.POST)
	public List<Activity> configList(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		// List<ConfigDto> list = new ArrayList<ConfigDto>();

		String activityId = request.getParameter("activityId");

		List<Activity> atyList = new ArrayList<Activity>();
		if (activityId != null && !activityId.equals("")) {
			atyList = activityService.getActivityList(" and id = '" + activityId + "'");
		} else {
			atyList = activityService.getActivityList(" and publisherId = '" + user.getId() + "'");
		}

		/*
		 * atyList =
		 * this.activityService.getActivityList(" and t.count is not null"); for
		 * (Activity aty : atyList) { ConfigDto cd = new ConfigDto();
		 * cd.setActivity(aty); cd.setActivityId(aty.getId());
		 * cd.setActivityName(aty.getTitle()); cd.setCount(aty.getCount());
		 * cd.setMoney(aty.getAmount()); }
		 */

		return atyList;
	}

	/**
	 * @Title: addConfig
	 * @Description: 添加编码配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/addConfig", method = RequestMethod.POST)
	public Json addConfig(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		Json j = new Json();

		String activityId = request.getParameter("activityIdA");
		if (activityId == null || activityId.equals("")) {
			j.setSuccess(false);
			j.setMsg("请选择正确的活动");
			return j;
		}

		Activity activity = activityService.findById(activityId);

		String tmp = request.getParameter("count");
		int count = Integer.parseInt(tmp); // 编码数目

		double totalMoney = 0.0d; // 总共的奖项预算经费

		/**
		 * 0、获取该活动对应的奖项信息
		 */
		List<Award> awardList = awardService.getDatagrid(" and activityId = '" + activityId + "' order by total asc");
		/*
		 * Collections.sort(awardList, new Comparator<Award>() {
		 * 
		 * @Override public int compare(Award o1, Award o2) { return
		 * o2.getTotal() - o1.getTotal(); }
		 * 
		 * });
		 */

		Map<Integer, String> countMap = new HashMap<Integer, String>();
		int[] countArr = new int[awardList.size()];

		int number = 0; // 获取奖项的总个数
		int p = 0;
		for (Award aw : awardList) {
			number += aw.getTotal();
			countMap.put(number, aw.getId()); // 2 5 8 11 递增序列
			countArr[p++] = number;

			totalMoney = aw.getAmount() * aw.getTotal();
		}

		int step = (number == 0 ? count : count / number);
		step = (step <= 0 ? 1 : step);

		/**
		 * 1、根据编码数目 生成商品 码
		 */
		List<Wares> waresList = new ArrayList<Wares>();

		for (int i = 0; i < count; i++) {
			Wares w = new Wares();
			w.setId(Tool.generateMajorKey());
			w.setPublicCode(activity.getPublicCode());
			w.setPrivateCode(RandomUtil.generateMixString(6));
			w.setInsideCode(RandomUtil.generateMixString(3));
			w.setCreater(user.getUsername());
			w.setCreateTime(new Date());
			w.setStatus("0");

			if (i % step == 0) {
				for (int m = 0; m < countArr.length; m++) {
					if (i / step < countArr[m]) {
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
			j.setMsg("成功生成商品编码" + waresList.size() + "条");
		} else {
			j.setSuccess(false);
			j.setMsg("生成商品编码失败");
		}

		return j;

	}

	//////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @Title: waresConfig
	 * @Description: 为活动配置编码
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/wares/config", method = RequestMethod.GET)
	public ModelAndView wesConfig(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		ModelAndView mv = new ModelAndView("wares/config");

		List<Activity> atyList = new ArrayList<Activity>();
		
		String activityId = request.getParameter("activityId");
		if (activityId != null && !activityId.equals("")) {
			atyList = activityService.getActivityList(" and id = '" + activityId + "'");
		} else {
			atyList = activityService.getActivityList(" and publisherId = '" + user.getId() + "'");
		}
		mv.addObject("atyList", atyList);

		return mv;
	}
	
	@RequestMapping(value = "/wes/prepareAdd", method = RequestMethod.GET)
	public ModelAndView wesPrepareAdd() {
		ModelAndView mv = new ModelAndView("wares/add");
		
		List<Activity> atyList = activityService.getActivityList("");
		mv.addObject("atyList", atyList);
		
		return mv;
	}
	
	@RequestMapping(value = "/wes/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editConfig(HttpServletRequest request, @PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("wares/edit");
		Activity aty = activityService.findById(id);
		
		mv.addObject("aty", aty);
		
		return mv;
	}
	
	@RequestMapping(value = "/wes/addCfg", method = RequestMethod.POST)
	public ModelAndView wesAddCfg(Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		ModelAndView mv = new ModelAndView("wares/config");

		String activityId = request.getParameter("id");
		if (activityId == null || activityId.equals("")) {
			mv.addObject("请选择正确的活动");
			return mv;
		}
		
		String tmp = request.getParameter("count");
		Integer count = Integer.parseInt(tmp);  //  生成的商品编码的个数
		
		Activity aty = activityService.findById(activityId);
		double totalMoney = 0.0d; // 总共的奖项预算经费

		/**
		 * 0、获取该活动对应的奖项信息
		 */
		List<Award> awardList = awardService.getDatagrid(" and activityId = '" + activityId + "' order by total asc");

		Map<Integer, String> countMap = new HashMap<Integer, String>();
		int[] countArr = new int[awardList.size()];

		int number = 0; // 获取奖项的总个数
		int p = 0;
		for (Award aw : awardList) {
			number += aw.getTotal();
			countMap.put(number, aw.getId()); // 2 5 8 11 递增序列
			countArr[p++] = number;

			totalMoney += aw.getAmount() * aw.getTotal();
		}
		
		// 如果生成的商品的总个数小于奖品的个数，则提示错误信息
		if (count < number) {
			//mv.setViewName("json");
			mv.addObject("生成的商品编码个数不能小于奖品数目");
			return mv;
		}

		int step = (number == 0 ? count : count / number);
		step = (step <= 0 ? 1 : step);

		/**
		 * 1、根据编码数目 生成商品 码
		 */
		List<Wares> waresList = new ArrayList<Wares>();
		
		Integer maxPrivateTitle = 0;
		Integer maxInsideTitle = 0;
		if (Integer.parseInt(aty.getPublicCode()) > 99) {
			maxPrivateTitle = 0;
		} else {
			maxPrivateTitle = 1;
		}
		
		if (Integer.parseInt(aty.getPublicCode()) < 294) {
			maxInsideTitle = 4;
		} else {
			maxInsideTitle = 3;
		}

		Random ran = new Random();

		String temp = "";
		for (int i = 0; i < count; i++) {
			Wares w = new Wares();
			w.setId(Tool.generateMajorKey());
			w.setPublicCode(aty.getPublicCode());  // publicCode 三位 000-999
			
			w.setPrivateCode(maxPrivateTitle + aty.getPublicCode() + ran.nextInt(4) + RandomUtil.generateNumberString(7) + ran.nextInt(9));  // 1 099 5116277 76
			temp = ran.nextInt(maxInsideTitle) + aty.getPublicCode() + ran.nextInt(8) + RandomUtil.generateNumberString(4) + ran.nextInt(9);
			w.setInsideCodeTmp(temp);   // 4 294 967269
			w.setInsideCode(temp);
			
			w.setCreater(user.getUsername());
			w.setCreateTime(new Date());
			w.setStatus("0");

			if (i % step == 0) {
				for (int m = 0; m < countArr.length; m++) {
					if (i / step < countArr[m]) {
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
		waresService.deleteByCondition(" publicCode = '" + aty.getPublicCode() + "'");
		waresService.addWaresBatch(waresList);
		

		/**
		 * 3、修改活动的奖项经费数据
		 */
		aty.setCount(count);
		aty.setAmount(totalMoney);
		activityService.updateActivity(aty);
		
		// to page
		List<Activity> list = activityService.getActivityList(" and publisherId = '" + user.getId() + "'");
		mv.addObject("atyList", list);

		return mv;
	}
	
	@RequestMapping(value = "/wes/downloadCfg1", method = RequestMethod.GET)
	public ResponseEntity<String> editConfig1(HttpSession session, @RequestParam("id") String id) throws Exception {
		String realPath = session.getServletContext().getRealPath("/WEB-INF/upload");
		String fileName="waresExport"+System.currentTimeMillis()+".xls";
		
		Activity aty = activityService.findById(id);
		waresExcelService.saveToExcel(realPath+"\\"+fileName, " and t.publicCode = '" + aty.getPublicCode() + "'");
		
		HttpHeaders headers = new HttpHeaders();    
		headers.setContentDispositionFormData("attachment", fileName); 	
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    return new ResponseEntity<String>(realPath+"\\"+fileName,    
                headers, HttpStatus.CREATED);
	    // FileUtils.readFileToByteArray(new File(realPath+"\\"+fileName)
	}
	
	@RequestMapping(value = "/wes/downloadCfg", method = RequestMethod.POST)
	public void editConfig(HttpServletRequest resquest, HttpServletResponse response, HttpSession session, @RequestParam("id") String id) throws Exception {
		ExportExcel ex = new ExportExcel();
		
		Activity aty = activityService.findById(id);
		List<Wares> waresList = waresService.getListByAtyId(id);
					
		String path = Const.TEMPFOLDER + "//emp.xls";
		OutputStream out = new FileOutputStream(path);
		createExcel(aty, out, waresList);
		
		out.close();
		ex.download(path, response);
		System.out.println("excel导出成功！");
	}
	
	private void createExcel(Activity aty, OutputStream os, List<Wares> list){
		String[] heads={"publicCode[公共编码]","atyCode[活动编码]","privateCodeTmp[瓶身外码]","insideCode[实验内码]"};
		WritableWorkbook workbook=null;
		try {
			workbook = Workbook.createWorkbook(os);
			workbook.setProtected(true);
			WritableSheet sheet = workbook.createSheet(aty.getTitle() + "_相关编码", 0);
			for(int i=0;i<heads.length;i++){
				sheet.addCell(new Label(i,0,heads[i]));
				sheet.setColumnView(i, 25);  // 设置列宽
			}
			for(int i=0;i<list.size();i++){
				sheet.addCell(new Label(1, i+1, list.get(i).getPublicCode()));
				sheet.addCell(new Label(0, i+1, aty.getAtyCode()));
				sheet.addCell(new Label(2, i+1, list.get(i).getPrivateCode()));
				sheet.addCell(new Label(3, i+1, list.get(i).getInsideCodeTmp()));
			}
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(workbook!=null)
				workbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(os!=null)
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 商品追踪
	 * @Title:			wesConfig
	 * @Description:	商品追踪
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wares/trace", method = RequestMethod.GET)
	public ModelAndView wesTrace(HttpServletRequest request, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
		ModelAndView mv = new ModelAndView("wares/trace");
		
		Page<WaresDto> page = new Page<WaresDto>();
		page.setPage(pageNumber);
		
		String publicCode = Tool.nvl(request.getParameter("publicCode"));
		String startDate = Tool.nvl(request.getParameter("startDate"));
		String endDate = Tool.nvl(request.getParameter("endDate"));
		String code = Tool.nvl(request.getParameter("code")); // 编码查询
		
		Map<String, String> paramMap = new HashMap<String, String>();
		StringBuffer conditionSql = new StringBuffer();
		if (Tool.isNotNullOrEmpty(publicCode)) {
			paramMap.put("publicCode", publicCode);
			conditionSql.append(" and w.publicCode = '").append(publicCode).append("' ");
		}
		
		if (Tool.isNotNullOrEmpty(startDate)) {
			paramMap.put("startDate", startDate);
			conditionSql.append(" and w.createTime >= '").append(startDate).append("' ");
		}
		
		if (Tool.isNotNullOrEmpty(endDate)) {
			paramMap.put("endDate", endDate);
			conditionSql.append(" and w.createTime <= '").append(endDate).append("' ");
		}
		
		if (Tool.isNotNullOrEmpty(code)) {
			paramMap.put("code", code);
			conditionSql.append(" and (w.publicCode like '%").append(code)
				.append("%' or w.privateCode like '%").append(code).append("%') ");
		}
		
		page = waresService.searchListByCondition(page, conditionSql.toString());
		
		mv.addObject("searchParams", Tool.doneQueryParam(paramMap));
		mv.addObject("page", page);
		mv.addObject("list", page.getContent());
		
		/**
		 * 获取活动列表
		 */
		List<Activity> atyList = new ArrayList<Activity>();
		
		atyList = activityService.getActivityList("");
		mv.addObject("atyList", atyList);
		
		mv.addObject("publicCode", publicCode);
		mv.addObject("startDate", startDate);
		mv.addObject("endDate", endDate);
		mv.addObject("code", code);
		
		return mv;
	}
	
	/**
	 * 商品追踪【详情】
	 * @Title:			wesConfig
	 * @Description:	商品追踪【详情】
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wes/traceDetail/{id}", method = RequestMethod.GET)
	public String wesTraceDetail(Model model, @PathVariable("id") String id) {
	
		List<ScanRecord> srList = scanRecordService.findByCondition(" and t.waresId = '" + id + "' order by scanTime desc");
		model.addAttribute("srs", srList);
		
		return "wares/traceDetail";
	}
}
