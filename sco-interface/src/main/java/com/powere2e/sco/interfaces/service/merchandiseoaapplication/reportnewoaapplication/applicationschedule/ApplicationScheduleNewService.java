package com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.applicationschedule;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.frame.commons.service.Service;
import com.powere2e.sco.model.merchandiseoaapplication.reportnewoaapplication.applicatonSchedule.ApplicationScheduleaNew;

/**
 * 新品引进 进度信息 Service接口
 * 
 * @author Gavillen.Zhou
 * @version 1.0
 * @since 2015年05月2日
 */
public interface ApplicationScheduleNewService extends Service {

	/**
	 * 进度信息列表
	 * 
	 * @param map
	 *            查询条件
	 * @param pageInfo
	 *            分页参数
	 * @return 进度信息list
	 */
	public List<ApplicationScheduleaNew> listApplicationScheduleNew(Map<String, Object> map,
			PageInfo pageInfo);

	/**
	 * 保存进度信息
	 * 
	 * @param map
	 *            相关参数
	 * @param dataArray
	 *            进度实例
	 */
	public void insertApplicationScheduleNew(
			Map<String, Object> map, ApplicationScheduleaNew[] dataArray);
}