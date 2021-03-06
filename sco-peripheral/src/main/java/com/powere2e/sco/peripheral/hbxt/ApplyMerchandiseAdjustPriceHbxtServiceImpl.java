package com.powere2e.sco.peripheral.hbxt;

import com.powere2e.frame.commons.config.ConfigFactory;
import com.powere2e.frame.commons.config.ConfigPath;
import com.powere2e.frame.commons.dao.PageInfo;
import com.powere2e.frame.web.exception.EscmException;
import com.powere2e.sco.common.service.BusinessConstants;
import com.powere2e.sco.common.service.merchandiseoaapplication.MerchandiseOaApplicationUtil;
import com.powere2e.sco.common.utils.PathUtils;
import com.powere2e.sco.common.utils.StrUtils;
import com.powere2e.sco.interfaces.service.common.MasterDataTypeService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.MerchandiseOaApplicationService;
import com.powere2e.sco.interfaces.service.merchandiseoaapplication.fastadjustpriceoaapplication.ApplicationFastadjustpriceService;
import com.powere2e.sco.interfaces.service.peripheral.hbxt.ApplyMerchandiseAdjustPriceHbxtService;
import com.powere2e.sco.model.merchandiseintention.MerchandiseIntention;
import com.powere2e.sco.model.merchandiseoaapplication.ApplicationMerchandise;
import com.powere2e.sco.model.peripheral.hbxt.AdjustPriceApplyReturnVO;
import com.powere2e.sco.model.peripheral.hbxt.CxfFileWrapper;
import com.powere2e.sco.model.peripheral.hbxt.MerchandiseAdjustPriceApplyResponseResult;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class ApplyMerchandiseAdjustPriceHbxtServiceImpl implements ApplyMerchandiseAdjustPriceHbxtService {
    public static Logger logger = Logger.getLogger("cn.vesung");
    private static AtomicInteger FILE_NO = new AtomicInteger(0);

    @Override
    public MerchandiseAdjustPriceApplyResponseResult createMerchandiseAdjustPriceApplication(String merchandiseCode, CxfFileWrapper[] files) {
        MerchandiseAdjustPriceApplyResponseResult responseResult = new MerchandiseAdjustPriceApplyResponseResult();
        responseResult.setSucess(true);

        try {
            StringBuilder sb = new StringBuilder();
            if (files != null && files.length >0) {
                for (int i = 0; i < files.length; i++) {
                    if (i > 0) sb.append(",");
                    sb.append(String.format("[%s] - fileName???%s, fileExtension[%s]" , i+1, files[i].getFileName(), files[i].getFileExtension()));
                }
            }
            populateFileNameAndExtinsion(files);
            logger.info(String.format("createMerchandiseAdjustPriceApplication????????????: " +
                    "merchandiseCode[%s],files[%s]", merchandiseCode, sb.toString()));

        /*try {
            for (CxfFileWrapper file : files) {
                InputStream is = file.getFile().getInputStream();
                FileOutputStream os = new FileOutputStream("E:\\" + file.getFileName() + "." + file.getFileExtension());
                BufferedOutputStream bos = new BufferedOutputStream(os);
                byte[] buffer = new byte[1024*1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
                is.close();
                os.close();
                bos.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }*/

            MerchandiseOaApplicationService merchandiseOaApplicationService = (MerchandiseOaApplicationService) ConfigFactory.getInstance().getBean("merchandiseOaApplicationService");
            MasterDataTypeService masterDataTypeService = (MasterDataTypeService) ConfigFactory.getInstance().getBean("masterDataTypeService");
            ApplicationFastadjustpriceService fastadjustpriceService = (ApplicationFastadjustpriceService) ConfigFactory.getInstance().getBean("fastAdjustpriceService");

            Map<String, Object> map = new HashMap<>();
            map.put("merchandiseCode", merchandiseCode);
            map.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_FASTADJUSTPRICE.toString());
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(1);
            pageInfo.setSize(1);
            List<MerchandiseIntention> merchandiseIntentionList = merchandiseOaApplicationService.queryMerchandiseApplicationList(map, pageInfo);
            if (merchandiseIntentionList == null || merchandiseIntentionList.isEmpty()
                    || merchandiseIntentionList.get(0).getMerchandiseCode().equals(merchandiseCode) == false) {
                responseResult.setSucess(false);
                responseResult.setMessage("???????????????");
                return responseResult;
            }
            MerchandiseIntention merchandiseIntention = merchandiseIntentionList.get(0);
            String applicationStatus = merchandiseIntention.getApplicationStatus();
            if ( !BusinessConstants.MerchandiseApplicationStatus.W.toString().equals(applicationStatus) &&
                    !BusinessConstants.MerchandiseApplicationStatus.SPTG.toString().equals(applicationStatus) ) {
                responseResult.setSucess(false);
                responseResult.setMessage("???????????????????????????????????????'???'???'????????????'?????????");
                return responseResult;

            }
            String applicationCode = "OA".concat(masterDataTypeService.nextID("S_OA_APPLICATION"));
            String supplierCode = merchandiseIntention.getSupplierCode();
            String intentionSupplierCode = supplierCode;
            String intentionAndSupplierCodes = merchandiseCode + ":" + supplierCode;

            String oaFastadjustpriceFilePath = PathUtils.getOaFastadjustpriceFilePath();
            oaFastadjustpriceFilePath = oaFastadjustpriceFilePath.concat("/");
            if (!oaFastadjustpriceFilePath.endsWith("/") && !oaFastadjustpriceFilePath.endsWith("\\")){
                oaFastadjustpriceFilePath += "/";
            }
            List<File> fileList = this.uploadFilesToDest(new File(oaFastadjustpriceFilePath), files);
            //???????????????????????????
            List<ApplicationMerchandise> applicationMerchandises = MerchandiseOaApplicationUtil
                    .getIntentionAndSupplierCodeGroupList(intentionAndSupplierCodes);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("list", applicationMerchandises);
            resultMap.put("applicationType", BusinessConstants.ApplicationType.MERCHANDISE_FASTADJUSTPRICE.toString());// ????????????
            List<ApplicationMerchandise> appList = merchandiseOaApplicationService.listIntentionOaApplication(resultMap);
            if (appList != null && appList.size() > 0) {
                for (ApplicationMerchandise merchandise : appList) {
                    if (!BusinessConstants.MerchandiseApplicationStatus.CG.toString().equals(
                            merchandise.getApplicationStatus())
                            && !BusinessConstants.MerchandiseApplicationStatus.BH.toString().equals(
                            merchandise.getApplicationStatus())) {
                        // ????????????????????????????????????"??????"???"??????"?????????????????????????????? return
                        responseResult.setSucess(false);
                        responseResult.setMessage("?????????????????????'??????'???'??????'?????????????????????");
                        return responseResult;
                    }
                }
            }

            if (!fileList.isEmpty()) {
                File file = fileList.get(0);
                String reportFileName = StrUtils.getFileResourceName(file.getName());
                String path = oaFastadjustpriceFilePath.replaceAll(ConfigPath.getUploadFilePath(), "").concat(file.getName());// ????????????
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("applicationCode", applicationCode);
                map1.put("intentionAndSupplierCodes", intentionAndSupplierCodes);
                map1.put("merchandiseCode", merchandiseCode);
                map1.put("supplierCode", supplierCode);
                map1.put("path", path);
                map1.put("reportFileName", reportFileName);
                try {
                    String receiptInfo = fastadjustpriceService.completeInsertOrUpdateFastadjustprice(file, map1);
                    if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.NONE.toString().equals(receiptInfo)
                            || MerchandiseOaApplicationUtil.ReportNewOaApplicationState.SAME_CG.toString().equals(receiptInfo)) {
                        logger.info(String.format("createMerchandiseAdjustPriceApplication?????????????????????%s???", "sucess"));
                        AdjustPriceApplyReturnVO adjustPriceApplyReturnVO = new AdjustPriceApplyReturnVO(merchandiseCode, applicationCode);
                        responseResult.setData(adjustPriceApplyReturnVO);
                    } else if (MerchandiseOaApplicationUtil.ReportNewOaApplicationState.DIFFER.toString().equals(receiptInfo)) {
                        //??????????????????????????????OA???????????????,??????????????????!
                        responseResult.setSucess(false);
                        responseResult.setMessage("??????????????????????????????OA???????????????,??????????????????!");
                        return responseResult;

                    } else {//?????????????????????????????????????????????????????????,????????????!
                        responseResult.setSucess(false);
                        responseResult.setMessage("?????????????????????????????????????????????????????????,????????????!");
                        return responseResult;
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    responseResult.setSucess(false);
                    responseResult.setMessage(e.getMessage());
                    return responseResult;
                }
            }


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            responseResult.setSucess(false);
            responseResult.setMessage(e.getMessage());
            return responseResult;
        }
        return responseResult;
    }

    /**
     *
     * @param baseDir  ????????????????????????
     * @param cxfFileWrappers
     * @return  ????????????
     */
    private List<File> uploadFilesToDest(File baseDir, CxfFileWrapper[] cxfFileWrappers) {
        // ????????????????????????
        if (cxfFileWrappers == null || cxfFileWrappers.length ==0) {
            return null;
        }

        // ????????????????????????
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        List<File> files = new ArrayList<File>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cxfFileWrappers.length; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            DecimalFormat df = new DecimalFormat("00");
            String str = df.format(FILE_NO.getAndIncrement());
            String newFileName = StringUtils.substringBeforeLast(cxfFileWrappers[i].getFileName(), ".") + "_" + sdf.format(new Date()) + str
                    + "."
                    + StringUtils.substringAfterLast(cxfFileWrappers[i].getFileName(), ".");
            try {
                File dest = new File(baseDir, newFileName);
                logger.error("??????????????????[????????????]:[???????????????" + dest.getPath() + "]");

                FileOutputStream fos = new FileOutputStream(dest);
                cxfFileWrappers[i].getFile().writeTo(fos);
                fos.flush();
                fos.close();
                files.add(dest);
                logger.error("????????????[????????????]:[??????????????????????" + dest.exists() + "]");
            } catch (IOException e) {
                logger.error("?????????????????????:<br>" + StrUtils.getStackMsgToStr(e));
                throw new EscmException("IO??????", new String[] { "?????????????????????" });
            }
        }
        if (sb.length() > 0) {
            logger.error("?????????????????????:<br>" + sb.toString());
            throw new EscmException(sb.toString());
        }
        return files;
    }

    /**
     * ????????????????????????  ?????????.?????????
     * @param files
     */
    private void populateFileNameAndExtinsion(CxfFileWrapper[] files) {
        if (files == null && files.length ==0) {
            return;
        }
        for (CxfFileWrapper file : files) {
            String fileName = file.getFileName();
            String fileExtension = file.getFileExtension();
            if (fileName.indexOf(".") != -1) {
                //???????????????
                file.setFileExtension(StringUtils.substringAfterLast(fileName, "."));
            } else {
                file.setFileName(fileName + "." + fileExtension);
            }
        }
    }
}
