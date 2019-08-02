package com.powere2e.sco.interfaces.dao.peripheral.webservice;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.Dao;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.sco.model.peripheral.webservice.ApplicationPackageDesignM;
/**
 * 商品包装设计申请单(商品)DAO接口
 * @author gavin.xu
 * @version 1.0
 * @since 2015年10月21日
 */
public interface ApplicationPackageDesignMDao extends Dao {
	/**
	 * 商品包装设计申请单(商品)查询
	 *
	 * @param map 
	 *				查询参数
	 * @param pageInfo 
	 *				分页参数
	 * @return 返回商品包装设计申请单(商品)列表
	 */
	public List<ApplicationPackageDesignM> listApplicationPackageDesignM(Map<String, Object> map,PageInfo pageInfo);
	/**
	 * 根据ID号加载一个商品包装设计申请单(商品)
	 *
	 * @param map
	 *				
	 * @return
	 */
	public ApplicationPackageDesignM loadApplicationPackageDesignM(Map<String,Object> map);
	/**
	 * 添加商品包装设计申请单(商品)
	 *
	 * @param map
	 *				
	 */
	public void insertApplicationPackageDesignM(Map<String, Object> map);
	/**
	 * 删除商品包装设计申请单(商品)
	 *
	 * @param map 
	 *				必须参数id为要删除的商品包装设计申请单(商品)id号，可以为多个id,用逗号隔开，如'1','2'
	 */
	public void deleteApplicationPackageDesignM(Map<String, Object> map);
	/**
	 * 修改商品包装设计申请单(商品)
	 *
	 * @param map 
	 *				必须参数id为要修改商品包装设计申请单(商品)的id号，不能为数组
	 */
	public void updateApplicationPackageDesignM(Map<String, Object> map);
}