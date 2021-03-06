package com.powere2e.sco.action.merchandiseoaapplication.reportnewoaapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.google.gson.Gson;
import com.lowagie.text.pdf.PushbuttonField;
import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.config.ConfigPath;
import com.powere2e.frame.commons.power.Authority;
import com.powere2e.frame.web.action.WorkAction;
import com.powere2e.sco.common.service.BusinessConstants;
import com.powere2e.sco.common.service.merchandiseoaapplication.MerchandiseOaApplicationUtil;
import com.powere2e.sco.common.utils.Constant;
import com.powere2e.sco.common.utils.DateUtils;
import com.powere2e.sco.interfaces.service.common.MasterDataTypeService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.MerchandiseOaApplicationService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.ApplicationReportNewService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.reportanalysis.AnalysisReportNewService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.reportnew.MerchandisePriceNewService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.supplierattachment.SupplierAttachmentMService;
import com.powere2e.sco.model.merchandiseintention.MerchandiseIntention;
import com.powere2e.sco.model.merchandiseintention.MerchandiseQuoted;
import com.powere2e.sco.model.merchandiseoaapplication.ApplicationLackFileM;
import com.powere2e.sco.model.merchandiseoaapplication.ApplicationMerchandise;
import com.powere2e.sco.model.merchandiseoaapplication.MerchandiseOaApplication;
import com.powere2e.sco.model.merchandiseoaapplication.reportadjustpriceoaapplication.reportadjustprice.MerchandiseMaterial;
import com.powere2e.sco.model.merchandiseoaapplication.reportnewoaapplication.ApplicationReportNew;
import com.powere2e.sco.model.merchandiseoaapplication.reportnewoaapplication.reportnew.MerchandisePriceNew;
import com.powere2e.sco.model.merchandiseoaapplication.reportnewoaapplication.reportnew.SameMerchandiseNew;
import com.powere2e.sco.model.merchandiseoaapplication.reportnewoaapplication.reportnew.SellAnticipatedNew;
import com.powere2e.sco.service.impl.merchandiseintention.MerchandiseQuotedServiceImpl;
import com.powere2e.security.utils.PowerUtils;

import net.sf.json.JSONArray;

/**
 * ????????????(????????????)???WEB???????????????
 * 
 * @author Joyce.Li
 * @version 1.0
 * @since 2015???4???20???
 */
