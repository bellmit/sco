package com.powere2e.sco.common.utils;

import java.util.ArrayList;

import com.powere2e.sco.common.service.BusinessConstants;
import com.powere2e.sco.common.service.BusinessConstants.AccessoryDetailType;
import com.powere2e.sco.common.service.BusinessConstants.ApplicationStatus;
import com.powere2e.sco.common.service.BusinessConstants.ContainerType;
import com.powere2e.sco.common.service.BusinessConstants.Development;
import com.powere2e.sco.common.service.BusinessConstants.ElseCost;
import com.powere2e.sco.common.service.BusinessConstants.EvaluateTemplateMarkDepartments;
import com.powere2e.sco.common.service.BusinessConstants.EvaluateTemplateStatus;
import com.powere2e.sco.common.service.BusinessConstants.FactoryPriceType;
import com.powere2e.sco.common.service.BusinessConstants.ForetasteType;
import com.powere2e.sco.common.service.BusinessConstants.FreightType;
import com.powere2e.sco.common.service.BusinessConstants.ImportRelevantCost;
import com.powere2e.sco.common.service.BusinessConstants.MaterialType;
import com.powere2e.sco.common.service.BusinessConstants.MerchandiseApplicationStatus;
import com.powere2e.sco.common.service.BusinessConstants.MerchandiseNPackag;
import com.powere2e.sco.common.service.BusinessConstants.MerchandiseWPackag;
import com.powere2e.sco.common.service.BusinessConstants.ProcurementDepartments;
import com.powere2e.sco.common.service.BusinessConstants.PurchaseType;
import com.powere2e.sco.common.service.BusinessConstants.QuotedCurrency;
import com.powere2e.sco.common.service.BusinessConstants.Rationed;
import com.powere2e.sco.common.service.BusinessConstants.Remind;
import com.powere2e.sco.common.service.BusinessConstants.SupplierAttachmentType;
import com.powere2e.sco.common.service.BusinessConstants.VisitApplicationStatus;
import com.powere2e.sco.common.service.BusinessConstants.YesNo;
import com.powere2e.sco.common.service.BusinessConstants.myReportType;
import com.powere2e.sco.common.service.BusinessConstants.signedQuantityType;
import com.powere2e.security.model.Option;

/**
 * ????????????????????????
 * 
 * @author Gavillen.Zhou
 * @since 2015???3???23???
 * @version 1.0
 */
public class BusinessUtils {
	/**
	 * ????????????????????????
	 */
	public final static ArrayList<Option> DEPARTMENTS;
	/**
	 * ??????????????????
	 */
	public final static ArrayList<Option> EVALUATE_TEMPLATE_STATUS;
	/**
	 * ???????????????????????????
	 */
	public final static ArrayList<Option> SINGED_QTY_LIST;
	/**
	 * ????????????
	 */
	public final static ArrayList<Option> PROCUREMENT_DEPARTMENTS;
	/**
	 * ?????????-????????????
	 */
	public final static ArrayList<Option> MATERIAL_TYPE;
	/**
	 * ???????????????
	 */
	public final static ArrayList<Option> RATIONED;
	/**
	 * ????????????
	 */
	public final static ArrayList<Option> PURCHASE_TYPE;
	/**
	 * ??????OA????????????
	 */
	public final static ArrayList<Option> APPLICATION_STATUS;

	/**
	 * ????????????(??????????????????)
	 */
	public final static ArrayList<Option> MYREPORT_TYPE;

	/**
	 * ????????????(??????????????????)
	 */
	public final static ArrayList<Option> MYREPORT_PART_TYPE;

	/**
	 * ????????????(??????OA????????????)
	 */
	public final static ArrayList<Option> MYREPORT_FOR_MOA;
	
	/**
	 * ?????????????????????
	 */
	public final static ArrayList<Option> MERCHANDISE_NPACKAG;
	/**
	 * ?????????????????????
	 */
	public final static ArrayList<Option> MERCHANDISE_NOHASW_NPACKAG;
	/**
	 * ?????????????????????
	 */
	public final static ArrayList<Option> MERCHANDISE_WPACKAG;
	/**
	 * ?????????????????????
	 */
	public final static ArrayList<Option> MERCHANDISE_NOHASW_WPACKAG;
	/**
	 * ?????????????????????
	 */
	public final static ArrayList<Option> SUPPLIER_ATTACHMENT_TYPE;

	/**
	 * ?????????????????????
	 */
	public final static ArrayList<Option> ACCESSORY_DETAIL_TYPE;

	/**
	 * ????????????
	 */
	public final static ArrayList<Option> ELSECOST;

