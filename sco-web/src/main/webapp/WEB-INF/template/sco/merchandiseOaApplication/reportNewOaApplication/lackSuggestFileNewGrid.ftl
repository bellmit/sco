<#compress>

<div style="padding: 5px;overflow: hidden;">
<form id="lackFileNew_form" method="post">
<input type="hidden" name="applicationCode" id="applicationCode" value="${applicationCode}" />
<#list lackFileList as lackFileNew>    
<div title="商品${lackFileNew_index+1}" >
	<fieldset style="margin-top:10px;border:1px solid #CCC;" data-options="fit:true">
		<!--1.商品基础信息-->
		<table class="tableForm" >
			<tr>
				 <th align="right" >申请单号</th>
				<td>
					<input name="lackFileList[${lackFileNew_index}].applicationCode" readonly="readonly" value="${lackFileNew.applicationCode}" style="background:lightgray;" class="easyui-validatebox" />
				</td>
			    <th align="right" >意向品编号</th>
				<td>
					<input name="lackFileList[${lackFileNew_index}].merchandiseCode" readonly="readonly" value="${lackFileNew.merchandiseCode}" style="background:lightgray;" class="easyui-validatebox"  />
				</td>
				  <th align="right" >意向品名称</th>
				<td>
					<input name="lackFileList[${lackFileNew_index}].merchandiseName" readonly="readonly" value="${lackFileNew.merchandiseName}" style="background:lightgray;" class="easyui-validatebox"  />
				</td>
				 <th align="right" >供应商编号</th>
				<td>
					<input name="lackFileList[${lackFileNew_index}].supplierCode" readonly="readonly" value="${lackFileNew.supplierCode}" style="background:lightgray;" class="easyui-validatebox" />
				</td>
			<tr>	
			</tr>	
				<th align="right" >供应商名称</th>
				<td>
					<input name="lackFileList[${lackFileNew_index}].supplierName" readonly="readonly" value="${lackFileNew.supplierName}" style="background:lightgray;" class="easyui-validatebox"  />
				</td>
				 <th align="right" >缺少文件名称</th>
				<td>
					<input name="lackFileList[${lackFileNew_index}].lackFileName" readonly="readonly" value="${lackFileNew.lackFileName}" style="background:lightgray;" class="easyui-validatebox" />
				</td>
				 <th align="right" >缺少文件版本</th>
				<td>
					<input name="lackFileList[${lackFileNew_index}].lackFileVersions" readonly="readonly" value="${lackFileNew.lackFileVersions}" style="background:lightgray;" class="easyui-validatebox" />
				</td>
				<th align="right" >缺少文件原因</th>
				<td>
					<input name="lackFileList[${lackFileNew_index}].lackFileCause"  value="${lackFileNew.lackFileCause}"  class="easyui-validatebox"  data-options="required:true,validType:'length[1,333]'"/>
				</td>
			</tr>
		</table>
	 </fieldset>	
  </div>
</#list>
</form>
</div>
</#compress>