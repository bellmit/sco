package com.powere2e.sco.model.merchandisecostanalysis.totalcostanalogyanalysis;

import java.util.List;

import com.powere2e.frame.server.model.AppModel;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.Accounting;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingAddedvaluetax;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingAggregate;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingAggregateRegion;
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
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingRegion;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingSbzjwh;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingTax;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingTaxDiffer;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingTaxRegion;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingWPackag;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingWastage;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.accounting.AccountingWec;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.ingredient.Ingredient;
import com.powere2e.sco.model.merchandisecostanalysis.accountingingredient.ingredient.IngredientItem;

public class TotalCostAnalysis extends AppModel{

	private static final long serialVersionUID = 560607212933419197L;
	
	private Accounting accounting;  //?????????
	private Ingredient ingredient;  //?????????
	private List<IngredientItem> ingredientItemList;  //????????????
	private List<AccountingRegion> accountingRegionList;  //???????????????
	private AccountingCostItem accountingCostItem;  //??????????????????
	private List<AccountingNPackag> accountingNPackagList;  //?????????
	private List<AccountingWPackag> accountingWPackagList;  //?????????
	private List<AccountingWastage> accountingWastageList;  //??????
	private AccountingWec accountingWec;  //?????????
	private AccountingSbzjwh accountingSbzjwh;  //?????????????????????
	private AccountingManpower accountingManpower;  //??????
	private AccountingManage accountingManage;  //??????
	private List<AccountingManageRegion> accountingManageRegionList;  //???????????????
	private AccountingFreight accountingFreight;  //??????
	private List<AccountingFreightRegion> accountingFreightRegionList;  //???????????????
	private AccountingTax accountingTax;  //??????
	private List<AccountingTaxRegion> accountingTaxRegionList;  //???????????????
	private AccountingProfit accountingProfit;  //??????
	private List<AccountingProfitRegion> accountingProfitRegionList;  //???????????????
	private AccountingElsesubtotal accountingElsesubtotal;  //??????????????????
	private List<AccountingElsesubtotalRegion> accountingElsesubtotalRegionList;  //???????????????????????????
	private AccountingAggregate accountingAggregate;  //??????
	private List<AccountingAggregateRegion> accountingAggregateRegionList;  //???????????????
	private List<MerchandiseContractPrice> merchandiseContractPrices;  //????????????
	private AccountingFactoryPrice accountingFactoryPrice;  //????????????
	private AccountingExchangerate accountingExchangerate;  //??????
	private AccountingOceanfreight accountingOceanfreight;  //???/?????????
	private AccountingCustomscharges accountingCustomscharges;  //????????????????????????
	private AccountingCustomsduties accountingCustomsduties;  //???????????????
	private AccountingAddedvaluetax accountingAddedvaluetax;  //??????????????????
	private AccountingTaxDiffer accountingTaxDiffer;  //???????????????
	private AccountingInterest accountingInterest;  //???????????????
	private String oaApplicationCode;   //OA????????????
	
