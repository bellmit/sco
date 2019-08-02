package com.powere2e.sco.interfaces.service.accessoryintention;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.frame.commons.service.Service;
import com.powere2e.sco.model.accessoryintention.AccessoryProofing;
/**
 * 辅料打样Service接口
 * @author gavin.xu
 * @version 1.0
 * @since 2015年4月8日
 */
public interface AccessoryProofingService extends Service {
	/**
	 * 辅料打样查询
	 
	 * @param map 
	 *				查询参数
	 * @param pageInfo 
	 *				分页参数
	 * @return 返回辅料打样列表
	 */
	public List<AccessoryProofing> listAccessoryProofing(Map<String, Object> map,PageInfo pageInfo);
	/**
	 * 根据ID号加载一个辅料打样
	 *
	 * @param map
	 *				
	 * @return
	 */
	public AccessoryProofing loadAccessoryProofing(String proofingCode);
	/**
	 * 添加辅料打样
	 *
	 * @param map
	 *				
	 */
	public void insertAccessoryProofing(AccessoryProofing accessoryProofing);
	/**
	 * 删除辅料打样
	 *
	 * @param map 
	 *				必须参数id为要删除的辅料打样id号，可以为多个id,用逗号隔开，如'1','2'
	 */
	public void deleteAccessoryProofing(String proofingCode);
	/**
	 * 修改辅料打样
	 *
	 * @param map 
	 *				必须参数id为要修改辅料打样的id号，不能为数组
	 */
	public void updateAccessoryProofing(AccessoryProofing accessoryProofing);
}