public class ApplicationReportNewAction extends WorkAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4779328921886632598L;
	private ApplicationReportNewService reportNewService;// ???????????????service
	private MerchandisePriceNewService priceNewService;// ?????????service
	private MerchandiseOaApplicationService merchandiseOaApplicationService;// oa?????????service
	private SupplierAttachmentMService supplierAttachmentMService;// ???????????????????????????service
	private AnalysisReportNewService analysisReportNewService;// ???????????????service
	private MasterDataTypeService masterDataTypeService;// ????????????id???service
	private List<ApplicationReportNew> applicationList = new ArrayList<ApplicationReportNew>();// ??????????????????
	private List<ApplicationLackFileM> lackFileList = new ArrayList<ApplicationLackFileM>();// ???????????????????????????
	private String priceRowsList;
	private String sameRowsList;
	private String sellRowsList;
	private String materialRowsList;
	
	/**
	 * ???????????????????????????
	 */
	@Override
	protected void beforeBuild() {
		reportNewService = (ApplicationReportNewService) ConfigFactory.getInstance().getBean("reportNewService");
		priceNewService = (MerchandisePriceNewService) ConfigFactory.getInstance().getBean("priceNewService");
		merchandiseOaApplicationService = (MerchandiseOaApplicationService) ConfigFactory.getInstance().getBean(
				"merchandiseOaApplicationService");
		supplierAttachmentMService = (SupplierAttachmentMService) ConfigFactory.getInstance().getBean(
				"supplierAttachmentMService");
		analysisReportNewService = (AnalysisReportNewService) ConfigFactory.getInstance().getBean(
				"analysisReportNewService");
		masterDataTypeService = (MasterDataTypeService) ConfigFactory.getInstance().getBean("masterDataTypeService");
	}

	/**
	 * ??????????????????OA????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowApplicationReportNewGrid() throws Exception {
		this.forwardPage("sco/merchandiseOaApplication/reportNewOaApplication/applicationReportNewGrid.ftl");
	}

	/**
	 * ????????????OA?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListMerchandiseIntentionApplication() throws Exception {
		Map<String, Object> map = getMerchandiseIntention().toMap();
		String createDateStart = this.asString("createDateStart");
		String createDateEnd = this.asString("createDateEnd");
		String applicationDateStart = this.asString("applicationDateStart");
		String applicationDateEnd = this.asString("applicationDateEnd");
		// ???????????????????????????
		String visitDateStart = this.asString("visitDateStart");
		String visitDateEnd = this.asString("visitDateEnd");
		// ??????????????????????????????
		String packageDateStart = this.asString("packageDateStart");
		String packageDateEnd = this.asString("packageDateEnd");

		map.put("createDateStart", createDateStart);
		map.put("createDateEnd", createDateEnd);
		map.put("applicationDateStart", applicationDateStart);
		map.put("applicationDateEnd", applicationDateEnd);

		map.put("visitDateStart", visitDateStart);
		map.put("visitDateEnd", visitDateEnd);
		map.put("packageDateStart", packageDateStart);
		map.put("packageDateEnd", packageDateEnd);

		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_NEW.toString());// ????????????????????????????????????????????????
		//??????"????????????"??????????????????????????????"??????????????????"
		map.put("notExistsApplicationType", BusinessConstants.ApplicationType.MERCHANDISE_FASTIMPORT.toString());
		List<MerchandiseIntention> list = merchandiseOaApplicationService.queryIntentionApplicationList(map,
				this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowInsertApplicationReportNewForm() throws Exception {
		ApplicationReportNew applicationReportNew = new ApplicationReportNew();
		// ???????????????OA????????????
		String applicationCode = "OA".concat(masterDataTypeService.nextID("S_OA_APPLICATION"));
		// ???????????????????????????????????????????????????????????????
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");
		applicationReportNew.setApplicationCode(applicationCode);

		applicationList = reportNewService.listApplicationReportNew(applicationCode, intentionAndSupplierCodes);
		this.putObject("applicationReportNew", applicationReportNew);
		this.putObject("applicationCode", applicationCode);
		this.putObject("intentionAndSupplierCodes", intentionAndSupplierCodes);
		this.putObject("applicationList", applicationList);
		this.forwardPage("sco/merchandiseOaApplication/reportNewOaApplication/reportNewPanel.ftl");
	}

	/**
	 * ??????????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowUpdateApplicationReportNewForm() throws Exception {
		String applicationCode = this.asString("applicationCode");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applicationCode", applicationCode);

		String intentionAndSupplierCodes = "";
		List<ApplicationMerchandise> codeList = merchandiseOaApplicationService
				.findMerchandiseCodeAndSupplierCodeByApplicationCode(map);
		if (codeList != null && codeList.size() > 0) {
			for (int i = 0; i < codeList.size(); i++) {
				ApplicationMerchandise eachMerchandise = codeList.get(i);
				if (i < codeList.size() - 1) {
					intentionAndSupplierCodes += eachMerchandise.getMerchandiseCode() + ":"
							+ eachMerchandise.getSupplierCode() + ",";
				} else if (i == codeList.size() - 1) {
					intentionAndSupplierCodes += eachMerchandise.getMerchandiseCode() + ":"
							+ eachMerchandise.getSupplierCode();
				}
			}
		}

		applicationList = reportNewService.listApplicationReportNew(applicationCode, intentionAndSupplierCodes);
		// this.putObject("applicationReportNew", applicationReportNew);
		this.putObject("applicationCode", applicationCode);
		this.putObject("intentionAndSupplierCodes", intentionAndSupplierCodes);
		this.putObject("applicationList", applicationList);
		this.forwardPage("sco/merchandiseOaApplication/reportNewOaApplication/reportNewPanel.ftl");
	}

	/**
	 * ??????????????????(????????????)
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationReportNew() throws Exception {
		String applicationCode = this.asString("applicationCode");
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");
		List<ApplicationMerchandise> list = MerchandiseOaApplicationUtil
				.getIntentionAndSupplierCodeGroupList(intentionAndSupplierCodes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_NEW.toString());// ????????????
		List<ApplicationMerchandise> appList = merchandiseOaApplicationService.listIntentionOaApplication(map);
		if (appList != null && appList.size() > 0) {
			for (ApplicationMerchandise merchandise : appList) {
				if (!BusinessConstants.MerchandiseApplicationStatus.CG.toString().equals(
						merchandise.getApplicationStatus())
						&& !BusinessConstants.MerchandiseApplicationStatus.BH.toString().equals(
								merchandise.getApplicationStatus())) {
					//????????????????????????????????????"??????"???"??????"??????????????????????????????
					this.forwardData(false, null, "?????????????????????????????????????????????????????????,????????????!");
				}
				if(merchandise.getCreateby()!=null&&!PowerUtils.getCurrentUser().getLoginName().equalsIgnoreCase(merchandise.getCreateby())){
					this.forwardData(false, null, "??????????????????,????????????!");
					return;
				}
			}
		}
		Gson gson = new Gson();
		if (applicationList != null && applicationList.size() > 0) {
			JSONArray jsonArr = JSONArray.fromObject("[" + priceRowsList + "]");
			Object priceObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + sameRowsList + "]");
			Object sameObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + sellRowsList + "]");
			Object sellObjArr[] = jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+materialRowsList+"]");
			Object materialObjArr[]=jsonArr.toArray();
			
			
			for (int i = 0; i < applicationList.size(); i++) {
				List<ApplicationReportNew> applicationListNew = new ArrayList<ApplicationReportNew>();
				if (priceRowsList != null) {
					MerchandisePriceNew priceArr[] = gson.fromJson(priceObjArr[i].toString(),
							MerchandisePriceNew[].class);
					if (priceArr != null) {
						applicationList.get(i).setPriceArr(priceArr);
					}
				}
				if (sameRowsList != null) {
					SameMerchandiseNew sameArr[] = gson.fromJson(sameObjArr[i].toString(), SameMerchandiseNew[].class);
					if (sameArr != null) {
						applicationList.get(i).setSameArr(sameArr);
					}
				}
				if (sellRowsList != null) {
					SellAnticipatedNew sellArr[] = gson.fromJson(sellObjArr[i].toString(), SellAnticipatedNew[].class);
					if (sellArr != null) {
						applicationList.get(i).setSellArr(sellArr);
					}
				}
				
				if (materialRowsList != null) {
					MerchandiseMaterial materialArr[] = gson.fromJson(materialObjArr[i].toString(), MerchandiseMaterial[].class);
					if (materialArr != null) {
						applicationList.get(i).setMaterialArr(materialArr);
					}

				}
				applicationListNew.add(applicationList.get(i));
				//???????????????html
				String path =priceNewService.saveApplicationReportNewToHtml("applicationReportNew_"+applicationCode+"_"+(i+1), applicationListNew);
				applicationList.get(i).setPath(path);
			}
		}
		String receiptInfo = reportNewService.insertApplicationReportNew(applicationList, applicationCode,
				intentionAndSupplierCodes);
		if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.NONE.toString().equals(receiptInfo)
				|| MerchandiseOaApplicationUtil.ReportNewOaApplicationState.SAME_CG.toString().equals(receiptInfo)) {
			
			for(ApplicationReportNew applicationReportNew:applicationList){
				applicationReportNew.setPath(applicationReportNew.getPath().replace(ConfigPath.getUploadFilePath(), ""));
				reportNewService.updateApplicationReportNew(applicationReportNew);
			}
			// ?????????????????????????????????
			applicationList = reportNewService.listApplicationReportNew(applicationCode, intentionAndSupplierCodes);
			this.putObject("applicationList", applicationList);
			this.forwardData(true, null, this.getText("public.success"));
		} else if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.DIFFER.toString().equals(receiptInfo)) {
			this.forwardData(false, null, "??????????????????????????????OA???????????????,??????????????????!");
		} else {
			this.forwardData(false, null, "?????????????????????????????????????????????????????????,???????????????");
		}
	}
	
	/**
	 * ??????????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationReportNewCG() throws Exception {
		String applicationCode = this.asString("applicationCode");
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");
		List<ApplicationMerchandise> list = MerchandiseOaApplicationUtil
				.getIntentionAndSupplierCodeGroupList(intentionAndSupplierCodes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_NEW.toString());// ????????????
		List<ApplicationMerchandise> appList = merchandiseOaApplicationService.listIntentionOaApplication(map);
		if (appList != null && appList.size() > 0) {
			for (ApplicationMerchandise merchandise : appList) {
				if (!BusinessConstants.MerchandiseApplicationStatus.CG.toString().equals(
						merchandise.getApplicationStatus())
						&& !BusinessConstants.MerchandiseApplicationStatus.BH.toString().equals(
								merchandise.getApplicationStatus())) {
					//????????????????????????????????????"??????"???"??????"??????????????????????????????
					this.forwardData(false, null, "?????????????????????????????????????????????????????????,????????????!");
				}
				if(merchandise.getCreateby()!=null&&!PowerUtils.getCurrentUser().getLoginName().equalsIgnoreCase(merchandise.getCreateby())){
					this.forwardData(false, null, "??????????????????,????????????!");
					return;
				}
			}
		}
		Gson gson = new Gson();
		if (applicationList != null && applicationList.size() > 0) {
			JSONArray jsonArr = JSONArray.fromObject("[" + priceRowsList + "]");
			Object priceObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + sameRowsList + "]");
			Object sameObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + sellRowsList + "]");
			Object sellObjArr[] = jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+materialRowsList+"]");
			Object materialObjArr[]=jsonArr.toArray();
			
			
			for (int i = 0; i < applicationList.size(); i++) {
				try {
					List<ApplicationReportNew> applicationListNew = new ArrayList<ApplicationReportNew>();
					if (priceRowsList != null) {
						MerchandisePriceNew priceArr[] = gson.fromJson(priceObjArr[i].toString(),
								MerchandisePriceNew[].class);
						if (priceArr != null) {
							applicationList.get(i).setPriceArr(priceArr);
						}
					}
					if (sameRowsList != null) {
						SameMerchandiseNew sameArr[] = gson.fromJson(sameObjArr[i].toString(), SameMerchandiseNew[].class);
						if (sameArr != null) {
							applicationList.get(i).setSameArr(sameArr);
						}
					}
					if (sellRowsList != null) {
						SellAnticipatedNew sellArr[] = gson.fromJson(sellObjArr[i].toString(), SellAnticipatedNew[].class);
						if (sellArr != null) {
							applicationList.get(i).setSellArr(sellArr);
						}
					}
					
					if (materialRowsList != null) {
						MerchandiseMaterial materialArr[] = gson.fromJson(materialObjArr[i].toString(), MerchandiseMaterial[].class);
						if (materialArr != null) {
							applicationList.get(i).setMaterialArr(materialArr);
						}

					}
					applicationListNew.add(applicationList.get(i));
					//???????????????html
					/*String path =priceNewService.saveApplicationReportNewToHtml("applicationReportNew_"+applicationCode+"_"+(i+1), applicationListNew);
					applicationList.get(i).setPath(path);*/
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		String receiptInfo = reportNewService.insertApplicationReportNew(applicationList, applicationCode,
				intentionAndSupplierCodes);
		if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.NONE.toString().equals(receiptInfo)
				|| MerchandiseOaApplicationUtil.ReportNewOaApplicationState.SAME_CG.toString().equals(receiptInfo)) {
			
			for(ApplicationReportNew applicationReportNew:applicationList){
			//	applicationReportNew.setPath(applicationReportNew.getPath().replace(ConfigPath.getUploadFilePath(), ""));
				reportNewService.updateApplicationReportNew(applicationReportNew);
			}
			// ?????????????????????????????????
			applicationList = reportNewService.listApplicationReportNew(applicationCode, intentionAndSupplierCodes);
			this.putObject("applicationList", applicationList);
			this.forwardData(true, null, this.getText("public.success"));
		} else if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.DIFFER.toString().equals(receiptInfo)) {
			this.forwardData(false, null, "??????????????????????????????OA???????????????,??????????????????!");
		} else {
			this.forwardData(false, null, "?????????????????????????????????????????????????????????,???????????????");
		}
	}

	/**
	 * ????????????(????????????)??????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListMerchandisePriceNew() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<MerchandisePriceNew> list = priceNewService.listMerchandisePriceNew(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}
	
	/**
	 * ?????????????????????(??????)??????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListMerchandiseMaterialPriceNew() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<MerchandiseMaterial> list = priceNewService.listMerchandiseMaterial(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????(????????????)??????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListSameMerchandiseNew() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<SameMerchandiseNew> list = priceNewService.listSameMerchandiseNew(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????(????????????)??????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListSellAnticipatedNew() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<SellAnticipatedNew> list = priceNewService.listSellAnticipatedNew(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????(????????????)
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doDeleteApplicationReportNew() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applicationCodes", this.asString("applicationCodes"));
		reportNewService.completeDeleteReportNewByApplicationCode(map);

		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ????????????????????????????????????????????????
	 */
	private MerchandiseIntention getMerchandiseIntention() throws Exception {
		MerchandiseIntention merchandiseIntention = new MerchandiseIntention();
		this.asBean(merchandiseIntention);
		return merchandiseIntention;
	}

	/**
	 * ???????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doExportReportNewApplicationToExcel() throws Exception {
		ServletOutputStream out = response.getOutputStream();
		String fileName = ("??????????????????OA????????????_").concat(DateUtils.formateDateTime()).concat(".xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(Constant.DEFAULT_ENCODED_1), Constant.DEFAULT_ENCODED_2) + "\"");
		
		Map<String, Object> map = getMerchandiseIntention().toMap();
		String createDateStart = this.asString("createDateStart");
		String createDateEnd = this.asString("createDateEnd");
		String applicationDateStart = this.asString("applicationDateStart");
		String applicationDateEnd = this.asString("applicationDateEnd");

		// ???????????????????????????
		String visitDateStart = this.asString("visitDateStart");
		String visitDateEnd = this.asString("visitDateEnd");
		// ??????????????????????????????
		String packageDateStart = this.asString("packageDateStart");
		String packageDateEnd = this.asString("packageDateEnd");

		map.put("createDateStart", createDateStart);
		map.put("createDateEnd", createDateEnd);
		map.put("applicationDateStart", applicationDateStart);
		map.put("applicationDateEnd", applicationDateEnd);

		map.put("visitDateStart", visitDateStart);
		map.put("visitDateEnd", visitDateEnd);
		map.put("packageDateStart", packageDateStart);
		map.put("packageDateEnd", packageDateEnd);

		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_NEW.toString());// ????????????????????????????????????????????????
		map.put("exportNew", this.asString("exportNew"));//???????????????????????????:???OA??????????????????
		
		//??????"????????????"??????????????????????????????"??????????????????"
		map.put("notExistsApplicationType", BusinessConstants.ApplicationType.MERCHANDISE_FASTIMPORT.toString());
		reportNewService.exportSignedQtyExcel(map, out);
		/*merchandiseOaApplicationService.queryIntentionApplicationList(map, null);
		this.putObject("applicationList", list);
		this.forwardDownload("excel/sco/merchandiseoaapplication/reportNewOaApplicationTemplate.xlsx",
				ExcelUtils.getEncodeFileName("??????????????????OA????????????_" + DateUtils.formateDateTime() + ".xlsx"));
*/
	}

	/**
	 * ??????????????????????????????????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doSyncApplicationNew() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String applicationCode = this.asString("applicationCode");
		map.put("applicationCode", applicationCode);

		// ??????????????????????????????
		List<ApplicationLackFileM> list = reportNewService.queryNotHaveReportMerchandiseNew(map);
		if (list != null && list.size() > 0) {
			// ?????????????????????????????????OA?????????
			StringBuffer check = new StringBuffer();
			for (ApplicationLackFileM reportNew : list) {
				check.append("????????????:" + reportNew.getApplicationCode() + ",????????????:" + reportNew.getMerchandiseCode()
						+ ",???????????????:" + reportNew.getSupplierCode() + "<br/>");
			}
			this.forwardData(false, check.toString(), "??????????????????");
		} else {
			List<ApplicationLackFileM> attachmentList = supplierAttachmentMService.listAttachmentLackInfo(
					applicationCode, BusinessConstants.ApplicationType.MERCHANDISE_NEW.toString());// ??????????????????????????????????????????????????????
			List<ApplicationLackFileM> analysisReportList = analysisReportNewService
					.listNoLinkMCAReportApplication(applicationCode);// ??????????????????????????????
			if ((attachmentList == null || attachmentList.size() == 0)
					&& (analysisReportList == null || analysisReportList.size() == 0)) {
				// ?????????????????????OA????????????
				// map.put("applicationStatus",
				// BusinessConstants.ApplicationStatus.YX.toString());
				// merchandiseOaApplicationService.updateOaApplicationStatus(map);

				this.forwardData(true, null, this.getText("public.success"));
			} else {
				this.forwardData(false, null, "??????????????????");
			}

		}
	}

	/**
	 * ???????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowLackSuggestFileNew() throws Exception {
		String applicationCode = this.asString("applicationCode");
		List<ApplicationLackFileM> attachmentList = supplierAttachmentMService.listAttachmentLackInfo(applicationCode,
				BusinessConstants.ApplicationType.MERCHANDISE_NEW.toString()); // ??????????????????????????????
		List<ApplicationLackFileM> analysisReportList = analysisReportNewService
				.listNoLinkMCAReportApplication(applicationCode);// ???????????????????????????

		lackFileList.addAll(attachmentList);
		lackFileList.addAll(analysisReportList);

		this.putObject("applicationCode", applicationCode);
		this.putObject("lackFileList", lackFileList);
		this.forwardPage("sco/merchandiseOaApplication/reportNewOaApplication/lackSuggestFileNewGrid.ftl");
	}

	/**
	 * ???????????????????????????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationLackFileNew() throws Exception {
		if (lackFileList != null && lackFileList.size() > 0) {
			String applicationCode = this.asString("applicationCode");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", lackFileList);
			map.put("applicationCode", applicationCode);

			merchandiseOaApplicationService.insertApplicationLackFileM(map);
			this.forwardData(true, null, this.getText("public.success"));
		} else {
			this.forwardData(false, null, "??????????????????????????????!");
		}
	}

	/**
	 * ????????????OA??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doUndoApplicationNew() throws Exception {
		String applicationCodes = this.asString("applicationCodes");
		merchandiseOaApplicationService.completeUndoOaApplicationStatus(applicationCodes);

		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ??????OA?????????????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doFindApproveOpinion() throws Exception {
		String applicationCode = this.asString("applicationCode");
		MerchandiseOaApplication oaApplication = merchandiseOaApplicationService.findApproveOpinion(applicationCode);
		if (oaApplication != null) {
			this.forwardData(true, oaApplication.getOaApplicationCode() + ":" + oaApplication.getOaContacts(),
					this.getText("public.success"));
		} else {
			this.forwardData(false, null, "??????OA?????????????????????????????????OA????????????!");
		}
	}

	/**
	 * ????????????TBPM??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doCreateTbpmVisit() throws Exception {
		String codes = this.asString("codes");
		String applicationVfCode = masterDataTypeService.nextID("S_APPLICATION_VISIT_FACTORY");
		// ???????????????????????????
		merchandiseOaApplicationService.insertApplicationVisitFactory(applicationVfCode,codes);
		this.forwardData(true, null, applicationVfCode);//???????????????????????????????????????????????????????????????OA??????
	}

	/**
	 * ????????????TBPM??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doCreateTbpmPackage() throws Exception {
		String applicationCode = this.asString("applicationCode");
		String merchandiseCode = this.asString("merchandiseCode");
		String supplierCode = this.asString("supplierCode");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applicationCode", applicationCode);
		//???????????????????????????????????????????????????
//		map.put("applicationStatus", BusinessConstants.VisitApplicationStatus.SPZ.toString());
		map.put("merchandiseCode", merchandiseCode);
		map.put("supplierCode", supplierCode);

		String applicationPdCode = masterDataTypeService.nextID("S_APPLICATION_PACKAGE_DESIGN");
		map.put("applicationPdCode", applicationPdCode);// ????????????
		// ???????????????????????????
		merchandiseOaApplicationService.insertApplicationPackageDesign(map);
		MerchandiseQuoted mq = new MerchandiseQuoted(); 
		map.put("intentionSupplierCode", supplierCode);
		MerchandiseQuotedServiceImpl.getMerchandiseQuotedServiceInstance()
			.searchCompanyInfoInSapAndLastQuoted(supplierCode, mq, map);
		this.forwardData(true, mq, applicationPdCode);
	}

	public List<ApplicationReportNew> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List<ApplicationReportNew> applicationList) {
		this.applicationList = applicationList;
	}

	public List<ApplicationLackFileM> getLackFileList() {
		return lackFileList;
	}

	public void setLackFileList(List<ApplicationLackFileM> lackFileList) {
		this.lackFileList = lackFileList;
	}

	public String getPriceRowsList() {
		return priceRowsList;
	}

	public void setPriceRowsList(String priceRowsList) {
		this.priceRowsList = priceRowsList;
	}

	public String getSameRowsList() {
		return sameRowsList;
	}

	public void setSameRowsList(String sameRowsList) {
		this.sameRowsList = sameRowsList;
	}

	public String getSellRowsList() {
		return sellRowsList;
	}

	public void setSellRowsList(String sellRowsList) {
		this.sellRowsList = sellRowsList;
	}

	public String getMaterialRowsList() {
		return materialRowsList;
	}

	public void setMaterialRowsList(String materialRowsList) {
		this.materialRowsList = materialRowsList;
	}

}
