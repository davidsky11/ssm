package com.crm.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crm.domain.Wares;
import com.crm.service.WaresService;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/** 
 * @ClassName	WaresExcelService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月17日 上午3:40:48
 * @Version 	V1.0    
 */
@Service("waresExcelService")
public class WaresExcelService {

	@Resource
	private WaresService waresService;
	
	public void saveToExcel(String path, String conditionSql) throws FileNotFoundException{
		List<Wares> waresList = waresService.getDatagrid(conditionSql);
		File file = new File(path);
		createExcel(new FileOutputStream(file), waresList);
	}
	
	private void createExcel(OutputStream os, List<Wares> list){
		String[] heads={"publicCode","privateCode","insideCode"};
		WritableWorkbook workbook=null;
		try {
			workbook = Workbook.createWorkbook(os);
		WritableSheet sheet = workbook.createSheet("wares sheet1", 0);
		for(int i=0;i<heads.length;i++){
			sheet.addCell(new Label(i,0,heads[i]));
		}
		for(int i=0;i<list.size();i++){
			sheet.addCell(new Label(0, i+1, list.get(i).getPublicCode()));
			sheet.addCell(new Label(1, i+1, list.get(i).getPrivateCode()));
			sheet.addCell(new Label(2, i+1, list.get(i).getInsideCode()));
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
	
}
 