<#compress>
<!DOCTYPE html>  
<html>  
<head>  
    <meta charset="UTF-8">  
    <#include "inc/easyui.ftl" />
    <script type="text/javascript" >
		<#include "sco/merchandiseCostAnalysis/totalCostAnalogyAnalysis/totalCostAnalogyAnalysis.js" />
		<#include "sco/common/masterDataType.js" />
    </script>
    <link rel="stylesheet" href="css/table.min.css" type="text/css" />
</head>
<body>
	<!-- 商品总成本类比分析表 --> 
	<div id="totalCostAnalogyAnalysis_toolbar">
		<form id="signedQty_search">
		  <table class="table table-condensed" style="width:1000px;">
				<tr>
					<td style="text-align:right;width:125px">供应商/意向供应商编号：</td>
					<td style="width:125px"><input class="easyui-validatebox" filterName="supplierCode" type="text" name="supplierCode" id="supplierCode" data-options="required:false,editable:false" style="width: 120px;" /></td>
					<td style="text-align:right;width:125px">供应商/意向供应商名称：</td>
					<td style="width:125px"><input class="easyui-validatebox" filterName="supplierName" type="text" name="supplierName" id="supplierName" data-options="required:false,editable:false" style="width: 120px;" /></td>
					<td style="text-align:right;width:125px">商品/意向品编号：</td>
					<td style="width:125px"><input class="easyui-validatebox" filterName="merchandiseCode" type="text" name="merchandiseCode" id="merchandiseCode" data-options="required:false,editable:false" style="width: 120px;" /></td>
					<td style="text-align:right;width:125px">商品/意向品名称：</td>
					<td style="width:125px"><input class="easyui-validatebox" filterName="merchandiseName" type="text" name="merchandiseName" id="merchandiseName" data-options="required:false,editable:false" style="width: 120px;" /></td>
				</tr>
				<tr>
					<td align="right">中分类：</td>
					<td>
						<input class="easyui-combobox filterSelect" filterName="centreTypeCode" id="centreTypeCodeElse" style="width: 124px;" 
								data-options="
									valueField:'id',
									textField:'text',
									editable:false,
								    url:'masterDataType_listCentreType_5.html'
								    ">
						</input>
					</td>
					<td align="right">小分类：</td>
					<td>
						<input class="easyui-combobox filterSelect" id="smallTypeCode" filterName="smallTypeCode" style="width: 124px;"
								data-options="
									valueField:'id',
									textField:'text',
									editable:false">
						</input>
					</td>
					<td align="right">明细类：</td>
					<td>
						<input class="easyui-combobox filterSelect" id="detailTypeCode" filterName="detailTypeCode" style="width: 124px;"
								data-options="
									valueField:'id',
									textField:'text',
									editable:false">
						</input>
					</td>
					<td align="right">细分类：</td>
					<td>
						<input class="easyui-combobox filterSelect" id="fineTypeCode" filterName="fineTypeCode" style="width: 124px;"
								data-options="
									valueField:'id',
									textField:'text',
									editable:false">
						</input>
					</td>
				</tr>
				<tr>
					<td align="right">采购部门：</td>
					<td>
						<input class="easyui-combobox filterSelect" filterName="purchaseDepartments" id="purchaseDepartments" style="width: 124px;" 
								data-options="
									valueField:'id',
									textField:'text',
									panelHeight:'auto',
									editable:false,
								    url:'businessComBox_procurementDepartments_5.html'
								    ">
						</input>
					</td>
					<td align="right">是否定量：</td>
					<td>
						<input class="easyui-combobox filterSelect" filterName="rationed" id="rationed" style="width: 124px;" 
								data-options="
									valueField:'id',
									textField:'text',
									panelHeight:'auto',
									editable:false,
								    url:'businessComBox_rationed_5.html'
								    ">
						</input>
					</td>
					<td align="right">采购类型：</td>
					<td>
						<input class="easyui-combobox filterSelect" filterName="purchaseType" id="purchaseType" style="width: 124px;" 
								data-options="
									valueField:'id',
									textField:'text',
									panelHeight:'auto',
									editable:false,
								    url:'businessComBox_purchaseType_5.html'
								    ">
						</input>
					</td>
					<td align="right">销售方式：</td>
					<td>
						<input class="easyui-combobox filterSelect" filterName="saleType" id="saleType" style="width: 124px;" 
								data-options="
									valueField:'id',
									textField:'text',
									panelHeight:'auto',
									editable:false,
								    url:'masterDataType_listSaleType_5.html'
								    ">
						</input>
					</td>
				</tr>
				<tr>
					<td align="right">核算/投料表编号：</td>
					<td><input class="easyui-validatebox" filterName="accountingCode" type="text" name="accountingCode" id="accountingCode" data-options="required:false,editable:false" style="width: 120px;" /></td>
					<td align="right">操作人：</td>
					<td><input class="easyui-validatebox" filterName="updateby" type="text" name="updateby" id="updateby" data-options="required:false,editable:false" style="width: 120px;" /></td>
					<td align="right">核算/投料表修改日期：</td>
					<td><input class="easyui-datebox filterInput" filterName="minUpdated" name="minUpdated" id="minUpdated" data-options="required:false,editable:false,onHidePanel:utils.dateFilter.setMaxDateValue" style="width: 124px;" /> </td>
					<td align="right">至：</td>
					<td><input class="easyui-datebox filterInput" filterName="maxUpdated" name="maxUpdated" id="maxUpdated" data-options="required:false,editable:false,onHidePanel:utils.dateFilter.setMinDateValue" style="width: 124px;" /></td>
				</tr>
				<tr>
					<td align="right">SCO申请单号：</td>
					<td><input class="easyui-validatebox" filterName="applicationCode" type="text" name="applicationCode" id="applicationCode" data-options="required:false,editable:false" style="width: 120px;" /></td>
					<td align="right">OA审批状态：</td>
					<td>
						<input class="easyui-combobox filterSelect" filterName="applicationStatus" id="applicationStatus" style="width: 124px;" 
								data-options="
									valueField:'id',
									textField:'text',
									panelHeight:'auto',
									editable:false,
								    url:'businessComBox_merchandiseApplicationStatus_5.html'
								    ">
						</input>
					</td>
					<td align="right">OA审批通过日期：</td>
					<td><input class="easyui-datebox filterInput" filterName="minApproveDate" name="minApproveDate" id="minApproveDate" data-options="required:false,editable:false,onHidePanel:utils.dateFilter.setMaxDateValue" style="width: 124px;" /> </td>
					<td align="right">至：</td>
					<td><input class="easyui-datebox filterInput" filterName="maxApproveDate" name="maxApproveDate" id="maxApproveDate" data-options="required:false,editable:false,onHidePanel:utils.dateFilter.setMinDateValue" style="width: 124px;" /></td>
				</tr>
				<tr>
					<td align="right">商品定性角色：</td>
					<td>
						<input class="easyui-combobox filterSelect" id="merchandiseDxRoleCode" name="merchandiseDxRoleCode" filterName="merchandiseDxRoleCode" style="width: 124px;"  
								data-options="
								valueField:'id',
								textField:'text',
								editable:false,
								url:'merchandiseRole_listQualitative_5.html'">
						</input>
					</td>
					<td align="right">商品定量角色：</td>
					<td>
						<input class="easyui-combobox filterSelect" id="merchandiseDlRoleCode" name="merchandiseDlRoleCode" filterName="merchandiseDlRoleCode" style="width: 124px;"  
								data-options="
								valueField:'id',
								textField:'text',
								editable:false,
								url:'merchandiseRole_listQuantify_5.html'">
						</input>
					</td>
					<td align="right">
						<input id="lastUpdated" name="lastUpdated" type="checkbox" />
					</td>
					<td colspan="3">只搜索商品修改时间最晚的核算表</td>
				</tr>
				<tr>
					<td colspan="8">
						<a id="search" onclick="totalCostAnalogyAnalysisFn.search();" plain="true" class="easyui-linkbutton" data-options="iconCls:'find'"> 搜索 </a>
						<a id="clear" onclick="totalCostAnalogyAnalysisFn.clearFilter();" plain="true" class="easyui-linkbutton" data-options="iconCls:'clear'"> 清空查询 </a>
						<a id="costcontrast" onclick="totalCostAnalogyAnalysisFn.costContrast();" plain="true" class="easyui-linkbutton" data-options="iconCls:'powertree'">成本对比 </a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
    <table  id="totalCostAnalogyAnalysis_grid"
			fit="true"
			iconCls= "icon-save"
			pagination = "true"
			pagePosition = 'bottom'
			pageSize = "20"
			pageList = "[ 10, 20, 30, 40 ]"
			toolbar="#totalCostAnalogyAnalysis_toolbar"
			url='totalCostAnalogyAnalysis_listTotalCostAnalogyAnalysis_2.html'
			data-options="rownumbers:true">
		<thead>
			<tr>
				<th halign="center" data-options="field:'merchandiseCode',width:40,checkbox:true"></th>
				<th data-options="field:'accountingCode',width:120,sortable:true,formatter:function(value,row){
																				 if(value != null){
																				 	return '<a href=javascript:totalCostAnalogyAnalysisFn.showLoad('+value+')>' + value + '</a>'
																				 }
																				}">核算表/投料表编号</th>
				<th data-options="field:'intentionCode',width:140,sortable:false,formatter:function(value,row){
																				 if(row.merchandiseCode == null) {
																					return value;
																				 }else {
																					return row.merchandiseCode;
																				 }
																				}">商品/意向品编号</th>
				<th data-options="field:'intentionName',width:200,sortable:false,formatter:function(value,row){
																				 if(row.merchandiseName == null) {
																					return value;
																				 }else {
																					return row.merchandiseName;
																				 }
																				}">商品/意向品名称</th>
				<th data-options="field:'intentionSupplierCode',width:140,sortable:false,formatter:function(value,row){
																				 if(row.supplierCode == null) {
																					return value;
																				 }else {
																					return row.supplierCode;
																				 }
																				}">供应商/意向供应商编号</th>
				<th data-options="field:'intentionSupplierName',width:230,sortable:false,formatter:function(value,row){
																				 if(row.supplierName == null) {
																					return value;
																				 }else {
																					return row.supplierName;
																				 }
																				}">供应商/意向供应商名称</th>
				<th data-options="field:'merchandiseDxRoleName',width:100,sortable:false">商品定性角色</th>
				<th data-options="field:'merchandiseDlRoleName',width:100,sortable:false">商品定量角色</th>
				<th data-options="field:'intentionPurchaseDepartments',width:100,sortable:false,formatter:function(value,row){
																				 if(row.merchandiseCode == null) {
																					return value;
																				 }else {
																					return row.purchaseDepartments;
																				 }
																				}">采购部门</th>
				<th data-options="field:'rationed',width:100,sortable:false,sortable:false,formatter:function(value,row){
																				 if(row.merchandiseCode == null) {
																					return value;
																				 }else {
																					return row.netWeight;
																				 }
																				}">是否定量装</th>
				<th data-options="field:'purchaseType',width:100,sortable:false">采购类型</th>
				<th data-options="field:'saleType',width:100,sortable:false,formatter:function(value,row){
																				 if(row.merchandiseCode == null) {
																					return value;
																				 }else {
																					return row.storageForm;
																				 }
																				}">销售方式</th>
				<th data-options="field:'centreTypeName',width:100,sortable:false">中分类</th>
				<th data-options="field:'smallTypeName',width:100,sortable:false,formatter:function(value,row){
																				 if(row.intentionSmallTypeCode != null && row.intentionSmallTypeCode == 'ELSE') {
																					return row.elseTypeName;
																				 }else {
																					return value;
																				 }
																				}">小分类</th>
				<th data-options="field:'detailTypeName',width:100,sortable:false">明细类</th>
				<th data-options="field:'fineTypeName',width:100,sortable:false">细分类</th>
				<th data-options="field:'accountingScanPath',width:100,sortable:true,formatter:function(value,row){
																				if(row.accountingCode != null){
																					if(value == null) {
																						return '无';
																					}else {
																						var accountingCode = row.accountingCode;
																						return '<a href=javascript:totalCostAnalogyAnalysisFn.downloadScan(\'' + row.accountingCode + '\',\'' + row.intentionCode + '\',\'' + row.intentionSupplierCode + '\',\'' + row.merchandiseCode + '\',\'' + row.supplierCode + '\',\'accounting\')>有</a>';
																					}
																				}
																				}">扫描版核算表</th>
				<th data-options="field:'ingredientScanPath',width:100,sortable:true,formatter:function(value,row){
																 				if(row.accountingCode != null){
																 					if(value == null) {
																						return '无';
																					}else {
																						var accountingCode = row.accountingCode;
																						return '<a href=javascript:totalCostAnalogyAnalysisFn.downloadScan(\'' + row.accountingCode + '\',\'' + row.intentionCode + '\',\'' + row.intentionSupplierCode + '\',\'' + row.merchandiseCode + '\',\'' + row.supplierCode + '\',\'ingredient\')>有</a>';
																					}
																 				}
																				}">扫描版投料表</th>
				<th data-options="field:'applicationCode',width:120,sortable:true">SCO申请单号</th>
				<th data-options="field:'applicationStatusValue',width:100,sortable:false">OA审批状态</th>
				<th data-options="field:'oaApproveDate',width:140,sortable:true">OA审批通过日期</th>
				<th data-options="field:'quotedDate',width:140,sortable:true">报价日期</th>
				<th data-options="field:'updated',width:140,sortable:true">核算表/投料表修改日期</th>
				<th data-options="field:'updateby',width:100,sortable:false">操作人</th>
			</tr>
		</thead>
	</table>
</body>
</html>
</#compress>