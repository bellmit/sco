<#compress>
	<div style="padding: 15px;overflow: hidden;">
		<form id="fastAdjustWlInfo_form" method="post">
			<table class="tableForm">
				<input type="hidden" name="applicationCode" id="faAppli" />
				<input type="hidden" name="intentionAndSupplierCodes" id="faIntSup" />
				<input type="hidden" name="intentionCode" id="faIntCode" />
				<input type="hidden" name="supplierCode" id="faSupp"/>
				<input type="hidden" name="oldRegion" value="${wlInfo.region}"/>
				<tr>
					<td style="text-align:center;width:95px"><font color="red">*</font>SAP物料号：</td>
					<td><input class="easyui-numberbox" name="accessorySAPCode" <#if ifEdit?exists>value="${wlInfo.accessorySAPCode}" disabled="disabled"</#if> style="width:151px;" maxlength="12" data-options="required:true" /></td>
				</tr>
				<tr>
					<td style="text-align:center;"><font color="red">*</font>SAP供应商号：</td>
					<td><input class="easyui-numberbox" name="supplierSAPCode" <#if ifEdit?exists>value="${wlInfo.supplierSAPCode}" disabled="disabled"</#if> style="width:151px;" maxlength="12" data-options="required:true" /></td>
				</tr>
				<tr>
					<td style="text-align:center;"><font color="red">*</font>进货地区：</td>
					<td>
						<input class="easyui-combobox" name="region" id="faWlInfoRegion"
							data-options="editable:false,valueField:'id',textField:'id',
								url:'masterDataType_listWarehouseOption_5.html',multiple:true,required:true,width:156,height:19,panelHeight:'200',
								onLoadSuccess : function() {
									if('${wlInfo.region}') {
										$(this).combobox('setValues', [${regionVal}]);
									}
								}">
					</td>
				</tr>
				<tr>
					<td style="text-align:center"><font color="red">*</font>合同价格：</td>
					<td>
						<input class="easyui-numberbox" data-options="min:0,precision:2,required:true" <#if wlInfo.sumPrice?exists>value="${wlInfo.sumPrice?c}"</#if> maxlength="12" name="sumPrice" style="width:152px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</#compress>