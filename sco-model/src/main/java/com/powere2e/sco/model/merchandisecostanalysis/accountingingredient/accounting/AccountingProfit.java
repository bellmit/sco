package com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting;

import com.powere2e.frame.server.model.AppModel;

/**
 * 核算表-利润实体类
 * 
 * @author matt.sun
 * @version 1.0
 * @since 2015年4月28日
 */
public class AccountingProfit extends AppModel {

	private static final long serialVersionUID = 6766470289729793090L;
	private String accountingCode;// 核算表编号
	private String remarks;// 备注

	public String getAccountingCode() {
		return accountingCode;
	}

	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}