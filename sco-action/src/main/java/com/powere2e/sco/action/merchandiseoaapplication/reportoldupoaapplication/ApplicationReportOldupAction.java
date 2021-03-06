package com.powere2e.sco.action.merchandiseoaapplication.reportoldupoaapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;

import com.google.gson.Gson;
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
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.reportanalysis.AnalysisReportNewService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportnewoaapplication.supplierattachment.SupplierAttachmentMService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportoldupoaapplication.ApplicationReportOldupService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.reportoldupoaapplication.reportoldup.HistoryPriceOldupService;
import com.powere2e.sco.model.masterdata.Merchandise;
import com.powere2e.sco.model.merchandiseintention.MerchandiseIntention;
import com.powere2e.sco.model.merchandiseoaapplication.ApplicationLackFileM;
import com.powere2e.sco.model.merchandiseoaapplication.ApplicationMerchandise;
import com.powere2e.sco.model.merchandiseoaapplication.reportadjustpriceoaapplication.reportadjustprice.MerchandiseMaterial;
import com.powere2e.sco.model.merchandiseoaapplication.reportoldupoaapplication.ApplicationReportOldup;
import com.powere2e.sco.model.merchandiseoaapplication.reportoldupoaapplication.reportoldup.AnticipatedSellOld;
import com.powere2e.sco.model.merchandiseoaapplication.reportoldupoaapplication.reportoldup.CheckStandardOldup;
import com.powere2e.sco.model.merchandiseoaapplication.reportoldupoaapplication.reportoldup.HistoryPriceOldup;
import com.powere2e.sco.model.merchandiseoaapplication.reportoldupoaapplication.reportoldup.SameMerchandiseOldup;
import com.powere2e.sco.model.merchandiseoaapplication.reportoldupoaapplication.reportoldup.UpDownMarketOldup;
import com.powere2e.security.utils.PowerUtils;

/**
 * ????????????(????????????)???WEB???????????????
 * 
 * @author Joyce.Li
 * @version 1.0
 * @since 2015???4???20???
 */
