package com.powere2e.sco.action.merchandiseoaapplication.reportadjustpriceoaapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportadjustpriceoaapplication.ApplicationReportAdjustpriceService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportadjustpriceoaapplication.reportadjustprice.HistoryPriceAdjustpriceService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.reportanalysis.AnalysisReportNewService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.supplierattachment.SupplierAttachmentMService;
import com.powere2e.sco.model.masterdata.Merchandise;
import com.powere2e.sco.model.merchandiseintention.MerchandiseIntention;
import com.powere2e.sco.model.merchandiseoaapplication.ApplicationLackFileM;
import com.powere2e.sco.model.merchandiseoaapplication.ApplicationMerchandise;
import com.powere2e.sco.model.merchandiseoaapplication.reportadjustpriceoaapplication.ApplicationReportAdjustprice;
import com.powere2e.sco.model.merchandiseoaapplication.reportadjustpriceoaapplication.reportadjustprice.HistoryPriceAdjustprice;
import com.powere2e.sco.model.merchandiseoaapplication.reportadjustpriceoaapplication.reportadjustprice.MerchandiseMaterial;
import com.powere2e.sco.model.merchandiseoaapplication.reportadjustpriceoaapplication.reportadjustprice.SameMerchandiseAdjustprice;
import com.powere2e.security.utils.PowerUtils;

/**
 * ????????????(????????????)???WEB???????????????
 * 
 * @author Joyce.Li
 * @version 1.0
 * @since 2015???4???20???
 */
