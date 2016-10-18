package com.crm.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.crm.dao.mybatis.ActivityMapper;
import com.crm.dao.mybatis.SaleMapper;
import com.crm.dao.mybatis.ScanRecordMapper;
import com.crm.domain.Activity;
import com.crm.domain.Sale;
import com.crm.domain.dto.SrDto;
import com.crm.util.Tool;

/**
 * 每月1号定时计算上月的销售记录。@DisallowConcurrentExecution 保证多个任务间不会同时执行.所以在多任务执行时最好加上
 */
@DisallowConcurrentExecution
public class SaleRecordJob implements Job {

    private ScanRecordMapper scanRecordMapper;
    private SaleMapper saleMapper;
    private ActivityMapper activityMapper;

    /**
     * 这里实现定时任务的方法
     */
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        ApplicationContext appCtx = null;
        try {
            appCtx = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
        } catch (SchedulerException e1) {
            e1.printStackTrace();
        }
        if (appCtx != null) {
        	scanRecordMapper = appCtx.getBean(ScanRecordMapper.class);
        	saleMapper = appCtx.getBean(SaleMapper.class);
        	activityMapper = appCtx.getBean(ActivityMapper.class);
        }
        
        resetScanRecord2Sale();
    }
    
    private void resetScanRecord2Sale() {
    	String firstDay;
    	String lastDay;
    	
    	Integer year = 2016;
    	Integer month = 01;
    	 
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

    	if (scanRecordMapper != null) {
	    	// 获取前月的第一天
	    	Calendar cal_1=Calendar.getInstance();//获取当前日期 
	    	
	    	year = cal_1.get(Calendar.YEAR);  // 当年
	    	month = cal_1.get(Calendar.MONTH);  // 上个月
	    	
	    	cal_1.add(Calendar.MONTH, -1);
	    	cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    	firstDay = format.format(cal_1.getTime());
	    	System.out.println("-----1------firstDay:"+firstDay);
	    	
	    	//获取前月的最后一天
	    	Calendar cale = Calendar.getInstance();   
	    	cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
	    	lastDay = format.format(cale.getTime());
	    	System.out.println("-----2------lastDay:"+lastDay);
	    	
	    	StringBuffer conditionSql = new StringBuffer();
	    	conditionSql.append(" and t.userType = '3'");
	    	
	    	conditionSql.append(" and t.scanTime >= '").append(firstDay).append("'");
	    	conditionSql.append(" and t.scanTime <= date_sub('").append(lastDay).append("', interval -1 day)");
			
	    	List<SrDto> srList = scanRecordMapper.findOnlyByUserAndWares(conditionSql.toString());
	    	
	    	/**
	    	 * 获取所有的用户id
	    	 */
	    	Set<String> userIdSet = new HashSet<String>();
	    	for (SrDto sd : srList) {
	    		userIdSet.add(sd.getUserId());
	    	}
	    	
	    	/**
	    	 * 获取所有的活动列表
	    	 */
	    	List<Activity> atyList = activityMapper.getActivityList("");
	    	
	    	/**
	    	 * 生成Sale列表
	    	 */
	    	List<Sale> saleList = new ArrayList<Sale>();
	    	
	    	for (String userId : userIdSet) {
	    		for (Activity aty : atyList) {
	    			Sale sale = new Sale();
		    		
		    		sale.setUserId(userId);
		    		sale.setYear(year);
		    		sale.setMonth(month);
		    		
		    		Integer amount = 0;
	    		
		    		for (SrDto dto : srList) {
		    			if (dto.getPublicCode().equals(aty.getPublicCode()) && dto.getUserId().equals(userId)) {
		    				sale.setActivityId(aty.getId());
		    				amount += dto.getCount();
			    		}
		    		}
		    		
		    		sale.setAmount(amount + 0.0d);
		    		
		    		if (Tool.isNotNullOrEmpty(sale.getActivityId()))	saleList.add(sale);
	    		}
	    	}
	    	
	    	/**
	    	 * 将saleList数据保存到sale数据表中
	    	 */
	    	saleMapper.addSaleBatch(saleList);
    	}
    }
    
}
