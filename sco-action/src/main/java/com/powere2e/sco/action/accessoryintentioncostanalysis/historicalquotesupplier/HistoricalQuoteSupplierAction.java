package com.powere2e.sco.action.accessoryintentioncostanalysis.historicalquotesupplier;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.power.Authority;
import com.powere2e.sco.common.service.BusinessConstants;
import com.powere2e.sco.common.utils.Constant;
import com.powere2e.sco.common.utils.DateUtils;
import com.powere2e.sco.common.utils.UploadUtils;
import com.powere2e.sco.interfaces.service.accessoryintentioncostanalysis.historicalquotesupplier.HistoricalQuoteSupplierService;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryAccessory;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryElse;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryMaterial;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryPacking;
import com.powere2e.sco.model.accessoryintention.AccessoryEnquiryTechnology;
import com.powere2e.sco.model.accessoryintentioncostanalysis.historicalquotesupplier.HistoricalQuoteSupplier;
import com.powere2e.sco.model.accessoryintentioncostanalysis.historicalquotesupplier.HistoricalQuoteSupplierForm;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryAccessoryServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryElseServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryMaterialServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryPackingServiceImpl;
import com.powere2e.sco.service.impl.accessoryintention.AccessoryEnquiryTechnologyServiceImpl;
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
public class HistoricalQuoteSupplierAction extends UploadUtils {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7675979135391063597L;
	private HistoricalQuoteSupplierService historicalQuoteSupplierService;

	/**
	 * ???????????????????????????
	 */
	@Override
	protected void beforeBuild() {
		historicalQuoteSupplierService = (HistoricalQuoteSupplierService) ConfigFactory.getInstance().getBean("historicalQuoteSupplierService");
	}

