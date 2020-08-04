package com.linln.admin.base.service.impl;
import com.linln.admin.base.domain.Device;
import com.linln.admin.base.domain.OperationInstruction;
import com.linln.admin.base.domain.OperationManual;
import com.linln.admin.base.repository.OperationInstructionRepository;
import com.linln.admin.base.repository.OperationManualRepository;
import com.linln.admin.base.service.OperationInstructionService;
import com.linln.admin.base.util.ApiResponse;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.vo.ResultVo;
import com.linln.constant.CommonConstant;
import com.linln.utill.FileUtil;
import com.spire.xls.PaperSizeType;
import com.spire.xls.Worksheet;
import com.spire.xls.WorksheetVisibility;
import com.spire.xls.core.spreadsheet.collections.PrstGeomShapeCollection;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 连
 * @date 2020/06/09
 */
@Service
public class OperationInstructionServiceImpl implements OperationInstructionService {

    @Autowired
    private OperationInstructionRepository operationInstructionRepository;

    @Autowired
    private OperationManualRepository operationManualRepository;

    @Value("${operationManual-path}")
    public String operationManualPath;

    @Value("${ESOPFormat}")
    public String ESOPFormat;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public OperationInstruction getById(Long id) {
        return operationInstructionRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<OperationInstruction> getPageList(Example<OperationInstruction> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return operationInstructionRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param operationInstruction 实体对象
     */
    @Override
    public OperationInstruction save(OperationInstruction operationInstruction) {
        return operationInstructionRepository.save(operationInstruction);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return operationInstructionRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }



    @Override
    public ApiResponse importOperationManual(MultipartFile file) {
        if (file == null || file.isEmpty()){
            return ApiResponse.ofParamError("导入文件失败，请选择文件");
        }
        try {
            //Workbook wb = WorkbookFactory.create(featuresImage.getInputStream());
            //System.out.println(wb.getNumberOfSheets());
            //获取第2个工作表
            com.spire.xls.Workbook wb= new com.spire.xls.Workbook();
            wb.loadFromStream(file.getInputStream());//设置excel工作表为一页
            //User user = CommonUtil.getUserInfo();
            System.out.println(wb.getWorksheets().getCount());
            wb.getConverterSetting().setSheetFitToPage(true);
            for(int i = 0 ;i < wb.getWorksheets().getCount();i++){
                int version = 1;//版本
                Worksheet sheet = wb.getWorksheets().get(i);
                //System.out.println(sheet.getVisibility());
                //判断工作本是否可见   Visible可见的   Hidden隐藏
                if(sheet.getVisibility() == WorksheetVisibility.Visible){
                    //设置纸张  以及页边距
                    sheet.getPageSetup().setPaperSize(PaperSizeType.PaperA4);
                    /*sheet.getPageSetup().setLeftMargin(0);
                    sheet.getPageSetup().setRightMargin(0);
                    sheet.getPageSetup().setTopMargin(0);
                    sheet.getPageSetup().setBottomMargin(0);*/
                    PrstGeomShapeCollection pg = sheet.getPrstGeomShapes();//读取有多少个形状
                    for(int j = 0 ;j< pg.size(); j++){
                        //将每个形状的宽高增大20
                        pg.get(j).setWidth(pg.get(j).getWidth() + 7);
                        pg.get(j).setHeight(pg.get(j).getHeight() +4 );

                    }
                    System.out.println(operationManualPath+sheet.getName());
                    int count = operationManualRepository.countWithImage(sheet.getName());//文件名重复的个数
                    version += count;

                    File f = new File(operationManualPath+sheet.getName()+".pdf");
                    if(f.exists()){
                        f.delete();
                    }
                    sheet.saveToPdf(operationManualPath+sheet.getName()+".pdf");//将excel转成PDF
                    File newFile = new File(operationManualPath+sheet.getName()+".pdf");
                    OperationManual operationManual = new OperationManual();
                    operationManual.setUserId(1L);
                    operationManual.setImage(sheet.getName()+".pdf");
                    operationManual.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    operationManual.setFileSize(String.valueOf(newFile.length()));
                    operationManual.setVersion("v"+version+".0");
                    operationManual.setMouldCode(ESOPFormat.replace("{0}",sheet.getName()));
                    operationManualRepository.saveAndFlush(operationManual);
                }

            }

            return ApiResponse.ofSuccess("导入成功");
        }catch (Exception e){
            e.printStackTrace();
            return ApiResponse.ofError("导入失败，请重试");
        }
    }
    @Override
    public void showPDF(HttpServletResponse response, Long id ) {

        OperationInstruction operationInstruction = operationInstructionRepository.findById(id).get();
        if(operationInstruction ==null){
            return;
        }
        String path = CommonConstant.file_path+CommonConstant.usebook_path+operationInstruction.getUploadFile();
        FileUtil.readPDF(response,path);
    }
}