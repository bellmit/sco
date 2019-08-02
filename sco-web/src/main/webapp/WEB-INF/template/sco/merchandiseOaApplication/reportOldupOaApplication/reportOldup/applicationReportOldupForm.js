var oldPriceEditIndex = [];
var nowPriceEditIndex = [];
var sameEditIndex = [];
var sellEditIndex = [];
var materialEditIndex = [];
var centreTypeFalg = [];
var smallTypeFalg = [];
var detailTypeFalg = [];
var fineTypeFalg = [];
$(document).ready(function(){
	$('[id^=centreTypeCode]').combobox({
		url:'masterDataType_listCentreType_5.html',
		onChange : function() {
			var number = reportOldupFormFn.getObjectNumber(this);
			reportOldupFormFn.reloadData("centreTypeCode", "smallTypeCode", "SmallType", number);
		},
		onLoadSuccess:function(){
			var number = reportOldupFormFn.getObjectNumber(this);
			if (centreTypeFalg[number] == undefined) {
				$('#centreTypeCode'+number).combobox('setValue',$('#valueCentreTypeCode'+number).val());
				centreTypeFalg[number] = 'yes';
			}
		}
	});
	$('[id^=smallTypeCode]').combobox({
		onChange : function() {
			var number = reportOldupFormFn.getObjectNumber(this);
			reportOldupFormFn.reloadData("smallTypeCode", "detailTypeCode", "DetailType", number);
		},
		onLoadSuccess:function(){
			var number = reportOldupFormFn.getObjectNumber(this);
			if (smallTypeFalg[number] == undefined) {
				$('#smallTypeCode'+number).combobox('setValue',$('#valueSmallTypeCode'+number).val());
				smallTypeFalg[number] = 'yes';
			}
		}
	});
	$('[id^=detailTypeCode]').combobox({
		onChange : function() {
			var number = reportOldupFormFn.getObjectNumber(this);
			reportOldupFormFn.reloadData("detailTypeCode", "fineTypeCode", "FineType", number);
		},
		onLoadSuccess:function(){
			var number = reportOldupFormFn.getObjectNumber(this);
			if (detailTypeFalg[number] == undefined) {
				$('#detailTypeCode'+number).combobox('setValue',$('#valueDetailTypeCode'+number).val());
				detailTypeFalg[number] = 'yes';
			}
		}
	});
	$('[id^=fineTypeCode]').combobox({
		onLoadSuccess:function(){
			var number = reportOldupFormFn.getObjectNumber(this);
			if (fineTypeFalg[number] == undefined) {
				$('#fineTypeCode'+number).combobox('setValue',$('#valueFineTypeCode'+number).val());
				fineTypeFalg[number] = 'yes';
			}
			
		}
	});
});

