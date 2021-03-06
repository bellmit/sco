package com.powere2e.sco.action.accessoryintentioncostanalysis.costanalogy;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.power.Authority;
import com.powere2e.sco.common.service.BusinessConstants;
import com.powere2e.sco.common.utils.Constant;
import com.powere2e.sco.common.utils.DateUtils;
import com.powere2e.sco.common.utils.UploadUtils;
import com.powere2e.sco.interfaces.service.accessoryintentioncostanalysis.costanalogy.CostAnalogyService;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryQuotedCount;
import com.powere2e.sco.model.accessoryintention.AccessoryIntention;
import com.powere2e.sco.model.accessoryintention.AccessoryProofing;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedAccessory;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedElectronic;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedElse;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedMaterial;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedPacking;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedTechnology;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedTotal;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedAccessoryAndSubtotal;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedAccessoryAndSubtotalList;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedElseList;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedMaterialAndSubtotal;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedMaterialAndSubtotalList;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedPackingAndSubtotal;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedPackingAndSubtotalList;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedTechnologyAndSubtotal;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.AccessoryQuotedTechnologyAndSubtotalList;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.CostAnalogy;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.CostAnalogyForm;
import com.powere2e.sco.model.accessoryintentioncostanalysis.costanalogy.SupplierForm;
import com.powere2e.sco.model.accessoryoaapplication.WlInfo;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryQuotedCountServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryIntentionServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryProofingServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryQuotedAccessoryServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryQuotedElectronicServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryQuotedElseServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryQuotedMaterialServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryQuotedPackingServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryQuotedTechnologyServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryQuotedTotalServiceImpl;
import com.powere2e.sco.service.impl.accessoryoaapplication.WlInfoServiceImpl;
import com.powere2e.sco.service.impl.reports.ReportsServiceImpl;

/**
 * ??????????????????WEB???????????????
 * 
 * @author gavin.xu
 * @version 1.0
 * @since 2015???3???16???
 */

/**
 * ?????? ????????????????????????
 * 
 * @author gavin.xu
 * @version 1.0
 * @since 2015???3???16???
 */
public class CostAnalogyAction extends UploadUtils {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 7467505029752175810L;

	private CostAnalogyService costAnalogyService;
	private List<String> remarkTextList=new ArrayList<String>();

	public List<String> getRemarkTextList() {
		return remarkTextList;
	}

	public void setRemarkTextList(List<String> remarkTextList) {
		this.remarkTextList = remarkTextList;
	}

	/**
	 * ???????????????????????????
	 */
	@Override
	protected void beforeBuild() {
		costAnalogyService = (CostAnalogyService) ConfigFactory.getInstance().getBean("costAnalogyService");
	}

