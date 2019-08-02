package com.powere2e.sco.model.merchandiseoaapplication.reportoldupoaapplication.reportoldup;

import com.powere2e.frame.server.model.AppModel;

/**
 * 同类商品市场零售价(老品新上)实体类
 * 
 * @author Joyce.Li
 * @version 1.0
 * @since 2015年5月5日
 */
public class SameMerchandiseOldup extends AppModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7239601782339810642L;
	private String reportCode;//
	private String reportType;//
	private String trademark;//
	private String merchandiseName;//
	private String sellChannel;//
	private String sellUnits;//
	private String sellPrice;//
	private String kgPrice;//
	private String remarks;
	
	// 获取
	public String getReportCode() {
		return reportCode;
	}

	// 设置
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}

	// 获取
	public String getReportType() {
		return reportType;
	}

	// 设置
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	// 获取
	public String getTrademark() {
		return trademark;
	}

	// 设置
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	// 获取
	public String getMerchandiseName() {
		return merchandiseName;
	}

	// 设置
	public void setMerchandiseName(String merchandiseName) {
		this.merchandiseName = merchandiseName;
	}

	// 获取
	public String getSellChannel() {
		return sellChannel;
	}

	// 设置
	public void setSellChannel(String sellChannel) {
		this.sellChannel = sellChannel;
	}

	// 获取
	public String getSellUnits() {
		return sellUnits;
	}

	// 设置
	public void setSellUnits(String sellUnits) {
		this.sellUnits = sellUnits;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	// 获取
	public String getKgPrice() {
		return kgPrice;
	}

	// 设置
	public void setKgPrice(String kgPrice) {
		this.kgPrice = kgPrice;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}