public class ApplicationReportOldupAction extends WorkAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8940385638845962313L;
	private ApplicationReportOldupService reportOldupService;// ???????????????service
	private HistoryPriceOldupService priceOldupService;// ?????????service
	private MerchandiseOaApplicationService merchandiseOaApplicationService;// oa?????????service
	private SupplierAttachmentMService supplierAttachmentMService;// ???????????????????????????service
	private AnalysisReportNewService analysisReportNewService;// ???????????????service
	private MasterDataTypeService masterDataTypeService;// ????????????id???service
	private List<ApplicationReportOldup> applicationList = new ArrayList<ApplicationReportOldup>();// ??????????????????
	private List<ApplicationLackFileM> lackFileList = new ArrayList<ApplicationLackFileM>();// ???????????????????????????
	private String oldPriceRowsList;
	private String nowPriceRowsList;
	private String sameRowsList;
	private String sellRowsList;
	private String upDownRowsList;
	private String materialRowsList;
	
	/**
	 * ???????????????????????????
	 */
	@Override
	protected void beforeBuild() {
		reportOldupService = (ApplicationReportOldupService) ConfigFactory.getInstance().getBean("reportOldupService");
		priceOldupService = (HistoryPriceOldupService) ConfigFactory.getInstance().getBean("priceOldupService");
		merchandiseOaApplicationService = (MerchandiseOaApplicationService) ConfigFactory.getInstance().getBean("merchandiseOaApplicationService");
		supplierAttachmentMService = (SupplierAttachmentMService) ConfigFactory.getInstance().getBean("supplierAttachmentMService");
		analysisReportNewService = (AnalysisReportNewService) ConfigFactory.getInstance().getBean("analysisReportNewService");
		masterDataTypeService = (MasterDataTypeService) ConfigFactory.getInstance().getBean("masterDataTypeService");
	}

	/**
	 * ????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowApplicationReportOldupGrid() throws Exception {
		this.forwardPage("sco/merchandiseOaApplication/reportOldupOaApplication/applicationReportOldupGrid.ftl");
	}

	/**
	 * ????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListMerchandiseApplication() throws Exception {
		Map<String, Object> map = getMerchandise().toMap();
		String applicationDateStart = this.asString("applicationDateStart");
		String applicationDateEnd = this.asString("applicationDateEnd");

		map.put("applicationDateStart", applicationDateStart);
		map.put("applicationDateEnd", applicationDateEnd);

		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_OLDUP.toString());// ????????????????????????????????????????????????
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
	public void doShowInsertApplicationReportOldupForm() throws Exception {
		// ??????OA??????????????????
		String applicationCode = "OA".concat(masterDataTypeService.nextID("S_OA_APPLICATION"));
		// ???????????????????????????????????????????????????????????????
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");
		applicationList = reportOldupService.listApplicationReportOldup(applicationCode, intentionAndSupplierCodes);
		this.putObject("applicationCode", applicationCode);
		this.putObject("intentionAndSupplierCodes", intentionAndSupplierCodes);
		this.putObject("applicationList", applicationList);
		this.forwardPage("sco/merchandiseOaApplication/reportOldupOaApplication/reportOldupPanel.ftl");
	}

	/**
	 * ??????????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doShowUpdateApplicationReportOldupForm() throws Exception {
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
		applicationList = reportOldupService.listApplicationReportOldup(applicationCode, intentionAndSupplierCodes);
		
		this.putObject("applicationCode", applicationCode);
		this.putObject("intentionAndSupplierCodes", intentionAndSupplierCodes);
		this.putObject("applicationList", applicationList);
		this.forwardPage("sco/merchandiseOaApplication/reportOldupOaApplication/reportOldupPanel.ftl");
	}

	/**
	 * ??????????????????(????????????)
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationReportOldup() throws Exception {
		String applicationCode = this.asString("applicationCode");
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");
		//???????????????????????????
		List<ApplicationMerchandise> list = MerchandiseOaApplicationUtil
				.getIntentionAndSupplierCodeGroupList(intentionAndSupplierCodes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_OLDUP.toString());// ????????????
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
		
		Gson gson = new Gson();
		if (applicationList != null && applicationList.size() > 0) {
			JSONArray jsonArr = JSONArray.fromObject("[" + oldPriceRowsList + "]");
			Object oldPriceObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + nowPriceRowsList + "]");
			Object nowPriceObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + sameRowsList + "]");
			Object sameObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + sellRowsList + "]");
			Object sellObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + upDownRowsList + "]");
			Object upDownObjArr[] = jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+materialRowsList+"]");
			Object materialObjArr[]=jsonArr.toArray();
			
			for (int i = 0; i < applicationList.size(); i++) {
				List<ApplicationReportOldup> applicationReportOldup = new ArrayList<ApplicationReportOldup>();
				if (oldPriceRowsList != null) {
					HistoryPriceOldup oldPriceArr[] = gson.fromJson(oldPriceObjArr[i].toString(), HistoryPriceOldup[].class);
					if (oldPriceArr != null) {
						applicationList.get(i).setOldPriceArr(oldPriceArr);
					}
				}
				if (nowPriceRowsList != null) {
					HistoryPriceOldup nowPriceArr[] = gson.fromJson(nowPriceObjArr[i].toString(), HistoryPriceOldup[].class);
					if (nowPriceArr != null) {
						applicationList.get(i).setNowPriceArr(nowPriceArr);
					}

				}
				if (sameRowsList != null) {
					SameMerchandiseOldup sameArr[] = gson.fromJson(sameObjArr[i].toString(), SameMerchandiseOldup[].class);
					if (sameArr != null) {
						applicationList.get(i).setSameArr(sameArr);
					}

				}
				if (sellRowsList != null) {
					AnticipatedSellOld sellArr[] = gson.fromJson(sellObjArr[i].toString(), AnticipatedSellOld[].class);
					if (sellArr != null) {
						applicationList.get(i).setSellArr(sellArr);
					}

				}
				if (upDownRowsList != null) {
					UpDownMarketOldup upDownArr[] = gson.fromJson(upDownObjArr[i].toString(), UpDownMarketOldup[].class);
					if (upDownArr != null) {
						applicationList.get(i).setUpDownArr(upDownArr);
					}
				}

				if (materialRowsList != null) {
					MerchandiseMaterial materialArr[] = gson.fromJson(materialObjArr[i].toString(), MerchandiseMaterial[].class);
					if (materialArr != null) {
						applicationList.get(i).setMaterialArr(materialArr);
					}

				}
				applicationReportOldup.add(applicationList.get(i));
				//???????????????html
				String path = priceOldupService.saveApplicationReportOldupToHtml(
						"applicationReportOldup_" + applicationCode + "_" + (i + 1), applicationCode,
						applicationReportOldup, intentionAndSupplierCodes);
				applicationList.get(i).setPath(path);
			}
		}
		String receiptInfo = reportOldupService.insertApplicationReportOldup(applicationList, applicationCode, intentionAndSupplierCodes);
		if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.NONE.toString().equals(receiptInfo)
				|| MerchandiseOaApplicationUtil.ReportNewOaApplicationState.SAME_CG.toString().equals(receiptInfo)) {
			
			for(ApplicationReportOldup applicationReportOldup:applicationList){
				applicationReportOldup.setPath(applicationReportOldup.getPath().replace(ConfigPath.getUploadFilePath(), ""));
				reportOldupService.updateApplicationReportOldup(applicationReportOldup);
			}
			// ?????????????????????????????????
			applicationList = reportOldupService.listApplicationReportOldup(applicationCode, intentionAndSupplierCodes);
			this.putObject("applicationList", applicationList);

			this.forwardData(true, applicationList, this.getText("public.success"));
		} else if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.DIFFER.toString().equals(receiptInfo)) {
			this.forwardData(false, null, "??????????????????????????????OA???????????????,??????????????????!");
		} else {
			this.forwardData(false, null, "?????????????????????????????????????????????????????????,????????????!");
		}
	}
	
	/**
	 * ??????????????????(????????????)??????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationReportOldupCG() throws Exception {
		String applicationCode = this.asString("applicationCode");
		String intentionAndSupplierCodes = this.asString("intentionAndSupplierCodes");
		//???????????????????????????
		List<ApplicationMerchandise> list = MerchandiseOaApplicationUtil
				.getIntentionAndSupplierCodeGroupList(intentionAndSupplierCodes);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_OLDUP.toString());// ????????????
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
		
		Gson gson = new Gson();
		if (applicationList != null && applicationList.size() > 0) {
			JSONArray jsonArr = JSONArray.fromObject("[" + oldPriceRowsList + "]");
			Object oldPriceObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + nowPriceRowsList + "]");
			Object nowPriceObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + sameRowsList + "]");
			Object sameObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + sellRowsList + "]");
			Object sellObjArr[] = jsonArr.toArray();

			jsonArr = JSONArray.fromObject("[" + upDownRowsList + "]");
			Object upDownObjArr[] = jsonArr.toArray();
			
			jsonArr=JSONArray.fromObject("["+materialRowsList+"]");
			Object materialObjArr[]=jsonArr.toArray();
			
			for (int i = 0; i < applicationList.size(); i++) {
				try {
					List<ApplicationReportOldup> applicationReportOldup = new ArrayList<ApplicationReportOldup>();
					if (oldPriceRowsList != null) {
						HistoryPriceOldup oldPriceArr[] = gson.fromJson(oldPriceObjArr[i].toString(), HistoryPriceOldup[].class);
						if (oldPriceArr != null) {
							applicationList.get(i).setOldPriceArr(oldPriceArr);
						}
					}
					if (nowPriceRowsList != null) {
						HistoryPriceOldup nowPriceArr[] = gson.fromJson(nowPriceObjArr[i].toString(), HistoryPriceOldup[].class);
						if (nowPriceArr != null) {
							applicationList.get(i).setNowPriceArr(nowPriceArr);
						}

					}
					if (sameRowsList != null) {
						SameMerchandiseOldup sameArr[] = gson.fromJson(sameObjArr[i].toString(), SameMerchandiseOldup[].class);
						if (sameArr != null) {
							applicationList.get(i).setSameArr(sameArr);
						}

					}
					if (sellRowsList != null) {
						AnticipatedSellOld sellArr[] = gson.fromJson(sellObjArr[i].toString(), AnticipatedSellOld[].class);
						if (sellArr != null) {
							applicationList.get(i).setSellArr(sellArr);
						}

					}
					if (upDownRowsList != null) {
						UpDownMarketOldup upDownArr[] = gson.fromJson(upDownObjArr[i].toString(), UpDownMarketOldup[].class);
						if (upDownArr != null) {
							applicationList.get(i).setUpDownArr(upDownArr);
						}
					}

					if (materialRowsList != null) {
						MerchandiseMaterial materialArr[] = gson.fromJson(materialObjArr[i].toString(), MerchandiseMaterial[].class);
						if (materialArr != null) {
							applicationList.get(i).setMaterialArr(materialArr);
						}

					}
					applicationReportOldup.add(applicationList.get(i));
					//???????????????html
					/*String path = priceOldupService.saveApplicationReportOldupToHtml(
							"applicationReportOldup_" + applicationCode + "_" + (i + 1), applicationCode,
							applicationReportOldup, intentionAndSupplierCodes);
					applicationList.get(i).setPath(path);*/
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		String receiptInfo = reportOldupService.insertApplicationReportOldup(applicationList, applicationCode, intentionAndSupplierCodes);
		if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.NONE.toString().equals(receiptInfo)
				|| MerchandiseOaApplicationUtil.ReportNewOaApplicationState.SAME_CG.toString().equals(receiptInfo)) {
			
			for(ApplicationReportOldup applicationReportOldup:applicationList){
			//	applicationReportOldup.setPath(applicationReportOldup.getPath().replace(ConfigPath.getUploadFilePath(), ""));
				reportOldupService.updateApplicationReportOldup(applicationReportOldup);
			}
			// ?????????????????????????????????
			applicationList = reportOldupService.listApplicationReportOldup(applicationCode, intentionAndSupplierCodes);
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
	public void doDeleteApplicationReportOldup() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applicationCodes", this.asString("applicationCodes"));
		reportOldupService.deleteAllReportOldUpByApplicationCode(map);

		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	private Merchandise getMerchandise() throws Exception {
		Merchandise merchandise = new Merchandise();
		this.asBean(merchandise);
		return merchandise;
	}

	/**
	 * ???????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doExportReportOldupApplicationToExcel() throws Exception {
		ServletOutputStream out = response.getOutputStream();
		String fileName = ("??????????????????OA????????????_").concat(DateUtils.formateDateTime()).concat(".xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(Constant.DEFAULT_ENCODED_1), Constant.DEFAULT_ENCODED_2) + "\"");
		
		Map<String, Object> map = getMerchandise().toMap();
		String applicationDateStart = this.asString("applicationDateStart");
		String applicationDateEnd = this.asString("applicationDateEnd");

		map.put("applicationDateStart", applicationDateStart);
		map.put("applicationDateEnd", applicationDateEnd);

		map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_OLDUP.toString());// ????????????????????????????????????????????????
		reportOldupService.exportSignedQtyExcel(map, out);
		/*List<MerchandiseIntention> list = merchandiseOaApplicationService.queryMerchandiseApplicationList(map, null);
		this.putObject("applicationList", list);
		this.forwardDownload("excel/sco/merchandiseoaapplication/reportOldupOaApplicationTemplate.xlsx", ExcelUtils.getEncodeFileName("??????????????????OA????????????_" + DateUtils.formateDateTime() + ".xlsx"));*/
	}

	/**
	 * ???????????????(????????????)
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListUpDownMarketOldup() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		List<UpDownMarketOldup> list = priceOldupService.listUpDownMarketOldup(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????(????????????)
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListCheckStandardOldup() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		List<CheckStandardOldup> list = priceOldupService.listCheckStandardOldup(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ?????????????????????(????????????)
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListHistoryPriceOldup() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("piceType", this.asString("piceType"));
		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<HistoryPriceOldup> list = priceOldupService.listHistoryPriceOldup(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListComparePriceOldup() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<HistoryPriceOldup> list = priceOldupService.listComparePriceOldup(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}
	
	/**
	 * ?????????????????????(??????)??????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListMerchandiseMaterialPriceOld() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<MerchandiseMaterial> list = priceOldupService.listMerchandiseMaterial(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}
	
	/**
	 * ???????????????????????????(????????????)
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListSameMerchandiseOldup() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<SameMerchandiseOldup> list = priceOldupService.listSameMerchandiseOldup(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????(????????????)
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doListAnticipatedSellOldup() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("intentionCode", this.asString("intentionCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("applicationCode", this.asString("applicationCode"));
		List<AnticipatedSellOld> list = priceOldupService.listAnticipatedSellOld(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????????????????????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doSyncApplicationOldup() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String applicationCode = this.asString("applicationCode");
		map.put("applicationCode", applicationCode);

		// ??????????????????????????????
		List<ApplicationLackFileM> list = reportOldupService.queryNotHaveReportMerchandiseOldup(map);
		if (list != null && list.size() > 0) {
			// ?????????????????????????????????OA?????????
			StringBuffer check = new StringBuffer();
			for (ApplicationLackFileM reportNew : list) {
				check.append("????????????:" + reportNew.getApplicationCode() + ",????????????:" + reportNew.getMerchandiseCode() + ",???????????????:" + reportNew.getSupplierCode() + "<br/>");
			}
			this.forwardData(false, check.toString(), "??????????????????");
		} else {
			List<ApplicationLackFileM> attachmentList = supplierAttachmentMService.listAttachmentLackInfo(applicationCode,BusinessConstants.ApplicationType.MERCHANDISE_OLDUP.toString());// ??????????????????????????????????????????????????????
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
	public void doShowLackSuggestFileOldup() throws Exception {
		String applicationCode = this.asString("applicationCode");
		List<ApplicationLackFileM> attachmentList = supplierAttachmentMService.listAttachmentLackInfo(applicationCode,BusinessConstants.ApplicationType.MERCHANDISE_OLDUP.toString());// ??????????????????????????????
		List<ApplicationLackFileM> analysisReportList = analysisReportNewService.listNoLinkMCAReportApplication(applicationCode);// ???????????????????????????
		lackFileList.addAll(attachmentList);
		lackFileList.addAll(analysisReportList);

		this.putObject("applicationCode", applicationCode);
		this.putObject("lackFileList", lackFileList);
		this.forwardPage("sco/merchandiseOaApplication/reportOldupOaApplication/lackSuggestFileOldupGrid.ftl");
	}

	/**
	 * ???????????????????????????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco.oaApplication")
	public void doInsertApplicationLackFileOldup() throws Exception {
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
	public void doUndoApplicationOldup() throws Exception {
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

	public List<ApplicationReportOldup> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(List<ApplicationReportOldup> applicationList) {
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

	public String getSellRowsList() {
		return sellRowsList;
	}

	public void setSellRowsList(String sellRowsList) {
		this.sellRowsList = sellRowsList;
	}

	public String getUpDownRowsList() {
		return upDownRowsList;
	}

	public void setUpDownRowsList(String upDownRowsList) {
		this.upDownRowsList = upDownRowsList;
	}

	public String getMaterialRowsList() {
		return materialRowsList;
	}

	public void setMaterialRowsList(String materialRowsList) {
		this.materialRowsList = materialRowsList;
	}

	/*
	 * public static void main(String[] args) { String str=
	 * "[[{\"status\":\"P\",\"stockSite\":\"?????????\",\"purchaseUnits\":\"??????\",\"purchasePrice\":\"23.00\",\"sellRegion\":\"??????\",\"sellUnits\":\"??????\",\"sellPrice\":\"12.00\"}],[{\"status\":\"P\",\"stockSite\":\"??????\",\"purchaseUnits\":\"?????????\",\"purchasePrice\":\"12.00\",\"sellRegion\":\"??????\",\"sellUnits\":\"?????????\",\"sellPrice\":\"11.00\"}]]"
	 * ; String str1=
	 * "[[{\"status\":\"P\",\"stockSite\":\"??????\",\"purchaseUnits\":\"?????????\",\"purchasePrice\":\"11.00\",\"sellRegion\":\"??????\",\"sellUnits\":\"??????\",\"sellPrice\":\"11.00\"},{\"status\":\"P\",\"stockSite\":\"?????????\",\"purchaseUnits\":\"?????????\",\"purchasePrice\":\"111.00\",\"sellRegion\":\"?????????\",\"sellUnits\":\"??????\",\"sellPrice\":\"111.00\"}],[{\"status\":\"P\",\"stockSite\":\"?????????\",\"purchaseUnits\":\"??????\",\"purchasePrice\":\"22.00\",\"sellRegion\":\"?????????\",\"sellUnits\":\"??????\",\"sellPrice\":\"22.00\"}],[{\"status\":\"P\",\"stockSite\":\"?????????\",\"purchaseUnits\":\"???\",\"purchasePrice\":\"33.00\",\"sellRegion\":\"???\",\"sellUnits\":\"????????????\",\"sellPrice\":\"33.00\"},{\"status\":\"P\",\"stockSite\":\"???\",\"purchaseUnits\":\"?????????\",\"purchasePrice\":\"333.00\",\"sellRegion\":\"????????????\",\"sellUnits\":\"?????????\",\"sellPrice\":\"333.00\"}]]"
	 * ; String str3="[[],[]]";
	 * 
	 * JSONArray priceJsonArr=JSONArray.fromObject(str3); Object
	 * objArr[]=priceJsonArr.toArray();
	 * 
	 * System.out.println(objArr.length); Gson gson = new Gson(); for(int
	 * i=0;i<objArr.length;i++){ System.out.println(objArr[i]);
	 * HistoryPriceOldup oldPriceArr[] = gson.fromJson(objArr[i].toString(),
	 * HistoryPriceOldup[].class); System.out.println(oldPriceArr.length); }
	 * //List<HistoryPriceOldup>
	 * list=JSONArray.toList(priceJsonArr,HistoryPriceOldup.class);
	 * //System.out.println(list.get(0)); }
	 */

}
