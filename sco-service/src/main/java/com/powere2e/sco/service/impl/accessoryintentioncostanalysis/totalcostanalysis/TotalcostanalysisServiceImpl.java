package com.powere2e.sco.service.impl.accessoryintentioncostanalysis.totalcostanalysis;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.config.ConfigPath;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.frame.commons.service.impl.ServiceImpl;
import com.powere2e.frame.web.exception.EscmException;
import com.powere2e.sco.common.service.BusinessConstants;
import com.powere2e.sco.common.utils.ExcelUtils;
import com.powere2e.sco.common.utils.FreeMarkerUtil;
import com.powere2e.sco.common.utils.LoggerUtil;
import com.powere2e.sco.interfaces.dao.accessoryintentioncostanalysis.totalcostanalysis.TotalcostanalysisDao;
import com.powere2e.sco.interfaces.service.accessoryintention.AccessoryQuotedElectronicService;
import com.powere2e.sco.interfaces.service.accessoryintentioncostanalysis.totalcostanalysis.TotalcostanalysisService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.ApplicationQuotedService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.ApplicationReportAccessoryService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.DhInfoService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.OaApplicationService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.SubscribeAccessoryService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.WlInfoService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.committeeApply.supplierattachment.QuotedElectronicOfScanService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.committeeApply.supplierattachment.SupplierAttachmentAService;
import com.powere2e.sco.interfaces.service.accessoryoaapplication.nonFoodApply.reportanalysis.NonFoodReportAnalysisService;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryAccessory;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryElse;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryMaterial;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryPacking;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryTechnology;
import com.powere2e.sco.model.accessoryintention.AccessoryIntention;
import com.powere2e.sco.model.accessoryintention.AccessoryQuotedElectronic;
import com.powere2e.sco.model.accessoryintentioncostanalysis.totalcostanalysis.QuotedDetailForm;
import com.powere2e.sco.model.accessoryintentioncostanalysis.totalcostanalysis.QuotedForm;
import com.powere2e.sco.model.accessoryintentioncostanalysis.totalcostanalysis.SupplierForm;
import com.powere2e.sco.model.accessoryintentioncostanalysis.totalcostanalysis.Totalcostanalysis;
import com.powere2e.sco.model.accessoryintentioncostanalysis.totalcostanalysis.TotalcostanalysisForm;
import com.powere2e.sco.model.accessoryoaapplication.ApplicationQuoted;
import com.powere2e.sco.model.accessoryoaapplication.ApplicationReportAccessory;
import com.powere2e.sco.model.accessoryoaapplication.OaApplication;
import com.powere2e.sco.model.accessoryoaapplication.SubscribeAccessory;
import com.powere2e.sco.model.accessoryoaapplication.WlInfo;
import com.powere2e.sco.model.reports.Reports;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryIntentionServiceImpl;
import com.powere2e.sco.service.impl.accessoryoaapplication.committeeApply.reportanalysis.CommitteeReportAnalysisServiceImpl;
import com.powere2e.sco.service.impl.accessoryoaapplication.committeeApply.supplierattachment.QuotedElectronicOfScanServiceImpl;
import com.powere2e.sco.service.impl.accessoryoaapplication.committeeApply.suppliercertificate.SupplierCertificateAServiceImpl;
import com.powere2e.sco.service.impl.reports.ReportsServiceImpl;

/**
 * ???????????????OA??????????????????
 * 
 * @author gavin.xu
 * @version 1.0
 * @since 2015???4???20???
 */