	/**
	 * ??????????????????
	 */
	public final static ArrayList<Option> IMPORTRELEVANTCOST;

	/**
	 * ??????
	 */
	public final static ArrayList<Option> DEVELOPMENT;

	/**
	 * ????????????
	 */
	public final static ArrayList<Option> QUOTEDCURRENCY;

	/**
	 * ????????????
	 */
	public final static ArrayList<Option> REMIND;

	/**
	 * ????????????
	 */
	public final static ArrayList<Option> WARNTYPE_W;

	/**
	 * ????????????(???"???")
	 */
	public final static ArrayList<Option> WARNTYPE;

	/**
	 * ?????????-????????????
	 */
	public final static ArrayList<Option> FREIGHTTYPE;

	/**
	 * ?????????-????????????
	 */
	public final static ArrayList<Option> CONTAINERTYPE;

	/**
	 * ?????????-???????????????
	 */
	public final static ArrayList<Option> FACTORYPRICETYPE;

	/**
	 * ???/???
	 */
	public final static ArrayList<Option> YESNO;
	/**
	 * ??????????????????
	 */
	public final static ArrayList<Option> FORETASTE_TYPE;

	/**
	 * ??????????????????
	 */
	public final static ArrayList<Option> VISIT_APPLICATION_STATUS;
	
	/**
	 * ??????OA???????????????
	 */
	public final static ArrayList<Option> MERCHANDISE_APPLICATION_STATUS;

