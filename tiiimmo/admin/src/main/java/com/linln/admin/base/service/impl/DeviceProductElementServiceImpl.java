package com.linln.admin.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linln.RespAndReqs.DeviceProductElementReq;
import com.linln.RespAndReqs.ScheduleJobReq;
import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.admin.base.domain.ElementProduct;
import com.linln.admin.base.repository.DeviceProductElementRepository;
import com.linln.admin.base.repository.ElementProductRepository;
import com.linln.admin.base.service.DeviceProductElementService;
import com.linln.admin.base.util.ConvertUtil;
import com.linln.admin.base.util.ExcelToDataUtil;
import com.linln.admin.produce.domain.ScheduleJobApi;
import com.linln.admin.produce.repository.ScheduleJobApiRepository;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.utill.ApiUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ww
 * @date 2020/06/17
 */
@Service
public class DeviceProductElementServiceImpl implements DeviceProductElementService {

    @Autowired
    private DeviceProductElementRepository deviceProductElementRepository;

    @Autowired
    private ScheduleJobApiRepository scheduleJobApiRepository;

    @Autowired
    private ElementProductRepository elementProductRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public DeviceProductElement getById(Long id) {
        return deviceProductElementRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<DeviceProductElement> getPageList(Example<DeviceProductElement> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        Sort sort = new Sort(Sort.Direction.ASC, "\\Qproduct_code\\E");
        PageRequest newPage = new PageRequest(page.getPageNumber(),page.getPageSize(),sort);
        return deviceProductElementRepository.findAll(example, newPage);
    }

    /**
     * 保存数据
     * @param deviceProductElement 实体对象
     */
    @Override
    public DeviceProductElement save(DeviceProductElement deviceProductElement) {
        return deviceProductElementRepository.save(deviceProductElement);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return deviceProductElementRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }


    @Override
    public ResultVo importCommonExcel(MultipartFile file) {
        try {
            //System.out.println("-------------------");
            //Workbook wb = WorkbookFactory.create(new FileInputStream((File) file));
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            //HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            //获取列头
            Map<Integer, String> CellHead = ExcelToDataUtil.GetCellHead(sheet, 0);
            //获取内容
            List<Map> entity = ExcelToDataUtil.ExcelConvertEntity(sheet, 1,CellHead );
            List<DeviceProductElement> elementList = new ArrayList<>();
            if(entity!=null&&entity.size()!=0){
                //删除旧的
                String a_or_b = ConvertUtil.ConvertToString(entity.get(0).get("AB面"));
                String pcb_code = ConvertUtil.ConvertToString(entity.get(0).get("成品号"));
                deviceProductElementRepository.deleteByABAndPcbCode(a_or_b,pcb_code);
            }
            for(int label =0; label<entity.size();label++){
                //同步根据元件号码同步查找物料号
                String element_name = ConvertUtil.ConvertToString(entity.get(label).get("元件名"));
                String product_code = ConvertUtil.ConvertToString(entity.get(label).get("物料代码"));
                if(product_code==null||product_code.equals("")){
                    //不存在则调用ERP接口同步并存至本地，存在则获取物料编号
                    ElementProduct elementProduct = elementProductRepository.findByElement_name(element_name);
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
                        paramList.add(element_name);
                        scheduleJobReq.setParamList(paramList);
                        JSONArray lists = ApiUtil.postToScheduleJobApi(jobApi.getApiUrl(),scheduleJobReq);
                        if(lists.size()!=0){
                            JSONObject param = lists.getJSONObject(0);
                            product_code = param.getString("fnumber");
                            elementProduct = new ElementProduct();
                            elementProduct.setProduct_code(product_code);
                            elementProduct.setElement_name(element_name);
                            elementProductRepository.save(elementProduct);
                        }

                    }else {
                        product_code = elementProduct.getProduct_code();
                    }
                }


                String skip_status = ConvertUtil.ConvertToString(entity.get(label).get("跳过"));
                String sample_name = ConvertUtil.ConvertToString(entity.get(label).get("图样名"));
                String device_code = ConvertUtil.ConvertToString(entity.get(label).get("机器"));
                String element_no = ConvertUtil.ConvertToString(entity.get(label).get("元件号码"));
                if(element_no.contains(".")){
                    element_no  = element_no.substring(0,element_no.indexOf("."));
                }
                String position = ConvertUtil.ConvertToString(entity.get(label).get("安装位置"));
                if(position.contains(".")){
                    position  = position.substring(0,position.indexOf("."));
                }
                String pcb_code = ConvertUtil.ConvertToString(entity.get(label).get("成品号"));
                String a_or_b = ConvertUtil.ConvertToString(entity.get(label).get("AB面"));
                String X = ConvertUtil.ConvertToString(entity.get(label).get("X"));
                String Y = ConvertUtil.ConvertToString(entity.get(label).get("Y"));
                String R = ConvertUtil.ConvertToString(entity.get(label).get("R"));
                String work_desk = ConvertUtil.ConvertToString(entity.get(label).get("工作台"));
                String head = ConvertUtil.ConvertToString(entity.get(label).get("Head"));
                String bad_flag = ConvertUtil.ConvertToString(entity.get(label).get("坏板标记"));
                String base_flag = ConvertUtil.ConvertToString(entity.get(label).get("基准标记"));
                String track = ConvertUtil.ConvertToString(entity.get(label).get("轨道"));
                String ori_palte_no = ConvertUtil.ConvertToString(entity.get(label).get("原拼板号码"));
                String machine_type = ConvertUtil.ConvertToString(entity.get(label).get("送料器类型"));
                String group_id = ConvertUtil.ConvertToString(entity.get(label).get("组ID"));
                String group_sort = ConvertUtil.ConvertToString(entity.get(label).get("组内顺序"));
                //String remark = ConvertUtil.ConvertToString(entity.get(label).get("备注"));

                DeviceProductElement element = new DeviceProductElement();
                element.setSkip_status(skip_status);
                element.setSample_name(sample_name);

                //需处理
                int first = device_code.indexOf(" ");
                int second = device_code.indexOf(" ", first + 1);
                device_code = device_code.substring(second+1);
                if(device_code.equalsIgnoreCase("ys12")){
                    element.setDevice_code("B15002");
                }
                if(device_code.equalsIgnoreCase("ys12_yh")){
                    element.setDevice_code("B15002");
                }
                if(device_code.equalsIgnoreCase("ys12f_bb_yh")){
                    element.setDevice_code("B15003");
                }
                if(device_code.equalsIgnoreCase("ys12f_yh")){
                    element.setDevice_code("B1902001");
                }

                element.setElement_no(element_no);
                element.setElement_name(element_name);
                //需处理
                String replace = product_code.replace(".", "");
                String newProductCode = replace.replace("E9", "");
                element.setProduct_code(newProductCode);
                element.setPosition(position);
                element.setPcb_code(pcb_code);
                element.setA_or_b(a_or_b);
                element.setX(X);
                element.setY(Y);
                element.setR(R);
                element.setWork_desk(work_desk);
                element.setHead(head);
                element.setBad_flag(bad_flag);
                element.setBase_flag(base_flag);
                element.setTrack(track);
                element.setOri_palte_no(ori_palte_no);
                element.setMachine_type(machine_type);
                element.setGroup_id(group_id);
                element.setGroup_sort(group_sort);
                element.setStatus(StatusEnum.OK.getCode());
                //element.setRemark(remark);
                elementList.add(element);
            }
            deviceProductElementRepository.saveAll(elementList);
            System.out.println("");

        }catch (Exception e){
            e.printStackTrace();
            return ResultVoUtil.error("导入失败");
        }

        return ResultVoUtil.success("导入成功");
    }

