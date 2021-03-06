package com.powere2e.sco.service.impl.merchandisecostanalysis.totalcostanalogyanalysis;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;

import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.config.ConfigPath;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.frame.commons.service.impl.ServiceImpl;
import com.powere2e.frame.web.exception.EscmException;
import com.powere2e.sco.common.service.BusinessConstants;
import com.powere2e.sco.common.utils.DateUtils;
import com.powere2e.sco.common.utils.DecimalFormatUtils;
import com.powere2e.sco.common.utils.ExcelUtils;
import com.powere2e.sco.common.utils.FreeMarkerUtil;
import com.powere2e.sco.common.utils.LoggerUtil;
import com.powere2e.sco.interfaces.dao.merchandisecostanalysis.accountingingredient.accounting.AccountingDao;
import com.powere2e.sco.interfaces.dao.merchandisecostanalysis.accountingingredient.ingredient.IngredientDao;
import com.powere2e.sco.interfaces.dao.merchandisecostanalysis.totalcostanalogyanalysis.TotalCostAnalogyAnalysisDao;
import com.powere2e.sco.interfaces.service.merchandisecostanalysis.totalcostanalogyanalysis.TotalCostAnalogyAnalysisService;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingAddedvaluetax;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingAggregate;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingAggregateRegion;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingBo;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingCostItem;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingCustomscharges;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingCustomsduties;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingElsesubtotal;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingElsesubtotalRegion;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingExchangerate;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingFactoryPrice;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingFreight;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingFreightRegion;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingInterest;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingManage;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingManageRegion;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingManpower;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingNPackag;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingOceanfreight;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingProfit;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingProfitRegion;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingSbzjwh;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingTax;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingTaxDiffer;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingTaxRegion;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingWPackag;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingWastage;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingWec;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.ingredient.Ingredient;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.ingredient.IngredientItem;
import com.powere2e.sco.model.merchandisecostanalysis.totalcostanalogyanalysis.TotalCostAnalysis;
import com.powere2e.sco.model.reports.CostAnalysisMerchandise;
import com.powere2e.sco.model.reports.Reports;
import com.powere2e.sco.service.impl.reports.ReportsServiceImpl;

/**
 * ????????????????????????
 * 
 * @author lipengjie
 * @version 1.0
 * @since 2015???4???23???
 */
public class TotalCostAnalogyAnalysisServiceImpl extends ServiceImpl implements TotalCostAnalogyAnalysisService {

	private static final long serialVersionUID = -5084903761701825157L;
	private TotalCostAnalogyAnalysisDao totalCostAnalogyAnalysisDao;
	private boolean setUnits = false;          //??????????????????????????????
	private boolean isShowAllRemarks = false;  //????????????????????????
	private boolean isShowThisRemarks = false; //???????????????????????????????????????
	private boolean isShowUnits = false;       //??????????????????????????????
	private boolean isShowSubTotal = false;    //?????????????????????
	private boolean isHideAllRemarks = true;   //????????????????????????
	private String inlandImport = "";		   //?????????????????????
	private int materialsSize = 0; 			   //?????????????????????
	private int npackagsSize = 0;			   //?????????????????????
	private int wpackagsSize = 0;			   //?????????????????????
	private int wastagesSize = 0;			   //??????????????????
	private CellStyle leftStyle = null;           //???????????????
	private CellStyle leftRedStyle = null;        //?????????????????????
	private CellStyle titleSyle = null;           //????????????
	private CellStyle twoPrecisionStyle = null;   //????????????????????????
	private CellStyle twoPrecisionRedStyle = null;//??????????????????????????????
	private CellStyle threePrecisionStyle = null; //????????????????????????
	private CellStyle threePrecisionRedStyle = null;//??????????????????????????????
	private CellStyle fourPrecisionStyle = null;    //????????????????????????
	private CellStyle fourPrecisionRedStyle = null; //??????????????????????????????
	private CellStyle accountingCodeStyle = null; //?????????????????????,???????????????????????????
	
	private String startTime;
	private String endTime;
	private AccountingDao accountingDao;
	private IngredientDao ingredientDao;
	public AccountingDao getAccountingDao() {
		return accountingDao;
	}

	public void setAccountingDao(AccountingDao accountingDao) {
		this.accountingDao = accountingDao;
	}

	public IngredientDao getIngredientDao() {
		return ingredientDao;
	}

	public void setIngredientDao(IngredientDao ingredientDao) {
		this.ingredientDao = ingredientDao;
	}
	
	public TotalCostAnalogyAnalysisDao getTotalCostAnalogyAnalysisDao() {
		return totalCostAnalogyAnalysisDao;
	}

	public void setTotalCostAnalogyAnalysisDao(TotalCostAnalogyAnalysisDao totalCostAnalogyAnalysisDao) {
		this.totalCostAnalogyAnalysisDao = totalCostAnalogyAnalysisDao;
	}

	public static TotalCostAnalogyAnalysisDao getInstance() {
		return (TotalCostAnalogyAnalysisDao) ConfigFactory.getInstance().getBean("totalCostAnalogyAnalysisService");
	}

	@Override
	public TotalCostAnalysis listThisApplicationMerchandise(Map<String, Object> map) {
		TotalCostAnalysis totalCostAnalysis = new TotalCostAnalysis();
		try {
			//???????????????????????????,?????????????????????????????????
			if (map.get("accountingCode") != null&&!(map.get("accountingCode") instanceof JSONNull)) {
				totalCostAnalysis.setAccounting(this.accountingDao.loadAccounting(map));
				totalCostAnalysis.setAccountingCostItem(this.accountingDao.loadAccountingCostItem(map));
				totalCostAnalysis.setAccountingElsesubtotal(this.accountingDao.loadAccountingElsesubtotal(map));
				totalCostAnalysis.setAccountingElsesubtotalRegionList(this.accountingDao.loadAccountingElsesubtotalRegion(map));
				totalCostAnalysis.setAccountingFreight(this.accountingDao.loadAccountingFreight(map));
				totalCostAnalysis.setIngredient(this.ingredientDao.loadIngredient(map));
				totalCostAnalysis.setIngredientItemList(this.ingredientDao.listIngredientItem(map, null));
				totalCostAnalysis.setAccountingAggregate(this.accountingDao.loadAccountingAggregate(map));
				totalCostAnalysis.setAccountingAggregateRegionList(this.accountingDao.loadAccountingAggregateRegion(map));
				totalCostAnalysis.setAccountingFreightRegionList(this.accountingDao.loadAccountingFreightRegion(map));
				totalCostAnalysis.setAccountingManage(this.accountingDao.loadAccountingManage(map));
				totalCostAnalysis.setAccountingManageRegionList(this.accountingDao.loadAccountingManageRegion(map));
				totalCostAnalysis.setAccountingManpower(this.accountingDao.loadAccountingManpower(map));
				totalCostAnalysis.setAccountingNPackagList(this.accountingDao.loadAccountingNPackag(map));
				totalCostAnalysis.setAccountingProfit(this.accountingDao.loadAccountingProfit(map));
				totalCostAnalysis.setAccountingProfitRegionList(this.accountingDao.loadAccountingProfitRegion(map));
				totalCostAnalysis.setAccountingRegionList(this.accountingDao.loadAccountingRegion(map));
				totalCostAnalysis.setAccountingSbzjwh(this.accountingDao.loadAccountingSbzjwh(map));
				totalCostAnalysis.setAccountingTax(this.accountingDao.loadAccountingTax(map));
				totalCostAnalysis.setAccountingTaxRegionList(this.accountingDao.loadAccountingTaxRegion(map));
				totalCostAnalysis.setAccountingWastageList(this.accountingDao.loadAccountingWastage(map));
				totalCostAnalysis.setAccountingWec(this.accountingDao.loadAccountingWec(map));
				totalCostAnalysis.setAccountingWPackagList(this.accountingDao.loadAccountingWPackag(map));
				totalCostAnalysis.setOaApplicationCode(totalCostAnalogyAnalysisDao.searchOAApplicationCode(map));
				//??????
				totalCostAnalysis.setAccountingFactoryPrice(this.accountingDao.loadAccountingFactoryPrice(map));
				totalCostAnalysis.setAccountingExchangerate(this.accountingDao.loadAccountingExchangerate(map));
				totalCostAnalysis.setAccountingOceanfreight(this.accountingDao.loadAccountingOceanfreight(map));
				totalCostAnalysis.setAccountingCustomscharges(this.accountingDao.loadAccountingCustomscharges(map));
				totalCostAnalysis.setAccountingCustomsduties(this.accountingDao.loadAccountingCustomsduties(map));
				totalCostAnalysis.setAccountingAddedvaluetax(this.accountingDao.loadAccountingAddedvaluetax(map));
				totalCostAnalysis.setAccountingTaxDiffer(this.accountingDao.loadAccountingTaxDiffer(map));
				totalCostAnalysis.setAccountingInterest(this.accountingDao.loadAccountingInterest(map));
			}else{
				totalCostAnalysis.setAccounting(this.totalCostAnalogyAnalysisDao.loadFalseAccounting(map));
				totalCostAnalysis.setAccountingRegionList(this.totalCostAnalogyAnalysisDao.listFalseAccountingRegion(map));
				totalCostAnalysis.setAccountingCostItem(new AccountingCostItem());
				totalCostAnalysis.setIngredient(new Ingredient());
				totalCostAnalysis.setIngredientItemList(new ArrayList<IngredientItem>());
				totalCostAnalysis.setAccountingAggregate(new AccountingAggregate());
				totalCostAnalysis.setAccountingAggregateRegionList(new ArrayList<AccountingAggregateRegion>());
				totalCostAnalysis.setAccountingElsesubtotal(new AccountingElsesubtotal());
				totalCostAnalysis.setAccountingElsesubtotalRegionList(new ArrayList<AccountingElsesubtotalRegion>());
				totalCostAnalysis.setAccountingFreight(new AccountingFreight());
				totalCostAnalysis.setAccountingFreightRegionList(new ArrayList<AccountingFreightRegion>());
				totalCostAnalysis.setAccountingManage(new AccountingManage());
				totalCostAnalysis.setAccountingManageRegionList(new ArrayList<AccountingManageRegion>());
				totalCostAnalysis.setAccountingManpower(new AccountingManpower());
				totalCostAnalysis.setAccountingNPackagList(new ArrayList<AccountingNPackag>());
				totalCostAnalysis.setAccountingProfit(new AccountingProfit());
				totalCostAnalysis.setAccountingProfitRegionList(new ArrayList<AccountingProfitRegion>());
				totalCostAnalysis.setAccountingSbzjwh(new AccountingSbzjwh());
				totalCostAnalysis.setAccountingTax(new AccountingTax());
				totalCostAnalysis.setAccountingTaxRegionList(new ArrayList<AccountingTaxRegion>());
				totalCostAnalysis.setAccountingWastageList(new ArrayList<AccountingWastage>());
				totalCostAnalysis.setAccountingWec(new AccountingWec());
				totalCostAnalysis.setAccountingWPackagList(new ArrayList<AccountingWPackag>());
				//??????
				totalCostAnalysis.setAccountingFactoryPrice(new AccountingFactoryPrice());
				totalCostAnalysis.setAccountingExchangerate(new AccountingExchangerate());
				totalCostAnalysis.setAccountingOceanfreight(new AccountingOceanfreight());
				totalCostAnalysis.setAccountingCustomscharges(new AccountingCustomscharges());
				totalCostAnalysis.setAccountingCustomsduties(new AccountingCustomsduties());
				totalCostAnalysis.setAccountingAddedvaluetax(new AccountingAddedvaluetax());
				totalCostAnalysis.setAccountingTaxDiffer(new AccountingTaxDiffer());
				totalCostAnalysis.setAccountingInterest(new AccountingInterest());
				totalCostAnalysis.setOaApplicationCode(totalCostAnalogyAnalysisDao.searchOAApplicationCode(map));
			}
			totalCostAnalysis.setMerchandiseContractPrices(totalCostAnalogyAnalysisDao.searchMerchandiseContractPrice(map));
		} catch (Exception e) {
			totalCostAnalysis = null;
		}
		return totalCostAnalysis;
	}
	
