package com.powere2e.sco.interfaces.service.accessoryoaapplication.compareApply.applicationschedule;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.frame.commons.service.Service;
import com.powere2e.sco.model.accessoryoaapplication.applicatonSchedule.AccessoryApplicationSchedulea;

/**
 * 辅料询价单比较 进度信息 Service接口
 * 
 * @author Gavillen.Zhou
 * @version 1.0
 * @since 2015年5月19日
 */
public interface CompareApplicationScheduleService extends Service {

	/**
	 * 进度信息列表
	 * 
	 * @param map
	 *            查询条件
	 * @param pageInfo
	 *            分页参数
	 * @return 进度信息list
	 */
	public List<AccessoryApplicationSchedulea> listApplicationScheduleCompare(Map<String, Object> map,
			PageInfo pageInfo);

	/**
	 * 保存进度信息
	 * 
	 * @param map
	 *            相关参数
	 * @param dataArray
	 *            进度实例
	 */
	public void insertApplicationScheduleCompare(
			Map<String, Object> map, AccessoryApplicationSchedulea[] dataArray);
}