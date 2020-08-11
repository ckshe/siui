package com.linln.admin.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linln.RespAndReqs.ScheduleJobReq;
import com.linln.admin.base.domain.DeviceProductReplaceElement;
import com.linln.admin.base.domain.ElementProduct;
import com.linln.admin.base.repository.DeviceProductReplaceElementRepository;
import com.linln.admin.base.repository.ElementProductRepository;
import com.linln.admin.base.service.DeviceProductReplaceElementService;
import com.linln.admin.produce.domain.ScheduleJobApi;
import com.linln.admin.produce.repository.ScheduleJobApiRepository;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ww
 * @date 2020/08/01
 */
@Service
public class DeviceProductReplaceElementServiceImpl implements DeviceProductReplaceElementService {

    @Autowired
    private DeviceProductReplaceElementRepository deviceProductReplaceElementRepository;
    @Autowired
    private ElementProductRepository elementProductRepository;

    @Autowired
    private ScheduleJobApiRepository scheduleJobApiRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DeviceProductReplaceElement getById(Long id) {
        return deviceProductReplaceElementRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DeviceProductReplaceElement> getPageList(Example<DeviceProductReplaceElement> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return deviceProductReplaceElementRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param deviceProductReplaceElement 实体对象
     */
    @Override
    public DeviceProductReplaceElement save(DeviceProductReplaceElement deviceProductReplaceElement) {
        if(deviceProductReplaceElement.getOriginal_product_code()==null||"".equals(deviceProductReplaceElement.getOriginal_product_code())){
            String originalProductName = deviceProductReplaceElement.getOriginal_product_name();
            String originalProductCode = "";
            ElementProduct elementProduct = elementProductRepository.findByElement_name(originalProductName);
            if(elementProduct==null){
                //ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("SIUI_MES_WU_LIAO_ID");
                ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("SIUI_MES_SCTLDCX");
                ScheduleJobReq scheduleJobReq = new ScheduleJobReq();
                scheduleJobReq.setDesc(jobApi.getRemark() == null ? "" : jobApi.getRemark());
                scheduleJobReq.setKey(jobApi.getKey() == null ? "" : jobApi.getKey());
                scheduleJobReq.setWhere(jobApi.getCondition() == null ? "" : jobApi.getCondition());
                scheduleJobReq.setAction(jobApi.getApiName() == null ? "" : jobApi.getApiName());
                scheduleJobReq.setSelect("");

                scheduleJobReq.setUrl(jobApi.getApiUrl());
                List<String> paramList = new ArrayList<>();
                paramList.add(originalProductName);
                scheduleJobReq.setParamList(paramList);
                JSONArray lists = ApiUtil.postToScheduleJobApi(jobApi.getApiUrl(),scheduleJobReq);
                if(lists.size()!=0){
                    JSONObject param = lists.getJSONObject(0);
                    originalProductCode = param.getString("fnumber");
                    elementProduct = new ElementProduct();
                    elementProduct.setProduct_code(originalProductCode);
                    elementProduct.setElement_name(originalProductName);
                    elementProductRepository.save(elementProduct);
                }

            }else {
                originalProductCode = elementProduct.getProduct_code();
            }

            deviceProductReplaceElement.setOriginal_product_code(originalProductCode);
        }
        if(deviceProductReplaceElement.getReplace_product_code()==null||"".equals(deviceProductReplaceElement.getReplace_product_code())) {
            String replaceProductName = deviceProductReplaceElement.getReplace_product_name();
            String replaceProductCode = "";
            ElementProduct relementProduct = elementProductRepository.findByElement_name(replaceProductName);
            if(relementProduct==null){
                //ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("SIUI_MES_WU_LIAO_ID");
                ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("SIUI_MES_SCTLDCX");
                ScheduleJobReq scheduleJobReq = new ScheduleJobReq();
                scheduleJobReq.setDesc(jobApi.getRemark() == null ? "" : jobApi.getRemark());
                scheduleJobReq.setKey(jobApi.getKey() == null ? "" : jobApi.getKey());
                scheduleJobReq.setWhere(jobApi.getCondition() == null ? "" : jobApi.getCondition());
                scheduleJobReq.setAction(jobApi.getApiName() == null ? "" : jobApi.getApiName());
                scheduleJobReq.setSelect("");

                scheduleJobReq.setUrl(jobApi.getApiUrl());
                List<String> paramList = new ArrayList<>();
                paramList.add(replaceProductName);
                scheduleJobReq.setParamList(paramList);
                JSONArray lists = ApiUtil.postToScheduleJobApi(jobApi.getApiUrl(),scheduleJobReq);
                if(lists.size()!=0){
                    JSONObject param = lists.getJSONObject(0);
                    replaceProductCode = param.getString("fnumber");
                    relementProduct = new ElementProduct();
                    relementProduct.setProduct_code(replaceProductCode);
                    relementProduct.setElement_name(replaceProductName);
                    elementProductRepository.save(relementProduct);
                }

            }else {
                replaceProductCode = relementProduct.getProduct_code();
            }
            deviceProductReplaceElement.setReplace_product_code(replaceProductCode);
        }
        return deviceProductReplaceElementRepository.save(deviceProductReplaceElement);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceProductReplaceElementRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    // ________________________________________________________________
    @Override
    public DeviceProductReplaceElement queryByOriginalProductCode(String original_product_code) {
        return deviceProductReplaceElementRepository.queryByOriginalProductCode(original_product_code);
    }

    @Override
    public ResultVo queryByReplaceProductCode(String replaceProductCode) {
        List<DeviceProductReplaceElement> elements = deviceProductReplaceElementRepository.findByReplaceProductCode(replaceProductCode);
        if (elements == null){
            return ResultVoUtil.error("查询不到替代料");
        }
        return ResultVoUtil.success(elements);
    }
}