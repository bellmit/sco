var oldPriceEditIndex = [];
var nowPriceEditIndex = [];
var sameEditIndex = [];
var materialEditIndex = [];

var centreTypeFalg = [];
var smallTypeFalg = [];
var detailTypeFalg = [];
var fineTypeFalg = [];
$(document).ready(function(){
	$('[id^=centreTypeCode]').combobox({
		url:'masterDataType_listCentreType_5.html',
		onChange : function() {
			var number = reportAdjustpriceFormFn.getObjectNumber(this);
			reportAdjustpriceFormFn.reloadData("centreTypeCode", "smallTypeCode", "SmallType", number);
		},
		onLoadSuccess:function(){
			var number = reportAdjustpriceFormFn.getObjectNumber(this);
			if (centreTypeFalg[number] == undefined) {
				$('#centreTypeCode'+number).combobox('setValue',$('#valueCentreTypeCode'+number).val());
				centreTypeFalg[number] = 'yes';
			}
		}
	});
	$('[id^=smallTypeCode]').combobox({
		onChange : function() {
			var number = reportAdjustpriceFormFn.getObjectNumber(this);
			reportAdjustpriceFormFn.reloadData("smallTypeCode", "detailTypeCode", "DetailType", number);
		},
		onLoadSuccess:function(){
			var number = reportAdjustpriceFormFn.getObjectNumber(this);
			if (smallTypeFalg[number] == undefined) {
				$('#smallTypeCode'+number).combobox('setValue',$('#valueSmallTypeCode'+number).val());
				smallTypeFalg[number] = 'yes';
			}
		}
	});
	$('[id^=detailTypeCode]').combobox({
		onChange : function() {
			var number = reportAdjustpriceFormFn.getObjectNumber(this);
			reportAdjustpriceFormFn.reloadData("detailTypeCode", "fineTypeCode", "FineType", number);
		},
		onLoadSuccess:function(){
			var number = reportAdjustpriceFormFn.getObjectNumber(this);
			if (detailTypeFalg[number] == undefined) {
				$('#detailTypeCode'+number).combobox('setValue',$('#valueDetailTypeCode'+number).val());
				detailTypeFalg[number] = 'yes';
			}
		}
	});
	$('[id^=fineTypeCode]').combobox({
		onLoadSuccess:function(){
			var number = reportAdjustpriceFormFn.getObjectNumber(this);
			if (fineTypeFalg[number] == undefined) {
				$('#fineTypeCode'+number).combobox('setValue',$('#valueFineTypeCode'+number).val());
				fineTypeFalg[number] = 'yes';
			}
			
		}
	});
});