public class ApplicationReportAdjustpriceAction extends WorkAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1915300534646641923L;
	private ApplicationReportAdjustpriceService reportAdjustpriceService;// ???????????????service
	private HistoryPriceAdjustpriceService priceAdjustpriceService;// ?????????service
	private MerchandiseOaApplicationService merchandiseOaApplicationService;// oa?????????service
	private SupplierAttachmentMService supplierAttachmentMService;// ???????????????????????????service
	private AnalysisReportNewService analysisReportNewService;// ???????????????service
	private MasterDataTypeService masterDataTypeService;// ????????????id???service
	private List<ApplicationReportAdjustprice> applicationList = new ArrayList<ApplicationReportAdjustprice>();// ??????????????????
	private List<ApplicationLackFileM> lackFileList = new ArrayList<ApplicationLackFileM>();// ???????????????????????????
	private String oldPriceRowsList;
	private String nowPriceRowsList;
	private String sameRowsList;
	private String materialRowsList;

	/**
	 * ???????????????????????????
	 */
	@Override
	protected void beforeBuild() {
		reportAdjustpriceService = (ApplicationReportAdjustpriceService) ConfigFactory.getInstance().getBean("reportAdjustpriceService");
		priceAdjustpriceService = (HistoryPriceAdjustpriceService) ConfigFactory.getInstance().getBean("priceAdjustpriceService");
		merchandiseOaApplicationService = (MerchandiseOaApplicationService) ConfigFactory.getInstance().getBean("merchandiseOaApplicationService");
		supplierAttachmentMService = (SupplierAttachmentMService) ConfigFactory.getInstance().getBean("supplierAttachmentMService");
		analysisReportNewService = (AnalysisReportNewService) ConfigFactory.getInstance().getBean("analysisReportNewService");
		masterDataTypeService = (MasterDataTypeService) ConfigFactory.getInstance().getBean("masterDataTypeService");
	}

	/**
	 * ????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowApplicationReportAdjustpriceGrid() throws Exception {
		this.forwardPage("sco/merchandiseOaApplication/reportAdjustpriceOaApplication/applicationReportAdjustpriceGrid.ftl");
	}

	/**
	 * ????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListMerchandiseApplicationAdjustprice() throws Exception {
		Map<String, Object> map = getMerchandise().toMap();
		String applicationDateStart = this.asString("applicationDateStart");
		String applicationDateEnd = this.asString("applicationDateEnd");

		map.put("applicationDateStart", applicationDateStart);
		map.put("applicationDateEnd", applicationDateEnd);

		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_ADJUSTPRICE.toString());// ????????????????????????????????????????????????
		List<MerchandiseIntention> list = merchandiseOaApplicationService.queryMerchandiseApplicationList(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowInsertApplicationReportAdjustpriceForm() throws Exception {
		// ??????OA??????????????????
		String applicationCode = "OA".concat(masterDataTypeService.nextID("S_OA_APPLICATION"));
		// ???????????????????????????????????????????????????????????????
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");

		applicationList = reportAdjustpriceService.listApplicationReportAdjustprice(applicationCode, intentionAndSupplierCodes);
		this.putObject("applicationCode", applicationCode);
		this.putObject("intentionAndSupplierCodes", intentionAndSupplierCodes);
		this.putObject("applicationList", applicationList);
		this.forwardPage("sco/merchandiseOaApplication/reportAdjustpriceOaApplication/reportAdjustpricePanel.ftl");
	}

	/**
	 * ??????????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowUpdateApplicationReportAdjustpriceForm() throws Exception {
		String applicationCode = this.asString("applicationCode");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applicationCode", applicationCode);

		String intentionAndSupplierCodes = "";
		List<ApplicationMerchandise> codeList = merchandiseOaApplicationService.findMerchandiseCodeAndSupplierCodeByApplicationCode(map);
		if (codeList != null && codeList.size() > 0) {
			for (int i = 0; i < codeList.size(); i++) {
				ApplicationMerchandise eachMerchandise = codeList.get(i);
				if (i < codeList.size() - 1) {
					intentionAndSupplierCodes += eachMerchandise.getMerchandiseCode() + ":" + eachMerchandise.getSupplierCode() + ",";
				} else if (i == codeList.size() - 1) {
					intentionAndSupplierCodes += eachMerchandise.getMerchandiseCode() + ":" + eachMerchandise.getSupplierCode();
				}
			}
		}
		applicationList = reportAdjustpriceService.listApplicationReportAdjustprice(applicationCode, intentionAndSupplierCodes);

		this.putObject("applicationCode", applicationCode);
		this.putObject("intentionAndSupplierCodes", intentionAndSupplierCodes);
		this.putObject("applicationList", applicationList);

		this.forwardPage("sco/merchandiseOaApplication/reportAdjustpriceOaApplication/reportAdjustpricePanel.ftl");
	}

	/**
	 * ??????????????????(????????????)
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationReportAdjustprice() throws Exception {
		String applicationCode = this.asString("applicationCode");
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");
		//???????????????????????????
		List<ApplicationMerchandise> list = MerchandiseOaApplicationUtil
				.getIntentionAndSupplierCodeGroupList(intentionAndSupplierCodes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_ADJUSTPRICE.toString());// ????????????
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
				/*if(merchandise.getCreateby()!=null&&!PowerUtils.getCurrentUser().getLoginName().equalsIgnoreCase(merchandise.getCreateby())){
					this.forwardData(false, null, "??????????????????,????????????!");
					return;
				}*/
			}
		}
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		if (applicationList != null && applicationList.size() > 0) {
			JSONArray jsonArr=JSONArray.fromObject("["+oldPriceRowsList+"]");
			Object oldPriceObjArr[]=jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+nowPriceRowsList+"]");
			Object nowPriceObjArr[]=jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+sameRowsList+"]");
			Object sameObjArr[]=jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+materialRowsList+"]");
			Object materialObjArr[]=jsonArr.toArray();
			for (int i = 0; i < applicationList.size(); i++) {
				List<ApplicationReportAdjustprice> applicationReportAdjustprice = new ArrayList<ApplicationReportAdjustprice>();
				if (oldPriceRowsList != null) {
					HistoryPriceAdjustprice oldPriceArr[] = gson.fromJson(oldPriceObjArr[i].toString(), HistoryPriceAdjustprice[].class);
					if (oldPriceArr != null) {
						applicationList.get(i).setOldPriceArr(oldPriceArr);
					}
				}
				if (nowPriceRowsList != null) {
					HistoryPriceAdjustprice nowPriceArr[] = gson.fromJson(nowPriceObjArr[i].toString(), HistoryPriceAdjustprice[].class);
					if (nowPriceArr != null) {
						applicationList.get(i).setNowPriceArr(nowPriceArr);
					}

				}
				if (sameRowsList != null) {
					SameMerchandiseAdjustprice sameArr[] = gson.fromJson(sameObjArr[i].toString(), SameMerchandiseAdjustprice[].class);
					if (sameArr != null) {
						applicationList.get(i).setSameArr(sameArr);
					}

				}
				if (materialRowsList != null) {
					MerchandiseMaterial materialArr[] = gson.fromJson(materialObjArr[i].toString(), MerchandiseMaterial[].class);
					if (materialArr != null) {
						applicationList.get(i).setMaterialArr(materialArr);
					}

				}
				applicationReportAdjustprice.add(applicationList.get(i));
				//???????????????html
				String path = priceAdjustpriceService.saveApplicationReportAdjustpriceToHtml(
						"applicationReportAdjustprice" + applicationCode + "_" + (i + 1), applicationCode,
						applicationReportAdjustprice, intentionAndSupplierCodes);
				applicationList.get(i).setPath(path);
			}
		}
		String receiptInfo = reportAdjustpriceService.insertApplicationReportAdjustprice(applicationList, applicationCode, intentionAndSupplierCodes);
		if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.NONE.toString().equals(receiptInfo)
				|| MerchandiseOaApplicationUtil.ReportNewOaApplicationState.SAME_CG.toString().equals(receiptInfo)) {
			for(ApplicationReportAdjustprice applicationReportAdjustprice:applicationList){
				applicationReportAdjustprice.setPath(applicationReportAdjustprice.getPath().replace(ConfigPath.getUploadFilePath(), ""));
				reportAdjustpriceService.updateApplicationReportAdjustprice(applicationReportAdjustprice);
			}
			// ?????????????????????????????????
			applicationList = reportAdjustpriceService.listApplicationReportAdjustprice(applicationCode, intentionAndSupplierCodes);
			this.putObject("applicationList", applicationList);
			this.forwardData(true, applicationList, this.getText("public.success"));
		} else if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.DIFFER.toString().equals(receiptInfo)) {
			this.forwardData(false, null, "??????????????????????????????OA???????????????,??????????????????!");
		} else {
			this.forwardData(false, null, "?????????????????????????????????????????????????????????,????????????!");
		}
	}
	/**
	 * ??????????????????(????????????)
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationReportAdjustpriceCG() throws Exception {
		String applicationCode = this.asString("applicationCode");
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");
		//???????????????????????????
		List<ApplicationMerchandise> list = MerchandiseOaApplicationUtil
				.getIntentionAndSupplierCodeGroupList(intentionAndSupplierCodes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_ADJUSTPRICE.toString());// ????????????
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
			/*	if(merchandise.getCreateby()!=null&&!PowerUtils.getCurrentUser().getLoginName().equalsIgnoreCase(merchandise.getCreateby())){
					this.forwardData(false, null, "??????????????????,????????????!");
					return;
				}*/
			}
		}
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		if (applicationList != null && applicationList.size() > 0) {
			JSONArray jsonArr=JSONArray.fromObject("["+oldPriceRowsList+"]");
			Object oldPriceObjArr[]=jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+nowPriceRowsList+"]");
			Object nowPriceObjArr[]=jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+sameRowsList+"]");
			Object sameObjArr[]=jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+materialRowsList+"]");
			Object materialObjArr[]=jsonArr.toArray();
			for (int i = 0; i < applicationList.size(); i++) {
				try {
					List<ApplicationReportAdjustprice> applicationReportAdjustprice = new ArrayList<ApplicationReportAdjustprice>();
					if (oldPriceRowsList != null) {
						HistoryPriceAdjustprice oldPriceArr[] = gson.fromJson(oldPriceObjArr[i].toString(), HistoryPriceAdjustprice[].class);
						if (oldPriceArr != null) {
							applicationList.get(i).setOldPriceArr(oldPriceArr);
						}
					}
					if (nowPriceRowsList != null) {
						HistoryPriceAdjustprice nowPriceArr[] = gson.fromJson(nowPriceObjArr[i].toString(), HistoryPriceAdjustprice[].class);
						if (nowPriceArr != null) {
							applicationList.get(i).setNowPriceArr(nowPriceArr);
						}

					}
					if (sameRowsList != null) {
						SameMerchandiseAdjustprice sameArr[] = gson.fromJson(sameObjArr[i].toString(), SameMerchandiseAdjustprice[].class);
						if (sameArr != null) {
							applicationList.get(i).setSameArr(sameArr);
						}

					}
					if (materialRowsList != null) {
						MerchandiseMaterial materialArr[] = gson.fromJson(materialObjArr[i].toString(), MerchandiseMaterial[].class);
						if (materialArr != null) {
							applicationList.get(i).setMaterialArr(materialArr);
						}

					}
					applicationReportAdjustprice.add(applicationList.get(i));
					//???????????????html
					/*String path = priceAdjustpriceService.saveApplicationReportAdjustpriceToHtml(
							"applicationReportAdjustprice" + applicationCode + "_" + (i + 1), applicationCode,
							applicationReportAdjustprice, intentionAndSupplierCodes);
					applicationList.get(i).setPath(path);*/
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		String receiptInfo = reportAdjustpriceService.insertApplicationReportAdjustprice(applicationList, applicationCode, intentionAndSupplierCodes);
		if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.NONE.toString().equals(receiptInfo)
				|| MerchandiseOaApplicationUtil.ReportNewOaApplicationState.SAME_CG.toString().equals(receiptInfo)) {
			for(ApplicationReportAdjustprice applicationReportAdjustprice:applicationList){
			//	applicationReportAdjustprice.setPath(applicationReportAdjustprice.getPath().replace(ConfigPath.getUploadFilePath(), ""));
				reportAdjustpriceService.updateApplicationReportAdjustprice(applicationReportAdjustprice);
			}
			// ?????????????????????????????????
			applicationList = reportAdjustpriceService.listApplicationReportAdjustprice(applicationCode, intentionAndSupplierCodes);
			this.putObject("applicationList", applicationList);
			this.forwardData(true, applicationList, this.getText("public.success"));
		} else if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.DIFFER.toString().equals(receiptInfo)) {
			this.forwardData(false, null, "??????????????????????????????OA???????????????,??????????????????!");
		} else {
			this.forwardData(false, null, "?????????????????????????????????????????????????????????,????????????!");
		}
	}
	/**
	 * ??????????????????(????????????)
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doDeleteApplicationReportAdjustprice() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applicationCodes", this.asString("applicationCodes"));
		reportAdjustpriceService.deleteAllReportAdjustpriceByApplicationCode(map);

		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ???????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doExportReportAdjustpriceApplicationToExcel() throws Exception {
		ServletOutputStream out = response.getOutputStream();
		String fileName = ("??????????????????OA????????????_").concat(DateUtils.formateDateTime()).concat(".xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(Constant.DEFAULT_ENCODED_1), Constant.DEFAULT_ENCODED_2) + "\"");
		
		Map<String, Object> map = getMerchandise().toMap();
		String applicationDateStart = this.asString("applicationDateStart");
		String applicationDateEnd = this.asString("applicationDateEnd");

		map.put("applicationDateStart", applicationDateStart);
		map.put("applicationDateEnd", applicationDateEnd);

		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_ADJUSTPRICE.toString());// ????????????????????????????????????????????????
		reportAdjustpriceService.exportSignedQtyExcel(map, out);
		/*List<MerchandiseIntention> list = merchandiseOaApplicationService.queryMerchandiseApplicationList(map, null);
		this.putObject("applicationList", list);
		this.forwardDownload("excel/sco/merchandiseoaapplication/reportAdjustpriceOaApplicationTemplate.xlsx", ExcelUtils.getEncodeFileName("??????????????????OA????????????_" + DateUtils.formateDateTime() + ".xlsx"));*/
	}

	/**
	 * ?????????????????????(????????????)
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListHistoryPriceAdjustprice() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("piceType", this.asString("piceType"));
		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<HistoryPriceAdjustprice> list = priceAdjustpriceService.listHistoryPriceAdjustprice(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????????????????(????????????)
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListComparePriceAdjustprice() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<HistoryPriceAdjustprice> list = priceAdjustpriceService.listComparePriceAdjustprice(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ???????????????????????????(????????????)
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListSameMerchandiseAdjustprice() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<SameMerchandiseAdjustprice> list = priceAdjustpriceService.listSameMerchandiseAdjustprice(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ?????????????????????(??????)??????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListMerchandiseMaterialAdjustprice() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<MerchandiseMaterial> list = priceAdjustpriceService.listMerchandiseMaterial(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????????????????????????????
	 */
	private Merchandise getMerchandise() throws Exception {
		Merchandise merchandise = new Merchandise();
		this.asBean(merchandise);
		return merchandise;
	}

	/**
	 * ??????????????????????????????????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doSyncApplicationAdjustprice() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String applicationCode = this.asString("applicationCode");
		map.put("applicationCode", applicationCode);

		// ??????????????????????????????
		List<ApplicationLackFileM> list = reportAdjustpriceService.queryNotHaveReportMerchandiseAdjustprice(map);
		if (list != null && list.size() > 0) {
			// ?????????????????????????????????OA?????????
			StringBuffer check = new StringBuffer();
			for (ApplicationLackFileM reportNew : list) {
				check.append("????????????:" + reportNew.getApplicationCode() + ",????????????:" + reportNew.getMerchandiseCode() + ",???????????????:" + reportNew.getSupplierCode() + "<br/>");
			}
			this.forwardData(false, check.toString(), "??????????????????");
		} else {
			List<ApplicationLackFileM> attachmentList = supplierAttachmentMService.listAttachmentLackInfo(applicationCode,BusinessConstants.ApplicationType.MERCHANDISE_ADJUSTPRICE.toString());// ??????????????????????????????????????????????????????
			List<ApplicationLackFileM> analysisReportList = analysisReportNewService.listNoLinkMCAReportApplication(applicationCode);// ??????????????????????????????
			if ((attachmentList == null || attachmentList.size() == 0) && (analysisReportList == null || analysisReportList.size() == 0)) {
//				map.put("applicationStatus", BusinessConstants.ApplicationStatus.YX.toString());
//				merchandiseOaApplicationService.updateOaApplicationStatus(map);

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
	public void doShowLackSuggestFileAdjustprice() throws Exception {
		String applicationCode = this.asString("applicationCode");
		List<ApplicationLackFileM> attachmentList = supplierAttachmentMService.listAttachmentLackInfo(applicationCode,BusinessConstants.ApplicationType.MERCHANDISE_ADJUSTPRICE.toString());// ??????????????????????????????
		List<ApplicationLackFileM> analysisReportList = analysisReportNewService.listNoLinkMCAReportApplication(applicationCode);// ???????????????????????????
		lackFileList.addAll(attachmentList);
		lackFileList.addAll(analysisReportList);

		this.putObject("applicationCode", applicationCode);
		this.putObject("lackFileList", lackFileList);
		this.forwardPage("sco/merchandiseOaApplication/reportAdjustpriceOaApplication/lackSuggestFileAdjustpriceGrid.ftl");
	}

	/**
	 * ???????????????????????????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationLackFileAdjustprice() throws Exception {
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
	public void doUndoApplicationAdjustprice() throws Exception {
		String applicationCodes = this.asString("applicationCodes");
		merchandiseOaApplicationService.completeUndoOaApplicationStatus(applicationCodes);

		this.forwardData(true, null, this.getText("public.success"));
	}

	public List<ApplicationLackFileM> getLackFileList() {
		return lackFileList;
	}

	public void setLackFileList(List<ApplicationLackFileM> lackFileList) {
		this.lackFileList = lackFileList;
	}

	public List<ApplicationReportAdjustprice> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List<ApplicationReportAdjustprice> applicationList) {
		this.applicationList = applicationList;
	}

	public String getOldPriceRowsList() {
		return oldPriceRowsList;
	}

	public void setOldPriceRowsList(String oldPriceRowsList) {
		this.oldPriceRowsList = oldPriceRowsList;
	}

	public String getNowPriceRowsList() {
		return nowPriceRowsList;
	}

	public void setNowPriceRowsList(String nowPriceRowsList) {
		this.nowPriceRowsList = nowPriceRowsList;
	}

	public String getSameRowsList() {
		return sameRowsList;
	}

	public void setSameRowsList(String sameRowsList) {
		this.sameRowsList = sameRowsList;
	}

	public String getMaterialRowsList() {
		return materialRowsList;
	}

	public void setMaterialRowsList(String materialRowsList) {
		this.materialRowsList = materialRowsList;
	}

}
