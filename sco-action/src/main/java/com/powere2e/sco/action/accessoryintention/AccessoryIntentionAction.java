package com.powere2e.sco.action.accessoryintention;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.config.ConfigPath;
import com.powere2e.frame.commons.power.Authority;
import com.powere2e.frame.web.exception.EscmException;
import com.powere2e.sco.common.utils.Constant;
import com.powere2e.sco.common.utils.DateUtils;
import com.powere2e.sco.common.utils.ExcelUtils;
import com.powere2e.sco.common.utils.LoggerUtil;
import com.powere2e.sco.common.utils.PathUtils;
import com.powere2e.sco.common.utils.UploadUtils;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryEnquiryAccessoryService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryEnquiryElseService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryEnquiryMaterialService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryEnquiryPackingService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryEnquiryQuotedCountService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryEnquiryService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryEnquiryTechnologyService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryIntentionService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryProofingService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryQuotedElectronicService;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryQuotedScanService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.ApplicationQuotedService;
import com.powere2e.sco.interfaces.service.common.MasterDataTypeService;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiry;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryAccessory;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryElse;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryMaterial;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryPacking;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryQuotedCount;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryTechnology;
import com.powere2e.sco.model.accessoryintention.AccessoryIntention;
import com.powere2e.sco.model.accessoryintention.AccessoryIntentionSupplier;
import com.powere2e.sco.model.accessoryintention.AccessoryProofing;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedElectronic;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedScan;
import com.powere2e.sco.model.accessoryoaapplication.ApplicationQuoted;
import com.powere2e.sco.model.accessoryoaapplication.supplierattachment.QuotedElectronicOfScan;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryQuotedElectronicServiceImpl;
import com.powere2e.sco.service.impl.accessoryoaapplication.ApplicationQuotedServiceImpl;
import com.powere2e.sco.service.impl.accessoryoaapplication.committeeApply.supplierattachment.QuotedElectronicOfScanServiceImpl;
import com.powere2e.sco.service.impl.common.MasterDataTypeServiceImpl;
import com.powere2e.security.model.Option;
import com.powere2e.security.utils.PowerUtils;

/**
 * ??????????????????WEB???????????????
 * 
 * @author gavin.xu
 * @version 1.0
 * @since 2015???3???16???
 */
public class AccessoryIntentionAction extends UploadUtils {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1831909998708561021L;
	private AccessoryIntentionService accessoryIntentionService;
	private AccessoryEnquiryMaterialService accessoryEnquiryMaterialService;
	private AccessoryEnquiryService accessoryEnquiryService;
	private AccessoryEnquiryAccessoryService accessoryEnquiryAccessoryService;
	private AccessoryEnquiryPackingService accessoryEnquiryPackingService;
	private AccessoryEnquiryTechnologyService accessoryEnquiryTechnologyService;
	private AccessoryEnquiryQuotedCountService accessoryEnquiryQuotedCountService;
	private AccessoryEnquiryElseService accessoryEnquiryElseService;
	private AccessoryQuotedElectronicService accessoryQuotedElectronicService;
	private AccessoryQuotedScanService accessoryQuotedScanService;
	private AccessoryProofingService accessoryProofingService;
	private MasterDataTypeService masterDataTypeService;
	private ApplicationQuotedService applicationQuotedService;

	/*
	 * private File accessoryUploadQuoted;// ????????????????????????
	 * 
	 * public File getAccessoryUploadQuoted() { return accessoryUploadQuoted; }
	 * 
	 * public void setAccessoryUploadQuoted(File accessoryUploadQuoted) {
	 * this.accessoryUploadQuoted = accessoryUploadQuoted; }
	 */

	// private File[] uploads;// ????????????????????????????????????

	/**
	 * ???????????????????????????
	 */
	@Override
	protected void beforeBuild() {
		accessoryIntentionService = (AccessoryIntentionService) ConfigFactory.getInstance().getBean("accessoryIntentionService");
		accessoryEnquiryMaterialService = (AccessoryEnquiryMaterialService) ConfigFactory.getInstance().getBean("accessoryEnquiryMaterialService");
		accessoryEnquiryService = (AccessoryEnquiryService) ConfigFactory.getInstance().getBean("accessoryEnquiryService");
		accessoryEnquiryAccessoryService = (AccessoryEnquiryAccessoryService) ConfigFactory.getInstance().getBean("accessoryEnquiryAccessoryService");
		accessoryEnquiryPackingService = (AccessoryEnquiryPackingService) ConfigFactory.getInstance().getBean("accessoryEnquiryPackingService");
		accessoryEnquiryTechnologyService = (AccessoryEnquiryTechnologyService) ConfigFactory.getInstance().getBean("accessoryEnquiryTechnologyService");
		accessoryEnquiryQuotedCountService = (AccessoryEnquiryQuotedCountService) ConfigFactory.getInstance().getBean("accessoryEnquiryQuotedCountService");
		accessoryEnquiryElseService = (AccessoryEnquiryElseService) ConfigFactory.getInstance().getBean("accessoryEnquiryElseService");
		accessoryQuotedElectronicService = (AccessoryQuotedElectronicService) ConfigFactory.getInstance().getBean("accessoryQuotedElectronicService");
		accessoryQuotedScanService = (AccessoryQuotedScanService) ConfigFactory.getInstance().getBean("accessoryQuotedScanService");
		accessoryProofingService = (AccessoryProofingService) ConfigFactory.getInstance().getBean("accessoryProofingService");
		masterDataTypeService = (MasterDataTypeService) ConfigFactory.getInstance().getBean("masterDataTypeService");
		applicationQuotedService = (ApplicationQuotedService) ConfigFactory.getInstance().getBean("applicationQuotedService");
	}

