package com.powere2e.sco.interfaces.service.accessoryintentioncostanalysis.historicalquotesupplier;


import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.frame.commons.service.Service;
import com.powere2e.sco.model.accessoryintentioncostanalysis.historicalquotesupplier.HistoricalQuoteSupplier;
import com.powere2e.sco.model.accessoryintentioncostanalysis.historicalquotesupplier.HistoricalQuoteSupplierForm;
import com.powere2e.sco.model.accessoryintentioncostanalysis.totalcostanalysis.QuotedDetailForm;
import com.powere2e.sco.model.accessoryintentioncostanalysis.totalcostanalysis.QuotedForm;
import com.powere2e.sco.model.accessoryoaapplication.ApplicationReportAccessory;
import com.powere2e.sco.model.accessoryoaapplication.SubscribeAccessory;
import com.powere2e.sco.model.accessoryoaapplication.WlInfo;
/**
 * 总成本分析Service接口
 * @author gavin.xu
 * @version 1.0
 * @since 2015年4月20日
 */
public interface HistoricalQuoteSupplierService extends Service {
	/**
	 * 保存总成本类比结果
	 * 
	 * @param fileName
	 *            保存的文件名
	 * @param paraMap
	 *            查询条件
	 * @return 消息
	 */
	public String saveSearchDataForm(String fileName, Map<String, Object> paraMap);
	/**
	 * 采购委员会竞价单OA申请意向品列表
	 */
	public List<HistoricalQuoteSupplier> listHistoricalQuoteSupplierIntentionApplication(Map<String, Object> map, PageInfo pageInfo);
	
	public List<HistoricalQuoteSupplierForm> listHistoricalQuoteSupplierForm(Map<String, Object> map, PageInfo pageInfo);
	/**
	 * 采购委员会竞价单OA申请意向品列表
	 */
	public List<QuotedForm> listQuotedForm(Map<String, Object> map, PageInfo pageInfo);
	
	/**
	 * 采购委员会竞价单OA申请意向品列表
	 */
	public List<QuotedDetailForm> listQuotedDetailForm(Map<String, Object> map, PageInfo pageInfo);
	/**
	 * 根据报价单编号
	 */
	public HistoricalQuoteSupplier loadHistoricalQuoteSupplierIntentionApplication(String quotedCode);
	/**
	 * 申请报告
	 * 
	 */
	public void insertApplicationReportAccessoryAndSubscribeAccessory(ApplicationReportAccessory applicationReportAccessory,List<SubscribeAccessory> subscribeAccessoryList) ;
	
	/**
	 * 是否可以操作，可以的话插入初始化数据
	 * @param quotedCodes 报价单编号（可多个）
	 * @param applicationCode 申请单号
	 * @param applicationType 申请类型
	 * @return true:可以操作; false:不可操作
	 */
	public Boolean committeeInsertUpdateDeleteIsOk(String quotedCodes,String applicationCode,String applicationType) ;
	
	/**
	 * 初始化插入
	 * @param quotedCodes 报价单编号（可多个）
	 * @param applicationCode 申请单号
	 * @param applicationType 申请类型
	 * @return true:可以操作; false:不可操作
	 */
	
	public void insertApplicationQuotedAndOaApplication(String quotedCodes,String applicationCode,String applicationType) ;
	/**
	 * 批量删除大货信息
	 * 
	 */
	public void deleteDhInfo(String[] dhinfoId) ;
	/**
	 * 批量删除申请单
	 * 
	 */
	public void deleteApplication(String [] applicationCodes) ;
	/**
	 * 批量关闭申请单
	 * 
	 */
	public void closeApplication(String [] applicationCodes) ;
	/**
	 * 取消OA同步
	 * 
	 */
	public void undoOaSynchronous(String [] applicationCodes) ;
	/**
	 * 允许OA同步
	 * 
	 */
	public void allowOaSynchronous(String  applicationCode) ;
	/**
	 * 检查申请单必填项
	 * 
	 */
	public String  checkApplication(String  applicationCode) ;
	/**
	 * 保存物料信息
	 * 
	 */
	public void  insertWlInfo(WlInfo[]  dataArray) ;
	
	/**
	 * 导出excel
	 * 
	 * @param map
	 * 
	 */
	public void exportExcel(List<HistoricalQuoteSupplierForm> historicalQuoteSupplierFormListTotal, ServletOutputStream out);
}