	@Override
	public List<AccountingBo> listTotalCostAnalogyAnalysis(Map<String, Object> map, PageInfo pageInfo) {
		return totalCostAnalogyAnalysisDao.listTotalCostAnalogyAnalysis(map, pageInfo);
	}

	@Override
	public List<AccountingBo> listReferMerchandise(Map<String, Object> map, PageInfo pageInfo) {
		return totalCostAnalogyAnalysisDao.listReferMerchandise(map, pageInfo);
	}

	@Override
	public List<AccountingBo> listReferIntention(Map<String, Object> map, PageInfo pageInfo) {
		return totalCostAnalogyAnalysisDao.listReferIntention(map, pageInfo);
	}

	@Override
	public Map<String, Object> listTitle(Map<String, Object> m) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("merchandiseName", "??????/???????????????");
		map.put("merchandiseCode", "??????/???????????????");
		map.put("supplierName", "?????????/?????????????????????");
		map.put("supplierCode", "?????????/?????????????????????");
		map.put("units", "??????????????????");
		map.put("quotedDate", "????????????");
		map.put("accountingCode", "??????/???????????????");
		map.put("updateby", "?????????");
		map.put("region", "????????????");
		if (!isShowSubTotal) {
			map.put("material", "??????");
		}
		map.put("yield", "????????????");
		map.put("zlsubtotalValue", "???????????????(????????????)");
		map.put("flsubtotalValue", "????????????(????????????)");
		//????????????????????????????????????????????????
		if ("INLAND".equals(m.get("inlandImport"))) {
			//??????????????????
			map.put("iTotalcostValue", "?????????????????????");
			map.put("mTotalcostValue", "??????????????????");
			map.put("packagproportionValue", "????????????");
			map.put("deductptcostValue", "?????????????????????????????????");
			if (!isShowSubTotal) {
				map.put("npackag", "?????????");
				map.put("wpackag", "?????????");
			}
			map.put("nwpackagsubtotalValue", "??????????????????????????????");
			if (!isShowSubTotal) {
				map.put("wastage", "??????");
			}
			if (!isShowSubTotal) {
				map.put("wecPrice", "?????????");
				map.put("sbzjwhPrice", "?????????????????????");
				map.put("ampPrice", "??????");
				map.put("amaPrice", "??????");
				map.put("afrPrice", "??????");
				map.put("atrPrice", "??????");
				map.put("aprPrice", "??????");
			}
		} else {
			//??????
			map.put("factoryPrice", "????????????");
			map.put("exchangeRate", "??????");
			map.put("rmbSettlementPrice", "???????????????????????????");
			if (!isShowSubTotal) {
				map.put("oceanfreight", "?????????/?????????");
				map.put("orderFee", "?????????");
				map.put("premium", "?????????");
				map.put("customscharges", "???????????????");
			}
			map.put("importFeeTotal", "??????????????????");
			if (!isShowSubTotal) {
				map.put("customsduties", "??????");
				map.put("addedvaluetax", "?????????");
			}
			map.put("cdAvtTotal", "????????????????????????");
			map.put("customsClearanceTotal", "????????????????????????");
			map.put("packagproportionValue", "????????????");
			map.put("deductptcostValue", "?????????????????????????????????");
			if (!isShowSubTotal) {
				map.put("npackag", "?????????");
				map.put("wpackag", "?????????");
			}
			map.put("nwpackagsubtotalValue", "??????????????????????????????");
			if (!isShowSubTotal) {
				map.put("wastage", "??????");
			}
			if (!isShowSubTotal) {
				map.put("wecPrice", "?????????");
				map.put("sbzjwhPrice", "?????????????????????");
				map.put("ampPrice", "??????");
				map.put("amaPrice", "??????");
				map.put("taxDiffer", "??????");
				map.put("interest", "??????");
				map.put("afrPrice", "??????");
				map.put("atrPrice", "??????");
				map.put("aprPrice", "??????");
			}
		}
		