	/**
	 * ??????????????????grid
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowCostAnalogyGrid() throws Exception {

		this.forwardPage("sco/accessoryIntentionCostAnalysis/costAnalogy/costAnalogyGrid.ftl");
	}

	/**
	 * ???????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowCostAnalogySet() throws Exception {
		String rows = this.asString("rows");
		this.putObject("rows", rows);
		this.forwardPage("sco/accessoryIntentionCostAnalysis/costAnalogy/costAnalogySetForm.ftl");
	}

	/**
	 * ?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowCostAnalogy() throws Exception {
		String rows = this.asString("rows");
		String allbjsl = this.asString("allbjsl");
		String zzxx = this.asString("zzxx");
		String ypxx = this.asString("ypxx");
		String zhfp = this.asString("zhfp");
		String txbjsl = this.asString("txbjsl");// ?????????????????????
		String quotedCodes = "";
		// ?????????????????????
		CostAnalogyForm costAnalogyForm = new CostAnalogyForm();
		List<SupplierForm> listSup = new ArrayList<SupplierForm>();
		JSONArray jsonArr = JSONArray.fromObject(rows);
		CostAnalogy[] dataArray = new CostAnalogy[jsonArr.size()];
		for (int j = 0; j < jsonArr.size(); j++) {
			dataArray[j] = (CostAnalogy) JSONObject.toBean(jsonArr.getJSONObject(j), CostAnalogy.class);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < dataArray.length; i++) {
			if (i == dataArray.length - 1) {
				quotedCodes += dataArray[i].getQuotedCode();
				AccessoryQuotedElectronic accessoryQuotedElectronic=AccessoryQuotedElectronicServiceImpl.getInstance().loadAccessoryQuotedElectronic( dataArray[i].getQuotedCode());
				dataArray[i].setEnquiryCode(accessoryQuotedElectronic.getEnquiryCode());
				dataArray[i].setIntentionCode(accessoryQuotedElectronic.getIntentionCode());
				dataArray[i].setIntentionSupplierCode(accessoryQuotedElectronic.getIntentionSupplierCode());
			} else {
				quotedCodes += dataArray[i].getQuotedCode() + ",";
				AccessoryQuotedElectronic accessoryQuotedElectronic=AccessoryQuotedElectronicServiceImpl.getInstance().loadAccessoryQuotedElectronic( dataArray[i].getQuotedCode());
				dataArray[i].setEnquiryCode(accessoryQuotedElectronic.getEnquiryCode());
				dataArray[i].setIntentionCode(accessoryQuotedElectronic.getIntentionCode());
				dataArray[i].setIntentionSupplierCode(accessoryQuotedElectronic.getIntentionSupplierCode());
			}

			map.clear();
			map.put("intentionCode", dataArray[i].getIntentionCode());
			map.put("intentionSupplierCode", dataArray[i].getSupplierCode() == null ? dataArray[i].getIntentionSupplierCode() : dataArray[i].getSupplierCode());
			map.put("quotedCode", dataArray[i].getQuotedCode());
			// ???????????????start
			List<AccessoryQuotedElectronic> accessoryQuotedElectronicList = AccessoryQuotedElectronicServiceImpl.getInstance().listAccessoryQuotedElectronic(map, null);
			AccessoryQuotedElectronic accessoryQuotedElectronic = new AccessoryQuotedElectronic();
			if (accessoryQuotedElectronicList != null && accessoryQuotedElectronicList.size() > 0) {
				accessoryQuotedElectronic = accessoryQuotedElectronicList.get(0);
				SupplierForm sup = new SupplierForm();
				sup.setQuotedCode(dataArray[i].getQuotedCode());
				sup.setIntentionSupplierCode(accessoryQuotedElectronic.getIntentionSupplierCode() == null ? accessoryQuotedElectronic.getSupplierCode() : accessoryQuotedElectronic
						.getIntentionSupplierCode());
				sup.setIntentionSupplierName(accessoryQuotedElectronic.getSupplierName() == null ? accessoryQuotedElectronic.getIntentionSupplierName() : accessoryQuotedElectronic.getSupplierName());
				AccessoryIntention intention=AccessoryIntentionServiceImpl.getInstance().loadAccessoryIntention(dataArray[i].getIntentionCode());
				if(intention!=null){
				sup.setIntentionCode(intention.getIntentionCode());
				sup.setIntentionName(intention.getIntentionName());
				sup.setTotal(new BigDecimal("0"));
				}
				if (zzxx.indexOf("???????????????-???????????????") > -1 || zzxx.indexOf("??????") > -1) {
					costAnalogyForm.setIntentionSupplierCodeShow("Y");
				}
				if (zzxx.indexOf("SAP?????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setMerchandiseCode(dataArray[i].getAccessorySapCode());
					costAnalogyForm.setMerchandiseCodeShow("Y");
				}
				sup.setQuotedDate(accessoryQuotedElectronic.getQuotedDate());
				costAnalogyForm.setQuotedDateShow("Y");
				if (zzxx.indexOf("?????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setContacts(accessoryQuotedElectronic.getContacts());
					costAnalogyForm.setContactsShow("Y");
				}
				if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setPhone(accessoryQuotedElectronic.getPhone());
					costAnalogyForm.setPhoneShow("Y");
				}
				if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setCompanySite(accessoryQuotedElectronic.getCompanySite());
					costAnalogyForm.setCompanySiteShow("Y");
				}
				
				 if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
				  sup.setFactorySite(accessoryQuotedElectronic.getFactorySite());
				  costAnalogyForm.setFactorySiteShow("Y"); }
				 
				if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setRegisterCapital(accessoryQuotedElectronic.getRegisterCapital());
					costAnalogyForm.setRegisterCapitalShow("Y");
				}
				if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setYearTurnover(accessoryQuotedElectronic.getYearTurnover());
					costAnalogyForm.setYearTurnoverShow("Y");
				}
				if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setFactoryArea(accessoryQuotedElectronic.getFactoryArea());
					costAnalogyForm.setFactoryAreaShow("Y");
				}
				if (zzxx.indexOf("?????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setStaffCount(accessoryQuotedElectronic.getStaffCount());
					costAnalogyForm.setStaffCountShow("Y");
				}
				
				if (zzxx.indexOf("???????????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setHzgpp(accessoryQuotedElectronic.getHzgpp());
					costAnalogyForm.setHzgppShow("Y");
				}

				sup.setInvoiceType(accessoryQuotedElectronic.getInvoiceType());
				if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
					costAnalogyForm.setInvoiceTypeShow("Y");
				}
				if (zzxx.indexOf("??????") > -1 || zzxx.indexOf("??????") > -1) {
				
					costAnalogyForm.setTaxRateShow("Y");
				}
				sup.setTaxRate(accessoryQuotedElectronic.getTaxRate());
				if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
					sup.setPaymentType(accessoryQuotedElectronic.getPaymentType());
					costAnalogyForm.setPaymentTypeShow("Y");
				}
				map.clear();
				if ("??????".equals(ypxx)) {
					costAnalogyForm.setProofingContentAfterShow("Y");
					costAnalogyForm.setProofingContentBeforeShow("Y");
					map.clear();
					map.put("quotedCode", dataArray[i].getQuotedCode());
					map.put("proofingType", "?????????");
					List<AccessoryProofing> accessoryProofingList = AccessoryProofingServiceImpl.getInstance().listAccessoryProofing(map, null);

					if (accessoryProofingList != null && accessoryProofingList.size() > 0) {
						AccessoryProofing accessoryProofing = accessoryProofingList.get(0);
							sup.setProofingContentBefore(accessoryProofing.getProofingContent() == null ? "" : accessoryProofing.getProofingContent());
							sup.setProofingEvaluateBefore(accessoryProofing.getProofingEvaluate() == null ? "" : accessoryProofing.getProofingEvaluate());

					} else {
						sup.setProofingContentBefore("");
						sup.setProofingEvaluateBefore("");

					}
					map.clear();
					map.put("quotedCode", dataArray[i].getQuotedCode());
					map.put("proofingType", "????????????");
					List<AccessoryProofing> accessoryProofingListAfter = AccessoryProofingServiceImpl.getInstance().listAccessoryProofing(map, null);

					if (accessoryProofingListAfter != null && accessoryProofingListAfter.size() > 0) {
						AccessoryProofing accessoryProofingAfter = accessoryProofingListAfter.get(0);

						sup.setProofingContentAfter(accessoryProofingAfter.getProofingContent() == null ? "" : accessoryProofingAfter.getProofingContent());
						sup.setProofingEvaluateAfter(accessoryProofingAfter.getProofingEvaluate() == null ? "" : accessoryProofingAfter.getProofingEvaluate());

					} else {
						sup.setProofingContentAfter("");
						sup.setProofingEvaluateAfter("");
					}
				} else {
					map.clear();
					map.put("quotedCode", dataArray[i].getQuotedCode());
					if ("?????????".equals(ypxx)){
						costAnalogyForm.setProofingContentBeforeShow("Y");
						map.put("proofingType", "?????????");
					}
					if ("????????????".equals(ypxx)){
						costAnalogyForm.setProofingContentAfterShow("Y");
						map.put("proofingType", "????????????");
					}

					List<AccessoryProofing> accessoryProofingList = AccessoryProofingServiceImpl.getInstance().listAccessoryProofing(map, null);

					if (accessoryProofingList != null && accessoryProofingList.size() > 0) {
						AccessoryProofing accessoryProofing = accessoryProofingList.get(0);
						if ("?????????".equals(ypxx)) {
							sup.setProofingContentBefore(accessoryProofing.getProofingContent() == null ? "" : accessoryProofing.getProofingContent());
							sup.setProofingEvaluateBefore(accessoryProofing.getProofingEvaluate() == null ? "" : accessoryProofing.getProofingEvaluate());
						}
						if ("????????????".equals(ypxx)) {
							sup.setProofingContentAfter(accessoryProofing.getProofingContent() == null ? "" : accessoryProofing.getProofingContent());
							sup.setProofingEvaluateAfter(accessoryProofing.getProofingEvaluate() == null ? "" : accessoryProofing.getProofingEvaluate());
						}
					} else {
						sup.setProofingContentBefore("");
						sup.setProofingEvaluateBefore("");
						sup.setProofingContentAfter("");
						sup.setProofingEvaluateAfter("");
					}
				}
				/*map.put("quotedCode", dataArray[i].getQuotedCode());
				if("?????????".equals(ypxx)) map.put("proofingType", "?????????");
				if("????????????".equals(ypxx)) map.put("proofingType", "????????????");
				List<AccessoryProofing> accessoryProofingList = AccessoryProofingServiceImpl.getInstance().listAccessoryProofing(map, null);
				if (accessoryProofingList != null && accessoryProofingList.size() > 0) {
					AccessoryProofing accessoryProofing = accessoryProofingList.get(0);
					sup.setProofingContent(accessoryProofing.getProofingContent() == null ? "" : accessoryProofing.getProofingContent());
					sup.setProofingEvaluate(accessoryProofing.getProofingEvaluate() == null ? "" : accessoryProofing.getProofingEvaluate());
				} else {
					sup.setProofingContent("");
					sup.setProofingEvaluate("");
				}*/
				map.clear();
				map.put("enquiryCode", dataArray[i].getEnquiryCode());
				List<AccessoryEnquiryQuotedCount> accessoryEnquiryQuotedCountList = AccessoryEnquiryQuotedCountServiceImpl.getInstance().listAccessoryEnquiryQuotedCount(map, null);
				if (accessoryEnquiryQuotedCountList != null && accessoryEnquiryQuotedCountList.size() > 0) {
					for (AccessoryEnquiryQuotedCount accessoryEnquiryQuotedCount : accessoryEnquiryQuotedCountList) {
						// ??????????????????????????????
						if ("???".equals(allbjsl)) {
							SupplierForm sup_1 = (SupplierForm) sup.clone();
							sup_1.setQuotedCount(accessoryEnquiryQuotedCount.getQuotedCount());
							if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
								sup_1.setQuotedCountShow("Y");
							}
							if (zzxx.indexOf("?????????") > -1 || zzxx.indexOf("??????") > -1) {
								map.clear();
								map.put("quotedCode", dataArray[i].getQuotedCode());
								map.put("orderCount", accessoryEnquiryQuotedCount.getQuotedCount());
								List<AccessoryQuotedTotal> accessoryQuotedTotalList = AccessoryQuotedTotalServiceImpl.getInstance().listAccessoryQuotedTotal(map, null);
								if (accessoryQuotedTotalList != null && accessoryQuotedTotalList.size() > 0) {
									AccessoryQuotedTotal accessoryQuotedTotal = accessoryQuotedTotalList.get(0);
									sup_1.setDailyCapacity(accessoryQuotedTotal.getDailyCapacity());
									sup_1.setDailyCapacityShow("Y");
								} 
								
							}
							listSup.add(sup_1);
							if ("???".equals(zhfp) && "??????".equals(accessoryQuotedElectronic.getInvoiceType())) {
								SupplierForm sup1 = (SupplierForm) sup_1.clone();
								sup1.setInvoiceType("???????????????");
								listSup.add(sup1);
							}
						} else {
							SupplierForm sup_1 = (SupplierForm) sup.clone();
							sup_1.setQuotedCount(accessoryEnquiryQuotedCount.getQuotedCount());
							if (zzxx.indexOf("????????????") > -1 || zzxx.indexOf("??????") > -1) {
								sup_1.setQuotedCountShow("Y");
							}
							if (zzxx.indexOf("?????????") > -1 || zzxx.indexOf("??????") > -1) {
								map.clear();
								map.put("quotedCode", dataArray[i].getQuotedCode());
								map.put("orderCount", accessoryEnquiryQuotedCount.getQuotedCount());
								List<AccessoryQuotedTotal> accessoryQuotedTotalList = AccessoryQuotedTotalServiceImpl.getInstance().listAccessoryQuotedTotal(map, null);
								if (accessoryQuotedTotalList != null && accessoryQuotedTotalList.size() > 0) {
									AccessoryQuotedTotal accessoryQuotedTotal = accessoryQuotedTotalList.get(0);
									sup_1.setDailyCapacity(accessoryQuotedTotal.getDailyCapacity());
									sup_1.setDailyCapacityShow("Y");
								} 
								
							}
							if (txbjsl.equals(accessoryEnquiryQuotedCount.getQuotedCount())) {
								listSup.add(sup_1);
								if ("???".equals(zhfp) && "??????".equals(accessoryQuotedElectronic.getInvoiceType())) {
									SupplierForm sup1 = (SupplierForm) sup_1.clone();
									sup1.setInvoiceType("???????????????");
									listSup.add(sup1);
								}
							}
						}
					}
				}

			}
		}
		// ???????????????
		map.clear();
		map.put("quotedCode", quotedCodes);
		List<AccessoryQuotedMaterial> accessoryQuotedMaterialList = AccessoryQuotedMaterialServiceImpl.getInstance().listAccessoryQuotedMaterial(map, null);
		if (accessoryQuotedMaterialList != null && accessoryQuotedMaterialList.size() > 1) {
			for (int i = 0; i < accessoryQuotedMaterialList.size(); i++) // ???????????????????????????
			{
				for (int j = accessoryQuotedMaterialList.size() - 1; j > i; j--) // ????????????
																					// ??????????????????????????????
				{

					if ((accessoryQuotedMaterialList.get(i).getMaterialName() == null ? "" : accessoryQuotedMaterialList.get(i).getMaterialName()).equals(accessoryQuotedMaterialList.get(j)
							.getMaterialName())) {
						accessoryQuotedMaterialList.remove(j);
					}

				}
			}
		}

		List<AccessoryQuotedMaterialAndSubtotalList> accessoryQuotedMaterialAndSubtotalList = new ArrayList<AccessoryQuotedMaterialAndSubtotalList>();
		List<BigDecimal> totalMaterialList = new ArrayList<BigDecimal>();
		// ??????
		map.clear();
		map.put("quotedCode", quotedCodes);
		List<AccessoryQuotedAccessory> accessoryQuotedAccessoryList = AccessoryQuotedAccessoryServiceImpl.getInstance().listAccessoryQuotedAccessory(map, null);
		if (accessoryQuotedAccessoryList != null && accessoryQuotedAccessoryList.size() > 1) {
			for (int i = 0; i < accessoryQuotedAccessoryList.size(); i++) // ???????????????????????????
			{
				for (int j = accessoryQuotedAccessoryList.size() - 1; j > i; j--) // ????????????
																					// ??????????????????????????????
				{

					if ((accessoryQuotedAccessoryList.get(i).getAccessoryName() == null ? "" : accessoryQuotedAccessoryList.get(i).getAccessoryName()).equals(accessoryQuotedAccessoryList.get(j)
							.getAccessoryName())) {
						accessoryQuotedAccessoryList.remove(j);
					}

				}
			}
		}

		//List<List<AccessoryQuotedAccessoryAndSubtotal>> accessoryQuotedAccessoryAndSubtotalList = new ArrayList<List<AccessoryQuotedAccessoryAndSubtotal>>();
		List<AccessoryQuotedAccessoryAndSubtotalList> accessoryQuotedAccessoryAndSubtotalList = new ArrayList<AccessoryQuotedAccessoryAndSubtotalList>();
		
		List<BigDecimal> totalAccessoryList = new ArrayList<BigDecimal>();
		// ????????????
		map.clear();
		map.put("quotedCode", quotedCodes);
		List<AccessoryQuotedPacking> accessoryQuotedPackingList = AccessoryQuotedPackingServiceImpl.getInstance().listAccessoryQuotedPacking(map, null);
		if (accessoryQuotedPackingList != null && accessoryQuotedPackingList.size() > 1) {
			for (int i = 0; i < accessoryQuotedPackingList.size(); i++) // ???????????????????????????
			{
				for (int j = accessoryQuotedPackingList.size() - 1; j > i; j--) // ????????????
																				// ??????????????????????????????
				{

					if ((accessoryQuotedPackingList.get(i).getPackingName() == null ? "" : accessoryQuotedPackingList.get(i).getPackingName()).equals(accessoryQuotedPackingList.get(j)
							.getPackingName())) {
						accessoryQuotedPackingList.remove(j);
					}

				}
			}
		}

		//List<List<AccessoryQuotedPackingAndSubtotal>> accessoryQuotedPackingAndSubtotalList = new ArrayList<List<AccessoryQuotedPackingAndSubtotal>>();
		List<AccessoryQuotedPackingAndSubtotalList> accessoryQuotedPackingAndSubtotalList = new ArrayList<AccessoryQuotedPackingAndSubtotalList>();
		
		
		List<BigDecimal> totalPackingList = new ArrayList<BigDecimal>();
		// ??????
		map.clear();
		map.put("quotedCode", quotedCodes);
		List<AccessoryQuotedTechnology> accessoryQuotedTechnologyList = AccessoryQuotedTechnologyServiceImpl.getInstance().listAccessoryQuotedTechnology(map, null);
		if (accessoryQuotedTechnologyList != null && accessoryQuotedTechnologyList.size() > 1) {
			for (int i = 0; i < accessoryQuotedTechnologyList.size(); i++) // ???????????????????????????
			{
				for (int j = accessoryQuotedTechnologyList.size() - 1; j > i; j--) // ????????????
																					// ??????????????????????????????
				{

					if ((accessoryQuotedTechnologyList.get(i).getTechnologyName() == null ? "" : accessoryQuotedTechnologyList.get(i).getTechnologyName()).equals(accessoryQuotedTechnologyList.get(j)
							.getTechnologyName())) {
						accessoryQuotedTechnologyList.remove(j);
					}

				}
			}
		}

		//List<List<AccessoryQuotedTechnologyAndSubtotal>> accessoryQuotedTechnologyAndSubtotalList = new ArrayList<List<AccessoryQuotedTechnologyAndSubtotal>>();
		List<AccessoryQuotedTechnologyAndSubtotalList> accessoryQuotedTechnologyAndSubtotalList = new ArrayList<AccessoryQuotedTechnologyAndSubtotalList>();
	
		
		List<BigDecimal> totalTechnologyList = new ArrayList<BigDecimal>();
		List<BigDecimal> totalMaterialAccessoryList = new ArrayList<BigDecimal>();
		// ?????????????????????
		for (int m = 0; m < listSup.size(); m++) {
			BigDecimal materialTotal = new BigDecimal("0");
			BigDecimal  materialAccessoryTotal =new BigDecimal("0");
			map.clear();
			map.put("quotedCode", listSup.get(m).getQuotedCode());
			map.put("orderCount", listSup.get(m).getQuotedCount());
			List<AccessoryQuotedMaterial> accessoryQuotedMaterialListsForTotal = AccessoryQuotedMaterialServiceImpl.getInstance().listAccessoryQuotedMaterial(map, null);
			if (accessoryQuotedMaterialListsForTotal != null && accessoryQuotedMaterialListsForTotal.size() > 0) {
				for (AccessoryQuotedMaterial accessoryQuotedMaterial : accessoryQuotedMaterialListsForTotal){
					materialTotal = materialTotal.add(accessoryQuotedMaterial.getCost());
				    materialAccessoryTotal=materialAccessoryTotal.add(accessoryQuotedMaterial.getCost());
				}
			}

			// ??????
			BigDecimal accessoryTotal = new BigDecimal("0");
			map.clear();
			map.put("quotedCode", listSup.get(m).getQuotedCode());
			map.put("orderCount", listSup.get(m).getQuotedCount());
			List<AccessoryQuotedAccessory> accessoryQuotedAccessoryListsForTotal = AccessoryQuotedAccessoryServiceImpl.getInstance().listAccessoryQuotedAccessory(map, null);
			if (accessoryQuotedAccessoryListsForTotal != null && accessoryQuotedAccessoryListsForTotal.size() > 0) {
				for (AccessoryQuotedAccessory accessoryQuotedAccessory : accessoryQuotedAccessoryListsForTotal){
					accessoryTotal = accessoryTotal.add(accessoryQuotedAccessory.getCost());
				materialAccessoryTotal=materialAccessoryTotal.add(accessoryQuotedAccessory.getCost());
				}
			}

			// ????????????
			BigDecimal packingTotal = new BigDecimal("0");
			map.clear();
			map.put("quotedCode", listSup.get(m).getQuotedCode());
			map.put("orderCount", listSup.get(m).getQuotedCount());
			List<AccessoryQuotedPacking> accessoryQuotedPackingListsForTotal = AccessoryQuotedPackingServiceImpl.getInstance().listAccessoryQuotedPacking(map, null);
			if (accessoryQuotedPackingListsForTotal != null && accessoryQuotedPackingListsForTotal.size() > 0) {
				for (AccessoryQuotedPacking accessoryQuotedPacking : accessoryQuotedPackingListsForTotal)
					packingTotal = packingTotal.add(accessoryQuotedPacking.getCost());
			}

			// ??????
			BigDecimal technologyTotal = new BigDecimal("0");
			map.clear();
			map.put("quotedCode", listSup.get(m).getQuotedCode());
			map.put("orderCount", listSup.get(m).getQuotedCount());
			List<AccessoryQuotedTechnology> accessoryQuotedTechnologyListsForTotal = AccessoryQuotedTechnologyServiceImpl.getInstance().listAccessoryQuotedTechnology(map, null);
			if (accessoryQuotedTechnologyListsForTotal != null && accessoryQuotedTechnologyListsForTotal.size() > 0) {
				for (AccessoryQuotedTechnology accessoryQuotedTechnology : accessoryQuotedTechnologyListsForTotal)
					technologyTotal = technologyTotal.add(accessoryQuotedTechnology.getCost());
			}

			if ("???????????????".equals(listSup.get(m).getInvoiceType())) {
				materialTotal = materialTotal.divide((new BigDecimal("1").add(listSup.get(m).getTaxRate().divide(new BigDecimal("100")))), 3, BigDecimal.ROUND_HALF_UP);
				accessoryTotal = accessoryTotal.divide((new BigDecimal("1").add(listSup.get(m).getTaxRate().divide(new BigDecimal("100")))), 3, BigDecimal.ROUND_HALF_UP);
				packingTotal = packingTotal.divide((new BigDecimal("1").add(listSup.get(m).getTaxRate().divide(new BigDecimal("100")))), 3, BigDecimal.ROUND_HALF_UP);
				technologyTotal = technologyTotal.divide((new BigDecimal("1").add(listSup.get(m).getTaxRate().divide(new BigDecimal("100")))), 3, BigDecimal.ROUND_HALF_UP);
			}
			totalMaterialList.add(materialTotal);
			totalAccessoryList.add(accessoryTotal);
			totalPackingList.add(packingTotal);
			totalTechnologyList.add(technologyTotal);
			totalMaterialAccessoryList.add(materialAccessoryTotal);
		}
		for (int i = 0; i < accessoryQuotedMaterialList.size(); i++) {
			AccessoryQuotedMaterialAndSubtotalList accessoryQuotedMaterialAndSubtotalarray=new AccessoryQuotedMaterialAndSubtotalList();
			List<AccessoryQuotedMaterialAndSubtotal> accessoryQuotedMaterialAndSubtotals = new ArrayList<AccessoryQuotedMaterialAndSubtotal>();
			for (int k = 0; k < listSup.size(); k++) {
				AccessoryQuotedMaterialAndSubtotal accessoryQuotedMaterialAndSubtotal = new AccessoryQuotedMaterialAndSubtotal();
				map.clear();
				map.put("materialName", accessoryQuotedMaterialList.get(i).getMaterialName());
				map.put("quotedCode", listSup.get(k).getQuotedCode());
				map.put("orderCount", listSup.get(k).getQuotedCount());
				AccessoryQuotedMaterial accessoryQuotedMaterial = new AccessoryQuotedMaterial();
				if (k == 0) {
					AccessoryQuotedMaterialAndSubtotal accessoryQuotedMaterialAndSubtotalNew = new AccessoryQuotedMaterialAndSubtotal();
					AccessoryQuotedMaterial accessoryQuotedMaterialNew = new AccessoryQuotedMaterial();
					accessoryQuotedMaterialNew.setMaterialName(accessoryQuotedMaterialList.get(i).getMaterialName());
					accessoryQuotedMaterialNew.setOrigin(accessoryQuotedMaterialList.get(i).getOrigin());
					accessoryQuotedMaterialNew.setBrand(accessoryQuotedMaterialList.get(i).getBrand());
					accessoryQuotedMaterialAndSubtotalNew.setAccessoryQuotedMaterial(accessoryQuotedMaterialNew);
					accessoryQuotedMaterialAndSubtotals.add(accessoryQuotedMaterialAndSubtotalNew);
				}
				List<AccessoryQuotedMaterial> accessoryQuotedMaterialLists = AccessoryQuotedMaterialServiceImpl.getInstance().listAccessoryQuotedMaterial(map, null);
				if (accessoryQuotedMaterialLists != null && accessoryQuotedMaterialLists.size() > 0) {
					// ???????????????????????????
					accessoryQuotedMaterial = accessoryQuotedMaterialLists.get(0);

					accessoryQuotedMaterialAndSubtotal.setAccessoryQuotedMaterial(accessoryQuotedMaterial);
					if ("???????????????".equals(listSup.get(k).getInvoiceType())) {
						accessoryQuotedMaterial.setCost(accessoryQuotedMaterial.getCost().divide((new BigDecimal("1").add(listSup.get(k).getTaxRate().divide(new BigDecimal("100")))), 2, BigDecimal.ROUND_HALF_UP));
						if(totalMaterialList.get(k)!=null&&!"0".equals(totalMaterialList.get(k).toString())&&!"0.00".equals(totalMaterialList.get(k).toString())){
						accessoryQuotedMaterialAndSubtotal.setSubtotal((accessoryQuotedMaterial.getCost().multiply(new BigDecimal("100"))
								.divide(totalMaterialList.get(k), 2, BigDecimal.ROUND_HALF_UP)).toString());
						}else{
							accessoryQuotedMaterialAndSubtotal.setSubtotal(new BigDecimal("0").toString());
						}
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedMaterial.getCost()));
					} else {
						if(totalMaterialList.get(k)!=null&&!"0".equals(totalMaterialList.get(k).toString())&&!"0.00".equals(totalMaterialList.get(k).toString())){
						accessoryQuotedMaterialAndSubtotal.setSubtotal((accessoryQuotedMaterial.getCost().multiply(new BigDecimal("100")).divide(totalMaterialList.get(k), 2, BigDecimal.ROUND_HALF_UP)).toString());
						}else{
							accessoryQuotedMaterialAndSubtotal.setSubtotal(new BigDecimal("0").toString());
						}
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedMaterial.getCost()));
					}
					// ?????????list
					accessoryQuotedMaterialAndSubtotals.add(accessoryQuotedMaterialAndSubtotal);
				} else {
					accessoryQuotedMaterialAndSubtotal.setAccessoryQuotedMaterial(accessoryQuotedMaterial);
					accessoryQuotedMaterialAndSubtotal.setSubtotal(new BigDecimal("0").toString());
					// ?????????list
					accessoryQuotedMaterialAndSubtotals.add(accessoryQuotedMaterialAndSubtotal);
				}
				
			}
			// ????????????list
			accessoryQuotedMaterialAndSubtotalarray.setAccessoryQuotedMaterialAndSubtotalarray(accessoryQuotedMaterialAndSubtotals);
			accessoryQuotedMaterialAndSubtotalList.add(accessoryQuotedMaterialAndSubtotalarray);
		}
		// ??????

		for (int i = 0; i < accessoryQuotedAccessoryList.size(); i++) {
			AccessoryQuotedAccessoryAndSubtotalList accessoryQuotedAccessoryAndSubtotalarray=new AccessoryQuotedAccessoryAndSubtotalList();
			List<AccessoryQuotedAccessoryAndSubtotal> accessoryQuotedAccessoryAndSubtotals = new ArrayList<AccessoryQuotedAccessoryAndSubtotal>();
			for (int k = 0; k < listSup.size(); k++) {
				AccessoryQuotedAccessoryAndSubtotal accessoryQuotedAccessoryAndSubtotal = new AccessoryQuotedAccessoryAndSubtotal();
				map.clear();
				map.put("accessoryName", accessoryQuotedAccessoryList.get(i).getAccessoryName());
				map.put("quotedCode", listSup.get(k).getQuotedCode());
				map.put("orderCount", listSup.get(k).getQuotedCount());
				AccessoryQuotedAccessory accessoryQuotedAccessory = new AccessoryQuotedAccessory();
				if (k == 0) {
					AccessoryQuotedAccessoryAndSubtotal accessoryQuotedAccessoryAndSubtotalNew = new AccessoryQuotedAccessoryAndSubtotal();
					AccessoryQuotedAccessory accessoryQuotedAccessoryNew = new AccessoryQuotedAccessory();
					accessoryQuotedAccessoryNew.setAccessoryName(accessoryQuotedAccessoryList.get(i).getAccessoryName());
					accessoryQuotedAccessoryNew.setOrigin(accessoryQuotedAccessoryList.get(i).getOrigin());
					accessoryQuotedAccessoryNew.setBrand(accessoryQuotedAccessoryList.get(i).getBrand());
					accessoryQuotedAccessoryAndSubtotalNew.setAccessoryQuotedAccessory(accessoryQuotedAccessoryNew);
					accessoryQuotedAccessoryAndSubtotals.add(accessoryQuotedAccessoryAndSubtotalNew);
				}
				List<AccessoryQuotedAccessory> accessoryQuotedAccessoryLists = AccessoryQuotedAccessoryServiceImpl.getInstance().listAccessoryQuotedAccessory(map, null);
				if (accessoryQuotedAccessoryLists != null && accessoryQuotedAccessoryLists.size() > 0) {
					// ????????????????????????
					accessoryQuotedAccessory = accessoryQuotedAccessoryLists.get(0);

					accessoryQuotedAccessoryAndSubtotal.setAccessoryQuotedAccessory(accessoryQuotedAccessory);
					
					if ("???????????????".equals(listSup.get(k).getInvoiceType())) {
						accessoryQuotedAccessory.setCost(accessoryQuotedAccessory.getCost().divide((new BigDecimal("1").add(listSup.get(k).getTaxRate().divide(new BigDecimal("100")))), 2, BigDecimal.ROUND_HALF_UP));
						if(totalAccessoryList.get(k)!=null&&!"0".equals(totalAccessoryList.get(k).toString())&&!"0.00".equals(totalAccessoryList.get(k).toString())){
						accessoryQuotedAccessoryAndSubtotal.setSubtotal(accessoryQuotedAccessory.getCost()
								.divide(totalAccessoryList.get(k), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						}else{
							accessoryQuotedAccessoryAndSubtotal.setSubtotal(new BigDecimal("0"));
						}
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedAccessory.getCost()));
					} else {
						if(totalAccessoryList.get(k)!=null&&!"0".equals(totalAccessoryList.get(k).toString())&&!"0.00".equals(totalAccessoryList.get(k).toString())){
						accessoryQuotedAccessoryAndSubtotal.setSubtotal(accessoryQuotedAccessory.getCost().divide(totalAccessoryList.get(k), 5, BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal("100")));
						}else{
							accessoryQuotedAccessoryAndSubtotal.setSubtotal(new BigDecimal("0"));
						}
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedAccessory.getCost()));
					}
					// ?????????list
					accessoryQuotedAccessoryAndSubtotals.add(accessoryQuotedAccessoryAndSubtotal);
				} else {
					accessoryQuotedAccessoryAndSubtotal.setAccessoryQuotedAccessory(accessoryQuotedAccessory);
					accessoryQuotedAccessoryAndSubtotal.setSubtotal(new BigDecimal("0"));
					// ?????????list
					accessoryQuotedAccessoryAndSubtotals.add(accessoryQuotedAccessoryAndSubtotal);
				}
			}
			// ????????????list
			accessoryQuotedAccessoryAndSubtotalarray.setAccessoryQuotedAccessoryAndSubtotalarray(accessoryQuotedAccessoryAndSubtotals);
			accessoryQuotedAccessoryAndSubtotalList.add(accessoryQuotedAccessoryAndSubtotalarray);
		}
		// ????????????

		for (int i = 0; i < accessoryQuotedPackingList.size(); i++) {
			AccessoryQuotedPackingAndSubtotalList accessoryQuotedPackingAndSubtotalarray=new AccessoryQuotedPackingAndSubtotalList();
			List<AccessoryQuotedPackingAndSubtotal> accessoryQuotedPackingAndSubtotals = new ArrayList<AccessoryQuotedPackingAndSubtotal>();
			for (int k = 0; k < listSup.size(); k++) {
				AccessoryQuotedPackingAndSubtotal accessoryQuotedPackingAndSubtotal = new AccessoryQuotedPackingAndSubtotal();
				map.clear();
				map.put("packingName", accessoryQuotedPackingList.get(i).getPackingName());
				map.put("quotedCode", listSup.get(k).getQuotedCode());
				map.put("orderCount", listSup.get(k).getQuotedCount());
				AccessoryQuotedPacking accessoryQuotedPacking = new AccessoryQuotedPacking();
				if (k == 0) {
					AccessoryQuotedPackingAndSubtotal accessoryQuotedPackingAndSubtotalNew = new AccessoryQuotedPackingAndSubtotal();
					AccessoryQuotedPacking accessoryQuotedPackingNew = new AccessoryQuotedPacking();
					accessoryQuotedPackingNew.setPackingName(accessoryQuotedPackingList.get(i).getPackingName());
					accessoryQuotedPackingNew.setOrigin(accessoryQuotedPackingList.get(i).getOrigin());
					accessoryQuotedPackingNew.setBrand(accessoryQuotedPackingList.get(i).getBrand());
					accessoryQuotedPackingAndSubtotalNew.setAccessoryQuotedPacking(accessoryQuotedPackingNew);
					accessoryQuotedPackingAndSubtotals.add(accessoryQuotedPackingAndSubtotalNew);
				}
				List<AccessoryQuotedPacking> accessoryQuotedPackingLists = AccessoryQuotedPackingServiceImpl.getInstance().listAccessoryQuotedPacking(map, null);
				if (accessoryQuotedPackingLists != null && accessoryQuotedPackingLists.size() > 0) {
					// ???????????????????????????
					accessoryQuotedPacking = accessoryQuotedPackingLists.get(0);

					accessoryQuotedPackingAndSubtotal.setAccessoryQuotedPacking(accessoryQuotedPacking);
					if ("???????????????".equals(listSup.get(k).getInvoiceType())) {
						accessoryQuotedPacking.setCost(accessoryQuotedPacking.getCost().divide((new BigDecimal("1").add(listSup.get(k).getTaxRate().divide(new BigDecimal("100")))), 2, BigDecimal.ROUND_HALF_UP));
						if(totalPackingList.get(k)!=null&&!"0".equals(totalPackingList.get(k).toString())&&!"0.00".equals(totalPackingList.get(k).toString())){
						accessoryQuotedPackingAndSubtotal.setSubtotal(accessoryQuotedPacking.getCost()
								.divide(totalPackingList.get(k), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						}else{
							accessoryQuotedPackingAndSubtotal.setSubtotal(new BigDecimal("0"));
						}
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedPacking.getCost()));
					} else {
						if(totalPackingList.get(k)!=null&&!"0".equals(totalPackingList.get(k).toString())&&!"0.00".equals(totalPackingList.get(k).toString())){
						accessoryQuotedPackingAndSubtotal.setSubtotal(accessoryQuotedPacking.getCost().divide(totalPackingList.get(k), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						}else{
							accessoryQuotedPackingAndSubtotal.setSubtotal(new BigDecimal("0"));	
						}
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedPacking.getCost()));
					}
					// ?????????list
					accessoryQuotedPackingAndSubtotals.add(accessoryQuotedPackingAndSubtotal);
				} else {
					accessoryQuotedPackingAndSubtotal.setAccessoryQuotedPacking(accessoryQuotedPacking);
					accessoryQuotedPackingAndSubtotal.setSubtotal(new BigDecimal("0"));
					// ?????????list
					accessoryQuotedPackingAndSubtotals.add(accessoryQuotedPackingAndSubtotal);
				}
			}
			// ????????????list
			accessoryQuotedPackingAndSubtotalarray.setAccessoryQuotedPackingAndSubtotalarray(accessoryQuotedPackingAndSubtotals);
			accessoryQuotedPackingAndSubtotalList.add(accessoryQuotedPackingAndSubtotalarray);
		}
		// ??????
		for (int i = 0; i < accessoryQuotedTechnologyList.size(); i++) {
			AccessoryQuotedTechnologyAndSubtotalList accessoryQuotedTechnologyAndSubtotalarray=new AccessoryQuotedTechnologyAndSubtotalList();
			List<AccessoryQuotedTechnologyAndSubtotal> accessoryQuotedTechnologyAndSubtotals = new ArrayList<AccessoryQuotedTechnologyAndSubtotal>();
			for (int k = 0; k < listSup.size(); k++) {
				AccessoryQuotedTechnologyAndSubtotal accessoryQuotedTechnologyAndSubtotal = new AccessoryQuotedTechnologyAndSubtotal();
				map.clear();
				map.put("technologyName", accessoryQuotedTechnologyList.get(i).getTechnologyName());
				map.put("quotedCode", listSup.get(k).getQuotedCode());
				map.put("orderCount", listSup.get(k).getQuotedCount());
				AccessoryQuotedTechnology accessoryQuotedTechnology = new AccessoryQuotedTechnology();
				if (k == 0) {
					AccessoryQuotedTechnologyAndSubtotal accessoryQuotedTechnologyAndSubtotalNew = new AccessoryQuotedTechnologyAndSubtotal();
					AccessoryQuotedTechnology accessoryQuotedTechnologyNew = new AccessoryQuotedTechnology();
					accessoryQuotedTechnologyNew.setTechnologyName(accessoryQuotedTechnologyList.get(i).getTechnologyName());
					accessoryQuotedTechnologyAndSubtotalNew.setAccessoryQuotedTechnology(accessoryQuotedTechnologyNew);
					accessoryQuotedTechnologyAndSubtotals.add(accessoryQuotedTechnologyAndSubtotalNew);
				}
				List<AccessoryQuotedTechnology> accessoryQuotedTechnologyLists = AccessoryQuotedTechnologyServiceImpl.getInstance().listAccessoryQuotedTechnology(map, null);
				if (accessoryQuotedTechnologyLists != null && accessoryQuotedTechnologyLists.size() > 0) {
					// ????????????????????????
					accessoryQuotedTechnology = accessoryQuotedTechnologyLists.get(0);

					accessoryQuotedTechnologyAndSubtotal.setAccessoryQuotedTechnology(accessoryQuotedTechnology);
					if ("???????????????".equals(listSup.get(k).getInvoiceType())) {
						accessoryQuotedTechnology.setCost(accessoryQuotedTechnology.getCost().divide((new BigDecimal("1").add(listSup.get(k).getTaxRate().divide(new BigDecimal("100")))), 2, BigDecimal.ROUND_HALF_UP));
						if(totalTechnologyList.get(k)!=null&&!"0".equals(totalTechnologyList.get(k).toString())&&!"0.00".equals(totalTechnologyList.get(k).toString())){
						accessoryQuotedTechnologyAndSubtotal.setSubtotal(accessoryQuotedTechnology.getCost()
								.divide(totalTechnologyList.get(k), 5, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						}else{
							accessoryQuotedTechnologyAndSubtotal.setSubtotal(new BigDecimal("0"));
						}
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedTechnology.getCost()));
					} else {
						if(totalTechnologyList.get(k)!=null&&!"0".equals(totalTechnologyList.get(k).toString())&&!"0.00".equals(totalTechnologyList.get(k).toString())){
						accessoryQuotedTechnologyAndSubtotal.setSubtotal(accessoryQuotedTechnology.getCost().divide(totalTechnologyList.get(k), 5, BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal("100")));
						}else{
							accessoryQuotedTechnologyAndSubtotal.setSubtotal(new BigDecimal("0"));
						}
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedTechnology.getCost()));
					}
					// ?????????list
					accessoryQuotedTechnologyAndSubtotals.add(accessoryQuotedTechnologyAndSubtotal);
				} else {
					accessoryQuotedTechnologyAndSubtotal.setAccessoryQuotedTechnology(accessoryQuotedTechnology);
					accessoryQuotedTechnologyAndSubtotal.setSubtotal(new BigDecimal("0"));
					// ?????????list
					accessoryQuotedTechnologyAndSubtotals.add(accessoryQuotedTechnologyAndSubtotal);
				}
			}
			// ????????????list
			accessoryQuotedTechnologyAndSubtotalarray.setAccessoryQuotedTechnologyAndSubtotalarray(accessoryQuotedTechnologyAndSubtotals);
			accessoryQuotedTechnologyAndSubtotalList.add(accessoryQuotedTechnologyAndSubtotalarray);
		}
		// ????????????
		//List<List<AccessoryQuotedElse>> accessoryQuotedElseList = new ArrayList<List<AccessoryQuotedElse>>();
		List<AccessoryQuotedElseList> accessoryQuotedElseList = new ArrayList<AccessoryQuotedElseList>();
	
		
		List<BigDecimal> allTotalList = new ArrayList<BigDecimal>();
		List<String> cooperativePriceList = new ArrayList<String>();
		for (int k = 0; k < listSup.size(); k++) {
			AccessoryQuotedElseList accessoryQuotedElsearray=new AccessoryQuotedElseList();
			List<AccessoryQuotedElse> accessoryQuotedElses = new ArrayList<AccessoryQuotedElse>();
			map.clear();
			map.put("quotedCode", listSup.get(k).getQuotedCode());
			map.put("orderCount", listSup.get(k).getQuotedCount());

			accessoryQuotedElses = AccessoryQuotedElseServiceImpl.getInstance().listAccessoryQuotedElse(map, null);
			if (accessoryQuotedElses != null && accessoryQuotedElses.size() > 0) {
				// ???????????????????????????
				for (AccessoryQuotedElse accessoryQuotedElse : accessoryQuotedElses) {
					if ("???????????????".equals(listSup.get(k).getInvoiceType())) {
						if (!(accessoryQuotedElse.getCostType().indexOf("??????") > -1)) {
							accessoryQuotedElse.setCost(accessoryQuotedElse.getCost().divide((new BigDecimal("1").add(listSup.get(k).getTaxRate().divide(new BigDecimal("100")))), 2, BigDecimal.ROUND_HALF_UP));
							listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedElse.getCost()));
						}
					} else {
						accessoryQuotedElse.setCost(accessoryQuotedElse.getCost());
						if (!(accessoryQuotedElse.getCostType().indexOf("??????") > -1)) {
						listSup.get(k).setTotal(listSup.get(k).getTotal().add(accessoryQuotedElse.getCost()));
					}
					}
				}
				accessoryQuotedElsearray.setAccessoryQuotedElsearray(accessoryQuotedElses);
				accessoryQuotedElseList.add(accessoryQuotedElsearray);
			} else {
				AccessoryQuotedElse accessoryQuotedElse = new AccessoryQuotedElse();
				List<AccessoryQuotedElse> accessoryQuotedElsesNew = new ArrayList<AccessoryQuotedElse>();
				accessoryQuotedElsesNew.add(accessoryQuotedElse);
				accessoryQuotedElsearray.setAccessoryQuotedElsearray(accessoryQuotedElsesNew);
				accessoryQuotedElseList.add(accessoryQuotedElsearray);
			}
			// ?????????
			map.clear();
			map.put("quotedCode", listSup.get(k).getQuotedCode());
			map.put("orderCount", listSup.get(k).getQuotedCount());
			List<AccessoryQuotedTotal> accessoryQuotedTotalList = AccessoryQuotedTotalServiceImpl.getInstance().listAccessoryQuotedTotal(map, null);
			if (accessoryQuotedTotalList != null && accessoryQuotedTotalList.size() > 0) {
				if ("???????????????".equals(listSup.get(k).getInvoiceType())) {
					allTotalList.add(accessoryQuotedTotalList.get(0).getUnitPrice().divide((new BigDecimal("1").add(listSup.get(k).getTaxRate().divide(new BigDecimal("100")))), 3, BigDecimal.ROUND_HALF_UP));
				} else {
					allTotalList.add(accessoryQuotedTotalList.get(0).getUnitPrice());
				}
			} else {
				allTotalList.add(new BigDecimal("0"));
			}
			// ????????????
			map.clear();
			map.put("quotedCode", listSup.get(k).getQuotedCode());
			map.put("enquiryCount", listSup.get(k).getQuotedCount());
			List<WlInfo> wlInfoList = WlInfoServiceImpl.getInstance().listWlInfo(map, null);
			if (wlInfoList != null && wlInfoList.size() > 0) {
				if(wlInfoList.get(0).getContractPrice()!=null){
				cooperativePriceList.add(formatNumber(new BigDecimal(wlInfoList.get(0).getContractPrice().toString())));
				}else{
					cooperativePriceList.add("");	
				}
			} else {
				cooperativePriceList.add("");
			}
		}
		
		//
		costAnalogyForm.setSupplierFormList(listSup);
		costAnalogyForm.setAccessoryQuotedMaterialAndSubtotalList(accessoryQuotedMaterialAndSubtotalList);
		costAnalogyForm.setAccessoryQuotedAccessoryAndSubtotalList(accessoryQuotedAccessoryAndSubtotalList);
		costAnalogyForm.setAccessoryQuotedPackingAndSubtotalList(accessoryQuotedPackingAndSubtotalList);
		costAnalogyForm.setAccessoryQuotedTechnologyAndSubtotalList(accessoryQuotedTechnologyAndSubtotalList);
		costAnalogyForm.setAccessoryQuotedElseList(accessoryQuotedElseList);
		costAnalogyForm.setTotalMaterialList(totalMaterialList);
		costAnalogyForm.setTotalAccessoryList(totalAccessoryList);
		costAnalogyForm.setTotalMaterialAccessoryList(totalMaterialAccessoryList);
		costAnalogyForm.setTotalPackingList(totalPackingList);
		costAnalogyForm.setTotalTechnologyList(totalTechnologyList);
		costAnalogyForm.setAllTotalList(allTotalList);
		costAnalogyForm.setCooperativePriceList(cooperativePriceList);
		String object=JSON.toJSONString(costAnalogyForm);
		String json = object.replaceAll("\"", "@%");
	//	JSONObject object = JSONObject.fromObject(costAnalogyForm);
	//	String json = object.toString().replaceAll("\"", "%");
		costAnalogyForm.setJson(json);
		this.putObject("costAnalogyForm", costAnalogyForm);
		this.forwardPage("sco/accessoryIntentionCostAnalysis/costAnalogy/costAnalogyForm.ftl");
	}
	/**
	 * ?????????Excel
	 * 
	 * @throws IOException
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doExportDataToExcel() throws Exception {
		String rows = this.asString("date");
		List<String> remarkTexts=this.remarkTextList;
		/*JSONObject jobj = JSONObject.fromObject(rows);
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("supplierFormList", SupplierForm.class);
		classMap.put("accessoryQuotedMaterialAndSubtotalList",AccessoryQuotedMaterialAndSubtotalList.class);
		classMap.put("accessoryQuotedAccessoryAndSubtotalList", AccessoryQuotedAccessoryAndSubtotalList.class);
		classMap.put("accessoryQuotedPackingAndSubtotalList", AccessoryQuotedPackingAndSubtotalList.class);
		classMap.put("accessoryQuotedTechnologyAndSubtotalList", AccessoryQuotedTechnologyAndSubtotalList.class);
		classMap.put("accessoryQuotedElseList", AccessoryQuotedElseList.class);
		
		classMap.put("accessoryQuotedMaterialAndSubtotalarray", AccessoryQuotedMaterialAndSubtotal.class);
		classMap.put("accessoryQuotedAccessoryAndSubtotalarray", AccessoryQuotedAccessoryAndSubtotal.class);
		classMap.put("accessoryQuotedPackingAndSubtotalarray", AccessoryQuotedPackingAndSubtotal.class);
		classMap.put("accessoryQuotedTechnologyAndSubtotalarray", AccessoryQuotedTechnologyAndSubtotal.class);
		classMap.put("accessoryQuotedElsearray", AccessoryQuotedElse.class);
		
		classMap.put("accessoryQuotedMaterialAndSubtotals", AccessoryQuotedMaterialAndSubtotal.class);
		classMap.put("accessoryQuotedAccessoryAndSubtotals", AccessoryQuotedAccessoryAndSubtotal.class);
		classMap.put("accessoryQuotedPackingAndSubtotals", AccessoryQuotedPackingAndSubtotal.class);
		classMap.put("accessoryQuotedTechnologyAndSubtotals", AccessoryQuotedTechnologyAndSubtotal.class);
		classMap.put("accessoryQuotedElses", AccessoryQuotedElse.class);
		
		classMap.put("accessoryQuotedMaterial", AccessoryQuotedMaterial.class);
		classMap.put("totalMaterialList", BigDecimal.class);
		classMap.put("totalAccessoryList", BigDecimal.class);
		classMap.put("totalMaterialAccessoryList", BigDecimal.class);
		classMap.put("totalPackingList", BigDecimal.class);
		classMap.put("totalTechnologyList", BigDecimal.class);
		classMap.put("allTotalList", BigDecimal.class);*/
	//	CostAnalogyForm costAnalogyForm = (CostAnalogyForm) JSONObject.toBean(jobj, CostAnalogyForm.class, classMap);
		CostAnalogyForm costAnalogyForm = JSON.parseObject(rows,CostAnalogyForm.class,Feature.InitStringFieldAsEmpty);
		ServletOutputStream out = response.getOutputStream();
		String fileName = "????????????????????????" + "_".concat(DateUtils.formateDateTime()).concat(".xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(Constant.DEFAULT_ENCODED_1), Constant.DEFAULT_ENCODED_2) + "\"");
		costAnalogyService.exportExcel(costAnalogyForm,remarkTexts, out);

	}

	/**
	 * ????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doSaveSearchDataForm() throws Exception {

		String fileName = this.asString("fileName");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("reportsTypeCode", BusinessConstants.myReportType.FCA);// ????????????
		map.put("reportsName", fileName);// ????????????
		// ??????????????????
		boolean exists = ReportsServiceImpl.getInstance().ifFileNameExists(map);
		if (exists) {
			this.forwardData(false, null, "???????????????????????????,?????????");
			return;
		}
		// ??????????????????
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("tableInfo", this.asString("data"));
		String msg = costAnalogyService.saveSearchDataForm(fileName, paraMap);
		if (StringUtils.isBlank(msg)) {
			this.forwardData(true, null, "??????????????????");
			return;
		}
		this.forwardData(false, null, msg);
	}

	/**
	 * ??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doListCostAnalogyIntentionApplication() throws Exception {
		Map<String, Object> map = getCostAnalogy().toMap();
		String supplierNo = this.asString("intentionSupplierCode");
		String supplierName = this.asString("intentionSupplierName");

		map.put("search", this.asString("search"));

		map.put("intentionSupplierCode", supplierNo);
		map.put("intentionSupplierName", supplierName);
		map.put("supplierCode", supplierNo);
		map.put("supplierName", supplierName);
		List<CostAnalogy> list = costAnalogyService.listCostAnalogyIntentionApplication(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * 
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doLoadCostAnalogyIntentionApplication() throws Exception {
		CostAnalogy costAnalogy = costAnalogyService.loadCostAnalogyIntentionApplication(asString("quotedCode"));
		this.forwardData(true, costAnalogy, null);
	}

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	private CostAnalogy getCostAnalogy() throws Exception {
		CostAnalogy costAnalogy = new CostAnalogy();
		this.asBean(costAnalogy);
		return costAnalogy;
	}
	/**
	 * ????????????????????????????????????????????????
	 * 
	 * 
	 */
	private String formatNumber(BigDecimal num) {
		DecimalFormat df = new DecimalFormat("#,##0.000;(#)");
		df.setRoundingMode(RoundingMode.HALF_UP);
		if (num != null) {
			return df.format(num);
		}
		return "";
	}

}
