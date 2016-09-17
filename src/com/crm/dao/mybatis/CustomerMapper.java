/**
 * 
 */
package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Contact;
import com.crm.domain.Customer;
import com.crm.domain.Visitlog;
import com.crm.domain.easyui.PageHelper;

/**
 * @author zh
 * 2014-8-2
 */
public interface CustomerMapper {

	//根据名称查询
	public Customer findByName(@Param("customername") String customername);

	//根据id查询
	public Customer getCustomerById(@Param("id") int id);

	//根据 客户id获取其所有联系人
	public List<Contact> getContactById(@Param("customerId") int customerId);
	
	//根据 客户id获取其所有拜访记录
	public List<Visitlog> getVisitlogById(@Param("customerId") int customerId);
	
	//获取所有
	public List<Customer> getDatagrid();

	//获取总数
	public Integer getDatagridTotal(Customer customer);

	//分页
	public List<Customer> datagridCustomer(PageHelper page);

	//添加
	public void addCustomer(Customer customer);

	//修改
	public void editCustomer(Customer customer);
	
	//删除
	public void deleteCustomer(int id);

}