	static {

		EvaluateTemplateMarkDepartments[] departments = BusinessConstants.EvaluateTemplateMarkDepartments.values();
		DEPARTMENTS = new ArrayList<Option>();
		for (EvaluateTemplateMarkDepartments emd : departments) {
			DEPARTMENTS.add(new Option(emd.toString(), emd.getTypeName()));
		}

		EvaluateTemplateStatus[] evaluateTemplateStatus = BusinessConstants.EvaluateTemplateStatus.values();
		EVALUATE_TEMPLATE_STATUS = new ArrayList<Option>();
		for (EvaluateTemplateStatus ets : evaluateTemplateStatus) {
			EVALUATE_TEMPLATE_STATUS.add(new Option(ets.toString(), ets.getTypeName()));
		}

		signedQuantityType[] signedQuantity = BusinessConstants.signedQuantityType.values();
		SINGED_QTY_LIST = new ArrayList<Option>();
		for (signedQuantityType sq : signedQuantity) {
			SINGED_QTY_LIST.add(new Option(sq.toString(), sq.getStatusName()));
		}

		ProcurementDepartments[] procurementDepartments = BusinessConstants.ProcurementDepartments.values();
		PROCUREMENT_DEPARTMENTS = new ArrayList<Option>();
		for (ProcurementDepartments pd : procurementDepartments) {
			PROCUREMENT_DEPARTMENTS.add(new Option(pd.toString(), pd.getDepartmentsName()));
		}

		MaterialType[] materialType = BusinessConstants.MaterialType.values();
		MATERIAL_TYPE = new ArrayList<Option>();
		for (MaterialType mt : materialType) {
			MATERIAL_TYPE.add(new Option(mt.toString(), mt.getTypeName()));
		}

		Rationed[] rationed = BusinessConstants.Rationed.values();
		RATIONED = new ArrayList<Option>();
		for (Rationed r : rationed) {
			RATIONED.add(new Option(r.toString(), r.getRationedName()));
		}

		PurchaseType[] purchaseType = BusinessConstants.PurchaseType.values();
		PURCHASE_TYPE = new ArrayList<Option>();
		for (PurchaseType pt : purchaseType) {
			PURCHASE_TYPE.add(new Option(pt.toString(), pt.getTypeName()));
		}

		ApplicationStatus[] applicationStatus = BusinessConstants.ApplicationStatus.values();
		APPLICATION_STATUS = new ArrayList<Option>();
		for (ApplicationStatus as : applicationStatus) {
			if(!"BH".equals(as.toString()))
			APPLICATION_STATUS.add(new Option(as.toString(), as.getStatusName()));
		}

		myReportType[] myReports = BusinessConstants.myReportType.values();
		MYREPORT_TYPE = new ArrayList<Option>();
		//??????OA????????????
		MYREPORT_FOR_MOA = new ArrayList<Option>();
		for (myReportType myReport : myReports) {
			Option ot = new Option(myReport.toString(), myReport.getTypeName());
			MYREPORT_TYPE.add(ot);
			if (!"FQA".equalsIgnoreCase(myReport.toString())
					&& !"FCA".equalsIgnoreCase(myReport.toString())
					&& !"FSP".equalsIgnoreCase(myReport.toString())) {
				MYREPORT_FOR_MOA.add(ot);
			}
		}
		
		//??????????????????
		MYREPORT_PART_TYPE = new ArrayList<Option>();
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.FQA.toString(), 
				BusinessConstants.myReportType.FQA.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.FCA.toString(), 
				BusinessConstants.myReportType.FCA.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.FSP.toString(), 
				BusinessConstants.myReportType.FSP.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.SCA.toString(), 
				BusinessConstants.myReportType.SCA.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.SMS.toString(), 
				BusinessConstants.myReportType.SMS.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.SDT.toString(), 
				BusinessConstants.myReportType.SDT.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.PSH.toString(), 
				BusinessConstants.myReportType.PSH.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.MPT.toString(), 
				BusinessConstants.myReportType.MPT.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.STD.toString(), 
				BusinessConstants.myReportType.STD.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.LTP.toString(), 
				BusinessConstants.myReportType.LTP.getTypeName()));
		MYREPORT_PART_TYPE.add(new Option(BusinessConstants.myReportType.SSA.toString(), 
				BusinessConstants.myReportType.SSA.getTypeName()));
		
		MerchandiseNPackag[] merchandiseNPackag = BusinessConstants.MerchandiseNPackag.values();
		MERCHANDISE_NPACKAG = new ArrayList<Option>();
		for (MerchandiseNPackag mn : merchandiseNPackag) {
			MERCHANDISE_NPACKAG.add(new Option(mn.toString(), mn.getTypeName()));
		}
		
		MERCHANDISE_NOHASW_NPACKAG = new ArrayList<Option>();
		for (MerchandiseNPackag mn : merchandiseNPackag) {
			if (!mn.toString().equals("W")) {
				MERCHANDISE_NOHASW_NPACKAG.add(new Option(mn.toString(), mn.getTypeName()));
			}
		}

		MerchandiseWPackag[] merchandiseWPackag = BusinessConstants.MerchandiseWPackag.values();
		MERCHANDISE_WPACKAG = new ArrayList<Option>();
		for (MerchandiseWPackag mw : merchandiseWPackag) {
			MERCHANDISE_WPACKAG.add(new Option(mw.toString(), mw.getTypeName()));
		}
		
		MERCHANDISE_NOHASW_WPACKAG = new ArrayList<Option>();
		for (MerchandiseWPackag mn : merchandiseWPackag) {
			if (!mn.toString().equals("W")) {
				MERCHANDISE_NOHASW_WPACKAG.add(new Option(mn.toString(), mn.getTypeName()));
			}
		}

		SupplierAttachmentType[] supplierAttachmentType = BusinessConstants.SupplierAttachmentType.values();
		SUPPLIER_ATTACHMENT_TYPE = new ArrayList<Option>();
		for (SupplierAttachmentType sat : supplierAttachmentType) {
			SUPPLIER_ATTACHMENT_TYPE.add(new Option(sat.toString(), sat.getTypeName()));
		}
		AccessoryDetailType[] accessDetTypes = BusinessConstants.AccessoryDetailType.values();
		ACCESSORY_DETAIL_TYPE = new ArrayList<Option>();
		for (AccessoryDetailType accDetType : accessDetTypes) {
			ACCESSORY_DETAIL_TYPE.add(new Option(accDetType.toString(), accDetType.getTypeName()));
		}

		ElseCost[] elseCost = BusinessConstants.ElseCost.values();
		ELSECOST = new ArrayList<Option>();
		for (ElseCost ec : elseCost) {
			ELSECOST.add(new Option(ec.toString(), ec.getElseCostName()));
		}

		ImportRelevantCost[] importRelevantCost = BusinessConstants.ImportRelevantCost.values();
		IMPORTRELEVANTCOST = new ArrayList<Option>();
		for (ImportRelevantCost importCost : importRelevantCost) {
			IMPORTRELEVANTCOST.add(new Option(importCost.toString(), importCost.getImportName()));
		}

		Development[] developmentList = BusinessConstants.Development.values();
		DEVELOPMENT = new ArrayList<Option>();
		for (Development development : developmentList) {
			DEVELOPMENT.add(new Option(development.getDevelopmentName(), development.getDevelopmentName()));
		}

		QuotedCurrency[] quotedCurrencyList = BusinessConstants.QuotedCurrency.values();
		QUOTEDCURRENCY = new ArrayList<Option>();
		for (QuotedCurrency quotedCurrency : quotedCurrencyList) {
			QUOTEDCURRENCY.add(new Option(quotedCurrency.getQuotedCurrencyName(), quotedCurrency
					.getQuotedCurrencyName()));
		}

		Remind[] remindList = BusinessConstants.Remind.values();
		REMIND = new ArrayList<Option>();
		for (Remind remind : remindList) {
			if (remind.toString() != BusinessConstants.Remind.YLJGHBYJ.toString()
					&& remind.toString() != BusinessConstants.Remind.YLJGTBYJ.toString()) {
				REMIND.add(new Option(remind.toString(), remind.getRemindName()));
			}
		}

		WARNTYPE = new ArrayList<Option>();
		WARNTYPE.add(new Option(BusinessConstants.Remind.YLJGHBYJ.toString(), BusinessConstants.Remind.YLJGHBYJ
				.getRemindName()));
		WARNTYPE.add(new Option(BusinessConstants.Remind.YLJGTBYJ.toString(), BusinessConstants.Remind.YLJGTBYJ
				.getRemindName()));
		WARNTYPE.add(new Option(BusinessConstants.Remind.SPJGYJ.toString(), BusinessConstants.Remind.SPJGYJ
				.getRemindName()));
		WARNTYPE.add(new Option(BusinessConstants.Remind.FLYXPJGYJ.toString(), BusinessConstants.Remind.FLYXPJGYJ
				.getRemindName()));

		WARNTYPE_W = new ArrayList<Option>();
		WARNTYPE_W.add(0, new Option("W", "???"));
		WARNTYPE_W.add(new Option(BusinessConstants.Remind.YLJGHBYJ.toString(), BusinessConstants.Remind.YLJGHBYJ
				.getRemindName()));
		WARNTYPE_W.add(new Option(BusinessConstants.Remind.YLJGTBYJ.toString(), BusinessConstants.Remind.YLJGTBYJ
				.getRemindName()));
		WARNTYPE_W.add(new Option(BusinessConstants.Remind.SPJGYJ.toString(), BusinessConstants.Remind.SPJGYJ
				.getRemindName()));
		WARNTYPE_W.add(new Option(BusinessConstants.Remind.FLYXPJGYJ.toString(), BusinessConstants.Remind.FLYXPJGYJ
				.getRemindName()));

		FreightType[] freightTypeList = BusinessConstants.FreightType.values();
		FREIGHTTYPE = new ArrayList<Option>();
		for (FreightType freightType : freightTypeList) {
			FREIGHTTYPE.add(new Option(freightType.toString(), freightType.getFreightTypeName()));
		}

		ContainerType[] containerTypeList = BusinessConstants.ContainerType.values();
		CONTAINERTYPE = new ArrayList<Option>();
		for (ContainerType containerType : containerTypeList) {
			CONTAINERTYPE.add(new Option(containerType.toString(), containerType.getContainerTypeName()));
		}

		YesNo[] yesNoList = BusinessConstants.YesNo.values();
		YESNO = new ArrayList<Option>();
		for (YesNo yesNo : yesNoList) {
			YESNO.add(new Option(yesNo.toString(), yesNo.getYesNoName()));
		}

		FactoryPriceType[] factoryPriceTypeList = BusinessConstants.FactoryPriceType.values();
		FACTORYPRICETYPE = new ArrayList<Option>();
		for (FactoryPriceType factoryPriceType : factoryPriceTypeList) {
			FACTORYPRICETYPE.add(new Option(factoryPriceType.toString(), factoryPriceType.getFactoryPriceTypeName()));
		}
		
		ForetasteType[] foretasteTypeList = BusinessConstants.ForetasteType.values();
		FORETASTE_TYPE = new ArrayList<Option>();
		for (ForetasteType foretasteType : foretasteTypeList) {
			FORETASTE_TYPE.add(new Option(foretasteType.toString(), foretasteType.getForetasteTypeName()));
		}
		
		VisitApplicationStatus[] visitApplicationStatusList = BusinessConstants.VisitApplicationStatus.values();
		VISIT_APPLICATION_STATUS = new ArrayList<Option>();
		for (VisitApplicationStatus visitApplicationStatus : visitApplicationStatusList) {
			VISIT_APPLICATION_STATUS.add(new Option(visitApplicationStatus.toString(), visitApplicationStatus.getStatusName()));
		}
		
		MerchandiseApplicationStatus[] merchandiseApplicationStatusList = BusinessConstants.MerchandiseApplicationStatus.values();
		MERCHANDISE_APPLICATION_STATUS = new ArrayList<Option>();
		for (MerchandiseApplicationStatus merchandiseApplicationStatus : merchandiseApplicationStatusList) {
			MERCHANDISE_APPLICATION_STATUS.add(new Option(merchandiseApplicationStatus.toString(), merchandiseApplicationStatus.getStatusName()));
		}
	}
}
