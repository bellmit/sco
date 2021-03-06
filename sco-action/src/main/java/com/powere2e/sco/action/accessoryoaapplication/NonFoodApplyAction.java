package com.powere2e.sco.action.accessoryoaapplication;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.config.ConfigPath;
import com.powere2e.frame.commons.power.Authority;
import com.powere2e.frame.server.sequence.SequenceFactory;
import com.powere2e.sco.common.service.BusinessConstants;
import com.powere2e.sco.common.utils.FreeMarkerUtil;
import com.powere2e.sco.common.utils.PathUtils;
import com.powere2e.sco.common.utils.UploadUtils;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryEnquiryQuotedCountService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.ApplicationQuotedService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.ApplicationReportAccessoryService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.DhInfoService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.NonFoodApplyService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.SubscribeAccessoryService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.WlInfoService;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryQuotedCount;
import com.powere2e.sco.model.accessoryoaapplication.ApplicationQuoted;
import com.powere2e.sco.model.accessoryoaapplication.ApplicationReportAccessory;
import com.powere2e.sco.model.accessoryoaapplication.DhInfo;
import com.powere2e.sco.model.accessoryoaapplication.NonFoodApply;
import com.powere2e.sco.model.accessoryoaapplication.OaApplication;
import com.powere2e.sco.model.accessoryoaapplication.SubscribeAccessory;
import com.powere2e.sco.model.accessoryoaapplication.WlInfo;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryAccessoryServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryElseServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryMaterialServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryPackingServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryTechnologyServiceImpl;
import com.powere2e.sco.service.impl.accessoryoaapplication.ApplicationReportAccessoryServiceImpl;
import com.powere2e.sco.service.impl.accessoryoaapplication.OaApplicationServiceImpl;
import com.powere2e.sco.service.impl.common.MasterDataTypeServiceImpl;
import com.powere2e.security.utils.PowerUtils;

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
public class NonFoodApplyAction extends UploadUtils {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7467505029752175810L;

	private NonFoodApplyService nonFoodApplyService;
	private AccessoryEnquiryQuotedCountService accessoryEnquiryQuotedCountService;
	private ApplicationQuotedService applicationQuotedService;
	private ApplicationReportAccessoryService applicationReportAccessoryService;
	private DhInfoService dhInfoService;
	private WlInfoService wlInfoService;
	private SubscribeAccessoryService subscribeAccessoryService;
	private List<SubscribeAccessory> subscribeAccessoryTextList = new ArrayList<SubscribeAccessory>();

	/**
	 * ???????????????????????????
	 */
	@Override
	protected void beforeBuild() {
		nonFoodApplyService = (NonFoodApplyService) ConfigFactory.getInstance().getBean("nonFoodApplyService");
		applicationQuotedService = (ApplicationQuotedService) ConfigFactory.getInstance().getBean("applicationQuotedService");
		applicationReportAccessoryService = (ApplicationReportAccessoryService) ConfigFactory.getInstance().getBean("applicationReportAccessoryService");
		subscribeAccessoryService = (SubscribeAccessoryService) ConfigFactory.getInstance().getBean("subscribeAccessoryService");
		dhInfoService = (DhInfoService) ConfigFactory.getInstance().getBean("dhInfoService");
		wlInfoService = (WlInfoService) ConfigFactory.getInstance().getBean("wlInfoService");
		accessoryEnquiryQuotedCountService = (AccessoryEnquiryQuotedCountService) ConfigFactory.getInstance().getBean("accessoryEnquiryQuotedCountService");
	}

