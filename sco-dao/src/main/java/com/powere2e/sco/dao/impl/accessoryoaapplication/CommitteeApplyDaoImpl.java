package com.powere2e.sco.dao.impl.accessoryoaapplication;

import java.util.List;
import java.util.Map;

import com.powere2e.frame.commons.dao.DaoImpl;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.sco.interfaces.dao.accessoryoaapplication.CommitteeApplyDao;
import com.powere2e.sco.model.accessoryoaapplication.CommitteeApply;

/**
 * 采购委员会OA申请DAO接口的实现
 * 
 * @author gavin.xu
 * @version 1.0
 * @since 2015年4月20日
 */
public class CommitteeApplyDaoImpl extends DaoImpl implements CommitteeApplyDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2191963660799254704L;

	// 新品引进OA申请意向品列表
	@Override
	public List<CommitteeApply> listCommitteeApplyIntentionApplication(Map<String, Object> map, PageInfo pageInfo) {
		return this.query(CommitteeApplyDao.class, "listCommitteeApplyIntentionApplication", map, pageInfo);
	}

	// 根据报价单编号获得采购委员会竞价单OA申请意向品
	@Override
	public CommitteeApply loadCommitteeApplyIntentionApplication(Map<String, Object> map) {
		return (CommitteeApply)this.get(CommitteeApplyDao.class, "loadCommitteeApplyIntentionApplication", map);
	}

	@Override
	public void updateIntentionSupplierAccessory(Map<String, Object> map) {
		this.update(CommitteeApplyDao.class, "updateIntentionSupplierAccessory", map);
		
	}
}