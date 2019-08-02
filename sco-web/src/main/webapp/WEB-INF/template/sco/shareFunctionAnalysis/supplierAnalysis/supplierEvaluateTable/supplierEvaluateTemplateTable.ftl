<#compress>
<!DOCTYPE html>  
<html>
<head>  
    <meta charset="UTF-8">  
    <#include "inc/easyui.ftl" />
    <link rel="stylesheet" href="css/table.min.css" type="text/css" />
    <script type="text/javascript" >
		<#include "sco/shareFunctionAnalysis/supplierAnalysis/supplierEvaluateTable/supplierEvaluateTemplateTable.js" />
    </script>
</head>
<body>
	<input type="hidden" name="evaluateTableCode" id="evaluateTableCode" value="${evaluateTableCode}" />
	<!-- 供应商考评表表详情-商品 --> 
	<div id="supplierEvaluateTemplate_toolbar">
		<form id="signedQty_search">
			<a onclick="parent.pubTab.closeCurrTab();" plain="true" class="easyui-linkbutton" data-options="iconCls:'close'">
				关闭
			</a>
			<a onclick="supplierEvaluateTemplate.export2Excel()" plain="true" class="easyui-linkbutton" data-options="iconCls:'excel'"> 导出Execl </a>
			<br/>
			<table style="width:800px">
				<tr>
					<td style="text-align:left;width:80px">
						考评表名称:<input id="templateName" style="width:120px;" value="<#if sesList?? && (sesList?size>0)>${sesList[0].templateName}</#if>" class="easyui-validatebox" required="true" />
					</td>
					<td style="text-align:left;width:144px">
						考&nbsp;核&nbsp;周&nbsp;期:<input class="easyui-datebox list-input" value="<#if sesList?? && (sesList?size>0)>${(sesList[0].evaluateStartDate?string('yyyy-MM-dd'))!}</#if>" id="minCreated" data-options="disabled:true,required:true,editable:false,onHidePanel:utils.dateFilter.setMaxDateValue" style="width:124px;"/>
					</td>
					<td style="text-align:left;width:144px">
						&nbsp;&nbsp;&nbsp;至:<input class="easyui-datebox list-input" value="<#if sesList?? && (sesList?size>0)>${(sesList[0].evaluateEndDate?string('yyyy-MM-dd'))!}</#if>" id="maxCreated" data-options="disabled:true,required:true,editable:false,onHidePanel:utils.dateFilter.setMinDateValue" style="width:124px;"/>
					</td>
				</tr>
				<tr>
					<td style="text-align:left;width:80px">
						供应商编号:<input id="supplierCode" style="width:120px;" value="<#if sesList?? && (sesList?size>0)>${sesList[0].supplierCode}</#if>" class="easyui-validatebox" required="true" />
					</td>
					<td style="text-align:left;width:160px">
						供应商名称:<input id="supplierName" style="width:120px;" value="<#if sesList?? && (sesList?size>0)>${sesList[0].supplierName}</#if>" class="easyui-validatebox" required="true" />
					</td>
					<td style="text-align:left;width:160px">
						总分(总权重分):<input id="evaluateFullScore" style="width:120px;" value="<#if sesList?? && (sesList?size>0)>${sesList[0].evaluateFullScore}</#if>" class="easyui-validatebox" required="true" />
					</td>
				</tr>
			</table><br/>
			<div>评分项:</div>
			<#list sesList as list>
					<table style="width:800px;border:1px solid grey">
						<tr>
							<td style="text-align:left">
								<table id="tab"style="margin-top:10px;margin-bottom:5px">
									<tr>
										<td style="text-align:right;width:80px">
											评分项名称:
										</td>
										<td style="width:120px;">
											<input id="evaluateItemName#{list_index}" value="${list.evaluateItemName}" style="width:120px;" class="easyui-validatebox" required="true" />
										</td>
										<td style="text-align:right;width:80px">
											权重(%):
										</td>
										<td style="width:120px;">
											<input id="weight#{list_index}" value="${list.weight}" style="width:120px;" class="easyui-validatebox" required="true" />
										</td>
										<td style="text-align:right;width:80px">
											权重分:
										</td>
										<td style="width:120px;">
											<input id="score#{list_index}" value="${list.score}" style="width:120px;" class="easyui-validatebox" required="true" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table>
									<tr>
										<td style="text-align:right;width:80px">
											分值:
										</td>
										<td style="width:120px;">
											<input id="evaluateItemScore#{list_index}" value="${list.evaluateItemScore}" style="width:120px;" class="easyui-validatebox" required="true" />
										</td>
										<#if list.templateType!='FL'>
											<td style="text-align:right;width:80px">
												打分部门:
											</td>
											<td>
												<input id="departments#{list_index}" value="${list.departments}" class="easyui-validatebox" style="width: 120px;"required="true" />
										    </td>
									    </#if>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table style="margin-top:10px;margin-bottom:10px">
									<tr>
										<td style="text-align:right;width:80px">
											评分依据:
										</td>
										<td>
											<textarea id="according#{list_index}" disabled="disabled" style="width:210px;height:100px;max-width:210px;max-height:100px" >${list.according}</textarea>
										</td>
										<td></td>
										<td style="text-align:right;width:80px">
											评分标准:
										</td>
										<td>
											<textarea id="standard#{list_index}" disabled="disabled" style="width:210px;height:100px;max-width:210px;max-height:100px" >${list.standard}</textarea>
										</td>
										<td></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
			</#list>
		</form>
	</div>
</body>
</html>
</#compress>