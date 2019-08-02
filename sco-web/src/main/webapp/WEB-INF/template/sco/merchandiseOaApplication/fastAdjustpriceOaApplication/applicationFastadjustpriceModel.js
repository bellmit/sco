var fastAdjustpriceUtil = null;
var clearSearch = false;// 清空查询
var firstIn = true;// 是否第一次进入页面
$(document).ready(
	function() {
		fastAdjustpriceUtil = utils.grid($('#fastAdjustpriceGrid'));
		// 注册查询框
		fastAdjustpriceUtil.registerExtFilters("supplierCode", "supplierName", "merchandiseCode", "merchandiseName",
				"centreTypeCode", "smallTypeCode", "detailTypeCode", "fineTypeCode", "purchaseDepartments",
				"rationed", "purchaseType", "saleType", "dxRoleCode", "dlRoleCode", "applicationStatus",
				"applicationCode", "applicationDateStart", "applicationDateEnd");
		fastAdjustpriceUtil.initFilters({
			onBeforeLoad : function(obj) {
				// 清空查询
				if (clearSearch) {
					return true;
				}
				var length = $.param(obj).split("&").length;
				// 判断参数个数
				if (length < 3) {
					if (!firstIn)
						$.messager.alert("提示", "请输入至少一项查询条件");
					firstIn = false;
					return false;
				}
				if (length == 4 && $.param(obj).indexOf("order") > -1) {
					var param = fastAdjustpriceUtil.getFilterValue();
					if ($.param(param).length > 0) {
						return true;
					}
					$.messager.alert("提示", "请输入至少一项查询条件");
					return false;
				}
			},
			onLoadSuccess : function() {
				firstIn = false;
			}
		});
	}
);