	/**
	 * ??????????????????grid
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowHistoricalQuoteSupplierGrid() throws Exception {

		this.forwardPage("sco/accessoryIntentionCostAnalysis/historicalQuoteSupplier/historicalQuoteSupplierGrid.ftl");
	}

	/**
	 * ???????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowHistoricalQuoteSupplierSet() throws Exception {
		String rows = this.asString("rows");
		this.putObject("rows", rows);
		this.forwardPage("sco/accessoryIntentionCostAnalysis/historicalQuoteSupplier/historicalQuoteSupplierSetForm.ftl");
	}

	/**
	 * ?????????????????????????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doShowHistoricalQuoteSupplier() throws Exception {
		String rows = this.asString("rows");
		String isYorN = this.asString("isYorN");
		List<HistoricalQuoteSupplierForm> historicalQuoteSupplierFormListTotal = new ArrayList<HistoricalQuoteSupplierForm>();
		JSONArray jsonArr = JSONArray.fromObject(rows);
		HistoricalQuoteSupplier[] dataArray = new HistoricalQuoteSupplier[jsonArr.size()];
		for (int j = 0; j < jsonArr.size(); j++) {
			dataArray[j] = (HistoricalQuoteSupplier) JSONObject.toBean(jsonArr.getJSONObject(j), HistoricalQuoteSupplier.class);
		} // ?????????????????????
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < dataArray.length; i++) {
			map.clear();
			map.put("intentionCode", dataArray[i].getIntentionCode());
			map.put("intentionSupplierCode", dataArray[i].getIntentionSupplierCode());
			if ("???".equals(isYorN))
				map.put("isYorN", "Y");
			List<HistoricalQuoteSupplierForm> historicalQuoteSupplierFormList = historicalQuoteSupplierService.listHistoricalQuoteSupplierForm(map, null);
			if (historicalQuoteSupplierFormList != null && historicalQuoteSupplierFormList.size() > 0) {
				for (HistoricalQuoteSupplierForm historicalQuoteSupplierForm : historicalQuoteSupplierFormList) {
					if (historicalQuoteSupplierForm.getIntentionSupplierName() == null)
						historicalQuoteSupplierForm.setIntentionSupplierName(historicalQuoteSupplierForm.getSupplierName());
					if (historicalQuoteSupplierForm.getReceivedOADate() != null && historicalQuoteSupplierForm.getActualDeliveryDate() != null)
						historicalQuoteSupplierForm.setActualProcessDays(new BigDecimal( this.daysBetween(historicalQuoteSupplierForm.getReceivedOADate(), historicalQuoteSupplierForm.getActualDeliveryDate())+1));
					if (historicalQuoteSupplierForm.getReceivedOADate() != null && historicalQuoteSupplierForm.getNormalProcessDays() != null&& historicalQuoteSupplierForm.getActualDeliveryDate() != null)
						historicalQuoteSupplierForm.setDifferencesDays(new BigDecimal( Math.abs(historicalQuoteSupplierForm.getActualProcessDays().subtract(historicalQuoteSupplierForm.getNormalProcessDays()).intValue())));
					if (historicalQuoteSupplierForm.getOaCompleteDate() != null && historicalQuoteSupplierForm.getOaSubmitDate() != null)
						historicalQuoteSupplierForm.setOaCompleteDays(new BigDecimal(this.daysBetween(historicalQuoteSupplierForm.getOaSubmitDate(), historicalQuoteSupplierForm.getOaCompleteDate())>-1?this.daysBetween(historicalQuoteSupplierForm.getOaSubmitDate(), historicalQuoteSupplierForm.getOaCompleteDate())+1:this.daysBetween(historicalQuoteSupplierForm.getOaSubmitDate(), historicalQuoteSupplierForm.getOaCompleteDate())-1));
					map.clear();
					map.put("enquiryCode", historicalQuoteSupplierForm.getEnquiryCode());
					historicalQuoteSupplierForm.setAccessoryEnquiryPackingList(AccessoryEnquiryPackingServiceImpl.getInstance().listAccessoryEnquiryPacking(map, null));
					historicalQuoteSupplierForm.setAccessoryEnquiryAccessoryList(AccessoryEnquiryAccessoryServiceImpl.getInstance().listAccessoryEnquiryAccessory(map, null));
					historicalQuoteSupplierForm.setAccessoryEnquiryMaterialList(AccessoryEnquiryMaterialServiceImpl.getInstance().listAccessoryEnquiryMaterial(map, null));
					historicalQuoteSupplierForm.setAccessoryEnquiryTechnologyList(AccessoryEnquiryTechnologyServiceImpl.getInstance().listAccessoryEnquiryTechnology(map, null));
					historicalQuoteSupplierForm.setAccessoryEnquiryElseList(AccessoryEnquiryElseServiceImpl.getInstance().listAccessoryEnquiryElse(map, null));
					historicalQuoteSupplierFormListTotal.add(historicalQuoteSupplierForm);
				}
			}
		}
		//JsonConfig jsonConfig = new JsonConfig();    
       // jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor()); 
		//JSONArray object = JSONArray.fromObject(historicalQuoteSupplierFormListTotal,jsonConfig);
		//String json = object.toString().replaceAll("\"", "%");
		String object=JSON.toJSONStringWithDateFormat(historicalQuoteSupplierFormListTotal,"yyyy-MM-dd",SerializerFeature.WriteDateUseDateFormat);
		String json = object.replaceAll("\"", "@%");
		this.putObject("json", json);
		this.putObject("historicalQuoteSupplierFormListTotal", historicalQuoteSupplierFormListTotal);
		this.forwardPage("sco/accessoryIntentionCostAnalysis/historicalQuoteSupplier/historicalQuoteSupplierForm.ftl");
	}
	/**
	 * ?????????Excel
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	@Authority(privilege = "com.powere2e.sco")
	public void doExportDataToExcel() throws Exception {
		String rows = this.asString("date");
		/*System.out.println(rows);
		JsonConfig jsonConfig = new JsonConfig();    
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor()); 
		JSONArray jobj = JSONArray.fromObject(rows,jsonConfig);*/
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("historicalQuoteSupplierFormListTotal", HistoricalQuoteSupplierForm.class);
		classMap.put("accessoryEnquiryPackingList", AccessoryEnquiryPacking.class);
		classMap.put("accessoryEnquiryElseList", AccessoryEnquiryElse.class);
		classMap.put("accessoryEnquiryMaterialList", AccessoryEnquiryMaterial.class);
		classMap.put("accessoryEnquiryAccessoryList", AccessoryEnquiryAccessory.class);
		classMap.put("accessoryEnquiryTechnologyList", AccessoryEnquiryTechnology.class);
		//JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd",""}));
		//@SuppressWarnings("unchecked")
		//List<HistoricalQuoteSupplierFormNoDate> historicalQuoteSupplierFormListTotal =  (List<HistoricalQuoteSupplierFormNoDate>) JSONArray.toArray(jobj, HistoricalQuoteSupplierFormNoDate.class, classMap);
		List<HistoricalQuoteSupplierForm> historicalQuoteSupplierFormListTotal = JSON.parseArray(rows,HistoricalQuoteSupplierForm.class);
		ServletOutputStream out = response.getOutputStream();
		String fileName = "?????????????????????????????????" + "_".concat(DateUtils.formateDateTime()).concat(".xlsx");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes(Constant.DEFAULT_ENCODED_1), Constant.DEFAULT_ENCODED_2) + "\"");
		historicalQuoteSupplierService.exportExcel(historicalQuoteSupplierFormListTotal, out);

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
		map.put("reportsTypeCode", BusinessConstants.myReportType.FSP);// ????????????
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
		String msg = historicalQuoteSupplierService.saveSearchDataForm(fileName, paraMap);
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
	public void doListHistoricalQuoteSupplierIntentionApplication() throws Exception {
		Map<String, Object> map = getHistoricalQuoteSupplier().toMap();
		String supplierNo = this.asString("intentionSupplierCode");
		String supplierName = this.asString("intentionSupplierName");

		map.put("search", this.asString("search"));

		map.put("intentionSupplierCode", supplierNo);
		map.put("intentionSupplierName", supplierName);
		map.put("supplierCode", supplierNo);
		map.put("supplierName", supplierName);
		List<HistoricalQuoteSupplier> list = historicalQuoteSupplierService.listHistoricalQuoteSupplierIntentionApplication(map, this.getPageInfo());
		this.forwardData(true, list, null);
	}

	/**
	 * 
	 */
	@Authority(privilege = "com.powere2e.sco.accessoryOaApplication")
	public void doLoadHistoricalQuoteSupplierIntentionApplication() throws Exception {
		HistoricalQuoteSupplier historicalQuoteSupplier = historicalQuoteSupplierService.loadHistoricalQuoteSupplierIntentionApplication(asString("quotedCode"));
		this.forwardData(true, historicalQuoteSupplier, null);
	}

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @throws Exception
	 *             ????????????Exception??????
	 */
	private HistoricalQuoteSupplier getHistoricalQuoteSupplier() throws Exception {
		HistoricalQuoteSupplier historicalQuoteSupplier = new HistoricalQuoteSupplier();
		this.asBean(historicalQuoteSupplier);
		return historicalQuoteSupplier;
	}

	/**
	 * ???????????????????????????????????????
	 * 
	 * @param smdate
	 *            ???????????????
	 * @param bdate
	 *            ???????????????
	 * @return ????????????
	 * 
	 */
	public int daysBetween(Date smdate, Date bdate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	/**
	 * ????????????
	 */
	@Authority(privilege = "com.powere2e.sco")
	public void doDownloadPicture() {
		String path = "upload/".concat(this.asString("path"));
		this.forwardDownload(path);
	}

	/**
	 * ?????????????????????????????????
	 */
	public int daysBetween(String smdate, String bdate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
}
