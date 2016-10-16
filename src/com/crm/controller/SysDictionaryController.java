package com.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crm.domain.system.SysDictionary;
import com.crm.service.SysDictionaryService;
import com.crm.util.GsonUtils;
import com.crm.util.Tool;
import com.crm.util.common.Const;
import com.crm.util.tree.Tree;
import com.crm.util.tree.TreeParser;
import com.crm.util.tree.TreeUtils;

import com.crm.domain.User;

/** 
 * @ClassName	SysDictionaryController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月20日 上午1:10:37
 * @Version 	V1.0    
 */
@Controller
@RequestMapping(value = "/dic")
public class SysDictionaryController {

	private final Logger log = LoggerFactory.getLogger(SysDictionaryController.class);
	
	@Resource
	private SysDictionaryService sysDictionaryService;
	
	@RequestMapping("/main")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView("dic/main");
		
		List<SysDictionary> dicList = sysDictionaryService.getDicList("");
		mv.addObject("dicTreeData", TreeParser.list2Tree(dicList));
		
		return mv;
	}
	
	@RequestMapping(value = "/list")
    public String dicTreeData(Model model) {
		/*List<SysDictionary> dicList = sysDictionaryService.getDicList("");
		model.addAttribute("dicList", dicList);*/
		return "dic/list";
    }
	
	@ResponseBody
	@RequestMapping(value = "/json/all", method=RequestMethod.GET)
    public List<Tree> getAll(HttpServletResponse response) {
		List<SysDictionary> dicList = sysDictionaryService.getDicList("");
		List<Tree> tree = TreeUtils.fomatDicToTree(dicList);
		
		/*response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(GsonUtils.toJson(tree));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}*/
		
		return tree;
    }
	
	/**
	 * 新增字典
	 * @Title:			addDic
	 * @Description:	新增字典
	 * @param model
	 * @param resource
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addDic(Model model, SysDictionary dic, HttpSession session){
		sysDictionaryService.saveDic(dic);
		
		return "dic/list";	
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deleteDic(Model model,@RequestParam("id") Integer id){
		sysDictionaryService.deleDic(id);
		
		return "dic/list";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)	
	public String updateRole(Model model, SysDictionary dic){
		sysDictionaryService.updateDic(dic);
		return "dic/list";
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String prepareUpdateRole(Model model,@PathVariable("id") Integer id){
		model.addAttribute("dic", sysDictionaryService.findById(id));
		return "dic/edit";
	}

	@RequestMapping(value="/detail/{id}",method=RequestMethod.GET)
	public String findResource(Model model,@PathVariable("id") Integer id){
		model.addAttribute("dic", sysDictionaryService.findById(id));
		return "dic/detail";
	}
	
}
 