var fastAdjustpriceFn = {
	
	// 添加申请报告(快速调价)
	showInsert : function() {
		var fastAdjustpriceGrid = $('#fastAdjustpriceGrid').datagrid('getChecked');
		if (fastAdjustpriceGrid.length == 0) {
			$.messager.alert('提示', '<center>请至少选择一条记录！</center>');
		} else {
			var intentionAndSupplierCodes = [];
			var applicationFlag = true;
			$.each(fastAdjustpriceGrid, function(index, item) {
				if ( item.applicationStatus == "W"||item.applicationStatus == "SPTG") {
					//没有做OA申请，或者做了OA申请，但审批已通过
					applicationFlag = true;
				}else{
					// 勾选已经做了OA申请记录,并且不是审批通过状态
					applicationFlag = false;
				}
				intentionAndSupplierCodes.push(item.merchandiseCode + ":" + item.supplierCode);
			});
			if (!applicationFlag) {
				$.messager.alert('提示', '<center>请勾选快速调价申请单状态是"无"或"审批通过"的记录！</center>');
				return;
			}
			var url = "fastAdjustprice_showInsertApplicationFastadjustpriceForm_1.html?intentionAndSupplierCodes="
					+ intentionAndSupplierCodes;
			var tabName = '新增商品快速调价申请单';
			if (parent.isExistsTab(tabName)) { // 判断当前要打开的选项卡是否存在，若是，选中存在的选项卡
				parent.activeTab(tabName);
				parent.refreshTab(tabName);
				return;
			}
			parent.addTabByUrl(tabName, 'agent', url);
		}
	},
	
	// 修改申请报告(快速调价)
	showEdit : function(applicationCode, intentionAndSupplierCodes, index) {
		var url = "fastAdjustprice_showUpdateApplicationFastadjustpriceForm_1.html?applicationCode=" + applicationCode
				+ "&intentionAndSupplierCodes=" + intentionAndSupplierCodes;
		var tabName = '申请单' + applicationCode;
		if (parent.isExistsTab(tabName)) { // 判断当前要打开的选项卡是否存在，若是，选中存在的选项卡
			parent.activeTab(tabName);
			parent.refreshTab(tabName);
			return;
		}
		parent.addTabByUrl(tabName, 'agent', url);
	},
	
	// 查询方法
	search : function() {
		var param = fastAdjustpriceUtil.getFilterValue();

		var applicationDateStart = $("#applicationDateStart").datebox("getValue");
		var applicationDateEnd = $("#applicationDateEnd").datebox("getValue");
		// 申请单提交日期的合法性校验
		if (applicationDateStart == "" && applicationDateEnd != "") {
			$.messager.alert('提示', '<center>请输入申请单开始时间！</center>');
			return;
		}
		if (applicationDateEnd == "" && applicationDateStart != "") {
			$.messager.alert('提示', '<center>请输入申请单结束时间！</center>');
			return;
		}
		if (applicationDateStart > applicationDateEnd) {
			$.messager.alert('提示', '<center>申请单结束时间不能早于申请单开始时间！</center>');
			return;
		}

		$('#fastAdjustpriceGrid').datagrid('load', param);
	},
	
	// 删除申请报告(快速调价)
	remove : function() {
		var fastAdjustpriceGrid = $('#fastAdjustpriceGrid').datagrid('getChecked');
		if (fastAdjustpriceGrid.length == 0) {
			$.messager.alert('提示', '<center>请至少选择一条OA申请状态是"草稿"的记录！</center>');
		} else {
			var applicationFlag = true;
			var applicationCodes = [];
			$.each(fastAdjustpriceGrid, function(index, item) {
				if (item.applicationStatus != "CG") {
					// 判断是否勾选已经做了OA申请记录的意向品
					applicationFlag = false;
				} else {
					applicationCodes.push("'" + item.applicationCode + "'");
				}
			});
			if (!applicationFlag) {
				$.messager.alert('提示', '此记录不是草稿状态或已有巡厂申请或包装设计申请，不可删除!');
				return;
			}
			utils.confirm("操作确认", "确认删除OA申请?", function() {
				utils.post("fastAdjustprice_deleteApplicationFastadjustprice_2.html?applicationCodes=" + applicationCodes, null,
					function() {
						fastAdjustpriceUtil.refresh();
					}
				);
			});
		}
	},
	
	// 清除查询
	clearFilter : function() {
		$('.datagrid-sort-desc,.datagrid-sort-asc').removeClass('datagrid-sort-desc datagrid-sort-asc');
		clearSearch = true;
		$('#fastAdjustprice_search').form('reset');
		clearComboboxOptions();//在IE8下，选中中分类，点击查询。清空查询条件时，小分类下拉框有数据
		$('#fastAdjustpriceGrid').datagrid('clearSelections');
		$('#fastAdjustpriceGrid').datagrid('loadData', {
			total : 0,
			rows : []
		});
		clearSearch = false;
	},
	
	refresh : function() {
		clearSearch = true;
		$('#fastAdjustpriceGrid').datagrid('reload');
		clearSearch = false;
	},
	
	// 导出列表页申请记录
	exportAllApplication : function() {
		var param=$.param(fastAdjustpriceUtil.getFilterValue());
		if (param.length < 1) {
			$.messager.alert('提示', '请输入至少一项查询条件!');
			return;
		}
		
		window.location = "fastAdjustprice_exportReportFastadjustpriceApplicationToExcel_3.html?"
				+ $.param(fastAdjustpriceUtil.getFilterValue());

		$.messager.show({
			title : '文件导出提示',
			msg : '文件正在导出中，请稍后！',
			timeout : 4000,
			showType : 'slide'
		});
	},
	
	// 新增创建OA快速调价申请
	createTbpmFastAdjustprice : function() {
		var fastAdjustpriceGrid = $('#fastAdjustpriceGrid').datagrid('getChecked');
		if (fastAdjustpriceGrid.length == 0) {
			$.messager.alert('提示', '<center>请勾选一条快速调价申请单状态是"草稿"的记录!</center>');
		}else if(fastAdjustpriceGrid.length >1){
			$.messager.alert('提示', '<center>请只勾选一条申请记录!</center>');
		} else {
			var applicationCode="";
			var applicationStatus="";
			var url = "fastAdjustprice_syncApplicationFastadjustprice_2.html";
			$.each(fastAdjustpriceGrid, function(index, item) {
				if (item.applicationStatus != "CG") {
					$.messager.alert('提示', '<center>请确认勾选的记录均为"草稿"状态!</center>');
					return;
				}
				applicationCode=item.applicationCode;
				applicationStatus=item.applicationStatus;
				//校验是否缺少必填文件和建议文件
				$.ajax({
					type : "post",
					url : url,
					async : false,//同步
					data : {applicationCode : applicationCode},
					dataType: "json",
					success : function(data){
						if(data.success) {
							//跳转到来伊份TBPM系统
							window.open("http://eip.laiyifen.com/tbpm/cordys/User Interface/com/laiyifen/goodsprice/商品快速调价申请表.caf?sco_no="+applicationCode);
							showRightDownMsg('提示', '创建OA快速调价申请成功！', 3000, 'slide');
						} else {
							//说明缺少文件
							$.messager.alert('提示', json.rows);
						}
					}
				});
				
				/*$.post(url+"?applicationCode="+applicationCode, null, function(json){
					if(json.success){
						//fastAdjustpriceGridUtil.refresh();
						//跳转到来伊份TBPM系统
						window.open("http://eip.laiyifen.com/tbpm/cordys/User Interface/com/laiyifen/goodsprice/商品快速调价申请表.caf?sco_no="+applicationCode);
						showRightDownMsg('提示', '创建OA快速调价申请成功！', 3000, 'slide');
					}else{
						//说明缺少文件
						$.messager.alert('提示', json.rows);
					}
				},"json");*/
				
			});
		}
	},
	
	// 打开TBPM待办
	openTbpmDb : function() {
		var fastAdjustpriceGrid = $('#fastAdjustpriceGrid').datagrid('getChecked');
		if (fastAdjustpriceGrid.length == 0) {
			$.messager.alert('提示', '<center>请勾选一条快速调价申请单状态是"驳回"的记录!</center>');
		}else if(fastAdjustpriceGrid.length >1){
			$.messager.alert('提示', '<center>请只勾选一条申请记录!</center>');
		} else {
			var applicationCode="";
			$.each(fastAdjustpriceGrid, function(index, item) {
				if (item.applicationStatus != "BH") {
					$.messager.alert('提示', '<center>请确认勾选的记录均为"驳回"状态!</center>');
					return;
				}
				 applicationCode=item.applicationCode;
				 //打开TBPM系统的待办页面
				 window.open("http://eip.laiyifen.com/tbpm/cordys/laiyifen/common/html/inbox/inbox.htm");
				}
			);
		}
	},
	
	// 同步OA申请
	syncApplication : function() {
		var fastAdjustpriceGrid = $('#fastAdjustpriceGrid').datagrid('getChecked');
		if (fastAdjustpriceGrid.length == 0) {
			$.messager.alert('提示', '<center>请至少选择一条记录！</center>');
		} else if(fastAdjustpriceGrid.length >1){
			$.messager.alert('提示', '<center>只能选择一条记录！</center>');
		}else{
			var applicationCode="";
			var applicationStatus="";
			$.each(fastAdjustpriceGrid, function(index, item) {
				if (item.applicationStatus != "CG") {
					$.messager.alert('提示', '<center请确认勾选的记录均为"草稿"状态！</center>');
					return;
				}
				 applicationCode=item.applicationCode;
				 applicationStatus=item.applicationStatus;
				 
				 utils.confirm("操作确认","确认同步OA申请吗?",function(){
						$.post("fastAdjustprice_syncApplicationFastadjustprice_2.html?applicationCode="+applicationCode, null, function(json){
							if(json.success){
								fastAdjustpriceUtil.refresh();
							}else{
								//说明缺少文件
								$.messager.alert('提示', '<center>缺少必填申请文件，不能OA同步!</center>');
								//utils.showDlg({title:'缺少申请报告商品错误提示',height:500,width:250,maximizable:true,resizable:true,content:json.rows},true);
							}
						},"json");
					});
			});
		}
	},
	
	// 撤销OA申请
	undoApplication : function() {
		var fastAdjustpriceGrid = $('#fastAdjustpriceGrid').datagrid('getChecked');
		if (fastAdjustpriceGrid.length == 0) {
			$.messager.alert('提示', '<center>请至少选择一条记录！</center>');
		}else{
			var applicationFlag = true;
			var applicationCodes = [];
			$.each(fastAdjustpriceGrid, function(index, item) {
				if (item.applicationStatus != "YX") {
					// 判断是否勾选已经做了OA申请记录的意向品
					applicationFlag = false;
				} else {
					applicationCodes.push(item.applicationCode);
				}
			});
			if (!applicationFlag) {
				$.messager.alert('提示', '<center>只能撤销OA申请单状态是"允许OA同步"的记录！</center>');
				return;
			}
			utils.confirm("操作确认", "确认撤销允许OA同步?", function() {
				utils.post("fastAdjustprice_undoApplicationFastadjustprice_2.html?applicationCodes=" + applicationCodes, null,
					function() {
						fastAdjustpriceUtil.refresh();
					}
				);
			});
		}
	},
	
	//查看OA审批意见
	lookApproveOpinion:function(){
		var fastAdjustpriceGrid = $('#fastAdjustpriceGrid').datagrid('getChecked');
		if (fastAdjustpriceGrid.length == 0) {
			$.messager.alert('提示', '<center>请选择一条记录！</center>');
		} else if(fastAdjustpriceGrid.length >1){
			$.messager.alert('提示', '<center>请只选择一条申请记录！</center>');
		}else{
			var applicationCode = "";// 申请单号
			var applicationStatus = "";// 申请状态
			var oaApplicationCode = "";// OA系统是申请单号
			var oaContacts = "";// OA系统的联系人
			$.each(fastAdjustpriceGrid, function(index, item) {
				applicationCode = item.applicationCode;
				oaApplicationCode = item.oaApplicationCode;
				oaContacts = item.oaContacts;
				applicationStatus = item.applicationStatus;
			});
			
			//需要先判断是否做了OA申请，然后判断OA系统的信息是否为空
			if (applicationCode == null || applicationCode == ""  || applicationStatus == "CG") {
				$.messager.alert('提示', '请选择已经做了OA申请且申请状态不为草稿的记录！');
			} else {
//				if (oaApplicationCode == null || oaApplicationCode == "" || oaContacts == null || oaContacts == "") {
//					$.messager.alert('提示', '<center>没有OA系统同步数据，不能查看OA审批意见!</center>');
//				} else {
					var url = "http://eip.laiyifen.com/tbpm/cordys/User Interface/com/laiyifen/"
						+ "newproduct/common/approvalHistoryPageToSco.caf?formId="+oaApplicationCode;
					window.open(url);
//				}
			}
		}
	}

};