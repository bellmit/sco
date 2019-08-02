package com.powere2e.sco.interfaces.dao.accessoryintention;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.Dao;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedPacking;
/**
 * 辅料报价-内外包装信息DAO接口
 * @author gavin.xu
 * @version 1.0
 * @since 2015年4月8日
 */
public interface AccessoryQuotedPackingDao extends Dao {
	/**
	 * 辅料报价-内外包装信息查询
	 *
	 * @param map 
	 *				查询参数
	 * @param pageInfo 
	 *				分页参数
	 * @return 返回辅料报价-内外包装信息列表
	 */
	public List<AccessoryQuotedPacking> listAccessoryQuotedPacking(Map<String, Object> map,PageInfo pageInfo);
	/**
	 * 根据ID号加载一个辅料报价-内外包装信息
	 *
	 * @param map
	 *				
	 * @return
	 */
	public AccessoryQuotedPacking loadAccessoryQuotedPacking(Map<String,Object> map);
	/**
	 * 添加辅料报价-内外包装信息
	 *
	 * @param map
	 *				
	 */
	public void insertAccessoryQuotedPacking(Map<String, Object> map);
	/**
	 * 删除辅料报价-内外包装信息
	 *
	 * @param map 
	 *				必须参数id为要删除的辅料报价-内外包装信息id号，可以为多个id,用逗号隔开，如'1','2'
	 */
	public void deleteAccessoryQuotedPacking(Map<String, Object> map);
	/**
	 * 修改辅料报价-内外包装信息
	 *
	 * @param map 
	 *				必须参数id为要修改辅料报价-内外包装信息的id号，不能为数组
	 */
	public void updateAccessoryQuotedPacking(Map<String, Object> map);
}