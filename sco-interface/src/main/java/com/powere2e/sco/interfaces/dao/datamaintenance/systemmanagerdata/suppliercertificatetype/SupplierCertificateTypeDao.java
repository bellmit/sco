package com.powere2e.sco.interfaces.dao.datamaintenance.systemmanagerdata.suppliercertificatetype;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.Dao;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.sco.model.datamaintenance.systemmanagerdata.suppliercertificatetype.SupplierCertificateType;
/**
 * 证件名称DAO接口
 * @author xiejiajie.w
 * @version 1.0
 * @since 2015年4月17日
 */
public interface SupplierCertificateTypeDao extends Dao {
	/**
	 * 证件名称查询
	 *
	 * @param map 
	 *				查询参数
	 * @param pageInfo 
	 *				分页参数
	 * @return 返回证件名称列表
	 */
	public List<SupplierCertificateType> listSupplierCertificateType(Map<String, Object> map,PageInfo pageInfo);
	/**
	 * 根据ID号加载一个证件名称
	 *
	 * @param map
	 *				
	 * @return
	 */
	public SupplierCertificateType loadSupplierCertificateType(Map<String,Object> map);
	/**
	 * 添加证件名称
	 *
	 * @param map
	 *				
	 */
	public void insertSupplierCertificateType(Map<String, Object> map);
	/**
	 * 删除证件名称
	 *
	 * @param map 
	 *				必须参数id为要删除的证件名称id号，可以为多个id,用逗号隔开，如'1','2'
	 */
	public void deleteSupplierCertificateType(Map<String, Object> map);
	/**
	 * 查询供应商中存在该证件编号的数量
	 * @param map
	 * @return
	 */
	public Integer serachSupplierCertificate(Map<String, Object> map);
}