var reportOldupFormFn = {
	// 提交新增或修改表单
	submitReportForm : function() {
		var applicationCode = $("input#applicationCode").val();
		var intentionAndSupplierCodes = $("input#intentionAndSupplierCodes").val();
		var intentionList=intentionAndSupplierCodes.split(",");
		
		var url="reportOldup_insertApplicationReportOldup_2.html?applicationCode=" + applicationCode
				+ "&intentionAndSupplierCodes=" + intentionAndSupplierCodes;			 
		 	var oldPriceRows = new Array();
			var nowPriceRows = new Array();
			var sameRows = new Array();
			var sellRows = new Array();
			var upDownRows = new Array();
			var materialRows = new Array();
			for(var i=0;i<intentionList.length;i++){
				//将编辑的下边存入数组
				oldPriceEditIndex [i]= undefined;
				nowPriceEditIndex [i]= undefined;
				sameEditIndex [i]    = undefined;
				sellEditIndex [i]    = undefined;
				
				var oldPriceGrid='#merchandisePriceOld_Grid'+i;
				var nowPriceGrid='#merchandisePriceNow_Grid'+i;
				var sameGrid='#merchandiseSame_Grid'+i;
				var sellGrid='#merchandiseSell_Grid'+i;
				var upDownGrid='#upDown_Grid'+i;
				var materialGrid='#merchandiseMaterial_Grid'+i;
				
				// 结束历史价格grid编辑
				if (reportOldupFormFn.endEditPriceOld(i)) {
					$(oldPriceGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// 结束本次价格grid编辑
				if (reportOldupFormFn.endEditPriceNow(i)) {
					$(nowPriceGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// 结束同类商品grid编辑
				if (reportOldupFormFn.endEditSame(i)) {
					$(sameGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// 结束销售grid编辑
				if (reportOldupFormFn.endEditSell(i)) {
					$(sellGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// 结束原料grid编辑
				if (reportOldupFormFn.endEditMaterial(i)) {
					$(materialGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				//判断商品价格模块是否已经填写
				if($(oldPriceGrid).datagrid('getRows').length<1){
					 $.messager.alert('提示', '<center>商品'+(i+1)+'商品历史价格模块不能为空！</center>');
					 return;   
				}
				//判断商品价格模块是否已经填写
				if($(nowPriceGrid).datagrid('getRows').length<1){
					$.messager.alert('提示', '<center>商品'+(i+1)+'商品本次价格模块不能为空！</center>');
					return;   
				}
				
				var selectedRadioValue=$("input[name='samePart"+i+"']:checked").val();
				if(selectedRadioValue=="1"){
					//说明选择了"有"单选框
					if($(sameGrid).datagrid('getRows').length<1){
						$.messager.alert('提示', '<center>商品'+(i+1)+'同类商品市场零售价模块不能为空！</center>');
						 return; 
					}else{
						sameRows[i]=JSON.stringify($(sameGrid).datagrid('getRows'));
					}
				}else{
					sameRows[i]='[]';
				}

				//判断商品销售预计模块是否已经填写
				if($(sellGrid).datagrid('getRows').length<1){
					 $.messager.alert('提示', '<center>商品'+(i+1)+'商品销售预计模块不能为空！</center>');
					 return;   
				}
				
				reportOldupFormFn.setPropertiesName(i);
				
				 oldPriceRows[i] =JSON.stringify($(oldPriceGrid).datagrid('getRows'));
				 nowPriceRows[i] = JSON.stringify($(nowPriceGrid).datagrid('getRows'));
				 sellRows[i] = JSON.stringify($(sellGrid).datagrid('getRows'));
				 upDownRows[i] = JSON.stringify($(upDownGrid).datagrid('getRows'));
				 materialRows[i]=JSON.stringify($(materialGrid).datagrid('getRows'));
			}
			
			//对form表单进行验证
			if(!$('#reportOldup_form').form('validate')){
				return;
			}
			
			$("#saveForm").linkbutton("disable");
			$("#reportOldup_form").form('submit', {
				url : url,
				onSubmit : function(param) {
					param.oldPriceRowsList = oldPriceRows;
					param.nowPriceRowsList = nowPriceRows;
					param.sameRowsList = sameRows;
					param.sellRowsList = sellRows;
					param.upDownRowsList = upDownRows;
					param.materialRowsList = materialRows;
				},
				success : function(data) {
					var json = $.parseJSON(data);
					if (json.success) {
						for (var i in json.rows) {
							$("#reportCode"+i).val(json.rows[i].reportCode);
						}
						parent.messagerShow({
							title : '操作成功!',
							msg : json.msg
						});
					}else{
						parent.messagerShow({
							title : '操作失败!',
							msg : json.msg
						});
					}
					$("#saveForm").linkbutton("enable");
					//保存之后，重新加载数据，将利率显示出来
					for(var k=0;k<intentionList.length;k++){
						$('#merchandisePriceOld_Grid'+k).datagrid('reload');
						$('#merchandisePriceNow_Grid'+k).datagrid('reload');
						$('#merchandisePriceCompare_Grid'+k).datagrid('reload');
					}
					
				}
			});
		 
		/*utils.form("reportOldup_form").submit(url, null, function() {
		 });*/
	},
	// 提交新增或修改表草稿
	submitReportCGForm : function() {
		var applicationCode = $("input#applicationCode").val();
		var intentionAndSupplierCodes = $("input#intentionAndSupplierCodes").val();
		var intentionList=intentionAndSupplierCodes.split(",");
		
		var url="reportOldup_insertApplicationReportOldupCG_2.html?applicationCode=" + applicationCode
				+ "&intentionAndSupplierCodes=" + intentionAndSupplierCodes;			 
		 	var oldPriceRows = new Array();
			var nowPriceRows = new Array();
			var sameRows = new Array();
			var sellRows = new Array();
			var upDownRows = new Array();
			var materialRows = new Array();
			for(var i=0;i<intentionList.length;i++){
				//将编辑的下边存入数组
				oldPriceEditIndex [i]= undefined;
				nowPriceEditIndex [i]= undefined;
				sameEditIndex [i]    = undefined;
				sellEditIndex [i]    = undefined;
				
				var oldPriceGrid='#merchandisePriceOld_Grid'+i;
				var nowPriceGrid='#merchandisePriceNow_Grid'+i;
				var sameGrid='#merchandiseSame_Grid'+i;
				var sellGrid='#merchandiseSell_Grid'+i;
				var upDownGrid='#upDown_Grid'+i;
				var materialGrid='#merchandiseMaterial_Grid'+i;
				
				// 结束历史价格grid编辑
				if (reportOldupFormFn.endEditPriceOld(i)) {
					$(oldPriceGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// 结束本次价格grid编辑
				if (reportOldupFormFn.endEditPriceNow(i)) {
					$(nowPriceGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// 结束同类商品grid编辑
				if (reportOldupFormFn.endEditSame(i)) {
					$(sameGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// 结束销售grid编辑
				if (reportOldupFormFn.endEditSell(i)) {
					$(sellGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// 结束原料grid编辑
				if (reportOldupFormFn.endEditMaterial(i)) {
					$(materialGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				//判断商品价格模块是否已经填写
				/*if($(oldPriceGrid).datagrid('getRows').length<1){
					 $.messager.alert('提示', '<center>商品'+(i+1)+'商品历史价格模块不能为空！</center>');
					 return;   
				}*/
				//判断商品价格模块是否已经填写
				/*if($(nowPriceGrid).datagrid('getRows').length<1){
					$.messager.alert('提示', '<center>商品'+(i+1)+'商品本次价格模块不能为空！</center>');
					return;   
				}*/
				
				var selectedRadioValue=$("input[name='samePart"+i+"']:checked").val();
				if(selectedRadioValue=="1"){
					//说明选择了"有"单选框
					if($(sameGrid).datagrid('getRows').length<1){
						/*$.messager.alert('提示', '<center>商品'+(i+1)+'同类商品市场零售价模块不能为空！</center>');
						 return; */
					}else{
						sameRows[i]=JSON.stringify($(sameGrid).datagrid('getRows'));
					}
				}else{
					sameRows[i]='[]';
				}

				//判断商品销售预计模块是否已经填写
				/*if($(sellGrid).datagrid('getRows').length<1){
					 $.messager.alert('提示', '<center>商品'+(i+1)+'商品销售预计模块不能为空！</center>');
					 return;   
				}*/
				
				reportOldupFormFn.setPropertiesName(i);
				//if($(oldPriceGrid).datagrid('getRows').length>0){
				 oldPriceRows[i] =JSON.stringify($(oldPriceGrid).datagrid('getRows'));
			//	}
			//	if($(nowPriceGrid).datagrid('getRows').length>0){
				 nowPriceRows[i] = JSON.stringify($(nowPriceGrid).datagrid('getRows'));
			//	}
			//	if($(sellGrid).datagrid('getRows').length>0){
				 sellRows[i] = JSON.stringify($(sellGrid).datagrid('getRows'));
			//	}
			//	if($(upDownGrid).datagrid('getRows').length>0){
				 upDownRows[i] = JSON.stringify($(upDownGrid).datagrid('getRows'));
			//	}
			//	if($(materialGrid).datagrid('getRows').length>0){
				 materialRows[i]=JSON.stringify($(materialGrid).datagrid('getRows'));
			//	}
			}
			
			//对form表单进行验证
			/*if(!$('#reportOldup_form').form('validate')){
				return;
			}*/
			
			$("#saveForm").linkbutton("disable");
			$("#reportOldup_form").form('submit', {
				url : url,
				onSubmit : function(param) {
					param.oldPriceRowsList = oldPriceRows;
					param.nowPriceRowsList = nowPriceRows;
					param.sameRowsList = sameRows;
					param.sellRowsList = sellRows;
					param.upDownRowsList = upDownRows;
					param.materialRowsList = materialRows;
				},
				success : function(data) {
					var json = $.parseJSON(data);
					if (json.success) {
						for (var i in json.rows) {
							$("#reportCode"+i).val(json.rows[i].reportCode);
						}
						parent.messagerShow({
							title : '操作成功!',
							msg : json.msg
						});
					}else{
						parent.messagerShow({
							title : '操作失败!',
							msg : json.msg
						});
					}
					$("#saveForm").linkbutton("enable");
					//保存之后，重新加载数据，将利率显示出来
					for(var k=0;k<intentionList.length;k++){
						$('#merchandisePriceOld_Grid'+k).datagrid('reload');
						$('#merchandisePriceNow_Grid'+k).datagrid('reload');
						$('#merchandisePriceCompare_Grid'+k).datagrid('reload');
					}
					
				}
			});
		 
		/*utils.form("reportOldup_form").submit(url, null, function() {
		 });*/
	},
	/**
	 * 设置下拉框选中的名称
	 */
	setPropertiesName:function(index) {
		var target = ['dxRoleCode', 'dlRoleCode', 'centreTypeCode',
		              'smallTypeCode', 'detailTypeCode', 'fineTypeCode'
		             ];
		for (var i in target) {
//				console.info("#" + target[i] + index);
			var text = $("#" + target[i] + index).combobox('getText');
			$("#" + index + target[i]).val(text);
		}
	},
	
	closeForm: function() {
		parent.pubTab.closeCurrTab();
	},
	
	/* 历史价格的js */
	endEditPriceOld : function(gridId) {
		if (oldPriceEditIndex[gridId] == undefined) {
			return true
		}
		if ($('#merchandisePriceOld_Grid'+gridId).datagrid('validateRow', oldPriceEditIndex[gridId])) {
			$('#merchandisePriceOld_Grid'+gridId).datagrid('endEdit', oldPriceEditIndex[gridId]);
			oldPriceEditIndex[gridId] = undefined;
			return true;
		} else {
			return false;
		}
	},
	onClickRowPriceOld : function(gridId,index) {
		if (oldPriceEditIndex[gridId] != index) {
			if (reportOldupFormFn.endEditPriceOld(gridId)) {
				$('#merchandisePriceOld_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
				oldPriceEditIndex[gridId] = index;
			} else {
				$('#merchandisePriceOld_Grid'+gridId).datagrid('selectRow', oldPriceEditIndex[gridId]);
			}
		}
	},
	appendPriceOld : function(gridId) {
		var date = $('#merchandisePriceOld_Grid'+gridId).datagrid('getData');
		if (reportOldupFormFn.endEditPriceOld(gridId)) {
			$('#merchandisePriceOld_Grid'+gridId).datagrid('appendRow', {
				status : 'P'
			});
			oldPriceEditIndex[gridId] = $('#merchandisePriceOld_Grid'+gridId).datagrid('getRows').length - 1;
			$('#merchandisePriceOld_Grid'+gridId).datagrid('selectRow', oldPriceEditIndex[gridId]).datagrid('beginEdit', oldPriceEditIndex[gridId]);
		}

	},
	removePriceOld : function(gridId) {
		if (oldPriceEditIndex[gridId] == undefined) {
			return
		}
		$('#merchandisePriceOld_Grid'+gridId).datagrid('cancelEdit', oldPriceEditIndex[gridId]).datagrid('deleteRow', oldPriceEditIndex[gridId]);
		oldPriceEditIndex[gridId] = undefined;
	},
	
	
	/* 本次价格的js */
	endEditPriceNow : function(gridId) {
		if (nowPriceEditIndex[gridId] == undefined) {
			return true
		}
		if ($('#merchandisePriceNow_Grid'+gridId).datagrid('validateRow', nowPriceEditIndex[gridId])) {
			$('#merchandisePriceNow_Grid'+gridId).datagrid('endEdit', nowPriceEditIndex[gridId]);
			nowPriceEditIndex[gridId] = undefined;
			return true;
		} else {
			return false;
		}
	},
	onClickRowPriceNow : function(gridId,index) {
		if (nowPriceEditIndex[gridId] != index) {
			if (reportOldupFormFn.endEditPriceNow(gridId)) {
				$('#merchandisePriceNow_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
				nowPriceEditIndex[gridId] = index;
			} else {
				$('#merchandisePriceNow_Grid'+gridId).datagrid('selectRow', nowPriceEditIndex[gridId]);
			}
		}
	},
	appendPriceNow : function(gridId) {
		var date = $('#merchandisePriceNow_Grid'+gridId).datagrid('getData');
		if (reportOldupFormFn.endEditPriceNow(gridId)) {
			$('#merchandisePriceNow_Grid'+gridId).datagrid('appendRow', {
				status : 'P'
			});
			nowPriceEditIndex[gridId] = $('#merchandisePriceNow_Grid'+gridId).datagrid('getRows').length - 1;
			$('#merchandisePriceNow_Grid'+gridId).datagrid('selectRow', nowPriceEditIndex[gridId]).datagrid('beginEdit', nowPriceEditIndex[gridId]);
		}

	},
	removePriceNow : function(gridId) {
		if (nowPriceEditIndex[gridId] == undefined) {
			return
		}
		$('#merchandisePriceNow_Grid'+gridId).datagrid('cancelEdit', nowPriceEditIndex[gridId]).datagrid('deleteRow', nowPriceEditIndex[gridId]);
		nowPriceEditIndex[gridId] = undefined;
	},
	
	/* 同类商品的js */
	endEditSame : function(gridId) {
		if (sameEditIndex[gridId] == undefined) {
			return true
		}
		if ($('#merchandiseSame_Grid'+gridId).datagrid('validateRow', sameEditIndex[gridId])) {
			$('#merchandiseSame_Grid'+gridId).datagrid('endEdit', sameEditIndex[gridId]);
			sameEditIndex[gridId] = undefined;
			return true;
		} else {
			return false;
		}
	},
	onClickRowSame : function(gridId,index) {
		if (sameEditIndex[gridId] != index) {
			if (reportOldupFormFn.endEditSame(gridId)) {
				$('#merchandiseSame_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
				sameEditIndex[gridId] = index;
			} else {
				$('#merchandiseSame_Grid'+gridId).datagrid('selectRow', sameEditIndex[gridId]);
			}
		}
	},
	appendSame : function(gridId) {
		var date = $('#merchandiseSame_Grid'+gridId).datagrid('getData');
		if (reportOldupFormFn.endEditSame(gridId)) {
			$('#merchandiseSame_Grid'+gridId).datagrid('appendRow', {
				status : 'P'
			});
			sameEditIndex[gridId] = $('#merchandiseSame_Grid'+gridId).datagrid('getRows').length - 1;
			$('#merchandiseSame_Grid'+gridId).datagrid('selectRow', sameEditIndex[gridId]).datagrid('beginEdit', sameEditIndex[gridId]);
		}

	},
	removeSame : function(gridId) {
		if (sameEditIndex[gridId] == undefined) {
			return
		}
		$('#merchandiseSame_Grid'+gridId).datagrid('cancelEdit', sameEditIndex[gridId]).datagrid('deleteRow', sameEditIndex[gridId]);
		sameEditIndex[gridId] = undefined;
	},
	
	/* 销售的js */
	endEditSell : function(gridId) {
		if (sellEditIndex[gridId] == undefined) {
			return true
		}
		if ($('#merchandiseSell_Grid'+gridId).datagrid('validateRow', sellEditIndex[gridId])) {
			$('#merchandiseSell_Grid'+gridId).datagrid('endEdit', sellEditIndex[gridId]);
			sellEditIndex[gridId] = undefined;
			return true;
		} else {
			return false;
		}
	},
	onClickRowSell : function(gridId,index) {
		if (sellEditIndex[gridId] != index) {
			if (reportOldupFormFn.endEditSell(gridId)) {
				$('#merchandiseSell_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
				sellEditIndex[gridId] = index;
			} else {
				$('#merchandiseSell_Grid'+gridId).datagrid('selectRow', sellEditIndex[gridId]);
			}
		}
	},
	appendSell : function(gridId) {
		var date = $('#merchandiseSell_Grid'+gridId).datagrid('getData');
		if (reportOldupFormFn.endEditSell(gridId)) {
			$('#merchandiseSell_Grid'+gridId).datagrid('appendRow', {
				status : 'P'
			});
			sellEditIndex[gridId] = $('#merchandiseSell_Grid'+gridId).datagrid('getRows').length - 1;
			$('#merchandiseSell_Grid'+gridId).datagrid('selectRow', sellEditIndex[gridId]).datagrid('beginEdit', sellEditIndex[gridId]);
		}

	},
	removeSell : function(gridId) {
		if (sellEditIndex[gridId] == undefined) {
			return
		}
		$('#merchandiseSell_Grid'+gridId).datagrid('cancelEdit', sellEditIndex[gridId]).datagrid('deleteRow', sellEditIndex[gridId]);
		sellEditIndex[gridId] = undefined;
	},
	
	// 商品原料
	endEditMaterial : function(gridId) {
		if (materialEditIndex[gridId] == undefined) {
			return true
		}
		if ($('#merchandiseMaterial_Grid'+gridId).datagrid('validateRow', materialEditIndex[gridId])) {
			$('#merchandiseMaterial_Grid'+gridId).datagrid('endEdit', materialEditIndex[gridId]);
			materialEditIndex[gridId] = undefined;
			return true;
		} else {
			return false;
		}
	},
	appendMaterial : function(gridId) {
		var date = $('#merchandiseMaterial_Grid'+gridId).datagrid('getData');
		if (reportOldupFormFn.endEditMaterial(gridId)) {
			$('#merchandiseMaterial_Grid'+gridId).datagrid('appendRow', {
				status : 'P'
			});
			materialEditIndex[gridId] = $('#merchandiseMaterial_Grid'+gridId).datagrid('getRows').length - 1;
			$('#merchandiseMaterial_Grid'+gridId).datagrid('selectRow', materialEditIndex[gridId]).datagrid('beginEdit', materialEditIndex[gridId]);
		}

	},
	removeMaterial : function(gridId) {
		if (materialEditIndex[gridId] == undefined) {
			return
		}
		$('#merchandiseMaterial_Grid'+gridId).datagrid('cancelEdit', materialEditIndex[gridId]).datagrid('deleteRow', materialEditIndex[gridId]);
		materialEditIndex[gridId] = undefined;
	},
	//商品原料
	onClickRowMaterial : function(gridId,index) {
		if (materialEditIndex[gridId] != index) {
			if (reportOldupFormFn.endEditMaterial(gridId)) {
				$('#merchandiseMaterial_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
				materialEditIndex[gridId] = index;
			} else {
				$('#merchandiseMaterial_Grid'+gridId).datagrid('selectRow', materialEditIndex[gridId]);
			}
		}
	},
	
	//判断是否有同类商品
	changeSameRaido:function(index,checkValue){
		if("1"==checkValue){
			//点击"有"
			$('#same_div'+index).show();
			$('#notSame_div'+index).hide();
			$("#merchandiseSame_Grid"+index).datagrid({fitColumns:true});
			$("#merchandiseSame_Grid"+index).datagrid('load');
		}else{
			//点击"无"
			//$('same_div0').style.display="none";
			$('#same_div'+index).hide();
			$('#notSame_div'+index).show();
			$("#merchandiseSame_Grid"+index).datagrid('load');
		}
	},
	
	
	// 重新加载
	reloadData : function(thisId, targetId, method, number) {
		reportOldupFormFn.clearSelectedData(targetId+number);
		// 当前框的值
		var value = $("#" + thisId+number).combobox("getValue");
		if (!value) {
			value = 'null';
		}
		$("#" + targetId+number).combobox('reload', "masterDataType_list" + method + "_5.html?" + thisId + "=" + value);
	},

	// 清空下一个连动框已选择的值
	clearSelectedData : function(targetId) {
		$("#" + targetId).combobox("setValue", "");
		$("#" + targetId).combobox("clear");
	},
	
	getObjectNumber:function(obj){
		var _id = $(obj).attr('id');
		var number;
		number = _id.substr(_id.length - 1, _id.length);
		return number;
	},
	
	//格式化百分比
	formatterPercent:function(val) {
		if (val == '' || val == undefined) return '';
		val += ""
		if (val.indexOf('%') > -1){
			val = val.substring(0, val.length - 1);
		} 
		if (val == '0' || val == '') {
			return '0.00%';
		} else {
			val = (new Number(val)).toFixed(2);
			return val + '%';
		}
	},
	
	//格式化预计销售门店数
	formatterSellStoreCount:function(val){
		return moneyFormatter(val, 0);
	}
};