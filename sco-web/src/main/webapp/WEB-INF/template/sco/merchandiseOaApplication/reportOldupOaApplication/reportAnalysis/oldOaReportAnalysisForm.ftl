<#compress>
	<div style="padding: 5px;overflow: hidden;">
		<form id="oldSeaRpt_form" method="post">
			<table class="tableForm">
				<input type="hidden" id="selectedRt" />
				<tr>
					<td style="text-align:right;width:110px">报表类型：</td>
					<td>
						<span id="typeName"></span>
						<input class="easyui-validatebox" type="hidden" filterName="reportsTypeCode" id="typeCode" style="width:103px;" />
					</td>
				</tr>
				<tr>
					<td style="text-align:right">报表名称：</td>
					<td>
						<input class="easyui-validatebox" filterName="reportsName" id="reportsName" style="width:214px;" />
					</td>
				</tr>
				<tr>
					<td style="text-align:right">
						报表保存日期：
					</td>	
					<td colspan="3">
						<input class="easyui-datebox list-input" filterName="created" id="minSaveDate" data-options="editable:false,onHidePanel:utils.dateFilter.setMaxDateValue" style="width:100px;"/>
						至：
						<input class="easyui-datebox list-input" filterName="createdEnd" id="maxSaveDate" data-options="editable:false,onHidePanel:utils.dateFilter.setMinDateValue" style="width:100px;"/>
					</td>
				</tr>
			</table>
		</form>
		<div id="searchRpt_toolbar">
			<a onclick="oldOaReportAnalyFn.searchRpt();" plain="true" class="easyui-linkbutton" data-options="iconCls:'search'">
				搜索
			</a>
			<a onclick="oldOaReportAnalyFn.clearSearch();" plain="true" class="easyui-linkbutton" data-options="iconCls:'clear'">
				清空查询
			</a>
		</div>
		<table id="oldOalinkReportGrid" 
			iconCls= "icon-save"
			pagination = "true"
			singleSelect = "true"
			pagePosition = "bottom"
			style="height:270px" 
			pageSize = "20"
			url="reports_listReportsByParams_2.html"
			pageList = "[ 10, 20, 30, 40 ]"
			toolbar="#searchRpt_toolbar"
			data-options="rownumbers:true
		">  
			<thead>
				<tr>
					<th rowspan="2" data-options="field:'NULL',	checkbox:false, hidden:true,halign:'left'"></th>
					<th rowspan="2" data-options="field:'reportsTypeName',width:150,halign:'left'">
						报表类型
					</th>
						<th rowspan="2" data-options="field:'reportsName',width:130,halign:'left'">
						报表名称
					</th>
						<th rowspan="2" data-options="field:'createUserName',width:90,halign:'left'">
						保存人姓名
					</th>
					<th rowspan="2" data-options="field:'created',width:90,halign:'left'">
						保存日期
					</th>
				</tr>
			</thead>
		</table>
	</div>
</#compress>