		map.put("subTotal", "??????????????????");
		map.put("sumPrice", "??????");
		map.put("mcpPrice", "????????????");
		return map;
	}
	
	@Override
	public void exportSignedQtyExcel(Map<String, Object> map, ServletOutputStream out) {
		// ??????????????????
		setUnits = Boolean.valueOf(map.get("setUnits").toString());
		isShowAllRemarks = Boolean.valueOf(map.get("isShowAllRemarks").toString());
		isShowThisRemarks = Boolean.valueOf(map.get("isShowThisRemarks").toString());
		isShowUnits = Boolean.valueOf(map.get("isShowUnits").toString());
		isShowSubTotal = Boolean.valueOf(map.get("isShowSubTotal").toString());
		isHideAllRemarks = Boolean.valueOf(map.get("isHideAllRemarks").toString());
		inlandImport = map.get("inlandImport").toString();
		materialsSize = 1;
		npackagsSize = 1;
		wpackagsSize = 1;
		wastagesSize = 1;
		// ????????????????????????List
		List<TotalCostAnalysis> list = new ArrayList<TotalCostAnalysis>();
		JSONArray jsonArray = JSONArray.fromObject(map.get("data"));
		Map<String, Object> accountingMap = new HashMap<String, Object>();
		JSONObject tempObj = null;
		JSONObject jsonObject = null;
		for (Object object : jsonArray) {
			accountingMap.clear();
			// ?????????????????????????????????
			jsonObject = JSONObject.fromObject(object);
			if (jsonObject == null) {
				continue;
			}
			// ???????????????????????????????????????????????????????????????
			accountingMap.put("accountingCode", jsonObject.get("accountingCode") instanceof JSONNull?null:jsonObject.get("accountingCode"));
			accountingMap.put("ingredientCode", jsonObject.get("accountingCode") instanceof JSONNull?null:jsonObject.get("accountingCode"));
			accountingMap.put("applicationCode", jsonObject.get("applicationCode") instanceof JSONNull?null:jsonObject.get("applicationCode"));
			accountingMap.put("merchandiseCode", jsonObject.get("merchandiseCode") instanceof JSONNull?null:jsonObject.get("merchandiseCode"));
			accountingMap.put("supplierCode", jsonObject.get("supplierCode") instanceof JSONNull?null:jsonObject.get("supplierCode"));
			if (jsonObject.getString("rowTitleName").contains("?????????????????????")) {
				for (int j = 0; j < jsonArray.size(); j++) {
					tempObj = jsonArray.getJSONObject(j);
					if (jsonObject.get("accountingCode") == null||jsonObject.get("accountingCode") instanceof JSONNull) {
						if (tempObj.getString("merchandiseCode").equals(jsonObject.get("merchandiseCode"))
						  &&tempObj.getString("supplierCode").equals(jsonObject.get("supplierCode"))
						 &&!tempObj.getString("rowTitleName").contains("?????????????????????")) {
							accountingMap.put("quantity", tempObj.get("quantity"));
						}
					} else {
						if (jsonObject.get("accountingCode").equals(tempObj.getString("accountingCode"))&&!tempObj.getString("rowTitleName").contains("?????????????????????")) {
							accountingMap.put("quantity", tempObj.get("quantity"));
						}
					}
				}
				accountingMap.put("convertAfterQuantity", jsonObject.get("quantity"));
			}
			TotalCostAnalysis tCostAnalysis = this.listThisApplicationMerchandise(accountingMap);
			// ??????????????????????????????
			if (tCostAnalysis != null) {
				materialsSize = tCostAnalysis.getIngredientItemList().size()>materialsSize?tCostAnalysis.getIngredientItemList().size():materialsSize;
				npackagsSize = tCostAnalysis.getAccountingNPackagList().size()>npackagsSize?tCostAnalysis.getAccountingNPackagList().size():npackagsSize;
				wpackagsSize = tCostAnalysis.getAccountingWPackagList().size()>wpackagsSize?tCostAnalysis.getAccountingWPackagList().size():wpackagsSize;
				wastagesSize = tCostAnalysis.getAccountingWastageList().size()>wastagesSize?tCostAnalysis.getAccountingWastageList().size():wastagesSize;
				// ??????????????????????????????list
				list.add(tCostAnalysis);
			}
		}
		SXSSFWorkbook wb = ExcelUtils.createSXSSFWorkbook();
		Sheet sheet = wb.createSheet("???????????????????????????");
		configStyle(wb,sheet);
		// ????????????
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("inlandImport", inlandImport);
		fillListHeaderCell(wb, sheet,m);
		try {
			// 2.????????????????????????
			if (!list.isEmpty()){
				this.fillDataCell(this.listTitle(m), list, wb, sheet, map);
				autoSizeListWidth(sheet);
				wb.write(out);
			}
		} catch (Exception e) {
			throw new EscmException("??????Excel?????????", new String[] { e.getMessage() });
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				throw new EscmException("??????Excel?????????", new String[] { e.getMessage() });
			}
		}
	}
	
	/**
	 * ??????????????????
	 * @param wb
	 * @param sheet
	 */
	public void configStyle(Workbook wb,Sheet sheet){
		Font commFont = wb.createFont();
		commFont.setFontName("????????????");
		
		Font redFont = wb.createFont();
		redFont.setColor(Font.COLOR_RED);
		redFont.setFontName("????????????");
		
		XSSFDataFormat format = (XSSFDataFormat) wb.createDataFormat();
		
		//?????????????????????
		leftStyle = wb.createCellStyle();
		ExcelUtils.setAlign(leftStyle, ExcelUtils.left, ExcelUtils.center, true);
		ExcelUtils.setFrame(leftStyle, true);
		leftStyle.setFont(commFont);
		//???????????????????????????
		leftRedStyle = wb.createCellStyle();
		ExcelUtils.setAlign(leftRedStyle, ExcelUtils.left, ExcelUtils.center, true);
		ExcelUtils.setFrame(leftRedStyle, true);
		leftRedStyle.setFont(redFont);
		
		//2??????
		twoPrecisionStyle = wb.createCellStyle();
		ExcelUtils.setAlign(twoPrecisionStyle, ExcelUtils.right, ExcelUtils.center, true);
		ExcelUtils.setFrame(twoPrecisionStyle, true);
		twoPrecisionStyle.setDataFormat(format.getFormat("#,##0.00"));
		twoPrecisionStyle.setFont(commFont);
		//3??????
		threePrecisionStyle = wb.createCellStyle();
		ExcelUtils.setAlign(threePrecisionStyle, ExcelUtils.right, ExcelUtils.center, true);
		ExcelUtils.setFrame(threePrecisionStyle, true);
		threePrecisionStyle.setDataFormat(format.getFormat("#,##0.000"));
		threePrecisionStyle.setFont(commFont);
		//4??????
		fourPrecisionStyle = wb.createCellStyle();
		ExcelUtils.setAlign(fourPrecisionStyle, ExcelUtils.right, ExcelUtils.center, true);
		ExcelUtils.setFrame(fourPrecisionStyle, true);
		fourPrecisionStyle.setDataFormat(format.getFormat("#,##0.0000"));
		fourPrecisionStyle.setFont(commFont);
		
		//??????
		//2??????
		twoPrecisionRedStyle = wb.createCellStyle();
		ExcelUtils.setAlign(twoPrecisionRedStyle, ExcelUtils.right, ExcelUtils.center, true);
		ExcelUtils.setFrame(twoPrecisionRedStyle, true);
		twoPrecisionRedStyle.setDataFormat(format.getFormat("#,##0.00"));
		twoPrecisionRedStyle.setFont(redFont);
		//3??????
		threePrecisionRedStyle = wb.createCellStyle();
		ExcelUtils.setAlign(threePrecisionRedStyle, ExcelUtils.right, ExcelUtils.center, true);
		ExcelUtils.setFrame(threePrecisionRedStyle, true);
		threePrecisionRedStyle.setDataFormat(format.getFormat("#,##0.000"));
		threePrecisionRedStyle.setFont(redFont);
		//4??????
		fourPrecisionRedStyle = wb.createCellStyle();
		ExcelUtils.setAlign(fourPrecisionRedStyle, ExcelUtils.right, ExcelUtils.center, true);
		ExcelUtils.setFrame(fourPrecisionRedStyle, true);
		fourPrecisionRedStyle.setDataFormat(format.getFormat("#,##0.0000"));
		fourPrecisionRedStyle.setFont(redFont);
		//????????????
		titleSyle = wb.createCellStyle();
		ExcelUtils.setAlign(titleSyle, ExcelUtils.left, ExcelUtils.center, true);
		ExcelUtils.setFrame(titleSyle, true);
		ExcelUtils.setFont(titleSyle, wb.createFont(),"????????????", 12, true);// ??????
		titleSyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);// ???????????????
		titleSyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//??????????????????
		accountingCodeStyle = wb.createCellStyle();
		ExcelUtils.setAlign(accountingCodeStyle, ExcelUtils.left, ExcelUtils.center, true);
		accountingCodeStyle.setFont(commFont);
		ExcelUtils.setFrame(leftStyle, true);
		accountingCodeStyle.setDataFormat(format.getFormat("0"));
	}
	
	/**
	 * ??????excel????????????
	 * 
	 * @param wb
	 *            ?????????
	 * @param sheet
	 *            sheet
	 */
	private void fillListHeaderCell(Workbook wb, Sheet sheet,Map<String, Object> m) {
		sheet.setColumnWidth(0, (short) (120 * 80));// ??????????????????????????????
		Row row = null;
		Cell cell = null;
		int i = 1;
		// ???????????????????????????
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleSyle);
		cell.setCellValue("????????????");
		// ???????????????????????????????????????
		for (Object temp : this.listTitle(m).values()) {
			row = sheet.createRow(i);
			cell = row.createCell(0);
			switch (temp.toString()) {
			 case "??????":
			    sheet.addMergedRegion(new CellRangeAddress(i, i + (materialsSize == 0?1:materialsSize) - 1, 0, 0));
				addMergedRegionBorder(sheet, i, i + (materialsSize == 0?1:materialsSize) - 1, 0, 0);
				i = i + materialsSize;
				break;
			case "?????????":
				sheet.addMergedRegion(new CellRangeAddress(i, i + (npackagsSize == 0?1:npackagsSize) - 1, 0, 0));
				addMergedRegionBorder(sheet, i, i + (npackagsSize == 0?1:npackagsSize) - 1, 0, 0);
				i = i + npackagsSize;
				break;
			case "?????????":
				sheet.addMergedRegion(new CellRangeAddress(i, i + (wpackagsSize == 0?1:wpackagsSize) - 1, 0, 0));
				addMergedRegionBorder(sheet, i, i + (wpackagsSize == 0?1:wpackagsSize) - 1, 0, 0);
				i = i + wpackagsSize;
				break;
			case "??????":
				sheet.addMergedRegion(new CellRangeAddress(i, i + (wastagesSize == 0?1:wastagesSize) - 1, 0, 0));
				addMergedRegionBorder(sheet, i, i + (wastagesSize == 0?1:wastagesSize) - 1, 0, 0);
				i = i + wastagesSize;
				break;
			default:
				i++;
				break;
			}
			if (temp.equals("?????????????????????????????????")||temp.equals("??????????????????????????????")||temp.equals("??????????????????")) {
				cell.setCellStyle(leftRedStyle);
			}else{
				cell.setCellStyle(leftStyle);
			}
			cell.setCellValue(temp.toString());
		}
	}
	
	/**
	 * ???sheet????????????
	 * 
	 * @param listMaps
	 *            ??????????????????
	 * @param list
	 *            ??????????????????
	 * @param wb
	 *            ?????????
	 * @param sheet
	 *            sheet???
	 * @param map
	 *            ?????????????????????Map
	 * @throws ParseException  ????????????
	 */
	private void fillDataCell(Map<String, Object> listHeaderMaps, List<TotalCostAnalysis> list, Workbook wb, Sheet sheet, Map<String, Object> map) throws ParseException {
		// ?????????????????? ?????????????????????
		JSONArray jsonArray = JSONArray.fromObject(map.get("data"));
		JSONObject obj = null;
		
		Map<String, Date> maxDateMap = new HashMap<String, Date>();
		for (int i = 0; i < jsonArray.size(); i++) {
			obj = jsonArray.getJSONObject(i);
			String key = obj.getString("merchandiseCode") + obj.getString("supplierCode");
			Date maxDate = maxDateMap.get(key);
			Date curDate = DateUtils.formatStrToDate(obj.getString("quotedDate"), "yyyy-MM-dd HH:mm:ss");
			if (maxDate == null || maxDate.before(curDate)){
				maxDateMap.put(key, curDate);
			}
		}
		List<Integer> indexs = new ArrayList<>();
		for (int i = 0; i < jsonArray.size(); i++) {
			obj = jsonArray.getJSONObject(i);
			String key = obj.getString("merchandiseCode") + obj.getString("supplierCode");
			if (maxDateMap.get(key).equals(DateUtils.formatStrToDate(obj.getString("quotedDate"), "yyyy-MM-dd HH:mm:ss"))) {
				indexs.add(i);
			}
		}
			
		int nextFillCellIndex = 1;
		for (int i = 0; i < list.size(); i++) {
			boolean noLast = true;
			for (int j = 0; j < indexs.size(); j++) {
				if (Integer.valueOf(indexs.get(j)) == i) {
					noLast = false;
				}
			}
			nextFillCellIndex = writeOneListDate(wb, sheet, listHeaderMaps, nextFillCellIndex, jsonArray.getJSONObject(i), list.get(i),noLast);
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param wb
	 *            ?????????
	 * @param sheet
	 *            sheet
	 * @param listHeaderMaps
	 *            ?????????????????????
	 * @param nextFillCellIndex
	 *            ????????????????????????????????????
	 * @param jsonObject
	 *            ?????????????????????,????????????????????????????????????????????????????????????
	 * @param data
	 *            ???????????????
	 * @param map
	 *            ??????????????????
	 * @return ????????????????????????????????????
	 */
	private int writeOneListDate(Workbook wb, Sheet sheet, Map<String, Object> listHeaderMaps, int nextFillCellIndex, JSONObject jsonObject, TotalCostAnalysis data,boolean noLast){
		Row row = null;             //???
		Cell cell = null;           //?????????
		JSONObject object = null;   //??????JSONObject,??????????????????
		JSONArray array = null;     //??????JSONArray,??????????????????
		String key = null;          //???????????????key
		Object value = null;        //???
		String remarks = null;      //??????
		String units = null;        //????????????
		int rowIndex = 1;           //?????????????????????
		boolean isThisApplication = false;  //????????????????????????????????????
		double countSum = 0;
		double costSum = 0;
		double avgSum = 0;
		CellStyle valueStyle = null;
		CellStyle remarksStyle = null;
		CellStyle unitsStyle = null;
		// ???????????????????????????Json??????,????????????
		JsonConfig jc = new JsonConfig();
		jc.registerDefaultValueProcessor(BigDecimal.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(@SuppressWarnings("rawtypes") Class c) {
				return "";
			}
		});
		jc.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(@SuppressWarnings("rawtypes") Class c) {
				return "";
			}
		});
		jc.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(@SuppressWarnings("rawtypes") Class c) {
				return "";
			}
		});
		JSONObject jsonData = JSONObject.fromObject(data,jc);
		// ????????????????????????
		int maxColSpan = JSONArray.fromObject(jsonData.get("accountingRegionList")).size();
		//???????????????????????????
		if (maxColSpan<2) {
			maxColSpan = 2;
		}
		//???????????????????????????????????????
		if (jsonObject.getString("rowTitleName").contains("??????????????????")) {
			isThisApplication = true;
		}else{
			isThisApplication = false;
		}
		//?????????????????????????????????
		if (isShowAllRemarks||(isShowThisRemarks&&isThisApplication&&!noLast)) {
			maxColSpan++;
		}
		//????????????????????????
		if (isShowUnits) {
			maxColSpan++;
		}
		
		// ?????????????????????
		row = sheet.getRow(0);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, nextFillCellIndex, nextFillCellIndex + maxColSpan - 1));
		addMergedRegionBorder(sheet, 0, 0, nextFillCellIndex, nextFillCellIndex + maxColSpan - 1);
		cell = row.createCell(nextFillCellIndex);
		cell.setCellStyle(titleSyle);
		cell.setCellValue(jsonObject.getString("rowTitleName"));
		// ??????????????????????????????????????????
		Set<Entry<String, Object>> s = listHeaderMaps.entrySet();
		Iterator<Entry<String, Object>> listHeaders = s.iterator();
		// ?????????????????????????????????hashMap??????key???jsonData??????????????????
		while (listHeaders.hasNext()) {
			//????????????????????????????????????
			valueStyle = leftStyle;
			remarksStyle = leftStyle;
			unitsStyle = leftStyle;
			
			value = "";
			remarks = "";
			units = formatNum(jsonData.getJSONObject("accounting").getString("quantity"), 3) + jsonData.getJSONObject("accounting").getString("units");
			// ????????????????????????????????????
			Entry<String, Object> temp = listHeaders.next();
			key = temp.getKey();
			try {
			if ("INLAND".equals(inlandImport)) {
			//?????????
				switch (key) {
				case "merchandiseName":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"merchandiseName").equals("")?getJSONObjectValue(jsonData.getJSONObject("accounting"),"intentionName"):getJSONObjectValue(jsonData.getJSONObject("accounting"),"merchandiseName");
					break;
				case "merchandiseCode":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"merchandiseCode").equals("")?getJSONObjectValue(jsonData.getJSONObject("accounting"),"intentionCode"):getJSONObjectValue(jsonData.getJSONObject("accounting"),"merchandiseCode");
					break;
				case "supplierName":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"supplierName").equals("")?getJSONObjectValue(jsonData.getJSONObject("accounting"),"intentionSupplierName"):getJSONObjectValue(jsonData.getJSONObject("accounting"),"supplierName");
					break;
				case "supplierCode":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"supplierCode").equals("")?getJSONObjectValue(jsonData.getJSONObject("accounting"),"intentionSupplierCode"):getJSONObjectValue(jsonData.getJSONObject("accounting"),"supplierCode");
					break;
				case "units":
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						value = "";
					}else{
					    value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"quantity").equals("")?"":(formatNum(jsonData.getJSONObject("accounting").getString("quantity"),3) + jsonData.getJSONObject("accounting").getString("units"));
					}
					break;
				case "quotedDate":
					value = JSONObject.toBean((JSONObject)(jsonData.getJSONObject("accounting").get("quotedDate") == null?"":jsonData.getJSONObject("accounting").getJSONObject("quotedDate")), Date.class);
					value = DateUtils.formatDateToStr((Date) value, "yyyy-MM-dd HH:mm:ss");
					break;
				case "accountingCode":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode");
					valueStyle = accountingCodeStyle;
					break;
				case "updateby":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"updateUserName");
					break;
				case "region":
					array = jsonData.getJSONArray("accountingRegionList");
					for (int i = 0; i < array.size(); i++) {
						row = sheet.getRow(rowIndex);
						cell = row.createCell(nextFillCellIndex + i);
						cell.setCellStyle(leftStyle);
						try {
							object = array.getJSONObject(i);
							cell.setCellValue(object.getString("region"));
						} catch (Exception e) {
							cell.setCellValue("");
						}
					}
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
						if (isShowUnits) {  //?????????????????????????????????????????????
							cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							cell.setCellStyle(leftStyle);
							sheet.setColumnWidth(nextFillCellIndex + maxColSpan-1, (short) (50 * 70));// ??????????????????????????????
							cell.setCellValue("??????????????????");
						}else{
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
						}
					}else{ 
			            if (isShowUnits) { //????????????????????????
			            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
							cell.setCellStyle(leftStyle);
							sheet.setColumnWidth(nextFillCellIndex + maxColSpan-1, (short) (50 * 70));// ??????????????????????????????
							cell.setCellValue("??????????????????");
						}else{ //???????????????????????????
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
						}
			                
			            cell = row.createCell(nextFillCellIndex + maxColSpan-1);
						cell.setCellStyle(leftStyle);
						sheet.setColumnWidth(nextFillCellIndex + maxColSpan-1, (short) (60 * 80));// ??????????????????????????????
						cell.setCellValue("");
					}
					rowIndex++;
					continue;
				case "material":
					array = jsonData.getJSONArray("ingredientItemList");
					for (int j = 0; j < array.size(); j++) {
						addMergedRegionBorder(sheet, rowIndex+j, rowIndex+j, nextFillCellIndex+2, nextFillCellIndex+maxColSpan-1);
						object = array.getJSONObject(j);
						if (object == null) {
							continue;
						}
						row = sheet.getRow(rowIndex+j);
						countSum += object.get("inputCount") == ""?0:object.getDouble("inputCount");
						costSum += object.get("inputCost") == ""?0:object.getDouble("inputCost");
						avgSum += object.get("avgCost") == ""?0:object.getDouble("avgCost");
								
						cell = row.createCell(nextFillCellIndex);
						cell.setCellStyle(leftStyle);
						cell.setCellValue((object.getString("materialType").equalsIgnoreCase("ZL")?"??????-":"??????-")+object.getString("materialName"));
						
						cell = row.createCell(nextFillCellIndex+1);
						cell.setCellStyle(fourPrecisionStyle);
						cell.setCellValue(formatNum(object.getDouble("avgCost"),4));
						
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
								cell = row.createCell(nextFillCellIndex+maxColSpan-1);
								cell.setCellStyle(leftStyle);
								if (!setUnits||!jsonObject.getString("rowTitleName").contains("?????????????????????")) {
									cell.setCellValue("1.000"+jsonData.getJSONObject("accounting").getString("units"));
								}else{
									cell.setCellValue(units);
								}
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
								cell = row.createCell(nextFillCellIndex+maxColSpan-2);
								cell.setCellStyle(leftStyle);
								if (!setUnits||!jsonObject.getString("rowTitleName").contains("?????????????????????")) {
									cell.setCellValue("1.000"+jsonData.getJSONObject("accounting").getString("units"));
								}else{
									cell.setCellValue(units);
								}
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
							}
							cell = row.createCell(nextFillCellIndex+maxColSpan-1);
							remarks = "??????????????????(???/kg) : " + formatNum(object.getString("purchasePrice"),4) + "\n" +
				      				  "???????????????(kg) : " + formatNum(object.getString("inputCount"),4) + "\n" +
				      				  "??????????????????(???) : " + formatNum(object.getString("inputCost"),4) + "\n" +
		      						  "??????   : " + getJSONObjectValue(object, "remarks");
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
				
					fillNoHasAccounting(sheet,rowIndex,nextFillCellIndex,maxColSpan,rowIndex+array.size(),rowIndex+materialsSize,isThisApplication,noLast);
					rowIndex = rowIndex+materialsSize;
					continue;
				case "npackag":
					array = jsonData.getJSONArray("accountingNPackagList");
					for (int j = 0; j < array.size(); j++) {
						addMergedRegionBorder(sheet, rowIndex+j, rowIndex+j, nextFillCellIndex+2, nextFillCellIndex+maxColSpan-1);
						object = array.getJSONObject(j);
						if (object == null) {
							continue;
						}
						row = sheet.getRow(rowIndex+j);
						cell = row.createCell(nextFillCellIndex);
						
						cell.setCellStyle(leftStyle);
						cell.setCellValue(getJSONObjectValue(object, "npackagName"));
						
						cell = row.createCell(nextFillCellIndex+1);
						cell.setCellStyle(threePrecisionStyle);
						cell.setCellValue(formatNum(object.get("price"),3));
						
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
								cell = row.createCell(nextFillCellIndex+maxColSpan-1);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
								cell = row.createCell(nextFillCellIndex+maxColSpan-2);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
							}
							cell = row.createCell(nextFillCellIndex+maxColSpan-1);
							
							switch (getJSONObjectValue(object, "npackagType")) {
							case "FHD_JM":
							case "FHD_LSM":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" + 
										  "????????????(???) : " + formatNum(object.getString("kgPrice"),3) + "\n" + 
										  "????????????(%) : " + formatNum(object.getString("weightProportion"),3) + "\n" + 
										  "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "FHD_ZD":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" + 
										  "??????(cm) : " + getJSONObjectValue(object, "materialSize") + "\n" + 
										  "????????????(g) : " + formatNum(object.getString("weight"),3) + "\n" +
										  "??????(???) : " + formatNum(object.getString("unitsPrice"),3) + "\n" + 
										  "????????????(???) : " + formatNum(object.getString("kgPrice"),3) + "\n" + 
										  "??????  : " + formatNum(object.getString("quantity"),3) + "\n" + 
										  "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "ST":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" + 
									      "????????????(g) : " + formatNum(object.getString("weight"),3) + "\n" +
									      "????????????(???) : " + formatNum(object.getString("kgPrice"),3) + "\n" + 
									      "????????????(%) : " + formatNum(object.getString("weightProportion"),3) + "\n" + 
									      "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "TYJ":
								remarks = "????????????(g) : " + formatNum(object.getString("weight"),3) + "\n" +
									      "??????(cm) : " + getJSONObjectValue(object, "materialSize") + "\n" + 
									      "??????(???) : " + formatNum(object.getString("unitsPrice"),3) + "\n" + 
									      "??????  : " + formatNum(object.getString("quantity"),3) + "\n" + 
									      "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "NDD":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" +
								          "??????(cm) : " + getJSONObjectValue(object, "materialSize") + "\n" + 
								          "??????(???) : " + formatNum(object.getString("unitsPrice"),3) + "\n" + 
								          "??????  : " + formatNum(object.getString("quantity"),3) + "\n" + 
								          "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "BQ":
							case "ZZL":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" +
							        	  "??????(cm) : " + getJSONObjectValue(object, "materialSize") + "\n" + 
							        	  "??????  : " + formatNum(object.getString("quantity"),3) + "\n" + 
							        	  "????????????  : " + getJSONObjectValue(object, "technologyRequirements") + "\n" + 
							        	  "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "ELSE":
								remarks = "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							default:
								remarks = "";
								break;
							}
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					fillNoHasAccounting(sheet,rowIndex,nextFillCellIndex,maxColSpan,rowIndex+array.size(),rowIndex+npackagsSize,isThisApplication,noLast);
					rowIndex = rowIndex+npackagsSize;
					continue;
				case "wpackag":
					array = jsonData.getJSONArray("accountingWPackagList");
					for (int j = 0; j < array.size(); j++) {
						addMergedRegionBorder(sheet, rowIndex+j, rowIndex+j, nextFillCellIndex+2, nextFillCellIndex+maxColSpan-1);
						object = array.getJSONObject(j);
						if (object == null) {
							continue;
						}
						row = sheet.getRow(rowIndex+j);
						cell = row.createCell(nextFillCellIndex);
						cell.setCellStyle(leftStyle);
						cell.setCellValue(getJSONObjectValue(object, "wpackagName"));
						
						cell = row.createCell(nextFillCellIndex+1);
						cell.setCellStyle(threePrecisionStyle);
						cell.setCellValue(formatNum(object.get("price"),3));
						
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
								cell = row.createCell(nextFillCellIndex+maxColSpan-1);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
								cell = row.createCell(nextFillCellIndex+maxColSpan-2);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
							}
							cell = row.createCell(nextFillCellIndex+maxColSpan-1);
							
							switch (getJSONObjectValue(object, "wpackagType")) {
							case "FXD":
								remarks =  "??????(???/???) : " + formatNum(object.getString("unitsPrice"),3) + "\n" + 
										   "?????????(???/???) : " + formatNum(object.getString("useQuantity"),3) + "\n" + 
										   "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "ZX":
								remarks =  "??????????????????:" + getJSONObjectValue(object, "texture") + "\n" + 
										   "???(cm) : " + formatNum(object.getString("length"),3) + "\n" + 
										   "???(cm) : " + formatNum(object.getString("width"),3) + "\n" + 
										   "???(cm) : " + formatNum(object.getString("height"),3) + "\n" + 
										   "??????????????????(???) : " + formatNum(object.getString("area"),3) + "\n" + 
										   "??????(???/???) : " + formatNum(object.getString("unitsPrice"),3) + "\n" + 
										   "??????????????????(???/???) : " + formatNum(object.getString("ylUnitsPrice"),3) + "\n" + 
								           "??????  : " + getJSONObjectValue(object, "specification") + "\n" + 
							               "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "ELSE":
								remarks =  "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							default:
								remarks = "";
								break;
							}
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					fillNoHasAccounting(sheet,rowIndex,nextFillCellIndex,maxColSpan,rowIndex+array.size(),rowIndex+wpackagsSize,isThisApplication,noLast);
					rowIndex = rowIndex+wpackagsSize;
					continue;
				case "wastage":
					array = jsonData.getJSONArray("accountingWastageList");
					for (int j = 0; j < array.size(); j++) {
						addMergedRegionBorder(sheet, rowIndex+j, rowIndex+j, nextFillCellIndex+2, nextFillCellIndex+maxColSpan-1);
						object = array.getJSONObject(j);
						if (object == null) {
							continue;
						}
						row = sheet.getRow(rowIndex+j);
						cell = row.createCell(nextFillCellIndex);
						cell.setCellStyle(leftStyle);
						cell.setCellValue(getJSONObjectValue(object, "wastageType"));
						
						cell = row.createCell(nextFillCellIndex+1);
						cell.setCellStyle(threePrecisionStyle);
						cell.setCellValue(formatNum(object.get("price"),3));
						
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
								cell = row.createCell(nextFillCellIndex+maxColSpan-1);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
								cell = row.createCell(nextFillCellIndex+maxColSpan-2);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
							}
							cell = row.createCell(nextFillCellIndex+maxColSpan-1);
							remarks = getJSONObjectValue(object, "remarks");
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					fillNoHasAccounting(sheet,rowIndex,nextFillCellIndex,maxColSpan,rowIndex+array.size(),rowIndex+wastagesSize,isThisApplication,noLast);
					rowIndex = rowIndex+wastagesSize;
					continue;
				case "yield":// ?????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("yieldValue"),2) + (formatNum(jsonData.getJSONObject("accountingCostItem").getString("yieldValue"),2).equals("")?"":"%");
					remarks = "???????????????(kg) : " + formatNum(countSum,4) + "\n" +
							  "??????????????????(???) : " + formatNum(costSum,4) + "\n" +
							  "??????????????????????????????(???/kg) : " + formatNum(avgSum,2) + "\n" +
							  "???????????????(kg) : " + formatNum(jsonData.getJSONObject("ingredient").getString("productCount"),4) + "\n" +
							  "???????????????(%) : " + jsonData.getJSONObject("ingredient").getString("moisture") + "\n" +
							  "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "yieldRemarks");
					if (!setUnits) {
						units = "1.000"+jsonData.getJSONObject("accounting").getString("units");
					}
					break;
				case "zlsubtotalValue": // ???????????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("zlsubtotalValue"),2);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "zlsubtotalRemarks");
					if (!setUnits) {
						units = "1.000"+jsonData.getJSONObject("accounting").getString("units");
					}
					break;
				case "flsubtotalValue":// ???????????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("flsubtotalValue"),2);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "flsubtotalRemarks");
					if (!setUnits) {
						units = "1.000"+jsonData.getJSONObject("accounting").getString("units");
					}
					break;
				case "iTotalcostValue":// ?????????????????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("itotalcostValue"),2);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "itotalcostRemarks");
					if (!setUnits) {
						units = "1.000"+jsonData.getJSONObject("accounting").getString("units");
					}
					break;
				case "mTotalcostValue":// ?????????????????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("mtotalcostValue"),2);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "mtotalcostRemarks");
					break;
				case "packagproportionValue":// ????????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("packagproportionValue"),2) + (formatNum(jsonData.getJSONObject("accountingCostItem").getString("packagproportionValue"),2).equals("")?"":"%");
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "packagproportionRemarks");
					break;
				case "deductptcostValue":// ?????????????????????????????????
					valueStyle = twoPrecisionRedStyle;
					remarksStyle = leftRedStyle;
					unitsStyle = leftRedStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("deductptcostValue"),2);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "deductptcostRemarks");
					break;
				case "nwpackagsubtotalValue":// ??????????????????????????????
					valueStyle = twoPrecisionRedStyle;
					remarksStyle = leftRedStyle;
					unitsStyle = leftRedStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("nwpackagsubtotalValue"),2);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "nwpackagsubtotalRemarks");
					break;
				case "wecPrice":// ?????????
					valueStyle = threePrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingWec").getString("price"),3);
					remarks =   "??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("water"),3) + "\n" + 
							    "??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("oil"),3) + "\n" + 
								"??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("electricity"),3) + "\n" + 
								"??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("gas"),3) + "\n" + 
								"??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("coal"),3) + "\n" + 
								"??????(???/kg??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("total"),3) + "\n" + 
								"?????? : "+ getJSONObjectValue(jsonData.getJSONObject("accountingWec"),"remarks");
					break;
				case "sbzjwhPrice":// ?????????????????????
					valueStyle = threePrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("price"),3);
			  	    remarks = "????????????(??????) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("totalPrice"),3) + "\n" + 
					          "????????????(???) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("ageLimit"),3) + "\n" + 
			  	    		  "?????????(??????/???) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("depreciation"),3) + "\n" + 
					          "?????????(t??????/???) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("capacity"),3)+ "\n" + 
					          "???????????????(???/kg??????) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("total"),3) + "\n" + 
			  	    		  "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingSbzjwh"),"remarks");
					break;
				case "ampPrice":// ??????
					valueStyle = threePrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingManpower").getString("price"),3);
				    remarks = "???????????????(??????) : " + formatNum(jsonData.getJSONObject("accountingManpower").getString("manpowerCount"),3)+ "\n" + 
							  "????????????(???/???/???) : " + formatNum(jsonData.getJSONObject("accountingManpower").getString("avgWage"),3)+ "\n" + 
							  "?????????(t) : " + formatNum(jsonData.getJSONObject("accountingManpower").getString("monthCapacity"),3)+ "\n" + 
							  "???kg??????(???/kg) : " + formatNum(jsonData.getJSONObject("accountingManpower").getString("unitsWage"),3)+ "\n" + 
							  "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingManpower"),"remarks");
					break;
				case "amaPrice":// ???????????????
					valueStyle = threePrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingManage").getString("price"),3);
					array = jsonData.getJSONArray("accountingManageRegionList");
					for (int i = 0; i < array.size(); i++) {
						try {
							object = array.getJSONObject(i);
							remarks += "????????????" + object.getString("region") + " : \n" + 
							           "??????(%) : " + formatNum(object.getString("proportion"),3) + "\n";
						} catch (Exception e) {
							remarks += "????????????" + " : /\n" + 
							           "??????(%) : " + "/\n";
						}
					}
					remarks += "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingManage"),"remarks");
					break;
				case "afrPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingFreightRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,false);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(threePrecisionStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								} else {
									cell.setCellValue(formatNum(object.get("price"),3));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				            for (int i = 0; i < array.size(); i++) {
								 try {
									object = array.getJSONObject(i);
									 remarks += "????????????" + object.getString("region") + " : \n" + 
											    "????????????(km) : "+ formatNum(object.getString("sumKm"),3) + "\n";
								} catch (Exception e) {
									remarks += "????????????" + " : /\n" + 
											   "????????????(km) : "+ "/\n";
								}
							}
							remarks += "????????????(???/"+jsonData.getJSONObject("accountingFreight").getString("units")+") : " + formatNum(jsonData.getJSONObject("accountingFreight").getString("unitsCost"),3) + "\n"+
							           "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingFreight"), "remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "atrPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingTaxRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,false);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(threePrecisionStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								} else {
									cell.setCellValue(formatNum(object.get("price"),3));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				            for (int i = 0; i < array.size(); i++) {
								 try {
									object = array.getJSONObject(i);
									 remarks += "????????????" + object.getString("region") + " : \n" + 
											    "??????(%) : "+ formatNum(object.getString("proportion"),3) + "\n";
								} catch (Exception e) {
									remarks += "????????????" + " : /\n" + 
											   "?????? (%) : "+ "/\n";
								}
							}
							remarks += "??????(%) : " + formatNum(jsonData.getJSONObject("accountingTax").getString("taxRate"),3) + "\n"+
							           "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingTax"), "remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "aprPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingProfitRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,false);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(threePrecisionStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								} else {
									cell.setCellValue(formatNum(object.get("price"),3));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				            for (int i = 0; i < array.size(); i++) {
								 try {
									object = array.getJSONObject(i);
									 remarks += "????????????" + object.getString("region") + " : \n" + 
											    "??????(%) : "+ formatNum(object.getString("proportion"),4) + "\n";
								} catch (Exception e) {
									remarks += "????????????" + ": /\n" + 
										       "??????(%) : " + "/\n";
								}
							}
				            remarks += "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingProfit"),"remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "subTotal":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingElsesubtotalRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,true);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(twoPrecisionRedStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								} else {
									cell.setCellValue(formatNum(object.get("subtotal"),2));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(leftRedStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(leftRedStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				            remarks = getJSONObjectValue(jsonData.getJSONObject("accountingElsesubtotal"), "remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftRedStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "sumPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingAggregateRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,false);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(twoPrecisionStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								}else{
									cell.setCellValue(formatNum(object.get("sumPrice"),2));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				            remarks = getJSONObjectValue(jsonData.getJSONObject("accountingAggregate"), "remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "mcpPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("merchandiseContractPrices");
					for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
						row = sheet.getRow(rowIndex);
						cell = row.createCell(nextFillCellIndex + i);
						cell.setCellStyle(twoPrecisionStyle);
						if (isThisApplication) {
							cell.setCellValue("");
						} else {
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								}else{
									cell.setCellValue(formatNum(object.get("price"),2));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
					}
					if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
						if (isShowUnits) {  //?????????????????????????????????????????????
							cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 2));
							cell.setCellStyle(unitsStyle);
							// ???????????????????????????,???????????????????????????
							if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
								cell.setCellValue("");
							}else{
								cell.setCellValue(units);
							}
						}else{
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 1));
						}
					}else{ 
			            if (isShowUnits) { //????????????????????????
			            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 3));
							cell.setCellStyle(unitsStyle);
							// ???????????????????????????,???????????????????????????
							if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
								cell.setCellValue("");
							}else{
								cell.setCellValue(units);
							}
						}else{ //???????????????????????????
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 2));
						}
						remarks = "";
						cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
						cell.setCellStyle(leftStyle);
						cell.setCellValue(remarks);
					}
					rowIndex++;
					continue;
				default:
					break;
				}
			} else {
			//??????
				switch (key) {
				case "merchandiseName":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"merchandiseName").equals("")?getJSONObjectValue(jsonData.getJSONObject("accounting"),"intentionName"):getJSONObjectValue(jsonData.getJSONObject("accounting"),"merchandiseName");
					break;
				case "merchandiseCode":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"merchandiseCode").equals("")?getJSONObjectValue(jsonData.getJSONObject("accounting"),"intentionCode"):getJSONObjectValue(jsonData.getJSONObject("accounting"),"merchandiseCode");
					break;
				case "supplierName":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"supplierName").equals("")?getJSONObjectValue(jsonData.getJSONObject("accounting"),"intentionSupplierName"):getJSONObjectValue(jsonData.getJSONObject("accounting"),"supplierName");
					break;
				case "supplierCode":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"supplierCode").equals("")?getJSONObjectValue(jsonData.getJSONObject("accounting"),"intentionSupplierCode"):getJSONObjectValue(jsonData.getJSONObject("accounting"),"supplierCode");
					break;
				case "units":
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						value = "";
					}else{
					    value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"quantity").equals("")?"":(formatNum(jsonData.getJSONObject("accounting").getString("quantity"),3) + jsonData.getJSONObject("accounting").getString("units"));
					}
					break;
				case "quotedDate":
					value = JSONObject.toBean((JSONObject) (jsonData.getJSONObject("accounting").get("quotedDate") == null?"":jsonData.getJSONObject("accounting").getJSONObject("quotedDate")), Date.class);
					value = DateUtils.formatDateToStr((Date) value, "yyyy-MM-dd HH:mm:ss");
					break;
				case "accountingCode":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode");
					valueStyle = accountingCodeStyle;
					break;
				case "updateby":
					value = getJSONObjectValue(jsonData.getJSONObject("accounting"),"updateUserName");
					break;
				case "region":
					array = jsonData.getJSONArray("accountingRegionList");
					for (int i = 0; i < array.size(); i++) {
						row = sheet.getRow(rowIndex);
						cell = row.createCell(nextFillCellIndex + i);
						cell.setCellStyle(leftStyle);
						try {
							object = array.getJSONObject(i);
							cell.setCellValue(object.getString("region"));
						} catch (Exception e) {
							cell.setCellValue("");
						}
					}
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
						if (isShowUnits) {  //?????????????????????????????????????????????
							cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							cell.setCellStyle(leftStyle);
							sheet.setColumnWidth(nextFillCellIndex + maxColSpan-1, (short) (50 * 70));// ??????????????????????????????
							cell.setCellValue("??????????????????");
						}else{
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
						}
					}else{ 
			            if (isShowUnits) { //????????????????????????
			            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
							cell.setCellStyle(leftStyle);
							sheet.setColumnWidth(nextFillCellIndex + maxColSpan-1, (short) (50 * 70));// ??????????????????????????????
							cell.setCellValue("??????????????????");
						}else{ //???????????????????????????
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
						}
			                
			            cell = row.createCell(nextFillCellIndex + maxColSpan-1);
						cell.setCellStyle(leftStyle);
						sheet.setColumnWidth(nextFillCellIndex + maxColSpan-1, (short) (60 * 80));// ??????????????????????????????
						cell.setCellValue("");
					}
					rowIndex++;
					continue;
				case "material":
					array = jsonData.getJSONArray("ingredientItemList");
					for (int j = 0; j < array.size(); j++) {
						addMergedRegionBorder(sheet, rowIndex+j, rowIndex+j, nextFillCellIndex+2, nextFillCellIndex+maxColSpan-1);
						object = array.getJSONObject(j);
						if (object == null) {
							continue;
						}
						row = sheet.getRow(rowIndex+j);
						countSum += object.get("inputCount") == ""?0:object.getDouble("inputCount");
						costSum += object.get("inputCost") == ""?0:object.getDouble("inputCost");
						avgSum += object.get("avgCost") == ""?0:object.getDouble("avgCost");
						
						cell = row.createCell(nextFillCellIndex);
						cell.setCellStyle(leftStyle);
						cell.setCellValue((object.getString("materialType").equalsIgnoreCase("ZL")?"??????-":"??????-")+object.getString("materialName"));
						
						cell = row.createCell(nextFillCellIndex+1);
						cell.setCellStyle(fourPrecisionStyle);
						cell.setCellValue(formatNum(object.getDouble("avgCost"),4));
						
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
								cell = row.createCell(nextFillCellIndex+maxColSpan-1);
								cell.setCellStyle(leftStyle);
								if (!setUnits||!jsonObject.getString("rowTitleName").contains("?????????????????????")) {
									cell.setCellValue("1.000"+jsonData.getJSONObject("accounting").getString("units"));
								}else{
									cell.setCellValue(units);
								}
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
								cell = row.createCell(nextFillCellIndex+maxColSpan-2);
								cell.setCellStyle(leftStyle);
								if (!setUnits||!jsonObject.getString("rowTitleName").contains("?????????????????????")) {
									cell.setCellValue("1"+jsonData.getJSONObject("accounting").getString("units"));
								}else{
									cell.setCellValue(units);
								}
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
							}
							cell = row.createCell(nextFillCellIndex+maxColSpan-1);
							remarks = "??????????????????(???/kg) : " + formatNum(object.getString("purchasePrice"),4) + "\n" +
				      				  "???????????????(kg) : " + formatNum(object.getString("inputCount"),4) + "\n" +
				      				  "??????????????????(???) : " + formatNum(object.getString("inputCost"),4) + "\n" +
		      						  "??????  : " + getJSONObjectValue(object, "remarks");
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					fillNoHasAccounting(sheet,rowIndex,nextFillCellIndex,maxColSpan,rowIndex+array.size(),rowIndex+materialsSize,isThisApplication,noLast);
					rowIndex = rowIndex+materialsSize;
					continue;
				case "npackag":
					array = jsonData.getJSONArray("accountingNPackagList");
					for (int j = 0; j < array.size(); j++) {
						addMergedRegionBorder(sheet, rowIndex+j, rowIndex+j, nextFillCellIndex+2, nextFillCellIndex+maxColSpan-1);
						object = array.getJSONObject(j);
						if (object == null) {
							continue;
						}
						row = sheet.getRow(rowIndex+j);
						cell = row.createCell(nextFillCellIndex);
						cell.setCellStyle(leftStyle);
						cell.setCellValue(getJSONObjectValue(object, "npackagName"));
						
						cell = row.createCell(nextFillCellIndex+1);
						cell.setCellStyle(fourPrecisionStyle);
						cell.setCellValue(formatNum(object.get("price"),4));
						
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
								cell = row.createCell(nextFillCellIndex+maxColSpan-1);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
								cell = row.createCell(nextFillCellIndex+maxColSpan-2);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
							}
							cell = row.createCell(nextFillCellIndex+maxColSpan-1);
							
							switch (getJSONObjectValue(object, "npackagType")) {
							case "FHD_JM":
							case "FHD_LSM":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" + 
										  "????????????(???) : " + formatNum(object.getString("kgPrice"),4) + "\n" + 
										  "????????????(%) : " + formatNum(object.getString("weightProportion"),4) + "\n" + 
										  "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "FHD_ZD":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" + 
										  "??????(cm) : " + getJSONObjectValue(object, "materialSize") + "\n" + 
										  "????????????(g) : " + formatNum(object.getString("weight"),4) + "\n" +
										  "??????(???) : " + formatNum(object.getString("unitsPrice"),4) + "\n" + 
										  "????????????(???) : " + formatNum(object.getString("kgPrice"),4) + "\n" + 
										  "??????  : " + formatNum(object.getString("quantity"),4) + "\n" + 
										  "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "ST":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" + 
									      "????????????(g) : " + formatNum(object.getString("weight"),4) + "\n" +
									      "????????????(???) : " + formatNum(object.getString("kgPrice"),4) + "\n" + 
									      "????????????(%) : " + formatNum(object.getString("weightProportion"),4) + "\n" + 
									      "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "TYJ":
								remarks = "????????????(g) : " + formatNum(object.getString("weight"),4) + "\n" +
									      "??????(cm) : " + getJSONObjectValue(object, "materialSize") + "\n" + 
									      "??????(???) : " + formatNum(object.getString("unitsPrice"),4) + "\n" + 
									      "??????  : " + formatNum(object.getString("quantity"),4) + "\n" + 
									      "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "NDD":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" +
								          "??????(cm) : " + getJSONObjectValue(object, "materialSize") + "\n" + 
								          "??????(???) : " + formatNum(object.getString("unitsPrice"),4) + "\n" + 
								          "??????  : " + formatNum(object.getString("quantity"),4) + "\n" + 
								          "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "BQ":
							case "ZZL":
								remarks = "????????????&??????  : " + getJSONObjectValue(object, "texture") + "\n" +
							        	  "??????(cm) : " + getJSONObjectValue(object, "materialSize") + "\n" + 
							        	  "??????  : " + formatNum(object.getString("quantity"),4) + "\n" + 
							        	  "????????????  : " + getJSONObjectValue(object, "technologyRequirements") + "\n" + 
							        	  "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "ELSE":
								remarks = "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							default:
								remarks = "";
								break;
							}
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					fillNoHasAccounting(sheet,rowIndex,nextFillCellIndex,maxColSpan,rowIndex+array.size(),rowIndex+npackagsSize,isThisApplication,noLast);
					rowIndex = rowIndex+npackagsSize;
					continue;
				case "wpackag":
					array = jsonData.getJSONArray("accountingWPackagList");
					for (int j = 0; j < array.size(); j++) {
						addMergedRegionBorder(sheet, rowIndex+j, rowIndex+j, nextFillCellIndex+2, nextFillCellIndex+maxColSpan-1);
						object = array.getJSONObject(j);
						if (object == null) {
							continue;
						}
						row = sheet.getRow(rowIndex+j);
						cell = row.createCell(nextFillCellIndex);
						cell.setCellStyle(leftStyle);
						cell.setCellValue(getJSONObjectValue(object, "wpackagName"));
						
						cell = row.createCell(nextFillCellIndex+1);
						cell.setCellStyle(fourPrecisionStyle);
						cell.setCellValue(formatNum(object.get("price"),4));
						
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
								cell = row.createCell(nextFillCellIndex+maxColSpan-1);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
								cell = row.createCell(nextFillCellIndex+maxColSpan-2);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
							}
							cell = row.createCell(nextFillCellIndex+maxColSpan-1);
							
							switch (getJSONObjectValue(object, "wpackagType")) {
							case "FXD":
								remarks =  "??????(???/???) : " + formatNum(object.getString("unitsPrice"),4) + "\n" + 
										   "?????????(???/???) : " + formatNum(object.getString("useQuantity"),4) + "\n" + 
										   "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "ZX":
								remarks =  "??????????????????:" + getJSONObjectValue(object, "texture") + "\n" + 
										   "???(cm) : " + formatNum(object.getString("length"),4) + "\n" + 
										   "???(cm) : " + formatNum(object.getString("width"),4) + "\n" + 
										   "???(cm) : " + formatNum(object.getString("height"),4) + "\n" + 
										   "??????????????????(???) : " + formatNum(object.getString("area"),4) + "\n" + 
										   "??????(???/???) : " + formatNum(object.getString("unitsPrice"),4) + "\n" + 
										   "??????????????????(???/???) : " + formatNum(object.getString("ylUnitsPrice"),4) + "\n" + 
								           "??????  : " + getJSONObjectValue(object, "specification") + "\n" + 
							               "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							case "ELSE":
								remarks =  "?????? : " + getJSONObjectValue(object, "remarks");
								break;
							default:
								remarks = "";
								break;
							}
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					fillNoHasAccounting(sheet,rowIndex,nextFillCellIndex,maxColSpan,rowIndex+array.size(),rowIndex+wpackagsSize,isThisApplication,noLast);
					rowIndex = rowIndex+wpackagsSize;
					continue;
				case "wastage":
					array = jsonData.getJSONArray("accountingWastageList");
					for (int j = 0; j < array.size(); j++) {
						addMergedRegionBorder(sheet, rowIndex+j, rowIndex+j, nextFillCellIndex+2, nextFillCellIndex+maxColSpan-1);
						object = array.getJSONObject(j);
						if (object == null) {
							continue;
						}
						row = sheet.getRow(rowIndex+j);
						cell = row.createCell(nextFillCellIndex);
						cell.setCellStyle(leftStyle);
						cell.setCellValue(getJSONObjectValue(object, "wastageType"));
						
						cell = row.createCell(nextFillCellIndex+1);
						cell.setCellStyle(fourPrecisionStyle);
						cell.setCellValue(formatNum(object.get("price"),4));
						
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
								cell = row.createCell(nextFillCellIndex+maxColSpan-1);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{
							if (isShowUnits) {
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
								cell = row.createCell(nextFillCellIndex+maxColSpan-2);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex+j, rowIndex+j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
							}
							cell = row.createCell(nextFillCellIndex+maxColSpan-1);
							remarks = getJSONObjectValue(object, "remarks");
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					fillNoHasAccounting(sheet,rowIndex,nextFillCellIndex,maxColSpan,rowIndex+array.size(),rowIndex+wastagesSize,isThisApplication,noLast);
					rowIndex = rowIndex+wastagesSize;
					continue;
				case "yield":// ?????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("yieldValue"),2) + (formatNum(jsonData.getJSONObject("accountingCostItem").getString("yieldValue"),2).equals("")?"":"%");
					remarks = "???????????????(kg) : " + formatNum(countSum,4) + "\n" +
							  "??????????????????(???) : " + formatNum(costSum,4) + "\n" +
							  "??????????????????????????????(???/kg) : " + formatNum(avgSum,2) + "\n" +
							  "???????????????(kg) : " + formatNum(jsonData.getJSONObject("ingredient").getString("productCount"),4) + "\n" +
							  "???????????????(%) : " + jsonData.getJSONObject("ingredient").getString("moisture") + "\n" +
							  "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "yieldRemarks");
					if (!setUnits) {
						units = "1.000"+jsonData.getJSONObject("accounting").getString("units");
					}
					break;
				case "zlsubtotalValue": // ???????????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("zlsubtotalValue"),2);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "zlsubtotalRemarks");
					if (!setUnits) {
						units = "1.000"+jsonData.getJSONObject("accounting").getString("units");
					}
					break;
				case "flsubtotalValue":// ???????????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("flsubtotalValue"),2);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "flsubtotalRemarks");
					if (!setUnits) {
						units = "1.000"+jsonData.getJSONObject("accounting").getString("units");
					}
					break;
				case "factoryPrice":// ????????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingFactoryPrice").getString("price"),4);
					remarks =   "?????? : "+ getJSONObjectValue(jsonData.getJSONObject("accountingFactoryPrice"), "currency") + "\n" + 
						     	"???????????? : "+ getJSONObjectValue(jsonData.getJSONObject("accountingFactoryPrice"), "paymentType") + "\n" + 
							    "?????? : "+ getJSONObjectValue(jsonData.getJSONObject("accountingFactoryPrice"), "remarks");
					break;
				case "exchangeRate":// ??????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingExchangerate").getString("exchangerate"), 4) + (formatNum(jsonData.getJSONObject("accountingExchangerate").getString("exchangerate"), 4).equals("")?"":"%");
					if(jsonData.getJSONObject("accountingExchangerate").get("referenceDate") == null){
						startTime = "";
					}else{
						try {
							startTime = DateUtils.formatDateToStr((Date)JSONObject.toBean((JSONObject) jsonData.getJSONObject("accountingExchangerate").get("referenceDate"), Date.class), "yyyy-MM-dd");
						} catch (Exception e) {
							startTime = "";
						}
					}
					remarks =   "???????????? : "+  startTime + "\n" + 
						        "???????????? : "+ getJSONObjectValue(jsonData.getJSONObject("accountingExchangerate"), "referenceBank") + "\n" + 
							    "?????? : "+ getJSONObjectValue(jsonData.getJSONObject("accountingExchangerate"), "remarks");
					break;
				case "rmbSettlementPrice"://???????????????????????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("rmbSettlementPrice"),4);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "rmbSettlementPriceRemarks");	
					break;
				case "oceanfreight"://?????????/?????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingOceanfreight").getString("price"),4);
					String containerType = getJSONObjectValue(jsonData.getJSONObject("accountingOceanfreight"), "containerTypeString");
					if(jsonData.getJSONObject("accountingOceanfreight").get("transportStartDate") == null){
						startTime = "";
					}else{
						try {
							startTime = DateUtils.formatDateToStr((Date)JSONObject.toBean((JSONObject) jsonData.getJSONObject("accountingOceanfreight").get("transportStartDate"), Date.class), "yyyy-MM-dd");
						} catch (Exception e) {
							startTime = "";
						}
					}
					if(jsonData.getJSONObject("accountingOceanfreight").get("transportEndDate") == null){
						endTime = "";
					}else{
						try {
							endTime = DateUtils.formatDateToStr((Date)JSONObject.toBean((JSONObject) jsonData.getJSONObject("accountingOceanfreight").get("transportEndDate"), Date.class), "yyyy-MM-dd");
						} catch (Exception e) {
							endTime = "";
						}
					}
							remarks = "???????????? : " + startTime+"???"+endTime+"\n" + 
  		               		   		  "????????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingOceanfreight"),"starting") + "\n" + 
  		               		   		  "????????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingOceanfreight"),"destination") + "\n" + 
  		               		   		  "???????????? : " + containerType + "\n" + 
  		               		   		  "???????????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingOceanfreight"),"containerSize") + "\n" + 
  		               		   		  "??????(???/??????) : " + formatNum(jsonData.getJSONObject("accountingOceanfreight").getString("unitPrice"),4) + "\n" + 
  		               		   		  "?????????????????????&?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingOceanfreight"),"containerCapacity") + "\n" + 
  		               		   		  "???????????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingOceanfreight"),"computeType") + "\n" + 
  		               		   		  "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingOceanfreight"),"remarks");	
					break;
				case "orderFee"://?????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("updateOrderFee"),4);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "updateOrderFeeRemarks");
					break;
				case "premium"://?????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("premium"),4);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "premiumRemarks");	
					break;
				case "customscharges"://???????????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCustomscharges").getString("price"),4);
					remarks =  "????????? : " + jsonData.getJSONObject("accountingCustomscharges").getString("customscharges") +"\n" + 
		               		   "????????? : " + jsonData.getJSONObject("accountingCustomscharges").getString("portSurcharge") + "\n" + 
		               		   "????????? : " + jsonData.getJSONObject("accountingCustomscharges").getString("demurrageCharge") + "\n" + 
		               		   "????????? : " + jsonData.getJSONObject("accountingCustomscharges").getString("containerDirtynessChange") + "\n" + 
		               		   "???????????? : " + jsonData.getJSONObject("accountingCustomscharges").getString("elseFee") + "\n" + 
		               		   "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingCustomscharges"), "remarks");	
					break;
				case "importFeeTotal"://??????????????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("importFeeTotal"),4);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "importFeeTotalRemarks");	
					break;
				case "customsduties"://??????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCustomsduties").getString("price"),4);
					remarks =   "HS?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingCustomsduties"), "hsCode") +"\n" + 
		               		   	"??????(%) : " + formatNum(jsonData.getJSONObject("accountingCustomsduties").getString("taxRate"),4) + "\n" + 
		               		   	"?????????????????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingCustomsduties"), "customsdutiesComputeType") + "\n" + 
		               		    "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingCustomsduties"), "remarks");
					break;
				case "addedvaluetax"://?????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingAddedvaluetax").getString("price"),4);
					remarks =   "??????(%) : " + formatNum(jsonData.getJSONObject("accountingAddedvaluetax").getString("taxRate"),4) +"\n" + 
		               			"?????????????????????  : " + getJSONObjectValue(jsonData.getJSONObject("accountingAddedvaluetax"), "addedvaluetaxComputeType") + "\n" + 
	               		        "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingAddedvaluetax"), "remarks");	
					break;
				case "cdAvtTotal"://????????????????????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("cdAvtTotal"),4);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "cdAvtTotalRemarks");	
					break;
				case "customsClearanceTotal"://????????????????????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("customsClearanceTotal"),4);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "customsClearanceTotalRemark");	
					break;
				case "packagproportionValue":// ????????????
					valueStyle = twoPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("packagproportionValue"),2) + (formatNum(jsonData.getJSONObject("accountingCostItem").getString("packagproportionValue"),2).equals("")?"":"%");
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "packagproportionRemarks");
					break;
				case "deductptcostValue":// ?????????????????????????????????
					valueStyle = fourPrecisionRedStyle;
					remarksStyle = leftRedStyle;
					unitsStyle = leftRedStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("deductptcostValue"),4);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "deductptcostRemarks");
					break;
				case "nwpackagsubtotalValue":// ??????????????????????????????
					valueStyle = fourPrecisionRedStyle;
					remarksStyle = leftRedStyle;
					unitsStyle = leftRedStyle;
					value = formatNum(jsonData.getJSONObject("accountingCostItem").getString("nwpackagsubtotalValue"),4);
					remarks = getJSONObjectValue(jsonData.getJSONObject("accountingCostItem"), "nwpackagsubtotalRemarks");
					break;
				case "taxDiffer"://??????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingTaxDiffer").getString("price"),4);
					remarks = "?????????????????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingTaxDiffer"), "taxDifferComputeType") +"\n" + 
           		   		       "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingTaxDiffer"), "remarks");
					break;
				case "interest"://??????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingInterest").getString("price"),4);
					remarks = "????????????(%) : " + formatNum(jsonData.getJSONObject("accountingInterest").getString("loanRate"),4) +"\n" + 
						      "?????????????????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingInterest"), "interestComputeType") +"\n" + 
		   		              "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingInterest"), "remarks");
					break;
				case "wecPrice":// ?????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingWec").getString("price"),4);
					remarks =   "??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("water"),4) + "\n" + 
							    "??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("oil"),4) + "\n" + 
								"??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("electricity"),4) + "\n" + 
								"??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("gas"),4) + "\n" + 
								"??????(???/t??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("coal"),4) + "\n" + 
								"??????(???/kg??????) : "+ formatNum(jsonData.getJSONObject("accountingWec").getString("total"),4) + "\n" + 
								"?????? : "+ getJSONObjectValue(jsonData.getJSONObject("accountingWec"), "remarks");
					break;
				case "sbzjwhPrice":// ?????????????????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("price"),4);
			  	    remarks = "????????????(??????) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("totalPrice"),4) + "\n" + 
					          "????????????(???) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("ageLimit"),4) + "\n" + 
			  	    		  "?????????(??????/???) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("depreciation"),4) + "\n" + 
					          "?????????(t??????/???) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("capacity"),4)+ "\n" + 
					          "???????????????(???/kg??????) : " + formatNum(jsonData.getJSONObject("accountingSbzjwh").getString("total"),4) + "\n" + 
			  	    		  "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingSbzjwh"), "remarks");
					break;
				case "ampPrice":// ??????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingManpower").getString("price"),4);
				    remarks = "???????????????(??????) : " + formatNum(jsonData.getJSONObject("accountingManpower").getString("manpowerCount"),4)+ "\n" + 
							  "????????????(???/???/???) : " + formatNum(jsonData.getJSONObject("accountingManpower").getString("avgWage"),4)+ "\n" + 
							  "?????????(t) : " + formatNum(jsonData.getJSONObject("accountingManpower").getString("monthCapacity"),4)+ "\n" + 
							  "???kg??????(???/kg) : " + formatNum(jsonData.getJSONObject("accountingManpower").getString("unitsWage"),4)+ "\n" + 
							  "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingManpower"), "remarks");
					break;
				case "amaPrice":// ???????????????
					valueStyle = fourPrecisionStyle;
					value = formatNum(jsonData.getJSONObject("accountingManage").getString("price"),4);
					array = jsonData.getJSONArray("accountingManageRegionList");
					for (int i = 0; i < array.size(); i++) {
						try {
							object = array.getJSONObject(i);
							remarks += "????????????" + object.getString("region") + " : \n" + 
							           "??????(%) : " + formatNum(object.getString("proportion"),4) + "\n";
						} catch (Exception e) {
							remarks += "????????????" + " : /\n" + 
							           "??????(%) : " + "/\n";
						}
					}
					remarks += "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingManage"), "remarks");
					break;
				case "afrPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingFreightRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,false);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(fourPrecisionStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								} else {
									cell.setCellValue(formatNum(object.get("price"),4));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				                
								for (int i = 0; i < array.size(); i++) {
									 try {
										object = array.getJSONObject(i);
										 remarks += "????????????" + object.getString("region") + " : \n" + 
												    "????????????(km) : "+ formatNum(object.getString("sumKm"),4) + "\n";
									} catch (Exception e) {
										remarks += "????????????" + " : /\n" + 
												   "????????????(km) : "+ "/\n";
									}
								}
								remarks += "????????????(???/"+jsonData.getJSONObject("accountingFreight").getString("units")+") : " + formatNum(jsonData.getJSONObject("accountingFreight").getString("unitsCost"),4) + "\n"+
								           "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingFreight"), "remarks");
								cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
								cell.setCellStyle(leftStyle);
								cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "atrPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingTaxRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,false);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(fourPrecisionStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								} else {
									cell.setCellValue(formatNum(object.get("price"),4));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				                
				            for (int i = 0; i < array.size(); i++) {
								 try {
									object = array.getJSONObject(i);
									 remarks += "????????????" + object.getString("region") + " : \n" + 
											    "??????(%) : "+ formatNum(object.getString("proportion"),4) + "\n";
								} catch (Exception e) {
									remarks += "????????????" + " : /\n" + 
											   "?????? (%) : "+ "/\n";
								}
							}
							remarks += "??????(%) : " + formatNum(jsonData.getJSONObject("accountingTax").getString("taxRate"),4) + "\n"+
							           "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingTax"), "remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "aprPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingProfitRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,false);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(fourPrecisionStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								} else {
									cell.setCellValue(formatNum(object.get("price"),4));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				            for (int i = 0; i < array.size(); i++) {
								 try {
									object = array.getJSONObject(i);
									 remarks += "????????????" + object.getString("region") + " : \n" + 
											    "??????(%) : "+ formatNum(object.getString("proportion"),4) + "\n";
								} catch (Exception e) {
									remarks += "????????????" + ": /\n" + 
										       "??????(%) : " + "/\n";
								}
							}
							remarks += "?????? : " + getJSONObjectValue(jsonData.getJSONObject("accountingProfit"), "remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "subTotal":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingElsesubtotalRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,true);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(fourPrecisionRedStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								} else {
									cell.setCellValue(formatNum(object.get("subtotal"),4));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(leftRedStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(leftRedStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				            remarks = getJSONObjectValue(jsonData.getJSONObject("accountingElsesubtotal"), "remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftRedStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "sumPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("accountingAggregateRegionList");
					row = sheet.getRow(rowIndex);
					if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
						fillNoHasAccounting(sheet,jsonData,row,rowIndex,nextFillCellIndex,maxColSpan,isThisApplication ,noLast,false);
					}else{
						for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
							cell = row.createCell(nextFillCellIndex + i);
							cell.setCellStyle(twoPrecisionStyle);
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								}else{
									cell.setCellValue(formatNum(object.get("sumPrice"),2));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
						if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
							if (isShowUnits) {  //?????????????????????????????????????????????
								cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 1));
							}
						}else{ 
				            if (isShowUnits) { //????????????????????????
				            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 3));
								cell.setCellStyle(unitsStyle);
								// ???????????????????????????,???????????????????????????
								cell.setCellValue(units);
							}else{ //???????????????????????????
									sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+array.size()-1, nextFillCellIndex + maxColSpan - 2));
							}
				            remarks = getJSONObjectValue(jsonData.getJSONObject("accountingAggregate"), "remarks");
							cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
							cell.setCellStyle(leftStyle);
							cell.setCellValue(remarks);
						}
					}
					rowIndex++;
					continue;
				case "mcpPrice":
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
					array = jsonData.getJSONArray("merchandiseContractPrices");
					for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
						row = sheet.getRow(rowIndex);
						cell = row.createCell(nextFillCellIndex + i);
						cell.setCellStyle(twoPrecisionStyle);
						if (isThisApplication) {
							cell.setCellValue("");
						} else {
							try {
								object = array.getJSONObject(i);
								if (object == null) {
									cell.setCellValue("");
								}else{
									cell.setCellValue(formatNum(object.get("price"),2));
								}
							} catch (Exception e) {
								cell.setCellValue("");
							}
						}
					}
					if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
						if (isShowUnits) {  //?????????????????????????????????????????????
							cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 2));
							cell.setCellStyle(unitsStyle);
							// ???????????????????????????,???????????????????????????
							if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
								cell.setCellValue("");
							}else{
								cell.setCellValue(units);
							}
						}else{
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 1));
						}
					}else{ 
			            if (isShowUnits) { //????????????????????????
			            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
							sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 3));
							cell.setCellStyle(unitsStyle);
							// ???????????????????????????,???????????????????????????
							if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")) {
								cell.setCellValue("");
							}else{
								cell.setCellValue(units);
							}
						}else{ //???????????????????????????
								sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 2));
						}
			            remarks = "";
						cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
						cell.setCellStyle(leftStyle);
						cell.setCellValue(remarks);
					}
					rowIndex++;
					continue;
				default:
					break;
				}
			}
			} catch (Exception e) {
				value="";
				remarks="";
			}
			// ????????????????????????
			if (getJSONObjectValue(jsonData.getJSONObject("accounting"),"accountingCode").equals("")&&rowIndex > 9) {
				value = "";
				remarks = "";
				units = "";
			}
			fillCell(sheet, rowIndex, isThisApplication, nextFillCellIndex, value, remarks, units,valueStyle,remarksStyle,unitsStyle, maxColSpan ,noLast);
			rowIndex++;
		}
		return (nextFillCellIndex + maxColSpan);
	}
	
	/**
	 * ????????????????????????
	 * 
	 * @param sheet
	 *            sheet
	 * @param rowIndex
	 *            ????????????????????????
	 * @param listIndex
	 *            ?????????????????????
	 * @param data
	 *            ??????????????????
	 * @param maxColSpan
	 *            ???????????????????????????
	 * @param regionSize
	 *            ???????????????
	 */
	public void fillCell(Sheet sheet, int rowIndex,Boolean isThisApplication, int nextFillCellIndex, Object value, String remarks, String units, CellStyle valueStyle, CellStyle remarksStyle,CellStyle unitsStyle, int maxColSpan, boolean noLast) {
		Row row = sheet.getRow(rowIndex);
		Cell cell  = null;
		if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
			if (isShowUnits && rowIndex > 9) {  //?????????????????????????????????????????????
				cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 2));
				addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 2);
				cell.setCellStyle(unitsStyle);
				// ???????????????????????????,???????????????????????????
				cell.setCellValue(units);
			}else{  //??????????????????
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 1));	
				addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 1);
			}
		}else{ 
            if (isShowUnits && rowIndex > 9) { //????????????????????????
            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 3));
				addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 3);
				cell.setCellStyle(unitsStyle);
				// ???????????????????????????,???????????????????????????
				cell.setCellValue(units);
			}else{ //???????????????????????????
				if (rowIndex < 9) {
					sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 1));
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 1);
				}else{
					sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 2));
					addMergedRegionBorder(sheet, rowIndex, rowIndex, nextFillCellIndex, nextFillCellIndex + maxColSpan - 2);
				}
			}
            	// ??????????????????????????????,????????????
                cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
				cell.setCellStyle(remarksStyle);
                
        		if (StringUtils.isBlank(remarks)) {
        			remarks = "";
        		}
    			cell.setCellValue(remarks);
		}
		// ???????????????????????????,????????????
		cell = row.createCell(nextFillCellIndex);
		//????????????????????????????????????
		cell.setCellStyle(valueStyle);
        value = (value == null ? "" : value.toString()); // ?????????????????????/
		try {
			if (rowIndex == 2 || rowIndex == 4) {//?????????????????????????????????????????????????????????,???????????????0?????????
				cell.setCellValue(value.toString());
			} else {
				cell.setCellValue(Double.valueOf(value.toString()));
			}
		} catch (Exception e) {
			cell.setCellValue(value.toString());
		}
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param sheet Sheet
	 * @param rowIndex ??????????????????
	 * @param nextFillCellIndex ??????????????????
	 * @param maxColSpan ?????????????????????
	 * @param firstRowIndex ????????????????????????
	 * @param lastRowIndex ???????????????????????????
	 * @param isThisApplication ???????????????????????????
	 * @param noLast ???????????????????????????
	 */
	public void fillNoHasAccounting(Sheet sheet, int rowIndex ,int nextFillCellIndex , int maxColSpan ,int firstRowIndex,int lastRowIndex , boolean isThisApplication , boolean noLast){
		for (int j = firstRowIndex; j < lastRowIndex; j++) {
			addMergedRegionBorder(sheet, j, j, nextFillCellIndex, nextFillCellIndex+maxColSpan-1);
			Row row = sheet.getRow(j);
			
			Cell cell = row.createCell(nextFillCellIndex);
			cell.setCellStyle(leftStyle);
			cell.setCellValue("");
			
			cell = row.createCell(nextFillCellIndex+1);
			cell.setCellStyle(twoPrecisionStyle);
			cell.setCellValue("");
			
			if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
				if (isShowUnits) {
					sheet.addMergedRegion(new CellRangeAddress(j, j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
					cell = row.createCell(nextFillCellIndex+maxColSpan-1);
					cell.setCellStyle(leftStyle);
					cell.setCellValue("");
				}else{
					sheet.addMergedRegion(new CellRangeAddress(j, j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 1));
				}
			}else{
				if (isShowUnits) {
					sheet.addMergedRegion(new CellRangeAddress(j, j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 3));
					cell = row.createCell(nextFillCellIndex+maxColSpan-2);
					cell.setCellStyle(leftStyle);
					cell.setCellValue("");
				}else{
					sheet.addMergedRegion(new CellRangeAddress(j, j, nextFillCellIndex+1, nextFillCellIndex + maxColSpan - 2));
				}
				cell = row.createCell(nextFillCellIndex+maxColSpan-1);
				cell.setCellStyle(leftStyle);
				cell.setCellValue("");
			}
		}
	}
	
	
	/**
	 * ??????????????????????????????????????????
	 * @param sheet Sheet
	 * @param jsonData ????????????
	 * @param row ?????????
	 * @param rowIndex ??????????????????
	 * @param nextFillCellIndex ??????????????????
	 * @param maxColSpan ?????????????????????
	 * @param isThisApplication ???????????????????????????
	 * @param noLast ???????????????????????????
	 * @param isRed ????????????????????????
	 */
	public void fillNoHasAccounting(Sheet sheet,JSONObject jsonData,Row row, int rowIndex ,int nextFillCellIndex , int maxColSpan ,boolean isThisApplication , boolean noLast,boolean isRed){
		Cell cell = null;
		for (int i = 0; i < jsonData.getJSONArray("accountingRegionList").size(); i++) {
			cell = row.createCell(nextFillCellIndex + i);
			cell.setCellStyle(twoPrecisionStyle);
			cell.setCellValue("");
		}
		if (isHideAllRemarks||(isShowThisRemarks&&!isThisApplication)||(isShowThisRemarks&&noLast)) {
			if (isShowUnits) {  //?????????????????????????????????????????????
				cell = row .createCell(nextFillCellIndex + maxColSpan - 1);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 2));
				if (isRed) {
					cell.setCellStyle(leftRedStyle);
				} else {
					cell.setCellStyle(leftStyle);
				}
				cell.setCellValue("");
			}else{
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 1));
			}
		}else{ 
            if (isShowUnits) { //????????????????????????
            	cell = row .createCell(nextFillCellIndex + maxColSpan - 2);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 3));
				if (isRed) {
					cell.setCellStyle(leftRedStyle);
				} else {
					cell.setCellStyle(leftStyle);
				}
				cell.setCellValue("");
			}else{ //???????????????????????????
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, nextFillCellIndex+jsonData.getJSONArray("accountingRegionList").size()-1, nextFillCellIndex + maxColSpan - 2));
			}
			cell = row.createCell(nextFillCellIndex + maxColSpan - 1);
			if (isRed) {
				cell.setCellStyle(leftRedStyle);
			} else {
				cell.setCellStyle(leftStyle);
			}
			cell.setCellValue("");
		}
	}
	
	
	/**
	 * ?????????????????????????????????
	 * 
	 * @param sheet sheet
	 * @param firstRow ?????????
	 * @param lastRow  ?????????
	 * @param firstCell  ?????????
	 * @param lastCell  ?????????
	 */
	public void addMergedRegionBorder(Sheet sheet,int firstRow, int lastRow, int firstCell, int lastCell){
		for (int i = firstRow; i <= lastRow; i++) {
			Row row = sheet.getRow(i);
			if (row == null) {
				row = sheet.createRow(i);
			}
			for (int j = firstCell; j <= lastCell; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
				    cell = row.createCell(j);	
				}
				cell.setCellStyle(leftStyle);
			}
		}
	}	
	
	/**
	 * ????????????
	 * @param sheet sheet
	 */
	public void autoSizeListWidth(Sheet sheet){
		Row row = sheet.getRow(0);
		for (int i = 0; i < row.getLastCellNum(); i++) {
			sheet.setColumnWidth(i, (90*80));
		}
	}
	
	/**
	 * ???????????????
	 * @param value ??????
	 * @param precision ??????
	 * @return ???????????????????????????
	 */
	public String formatNum(Object value,int precision) {
			if (value == null) {
				return "";
			}else{
				try {
					switch (precision) {
					case 2:
						value =  DecimalFormatUtils.formatBigDecimal(new BigDecimal(value.toString()), "#,##0.00");
						break;
					case 3:
						value =  DecimalFormatUtils.formatBigDecimal(new BigDecimal(value.toString()), "#,##0.000");
						break;
					case 4:
						value =  DecimalFormatUtils.formatBigDecimal(new BigDecimal(value.toString()), "#,##0.0000");
						break;
					default:
						break;
					}
					return value.toString();
				} catch (NumberFormatException e) {
					return value.toString();
				}
			}
	}
	
	/**
	 *  ??????JSONObject??????
	 * @param obj JSONObject??????
	 * @param key ?????????key
	 * @return ????????????
	 */
	public String getJSONObjectValue(JSONObject obj,String key){
		try {
			if (obj == null||obj.get(key) == null) {
				return "";
			}else{
				return StringUtils.trim(obj.get(key).toString());
			}
		} catch (Exception e) {
			return "";
		}
	}
	
	
	@Override
	public String isnertReports(String fileName, Map<String, Object> paraMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableInfo", paraMap.get("tableInfo"));
		map.put("width", paraMap.get("width"));
		String tarPath;
		try {
			tarPath = FreeMarkerUtil.generateHtml("totalCostAnalogyAnalysis/totalCostAnalogyAnalysisReport.ftl", "totalCostAnalogyAnalysis".concat("/").concat(fileName), map);
		} catch (Exception e) {
			return "???????????????????????????";
		}
		File file = new File(tarPath);// ????????????
		if (file.exists()) {
			try {
				Reports myReport = new Reports(BusinessConstants.myReportType.MCA.toString(), fileName, tarPath.replace(ConfigPath.getUploadFilePath(), ""));
				ReportsServiceImpl.getInstance().insertReports(myReport);
				//????????????????????????????????????
				String reportCode = ReportsServiceImpl.getInstance().searchCurrSequence();
				CostAnalysisMerchandise costAnalysisMerchandise = new CostAnalysisMerchandise();
				JSONArray array = JSONArray.fromObject(paraMap.get("info"));
				for (int i = 0; i < array.size(); i++) {
					JSONObject object = array.getJSONObject(i);
					costAnalysisMerchandise.setReportsCode(reportCode);
					costAnalysisMerchandise.setAccountingCode(object.get("accountingCode").toString());
					costAnalysisMerchandise.setMerchandiseCode(object.get("merchandiseCode").toString());
					costAnalysisMerchandise.setSupplierCode(object.get("supplierCode").toString());
					ReportsServiceImpl.getInstance().insertReportAnalysis(costAnalysisMerchandise);
				}
			} catch (Exception e) {
				file.delete();
				LoggerUtil.logger.error("TotalCostAnalogyAnalysisServiceImpl.isnertReports????????????["+ file.getPath() +"]");
				return "?????????????????????????????????";
			}
		} else {
			return "??????Html????????????";
		}
		return null;
	}

}
