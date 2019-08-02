package com.powere2e.sco.action.datamaintenance.gradecontroldata.merchandiserecycle;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.power.Authority;
import com.powere2e.sco.common.utils.ExcelUtils;
import com.powere2e.sco.common.utils.UploadUtils;
import com.powere2e.sco.interfaces.service.datamaintenance.gradecontroldata.merchandiserecycle.MerchandiseRecycleService;
import com.powere2e.sco.model.datamaintenance.gradecontroldata.merchandiserecycle.MerchandiseRecycle;

/**
 * 商品回收记录的WEB请求响应类
 * @author lipengjie
 * @version 1.0
 * @since 2015年4月14日
 */
public class MerchandiseRecycleAction extends UploadUtils{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8868056619606412985L;
	private MerchandiseRecycleService merchandiseRecycleService;
	/**
	 * 设置要用到的业务类
	 */
	@Override
	protected void beforeBuild() {
		merchandiseRecycleService=(MerchandiseRecycleService)ConfigFactory.getInstance().getBean("merchandiseRecycleService");
	}
	/**
	 * 商品回收记录表显示
	 *
	 * @throws Exception
	 *				可能抛出Exception异常
	 */
	@Authority(privilege="com.powere2e.sco.datamaintenance.gradecontroldata.merchandiserecycle")
	public void doShowMerchandiseRecycle() throws Exception
	{
		this.forwardPage("sco/dataMaintenance/gradecontrolData/merchandiseRecycle/merchandiseRecycleGrid.ftl");
	}
	/**
	 * 商品回收记录列表
	 *
	 * @throws Exception
	 *				可能抛出Exception异常
	 */
	@Authority(privilege="com.powere2e.sco.datamaintenance.gradecontroldata.merchandiserecycle")
	public void doListMerchandiseRecycle() throws Exception
	{
		Map<String,Object> map=getMerchandiseRecycle().toMap();
			map.put("created", this.asString("created"));
			map.put("updated", this.asString("updated"));
			map.put("marStartDate",this.asString("marStartDate")==null?null:this.asDate("marStartDate"));
			map.put("marEndDate", this.asString("marEndDate")==null?null:this.asDate("marEndDate"));
		List<MerchandiseRecycle> list=merchandiseRecycleService.listMerchandiseRecycle(map,this.getPageInfo());
		this.forwardData(true, list, null);
	}
	/**
	 * 显示上传商品回收记录表界面
	 */
	@Authority(privilege = "com.powere2e.sco.datamaintenance.gradecontroldata.merchandiserecycle.upload")
	public void doShowMerchandiseRecycleForm() {
		this.forwardPage("sco/dataMaintenance/gradecontrolData/merchandiseRecycle/merchandiseRecycleUploadForm.ftl");
	}
	/**
	 * 上传商品回收记录表
	 */
	@Authority(privilege = "com.powere2e.sco.datamaintenance.gradecontroldata.merchandiserecycle.upload")
	public void doCompleteImportMerchandiseRecycleData() {
		List<File> fileList = this.doUpload("XLSX");
		File file = null; 
		try {
			file = fileList.get(0);
		} catch (Exception e) {
			this.forwardData(false, null, e.getMessage());
			return ;
		}
		String msg =this.merchandiseRecycleService.completeImportConcessionReceiveData(file);
		if (StringUtils.isBlank(msg)) {
			this.forwardData(true, null, "文件导入成功！");
		} else {
			this.forwardData(false, null, msg);
		}
	}
	/**
	 * 下载导入模板
	 * @throws Exception 
	 */
	@Authority(privilege = "com.powere2e.sco.datamaintenance.gradecontroldata.merchandiserecycle.upload")
	public void doDownloadMerchandiseRecycleDataTemplate() throws Exception {
		this.forwardDownload("excel/sco/dataMaintenance/gradecontrolData/merchandiseRecycle/merchandiseRecycleTemplate.xlsx", ExcelUtils.getEncodeFileName("商品回收数据维护模板.xlsx"));
	}
	/**
	 * 获得页面传递的与实体属性相关的值
	 * 
	 * @throws Exception
	 *				可能抛出Exception异常
	 */
	private MerchandiseRecycle getMerchandiseRecycle() throws Exception{
		MerchandiseRecycle merchandiseRecycle=new MerchandiseRecycle();
		this.asBean(merchandiseRecycle);
		return merchandiseRecycle;
	}
}
