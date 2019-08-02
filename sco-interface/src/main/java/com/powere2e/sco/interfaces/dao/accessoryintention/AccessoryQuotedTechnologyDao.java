package com.powere2e.sco.interfaces.dao.accessoryintention;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.Dao;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedTechnology;
/**
 * 辅料报价-工艺信息DAO接口
 * @author gavin.xu
 * @version 1.0
 * @since 2015年4月8日
 */
public interface AccessoryQuotedTechnologyDao extends Dao {
	/**
	 * 辅料报价-工艺信息查询
	 *
	 * @param map 
	 *				查询参数
	 * @param pageInfo 
	 *				分页参数
	 * @return 返回辅料报价-工艺信息列表
	 */
	public List<AccessoryQuotedTechnology> listAccessoryQuotedTechnology(Map<String, Object> map,PageInfo pageInfo);
	/**
	 * 根据ID号加载一个辅料报价-工艺信息
	 *
	 * @param map
	 *				
	 * @return
	 */
	public AccessoryQuotedTechnology loadAccessoryQuotedTechnology(Map<String,Object> map);
	/**
	 * 添加辅料报价-工艺信息
	 *
	 * @param map
	 *				
	 */
	public void insertAccessoryQuotedTechnology(Map<String, Object> map);
	/**
	 * 删除辅料报价-工艺信息
	 *
	 * @param map 
	 *				必须参数id为要删除的辅料报价-工艺信息id号，可以为多个id,用逗号隔开，如'1','2'
	 */
	public void deleteAccessoryQuotedTechnology(Map<String, Object> map);
	/**
	 * 修改辅料报价-工艺信息
	 *
	 * @param map 
	 *				必须参数id为要修改辅料报价-工艺信息的id号，不能为数组
	 */
	public void updateAccessoryQuotedTechnology(Map<String, Object> map);
}