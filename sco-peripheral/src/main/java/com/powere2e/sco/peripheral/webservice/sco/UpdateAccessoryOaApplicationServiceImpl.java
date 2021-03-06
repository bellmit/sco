package com.powere2e.sco.peripheral.webservice.sco;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;

import com.powere2e.sco.common.service.BusinessConstants.ApplicationStatus;
import com.powere2e.sco.interfaces.service.peripheral.webservice.UpdateAccessoryOaApplicationService;
import com.powere2e.sco.model.accessoryoaapplication.OaApplication;
import com.powere2e.sco.peripheral.utils.PeripheralFileUtils;
import com.powere2e.sco.service.impl.accessoryoaapplication.OaApplicationServiceImpl;

@WebService(endpointInterface = "com.powere2e.sco.interfaces.service.peripheral.webservice.UpdateAccessoryOaApplicationService")
public class UpdateAccessoryOaApplicationServiceImpl implements UpdateAccessoryOaApplicationService {

	public String UpdateAccessoryOaApplication(String oaApplicationCode, String oaUid, String status, String date) {
		String result = "error";
		if (!StringUtils.isBlank(oaApplicationCode) && !StringUtils.isBlank(status) && !StringUtils.isBlank(oaUid) && !StringUtils.isBlank(date)) {
			oaApplicationCode = oaApplicationCode.trim();
			oaUid = oaUid.trim();
			status = status.trim();
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date UpdateDate = dateFormater.parse(date);
				OaApplication oaApplication = OaApplicationServiceImpl.getInstance().loadOaApplication(oaApplicationCode);
				if (oaApplication != null) {
					oaApplication.setApplicationCode(oaApplicationCode);
					oaApplication.setApplicationStatus(status.equals(ApplicationStatus.BH.toString()) ? ApplicationStatus.CG.toString() : status);
					oaApplication.setApplicationStatusDate(UpdateDate);
					oaApplication.setOaApplicationCode(oaUid);
					if (status.equals(ApplicationStatus.SPTG.toString())) {
						oaApplication.setOaApproveDate(UpdateDate);
					}
					if (status.equals(ApplicationStatus.SPZ.toString())) {
						oaApplication.setOaInitiateDate(UpdateDate);
					}
					Integer count = OaApplicationServiceImpl.getInstance().updateOaApplication(oaApplication);
					if (count != 0) {
						result = "success";
						PeripheralFileUtils.logger.info("OA????????????-????????????-SCO????????????:" + oaApplicationCode + "-" + "OA????????????:" + oaUid + "-" + "????????????:" + status + "-" + "??????????????????:" + date);
					}
				} else {
					PeripheralFileUtils.logger.info("OA????????????-????????????-SCO????????????:" + oaApplicationCode + "-" + "OA????????????:" + oaUid + "-" + "????????????:" + status + "-" + "??????????????????:" + date);
				}
			} catch (Exception e) {
				PeripheralFileUtils.logger.error("OA????????????-????????????-SCO????????????:" + oaApplicationCode + "-" + "OA????????????:" + oaUid + "-" + "????????????:" + status + "-" + "??????????????????:" + date);
				PeripheralFileUtils.logger.error(e);
				e.printStackTrace();
			}

		} else {
			PeripheralFileUtils.logger.info("OA????????????-????????????-SCO????????????:" + oaApplicationCode + "-" + "OA????????????:" + oaUid + "-" + "????????????:" + status + "-" + "??????????????????:" + date);
		}
		return result;

	}
}
