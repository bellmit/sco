package com.powere2e.sco.interfaces.service.accessoryintention;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.frame.commons.service.Service;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryAccessory;
/**
 * 询价单原材料Service接口
 * @author gavin.xu
 * @version 1.0
 * @since 2015年3月27日
 */
public interface AccessoryEnquiryAccessoryService extends Service {
	/**
	 * 询价单原材料查询
	 
	 * @param map 
	 *				查询参数
	 * @param pageInfo 
	 *				分页参数
	 * @return 返回询价单原材料列表
	 */
	public List<AccessoryEnquiryAccessory> listAccessoryEnquiryAccessory(Map<String, Object> map,PageInfo pageInfo);
	/**
	 * 根据ID号加载一个询价单原材料
	 *
	 * @param map
	 *				
	 * @return
	 */
	public AccessoryEnquiryAccessory loadAccessoryEnquiryAccessory(String accessoryCode);
	/**
	 * 添加询价单原材料
	 *
	 * @param map
	 *				
	 */
	public void insertAccessoryEnquiryAccessory(AccessoryEnquiryAccessory accessoryEnquiryAccessory);
	/**
	 * 删除询价单原材料
	 *
	 * @param map 
	 *				必须参数id为要删除的询价单原材料id号，可以为多个id,用逗号隔开，如'1','2'
	 */
	public void deleteAccessoryEnquiryAccessory(String enquiryCode);
	/**
	 * 修改询价单原材料
	 *
	 * @param map 
	 *				必须参数id为要修改询价单原材料的id号，不能为数组
	 */
	public void updateAccessoryEnquiryAccessory(AccessoryEnquiryAccessory accessoryEnquiryAccessory);
}