public class TotalcostanalysisServiceImpl extends ServiceImpl implements TotalcostanalysisService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5497243415785782818L;
	private ApplicationReportAccessoryService applicationReportAccessoryService;
	private SubscribeAccessoryService subscribeAccessoryService;
	private OaApplicationService oaApplicationService;
	private ApplicationQuotedService applicationQuotedService;
	private AccessoryQuotedElectronicService accessoryQuotedElectronicService;
	private DhInfoService dhInfoService;
	private WlInfoService wlInfoService;
	private NonFoodReportAnalysisService nonFoodReportAnalysisService;
	private SupplierAttachmentAService supplierAttachmentAService;
	private QuotedElectronicOfScanService quotedElectronicOfScanService;
	private TotalcostanalysisDao totalcostanalysisDao;

	public static TotalcostanalysisService getInstance() {
		return (TotalcostanalysisService) ConfigFactory.getInstance().getBean("totalcostanalysisService");
	}

	public ApplicationReportAccessoryService getApplicationReportAccessoryService() {
		return applicationReportAccessoryService;
	}

	public void setApplicationReportAccessoryService(ApplicationReportAccessoryService applicationReportAccessoryService) {
		this.applicationReportAccessoryService = applicationReportAccessoryService;
	}

	public SubscribeAccessoryService getSubscribeAccessoryService() {
		return subscribeAccessoryService;
	}

	public void setSubscribeAccessoryService(SubscribeAccessoryService subscribeAccessoryService) {
		this.subscribeAccessoryService = subscribeAccessoryService;
	}

	public OaApplicationService getOaApplicationService() {
		return oaApplicationService;
	}

	public void setOaApplicationService(OaApplicationService oaApplicationService) {
		this.oaApplicationService = oaApplicationService;
	}

	public ApplicationQuotedService getApplicationQuotedService() {
		return applicationQuotedService;
	}

	public void setApplicationQuotedService(ApplicationQuotedService applicationQuotedService) {
		this.applicationQuotedService = applicationQuotedService;
	}

	public QuotedElectronicOfScanService getQuotedElectronicOfScanService() {
		return quotedElectronicOfScanService;
	}

	public void setQuotedElectronicOfScanService(QuotedElectronicOfScanService quotedElectronicOfScanService) {
		this.quotedElectronicOfScanService = quotedElectronicOfScanService;
	}

	public DhInfoService getDhInfoService() {
		return dhInfoService;
	}

	public void setDhInfoService(DhInfoService dhInfoService) {
		this.dhInfoService = dhInfoService;
	}

	public WlInfoService getWlInfoService() {
		return wlInfoService;
	}

	public void setWlInfoService(WlInfoService wlInfoService) {
		this.wlInfoService = wlInfoService;
	}

	public NonFoodReportAnalysisService getNonFoodReportAnalysisService() {
		return nonFoodReportAnalysisService;
	}

	public void setNonFoodReportAnalysisService(NonFoodReportAnalysisService nonFoodReportAnalysisService) {
		this.nonFoodReportAnalysisService = nonFoodReportAnalysisService;
	}

	// ???????????????DAO??????
	public TotalcostanalysisDao getTotalcostanalysisDao() {
		return totalcostanalysisDao;
	}

	// ???????????????DAO??????
	public void setTotalcostanalysisDao(TotalcostanalysisDao totalcostanalysisDao) {
		this.totalcostanalysisDao = totalcostanalysisDao;
	}

	public AccessoryQuotedElectronicService getAccessoryQuotedElectronicService() {
		return accessoryQuotedElectronicService;
	}

	public void setAccessoryQuotedElectronicService(AccessoryQuotedElectronicService accessoryQuotedElectronicService) {
		this.accessoryQuotedElectronicService = accessoryQuotedElectronicService;
	}

	public SupplierAttachmentAService getSupplierAttachmentAService() {
		return supplierAttachmentAService;
	}

	public void setSupplierAttachmentAService(SupplierAttachmentAService supplierAttachmentAService) {
		this.supplierAttachmentAService = supplierAttachmentAService;
	}

	@Override
	public String saveSearchDataForm(String fileName, Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableInfo", paraMap.get("tableInfo"));
		String tarPath;
		try {
			tarPath = FreeMarkerUtil.generateHtml("totalCostAnalogyAnalysis/accessoryIntentionCostAnalogyAnalysis.ftl", "accessoryIntentiontotalCostAnalogyAnalysis/totalcostanalysis".concat("/")
					.concat(fileName), map);
		} catch (Exception e) {
			return "???????????????????????????";
		}
		File file = new File(tarPath);// ????????????
		if (file.exists()) {
			try {
				Reports myReport = new Reports(BusinessConstants.myReportType.FQA.toString(), fileName, tarPath.replace(ConfigPath.getUploadFilePath(), ""));
				ReportsServiceImpl.getInstance().insertReports(myReport);
			} catch (Exception e) {
				file.delete();
				LoggerUtil.logger.error("TotalcostanalysisServiceImpl.saveSearchDataForm????????????["+ file.getPath() +"]");
				return "?????????????????????????????????";
			}
		} else {
			return "??????Html????????????";
		}
		return null;
	}

	// ????????????????????????OA?????????????????????
	@Override
	public List<Totalcostanalysis> listTotalcostanalysisIntentionApplication(Map<String, Object> map, PageInfo pageInfo) {
		return totalcostanalysisDao.listTotalcostanalysisIntentionApplication(map, pageInfo);
	}

	// ???????????????????????????????????????????????????OA???????????????
	@Override
	public Totalcostanalysis loadTotalcostanalysisIntentionApplication(String quotedCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("quotedCode", quotedCode);
		return totalcostanalysisDao.loadTotalcostanalysisIntentionApplication(map);
	}

	@Override
	public void insertApplicationReportAccessoryAndSubscribeAccessory(ApplicationReportAccessory applicationReportAccessory, List<SubscribeAccessory> subscribeAccessoryList) {
		applicationReportAccessoryService.deleteApplicationReportAccessory(applicationReportAccessory.getApplicationCode());
		subscribeAccessoryService.deleteSubscribeAccessory(applicationReportAccessory.getApplicationCode());
		applicationReportAccessoryService.insertApplicationReportAccessory(applicationReportAccessory);
		for (SubscribeAccessory subscribeAccessory : subscribeAccessoryList) {
			subscribeAccessoryService.insertSubscribeAccessory(subscribeAccessory);
		}

	}

	@Override
	public Boolean committeeInsertUpdateDeleteIsOk(String quotedCodes, String applicationCode, String applicationType) {
		Boolean isOk = true;
		try {
			String qtc[] = quotedCodes.split(",");
			List<String> applicationCodeList = new ArrayList<String>();
			for (String quotedCode : qtc) {
				String appcode = null;
				ApplicationQuoted applicationQuoted = new ApplicationQuoted();
				applicationQuoted = applicationQuotedService.loadApplicationQuoted(quotedCode);
				if (applicationQuoted != null)
					appcode = applicationQuoted.getApplicationCode();
				if (appcode != null)
					applicationCodeList.add(appcode);
			}
			if (applicationCodeList.size() > 0) {
				for (String code : applicationCodeList) {
					if (!code.equalsIgnoreCase(applicationCode))
						isOk = false;
				}
			}
			OaApplication oaApplication = oaApplicationService.loadOaApplication(applicationCode);
			if (oaApplication != null && !BusinessConstants.ApplicationStatus.W.toString().equals(oaApplication.getApplicationStatus())
					&& !BusinessConstants.ApplicationStatus.CG.toString().equals(oaApplication.getApplicationStatus())) {
				isOk = false;
			}
			if (isOk) {
				try {
					this.insertApplicationQuotedAndOaApplication(quotedCodes, applicationCode, applicationType);
				} catch (Exception e) {
					isOk = false;
				}
			}
		} catch (Exception e) {
			throw new EscmException("????????????????????????");
		}
		return isOk;
	}

	@Override
	public void insertApplicationQuotedAndOaApplication(String quotedCodes, String applicationCode, String applicationType) {
		String qtc[] = quotedCodes.split(",");
		List<ApplicationQuoted> applicationQuotedList = new ArrayList<ApplicationQuoted>();
		for (String quotedCode : qtc) {
			ApplicationQuoted applicationQuoted = new ApplicationQuoted();
			AccessoryQuotedElectronic accessoryQuotedElectronic = new AccessoryQuotedElectronic();
			accessoryQuotedElectronic = accessoryQuotedElectronicService.loadAccessoryQuotedElectronic(quotedCode);
			applicationQuoted.setApplicationCode(applicationCode);
			applicationQuoted.setQuotedCode(quotedCode);
			applicationQuoted.setIntentionCode(accessoryQuotedElectronic.getIntentionCode());
			applicationQuoted.setAccessoryCode(accessoryQuotedElectronic.getIntentionCode());
			applicationQuoted.setEnquiryCode(accessoryQuotedElectronic.getEnquiryCode());
			applicationQuoted.setSupplierCode(accessoryQuotedElectronic.getSupplierCode() == null ? accessoryQuotedElectronic.getIntentionSupplierCode() : accessoryQuotedElectronic.getSupplierCode());
			applicationQuotedList.add(applicationQuoted);
		}
		OaApplication oaApplication = new OaApplication();
		oaApplication.setApplicationCode(applicationCode);
		oaApplication.setApplicationType(applicationType);
		oaApplication.setApplicationStatus(BusinessConstants.ApplicationStatus.CG.toString());
		OaApplication oaApplicationNow = oaApplicationService.loadOaApplication(applicationCode);
		if (oaApplicationNow == null) {
			oaApplicationService.insertOaApplication(oaApplication);
			for (ApplicationQuoted applicationQuotedNow : applicationQuotedList) {
				applicationQuotedService.insertApplicationQuoted(applicationQuotedNow);
			}
		}
	}

	@Override
	public void deleteDhInfo(String[] dhinfoId) {
		for (String id : dhinfoId) {
			dhInfoService.deleteDhInfo(id);
		}
	}

	@Override
	public void deleteApplication(String[] applicationCodes) {
		for (String applicationCode : applicationCodes) {
			oaApplicationService.deleteOaApplication(applicationCode);
			applicationQuotedService.deleteApplicationQuotedFromCode(applicationCode);
			dhInfoService.deleteDhInfoFromCode(applicationCode);
			subscribeAccessoryService.deleteSubscribeAccessory(applicationCode);
		}

	}

	@Override
	public void closeApplication(String[] applicationCodes) {
		for (String applicationCode : applicationCodes) {
			OaApplication oaApplication = new OaApplication();
			oaApplication = oaApplicationService.loadOaApplication(applicationCode);
			oaApplication.setApplicationStatus(BusinessConstants.ApplicationStatus.GB.toString());
			oaApplicationService.updateOaApplication(oaApplication);
		}
	}

	@Override
	public void undoOaSynchronous(String[] applicationCodes) {
		for (String applicationCode : applicationCodes) {
			OaApplication oaApplication = new OaApplication();
			oaApplication.setApplicationStatus(BusinessConstants.ApplicationStatus.CG.toString());
			oaApplication.setApplicationCode(applicationCode);
			oaApplicationService.updateOaApplicationForUndo(oaApplication);
		}

	}

	@Override
	public String checkApplication(String applicationCode) {
		String check = "";
		if (applicationReportAccessoryService.loadApplicationReportAccessory(applicationCode) == null)
			check += "??????????????????.";
		if (!CommitteeReportAnalysisServiceImpl.getInstance().ifApplicationExistsMCA(applicationCode))
			check += "?????????????????????.";
		if (!CommitteeReportAnalysisServiceImpl.getInstance().ifApplicationExistsPurOrder(applicationCode))
			check += "???????????????.";
		if (!QuotedElectronicOfScanServiceImpl.getInstance().searchCount(applicationCode))
			check += "????????????????????????????????????.";
		if (!SupplierCertificateAServiceImpl.getInstance().searchCount(applicationCode))
			check += "?????????????????????????????????????????????????????????????????????????????????????????????.";
		/*
		 * Map<String, Object> map = new HashMap<String, Object>();
		 * map.put("applicationCode", applicationCode); List<Reports>
		 * list=nonFoodReportAnalysisService.listAnalysisReportUpload(map,
		 * null); if(list!=null &&list.size()>0 ) check+="?????????????????????."; String
		 * quotedCodes="" List<ApplicationQuoted> applicationQuotedList=
		 * applicationQuotedService.listApplicationQuoted(map, null);
		 * for(ApplicationQuoted applicationQuoted:applicationQuotedList){
		 * quotedCodes=quotedCodes+applicationQuoted.getQuotedCode()+","; }
		 * Map<String, Object> map1 = new HashMap<String, Object>();
		 * map.put("quotedCode", quotedCodes.split(","));
		 * List<SupplierAttachmentA> listSupplierAttachmentA =
		 * supplierAttachmentAService.listQuotedRecord(map, null);
		 */
		return check;
	}

	@Override
	public void allowOaSynchronous(String applicationCode) {
		OaApplication oaApplication = new OaApplication();
		oaApplication.setApplicationStatus(BusinessConstants.ApplicationStatus.YX.toString());
		oaApplication.setApplicationCode(applicationCode);
		oaApplicationService.updateOaApplicationForAllow(oaApplication);

	}

	@Override
	public void insertWlInfo(WlInfo[] dataArray) {
		for (WlInfo wlInfo : dataArray) {
			wlInfoService.insertWlInfo(wlInfo);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accessorySapCode", wlInfo.getAccessorySapCode());
			map.put("supplierSapCode", wlInfo.getSupplierSapCode());
			map.put("intentionCode", wlInfo.getIntentionCode());
			totalcostanalysisDao.updateIntentionSupplierAccessory(map);
		}
	}

	@Override
	public List<QuotedForm> listQuotedForm(Map<String, Object> map, PageInfo pageInfo) {
		return totalcostanalysisDao.listQuotedForm(map, pageInfo);
	}

	@Override
	public List<QuotedDetailForm> listQuotedDetailForm(Map<String, Object> map, PageInfo pageInfo) {
		// TODO Auto-generated method stub
		return totalcostanalysisDao.listQuotedDetailForm(map, pageInfo);
	}

	@Override
	public void exportExcel(TotalcostanalysisForm totalcostanalysisForm, ServletOutputStream out) {
		Workbook wb = new XSSFWorkbook();
		XSSFFont blueFont = (XSSFFont) wb.createFont();
		XSSFColor blueColor = new XSSFColor(Color.BLUE);
		blueFont.setColor(blueColor);// ??????
		blueFont.setFontName("????????????"); 
		blueFont.setBold(true);
		XSSFFont blackFont = (XSSFFont) wb.createFont();
		blackFont.setFontName("????????????"); 
		blackFont.setBold(true);
		 XSSFFont fontYH = (XSSFFont) wb.createFont();//??????????????????  
		 fontYH.setFontName("????????????"); 
		//SXSSFWorkbook wb = ExcelUtils.createSXSSFWorkbook();
		Sheet sheet = wb.createSheet("????????????????????????");
		CellStyle strStyle = ExcelUtils.getDefaultStringStyle(wb);// ???????????????
		// CellStyle dateStyle = ExcelUtils.getDefaultDateStyle(wb);// ???????????????
		// CellStyle amtStyle = ExcelUtils.getDefaultAmoutStyle(wb);// ????????????
		CellStyle strStyleCenter = ExcelUtils.getDefaultStringStyle(wb);// ???????????????????????????
		ExcelUtils.setAlign(strStyleCenter, "left", "top", true);
		CellStyle strStyleCenterCenter = ExcelUtils.getDefaultStringStyle(wb);// ??????????????????
		ExcelUtils.setAlign(strStyleCenterCenter, "center", "center", true);
		CellStyle strStyleCenterFont = ExcelUtils.getDefaultStringStyle(wb);// ???????????????????????????
		Font font  = wb.createFont();      
		font.setFontHeightInPoints((short) 11);//??????      
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);//??????    
		font.setFontName("????????????"); 
		strStyleCenterFont.setFont(font);
		strStyleCenter.setFont(fontYH);
		strStyle.setFont(fontYH);
		strStyleCenterCenter.setFont(fontYH);
		ExcelUtils.setAlign(strStyleCenterFont, "center", "center", true);
		CellStyle strStyleLeftFont = ExcelUtils.getDefaultStringStyle(wb);// ???????????????????????????
		strStyleLeftFont.setFont(font);
		ExcelUtils.setAlign(strStyleLeftFont, "left", "top", true);
		Row row = sheet.createRow(0);
		
		// row.setHeight((short) (50 * 20));
		// 4?????????????????????
		sheet.setColumnWidth(0, 60 * 70);
		sheet.setColumnWidth(1, 80 * 90);
		sheet.setColumnWidth(2, 60 * 70);
		sheet.setColumnWidth(3, 60 * 70);
		for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size() + 4; i++) {
			sheet.setColumnWidth(4 + i, 90 * 90);
		}
		CellStyle firstRowStyle = ExcelUtils.getHeaderCellStyle(wb, 11);
		CellStyle firstRowStyleLeft = ExcelUtils.getHeaderCellStyle(wb, 11);
		ExcelUtils.setAlign(firstRowStyleLeft, "left", "center", true);
		firstRowStyle.setWrapText(true);
		strStyle.setWrapText(true);
		strStyleCenter.setWrapText(true);
		int rows=0;//????????????
		for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
			String value = "";
			SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getIntentionSupplierCodeShow())){
				value += "???????????????-???????????????"+"\r" + supplierForm.getIntentionSupplierCode() + "-" + supplierForm.getIntentionSupplierName() ;
				Cell cell;
				if(i==0){
				cell = row.createCell(0);
				cell.setCellValue("???????????????");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("????????????????????????");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("????????????");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("????????????");
				cell.setCellStyle(strStyleCenterFont);
				}
			cell = row.createCell(4 + i);
			cell.setCellValue(value);
			cell.setCellStyle(strStyleLeftFont);
				if(i==0){
			rows++;
				}
				
			}
			
		}
		for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
			String value = "";
			SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getContactsShow())){
				value += "????????????" + supplierForm.getContacts() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
				
			}
			}
		for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
			String value = "";
			SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getPhoneShow())){
				value += "???????????????" + supplierForm.getPhone() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
		for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
			String value = "";
			SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getCompanySiteShow())){
				value += "???????????????"+"\r" + supplierForm.getCompanySite() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getFactorySiteShow())){
				value += "???????????????"+"\r" + supplierForm.getFactorySite();
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getRegisterCapitalShow())){
				value += "????????????(??????)???" + supplierForm.getRegisterCapital() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getYearTurnoverShow())){
				value += "????????????(??????)???" + supplierForm.getYearTurnover() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getFactoryAreaShow())){
				value += "????????????(???)???" + formatNumber(supplierForm.getFactoryArea()) ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getStaffCountShow())){
				value += "????????????" + formatNumber(supplierForm.getStaffCount()).replace(".000", "") ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getDailyCapacityShow())){
				value += "????????????" + supplierForm.getDailyCapacity() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getHzgppShow())){
				value += "??????????????????" + supplierForm.getHzgpp() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getInvoiceTypeShow())){
				value += "????????????(???/??????)???" + supplierForm.getInvoiceType() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getQuotedCurrencyShow())){
				value += "???????????????" + supplierForm.getQuotedCurrency() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getTaxRateShow())){
				value += "??????(%)???" + formatNumber(supplierForm.getTaxRate())+"%" ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
			if (StringUtils.isNotBlank(totalcostanalysisForm.getPaymentTypeShow())){
				value += "???????????????" + supplierForm.getPaymentType() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
			}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
				if (StringUtils.isNotBlank(totalcostanalysisForm.getProofingContentBeforeShow())){
				value += "????????????????????????"+"\r" + supplierForm.getProofingContentBefore() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
				}
			}
			for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
				String value = "";
				SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
				if (StringUtils.isNotBlank(totalcostanalysisForm.getProofingContentBeforeShow())){
				value += "????????????????????????"+"\r" + supplierForm.getProofingEvaluateBefore() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
				}
			}
				for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
					String value = "";
					SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
					if (StringUtils.isNotBlank(totalcostanalysisForm.getProofingContentAfterShow())){
				value += "???????????????????????????"+"\r" + supplierForm.getProofingContentAfter() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
					}
				}
				for (int i = 0; i < totalcostanalysisForm.getSupplierFormList().size(); i++) {
					String value = "";
					SupplierForm supplierForm = totalcostanalysisForm.getSupplierFormList().get(i);
					if (StringUtils.isNotBlank(totalcostanalysisForm.getProofingContentAfterShow())){
				value += "???????????????????????????"+"\r" + supplierForm.getProofingEvaluateAfter() ;
				Cell cell;
				if(i==0){
				row = sheet.createRow(rows);
				
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(1);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(2);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				cell = row.createCell(3);
				cell.setCellValue("");
				cell.setCellStyle(strStyleCenterFont);
				}
				cell = row.createCell(4 + i);
				cell.setCellValue(value);
				cell.setCellStyle(strStyleCenter);
				if(i==0){
					rows++;
						}
					}
				}
				ExcelUtils.addMergedRegion(sheet, "A1" + ":" + "A" + (rows));
				ExcelUtils.addMergedRegion(sheet, "B1" + ":" + "B" + (rows));
				ExcelUtils.addMergedRegion(sheet, "C1" + ":" + "C" + (rows));
				ExcelUtils.addMergedRegion(sheet, "D1" + ":" + "D" + (rows));
		int line = rows;
		for (int i = 0; i < totalcostanalysisForm.getIntentionFormList().size(); i++) {
			for (int m = 0; m < totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().size(); m++) {
				for (int n = 0; n < totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().size(); n++) {
					row = sheet.createRow(line);
					Cell cell = row.createCell(0);
					AccessoryIntention accessoryIntention=AccessoryIntentionServiceImpl.getInstance().loadAccessoryIntention(totalcostanalysisForm.getIntentionFormList().get(i).getIntentionCode());
					cell.setCellValue(accessoryIntention.getIntentionName());
					cell.setCellStyle(strStyleCenterFont);
					// ??????
					int[] length=new int[4];
					cell = row.createCell(1);
					String gg = "";
					if (n == 0) {
						gg += "???????????????|" + "??????|" + "??????|" + "\r\n";
						List<AccessoryEnquiryMaterial> accessoryEnquiryMaterialList = totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryMaterialList();
						if (accessoryEnquiryMaterialList != null) {
							for (AccessoryEnquiryMaterial accessoryEnquiryMaterial : accessoryEnquiryMaterialList) {
								gg += accessoryEnquiryMaterial.getMaterialName() + "|" + accessoryEnquiryMaterial.getMaterial() + "|" + accessoryEnquiryMaterial.getMaterialSize() + "\r\n";
							}
						}
						length[0]=gg.length();
						gg += "????????????|" + "??????|" + "??????|" + "\r\n";
						List<AccessoryEnquiryAccessory> accessoryEnquiryAccessoryList = totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryAccessoryList();
						if (accessoryEnquiryAccessoryList != null) {
							for (AccessoryEnquiryAccessory accessoryEnquiryAccessory : accessoryEnquiryAccessoryList) {
								gg += accessoryEnquiryAccessory.getAccessoryName() + "|" + accessoryEnquiryAccessory.getMaterial() + "|" + accessoryEnquiryAccessory.getMaterialSize() + "\r\n";
							}
						}
						length[1]=gg.length();
						gg += "????????????????????????|" + "??????|" + "??????|" + "\r\n";
						List<AccessoryEnquiryPacking> accessoryEnquiryPackingList = totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryPackingList();
						if (accessoryEnquiryPackingList != null) {
							for (AccessoryEnquiryPacking accessoryEnquiryPacking : accessoryEnquiryPackingList) {
								gg += accessoryEnquiryPacking.getPackingName() + "|" + accessoryEnquiryPacking.getPackingMaterial() + "|" + accessoryEnquiryPacking.getMaterialSize() + "\r\n";
							}
						}
						length[2]=gg.length();
						gg += "????????????|" + "????????????|" + "\r\n";
						List<AccessoryEnquiryTechnology> accessoryEnquiryTechnologyList = totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m)
								.getAccessoryEnquiryTechnologyList();
						if (accessoryEnquiryTechnologyList != null) {
							for (AccessoryEnquiryTechnology accessoryEnquiryTechnology : accessoryEnquiryTechnologyList) {
								gg += accessoryEnquiryTechnology.getTechnologyName() + "|" + accessoryEnquiryTechnology.getTechnologyInfo() + "\r\n";
							}
						}
						length[3]=gg.length();
						gg += "????????????|" + "????????????|" + "\r\n";
						List<AccessoryEnquiryElse> accessoryEnquiryElseList = totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryElseList();
						if (accessoryEnquiryElseList != null) {
							for (AccessoryEnquiryElse accessoryEnquiryElse : accessoryEnquiryElseList) {
								gg += accessoryEnquiryElse.getName() + "|" + accessoryEnquiryElse.getInfo() + "\r\n";
							}
						}
					}
					if (n == 0) {
					XSSFRichTextString richString = new XSSFRichTextString(gg);
					richString.applyFont(0, 12, blueFont);
					richString.applyFont(length[0], length[0]+11, blueFont);
					richString.applyFont(length[1], length[1]+15, blueFont);
					richString.applyFont(length[2], length[2]+10, blueFont);
					richString.applyFont(length[3], length[3]+10, blueFont);
					cell.setCellValue(richString);
					}else{
						cell.setCellValue(gg);
					}
					//cell.setCellValue(gg);
					cell.setCellStyle(strStyleCenter);
					// ????????????
					cell = row.createCell(2);
					cell.setCellValue(formatNumber(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedCount()).replace(".000", ""));
					cell.setCellStyle(strStyleCenter);
					// ??????????????????
					cell = row.createCell(3);
					cell.setCellValue(formatNumber(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getPurchaseCount()).replace(".000", ""));
					cell.setCellStyle(strStyleCenter);
				
					for (int k = 0; k < totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().size(); k++) {
						cell = row.createCell(4 + k);
						String supValue = "";
						List<Integer> strLengthList=new ArrayList<Integer>();
						if (totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().get(k) != null
								&& totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().get(k)
										.getLastQuotedRank() != null) {
							supValue +=StringUtils.isBlank(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().get(k)
									.getLastQuotedRank())?"": "???????????????????????????"
									+ totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().get(k)
											.getLastQuotedRank() + "\r\n";
							supValue += StringUtils.isBlank(formatNumber(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().get(k)
									.getLastProductionCycle()))?"":"??????????????????????????????(???)???"
									+ formatNumber(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().get(k)
											.getLastProductionCycle()).replace(".000", "") + "\r\n";
							strLengthList.add(supValue.length());
						}
						if (totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().get(k)
								.getQuotedDetailFormList() != null) {
							for (int x = 0; x < totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n).getQuotedFormList().get(k)
									.getQuotedDetailFormList().size(); x++) {
								SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
								QuotedDetailForm quotedDetailForm = totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().get(n)
										.getQuotedFormList().get(k).getQuotedDetailFormList().get(x);
								if (totalcostanalysisForm.getAllbjd()!=null&&totalcostanalysisForm.getAllbjd()) {
									supValue += "???" + (x + 1) + "??????????????????" + time.format(quotedDetailForm.getQuotedDate()) + "\r\n";
											
									strLengthList.add(supValue.length());
									supValue +="????????????(???)???" + formatNumber(quotedDetailForm.getUnitPrice()) + "\r\n"+"????????????(???)???" + formatNumber(quotedDetailForm.getQuotedTotal())+ "\r\n";
								} else {
									supValue += "???????????????????????????" + time.format(quotedDetailForm.getQuotedDate()) + "\r";
									strLengthList.add(supValue.length());
									supValue +="????????????(???)???" + formatNumber(quotedDetailForm.getUnitPrice()) + "\r\n"+"????????????(???)???" + formatNumber(quotedDetailForm.getQuotedTotal()) + "\r\n";
								}
							}
						}
						if(StringUtils.isNotBlank(supValue)&&strLengthList.size()>1){
							XSSFRichTextString richString = new XSSFRichTextString(supValue);
							if(totalcostanalysisForm.getAllbjd()!=null&&totalcostanalysisForm.getAllbjd()){
							richString.applyFont(0, strLengthList.get(0), blackFont);
							for(int x=1;x<strLengthList.size();x++){
								richString.applyFont(strLengthList.get(x)-20, strLengthList.get(x), blackFont);	
							}
							}else{
								richString.applyFont(0, strLengthList.get(0), blackFont);
								for(int x=1;x<strLengthList.size();x++){
									richString.applyFont(strLengthList.get(x)-22, strLengthList.get(x), blackFont);	
								}	
							}
							cell.setCellValue(richString);
						}else{
						cell.setCellValue(supValue);
						}
						cell.setCellStyle(strStyleCenter);
					}
					if(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().size()<=1){
					row.setHeight((short) (28 * gg.length()));
					}
					if(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().size()==2&&gg.length()>450){
						row.setHeight((short) (28 * gg.length()));
						}
					if(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().size()==3&&gg.length()>700){
						row.setHeight((short) (28 * gg.length()));
						}
					if(totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().size()==4&&gg.length()>1000){
						row.setHeight((short) (28 * gg.length()));
						}
					line++;
				}
				ExcelUtils.addMergedRegion(sheet, "B" + (line - totalcostanalysisForm.getIntentionFormList().get(i).getEnquiryList().get(m).getAccessoryEnquiryQuotedCountList().size() + 1) + ":"
						+ "B" + (line));
			}
			ExcelUtils.addMergedRegion(sheet, "A" + (line - totalcostanalysisForm.getIntentionFormList().get(i).getCount() + 1) + ":" + "A" + (line));
		}
		// ExcelUtils.addMergedRegion(sheet, "A" + (rowIndex - listAem.size() +
		// 1) + ":" + "A" + (rowIndex));
		/*
		 * if (totalcostanalysisForm.getIntentionTotalFormList().size() > 1) {
		 * // ?????? for (int i = 0; i <
		 * totalcostanalysisForm.getIntentionTotalFormList().size(); i++) { row
		 * = sheet.createRow(line); Cell cell = row.createCell(0);
		 * cell.setCellValue("?????????????????????????????????:"); cell.setCellStyle(strStyle); cell
		 * = row.createCell(1); cell.setCellValue("");
		 * cell.setCellStyle(strStyleCenter); cell = row.createCell(2);
		 * cell.setCellValue
		 * (totalcostanalysisForm.getIntentionTotalFormList().get
		 * (i).getQuotedCount()); cell.setCellStyle(strStyleCenter); cell =
		 * row.createCell(3); cell.setCellValue("");
		 * cell.setCellStyle(strStyleCenter); ExcelUtils.addMergedRegion(sheet,
		 * "A" + (line + 1) + ":" + "B" + (line + 1)); for (int m = 0; m <
		 * totalcostanalysisForm
		 * .getIntentionTotalFormList().get(i).getQuotedTotalFormList().size();
		 * m++) { cell = row.createCell(4 + m); String hj = ""; hj +=
		 * totalcostanalysisForm
		 * .getIntentionTotalFormList().get(i).getQuotedTotalFormList
		 * ().get(m).getLastQuotedRank() + "\r\n"; for (int n = 0; n <
		 * totalcostanalysisForm
		 * .getIntentionTotalFormList().get(i).getQuotedTotalFormList
		 * ().get(m).getQuotedDetailTotalFormList().size(); n++) { hj += "???" +
		 * (n + 1) + "???????????????" +
		 * df.format(totalcostanalysisForm.getIntentionTotalFormList
		 * ().get(i).getQuotedTotalFormList
		 * ().get(m).getQuotedDetailTotalFormList
		 * ().get(n).getQuotedTotal()).replace(".000", "") + "\r\n"; }
		 * cell.setCellValue(hj); cell.setCellStyle(strStyleCenter); } line++; }
		 * // ??????????????? for (int i = 0; i <
		 * totalcostanalysisForm.getIntentionTotalFormList().size(); i++) { row
		 * = sheet.createRow(line); Cell cell = row.createCell(0);
		 * cell.setCellValue("?????????????????????????????????(????????????):"); cell.setCellStyle(strStyle);
		 * cell = row.createCell(1); cell.setCellValue("");
		 * cell.setCellStyle(strStyleCenter); cell = row.createCell(2);
		 * cell.setCellValue
		 * (totalcostanalysisForm.getIntentionTotalFormList().get
		 * (i).getQuotedCount()); cell.setCellStyle(strStyleCenter); cell =
		 * row.createCell(3); cell.setCellValue("");
		 * cell.setCellStyle(strStyleCenter); ExcelUtils.addMergedRegion(sheet,
		 * "A" + (line + 1) + ":" + "B" + (line + 1)); for (int m = 0; m <
		 * totalcostanalysisForm
		 * .getIntentionTotalFormList().get(i).getQuotedTotalFormList().size();
		 * m++) { cell = row.createCell(4 + m); String hj = ""; hj +=
		 * totalcostanalysisForm
		 * .getIntentionTotalFormList().get(i).getQuotedTotalFormList
		 * ().get(m).getLastQuotedRank() + "\r\n"; for (int n = 0; n <
		 * totalcostanalysisForm
		 * .getIntentionTotalFormList().get(i).getQuotedTotalFormList
		 * ().get(m).getQuotedDetailTotalFormListHs().size(); n++) { hj += "???" +
		 * (n + 1) + "???????????????" +
		 * df.format(totalcostanalysisForm.getIntentionTotalFormList
		 * ().get(i).getQuotedTotalFormList
		 * ().get(m).getQuotedDetailTotalFormListHs
		 * ().get(n).getQuotedTotal()).replace(".000", "") + "\r\n"; }
		 * cell.setCellValue(hj); cell.setCellStyle(strStyleCenter); } line++; }
		 * 
		 * }
		 */
		row = sheet.createRow(line);
		Cell cell = row.createCell(0);
		cell.setCellValue("????????????:"+totalcostanalysisForm.getRemark());
		cell.setCellStyle(strStyle);
		for (int i = 1; i <totalcostanalysisForm.getSupplierFormList().size()+4; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(strStyle);
		}
		ExcelUtils.addMergedRegion(sheet, "A" + (line+1) + ":" + "E" + (line+1));
		try {
			wb.write(out);
		} catch (IOException e) {
			throw new EscmException("public.upload.io.error", new String[] { e.getMessage() });
		} finally {
			try {
				if (out != null){
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				throw new EscmException("public.upload.io.error", new String[] { e.getMessage() });
			}
		}
	}
	/**
	 * ????????????????????????????????????????????????
	 * 
	 * 
	 */
	private String  formatNumber(BigDecimal num)  {
		DecimalFormat df =new DecimalFormat("#,##0.000;(#)");
		df.setRoundingMode(RoundingMode.HALF_UP); 
		if(num!=null){
			return df.format(num);
		}
		return "";
	}
}