    @Override
    public ResultVo findElement(DeviceProductElementReq req) {
        Integer page = req.getPage(); //当前页
        Integer size = req.getSize(); //每页条数
        String sampleName = req.getSampleName(); //图样名
        String deviceCode = req.getDeviceCode(); //机台编号
        String productCode = req.getProductCode(); //物料编号
        String pcbCode = req.getPcbCode(); //规格型号
        String aORb = req.getAORb(); // ab面

        if(page == null||size == null){
            page = req.getPage();
            size = req.getSize();
        }

        StringBuffer wheresql = new StringBuffer(" where 1=1 ");
        if(sampleName!=null&&!"".equals(sampleName)){
            wheresql.append(" and sample_name  like '" +
                    "%" + sampleName + "%" +
                    "' ");
        }
        if(deviceCode!=null&&!"".equals(deviceCode)){
            wheresql.append(" and device_code  like '" +
                    "%" + deviceCode + "%" +
                    "' ");
        }
        if(productCode!=null&&!"".equals(productCode)){
            wheresql.append(" and product_code  like '" +
                    "%" + productCode + "%" +
                    "' ");
        }
        if(pcbCode!=null&&!"".equals(pcbCode)){
            wheresql.append(" and pcb_code  like '" +
                    "%" + pcbCode + "%" +
                    "' ");
        }
        if(aORb!=null&&!"".equals(aORb)){
            wheresql.append(" and a_or_b  like '" +
                    "%" + aORb + "%" +
                    "' ");
        }

        StringBuffer sql = new StringBuffer("select  *\n" +
                "                from (select row_number()\n" +
                "                over(order by id) as rownumber,*\n" +
                "                from base_device_product_element " +
                wheresql.toString() +
                ") temp_row ");

        List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
        sql.append(" where rownumber between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) +
                "");

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());

        return ResultVoUtil.success("查询成功",mapList,count.size());

    }
}