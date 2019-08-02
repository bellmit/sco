package com.powere2e.sco.interfaces.dao.datamaintenance.purchaserdata.relevancematerialandwebsite;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.Dao;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.sco.model.datamaintenance.purchaserdata.relevancematerialandwebsite.RelevanceMaterialAndWebsite;

/**
 * 关联商品原料与公示网站原料 DAO接口
 * 
 * @author Gavillen.Zhou
 * @version 1.0
 * @since 2015年4月13日
 */
public interface RelevanceMaterialAndWebsiteDao extends Dao {
	/**
	 * 查询
	 * 
	 * @param map
	 *            查询参数
	 * @param pageInfo
	 *            分页参数
	 * @return 返回列表
	 */
	public List<RelevanceMaterialAndWebsite> listRelevanceMaterialAndWebsite(
			Map<String, Object> map, PageInfo pageInfo);

	/**
	 * 新增关联关系
	 * 
	 * @param map 
	 * 
	 */
	public void insertRelevanceMaterialAndWebsite(Map<String, Object> map);

	/**
	 * 取消关联
	 * 
	 * @param map
	 *            必须参数id为要删除的id号，可以为多个id,用逗号隔开，如'1','2'
	 */
	public void deleteRelevanceMaterialAndWebsite(Map<String, Object> map);

	/**
	 * 验证是否存在重复数据
	 * 
	 * @param map
	 * 		:
	 *            [releMaterialAndWeb 关联实例]
	 */
	public String ifExistsReleMateAndWeb(Map<String, Object> map);

	/**
	 * 暂不关联公示网站
	 * 
	 * @param map
	 *            : [list 投料编号、原料编号]
	 */
	public void completeNotLinkMaterialAndWebsite(Map<String, Object> map);
	
}