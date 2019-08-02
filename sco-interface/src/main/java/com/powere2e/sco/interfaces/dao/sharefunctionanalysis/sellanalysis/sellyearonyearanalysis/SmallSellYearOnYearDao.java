package com.powere2e.sco.interfaces.dao.sharefunctionanalysis.sellanalysis.sellyearonyearanalysis;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.Dao;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.sco.model.sharefunctionanalysis.sellanalysis.sellyearonyearanalysis.SellYearOnYear;
import com.powere2e.sco.model.sharefunctionanalysis.sellanalysis.sellyearonyearanalysis.SellYearOnYearAnalysis;

/**
 * 小分类销售同比Dao接口
 * 
 * @author Joyce.li
 * @since 2015年6月8日 下午5:45:29
 * @version 1.0
 */
public interface SmallSellYearOnYearDao extends Dao {

	/**
	 * 查询小分类信息
	 * 
	 * @param map
	 * @param pageInfo
	 * @return
	 */
	public List<SellYearOnYear> listSmallTypeInfo(Map<String, Object> map, PageInfo pageInfo);

	/**
	 * 查询小分类销售同比
	 * 
	 * @param map
	 * @param pageInfo
	 * @return
	 */
	public List<SellYearOnYearAnalysis> querySmallSellYearOnYear(Map<String, Object> map, PageInfo pageInfo);

	/**
	 * 查询小分类销售同比总计
	 * 
	 * @param map
	 * @param pageInfo
	 * @return
	 */
	public List<SellYearOnYearAnalysis> querySmallSellSum(Map<String, Object> map, PageInfo pageInfo);
}