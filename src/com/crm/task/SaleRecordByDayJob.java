package com.crm.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * 每天凌晨1点定时计算昨天的销售记录。@DisallowConcurrentExecution 保证多个任务间不会同时执行.所以在多任务执行时最好加上
 */
@DisallowConcurrentExecution
public class SaleRecordByDayJob implements Job {

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
        System.out.println("insert");
    }
    
    private void resetScanRecord2Sale() {
    	String startDay;
    	String endDay;
    	
    	int year = 2017;
    	int month = 01;
    	int day = 01;
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

    	if (scanRecordMapper != null) {
    		Date date = new Date();
    		
	    	// 获取前月的第一天
	    	Calendar cal_1=Calendar.getInstance();//获取当前日期 
	    	cal_1.setTime(date);
	    	cal_1.add(Calendar.DATE, -1);
	    	
	    	year = cal_1.get(Calendar.YEAR);
	    	month = cal_1.get(Calendar.MONTH) + 1;
	    	day = cal_1.get(Calendar.DAY_OF_MONTH);
	    	
	    	startDay = format.format(cal_1.getTime());
	    	endDay = format.format(date);
	    	
	    	StringBuffer conditionSql = new StringBuffer();
	    	conditionSql.append(" and t.userType = '3'");
	    	
	    	conditionSql.append(" and t.scanTime >= '").append(startDay).append("'");
	    	conditionSql.append(" and t.scanTime < '").append(endDay).append("'");
	    	
	    	//conditionSql.append(" and t.scanTime <= date_sub('").append(lastDay).append("', interval -1 day)");
			
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
		    		sale.setDay(day);
		    		
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