var reportAdjustpriceFormFn = {
		// ???????????????????????????
		submitReportForm : function() {
			var applicationCode = $("input#applicationCode").val();
			var intentionAndSupplierCodes = $("input#intentionAndSupplierCodes").val();
			var intentionList=intentionAndSupplierCodes.split(",");
			
			var url="reportAdjustprice_insertApplicationReportAdjustprice_2.html?applicationCode=" + applicationCode
					+ "&intentionAndSupplierCodes=" + intentionAndSupplierCodes;
			
			var oldPriceRows = new Array();
			var nowPriceRows = new Array();
			var sameRows = new Array();
			var materialRows = new Array();
			for(var i=0;i<intentionList.length;i++){
				//??????????????????????????????
				oldPriceEditIndex [i]= undefined;
				nowPriceEditIndex [i]= undefined;
				sameEditIndex [i]    = undefined;
				materialEditIndex [i]= undefined;
				
				var oldPriceGrid='#merchandisePriceOld_Grid'+i;
				var nowPriceGrid='#merchandisePriceNow_Grid'+i;
				var sameGrid='#merchandiseSame_Grid'+i;
				var materialGrid='#merchandiseMaterial_Grid'+i;
				
				// ??????????????????grid??????
				if (reportAdjustpriceFormFn.endEditPriceOld(i)) {
					$(oldPriceGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// ??????????????????grid??????
				if (reportAdjustpriceFormFn.endEditPriceNow(i)) {
					$(nowPriceGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// ??????????????????grid??????
				if (reportAdjustpriceFormFn.endEditSame(i)) {
					$(sameGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// ????????????grid??????
				if (reportAdjustpriceFormFn.endEditMaterial(i)) {
					$(materialGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				
				//??????????????????????????????????????????
				if($(oldPriceGrid).datagrid('getRows').length<1){
					 $.messager.alert('??????', '<center>??????'+(i+1)+'???????????????????????????????????????</center>');
					 return;   
				}
				//??????????????????????????????????????????
				if($(nowPriceGrid).datagrid('getRows').length<1){
					$.messager.alert('??????', '<center>??????'+(i+1)+'???????????????????????????????????????</center>');
					return;   
				}
				
				var selectedRadioValue=$("input[name='samePart"+i+"']:checked").val();
				if(selectedRadioValue=="1"){
					//???????????????"???"?????????
					if($(sameGrid).datagrid('getRows').length<1){
						$.messager.alert('??????', '<center>??????'+(i+1)+'????????????????????????????????????????????????</center>');
						 return; 
					}else{
						sameRows[i]=JSON.stringify($(sameGrid).datagrid('getRows'));
					}
				}else{
					sameRows[i]='[]';
				}

				//??????????????????????????????????????????
				/*if($(materialGrid).datagrid('getRows').length<1){
					 $.messager.alert('??????', '<center>??????'+(i+1)+'?????????????????????????????????</center>');
					 return;   
				}*/
				reportAdjustpriceFormFn.setPropertiesName(i);
				
				oldPriceRows[i]=JSON.stringify($(oldPriceGrid).datagrid('getRows'));
				nowPriceRows[i]=JSON.stringify($(nowPriceGrid).datagrid('getRows'));
				materialRows[i]=JSON.stringify($(materialGrid).datagrid('getRows'));
				 
			}
			
			//???form??????????????????
			 if(!$('#reportAdjustprice_form').form('validate')){
					return;
			 }
			
			 $("#saveForm").linkbutton("disable");
			 $("#reportAdjustprice_form").form('submit', {
					url : url,
					onSubmit : function(param) {
						param.oldPriceRowsList = oldPriceRows;
						param.nowPriceRowsList = nowPriceRows;
						param.sameRowsList = sameRows;
						param.materialRowsList = materialRows;
					},
					success : function(data) {
						var json = $.parseJSON(data);
						if (json.success) {
							for (var i in json.rows) {
								$("#reportCode"+i).val(json.rows[i].reportCode);
							}
							parent.messagerShow({
								title : '????????????!',
								msg : json.msg
							});
						}else{
							parent.messagerShow({
								title : '????????????!',
								msg : json.msg
							});
						}
						$("#saveForm").linkbutton("enable");
						//?????????????????????????????????????????????????????????
						for(var k=0;k<intentionList.length;k++){
							$('#merchandisePriceOld_Grid'+k).datagrid('reload');
							$('#merchandisePriceNow_Grid'+k).datagrid('reload');
							$('#merchandisePriceCompare_Grid'+k).datagrid('reload');
						}
					}
				});
			/*utils.form("reportAdjustprice_form").submit(url, null, function() {
			 });*/
		},
		
		submitReportCGForm : function() {
			var applicationCode = $("input#applicationCode").val();
			var intentionAndSupplierCodes = $("input#intentionAndSupplierCodes").val();
			var intentionList=intentionAndSupplierCodes.split(",");
			
			var url="reportAdjustprice_insertApplicationReportAdjustpriceCG_2.html?applicationCode=" + applicationCode
					+ "&intentionAndSupplierCodes=" + intentionAndSupplierCodes;
			
			var oldPriceRows = new Array();
			var nowPriceRows = new Array();
			var sameRows = new Array();
			var materialRows = new Array();
			for(var i=0;i<intentionList.length;i++){
				//??????????????????????????????
				oldPriceEditIndex [i]= undefined;
				nowPriceEditIndex [i]= undefined;
				sameEditIndex [i]    = undefined;
				materialEditIndex [i]= undefined;
				
				var oldPriceGrid='#merchandisePriceOld_Grid'+i;
				var nowPriceGrid='#merchandisePriceNow_Grid'+i;
				var sameGrid='#merchandiseSame_Grid'+i;
				var materialGrid='#merchandiseMaterial_Grid'+i;
				
				// ??????????????????grid??????
				if (reportAdjustpriceFormFn.endEditPriceOld(i)) {
					$(oldPriceGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// ??????????????????grid??????
				if (reportAdjustpriceFormFn.endEditPriceNow(i)) {
					$(nowPriceGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// ??????????????????grid??????
				if (reportAdjustpriceFormFn.endEditSame(i)) {
					$(sameGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				// ????????????grid??????
				if (reportAdjustpriceFormFn.endEditMaterial(i)) {
					$(materialGrid).datagrid('acceptChanges');
				} else {
					return false;
				}
				
				/*//??????????????????????????????????????????
				if($(oldPriceGrid).datagrid('getRows').length<1){
					 $.messager.alert('??????', '<center>??????'+(i+1)+'???????????????????????????????????????</center>');
					 return;   
				}
				//??????????????????????????????????????????
				if($(nowPriceGrid).datagrid('getRows').length<1){
					$.messager.alert('??????', '<center>??????'+(i+1)+'???????????????????????????????????????</center>');
					return;   
				}*/
				
				var selectedRadioValue=$("input[name='samePart"+i+"']:checked").val();
				if(selectedRadioValue=="1"){
					//???????????????"???"?????????
					if($(sameGrid).datagrid('getRows').length<1){
						//$.messager.alert('??????', '<center>??????'+(i+1)+'????????????????????????????????????????????????</center>');
						// return; 
					}else{
						sameRows[i]=JSON.stringify($(sameGrid).datagrid('getRows'));
					}
				}else{
					sameRows[i]='[]';
				}

				//??????????????????????????????????????????
				/*if($(materialGrid).datagrid('getRows').length<1){
					 $.messager.alert('??????', '<center>??????'+(i+1)+'?????????????????????????????????</center>');
					 return;   
				}*/
				reportAdjustpriceFormFn.setPropertiesName(i);
				
				/*//??????????????????????????????????????????
				if($(oldPriceGrid).datagrid('getRows').length<1){
					 $.messager.alert('??????', '<center>??????'+(i+1)+'???????????????????????????????????????</center>');
					 return;   
				}
				//??????????????????????????????????????????
				if($(nowPriceGrid).datagrid('getRows').length<1){
					$.messager.alert('??????', '<center>??????'+(i+1)+'???????????????????????????????????????</center>');
					return;   
				}*/
			//	if($(oldPriceGrid).datagrid('getRows').length>0){
				oldPriceRows[i]=JSON.stringify($(oldPriceGrid).datagrid('getRows'));
			//	}
			//	if($(nowPriceGrid).datagrid('getRows').length>0){
				nowPriceRows[i]=JSON.stringify($(nowPriceGrid).datagrid('getRows'));
			//	}
			//	if($(materialGrid).datagrid('getRows').length>0){
				materialRows[i]=JSON.stringify($(materialGrid).datagrid('getRows'));
			//	}
				 
			}
			
			/*//???form??????????????????
			 if(!$('#reportAdjustprice_form').form('validate')){
					return;
			 }*/
			
			// $("#saveForm").linkbutton("disable");
			 $("#reportAdjustprice_form").form('submit', {
					url : url,
					onSubmit : function(param) {
						param.oldPriceRowsList = oldPriceRows;
						param.nowPriceRowsList = nowPriceRows;
						param.sameRowsList = sameRows;
						param.materialRowsList = materialRows;
					},
					success : function(data) {
						var json = $.parseJSON(data);
						if (json.success) {
							for (var i in json.rows) {
								$("#reportCode"+i).val(json.rows[i].reportCode);
							}
							parent.messagerShow({
								title : '????????????!',
								msg : json.msg
							});
						}else{
							parent.messagerShow({
								title : '????????????!',
								msg : json.msg
							});
						}
						$("#saveForm").linkbutton("enable");
						//?????????????????????????????????????????????????????????
						for(var k=0;k<intentionList.length;k++){
							$('#merchandisePriceOld_Grid'+k).datagrid('reload');
							$('#merchandisePriceNow_Grid'+k).datagrid('reload');
							$('#merchandisePriceCompare_Grid'+k).datagrid('reload');
						}
					}
				});
			/*utils.form("reportAdjustprice_form").submit(url, null, function() {
			 });*/
		},
		closeForm: function() {
			parent.pubTab.closeCurrTab();
		},
		
		/**
		 * ??????????????????????????????
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
		
		// ???????????????js 
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
				if (reportAdjustpriceFormFn.endEditPriceOld(gridId)) {
					$('#merchandisePriceOld_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
					oldPriceEditIndex[gridId] = index;
				} else {
					$('#merchandisePriceOld_Grid'+gridId).datagrid('selectRow', oldPriceEditIndex[gridId]);
				}
			}
		},
		appendPriceOld : function(gridId) {
			var date = $('#merchandisePriceOld_Grid'+gridId).datagrid('getData');
			if (reportAdjustpriceFormFn.endEditPriceOld(gridId)) {
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
		
		
		// ???????????????js
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
				if (reportAdjustpriceFormFn.endEditPriceNow(gridId)) {
					$('#merchandisePriceNow_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
					nowPriceEditIndex[gridId] = index;
				} else {
					$('#merchandisePriceNow_Grid'+gridId).datagrid('selectRow', nowPriceEditIndex[gridId]);
				}
			}
		},
		appendPriceNow : function(gridId) {
			var date = $('#merchandisePriceNow_Grid'+gridId).datagrid('getData');
			if (reportAdjustpriceFormFn.endEditPriceNow(gridId)) {
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
		
		// ???????????????js
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
				if (reportAdjustpriceFormFn.endEditSame(gridId)) {
					$('#merchandiseSame_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
					sameEditIndex[gridId] = index;
				} else {
					$('#merchandiseSame_Grid'+gridId).datagrid('selectRow', sameEditIndex[gridId]);
				}
			}
		},
		appendSame : function(gridId) {
			var date = $('#merchandiseSame_Grid'+gridId).datagrid('getData');
			if (reportAdjustpriceFormFn.endEditSame(gridId)) {
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
		
		// ???????????????js 
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
		onClickRowMaterial : function(gridId,index) {
			if (materialEditIndex[gridId] != index) {
				if (reportAdjustpriceFormFn.endEditMaterial(gridId)) {
					$('#merchandiseMaterial_Grid'+gridId).datagrid('selectRow', index).datagrid('beginEdit', index);
					materialEditIndex[gridId] = index;
				} else {
					$('#merchandiseMaterial_Grid'+gridId).datagrid('selectRow', materialEditIndex[gridId]);
				}
			}
		},
		appendMaterial : function(gridId) {
			var date = $('#merchandiseMaterial_Grid'+gridId).datagrid('getData');
			if (reportAdjustpriceFormFn.endEditMaterial(gridId)) {
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
		//???????????????????????????
		changeSameRaido:function(index,checkValue){
			if("1"==checkValue){
				//??????"???"
				$('#same_div'+index).show();
				$('#notSame_div'+index).hide();
				$("#merchandiseSame_Grid"+index).datagrid({fitColumns:true});
				$("#merchandiseSame_Grid"+index).datagrid('load');
			}else{
				//??????"???"
				//$('same_div0').style.display="none";
				$('#same_div'+index).hide();
				$('#notSame_div'+index).show();
				$("#merchandiseSame_Grid"+index).datagrid('load');
			}
		},
		
		
		// ????????????
		reloadData : function(thisId, targetId, method, number) {
			reportAdjustpriceFormFn.clearSelectedData(targetId+number);
			// ???????????????
			var value = $("#" + thisId+number).combobox("getValue");
			if (!value) {
				value = 'null';
			}
			$("#" + targetId+number).combobox('reload', "masterDataType_list" + method + "_5.html?" + thisId + "=" + value);
		},

		// ???????????????????????????????????????
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
		
		//??????????????????
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
		}
		
	};