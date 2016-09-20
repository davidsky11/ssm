package com.crm.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.crm.domain.system.SysDictionary;
import com.crm.service.SysDictionaryService;
import com.crm.util.tree.TreeParser;

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
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@RequestMapping("/main")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView("dic/main");
		
		List<SysDictionary> dicList = sysDictionaryService.getDicList("");
		mv.addObject("dicTreeData", TreeParser.list2Tree(dicList));
		
		return mv;
	}
	
	@RequestMapping(value = "/dicTreeData")
    public ModelAndView dicTreeData() throws Exception {
        ModelAndView mv = new ModelAndView("json");

        /*List list = new ArrayList();
        String id = Tool.nvl(request.getParameter("id"));
        String querName = Tool.nvl(request.getParameter("queryname"));
         
        SysDictionary key = new SysDictionary();
        
    	list = sysDictionaryService.findDictionaryTree(key);
    	mv.addObject("json",TreeParser.list2Tree(list));*/
        	
        return mv;
    }
	
	/**
	 * @Title:			addAty
	 * @Description:	添加厂商
	 * @param model
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addAty(Model model, SysDictionary dic, @RequestParam("tmp") String tmp){
		try {
			
			sysDictionaryService.saveDic(dic);
			
		} catch (Exception e) {
			
		}
		
        return "";	
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deleteAtys(Model model, @RequestParam("id") Integer id){
		
		sysDictionaryService.deleDic(id);
		
		return "";	
	}
	
}
 