	public Accounting getAccounting() {
		return accounting;
	}
	public void setAccounting(Accounting accounting) {
		this.accounting = accounting;
	}
	public Ingredient getIngredient() {
		return ingredient;
	}
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	public List<IngredientItem> getIngredientItemList() {
		return ingredientItemList;
	}
	public void setIngredientItemList(List<IngredientItem> ingredientItemList) {
		this.ingredientItemList = ingredientItemList;
	}
	public List<AccountingRegion> getAccountingRegionList() {
		return accountingRegionList;
	}
	public void setAccountingRegionList(List<AccountingRegion> accountingRegionList) {
		this.accountingRegionList = accountingRegionList;
	}
	public AccountingCostItem getAccountingCostItem() {
		return accountingCostItem;
	}
	public void setAccountingCostItem(AccountingCostItem accountingCostItem) {
		this.accountingCostItem = accountingCostItem;
	}
	public List<AccountingNPackag> getAccountingNPackagList() {
		return accountingNPackagList;
	}
	public void setAccountingNPackagList(List<AccountingNPackag> accountingNPackagList) {
		this.accountingNPackagList = accountingNPackagList;
	}
	public List<AccountingWPackag> getAccountingWPackagList() {
		return accountingWPackagList;
	}
	public void setAccountingWPackagList(List<AccountingWPackag> accountingWPackagList) {
		this.accountingWPackagList = accountingWPackagList;
	}
	public List<AccountingWastage> getAccountingWastageList() {
		return accountingWastageList;
	}
	public void setAccountingWastageList(List<AccountingWastage> accountingWastageList) {
		this.accountingWastageList = accountingWastageList;
	}
	public AccountingWec getAccountingWec() {
		return accountingWec;
	}
	public void setAccountingWec(AccountingWec accountingWec) {
		this.accountingWec = accountingWec;
	}
	public AccountingSbzjwh getAccountingSbzjwh() {
		return accountingSbzjwh;
	}
	public void setAccountingSbzjwh(AccountingSbzjwh accountingSbzjwh) {
		this.accountingSbzjwh = accountingSbzjwh;
	}
	public AccountingManpower getAccountingManpower() {
		return accountingManpower;
	}
	public void setAccountingManpower(AccountingManpower accountingManpower) {
		this.accountingManpower = accountingManpower;
	}
	public AccountingManage getAccountingManage() {
		return accountingManage;
	}
	public void setAccountingManage(AccountingManage accountingManage) {
		this.accountingManage = accountingManage;
	}
	public List<AccountingManageRegion> getAccountingManageRegionList() {
		return accountingManageRegionList;
	}
	public void setAccountingManageRegionList(List<AccountingManageRegion> accountingManageRegionList) {
		this.accountingManageRegionList = accountingManageRegionList;
	}
	public AccountingFreight getAccountingFreight() {
		return accountingFreight;
	}
	public void setAccountingFreight(AccountingFreight accountingFreight) {
		this.accountingFreight = accountingFreight;
	}
	public List<AccountingFreightRegion> getAccountingFreightRegionList() {
		return accountingFreightRegionList;
	}
	public void setAccountingFreightRegionList(List<AccountingFreightRegion> accountingFreightRegionList) {
		this.accountingFreightRegionList = accountingFreightRegionList;
	}
	public AccountingTax getAccountingTax() {
		return accountingTax;
	}
	public void setAccountingTax(AccountingTax accountingTax) {
		this.accountingTax = accountingTax;
	}
	public List<AccountingTaxRegion> getAccountingTaxRegionList() {
		return accountingTaxRegionList;
	}
	public void setAccountingTaxRegionList(List<AccountingTaxRegion> accountingTaxRegionList) {
		this.accountingTaxRegionList = accountingTaxRegionList;
	}
	public AccountingProfit getAccountingProfit() {
		return accountingProfit;
	}
	public void setAccountingProfit(AccountingProfit accountingProfit) {
		this.accountingProfit = accountingProfit;
	}
	public List<AccountingProfitRegion> getAccountingProfitRegionList() {
		return accountingProfitRegionList;
	}
	public void setAccountingProfitRegionList(List<AccountingProfitRegion> accountingProfitRegionList) {
		this.accountingProfitRegionList = accountingProfitRegionList;
	}
	public AccountingElsesubtotal getAccountingElsesubtotal() {
		return accountingElsesubtotal;
	}
	public void setAccountingElsesubtotal(AccountingElsesubtotal accountingElsesubtotal) {
		this.accountingElsesubtotal = accountingElsesubtotal;
	}
	public List<AccountingElsesubtotalRegion> getAccountingElsesubtotalRegionList() {
		return accountingElsesubtotalRegionList;
	}
	public void setAccountingElsesubtotalRegionList(List<AccountingElsesubtotalRegion> accountingElsesubtotalRegionList) {
		this.accountingElsesubtotalRegionList = accountingElsesubtotalRegionList;
	}
	public AccountingAggregate getAccountingAggregate() {
		return accountingAggregate;
	}
	public void setAccountingAggregate(AccountingAggregate accountingAggregate) {
		this.accountingAggregate = accountingAggregate;
	}
	public List<AccountingAggregateRegion> getAccountingAggregateRegionList() {
		return accountingAggregateRegionList;
	}
	public void setAccountingAggregateRegionList(List<AccountingAggregateRegion> accountingAggregateRegionList) {
		this.accountingAggregateRegionList = accountingAggregateRegionList;
	}
	public List<MerchandiseContractPrice> getMerchandiseContractPrices() {
		return merchandiseContractPrices;
	}
	public void setMerchandiseContractPrices(List<MerchandiseContractPrice> merchandiseContractPrices) {
		this.merchandiseContractPrices = merchandiseContractPrices;
	}
	public AccountingFactoryPrice getAccountingFactoryPrice() {
		return accountingFactoryPrice;
	}
	public void setAccountingFactoryPrice(AccountingFactoryPrice accountingFactoryPrice) {
		this.accountingFactoryPrice = accountingFactoryPrice;
	}
	public AccountingExchangerate getAccountingExchangerate() {
		return accountingExchangerate;
	}
	public void setAccountingExchangerate(AccountingExchangerate accountingExchangerate) {
		this.accountingExchangerate = accountingExchangerate;
	}
	public AccountingOceanfreight getAccountingOceanfreight() {
		return accountingOceanfreight;
	}
	public void setAccountingOceanfreight(AccountingOceanfreight accountingOceanfreight) {
		this.accountingOceanfreight = accountingOceanfreight;
	}
	public AccountingCustomscharges getAccountingCustomscharges() {
		return accountingCustomscharges;
	}
	public void setAccountingCustomscharges(AccountingCustomscharges accountingCustomscharges) {
		this.accountingCustomscharges = accountingCustomscharges;
	}
	public AccountingCustomsduties getAccountingCustomsduties() {
		return accountingCustomsduties;
	}
	public void setAccountingCustomsduties(AccountingCustomsduties accountingCustomsduties) {
		this.accountingCustomsduties = accountingCustomsduties;
	}
	public AccountingAddedvaluetax getAccountingAddedvaluetax() {
		return accountingAddedvaluetax;
	}
	public void setAccountingAddedvaluetax(AccountingAddedvaluetax accountingAddedvaluetax) {
		this.accountingAddedvaluetax = accountingAddedvaluetax;
	}
	public AccountingTaxDiffer getAccountingTaxDiffer() {
		return accountingTaxDiffer;
	}
	public void setAccountingTaxDiffer(AccountingTaxDiffer accountingTaxDiffer) {
		this.accountingTaxDiffer = accountingTaxDiffer;
	}
	public AccountingInterest getAccountingInterest() {
		return accountingInterest;
	}
	public void setAccountingInterest(AccountingInterest accountingInterest) {
		this.accountingInterest = accountingInterest;
	}
	public String getOaApplicationCode() {
		return oaApplicationCode;
	}
	public void setOaApplicationCode(String oaApplicationCode) {
		this.oaApplicationCode = oaApplicationCode;
	}
	
}