	/**
	 * ?????????????????????grid
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doShowAccessoryIntentionGrid() throws Exception {
		this.forwardPage("sco/accessoryIntention/accessoryIntentionGrid.ftl");
	}

	/**
	 * ?????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doListAccessoryIntention() throws Exception {
		Map<String, Object> map = getAccessoryIntention().toMap();
		/*
		 * if (accessoryIntention.getCreatedEnd() != null &&
		 * !"".equals(accessoryIntention.getCreatedEnd())) { Date createdEnd =
		 * asDate("createdEnd"); Calendar rightNow = Calendar.getInstance();
		 * rightNow.setTime(createdEnd); rightNow.add(Calendar.DAY_OF_YEAR,
		 * 1);// ?????????1??? createdEnd = rightNow.getTime(); map.put("createdEnd",
		 * createdEnd); }
		 */
		map.put("search", this.asString("search"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("supplierName", this.asString("supplierName"));
		List<AccessoryIntention> list = accessoryIntentionService.listAccessoryIntention(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention.add")
	public void doAddIntention() throws Exception {
		String intentionCode = asString("intentionCode");
		AccessoryIntention accessoryIntention = new AccessoryIntention();
		if (StringUtils.isNotBlank(intentionCode)) {
			accessoryIntention = this.accessoryIntentionService.loadAccessoryIntention(asString("intentionCode"));
		} else {
			accessoryIntention.setIntentionCode("FY" + masterDataTypeService.nextID("S_ACCESSORY_INTENTION"));
		}
		this.putObject("accessoryIntention", accessoryIntention);
		this.forwardPage("sco/accessoryIntention/addAccessoryIntention.ftl");
	}

	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowInsertSupplierForm() throws Exception {
		String supplierCode = masterDataTypeService.nextID("S_ACCESSORY_INTENTION_SUPPLIER");
		this.putObject("supplierCode", "FYG" + supplierCode);
		this.putObject("intentionCode", asString("intentionCode"));
		this.forwardPage("sco/accessoryIntention/createSupplierForm.ftl");
	}

	/**
	 * ?????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowInsertXJDForm() throws Exception {
		String enquiryCode = asString("enquiryCode");
		AccessoryEnquiry accessoryEnquiry = new AccessoryEnquiry();
		if (enquiryCode != null && !"".equals(enquiryCode)) {
			accessoryEnquiry = accessoryEnquiryService.loadAccessoryEnquiry(enquiryCode);
			if (accessoryEnquiry.getAttachment() != null) {
				String fileName = accessoryEnquiry.getAttachment().substring(accessoryEnquiry.getAttachment().lastIndexOf("/") + 1, accessoryEnquiry.getAttachment().length());
				accessoryEnquiry.setAttachment(fileName);
			}
		} else {
			accessoryEnquiry.setEnquiryCode(masterDataTypeService.nextID("S_ACCESSORY_ENQUIRY"));
		}
		this.putObject("accessoryEnquiry", accessoryEnquiry);
		this.forwardPage("sco/accessoryIntention/createXJDForm.ftl");
	}

	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doInsertSupplierAndRelateSupplier() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		String supplierCode = this.asString("supplierCode");
		String intentionCode = this.asString("intentionCode");
		AccessoryIntention accessoryIntention = this.accessoryIntentionService.loadAccessoryIntention(intentionCode);
		if (accessoryIntention == null) {
			this.forwardData(false, null, "???????????????????????????");
		} else {
			if (accessoryIntentionService.editIsOk(intentionCode)) {

				map.put("intentionSupplierCode", supplierCode);

				map.put("intentionCode", this.asString("intentionCode"));
				map.put("supplierName", this.asString("supplierName"));
				map1.put("supplierCode", this.asString("supplierCode"));
				map1.put("supplierName", this.asString("supplierName"));
				map2.put("supplierName", this.asString("supplierName"));
				AccessoryIntentionSupplier accessoryIntentionSupplier = this.accessoryIntentionService.loadIntentionSupplier(map2);
				if (accessoryIntentionSupplier == null) {
					this.accessoryIntentionService.insertIntentionSupplier(map, map1);
					this.forwardData(true, null, this.getText("public.success"));
				} else {
					this.forwardData(false, null, "???????????????????????????");
				}
			} else {
				this.forwardData(false, null, "??????????????????????????????");
			}
		}
	}

	/**
	 * ??????ID???????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doLoadAccessoryIntention() throws Exception {
		AccessoryIntention accessoryIntention = this.accessoryIntentionService.loadAccessoryIntention(asString("intentionCode"));
		this.forwardData(true, accessoryIntention, null);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.insert")
	public void doShowInsertAccessoryIntentionForm() throws Exception {
		AccessoryIntention accessoryIntention = new AccessoryIntention();
		this.putObject("accessoryIntention", accessoryIntention);
		this.forwardPage("accessoryIntention/accessoryIntentionForm");
	}

	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.edit")
	public void doShowUpdateAccessoryIntentionForm() throws Exception {
		AccessoryIntention accessoryIntention = accessoryIntentionService.loadAccessoryIntention(asString("intentionCode"));
		this.putObject("accessoryIntention", accessoryIntention);
		this.forwardPage("accessoryIntention/accessoryIntentionForm");
	}

	/**
	 * ?????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doInsertAccessoryIntention() throws Exception {
		AccessoryIntention accessoryIntention = null;
		String intentionCode = asString("intentionCode");
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			accessoryIntention = this.accessoryIntentionService.loadAccessoryIntention(intentionCode);
			if (accessoryIntention != null) {
				accessoryIntention = getAccessoryIntention();
				accessoryIntention.setCreateby(PowerUtils.getCurrentUser().getLoginName());
				// accessoryIntention.setIntentionCode(SequenceFactory.getInstance().nextID("accessory_intention"));
				accessoryIntentionService.updateAccessoryIntention(accessoryIntention);
			} else {
				accessoryIntention = getAccessoryIntention();
				// accessoryIntention.setIntentionCode(SequenceFactory.getInstance().nextID("accessory_intention"));
				accessoryIntentionService.insertAccessoryIntention(accessoryIntention);
			}
			this.forwardData(true, null, this.getText("public.success"));
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ???????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doInsertAccessoryIntentionXJD() throws Exception {
		// String str = this.asString("date[0][intentionSupplierCode]");
		String intentionCode = asString("intentionCode");
		String enquiryCode = asString("enquiryCode");
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("enquiryCode", enquiryCode);
			List<AccessoryQuotedElectronic> accessoryQuotedElectronicList = AccessoryQuotedElectronicServiceImpl.getInstance().listAccessoryQuotedElectronic(map, null);
			if (accessoryQuotedElectronicList != null && accessoryQuotedElectronicList.size() > 0) {
				this.forwardData(false, null, "????????????????????????????????????");
			} else {
				List<File> fList = null;
				String accIntenEnquiryFilePath = PathUtils.getAccessoryintentionEnquiryFileUploadPath();
				if (this.getUploads() != null && this.getUploads()[0] != null) {
					fList = this.doUploadBySaveDir(accIntenEnquiryFilePath);
				}
				Gson gson = new Gson();
				AccessoryEnquiry ae = getAccessoryEnquiry();
				map.clear();
				map.put("enquiryName", ae.getEnquiryName());
				map.put("intentionCode", intentionCode);
				List<AccessoryEnquiry> accessoryEnquiryList = AccessoryEnquiryServiceImpl.getInstance().listAccessoryEnquiry(map, null);
				if (accessoryEnquiryList != null && accessoryEnquiryList.size() > 0 && !accessoryEnquiryList.get(0).getEnquiryCode().equals(enquiryCode)) {
					this.forwardData(false, null, "????????????????????????");
				} else {
					Boolean canInsert = true;
					try {
						if (fList != null) {
							ae.setAttachment(accIntenEnquiryFilePath.replaceAll(ConfigPath.getUploadFilePath(), "").concat(fList.get(0).getName()));// ????????????
						}
						String rows1 = this.asString("rows1");
						AccessoryEnquiryMaterial[] arr1 = gson.fromJson(rows1, AccessoryEnquiryMaterial[].class);

						String rows2 = this.asString("rows2");
						AccessoryEnquiryAccessory[] arr2 = gson.fromJson(rows2, AccessoryEnquiryAccessory[].class);

						String rows3 = this.asString("rows3");
						AccessoryEnquiryPacking[] arr3 = gson.fromJson(rows3, AccessoryEnquiryPacking[].class);

						String rows4 = this.asString("rows4");
						AccessoryEnquiryTechnology[] arr4 = gson.fromJson(rows4, AccessoryEnquiryTechnology[].class);

						String rows5 = this.asString("rows5");
						AccessoryEnquiryQuotedCount[] arr5 = gson.fromJson(rows5, AccessoryEnquiryQuotedCount[].class);

						String rows6 = this.asString("rows6");
						AccessoryEnquiryElse[] arr6 = gson.fromJson(rows6, AccessoryEnquiryElse[].class);
						/*
						 * String rows1=this.asString("rows1"); Gson gson = new
						 * Gson(); //?????????????????????????????? AccessoryIntentionSupplier[] arr
						 * = gson.fromJson(rows1,
						 * AccessoryIntentionSupplier[].class);
						 */

						accessoryIntentionService.insertAccessoryIntentionXJD(enquiryCode, intentionCode, ae, arr1, arr2, arr3, arr4, arr5, arr6);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						this.forwardData(false, null, "????????????");
						canInsert = false;
					}
					if (canInsert)
						this.forwardData(true, null, this.getText("public.success"));
				}
			}
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ???????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDeleteXJD() throws Exception {
		String enquiryCode[] = asString("enquiryCode").split(",");
		String intentionCode = asString("intentionCode");
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			try {
				accessoryIntentionService.deleteAccessoryEnquiry(enquiryCode);
				this.forwardData(true, null, this.getText("public.success"));
			} catch (EscmException e) {
				this.forwardData(false, null, "?????????????????????????????????????????????");
			}

		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ??????????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doShowInsertProofingForm() throws Exception {
		AccessoryProofing accessoryProofing = new AccessoryProofing();
		String intentionCode = this.asString("intentionCode");// ???????????????
		String quotedCode = this.asString("quotedCode");// ???????????????
		String enquiryCode = this.asString("enquiryCode");// ????????????
		String supplierCode = this.asString("supplierCode");
		String intentionSupplierCode = this.asString("intentionSupplierCode");
		accessoryProofing.setIntentionCode(intentionCode);
		accessoryProofing.setEnquiryCode(enquiryCode);
		accessoryProofing.setQuotedCode(quotedCode);
		accessoryProofing.setSupplierCode(supplierCode);
		accessoryProofing.setIntentionSupplierCode(intentionSupplierCode);
		this.putObject("accessoryProofing", accessoryProofing);
		this.forwardPage("sco/accessoryIntention/accessoryProofingForm.ftl");
	}

	/**
	 * ????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doInsertProofing() throws Exception {
		AccessoryProofing accessoryProofing = new AccessoryProofing();
		List<File> fList = null;
		accessoryProofing = getAccessoryProofing();
		String intentionCode = accessoryProofing.getIntentionCode();
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			String accIntenProofingpicFilePath = PathUtils.getAccessoryintentionProofingpictureFileUploadPath();
			if (this.getUploads() != null && this.getUploads()[0] != null) {
				fList = this.doUploadBySaveDir(accIntenProofingpicFilePath);
			}
			if (fList != null) {
				String path = accIntenProofingpicFilePath.replaceAll(ConfigPath.getUploadFilePath(), "").concat(fList.get(0).getName());// ????????????
				accessoryProofing.setPath(path);
			}
			accessoryProofingService.insertAccessoryProofing(accessoryProofing);
			this.forwardData(true, null, this.getText("public.success"));
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ???????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doInsertAccessoryIntentionSupplier() throws Exception {
		String supplierCode = this.asString("supplierCode");
		String intentionCode = asString("intentionCode");
		Boolean repeat = true;
		AccessoryIntention accessoryIntention = this.accessoryIntentionService.loadAccessoryIntention(intentionCode);
		if (accessoryIntention == null) {
			this.forwardData(false, null, "???????????????????????????");
		} else {
			if (accessoryIntentionService.editIsOk(intentionCode)) {
				Map<String, Object> mapsu = new HashMap<String, Object>();
				mapsu.put("intentionCode", this.asString("intentionCode"));
				List<AccessoryIntentionSupplier> list = accessoryIntentionService.listAccessoryIntentionSupplier(mapsu, null);
				if (list != null && list.size() > 0) {
					for (AccessoryIntentionSupplier accessoryIntentionSupplier : list) {
						if (supplierCode.equals(accessoryIntentionSupplier.getIntentionSupplierCode())) {
							repeat = false;
							break;
						}
					}
				}
				if (repeat) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("intentionCode", this.asString("intentionCode"));

					map.put("intentionSupplierCode", supplierCode);
					map.put("supplierCode", "");

					this.accessoryIntentionService.insertAccessoryIntentionSupplier(map);
					this.forwardData(true, null, this.getText("public.success"));
				} else {
					this.forwardData(false, null, "?????????????????????????????????????????????");
				}
			} else {
				this.forwardData(false, null, "??????????????????????????????");
			}
		}

	}

	/**
	 * ?????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco.edit")
	public void doUpdateAccessoryIntention() throws Exception {
		accessoryIntentionService.updateAccessoryIntention(getAccessoryIntention());
		this.forwardData(true, null, this.getText("public.success"));
	}

	/**
	 * ?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListCentreType() {
		List<Option> list = MasterDataTypeServiceImpl.getInstance().listCenterType(new HashMap<String, Object>());
		this.forwardData(true, list, null);
	}

	/**
	 * ?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListSmallType() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("centreTypeCode", asString("centreType"));
		List<Option> list = MasterDataTypeServiceImpl.getInstance().listSmallType(map);

		this.forwardData(true, list, null);
	}

	/**
	 * ?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListDetailType() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smallTypeCode", asString("smallType"));
		List<Option> list = MasterDataTypeServiceImpl.getInstance().listDetailType(map);
		this.forwardData(true, list, null);
	}

	/**
	 * ?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListMinceType() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Option> list = accessoryIntentionService.listMinceType(map);
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListSupplier() {
		List<Option> list = accessoryIntentionService.listSupplier(new HashMap<String, Object>());
		this.forwardData(true, list, null);
	}

	/**
	 * ?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doListAccessoryEnquiry() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AccessoryEnquiry> list = new ArrayList<AccessoryEnquiry>();
		String intentionCode = asString("intentionCode");
		if (intentionCode != null && !"".equals(intentionCode)) {
			map.put("intentionCode", intentionCode);
			list = accessoryEnquiryService.listAccessoryEnquiry(map, this.getPageInfo());
		}
		this.forwardData(true, list, null);
	}

	/**
	 * ?????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListAccessoryEnquiryMaterial() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AccessoryEnquiryMaterial> list = new ArrayList<AccessoryEnquiryMaterial>();
		String enquiryCode = asString("enquiryCode");
		if (enquiryCode != null && !"".equals(enquiryCode)) {
			map.put("enquiryCode", enquiryCode);
			list = accessoryEnquiryMaterialService.listAccessoryEnquiryMaterial(map, this.getPageInfo());
		}
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListAccessoryEnquiryAccessory() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AccessoryEnquiryAccessory> list = new ArrayList<AccessoryEnquiryAccessory>();
		String enquiryCode = asString("enquiryCode");
		if (enquiryCode != null && !"".equals(enquiryCode)) {
			map.put("enquiryCode", enquiryCode);
			list = accessoryEnquiryAccessoryService.listAccessoryEnquiryAccessory(map, this.getPageInfo());
		}
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListAccessoryEnquiryPacking() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AccessoryEnquiryPacking> list = new ArrayList<AccessoryEnquiryPacking>();
		String enquiryCode = asString("enquiryCode");
		if (enquiryCode != null && !"".equals(enquiryCode)) {
			map.put("enquiryCode", enquiryCode);
			list = accessoryEnquiryPackingService.listAccessoryEnquiryPacking(map, this.getPageInfo());
		}
		this.forwardData(true, list, null);
	}

	/**
	 * ??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListAccessoryEnquiryTechnology() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AccessoryEnquiryTechnology> list = new ArrayList<AccessoryEnquiryTechnology>();
		String enquiryCode = asString("enquiryCode");
		if (enquiryCode != null && !"".equals(enquiryCode)) {
			map.put("enquiryCode", enquiryCode);
			list = accessoryEnquiryTechnologyService.listAccessoryEnquiryTechnology(map, this.getPageInfo());
		}
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListAccessoryEnquiryQuotedCount() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AccessoryEnquiryQuotedCount> list = new ArrayList<AccessoryEnquiryQuotedCount>();
		String enquiryCode = asString("enquiryCode");
		if (enquiryCode != null && !"".equals(enquiryCode)) {
			map.put("enquiryCode", enquiryCode);
			list = accessoryEnquiryQuotedCountService.listAccessoryEnquiryQuotedCount(map, this.getPageInfo());
		}
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doListAccessoryEnquiryElse() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AccessoryEnquiryElse> list = new ArrayList<AccessoryEnquiryElse>();
		String enquiryCode = asString("enquiryCode");
		if (enquiryCode != null && !"".equals(enquiryCode)) {
			map.put("enquiryCode", enquiryCode);
			list = accessoryEnquiryElseService.listAccessoryEnquiryElse(map, this.getPageInfo());
		}
		this.forwardData(true, list, null);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doListAccessoryIntentionSupplier() throws Exception {
		Map<String, Object> map = getAccessoryIntention().toMap();
		map.put("intentionCode", this.asString("intentionCode"));
		List<AccessoryIntentionSupplier> list = accessoryIntentionService.listAccessoryIntentionSupplier(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doCancelSupplier() throws Exception {
		int count = 0;
		String intentionCode = asString("intentionCode");
		if (accessoryIntentionService.editIsOk(intentionCode)) {

			String intentionSupplierCode[] = asString("intentionSupplierCode").split(",");
			for (String code : intentionSupplierCode) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("intentionCode", intentionCode);
				map.put("supplierCode", code);
				List<ApplicationQuoted> applicationQuotedList = ApplicationQuotedServiceImpl.getInstance().listApplicationQuoted(map, null);
				if (applicationQuotedList != null && applicationQuotedList.size() > 0) {
					count++;
					this.forwardData(false, null, "?????????:" + code + "???OA???????????????????????????");
					break;
				} else {
					map.clear();
					map.put("intentionCode", intentionCode);
					map.put("intentionSupplierCode", code);
					accessoryIntentionService.deleteAccessoryIntentionSupplier(map);
				}
			}
			if (count == 0)
				this.forwardData(true, null, "????????????");
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doListAccessoryQuotedElectronic() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("intentionCode", this.asString("intentionCode"));
		List<AccessoryQuotedElectronic> list = accessoryQuotedElectronicService.listAccessoryQuotedElectronic(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDeleteAccessoryQuotedElectronic() throws Exception {
		String intentionCode = asString("intentionCode");
		String quotedCode[] = asStrings("quotedCode[]");
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			for (String code : quotedCode) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("quotedCode", code);
				List<AccessoryProofing> accessoryProofingList = accessoryProofingService.listAccessoryProofing(map, null);
				if (accessoryProofingList != null && accessoryProofingList.size() > 0) {
					this.forwardData(false, null, "????????????????????????????????????????????????");
					return;
				}

				List<ApplicationQuoted> applicationQuotedList = applicationQuotedService.listApplicationQuoted(map, null);
				if (applicationQuotedList != null && applicationQuotedList.size() > 0) {
					this.forwardData(false, null, "????????????OA?????????????????????????????????");
					return;
				}
			}
			accessoryIntentionService.deleteAccessoryQuotedElectronic(quotedCode);
			this.forwardData(true, null, this.getText("public.success"));
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDeleteAccessoryQuotedScan() throws Exception {
		String intentionCode = asString("intentionCode");
		String quotedCode[] = asString("quotedCode").split(",");
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			for (String code : quotedCode) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("quotedCodeScan", code);
				List<QuotedElectronicOfScan> quotedElectronicOfScanList = QuotedElectronicOfScanServiceImpl.getInstance().listQuotedElectronicOfScan(map, null);
				if (quotedElectronicOfScanList != null && quotedElectronicOfScanList.size() > 0) {
					this.forwardData(false, null, "??????????????????OA??????????????????????????????????????????");
					return;
				}
			}
			accessoryIntentionService.deleteAccessoryQuotedScan(quotedCode);
			this.forwardData(true, null, this.getText("public.success"));
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDeleteAccessoryProofing() throws Exception {
		String intentionCode = asString("intentionCode");
		String proofingCode[] = asString("proofingCode").split(",");
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			accessoryIntentionService.deleteAccessoryProofing(proofingCode);
			this.forwardData(true, null, this.getText("public.success"));
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ?????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDeleteAccessoryIntention() throws Exception {
		String intentionCode[] = asString("intentionCode").split(",");
		Boolean isOk = true;
		Boolean otherOk = true;
		for (String code : intentionCode) {
			if (!accessoryIntentionService.editIsOk(code)) {
				isOk = false;
				break;
			}
		}
		for (String code : intentionCode) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("intentionCode", code);
			List<ApplicationQuoted> applicationQuotedList = applicationQuotedService.listApplicationQuoted(map, null);
			if (applicationQuotedList != null && applicationQuotedList.size() > 0) {
				otherOk = false;
				break;
			}
		}
		if (isOk) {
			if (otherOk) {
				accessoryIntentionService.deleteAccessoryIntention(intentionCode);
				this.forwardData(true, null, this.getText("public.success"));
			} else {
				this.forwardData(false, null, "??????????????????OA?????????????????????");
			}
		} else {
			this.forwardData(false, null, "????????????????????????????????????");
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDownloadAccessoryQuotedElectronic() throws Exception {
		AccessoryQuotedElectronic accessoryQuotedElectronic = accessoryQuotedElectronicService.loadAccessoryQuotedElectronic(asString("quotedCode"));
		String path = accessoryQuotedElectronic.getPath();
		if (path != null) {
			path = "upload/".concat(path);
			this.forwardDownload(path);
		} else {
			this.forwardData(false, null, "???????????????");
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDownloadAccessoryQuotedScan() throws Exception {
		AccessoryQuotedScan accessoryQuotedScan = accessoryQuotedScanService.loadAccessoryQuotedScan(asString("quotedCode"));
		String path = accessoryQuotedScan.getPath();
		if (path != null) {
			path = "upload/".concat(path);
			this.forwardDownload(path);
		} else {
			this.forwardData(false, null, "???????????????");
		}
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doListAccessoryQuotedScan() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("intentionCode", this.asString("intentionCode"));
		map.put("enquiryCode", this.asString("enquiryCode"));
		map.put("supplierCode", this.asString("supplierCode"));
		map.put("enquiryCodes", this.asString("enquiryCodes"));
		map.put("supplierCodes", this.asString("supplierCodes"));
		List<AccessoryQuotedScan> list = accessoryQuotedScanService.listAccessoryQuotedScan(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doListAccessoryProofing() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("intentionCode", this.asString("intentionCode"));
		List<AccessoryProofing> list = accessoryProofingService.listAccessoryProofing(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * ????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryIntention")
	public void doExportAccessoryProofingToExcel() throws Exception {
		AccessoryIntention accessoryIntention = this.accessoryIntentionService.loadAccessoryIntention(asString("intentionCode"));
		String intentionName = accessoryIntention.getIntentionName();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("intentionCode", asString("intentionCode"));
		List<AccessoryProofing> accessoryProofingList = accessoryProofingService.listAccessoryProofing(map, null);
		for (AccessoryProofing accessoryProofing : accessoryProofingList) {
			if (accessoryProofing.getRemarks() == null) {
				accessoryProofing.setProofingYorS("???");
			} else {
				accessoryProofing.setProofingYorS("???");
			}
			if (accessoryProofing.getSupplierCode() == null || "null".equals(accessoryProofing.getSupplierCode())) {
				accessoryProofing.setSupplierCode(accessoryProofing.getIntentionSupplierCode());
			}
			if (accessoryProofing.getSupplierName() == null || "null".equals(accessoryProofing.getSupplierName())) {
				accessoryProofing.setSupplierName(accessoryProofing.getIntentionSupplierName());
			}
			if (accessoryProofing.getPath() != null) {
				accessoryProofing.setHavePicture("???");
			} else {
				accessoryProofing.setHavePicture("???");
			}
		}
		String excelName = intentionName + "_????????????.xlsx";
		excelName = ExcelUtils.getEncodeFileName(excelName);
		this.putObject("accessoryProofingList", accessoryProofingList);
		this.forwardDownload("excel/sco/accessoryIntention/exportAccessoryProofing.xlsx", excelName);

	}

	/**
	 * ?????????????????????Excel
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doExportAccessoryEnquiryToExcel() throws Exception {
		String enquiryCode = asString("enquiryCode");
		String supplierCode = asString("supplierCode");
		String enquiryDate = asString("enquiryDate");
		String receiveDate = asString("receiveDate");
		String intentionCode = asString("intentionCode");
		String intentionName = "";
		// String supplierName = "";
		AccessoryIntention accessoryIntention = accessoryIntentionService.loadAccessoryIntention(intentionCode);
		/*
		 * Map<String, Object> map1 = new HashMap<String, Object>();
		 * map1.put("supplierCode", supplierCode); AccessoryIntentionSupplier
		 * accessoryIntentionSupplier =
		 * accessoryIntentionService.loadIntentionSupplier(map1);
		 */
		if (accessoryIntention != null)
			intentionName = accessoryIntention.getIntentionName();
		/*
		 * if (accessoryIntentionSupplier != null) supplierName =
		 * accessoryIntentionSupplier.getIntentionSupplierName();
		 */
		ServletOutputStream out = response.getOutputStream();
		String fileName = intentionName + "_".concat(DateUtils.formateDateTime()).concat(".xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(Constant.DEFAULT_ENCODED_1), Constant.DEFAULT_ENCODED_2) + "\"");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enquiryCode", enquiryCode);
		map.put("supplierCode", supplierCode);
		map.put("enquiryDate", enquiryDate);
		map.put("receiveDate", receiveDate);
		map.put("intentionCode", intentionCode);
		this.accessoryIntentionService.exportAccessoryEnquiryToExcel(map, out);
	}

	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doUploadQuotedExcel() throws Exception {
		String quotedCode = masterDataTypeService.nextID("S_ACCESSORY_QUOTED_ELECTRONIC");
		String intentionCode = this.asString("intentionCode");
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			String enquiryCode = this.asString("enquiryCode");
			String intentionSupplierCode = this.asString("intentionSupplierCode");// ??????????????????
			// Date quotedDate = this.asDate("quotedDate");
			Date quotedDate = this.asDate("quotedDate", "yyyy-MM-dd HH:mm:ss");
			List<File> fList = null;
			String path = null;
			String accIntenQuotedelectronicPath = PathUtils.getAccessoryintentionQuotedelectronicFileUploadPath();
			if (this.getUploads() != null && this.getUploads()[0] != null) {
				fList = this.doUploadBySaveDir(accIntenQuotedelectronicPath);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("enquiryCode", enquiryCode);
			map.put("intentionSupplierCode", intentionSupplierCode);
			map.put("quotedDate", quotedDate);
			map.put("intentionCode", intentionCode);
			map.put("quotedCode", quotedCode);
			String check = accessoryIntentionService.insertUploadQuotedFromExcel(this.getUploads()[0], map);
			if (check == null || "".equals(check)) {

				if (fList != null) {
					path = (accIntenQuotedelectronicPath).replaceAll(ConfigPath.getUploadFilePath(), "").concat(fList.get(0).getName());// ????????????
					AccessoryQuotedElectronic accessoryQuotedElectronic = new AccessoryQuotedElectronic();
					accessoryQuotedElectronic.setPath(path);
					accessoryQuotedElectronic.setQuotedCode(quotedCode);
					accessoryQuotedElectronicService.updateAccessoryQuotedElectronic(accessoryQuotedElectronic);
					this.forwardData(true, "", "");
				}

			} else {
				path = (accIntenQuotedelectronicPath).replaceAll(ConfigPath.getUploadFilePath(), "").concat(fList.get(0).getName());// ????????????
				path = ConfigPath.getUploadFilePath().concat(path);
				File file = new File(path);
				if (file.exists())
					file.delete();
				LoggerUtil.logger.error("AccessoryIntentionAction.doUploadQuotedExcel????????????[" + file.getPath() + "]");
				this.session.put("check", check);
				this.forwardData(false, "", check);
			}
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ????????????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doUploadScanQuotedExcel() throws Exception {

		String intentionCode = this.asString("intentionCode");
		if (accessoryIntentionService.editIsOk(intentionCode)) {
			try {
				String quotedCode = masterDataTypeService.nextID("S_ACCESSORY_QUOTED_ELECTRONIC");
				String enquiryCode = this.asString("enquiryCode");
				String intentionSupplierCode = this.asString("intentionSupplierCode");// ??????????????????
				Date quotedDate = this.asDate("quotedDate", "yyyy-MM-dd HH:mm:ss");
				List<File> fList = null;
				String path = null;
				String accIntenQuotedscanPath = PathUtils.getAccessoryintentionQuotedscanFileUploadPath();
				if (this.getUploads() != null && this.getUploads()[0] != null) {
					fList = this.doUploadBySaveDir(accIntenQuotedscanPath);
				}
				if (fList != null) {
					path = (accIntenQuotedscanPath).replaceAll(ConfigPath.getUploadFilePath(), "").concat(fList.get(0).getName());// ????????????
				}
				AccessoryQuotedScan accessoryQuotedScan = new AccessoryQuotedScan();
				accessoryQuotedScan.setPath(path);
				accessoryQuotedScan.setQuotedCode(quotedCode);
				accessoryQuotedScan.setEnquiryCode(enquiryCode);
				accessoryQuotedScan.setIntentionCode(intentionCode);

				accessoryQuotedScan.setIntentionSupplierCode(intentionSupplierCode);

				accessoryQuotedScan.setQuotedDate(quotedDate);
				accessoryQuotedScanService.insertAccessoryQuotedScan(accessoryQuotedScan);
				this.forwardData(true, "", "");
			} catch (Exception e) {
				this.forwardData(false, "", "????????????");
				e.printStackTrace();
			}
			
		} else {
			this.forwardData(false, null, "??????????????????????????????");
		}
	}

	/**
	 * ??????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDownloadProofingPicture() {
		String path = "upload/".concat(this.asString("path"));
		this.forwardDownload(path);
	}

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	private AccessoryIntention getAccessoryIntention() throws Exception {
		AccessoryIntention accessoryIntention = new AccessoryIntention();
		this.asBean(accessoryIntention);
		return accessoryIntention;
	}

	private AccessoryEnquiry getAccessoryEnquiry() throws Exception {
		AccessoryEnquiry accessoryEnquiry = new AccessoryEnquiry();
		this.asBean(accessoryEnquiry);
		return accessoryEnquiry;
	}

	private AccessoryProofing getAccessoryProofing() throws Exception {
		AccessoryProofing accessoryProofing = new AccessoryProofing();
		this.asBean(accessoryProofing);
		return accessoryProofing;
	}

	/**
	 * ???????????????????????????
	 * 
	 * @throws Exception
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doIfApproved() throws Exception {
		AccessoryIntention accessoryIntention = this.accessoryIntentionService.loadAccessoryIntention(asString("intentionCode"));
		boolean flag = (accessoryIntention == null ? false : true);
		if (flag) {
			this.forwardData(flag, null, null);
			return;
		} else {
			this.forwardData(flag, null, "??????????????????????????????????????????!");
		}
	}
}
