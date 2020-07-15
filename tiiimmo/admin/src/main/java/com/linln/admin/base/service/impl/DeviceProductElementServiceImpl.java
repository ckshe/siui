package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.DeviceProductElement;
import com.linln.admin.base.repository.DeviceProductElementRepository;
import com.linln.admin.base.service.DeviceProductElementService;
import com.linln.admin.base.util.ConvertUtil;
import com.linln.admin.base.util.ExcelToDataUtil;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        return deviceProductElementRepository.findAll(example, page);
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
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            //获取列头
            Map<Integer, String> CellHead = ExcelToDataUtil.GetCellHead(sheet, 0);
            //获取内容
            List<Map> entity = ExcelToDataUtil.ExcelConvertEntity(sheet, 1,CellHead );
            List<DeviceProductElement> elementList = new ArrayList<>();
            for(int label =0; label<entity.size();label++){
                String skip_status = ConvertUtil.ConvertToString(entity.get(label).get("跳过"));
                String sample_name = ConvertUtil.ConvertToString(entity.get(label).get("图样名"));
                String device_code = ConvertUtil.ConvertToString(entity.get(label).get("机器"));
                String element_no = ConvertUtil.ConvertToString(entity.get(label).get("元件号码"));
                String element_name = ConvertUtil.ConvertToString(entity.get(label).get("元件名"));
                String product_code = ConvertUtil.ConvertToString(entity.get(label).get("物料代码"));
                String position = ConvertUtil.ConvertToString(entity.get(label).get("安装位置"));
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
                element.setProduct_code(replace);
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
}