	/**
	 * ?????????????????????grid
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doShowNonFoodApplyGrid() throws Exception {
		this.forwardPage("sco/accessoryOaApplication/nonFoodApply/nonFoodApplyGrid.ftl");
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doAddNonFoodApply() throws Exception {
		List<SubscribeAccessory> subscribeAccessoryList = new ArrayList<SubscribeAccessory>();
		ApplicationReportAccessory applicationReportAccessory = new ApplicationReportAccessory();
		String applicationCodeNow = this.asString("applicationCode");
		String quotedCodes = this.asString("quotedCodes");
		if (applicationCodeNow == null || "null".equals(applicationCodeNow)) {
			// ??????OA??????????????????
			applicationCodeNow = this.getOaApplicationPrimaryKey();
			// ???????????????????????????????????????????????????????????????
			String qcs[] = quotedCodes.split(",");
			for (int i = 0; i < qcs.length; i++) {
				NonFoodApply nonFoodApply = new NonFoodApply();
				SubscribeAccessory subscribeAccessory = new SubscribeAccessory();
				nonFoodApply = nonFoodApplyService.loadNonFoodApplyIntentionApplication(qcs[i]);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("enquiryCode", nonFoodApply.getEnquiryCode());
				List<AccessoryEnquiryQuotedCount> accessoryEnquiryQuotedCountList = accessoryEnquiryQuotedCountService.listAccessoryEnquiryQuotedCount(map, null);
				subscribeAccessory.setAccessoryEnquiryQuotedCountList(accessoryEnquiryQuotedCountList);
				subscribeAccessory.setAccessoryName(nonFoodApply.getIntentionName());
				subscribeAccessory.setIntentionCode(nonFoodApply.getIntentionCode());
				subscribeAccessory.setEnquiryCode(nonFoodApply.getEnquiryCode());
				subscribeAccessory.setAccessoryEnquiryQuotedCountList(accessoryEnquiryQuotedCountList);
				subscribeAccessory.setAccessoryEnquiryPackingList(AccessoryEnquiryPackingServiceImpl.getInstance().listAccessoryEnquiryPacking(map, null));
				subscribeAccessory.setAccessoryEnquiryAccessoryList(AccessoryEnquiryAccessoryServiceImpl.getInstance().listAccessoryEnquiryAccessory(map, null));
				subscribeAccessory.setAccessoryEnquiryMaterialList(AccessoryEnquiryMaterialServiceImpl.getInstance().listAccessoryEnquiryMaterial(map, null));
				subscribeAccessory.setAccessoryEnquiryTechnologyList(AccessoryEnquiryTechnologyServiceImpl.getInstance().listAccessoryEnquiryTechnology(map, null));
				subscribeAccessory.setAccessoryEnquiryElseList(AccessoryEnquiryElseServiceImpl.getInstance().listAccessoryEnquiryElse(map, null));
				subscribeAccessoryList.add(subscribeAccessory);
			}
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("applicationCode", applicationCodeNow);
			// wlInfoList = wlInfoService.listWlInfo(map, null);
			if (!StringUtils.isNotBlank(quotedCodes)) {
				quotedCodes = "";
				List<ApplicationQuoted> applicationQuotedList = applicationQuotedService.listApplicationQuoted(map, null);
				for (ApplicationQuoted applicationQuoted : applicationQuotedList) {
					quotedCodes = quotedCodes + applicationQuoted.getQuotedCode() + ",";
				}
			}
			applicationReportAccessory = applicationReportAccessoryService.loadApplicationReportAccessory(applicationCodeNow);
			subscribeAccessoryList = subscribeAccessoryService.listSubscribeAccessory(map, null);
			if (subscribeAccessoryList != null && subscribeAccessoryList.size() > 0) {
				for (SubscribeAccessory subscribeAccessory : subscribeAccessoryList) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("enquiryCode", subscribeAccessory.getEnquiryCode());
					List<AccessoryEnquiryQuotedCount> accessoryEnquiryQuotedCountList = accessoryEnquiryQuotedCountService.listAccessoryEnquiryQuotedCount(map1, null);
					subscribeAccessory.setAccessoryEnquiryQuotedCountList(accessoryEnquiryQuotedCountList);
					subscribeAccessory.setAccessoryEnquiryPackingList(AccessoryEnquiryPackingServiceImpl.getInstance().listAccessoryEnquiryPacking(map1, null));
					subscribeAccessory.setAccessoryEnquiryAccessoryList(AccessoryEnquiryAccessoryServiceImpl.getInstance().listAccessoryEnquiryAccessory(map1, null));
					subscribeAccessory.setAccessoryEnquiryMaterialList(AccessoryEnquiryMaterialServiceImpl.getInstance().listAccessoryEnquiryMaterial(map1, null));
					subscribeAccessory.setAccessoryEnquiryTechnologyList(AccessoryEnquiryTechnologyServiceImpl.getInstance().listAccessoryEnquiryTechnology(map1, null));
					subscribeAccessory.setAccessoryEnquiryElseList(AccessoryEnquiryElseServiceImpl.getInstance().listAccessoryEnquiryElse(map1, null));
				}
			} else {
				String qcs[] = quotedCodes.split(",");
				for (int i = 0; i < qcs.length; i++) {
					NonFoodApply nonFoodApply = new NonFoodApply();
					SubscribeAccessory subscribeAccessory = new SubscribeAccessory();
					nonFoodApply = nonFoodApplyService.loadNonFoodApplyIntentionApplication(qcs[i]);
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("enquiryCode", nonFoodApply.getEnquiryCode());
					List<AccessoryEnquiryQuotedCount> accessoryEnquiryQuotedCountList = accessoryEnquiryQuotedCountService.listAccessoryEnquiryQuotedCount(map1, null);
					subscribeAccessory.setAccessoryEnquiryQuotedCountList(accessoryEnquiryQuotedCountList);
					subscribeAccessory.setAccessoryName(nonFoodApply.getIntentionName());
					subscribeAccessory.setIntentionCode(nonFoodApply.getIntentionCode());
					subscribeAccessory.setEnquiryCode(nonFoodApply.getEnquiryCode());
					subscribeAccessory.setAccessoryEnquiryQuotedCountList(accessoryEnquiryQuotedCountList);
					subscribeAccessory.setAccessoryEnquiryPackingList(AccessoryEnquiryPackingServiceImpl.getInstance().listAccessoryEnquiryPacking(map1, null));
					subscribeAccessory.setAccessoryEnquiryAccessoryList(AccessoryEnquiryAccessoryServiceImpl.getInstance().listAccessoryEnquiryAccessory(map1, null));
					subscribeAccessory.setAccessoryEnquiryMaterialList(AccessoryEnquiryMaterialServiceImpl.getInstance().listAccessoryEnquiryMaterial(map1, null));
					subscribeAccessory.setAccessoryEnquiryTechnologyList(AccessoryEnquiryTechnologyServiceImpl.getInstance().listAccessoryEnquiryTechnology(map1, null));
					subscribeAccessory.setAccessoryEnquiryElseList(AccessoryEnquiryElseServiceImpl.getInstance().listAccessoryEnquiryElse(map1, null));
					subscribeAccessoryList.add(subscribeAccessory);
				}
			}
		}
		if (applicationReportAccessory != null) {
			applicationReportAccessory.setResponsiblePerson(PowerUtils.getCurrentUser().getRealName());
		} else {
			applicationReportAccessory = new ApplicationReportAccessory();
			applicationReportAccessory.setResponsiblePerson(PowerUtils.getCurrentUser().getRealName());
		}
		this.removeDuplicate(subscribeAccessoryList);
		this.putObject("applicationReportAccessory", applicationReportAccessory);
		this.putObject("subscribeAccessoryList", subscribeAccessoryList);
		this.putObject("subscribeAccessoryCount", subscribeAccessoryList.size());
		this.putObject("applicationCodeNow", applicationCodeNow);
		this.putObject("quotedCodes", quotedCodes);
		this.forwardPage("sco/accessoryOaApplication/nonFoodApply/addNonFoodApply.ftl");
	}

	/**
	 * ????????????????????????OA?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doListNonFoodApplyIntentionApplication() throws Exception {
		Map<String, Object> map = getNonFoodApply().toMap();
		String supplierNo = this.asString("intentionSupplierCode");
		String supplierName = this.asString("intentionSupplierName");
		map.put("search", this.asString("search"));
		map.put("intentionSupplierCode", supplierNo);
		map.put("intentionSupplierName", supplierName);
		map.put("supplierCode", supplierNo);
		map.put("supplierName", supplierName);
		List<NonFoodApply> list = nonFoodApplyService.listNonFoodApplyIntentionApplication(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????OA??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doListWlInfo() throws Exception {
		List<WlInfo> list = new ArrayList<WlInfo>();
		String applicationCodeNow = this.asString("applicationCodeNow");
		String quotedCodes = this.asString("quotedCodes");
		String qcs[] = quotedCodes.split(",");
		ApplicationQuoted applicationQuoted = applicationQuotedService.loadApplicationQuoted(qcs[0]);
		if (applicationQuoted != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("applicationCode", applicationCodeNow);
			list = wlInfoService.listWlInfo(map, null);
				for (int i = 0; i < qcs.length; i++) {
					NonFoodApply nonFoodApply = nonFoodApplyService.loadNonFoodApplyIntentionApplication(qcs[i]);
					WlInfo wlInfo = new WlInfo();
					wlInfo.setIntentionCode(nonFoodApply.getIntentionCode());
					wlInfo.setIntentionName(nonFoodApply.getIntentionName());
					wlInfo.setSupplierCode(nonFoodApply.getSupplierCode() == null ? nonFoodApply.getIntentionSupplierCode() : nonFoodApply.getSupplierCode());
					wlInfo.setSupplierName(nonFoodApply.getSupplierName() == null ? nonFoodApply.getIntentionSupplierName() : nonFoodApply.getSupplierName());
					wlInfo.setQuotedCode(nonFoodApply.getQuotedCode());
					wlInfo.setEnquiryCode(nonFoodApply.getEnquiryCode());
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("enquiryCode", nonFoodApply.getEnquiryCode());
					List<SubscribeAccessory> subscribeAccessory = subscribeAccessoryService.listSubscribeAccessory(map2, null);
					String enquiryCount = "";
					if (subscribeAccessory != null && subscribeAccessory.size() > 0)
						enquiryCount = subscribeAccessory.get(0).getEnquiryCount();
					wlInfo.setEnquiryCount(enquiryCount);
					list.add(wlInfo);
				}
				if (list != null && list.size() > 1) {
					for (int i = 0; i < list.size(); i++) // ???????????????????????????
					{
						for (int j = list.size() - 1; j > i; j--) // ???????????? ??????????????????????????????
						{

							if (list.get(i).getQuotedCode().equals(list.get(j).getQuotedCode()) ) {
								list.remove(j);
							}

						}
					}
				}
		} else {
			for (int i = 0; i < qcs.length; i++) {
				NonFoodApply nonFoodApply = new NonFoodApply();
				nonFoodApply = nonFoodApplyService.loadNonFoodApplyIntentionApplication(qcs[i]);
				WlInfo wlInfo = new WlInfo();
				wlInfo.setIntentionCode(nonFoodApply.getIntentionCode());
				wlInfo.setIntentionName(nonFoodApply.getIntentionName());
				wlInfo.setSupplierCode(nonFoodApply.getSupplierCode() == null ? nonFoodApply.getIntentionSupplierCode() : nonFoodApply.getSupplierCode());
				wlInfo.setSupplierName(nonFoodApply.getSupplierName() == null ? nonFoodApply.getIntentionSupplierName() : nonFoodApply.getSupplierName());
				wlInfo.setQuotedCode(nonFoodApply.getQuotedCode());
				list.add(wlInfo);
			}
		}
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????OA?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doLoadNonFoodApplyIntentionApplication() throws Exception {
		NonFoodApply nonFoodApply = nonFoodApplyService.loadNonFoodApplyIntentionApplication(asString("quotedCode"));
		this.forwardData(true, nonFoodApply, null);
	}

	/**
	 * ???????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doInsertApplicationReportAccessoryAndSubscribeAccessory() throws Exception {
		String applicationCodeNow = asString("applicationCodeNow");
		String quotedCodes = asString("quotedCodes");
		String applicationType = BusinessConstants.ApplicationType.ACCESSORY_FSPJJD.toString();

		if (nonFoodApplyService.nonFoodInsertUpdateDeleteIsOk(quotedCodes, applicationCodeNow, applicationType)) {

			try {
				ApplicationReportAccessory applicationReportAccessory = getApplicationReportAccessory();
				applicationReportAccessory.setApplicationCode(applicationCodeNow);
				List<SubscribeAccessory> subscribeAccessoryList = new ArrayList<SubscribeAccessory>();
				subscribeAccessoryList = this.subscribeAccessoryTextList;
				for (SubscribeAccessory subscribeAccessory : subscribeAccessoryList) {
					subscribeAccessory.setApplicationCode(applicationCodeNow);
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("enquiryCode", subscribeAccessory.getEnquiryCode());
					List<AccessoryEnquiryQuotedCount> accessoryEnquiryQuotedCountList = accessoryEnquiryQuotedCountService.listAccessoryEnquiryQuotedCount(map1, null);
					subscribeAccessory.setAccessoryEnquiryQuotedCountList(accessoryEnquiryQuotedCountList);
					subscribeAccessory.setAccessoryEnquiryPackingList(AccessoryEnquiryPackingServiceImpl.getInstance().listAccessoryEnquiryPacking(map1, null));
					subscribeAccessory.setAccessoryEnquiryAccessoryList(AccessoryEnquiryAccessoryServiceImpl.getInstance().listAccessoryEnquiryAccessory(map1, null));
					subscribeAccessory.setAccessoryEnquiryMaterialList(AccessoryEnquiryMaterialServiceImpl.getInstance().listAccessoryEnquiryMaterial(map1, null));
					subscribeAccessory.setAccessoryEnquiryTechnologyList(AccessoryEnquiryTechnologyServiceImpl.getInstance().listAccessoryEnquiryTechnology(map1, null));
					subscribeAccessory.setAccessoryEnquiryElseList(AccessoryEnquiryElseServiceImpl.getInstance().listAccessoryEnquiryElse(map1, null));
				}
				nonFoodApplyService.insertApplicationReportAccessoryAndSubscribeAccessory(applicationReportAccessory, subscribeAccessoryList);
				Map<String, Object> paraMap=new HashMap<String, Object>();
				paraMap.put("applicationReportAccessory", applicationReportAccessory);
				paraMap.put("subscribeAccessoryList", subscribeAccessoryList);
				String fileName=applicationCodeNow;
				String msg=this.saveToHtml(fileName,paraMap);
				if (!StringUtils.isBlank(msg)) {
					this.forwardData(false, null, "??????????????????");
					return;
				}
			} catch (Exception e) {
				this.forwardData(false, null, "????????????????????????");
				return;
			}

		} else {
			this.forwardData(false, null, "????????????????????????????????????????????????????????????????????????????????????????????????");
			return;
		}
		this.forwardData(true, null, "????????????");
	}
	public String saveToHtml(String fileName, Map<String, Object> paraMap) {
		String tarPath;
		try {                                      
			tarPath = FreeMarkerUtil.generateHtml("nonfood/applicationReportAccessory.ftl",
					"nonfood".concat("/").concat(fileName), paraMap);
		} catch (Exception e) {
			return "???????????????????????????";
		}
		File file = new File(tarPath);//????????????
		if(file.exists()) {
			ApplicationReportAccessory applicationReportAccessory=(ApplicationReportAccessory)paraMap.get("applicationReportAccessory");
			applicationReportAccessory.setPath(tarPath.replace(ConfigPath.getUploadFilePath(), ""));
			ApplicationReportAccessoryServiceImpl.getInstance().updateApplicationReportAccessory(applicationReportAccessory);
		} else {
			return "??????Html????????????";
		}
		return null;
	}
	/**
	 * ??????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doListDhInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applicationCode", asString("applicationCodeNow"));
		List<DhInfo> list = dhInfoService.listDhInfo(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????? (??????)
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doListApplicationQuotedForDhInfo() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("applicationCode", asString("applicationCodeNow"));
		List<ApplicationQuoted> list = applicationQuotedService.listApplicationQuotedForDhInfo(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowInsertDhFile() throws Exception {
		String intentionCode = this.asString("intentionCode");// ???????????????
		String supplierCode = this.asString("supplierCode");
		String applicationCode = this.asString("applicationCode");
		this.putObject("intentionCode", intentionCode);
		this.putObject("supplierCode", supplierCode);
		this.putObject("applicationCode", applicationCode);
		this.forwardPage("sco/accessoryOaApplication/nonFoodApply/dhInfoForm.ftl");
	}

	/**
	 * ??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doInsertDhInfo() throws Exception {
		DhInfo dhInfo = new DhInfo();
		List<File> fList = null;
		dhInfo = getDhInfo();
		dhInfo.setId(SequenceFactory.getInstance().nextID("DH_INFO"));
		if ("??????".equals(dhInfo.getFileType())) dhInfo.setFileType(dhInfo.getFileTypeOther());
		String oaDhFilePath = PathUtils.getOaDhFilePath();
		if (this.getUploads() != null && this.getUploads()[0] != null) {
			fList = this.doUploadBySaveDir(oaDhFilePath);
		}
		if (fList != null) {
			String path = oaDhFilePath.replaceAll(ConfigPath.getUploadFilePath(), "").concat(fList.get(0).getName());// ????????????
			dhInfo.setPath(path);
			dhInfo.setFileName(fList.get(0).getName());
		}
		dhInfoService.insertDhInfo(dhInfo);
		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ??????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDeleteDhInfo() throws Exception {
		String dhId = this.asString("dhId");
		if (dhId != null) {
			String dhinfoId[] = dhId.split(",");
			nonFoodApplyService.deleteDhInfo(dhinfoId);
			this.forwardData(true, null, this.getText("public.success"));
		}
	}

	/**
	 * ??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDownloadDhFile() {
		String path = "upload/".concat(this.asString("path"));
		this.forwardDownload(path);
	}

	/**
	 * ??????OA??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDeleteApplication() {
		String applicationCode = this.asString("applicationCode");
		if (StringUtils.isNotBlank(applicationCode)) {
			String applicationCodes[] = applicationCode.split(",");
			/*
			 * String applicationCodeIn=""; for(String code:applicationCodes){
			 * applicationCodeIn=applicationCodeIn+code+","; }
			 */
			// applicationCodeIn=applicationCodeIn.substring(0,applicationCodeIn.length()-2);
			// Map<String,Object> map=new HashMap<String,Object>();
			// map.put("applicationCodeIn", applicationCodeIn);
			nonFoodApplyService.deleteApplication(applicationCodes);

		}
		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ??????OA??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doCloseApplication() {
		String applicationCode = this.asString("applicationCode");
		if (StringUtils.isNotBlank(applicationCode)) {
			String applicationCodes[] = applicationCode.split(",");
			nonFoodApplyService.closeApplication(applicationCodes);

		}
		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ????????????OA??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doUndoOaSynchronous() {
		String applicationCode = this.asString("applicationCode");
		if (StringUtils.isNotBlank(applicationCode)) {
			String applicationCodes[] = applicationCode.split(",");
			nonFoodApplyService.undoOaSynchronous(applicationCodes);

		}
		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ??????OA??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doAllowOaSynchronous() {
		String check = "";
		String applicationCode = this.asString("applicationCode");
		check = nonFoodApplyService.checkApplication(applicationCode);
		if (StringUtils.isNotBlank(applicationCode) && "".equals(check)) {
			nonFoodApplyService.allowOaSynchronous(applicationCode);
			this.forwardData(true, null, this.getText("public.success"));
		} else {
			this.forwardData(false, null, check);
		}

	}

	/**
	 * ??????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doInsertWlInfo() throws Exception {
		String rows = this.asString("rows");
	//	String applicationCodeNow = asString("applicationCodeNow");
	//	String quotedCodes = asString("quotedCodes");
	//	String applicationType = BusinessConstants.ApplicationType.ACCESSORY_CGWYHJJD.toString();
		/*
		 * if (nonFoodApplyService.nonFoodInsertUpdateDeleteIsOk(quotedCodes,
		 * applicationCodeNow, applicationType)) {
		 */
		try {
			if (!StringUtils.isBlank(rows)) {
				JSONArray jsonArr = JSONArray.fromObject(rows);
				WlInfo[] dataArray = new WlInfo[jsonArr.size()];
				for (int j = 0; j < jsonArr.size(); j++) {
					dataArray[j] = (WlInfo) JSONObject.toBean(jsonArr.getJSONObject(j), WlInfo.class);
				}
				this.nonFoodApplyService.insertWlInfo(dataArray);
			}
		} catch (Exception e) {
			this.forwardData(false, null, "????????????????????????");
			return;
		}
		/*
		 * }else{ this.forwardData(false, null,
		 * "????????????????????????????????????????????????????????????????????????????????????????????????"); return; }
		 */
		this.forwardData(true, null, "????????????");
	}

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	private NonFoodApply getNonFoodApply() throws Exception {
		NonFoodApply nonFoodApply = new NonFoodApply();
		this.asBean(nonFoodApply);
		return nonFoodApply;
	}

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	private ApplicationReportAccessory getApplicationReportAccessory() throws Exception {
		ApplicationReportAccessory applicationReportAccessory = new ApplicationReportAccessory();
		this.asBean(applicationReportAccessory);
		return applicationReportAccessory;
	}

	private DhInfo getDhInfo() throws Exception {
		DhInfo dhInfo = new DhInfo();
		this.asBean(dhInfo);
		return dhInfo;
	}

	// ??????OA??????????????????
	private String getOaApplicationPrimaryKey() {
		return "OA".concat(MasterDataTypeServiceImpl.getInstance().nextID("S_OA_APPLICATION"));
	}

	public List<SubscribeAccessory> getSubscribeAccessoryTextList() {
		return subscribeAccessoryTextList;
	}

	public void setSubscribeAccessoryTextList(List<SubscribeAccessory> subscribeAccessoryTextList) {
		this.subscribeAccessoryTextList = subscribeAccessoryTextList;
	}

	public void removeDuplicate(List<SubscribeAccessory> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getEnquiryCode().equals(list.get(i).getEnquiryCode())) {
					list.remove(j);
				}
			}
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doIfApproved() throws Exception {
		OaApplication oa = OaApplicationServiceImpl.getInstance().loadOaApplication(asString("applicationCode"));
		boolean flag = (oa == null ? false : (BusinessConstants.ApplicationStatus.SPTG.toString().equals(oa.getApplicationStatus())));
		if (flag) {
			this.forwardData(flag, null, null);
			return;
		} else {
			this.forwardData(flag, null, "?????????????????????OA?????????????????????!");
		}
	}
}
