package com.linln.admin.produce.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.linln.RespAndReqs.ScheduleJobReq;
import com.linln.RespAndReqs.PcbTaskReq;
import com.linln.RespAndReqs.responce.PTDeviceResp;
import com.linln.RespAndReqs.ProcessTaskReq;
import com.linln.RespAndReqs.responce.PlateNoInfo;
import com.linln.admin.base.domain.Device;
import com.linln.admin.base.domain.DeviceTheoryTime;
import com.linln.admin.base.domain.Process;
import com.linln.admin.base.domain.ScannerProcessTask;
import com.linln.admin.base.repository.*;
import com.linln.admin.produce.domain.*;
import com.linln.admin.produce.repository.*;
import com.linln.admin.produce.service.PcbTaskService;
import com.linln.admin.produce.service.ProcessTaskService;
import com.linln.admin.quality.domain.BadClassDetail;
import com.linln.admin.quality.domain.DeviceStatusRecord;
import com.linln.admin.quality.repository.BadClassDetailRepository;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import com.linln.common.utils.ResultVoUtil;
import com.linln.common.vo.ResultVo;
import com.linln.component.shiro.ShiroUtil;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.UserRepository;
import com.linln.utill.ApiUtil;
import com.linln.utill.DateUtil;
import org.apache.xmlbeans.impl.common.ResolverUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.xml.soap.Detail;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 小懒虫
 * @date 2020/05/18
 */
@Service
public class PcbTaskServiceImpl implements PcbTaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PcbTaskRepository pcbTaskRepository;

    @Autowired
    private ModelsRepository modelsRepository;

    @Autowired
    private DeviceStatusRecordRepository deviceStatusRecordRepository;

    @Autowired
    private ProcessTaskRepository processTaskRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ScheduleJobApiRepository scheduleJobApiRepository;

    @Autowired
    private FeedingTaskRepository feedingTaskRepository;

    @Autowired
    private ProcessTaskDeviceRepository processTaskDeviceRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PCBPlateNoRepository pcbPlateNoRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserDeviceHistoryRepository userDeviceHistoryRepository;

    @Autowired
    private PcbTaskPlateNoRepository pcbTaskPlateNoRepository;

    @Autowired
    private BadClassDetailRepository badClassDetailRepository;

    @Autowired
    private PcbTaskPositionRecordDetailRepositoty pcbTaskPositionRecordDetailRepositoty;

    @Autowired
    private ProcessTaskRealPlateSortRepository processTaskRealPlateSortRepository;

    @Autowired
    private ProcessTaskStatusHistoryRepository processTaskStatusHistoryRepository;

    @Autowired
    private ProcessTaskDetailRepositoty processTaskDetailRepositoty;

    @Autowired
    private ScannerProcessTaskRepository scannerProcessTaskRepository;

    @Autowired
    private PcbTaskFirstPlateNoRepository pcbTaskFirstPlateNoRepository;

    @Autowired
    private ProcessTaskDetailDeviceRepository processTaskDetailDeviceRepository;

    @Autowired
    private ProcessTaskDeviceTheoryTimeRepository processTaskDeviceTheoryTimeRepository;

    @Autowired
    private DeviceTheoryTimeRepository deviceTheoryTimeRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public PcbTask getById(Long id) {
        return pcbTaskRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<PcbTask> getPageList(Example<PcbTask> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        Sort sort = new Sort(Sort.Direction.DESC, "\\Qproduce_plan_complete_date\\E");
        PageRequest newpage = new PageRequest(page.getPageNumber(),page.getPageSize(),sort);


        return pcbTaskRepository.findAll(example, newpage);
    }

    /**
     * 保存数据
     * @param pcbTask 实体对象
     */
    @Override
    public PcbTask save(PcbTask pcbTask) {
        return pcbTaskRepository.save(pcbTask);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return pcbTaskRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public ResultVo getPcbTaskFromERP(String dataBetween) {
        System.out.println("-----------开始同步-------------");

        ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("SIUI_MES_SCRWDCX");
        ScheduleJobReq scheduleJobReq = new ScheduleJobReq();
        scheduleJobReq.setDesc(jobApi.getRemark() == null ? "" : jobApi.getRemark());
        scheduleJobReq.setKey(jobApi.getKey() == null ? "" : jobApi.getKey());
        scheduleJobReq.setWhere(jobApi.getCondition() == null ? "" : jobApi.getCondition());
        scheduleJobReq.setAction(jobApi.getApiName() == null ? "" : jobApi.getApiName());
        scheduleJobReq.setSelect("");

        scheduleJobReq.setUrl(jobApi.getApiUrl());
        List<String> paramList = new ArrayList<>();
        paramList.add("");
        scheduleJobReq.setParamList(paramList);
        JSONArray lists = ApiUtil.postToScheduleJobApi(jobApi.getApiUrl(),scheduleJobReq);

       /* System.out.println(scheduleJobReq.toString());
        String path = "C:\\chaosheng_file\\task.json";

        String s = ReadUtill.readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        JSONArray lists = jobj.getJSONArray("data");*/
        System.out.println("-----------list-------------"+lists.size());
        List<PcbTask> pckTaskList = new ArrayList<>();
        List<PCBPlateNo> plateNoList = new ArrayList<>();
        List<String> pcbTaskCodeList = new ArrayList<>();
        List<PcbTask> touchanPcbTaskList = pcbTaskRepository.findAllByPcbTaskStatus("已投产");

        for(int i = 0 ; i<lists.size();i++){
            JSONObject param = lists.getJSONObject(i);
            //生产任务单号
            String pcb_task_code = param.getString("FRWFBillNo");

            //入库数量
            Integer finishCount = param.getInteger("FAuxStockQty");

            //pcb编码
            String pcb_id = param.getString("FPCBModel");
            //工单状态
            String pcb_task_status = param.getString("FStatus");
            if(!pcb_id.contains("DCY2.90")&&!pcb_id.contains("DCY5")){
                continue;
            }
            if("结案".equals(pcb_task_status)||"计划".equals(pcb_task_status)){
                continue;
            }
            pcbTaskCodeList.add(pcb_task_code);

            //这里需要匹配是否已同步过的
            PcbTask pcbTask = new PcbTask();

            List<PcbTask> oldPcbTasks = pcbTaskRepository.findAllByPcb_task_code(pcb_task_code);
            //计划投产时间
            Date produce_plan_date =  param.getDate("FPlanCommitDate");
            //计划完成时间
            Date produce_plan_complete_date =  param.getDate("FPlanFinishDate");
            //pcb 生产数量
            Integer pcb_quantity = param.getInteger("FPCBQty");

            if(oldPcbTasks!=null&&oldPcbTasks.size()!=0){
                pcbTask = oldPcbTasks.get(0);

                if(pcbTask.getPcb_task_status().contains("下达")||"确认".equals(pcbTask.getPcb_task_status())){
                    if(pcbTask.getAmount_completed()!=null&&!pcbTask.getAmount_completed().equals(finishCount)){
                        //更改入库时间
                        pcbTask.setWarehousing_time(new Date());
                    }
                    pcbTask.setAmount_completed(finishCount);
                   /* //修改入库工序
                    ProcessTask rukuProcessTaskCode = processTaskRepository.findByPcb_task_idAndProcess(pcbTask.getId(), "入库");
                    rukuProcessTaskCode.setProcess_task_status("进行中");
                    rukuProcessTaskCode.setAmount_completed(finishCount);
                    rukuProcessTaskCode.setFinish_time(new Date());
                    processTaskRepository.save(rukuProcessTaskCode);*/
                    pcbTask.setProduce_plan_complete_date(produce_plan_complete_date);
                    pcbTask.setProduce_plan_date(produce_plan_date);
                    pcbTask.setPcb_quantity(pcb_quantity);
                    pcbTask.setPcb_task_status(pcb_task_status);
                    pcbTaskRepository.save(pcbTask);
                    continue;
                }
                if(!"已完成".equals(pcbTask.getPcb_task_status())){
                    if(!pcbTask.getAmount_completed().equals(finishCount)){
                        //更改入库时间
                        pcbTask.setWarehousing_time(new Date());
                    }
                    pcbTask.setAmount_completed(finishCount);
                    //修改入库工序
                    ProcessTask rukuProcessTaskCode = processTaskRepository.findByPcb_task_idAndProcess(pcbTask.getId(), "入库");
                    rukuProcessTaskCode.setProcess_task_status("进行中");
                    rukuProcessTaskCode.setAmount_completed(finishCount);
                    processTaskRepository.save(rukuProcessTaskCode);
                    pcbTask.setProduce_plan_complete_date(produce_plan_complete_date);
                    pcbTask.setProduce_plan_date(produce_plan_date);
                    pcbTaskRepository.save(pcbTask);
                    continue;
                }
                if("已完成".equals(pcbTask.getPcb_task_status())){
                    continue;
                }
            }


            //制造编号
            String task_sheet_code = param.getString("FProduceNo");

            //pcb名称
            String pcb_name = param.getString("FPCBName");

            //实际投产时间
            Date produce_date =  param.getDate("FStartDate");
            //生产完成时间
            Date produce_complete_date =  param.getDate("FFinishDate");
            //机型名称
            String model_name = param.getString("FProduceName");

            //机型版本
            String model_ver = param.getString("FProduceModel");


            //投料单号
            String feeding_code = param.getString("FTLFBillno");
            //车间
            String workshop = param.getString("FWorkShop");
            //ROHS
            String is_rohs = param.getString("FRoHSBz");
            //通知日期
            Date noticeDate = param.getDate("FNoticeDate");
            pcbTask.setProduce_plan_date(produce_plan_date);
            pcbTask.setTask_sheet_code(task_sheet_code);
            pcbTask.setIs_rohs(is_rohs);
            pcbTask.setPcb_task_code(pcb_task_code);
            pcbTask.setProduce_date(produce_date);
            pcbTask.setTask_sheet_date(noticeDate);
            pcbTask.setPcb_name(pcb_name);
            pcbTask.setFactory("万吉厂区");
            pcbTask.setProduce_plan_complete_date(produce_plan_complete_date);
            pcbTask.setProduce_complete_date(produce_complete_date);
            pcbTask.setModel_name(model_name);
            pcbTask.setPcb_id(pcb_id);
            pcbTask.setPcb_task_status(pcb_task_status);
            pcbTask.setModel_ver(model_ver);
            pcbTask.setFeeding_task_code(feeding_code);
            pcbTask.setPcb_quantity(pcb_quantity);
            pcbTask.setWorkshop(workshop);
            pcbTask.setTemp_status_useless(pcb_task_status);


            //pcbPlateNoRepository.save(pcbPlateNo);
            String temp = pcb_id.substring(pcb_id.length()-2,pcb_id.length());
            if(temp.contains("R")){
                pcbTask.setIs_rohs("是");
            }else {
                pcbTask.setIs_rohs("否");
            }
            //优先级默认1
            pcbTask.setPriority(1);
            pckTaskList.add(pcbTask);
        }
        pcbPlateNoRepository.saveAll(plateNoList);
        pcbTaskRepository.saveAll(pckTaskList);

        //循环本地已投产的排产计划
        for(PcbTask pcbTask : touchanPcbTaskList){
            boolean isExist = pcbTaskCodeList.contains(pcbTask.getPcb_task_code());
            if(!isExist){
                if(!pcbTask.getAmount_completed().equals(pcbTask.getPcb_quantity())){
                    //更改入库时间
                    pcbTask.setWarehousing_time(new Date());
                }
                pcbTask.setAmount_completed(pcbTask.getPcb_quantity());
                pcbTask.setPcb_task_status("已完成");
                pcbTask.setProduce_complete_date(new Date());

                pcbTaskRepository.save(pcbTask);
                //修改入库工序
                ProcessTask rukuProcessTaskCode = processTaskRepository.findByPcb_task_idAndProcess(pcbTask.getId(), "入库");
                rukuProcessTaskCode.setProcess_task_status("已完成");
                rukuProcessTaskCode.setIs_now_flag("");
                rukuProcessTaskCode.setAmount_completed(rukuProcessTaskCode.getPcb_quantity());
                rukuProcessTaskCode.setFinish_time(new Date());
                processTaskRepository.save(rukuProcessTaskCode);
            }
        }
        System.out.println("-----------结束同步-------------");

        return ResultVoUtil.success("同步完成");
    }


    @Override
    public ResultVo getFeedingTaskFromERP(String FRWFBillNo) {

        ScheduleJobApi jobApi = scheduleJobApiRepository.findAllByApiName("SIUI_MES_SCTLDCX");
        ScheduleJobReq scheduleJobReq = new ScheduleJobReq();
        scheduleJobReq.setKey(jobApi.getKey() == null ? "" : jobApi.getKey());
        scheduleJobReq.setDesc(jobApi.getRemark() == null ? "" : jobApi.getRemark());
        scheduleJobReq.setWhere(jobApi.getCondition() == null ? "" : jobApi.getCondition());
        scheduleJobReq.setAction(jobApi.getApiName() == null ? "" : jobApi.getApiName());
        scheduleJobReq.setSelect("");
        scheduleJobReq.setUrl(jobApi.getApiUrl());
        List<String> paramList = new ArrayList<>();
        paramList.add(FRWFBillNo);
        scheduleJobReq.setParamList(paramList);
        JSONArray lists = ApiUtil.postToScheduleJobApi(jobApi.getApiUrl(),scheduleJobReq);

        /*String path = "C:\\chaosheng_file\\feeding.json";

        String s = ReadUtill.readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        JSONArray lists = jobj.getJSONArray("data");*/
        List<FeedingTask> feedingTaskList = new ArrayList<>();
        BigDecimal sumQTLv = BigDecimal.ZERO;
        String feedingTaskCode = "";
        String lightPlateNot ="";
        for(int i =0;i<lists.size();i++){
            JSONObject param = lists.getJSONObject(i);
            feedingTaskCode = param.getString("FTLFBillno");
            FeedingTask feedingTask = new FeedingTask();

            feedingTask.setProduct_code(param.getString("FNumber"));
            feedingTask.setProduct_name(param.getString("FName"));
            feedingTask.setSpecification_model(param.getString("FModel"));
            String lightPlateNo = param.getString("FModel");
            if(lightPlateNo!=null&&lightPlateNo.contains("DCY7")){
                lightPlateNot = lightPlateNo;
            }
            feedingTask.setStock_name(param.getString("FStock"));
            feedingTask.setUnit(param.getString("FUnit"));
            feedingTask.setFQtyScrap(param.getBigDecimal("FQtyScrap"));
            feedingTask.setFQtyMust(param.getBigDecimal("FQtyMust"));
            feedingTask.setFStockQty(param.getBigDecimal("FStockQty"));
            feedingTask.setFNStockQty(param.getBigDecimal("FNStockQty"));
            feedingTask.setFQty(param.getBigDecimal("FQty"));
            feedingTask.setFQtlv(param.getString("FQtlv"));
            feedingTask.setStatus(StatusEnum.OK.getCode());
            String qtl = param.getString("FQtlv").replace("%","");
            BigDecimal deqtl = new BigDecimal(qtl);
            sumQTLv = sumQTLv.add(deqtl);
            feedingTask.setPcb_task_code(param.getString("FRWFBillno"));
            feedingTask.setFeeding_task_code(param.getString("FTLFBillno"));
            //feedingTask = feedingTaskRepository.save(feedingTask);
            //System.out.println("------------"+feedingTask.getId());
            feedingTaskList.add(feedingTask);
        }
        feedingTaskRepository.deleteByFeeding_task_code(feedingTaskCode);

        BigDecimal size = new BigDecimal(lists.size()==0?1:lists.size());
        BigDecimal qtlRate = sumQTLv.divide(size,2, RoundingMode.HALF_DOWN);
        System.out.println("------FRWFBillNo:"+FRWFBillNo+"----"+feedingTaskList.size()+"----rate:"+qtlRate);
        feedingTaskRepository.saveAll(feedingTaskList);
        return ResultVoUtil.success(qtlRate+"",lightPlateNot);

    }

    @Override
    public ResultVo generateBatchId(Long pcbTaskId) {

        PcbTask pcbTask = null;

        Optional<PcbTask> optional = pcbTaskRepository.findById(pcbTaskId);
        pcbTask = optional.isPresent()?optional.get():null;
        if(pcbTask==null) {
            return ResultVoUtil.error("查不到该pcb任务单");
        }
        //pcb记录最后版编号
        PCBPlateNo pcbPlateNo = pcbPlateNoRepository.findByPcb_code(pcbTask.getPcb_id());
        Integer first = 1;
        Integer last = 0;
        Integer biginNum = 1;

        //默认20
        Integer year = 20;
        //默认1
        Integer multiple = 1;
        if(pcbPlateNo==null){
            pcbPlateNo = new PCBPlateNo();
            pcbPlateNo.setPcb_code(pcbTask.getPcb_id());
            pcbPlateNo.setLast_plate_no("");
            pcbPlateNo.setAll_count(pcbTask.getPcb_quantity());
            first = first + year*10000;
            last = pcbTask.getPcb_quantity()+ year*10000;
        }else {
            if(pcbPlateNo.getYear()!=null){
                year = pcbPlateNo.getYear();
            }else {
                pcbPlateNo.setYear(year);
            }
            if(pcbPlateNo.getMultiple()!=null){
                multiple = pcbPlateNo.getMultiple();
            }

            first = pcbPlateNo.getAll_count()+1 + year*10000;
            biginNum = pcbPlateNo.getAll_count()+1;

            last = pcbPlateNo.getAll_count()+pcbTask.getPcb_quantity();
            pcbPlateNo.setAll_count(last);
        }
        //生成板编号
        // DCY2.908.H1339B-AL06-A
        // DCY2.909.0186GS-RC
        // DCY2.908.0186GS-RC
        String split[] = pcbTask.getPcb_id().split("\\.");
        //908
        String code1 = split[1];
        //0186GS-RC
        String code2[] = split[2].split("-");
        //0186GS
        String code3 = code2[0];
        //RC
        String code4 = code2[code2.length-1];
        //提取前四位
        //String trim = code3.substring(0,4);
        String trim = code3.replace("GS","");
        String prefix = "";
        String suffix = "";
        //带H DCY2.908.H1356B-AA01-A
        //C
        char lastLetter = code4.charAt(code4.length()-1);
        if("908".equals(code1)&&"H".equals(code3.charAt(0)+"")){
            trim = code2[1];
            prefix = trim+lastLetter;
        }else {
            prefix = trim+lastLetter;
            if("909".equals(code1)){
                prefix = "X"+prefix;
            }
            //char temp = code4.charAt(0);
            if("R".equals(code4.charAt(0)+"")){
                suffix = "R";
            }
        }
        String firstStr = first+"";
        String lastStr = last+"";
        String firstPlate = prefix+firstStr+suffix;
        PlateNoInfo plateNoInfo = new PlateNoInfo();
        plateNoInfo.setPrefix(prefix);
        plateNoInfo.setSuffix(suffix);
        if(pcbPlateNo.getSuffix()!=null&&pcbPlateNo.getPrefix()!=null){
            plateNoInfo.setPrefix(pcbPlateNo.getPrefix());
            plateNoInfo.setSuffix(pcbPlateNo.getSuffix());
        }
        plateNoInfo.setBiginPlateNo(firstPlate);
        plateNoInfo.setBiginNum(biginNum);
        plateNoInfo.setPcbCode(pcbTask.getPcb_id());
        plateNoInfo.setPcbTaskId(pcbTaskId);
        plateNoInfo.setYear(year);
        plateNoInfo.setMultiple(multiple);


        return ResultVoUtil.success(plateNoInfo);
    }

    @Override
    public ResultVo putIntoProduceByPlateInfo(PlateNoInfo plateNoInfo) {
        PcbTask pcbTask = null;

        Optional<PcbTask> optional = pcbTaskRepository.findById(plateNoInfo.getPcbTaskId());
        pcbTask = optional.isPresent()?optional.get():null;
        if(pcbTask==null) {
            return ResultVoUtil.error("查不到该pcb任务单");
        }
        List<ProcessTask> processTaskList = new ArrayList<>();
        List<Process> processList = processRepository.findAllByStatus(StatusEnum.OK.getCode());
        int i = 1;
        //pcb记录最后版编号
        PCBPlateNo pcbPlateNo = pcbPlateNoRepository.findByPcb_code(pcbTask.getPcb_id());
        Integer first = 1;
        Integer last = 0;
        if(pcbPlateNo==null){
            pcbPlateNo = new PCBPlateNo();
        }
        pcbPlateNo.setMultiple(plateNoInfo.getMultiple());
        pcbPlateNo.setPcb_code(pcbTask.getPcb_id());
        pcbPlateNo.setLast_plate_no("");
        pcbPlateNo.setAll_count(pcbTask.getPcb_quantity()+plateNoInfo.getBiginNum()-1);
        pcbPlateNo.setPrefix(plateNoInfo.getPrefix());
        pcbPlateNo.setSuffix(plateNoInfo.getSuffix());
        pcbPlateNo.setYear(plateNoInfo.getYear());
        first = plateNoInfo.getBiginNum() + plateNoInfo.getYear()*10000;
        last = plateNoInfo.getBiginNum() -1 + pcbTask.getPcb_quantity()+plateNoInfo.getYear()*10000;
       /* }else {
            pcbPlateNo.setPrefix(plateNoInfo.getPrefix());
            pcbPlateNo.setSuffix(plateNoInfo.getSuffix());
            first = plateNoInfo.getBiginNum() + pcbPlateNo.getYear()*10000;
            last = plateNoInfo.getBiginNum() -1 + pcbTask.getPcb_quantity()+pcbPlateNo.getYear()*10000  ;
            pcbPlateNo.setAll_count(pcbTask.getPcb_quantity()+plateNoInfo.getBiginNum()-1);
        }*/
        String prefix = plateNoInfo.getPrefix();
        String suffix = plateNoInfo.getSuffix();
        pcbPlateNo.setLast_plate_no(prefix+last+suffix);
        pcbPlateNoRepository.save(pcbPlateNo);

        String firstStr = first+"";
        String lastStr = last+"";
        String firstPlate = prefix+firstStr+suffix;
        String lastPlate = prefix+lastStr+suffix;
        //.保存该排产计划初始板编号初始值
        PcbTaskFirstPlateNo firstPlateNo = new PcbTaskFirstPlateNo();
        firstPlateNo.setFirst_no(plateNoInfo.getBiginNum());
        firstPlateNo.setPcb_code(pcbTask.getPcb_id());
        firstPlateNo.setPcb_task_code(pcbTask.getPcb_task_code());
        firstPlateNo.setPrefix(prefix);
        firstPlateNo.setSuffix(suffix);
        firstPlateNo.setStatus(StatusEnum.OK.getCode());
        firstPlateNo.setYear(pcbPlateNo.getYear());
        pcbTaskFirstPlateNoRepository.save(firstPlateNo);

        pcbTask.setBatch_id(firstPlate+"~"+lastPlate);


        String todayStr = DateUtil.getTodayStringForProcessTaskCode();
        for(Process p : processList){
            String oldDayStr = "";
            Integer last_no = 0;
            if(p.getLast_no()==null){
                last_no = Integer.parseInt(todayStr+"001");
            }else {
                oldDayStr =  p.getLast_no().toString().substring(0,6);
                //如果是同一天，则累加
                if(oldDayStr.equals(todayStr)){
                    last_no = p.getLast_no()+1;
                }else {
                    last_no = Integer.parseInt(todayStr+"001");
                }
            }
            String pre = p.getProcess_pre();
            p.setLast_no(last_no);
            processRepository.save(p);

            ProcessTask processTask = new ProcessTask();
            processTask.setCount_type(p.getCount_type());
            processTask.setPcb_task_code(pcbTask.getPcb_task_code());
            processTask.setIs_rohs(pcbTask.getIs_rohs());
            processTask.setPcb_name(pcbTask.getPcb_name());
            processTask.setPcb_task_id(pcbTask.getId());
            processTask.setPcb_quantity(pcbTask.getPcb_quantity());
            processTask.setPcb_code(pcbTask.getPcb_id());
            processTask.setAmount_completed(0);
            processTask.setProcess_name(p.getName());
            processTask.setWork_time(BigDecimal.ZERO);
            processTask.setTask_sheet_code(pcbTask.getTask_sheet_code());
            processTask.setPcb_name(pcbTask.getPcb_name());
            processTask.setProcess_task_status("未下达");
            processTask.setMultiple(plateNoInfo.getMultiple());
            String processTaskCode = pre+last_no;
            i++;
            processTask.setProcess_task_code(processTaskCode);
            processTaskList.add(processTask);

         /*   //通过生产数量生成每一块板的记录 转移到工序任务下达机台接口
            Integer tempCount = first;
            for(int o = 0;o<pcbTask.getPcb_quantity();o++){
                String tempPlanNo = prefix+tempCount+suffix;
                tempCount ++;
                PcbTaskPlateNo pcbTaskPlateNo = new PcbTaskPlateNo();
                pcbTaskPlateNo.setProcess_task_code(processTaskCode);
                pcbTaskPlateNo.setPlate_no(tempPlanNo);
                pcbTaskPlateNo.setPcb_task_code(pcbTask.getPcb_task_code());
                pcbTaskPlateNo.setPcb_code(pcbTask.getPcb_id());
                pcbTaskPlateNo.setIs_count("0");
                pcbTaskPlateNoRepository.save(pcbTaskPlateNo);
            }*/
        }
        processTaskRepository.saveAll(processTaskList);
        pcbTask.setPcb_task_status("已投产");
        pcbTaskRepository.save(pcbTask);
        return  ResultVoUtil.success("下达切分完成");

    }

    @Override
    public ResultVo putIntoProduceBefore(Long pcbTaskId) {
        PcbTask pcbTask = null;

        Optional<PcbTask> optional = pcbTaskRepository.findById(pcbTaskId);
        pcbTask = optional.isPresent()?optional.get():null;
        if(pcbTask==null) {
            return ResultVoUtil.error("查不到该pcb任务单");
        }
        List<ProcessTask> processTaskList = new ArrayList<>();
        List<Process> processList = processRepository.findAllByStatus(StatusEnum.OK.getCode());
        int i = 1;
        //pcb记录最后版编号
        PCBPlateNo pcbPlateNo = pcbPlateNoRepository.findByPcb_code(pcbTask.getPcb_id());
        Integer first = 1;
        Integer last = 0;
        if(pcbPlateNo==null){
            pcbPlateNo = new PCBPlateNo();
            pcbPlateNo.setPcb_code(pcbTask.getPcb_id());
            pcbPlateNo.setLast_plate_no("");
            pcbPlateNo.setAll_count(pcbTask.getPcb_quantity());
            first = first + 200000;
            last = pcbTask.getPcb_quantity()+200000;
        }else {
            first = pcbPlateNo.getAll_count()+1;
            last = pcbPlateNo.getAll_count()+pcbTask.getPcb_quantity();
            pcbPlateNo.setAll_count(last);
        }
        //生成板编号
        // DCY2.908.H1339B-AL06-A
        // DCY2.909.0186GS-RC
        // DCY2.908.0186GS-RC
        String split[] = pcbTask.getPcb_id().split("\\.");
        String code1 = split[1];
        String code2[] = split[2].split("-");
        String code3 = code2[0];
        String code4 = code2[code2.length-1];
        //提取前四位
        String trim = code3.substring(0,4);
        String prefix = "";
        String suffix = "";
        char lastLetter = code4.charAt(code4.length()-1);
        if("908".equals(code1)&&"H".equals(code3.charAt(0))){
            trim = code2[1];
            prefix = trim+lastLetter;
        }else {
            prefix = trim+lastLetter;
            if("909".equals(code1)){
                prefix = "X"+prefix;
            }
            //char temp = code4.charAt(0);
            if("R".equals(code4.charAt(0)+"")){
                suffix = "R";
            }
        }
        String firstStr = first+"";
        String lastStr = last+"";
        /*if(firstStr.length()<5){
            Integer fleng = firstStr.length();
            int need = 4-fleng;
            String add = "";
            for(int k = 0;k<need;k++){
                add = "0"+add;
            }
            firstStr = add+firstStr;
        }
        if(lastStr.length()<5){
            Integer fleng = lastStr.length();
            int need = 4-fleng;
            String add = "";
            for(int k = 0;k<need;k++){
                add = "0"+add;
            }
            lastStr = add+lastStr;
        }*/
        String firstPlate = prefix+firstStr+suffix;
        String lastPlate = prefix+lastStr+suffix;
        //通过生产数量生成每一块板的记录
        Integer tempCount = first;
        for(int o = 0;o<pcbTask.getPcb_quantity();o++){
            String tempPlanNo = prefix+tempCount+suffix;
            tempCount ++;
            PcbTaskPlateNo pcbTaskPlateNo = new PcbTaskPlateNo();
            pcbTaskPlateNo.setPlate_no(tempPlanNo);
            pcbTaskPlateNo.setPcb_task_code(pcbTask.getPcb_task_code());
            pcbTaskPlateNo.setPcb_code(pcbTask.getPcb_id());
            pcbTaskPlateNo.setIs_count("0");
            pcbTaskPlateNoRepository.save(pcbTaskPlateNo);

        }

        pcbTask.setBatch_id(firstPlate+"~"+lastPlate);
        pcbPlateNo.setLast_plate_no(lastPlate);
        pcbPlateNoRepository.save(pcbPlateNo);
        String todayStr = DateUtil.getTodayStringForProcessTaskCode();
        for(Process p : processList){
            String oldDayStr = "";
            Integer last_no = 0;
            if(p.getLast_no()==null){
                last_no = Integer.parseInt(todayStr+"001");
            }else {
                oldDayStr =  p.getLast_no().toString().substring(0,6);
                //如果是同一天，则累加
                if(oldDayStr.equals(todayStr)){
                    last_no = p.getLast_no()+1;
                }else {
                    last_no = Integer.parseInt(todayStr+"001");
                }
            }
            String pre = p.getProcess_pre();
            p.setLast_no(last_no);
            processRepository.save(p);

            ProcessTask processTask = new ProcessTask();
            processTask.setPcb_task_code(pcbTask.getPcb_task_code());
            processTask.setIs_rohs(pcbTask.getIs_rohs());
            processTask.setPcb_name(pcbTask.getPcb_name());
            processTask.setPcb_task_id(pcbTask.getId());
            processTask.setPcb_quantity(pcbTask.getPcb_quantity());
            processTask.setPcb_code(pcbTask.getPcb_id());
            processTask.setAmount_completed(0);
            processTask.setProcess_name(p.getName());
            processTask.setWork_time(BigDecimal.ZERO);
            processTask.setTask_sheet_code(pcbTask.getTask_sheet_code());
            processTask.setPcb_name(pcbTask.getPcb_name());
            processTask.setProcess_task_status("未下达");
            String processTaskCode = pre+last_no;
            i++;
            processTask.setProcess_task_code(processTaskCode);
            processTaskList.add(processTask);

        }
        processTaskRepository.saveAll(processTaskList);
        pcbTask.setPcb_task_status("已投产");
        pcbTaskRepository.save(pcbTask);
        return  ResultVoUtil.success("下达切分完成");
    }

    @Override
    @Transactional
    public ResultVo putIntoProduce(PcbTaskReq pcbTaskReq) {

        ProcessTask processTask = null;
        Optional<ProcessTask> optional = processTaskRepository.findById(pcbTaskReq.getProcessTaskId());;
        processTask = optional.isPresent()?optional.get():null;
        if(processTask==null){
            return ResultVoUtil.error("查找不到该工序计划");
        }

        PcbTaskFirstPlateNo pcbTaskFirstPlateNo = pcbTaskFirstPlateNoRepository.findByPcb_task_code(processTask.getPcb_task_code());
        //PCBPlateNo pcbPlateNo = pcbPlateNoRepository.findByPcb_code(processTask.getPcb_code());

        processTask.setDevice_code(pcbTaskReq.getDeviceCode());
        processTask.setDevice_name(pcbTaskReq.getDeviceName());
        processTask.setPlan_start_time(pcbTaskReq.getPlanStartTime());
        processTask.setPlan_finish_time(pcbTaskReq.getPlanFinishTime());
        //processTask.setAmount_completed(0);
        processTask.setProcess_task_status("已下达");
        processTaskRepository.save(processTask);
        String[] split = pcbTaskReq.getDeviceCode().split(",");
        List<ProcessTaskDevice> list = new ArrayList<>();
        for (int i=0;i<split.length;i++){
            String deviceCode = split[i];
            ProcessTaskDevice processTaskDevice = new ProcessTaskDevice();
            processTaskDevice.setAmount(0);
            processTaskDevice.setDevice_code(deviceCode);
            processTaskDevice.setProcess_task_code(processTask.getProcess_task_code());
            processTaskDevice.setPlant_code("");
            processTaskDevice.setTd_status("");
            processTaskDevice.setReCount("0");
            processTaskDevice.setLast_amount(0);

            list.add(processTaskDevice);
        }
        Integer year = pcbTaskFirstPlateNo.getYear();
        if(year==null){
            year = 20;
        }
        if(split.length>0){
            Integer tempCount = pcbTaskFirstPlateNo.getFirst_no();
            for(int o = 0;o<processTask.getPcb_quantity();o++){
                String tempPlanNo = pcbTaskFirstPlateNo.getPrefix()+(tempCount+year*10000)+pcbTaskFirstPlateNo.getSuffix();
                tempCount ++;
                PcbTaskPlateNo pcbTaskPlateNo = new PcbTaskPlateNo();
                pcbTaskPlateNo.setProcess_task_code(processTask.getProcess_task_code());
                pcbTaskPlateNo.setPlate_no(tempPlanNo);
                pcbTaskPlateNo.setPcb_task_code(processTask.getPcb_task_code());
                pcbTaskPlateNo.setPcb_code(processTask.getPcb_code());
                pcbTaskPlateNo.setIs_count("0");
                pcbTaskPlateNoRepository.save(pcbTaskPlateNo);
            }
        }
        processTaskDeviceRepository.saveAll(list);
        return ResultVoUtil.success("工序计划下达到机台成功");
    }

    @Override
    public ResultVo findProcessTaskByPCBTaskId(Long id) {
        List<ProcessTask> processTaskList = processTaskRepository.findAllByPcb_task_id(id);

        return ResultVoUtil.success(processTaskList);
    }

    @Override
    public ResultVo findProcessTaskByProcessName(PcbTaskReq pcbTaskReq) {

        StringBuffer wheresql = new StringBuffer();

        if(pcbTaskReq.getProcessTaskCode()!=null&&!"".equals(pcbTaskReq.getProcessTaskCode())){
            wheresql.append(" and t1.process_task_code like '%" +
                    pcbTaskReq.getProcessTaskCode() +
                    "%' ");
        }
        if(pcbTaskReq.getPcbTaskCode()!=null&&!"".equals(pcbTaskReq.getPcbTaskCode())){
            wheresql.append(" and t1.pcb_task_code like '%" +
                    pcbTaskReq.getPcbTaskCode() +
                    "%' ");
        }
        StringBuffer sql = new StringBuffer("select * from(\n" +
                "select *, ROW_NUMBER() OVER(order by t4.Id asc) row from\n" +
                "(SELECT t1.*,t2.pcb_id,t2.feeding_task_code from produce_process_task t1 LEFT JOIN produce_pcb_task t2 on t2.pcb_task_code = t1.pcb_task_code where t1.process_name = '备料' and t1.process_task_status != '未下达' " +
                wheresql.toString() +
                ")t4)t3\n");
        Integer page = 1;
        Integer size = 10;
        if(pcbTaskReq.getPage()!=null&&pcbTaskReq.getSize()!=null){
            page = pcbTaskReq.getPage();
            size = pcbTaskReq.getSize();
        }

        List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());

        sql.append("where t3.Row between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) +
                "");


        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        return ResultVoUtil.success("查询成功",mapList,count.size());
    }

    // 7.06修改
    // 排产计划列表
    @Override
    public ResultVo findScheduling(PcbTaskReq pcbTaskReq) {



        Integer page = pcbTaskReq.getPage(); //当前页
        Integer size = pcbTaskReq.getSize(); //每页条数
        String taskSheetCode = pcbTaskReq.getTaskSheetCode();  //生产批次
        String pcbId = pcbTaskReq.getPcbId(); //规格型号
        String pcbName = pcbTaskReq.getPcbName(); //物料名称
        String pcbTask = pcbTaskReq.getPcbTaskCode();
        String status = pcbTaskReq.getStatus();
        if(status==null||"".equals(status)){
            status = "已投产";
        }
        if(pcbTaskReq.getPage()!=null&&pcbTaskReq.getSize()!=null){
            page = pcbTaskReq.getPage();
            size = pcbTaskReq.getSize();
        }

        StringBuffer wheresql = new StringBuffer(" where 1=1 and pcb_task_status != '确认' ");
        if(taskSheetCode!=null&&!"".equals(taskSheetCode)){
            wheresql.append(" and task_sheet_code  like '" +
                    "%" + taskSheetCode + "%" +
                    "' ");
        }
        if(pcbId!=null&&!"".equals(pcbId)){
            wheresql.append(" and pcb_id like '" +
                    "%" + pcbId + "%" +
                    "' ");
        }
        if(pcbName!=null&&!"".equals(pcbName)){
            wheresql.append(" and pcb_name like '" +
                    "%" + pcbName + "%" +
                    "' ");
        }
        if(pcbTask!=null&&!"".equals(pcbTask)){
            wheresql.append(" and pcb_task_code like '" +
                    "%" + pcbTask + "%" +
                    "' ");
        }
        if(status!=null&&!"".equals(status)&&!"全部".equals(status)){
            wheresql.append(" and pcb_task_status like '" +
                    "%" + status + "%" +
                    "' ");
        }
        StringBuffer sql = new StringBuffer("select  *\n" +
                "                from (select row_number()\n" +
                "                over(order by produce_plan_date desc) as rownumber,*\n" +
                "                from produce_pcb_task " +
                wheresql.toString() +
                ") temp_row ");
        /*if(pcbTaskCode!=null&&!"".equals(pcbTaskCode)){
            wheresql.append(" and pcb_task_code = '" +
                    pcbTaskCode +
                    "' ");
        }
        if(pcbId!=null&&!"".equals(pcbId)){
            wheresql.append(" and pcb_id = '" +
                    pcbId +
                    "' ");
        }
        if(pcbName!=null&&!"".equals(pcbName)){
            wheresql.append(" and pcb_name = '" +
                    pcbName +
                    "' ");
        }*/
        //sql.append(wheresql);
            List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
        sql.append(" where rownumber between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) +
                "");

        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        //同步领料单
        for(Map<String,Object>  map: mapList){
            String pcbTaskCode = (String)map.get("pcb_task_code");
            String pcbPlateId = (String)map.get("pcb_plate_id");
            if(pcbPlateId!=null&&!"".equals(pcbPlateId)){
                continue;
            }
            Long id = (Long)map.get("id");
            PcbTask pcbTask2 = pcbTaskRepository.findById(id).get();
            ResultVo resultVo = getFeedingTaskFromERP(pcbTaskCode);
           /* String qtl = resultVo.getMsg();
            qtl = qtl==null||"".equals(qtl)?"0":qtl;*/
            String lightPlateno = (String)resultVo.getData();
            pcbTask2.setPcb_plate_id(lightPlateno);
            //pcbTask2.setQi_tao_lv(qtl);
            pcbTaskRepository.save(pcbTask2);
        }

        return ResultVoUtil.success("查询成功",mapList,count.size());


    }



    /*@Override
    public ResultVo findScheduling(PcbTaskReq pcbTaskReq) {

        *//*StringBuffer sql = new StringBuffer("select  *\n" +
                "                from (select row_number()\n" +
                "                over(order by produce_plan_date desc) as rownumber,*\n" +
                "                from produce_pcb_task) temp_row ");*//*
        Integer page = pcbTaskReq.getPage(); //当前页
        Integer size = pcbTaskReq.getSize(); //每页条数
        String taskSheetCode = pcbTaskReq.getTaskSheetCode();  //生产批次
        String pcbId = pcbTaskReq.getPcbId(); //规格型号
        String pcbName = pcbTaskReq.getPcbName(); //物料名称

        if(pcbTaskReq.getPage()==null||pcbTaskReq.getSize()==null){
            page = pcbTaskReq.getPage();
            size = pcbTaskReq.getSize();
        }

        if (page == 1){
            StringBuffer sql = new StringBuffer("SELECT TOP " + size +
                    " * FROM  produce_pcb_task ");


            StringBuffer wheresql = new StringBuffer(" where 1=1 ");
            if(taskSheetCode!=null&&!"".equals(taskSheetCode)){
                wheresql.append(" and task_sheet_code  like '" +
                        "%" + taskSheetCode + "%" +
                        "' ");
            }
            if(pcbId!=null&&!"".equals(pcbId)){
                wheresql.append(" and pcb_id like '" +
                        "%" + pcbId + "%" +
                        "' ");
            }
            if(pcbName!=null&&!"".equals(pcbName)){
                wheresql.append(" and pcb_name like '" +
                        "%" + pcbName + "%" +
                        "' ");
            }

            sql.append(wheresql);
            sql.append(" order by id");
            System.out.println(sql);
            List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
            return ResultVoUtil.success("查询成功",mapList,count.size());
        } else {
            StringBuffer sql = new StringBuffer("SELECT TOP " + size +
                    " * FROM  produce_pcb_task ");

            StringBuffer wheresql = new StringBuffer(" where 1=1 ");
            if(taskSheetCode!=null&&!"".equals(taskSheetCode)){
                wheresql.append(" and task_sheet_code  like '" +
                        "%" + taskSheetCode + "%" +
                        "' ");
            }
            if(pcbId!=null&&!"".equals(pcbId)){
                wheresql.append(" and pcb_id like '" +
                        "%" + pcbId + "%" +
                        "' ");
            }
            if(pcbName!=null&&!"".equals(pcbName)){
                wheresql.append(" and pcb_name like '" +
                        "%" + pcbName + "%" +
                        "' ");
            }

            sql.append(wheresql);

            List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
            sql.append(" and id > (select max(id) from (select top " +
                     (page - 1 ) * size  +
                    "id from produce_pcb_task order by  id ) as temp) ");
            sql.append( " order by id");
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
            return ResultVoUtil.success("查询成功",mapList,count.size());
            }

    }*/


    /*@Override
    public ResultVo findScheduling(PcbTaskReq pcbTaskReq) {
//        StringBuffer sql = new StringBuffer("select * from produce_pcb_task ");
        Integer page = pcbTaskReq.getPage(); //当前页
        Integer size = pcbTaskReq.getSize(); //每页条数
        String pcbTaskCode = pcbTaskReq.getPcbTaskCode();  //任务号
        String pcbId = pcbTaskReq.getPcbId(); //规格型号
        String pcbName = pcbTaskReq.getPcbName(); //物料名称
        *//*if(pcbTaskReq.getPage()==null||pcbTaskReq.getSize()==null){
            page = pcbTaskReq.getPage();
            size = pcbTaskReq.getSize();
        }*//*

        if(pcbTaskCode == null && pcbId == null && pcbName == null &&
                "".equals(pcbTaskCode) && "".equals(pcbId) && "".equals(pcbName)){
            StringBuffer sql = new StringBuffer("select  *\n" +
                    "                from (select row_number()\n" +
                    "                        over(order by produce_plan_date desc) as rownumber,*\n" +
                    "                from produce_pcb_task) temp_row ");
            List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
            //StringBuffer wheresql = new StringBuffer(" where 1=1 ");

            //sql.append(wheresql);
            sql.append(" where rownumber between " +
                    ((page-1)*size+1) +
                    " and " +
                    (page*size) +
                    "");
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
            return ResultVoUtil.success("查询成功",mapList,count.size());
        } else {
            StringBuffer wheresql = new StringBuffer(" where 1=1 ");
            if(pcbTaskCode!=null&&!"".equals(pcbTaskCode)){
                wheresql.append(" and pcb_task_code = '" +
                        pcbTaskCode +
                        "' ");
            }
            if(pcbId!=null&&!"".equals(pcbId)){
                wheresql.append(" and pcb_id = '" +
                        pcbId +
                        "' ");
            }
            if(pcbName!=null&&!"".equals(pcbName)){
                wheresql.append(" and pcb_name = '" +
                        pcbName +
                        "' ");
            }
            *//*StringBuffer sql = new StringBuffer("select  top 1 *\n" +
                    "from produce_pcb_task"+ wheresql);*//*
            StringBuffer sql = new StringBuffer("select top "+(page*size)+" *\n" +
                    "from produce_pcb_task  "+ wheresql +" \n" +
                    "and id not in (SELECT top "+((page-1)*size)+" id \n" +
                    "from produce_pcb_task "+ wheresql +")  \n" +
                    "order by id");

            List<Map<String,Object>> count = jdbcTemplate.queryForList(sql.toString());
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
            return ResultVoUtil.success("查询成功",mapList,count.size());
        }
    }*/

    @Override
    public ResultVo findFeedingTask(PcbTaskReq pcbTaskReq) {
        List<FeedingTask> list = feedingTaskRepository.findByFeeding_task_code(pcbTaskReq.getFeedindTaskCode());

        return ResultVoUtil.success(list);
    }



    @Override
    public  Map<String,Object> scanCountPlate(PcbTaskReq pcbTaskReq) {
        Map<String,Object> map = new HashMap<>();

        for(PcbTaskReq req : pcbTaskReq.getData()){
            ScannerProcessTask scannerProcessTask = scannerProcessTaskRepository.findBydevicesort(Integer.parseInt(req.getDeviceCode()));

            //Device device = deviceRepository.fingDeviceBySort(req.getDeviceCode());
            ProcessTask processTask = processTaskRepository.findByProcessTaskCode(scannerProcessTask.getProcessTaskCode());
            if(processTask==null){
                map.put("result","200");
                map.put("timeStamp",pcbTaskReq.getTimeStamp());
                map.put("msg",req.getDeviceCode()+" 该机台没有进行中的任务！");
                map.put("url","scanCountPlate");
                return map;
            }
            ProcessTaskRealPlateSort plateSort  = processTaskRealPlateSortRepository.findByProcess_task_codeAndPlate_no(processTask.getProcess_task_code(), req.getPlateNo());
            if(req.getDeviceCode().equals("0")){
                //为第一个扫码枪记录
                if(plateSort==null){
                    plateSort = new ProcessTaskRealPlateSort();
                    plateSort.setPlate_no(req.getPlateNo());
                    plateSort.setProcess_task_code(processTask.getProcess_task_code());
                    plateSort.setPlate_no(req.getPlateNo());
                    plateSort.setRecord_start_time(new Date());
                    plateSort.setStatus(StatusEnum.OK.getCode());
                    processTaskRealPlateSortRepository.save(plateSort);
                }else {
                    map.put("result","200");
                    map.put("timeStamp",pcbTaskReq.getTimeStamp());
                    map.put("msg",req.getDeviceCode()+" 第一个扫码枪已扫！");
                    map.put("url","scanCountPlate");
                    return map;
                }
            }else {
                PcbTaskPlateNo pcbTaskPlateNo = pcbTaskPlateNoRepository.findByPlate_no(req.getPlateNo(),processTask.getProcess_task_code());
                if(pcbTaskPlateNo!=null){
                    pcbTaskPlateNo.setIs_count("1");
                    pcbTaskPlateNo.setUpdate_time(new Date());
                    pcbTaskPlateNoRepository.save(pcbTaskPlateNo);
                }
                if(plateSort!=null&&plateSort.getRecord_end_time()==null){
                    plateSort.setRecord_end_time(new Date());
                    processTaskRealPlateSortRepository.save(plateSort);

                }else if(plateSort!=null&&plateSort.getRecord_end_time()!=null) {
                    map.put("result","200");
                    map.put("timeStamp",pcbTaskReq.getTimeStamp());
                    map.put("msg",req.getDeviceCode()+" 该板编号已完成！");
                    map.put("url","scanCountPlate");
                    return map;
                }
            }

        }
        map.put("result","200");
        map.put("timeStamp",pcbTaskReq.getTimeStamp());
        map.put("msg","扫描成功！");
        map.put("url","scanCountPlate");

        return map;

    }

    @Override
    public  Map<String,Object> deviceProduceAmount(PcbTaskReq pcbTaskReq) {
        Date today = new Date();
        List<PTDeviceResp> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        Set<String> deviceSet = new HashSet<>();
        for(PcbTaskReq req : pcbTaskReq.getData()){
            PTDeviceResp resp = new PTDeviceResp();
            Device device = deviceRepository.fingDeviceBySort(req.getDeviceCode());
            //加入设备状态记录表
            //step1:找到设备最后一条没有结束时间的记录
//           DeviceStatusRecord deviceStatusRecord = deviceStatusRecordRepository.findByDevice_code(device.getDevice_code());
            List<DeviceStatusRecord> recordList = deviceStatusRecordRepository.findByDevice_codeLastOne(device.getDevice_code());
            DeviceStatusRecord deviceStatusRecord = (recordList==null||recordList.size()==0)?null:recordList.get(0);
            if(deviceStatusRecord==null){
                DeviceStatusRecord newRecord = new DeviceStatusRecord();
                newRecord.setContinue_time(0);
                newRecord.setStart_time(today);
                newRecord.setEnd_time(today);
                newRecord.setDevice_code(device.getDevice_code());
                newRecord.setDevice_name(device.getDevice_name());
                newRecord.setDevice_status(req.getStatus());
                deviceStatusRecordRepository.save(newRecord);
            }else {
                //step2:状态相同则重写结束时间与持续时间
                if(req.getStatus().equals(deviceStatusRecord.getDevice_status())){
                    deviceStatusRecord.setEnd_time(today);
                    Long cha = (today.getTime()-deviceStatusRecord.getStart_time().getTime())/1000;
                    deviceStatusRecord.setContinue_time(Integer.parseInt(cha+""));
                    deviceStatusRecordRepository.save(deviceStatusRecord);
                }else {
                    //step3:状态不同新增一条
                    //新增
                    DeviceStatusRecord newRecord = new DeviceStatusRecord();
                    newRecord.setContinue_time(0);
                    newRecord.setStart_time(today);
                    newRecord.setEnd_time(today);
                    newRecord.setDevice_code(device.getDevice_code());
                    newRecord.setDevice_name(device.getDevice_name());
                    newRecord.setDevice_status(req.getStatus());
                    deviceStatusRecordRepository.save(newRecord);
                }
            }

            String reCount = device.getRe_count();
            //是，记录数据返回清零标志
            resp.setReCount(reCount);
            //这里直接传完清零信号后改状态还是等下一次调用改清零状态
            /*if("1".equals(reCount)){
                device.setRe_count("0");
            }*/
            if(!req.getReCounttimeStamp().equals(device.getReCounttimeStamp())){
                device.setRe_count("0");
                resp.setReCount("0");
                device.setReCounttimeStamp(req.getReCounttimeStamp());
            }
            //记录设备启停状态
            device.setDevice_status(req.getStatus());
            deviceRepository.save(device);


            ProcessTask processTask = processTaskRepository.findProducingByDevice_code("%"+device.getDevice_code()+"%");

            //查无生产中的单怎么办
            if(processTask==null){

            }else {
                Process process = processRepository.findByProcessName(processTask.getProcess_name());
                set.add(processTask.getProcess_task_code());
                if(process.getCount_type()==0){
                    if(processTask.getMultiple()!=null){
                        req.setAmount(req.getAmount()*processTask.getMultiple());
                    }
                    ProcessTaskDevice processTaskDevice = processTaskDeviceRepository.findByPTCodeDeviceCode(device.getDevice_code(),processTask.getProcess_task_code());
                    if(processTaskDevice!=null){
                        processTaskDevice.setTd_status(req.getStatus());
                        if(req.getAmount()+processTaskDevice.getLast_amount()==processTaskDevice.getAmount()+1){
                            deviceSet.add(device.getDevice_code());
                        }
                        processTaskDevice.setAmount(req.getAmount()+processTaskDevice.getLast_amount());
                        processTaskDevice.setTime_stamp(pcbTaskReq.getTimeStamp());
                        processTaskDeviceRepository.save(processTaskDevice);
                        String deviceCodes = processTask.getDevice_code();
                        String  bigone = findDeviceBigIdCode(deviceCodes);
                        if(bigone.equals(device.getDevice_code())){
                            //工序最后机台数量计入工序任务
                            processTask.setAmount_completed(processTaskDevice.getAmount());
                            processTaskRepository.save(processTask);
                        }

                        //查找工序任务详情
                        String todaystr = DateUtil.date2String(today,"");
                        List<ProcessTaskDetail> detailList = processTaskDetailRepositoty.findByProcess_task_code(processTask.getProcess_task_code());
                        Integer detailSumCount = 0;
                        ProcessTaskDetail currentDetail = null;
                        for(ProcessTaskDetail detail :detailList){
                            int flag = DateUtil.compareDate(detail.getPlan_day_time(),today);
                            if(flag==-1){
                                detailSumCount = detailSumCount + detail.getFinish_count();
                            }
                            if(flag==0){
                                currentDetail = detail;
                            }
                        }
                        //当当天没有日计划详情则新增
                        if(currentDetail==null){
                            currentDetail = new ProcessTaskDetail();
                            currentDetail.setPlan_day_time(today);
                            currentDetail.setPlan_count(0);
                            currentDetail.setFinish_count(0);
                            currentDetail.setStatus(StatusEnum.OK.getCode());
                            currentDetail.setProcess_task_code(processTask.getProcess_task_code());
                            currentDetail.setDetail_type("系统分配");
                            currentDetail.setProcess_name(processTask.getProcess_name());
                            currentDetail.setUser_name("");
                        }else {
                            currentDetail.setFinish_count(processTask.getAmount_completed()-detailSumCount);
                        }
                        processTaskDetailRepositoty.save(currentDetail);
                    }
                }

            }
            resp.setDeviceCode(req.getDeviceCode());
            list.add(resp);
        }


        for(String taskcode:set){
            List<ProcessTaskDevice> taskDevicelList = processTaskDeviceRepository.findByPTCode(taskcode);
            for(int i=0;i<taskDevicelList.size();i++){
                ProcessTaskDevice taskDevice = taskDevicelList.get(i);
                ProcessTaskDeviceTheoryTime deviceTheoryTime = processTaskDeviceTheoryTimeRepository.findByProcess_task_codeAndDevice_code(taskcode, taskDevice.getDevice_code());
                if(deviceTheoryTime==null){
                    ProcessTask p = processTaskRepository.findByProcessTaskCode(taskcode);
                    deviceTheoryTime = new ProcessTaskDeviceTheoryTime();
                    deviceTheoryTime.setAmount(0);
                    deviceTheoryTime.setCreateTime(today);
                    deviceTheoryTime.setDeviceCode(taskDevice.getDevice_code());
                    deviceTheoryTime.setPcbCode(p.getPcb_code());
                    deviceTheoryTime.setProcessTaskCode(taskcode);
                    deviceTheoryTime.setUtilizationRate("0");
                    deviceTheoryTime.setWorkTime(BigDecimal.ZERO);
                    deviceTheoryTime.setTheoryTime(new BigDecimal(2.5));
                    processTaskDeviceTheoryTimeRepository.save(deviceTheoryTime);

                }
                //if(taskDevice.getAmount()== deviceTheoryTime.getAmount()+1){
                if(deviceSet.contains(taskDevice.getDevice_code())){
                    if(deviceTheoryTime.getStartTime()!=null){
                        BigDecimal workTime = (new BigDecimal((today.getTime() - deviceTheoryTime.getStartTime().getTime()))).divide(new BigDecimal((1000*60)),2,BigDecimal.ROUND_HALF_UP);
                        if(workTime.compareTo(new BigDecimal(60))<0){
                            deviceTheoryTime.setWorkTime(deviceTheoryTime.getWorkTime().add(workTime.setScale(2, BigDecimal.ROUND_HALF_UP)) );
                            deviceTheoryTime.setStartTime(today);
                            deviceTheoryTime.setAmount(deviceTheoryTime.getAmount()+1);
                            processTaskDeviceTheoryTimeRepository.save(deviceTheoryTime);
                        }
                    }
                    deviceTheoryTime.setStartTime(null);
                    processTaskDeviceTheoryTimeRepository.save(deviceTheoryTime);
                    if(i!=taskDevicelList.size()-1){
                        String nextDeviceCode = taskDevicelList.get(i+1).getDevice_code();
                        ProcessTaskDeviceTheoryTime next = processTaskDeviceTheoryTimeRepository.findByProcess_task_codeAndDevice_code(taskcode, nextDeviceCode);
                        if(deviceTheoryTime.getAmount()==next.getAmount()+1){
                            next.setStartTime(today);
                            processTaskDeviceTheoryTimeRepository.save(next);
                        }
                    }
                    if(i!=0){
                        String lastDeviceCode = taskDevicelList.get(i-1).getDevice_code();
                        ProcessTaskDeviceTheoryTime last = processTaskDeviceTheoryTimeRepository.findByProcess_task_codeAndDevice_code(taskcode, lastDeviceCode);
                        if(deviceTheoryTime.getAmount()<last.getAmount()){
                            deviceTheoryTime.setStartTime(today);
                            processTaskDeviceTheoryTimeRepository.save(deviceTheoryTime);
                        }
                    }else {
                        deviceTheoryTime.setStartTime(today);
                        processTaskDeviceTheoryTimeRepository.save(deviceTheoryTime);
                    }
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result","200");
        map.put("data",list);
        map.put("timeStamp",pcbTaskReq.getTimeStamp());
        map.put("msg","");
        map.put("url","deviceProduceAmount");
        return map;
    }


    @Override
    public Map<String, Object> checkPositionSort(PcbTaskReq pcbTaskReq) {
        List<Map<String,String>> mapList = new ArrayList<>();
        for(PcbTaskReq req : pcbTaskReq.getData()){
            Device device = deviceRepository.fingDeviceBySort(req.getDeviceCode());
            List<PcbTaskPositionRecordDetail> list = pcbTaskPositionRecordDetailRepositoty.findByDevice_code(device.getDevice_code());
            Map<String,String> map = new HashMap<>();
//            map.put("deviceCode",req.getDeviceCode());
//            map.put("positionSort","");
            if(list!=null&&list.size()!=0){
                PcbTaskPositionRecordDetail detail = list.get(0);
                map.put("deviceCode",req.getDeviceCode());
                map.put("positionSort",detail.getPosition());
                mapList.add(map);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result","200");
        map.put("data",mapList);
        map.put("timeStamp",pcbTaskReq.getTimeStamp());
        map.put("msg","");
        map.put("url","checkPositionSort");
        return map;
    }


    @Override
    public Map<String, Object> noticePutinStatus(PcbTaskReq pcbTaskReq) {
        for(PcbTaskReq req : pcbTaskReq.getData()){
            Device device = deviceRepository.fingDeviceBySort(req.getDeviceCode());
            List<PcbTaskPositionRecordDetail> list = pcbTaskPositionRecordDetailRepositoty.findByDevice_code(device.getDevice_code());
            if(list!=null&&list.size()!=0) {
                PcbTaskPositionRecordDetail detail = list.get(0);
                detail.setInstall_status("2");
                pcbTaskPositionRecordDetailRepositoty.save(detail);
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result","200");
        map.put("timeStamp",pcbTaskReq.getTimeStamp());
        map.put("msg","");
        map.put("url","noticePutinStatus");
        return map;

    }

    public String  findDeviceBigIdCode(String deviceCodes){
        String devicecode [] = deviceCodes.split(",");
        Integer bigInteger = 0;
        String bigCode = "";
        for(String code : devicecode){
            Device device = deviceRepository.findbyDeviceCode(code);
            if(device!=null&&device.getDevice_sort()>bigInteger){
                bigInteger = device.getDevice_sort();
                bigCode = code;
            }
        }
        return bigCode;
    }

    @Override
    public ResultVo findProcessTaskByDevice(PcbTaskReq pcbTaskReq) {
        Date today = new Date();
        String todaystr = DateUtil.date2String(today,"");
        StringBuffer sql = new StringBuffer("SELECT\n" +
                "\tt1.*,\n" +
                "\tt2.pcb_id,t1.multiple,\n" +
                "\tt2.feeding_task_code,\n" +
                "\tt2.model_name,\n" +
                "\tt2.batch_id ,t2.pcb_plate_id,\n" +
                "\tt3.plan_count detail_plan_count,\n" +
                "\tt3.finish_count detail_finish_count,t4.device_detail_status,\n" +
                "\tt4.finish_count detail_device_finish_count,t4.plan_count detail_device_plan_count \n" +
                "FROM\n" +
                "\tproduce_process_task t1\n" +
                "\tLEFT JOIN produce_process_task_detail t3 ON t1.process_task_code = t3.process_task_code \n" +
                "\tAND CONVERT ( VARCHAR ( 100 ), t3.plan_day_time, 23 ) = '" +
                todaystr +
                "'\n" +
                "\tLEFT JOIN produce_pcb_task t2 ON t2.pcb_task_code = t1.pcb_task_code " +
                " LEFT JOIN produce_process_task_detail_device t4 on t4.process_task_code = t1.process_task_code AND\n" +
                "\tCONVERT ( VARCHAR ( 100 ), t4.plan_day_time, 23 ) = '" +
                todaystr +
                "' AND t4.device_code = '" +
                pcbTaskReq.getDeviceCode() +
                "'" +
                "\n" +
                "WHERE\n" +
                "\tt1.device_code LIKE '%" +
                pcbTaskReq.getDeviceCode() +
                "%' \n" +
                "\tAND ( t1.process_task_status = '生产中' OR t1.process_task_status LIKE '%已下达%' OR t1.process_task_status LIKE '%暂停%' OR t1.process_task_status LIKE '%进行中%' ) \n" +
                "ORDER BY\n" +
                "\tt2.priority DESC,\n" +
                "\tt1.plan_start_time");
        List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());

        return ResultVoUtil.success(mapList);
    }
    @Override
    @Transactional
    public ResultVo modifyProcessTaskStatus2(PcbTaskReq pcbTaskReq) {
      /*  ProcessTask lastProcessTask = processTaskRepository.findById(pcbTaskReq.getLastProcassTaskId()).get();
        if(!"暂停".equals(lastProcessTask.getProcess_task_status())){
            return ResultVoUtil.error("切单前请先暂停当前工序任务");
        }*/
        ProcessTask processTask = processTaskRepository.findById(pcbTaskReq.getProcessTaskId()).get();
        if("已完成".equals(processTask.getProcess_task_status())){
            return ResultVoUtil.error("该工序任务已完成");
        }
        ProcessTaskStatusHistory history = null;
        if(pcbTaskReq.getCountType()==1){
            history = processTaskStatusHistoryRepository.findByProcessTaskCodeAndDeviceLastRecord(processTask.getProcess_task_code(),pcbTaskReq.getDeviceCode());
        }else {
            history = processTaskStatusHistoryRepository.findByProcessTaskCodeLastRecord(processTask.getProcess_task_code());
        }
        Date today = new Date();
        String dayStr = DateUtil.date2String(today,"");
        if(history==null){
            ProcessTaskStatusHistory newhistory = new ProcessTaskStatusHistory();
            newhistory.setContinue_time(0);
            newhistory.setStart_time(today);
            newhistory.setDevice_code(pcbTaskReq.getDeviceCode());
            if(pcbTaskReq.getCountType()==1){
                newhistory.setDevice_code(pcbTaskReq.getDeviceCode());
            }
            newhistory.setDevice_name(processTask.getDevice_name());
            newhistory.setProcess_task_code(processTask.getProcess_task_code());
            newhistory.setProcess_task_status(pcbTaskReq.getProcessTaskStatus());
            newhistory.setProcess_name(processTask.getProcess_name());
            if("已完成".equals(pcbTaskReq.getProcessTaskStatus())){
                newhistory.setEnd_time(today);
            }
            processTaskStatusHistoryRepository.save(newhistory);
        }else {
            //step2:状态相同则跳过
            if(pcbTaskReq.getProcessTaskStatus().equals(history.getProcess_task_status())){

            }else {
                //step3:状态不同结束上一条并计算持续时间，新增一条
                history.setEnd_time(today);
                Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                history.setContinue_time(Integer.parseInt(cha+""));
                processTaskStatusHistoryRepository.save(history);
                //新增
                ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                newRecord.setContinue_time(0);
                newRecord.setStart_time(today);
                newRecord.setDevice_code(pcbTaskReq.getDeviceCode());
                if(pcbTaskReq.getCountType()==1){
                    newRecord.setDevice_code(pcbTaskReq.getDeviceCode());
                }
                newRecord.setDevice_name(processTask.getDevice_name());
                newRecord.setProcess_task_status(pcbTaskReq.getProcessTaskStatus());
                newRecord.setProcess_name(processTask.getProcess_name());
                newRecord.setProcess_task_code(processTask.getProcess_task_code());
                if("已完成".equals(pcbTaskReq.getProcessTaskStatus())){
                    newRecord.setEnd_time(today);
                }
                processTaskStatusHistoryRepository.save(newRecord);
            }
        }
        User user = ShiroUtil.getSubject();
       /* User user = new User();
        user.setId(1L);*/
        //if(!"备料".equals(processTask.getProcess_name())){
        //List<ProcessTask> list = processTaskRepository.findByDevice_code("%"+pcbTaskReq.getDeviceCode()+"%");

        //开始工序计划 进行中
        //将未分配的上机员工转移到这里
        if("进行中".equals(pcbTaskReq.getProcessTaskStatus())||"".equals(pcbTaskReq.getProcessTaskStatus())){

            String deviceCodes[] = processTask.getDevice_code().split(",");
            if(pcbTaskReq.getCountType()==1){
            }else {
                for(int i = 0;i<deviceCodes.length;i++){
                    List<ProcessTask> listByDevice_code = processTaskRepository.findProducingListByDevice_code("%"+deviceCodes[i]+"%");
                    List<ProcessTask> stopList = listByDevice_code.stream().filter(d-> "暂停".equals(d.getProcess_task_status())).collect(Collectors.toList());
                    if(stopList!=null&&stopList.size()!=0){
                        stopList.forEach(p->p.setIs_now_flag(""));
                        processTaskRepository.saveAll(stopList);
                        //continue;
                    }
                    listByDevice_code.removeAll(stopList);
                    if(listByDevice_code!=null&&listByDevice_code.size()!=0){
                        if(!deviceCodes[i].equals(pcbTaskReq.getDeviceCode())){
                            return ResultVoUtil.error("欲启动的工序任务中该"+deviceCodes[i]+"机台有其他生产中的任务");
                        }
                    }
                }
            }
            ProcessTask lastProcessTask = null;
            if(pcbTaskReq.getLastProcessTaskId()!=null){
                lastProcessTask = processTaskRepository.findById(pcbTaskReq.getLastProcessTaskId()).get();
            }
            if(pcbTaskReq.getCountType()==1){
                //移除旧的工序任务flag里的设备
                if(lastProcessTask!=null){
                    String lastflag = lastProcessTask.getIs_now_flag();
                    List<String> lastresult = new ArrayList<>();
                    if(lastflag!=null&&!lastflag.equals("")){
                        lastresult = new ArrayList(Arrays.asList(lastflag.split(",")));
                    }
                    lastresult.remove(pcbTaskReq.getDeviceCode());
                    String laststr = Joiner.on(",").join(lastresult);
                    lastProcessTask.setIs_now_flag(laststr);
                }

                //增加切单的flag里的设备
                String flag = processTask.getIs_now_flag();
                List<String> result = new ArrayList<>();
                if(flag!=null&&!flag.equals("")){
                    result = new ArrayList(Arrays.asList(flag.split(",")));
                }
                if(!result.contains(pcbTaskReq.getDeviceCode())){
                    result.add(pcbTaskReq.getDeviceCode());
                }
                String str = Joiner.on(",").join(result);
                processTask.setIs_now_flag(str);

                ProcessTaskDetailDevice detailDevice = processTaskDetailDeviceRepository.findByTaskCodeAndDayTimeAndDeviceCode1(processTask.getProcess_task_code(), dayStr, pcbTaskReq.getDeviceCode());
                if(detailDevice!=null){
                    detailDevice.setDevice_detail_status("进行中");
                    processTaskDetailDeviceRepository.save(detailDevice);
                }else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultVoUtil.error("目标工序计划没有当天日计划！");
                }
            }else {
                if(lastProcessTask!=null){
                    lastProcessTask.setIs_now_flag("");
                }
                processTask.setIs_now_flag(processTask.getDevice_code());
            }
            String date = DateUtil.date2String(new Date(),"");
            ProcessTaskDetail detail = processTaskDetailRepositoty.findAllByProcess_task_codeAndPlan_day_time(processTask.getProcess_task_code(),date);
            if(detail==null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultVoUtil.error("目标工序计划没有当天日计划！");
            }

            processTask.setProcess_task_status("进行中");
            if(lastProcessTask!=null){
                processTaskRepository.save(lastProcessTask);
            }
            processTaskRepository.save(processTask);
            if(processTask.getStart_time()==null){
                processTask.setStart_time(new Date());
            }


            UserDeviceHistory one = userDeviceHistoryRepository.findAllByProcessTaskDateDeviceUser(processTask.getProcess_task_code(),date,pcbTaskReq.getDeviceCode(),user.getId());
            if(one!=null){

            }else {

                if(processTask.getCount_type()==0){
                    String [] deviceArray = processTask.getDevice_code().split(",");
                    for(String devicecode : deviceArray){
                        UserDeviceHistory tow = userDeviceHistoryRepository.findOnlyUpTimeRecord(devicecode);
                        if(tow!=null){
                            tow.setProcess_task_code(processTask.getProcess_task_code());
                            userDeviceHistoryRepository.save(tow);
                        }

                    }

                }else {
                    UserDeviceHistory tow = userDeviceHistoryRepository.findOnlyUpTimeRecord(pcbTaskReq.getDeviceCode());
                    if(tow!=null){
                        tow.setProcess_task_code(processTask.getProcess_task_code());
                        userDeviceHistoryRepository.save(tow);
                    }
                }

            }

        }
        //启动工序计划 生产中
        if("生产中".equals(pcbTaskReq.getProcessTaskStatus())){

            //Process process = processRepository.findByProcessName(processTask.getProcess_name());

            //工序计数方式为机台计数时
            if(pcbTaskReq.getCountType()==0){

                //DeviceTheoryTime deviceTheoryTime = deviceTheoryTimeRepository.findByDevice_codeAndPcb_code(pcbTaskReq.getDeviceCode(), processTask.getPcb_code());
                String a_or_b = "";
                if(processTask.getProcess_name().contains("贴片A")){
                    a_or_b = "A";
                }
                if(processTask.getProcess_name().contains("贴片B")){
                    a_or_b = "B";
                }
                DeviceTheoryTime deviceTheoryTime = deviceTheoryTimeRepository.findByDevice_codeAndPcb_codeAndAB(pcbTaskReq.getDeviceCode(), processTask.getPcb_code(),a_or_b);
                if(deviceTheoryTime==null){
                    deviceTheoryTime = new DeviceTheoryTime();
                    deviceTheoryTime.setTheory_time(new BigDecimal(2.5));
                    deviceTheoryTime.setPcb_code(processTask.getPcb_code());
                    deviceTheoryTime.setDevice_code(pcbTaskReq.getDeviceCode());
                    deviceTheoryTime.setA_or_b(a_or_b);
                    deviceTheoryTimeRepository.save(deviceTheoryTime);
                }
                //贴片线工序判断生成工时记录表
                ProcessTaskDeviceTheoryTime processTaskDeviceTheoryTime = processTaskDeviceTheoryTimeRepository.findByProcess_task_codeAndDevice_code(processTask.getProcess_task_code(), pcbTaskReq.getDeviceCode());
                if(processTaskDeviceTheoryTime==null){
                    //如果为空，每个设备都要生成记录
                    String deviceCodes[] = processTask.getDevice_code().split(",");
                    List<ProcessTaskDeviceTheoryTime> theoryTimeRecord  = new ArrayList<>();
                    for(int i = 0 ;i<deviceCodes.length;i++){
                        ProcessTaskDeviceTheoryTime theoryTime = new ProcessTaskDeviceTheoryTime();
                        theoryTime.setAmount(0);
                        theoryTime.setCreateTime(today);
                        theoryTime.setDeviceCode(deviceCodes[i]);
                        theoryTime.setPcbCode(processTask.getPcb_code());
                        theoryTime.setProcessTaskCode(processTask.getProcess_task_code());
                        theoryTime.setUtilizationRate("0");
                        theoryTime.setWorkTime(BigDecimal.ZERO);
                        theoryTime.setTheoryTime(deviceTheoryTime.getTheory_time());
                        theoryTimeRecord.add(theoryTime);
                    }
                    processTaskDeviceTheoryTimeRepository.saveAll(theoryTimeRecord);


                }


                //同时将扫码枪绑定工序任务重新绑定
                List<ScannerProcessTask> scannerProcessTasks = scannerProcessTaskRepository.findAll();
                scannerProcessTasks.forEach(t->{
                    t.setProcessTaskCode(processTask.getProcess_task_code());
                });
                scannerProcessTaskRepository.saveAll(scannerProcessTasks);

            }else {
                ProcessTaskDetailDevice detailDevice = processTaskDetailDeviceRepository.findByTaskCodeAndDayTimeAndDeviceCode1(processTask.getProcess_task_code(), dayStr, pcbTaskReq.getDeviceCode());
                if(detailDevice==null){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return  ResultVoUtil.error("该工单没有当天工站日计划");
                }
                detailDevice.setDevice_detail_status("生产中");
                processTaskDetailDeviceRepository.save(detailDevice);
            }


            processTask.setProcess_task_status("生产中");
            processTaskRepository.save(processTask);

            //所在工序计划的所有机台一同清零
            //重新计数记录在设备处
            List<ProcessTaskDevice> prl = processTaskDeviceRepository.findByPTCode(processTask.getProcess_task_code());
            for(ProcessTaskDevice de:prl){
                Device device = deviceRepository.findbyDeviceCode(de.getDevice_code());
                device.setRe_count("1");
                deviceRepository.save(device);
                de.setAmount(processTask.getAmount_completed());
                de.setLast_amount(de.getAmount());
                processTaskDeviceRepository.save(de);
            }
        }
        //暂停工序计划 暂停
        if("暂停".equals(pcbTaskReq.getProcessTaskStatus())){
            if(pcbTaskReq.getCountType()==1){
                ProcessTaskDetailDevice detailDevice = processTaskDetailDeviceRepository.findByTaskCodeAndDayTimeAndDeviceCode1(processTask.getProcess_task_code(), dayStr, pcbTaskReq.getDeviceCode());
                if(detailDevice!=null){
                    detailDevice.setDevice_detail_status("暂停");
                    processTaskDetailDeviceRepository.save(detailDevice);

                }
            }else {
                processTask.setProcess_task_status("暂停");
                processTaskRepository.save(processTask);
            }
            //所在工序计划的所有机台一同清零
            //重新计数记录在设备处
            List<ProcessTaskDevice> prl = processTaskDeviceRepository.findByPTCode(processTask.getProcess_task_code());
            for(ProcessTaskDevice de:prl){
                Device device = deviceRepository.findbyDeviceCode(de.getDevice_code());
                device.setRe_count("1");
                deviceRepository.save(device);
                de.setLast_amount(de.getAmount());
                processTaskDeviceRepository.save(de);
            }

        }
        //结束工序计划 完成
        if("已完成".equals(pcbTaskReq.getProcessTaskStatus())){
            if(pcbTaskReq.getAmountCompleted()!=null){
                processTask.setAmount_completed(pcbTaskReq.getAmountCompleted());
            }
            Date finishTime = new Date();
            processTask.setFinish_time(finishTime);
            processTask.setIs_now_flag("");
               /* BigDecimal workTime = DateUtil.differTwoDate(finishTime,processTask.getStart_time());
                processTask.setWork_time(workTime);*/
            //重新计数
            List<ProcessTaskDevice> prl = processTaskDeviceRepository.findByPTCode(processTask.getProcess_task_code());
            for(ProcessTaskDevice de:prl){
                Device device = deviceRepository.findbyDeviceCode(de.getDevice_code());
                device.setRe_count("1");
                deviceRepository.save(device);
                de.setLast_amount(de.getAmount());
                processTaskDeviceRepository.save(de);
            }
        }
       /* }else {

        }*/
       // processTaskRepository.save(processTask);
        return ResultVoUtil.success("操作成功");
    }
    @Override
    public ResultVo modifyProcessTaskStatus(PcbTaskReq pcbTaskReq) {/*
      *//*  ProcessTask lastProcessTask = processTaskRepository.findById(pcbTaskReq.getLastProcassTaskId()).get();
        if(!"暂停".equals(lastProcessTask.getProcess_task_status())){
            return ResultVoUtil.error("切单前请先暂停当前工序任务");
        }*//*
        ProcessTask processTask = processTaskRepository.findById(pcbTaskReq.getProcessTaskId()).get();

        if("已完成".equals(processTask.getProcess_task_status())){
            return ResultVoUtil.error("该工序任务已完成");
        }
        ProcessTaskStatusHistory history = processTaskStatusHistoryRepository.findByProcessTaskCodeLastRecord(processTask.getProcess_task_code());
        Date today = new Date();
        if(history==null){
            ProcessTaskStatusHistory newhistory = new ProcessTaskStatusHistory();
            newhistory.setContinue_time(0);
            newhistory.setStart_time(today);
            newhistory.setDevice_code(processTask.getDevice_code());
            newhistory.setDevice_name(processTask.getDevice_name());
            newhistory.setProcess_task_code(processTask.getProcess_task_code());
            newhistory.setProcess_task_status(pcbTaskReq.getProcessTaskStatus());
            newhistory.setProcess_name(processTask.getProcess_name());
            if("已完成".equals(pcbTaskReq.getProcessTaskStatus())){
                newhistory.setEnd_time(today);
            }
            processTaskStatusHistoryRepository.save(newhistory);
        }else {
            //step2:状态相同则跳过
            if(pcbTaskReq.getProcessTaskStatus().equals(history.getProcess_task_status())){

            }else {
                //step3:状态不同结束上一条并计算持续时间，新增一条
                history.setEnd_time(today);
                Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                history.setContinue_time(Integer.parseInt(cha+""));
                processTaskStatusHistoryRepository.save(history);
                //新增
                ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                newRecord.setContinue_time(0);
                newRecord.setStart_time(today);
                newRecord.setDevice_code(processTask.getDevice_code());
                newRecord.setDevice_name(processTask.getDevice_name());
                newRecord.setProcess_task_status(pcbTaskReq.getProcessTaskStatus());
                newRecord.setProcess_name(processTask.getProcess_name());
                newRecord.setProcess_task_code(processTask.getProcess_task_code());
                if("已完成".equals(pcbTaskReq.getProcessTaskStatus())){
                    newRecord.setEnd_time(today);
                }
                processTaskStatusHistoryRepository.save(newRecord);
            }
        }
        User user = ShiroUtil.getSubject();
        //if(!"备料".equals(processTask.getProcess_name())){
            List<ProcessTask> list = processTaskRepository.findByDevice_code("%"+pcbTaskReq.getDeviceCode()+"%");

            //开始工序计划 进行中
            //将未分配的上机员工转移到这里
            if("进行中".equals(pcbTaskReq.getProcessTaskStatus())){
                list.forEach(p -> p.setIs_now_flag("0"));
                processTaskRepository.saveAll(list);
                processTask.setIs_now_flag("1");
                processTask.setStart_time(new Date());
                String date = DateUtil.date2String(new Date(),"");
                UserDeviceHistory one = userDeviceHistoryRepository.findAllByProcessTaskDateDeviceUser(processTask.getProcess_task_code(),date,pcbTaskReq.getDeviceCode(),user.getId());
                if(one!=null){
                }else {
                    UserDeviceHistory tow = userDeviceHistoryRepository.findOnlyUpTimeRecord(pcbTaskReq.getDeviceCode());
                    if(tow!=null){
                        tow.setProcess_task_code(processTask.getProcess_task_code());
                        userDeviceHistoryRepository.save(tow);
                    }
                }
               *//* ProcessTaskDevice now = processTaskDeviceRepository.findByPTCodeDeviceCode(pcbTaskReq.getDeviceCode(),processTask.getProcess_task_code());

                if(now !=null){

                }else {
                    ProcessTaskDevice no = processTaskDeviceRepository.findByPTCodeDeviceCode(pcbTaskReq.getDeviceCode(),"未分配");
                    now.setUser_ids(no.getUser_ids());
                    processTaskDeviceRepository.save(now);
                    no.setUser_ids("");
                    processTaskDeviceRepository.save(no);
                }*//*
            }
            //启动工序计划 生产中
            if("生产中".equals(pcbTaskReq.getProcessTaskStatus())){
                Process process = processRepository.findByProcessName(processTask.getProcess_name());
                //工序计数方式为机台计数时
                if(process.getCount_type()==0){
                    //同时将扫码枪绑定工序任务重新绑定
                    List<ScannerProcessTask> scannerProcessTasks = scannerProcessTaskRepository.findAll();
                    scannerProcessTasks.forEach(t->{
                        t.setProcessTaskCode(processTask.getProcess_task_code());
                    });
                    scannerProcessTaskRepository.saveAll(scannerProcessTasks);
                }


                String deviceCodes[] = processTask.getDevice_code().split(",");
                for(int i = 0;i<deviceCodes.length;i++){
                    List<ProcessTask> listByDevice_code = processTaskRepository.findProducingListByDevice_code("%"+deviceCodes[i]+"%");
                    if(listByDevice_code!=null&&listByDevice_code.size()!=0){
                        return ResultVoUtil.error("欲启动的工序任务中该"+deviceCodes[i]+"机台有其他生产中的任务");

                    }
                }

                //所在工序计划的所有机台一同清零
                //重新计数记录在设备处
                List<ProcessTaskDevice> prl = processTaskDeviceRepository.findByPTCode(processTask.getProcess_task_code());
                for(ProcessTaskDevice de:prl){
                    Device device = deviceRepository.findbyDeviceCode(de.getDevice_code());
                    device.setRe_count("1");
                    deviceRepository.save(device);
                    de.setLast_amount(de.getAmount());
                    processTaskDeviceRepository.save(de);
                }
            }
            //暂停工序计划 暂停
            if("暂停".equals(pcbTaskReq.getProcessTaskStatus())){

                //所在工序计划的所有机台一同清零
                //重新计数记录在设备处
                List<ProcessTaskDevice> prl = processTaskDeviceRepository.findByPTCode(processTask.getProcess_task_code());
                for(ProcessTaskDevice de:prl){
                    Device device = deviceRepository.findbyDeviceCode(de.getDevice_code());
                    device.setRe_count("1");
                    deviceRepository.save(device);
                    de.setLast_amount(de.getAmount());
                    processTaskDeviceRepository.save(de);
                }

            }
            //结束工序计划 完成
            if("已完成".equals(pcbTaskReq.getProcessTaskStatus())){
                if(pcbTaskReq.getAmountCompleted()!=null){
                    processTask.setAmount_completed(pcbTaskReq.getAmountCompleted());
                }
                Date finishTime = new Date();
                processTask.setFinish_time(finishTime);
                processTask.setIs_now_flag("0");
               *//* BigDecimal workTime = DateUtil.differTwoDate(finishTime,processTask.getStart_time());
                processTask.setWork_time(workTime);*//*
                //重新计数
                List<ProcessTaskDevice> prl = processTaskDeviceRepository.findByPTCode(processTask.getProcess_task_code());
                for(ProcessTaskDevice de:prl){
                    Device device = deviceRepository.findbyDeviceCode(de.getDevice_code());
                    device.setRe_count("1");
                    deviceRepository.save(device);
                    de.setLast_amount(de.getAmount());
                    processTaskDeviceRepository.save(de);
                }
            }
       *//* }else {

        }*//*
        processTask.setProcess_task_status(pcbTaskReq.getProcessTaskStatus());
        processTaskRepository.save(processTask);*/
        return ResultVoUtil.success("操作成功");
    }

    @Override
    @Transactional
    public ResultVo settlementDetailCount(PcbTaskReq pcbTaskReq) {
        //记录工序任务当天日计划数量，统计并反写入工序计划，低于总计划数量则暂停，否则结束，新增操作记录
        Date today = new Date();
        String todayStr = DateUtil.date2String(today,"");
        ProcessTask processTask = processTaskRepository.findById(pcbTaskReq.getProcessTaskId()).get();
        ProcessTaskDetail detail = processTaskDetailRepositoty.findAllByProcess_task_codeAndPlan_day_time(processTask.getProcess_task_code(), todayStr);
        detail.setFinish_count(pcbTaskReq.getAmountCompleted());
        boolean deviceDetailFinishFlag = false;
        if(processTask.getCount_type()==1){
            ProcessTaskDetailDevice detailDevice = processTaskDetailDeviceRepository.findByTaskCodeAndDayTimeAndDeviceCode1(processTask.getProcess_task_code(),todayStr,pcbTaskReq.getDeviceCode());
            detailDevice.setFinish_count(pcbTaskReq.getAmountCompleted());
            if(detailDevice.getFinish_count()>=detailDevice.getPlan_count()){
                deviceDetailFinishFlag = true;
                detailDevice.setDevice_detail_status("已完成");
                ProcessTaskStatusHistory history = processTaskStatusHistoryRepository.findByProcessTaskCodeAndDeviceLastRecord(processTask.getProcess_task_code(),pcbTaskReq.getDeviceCode());
                history.setEnd_time(today);
                Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                history.setContinue_time(Integer.parseInt(cha+""));
                processTaskStatusHistoryRepository.save(history);
                //新增
                ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                newRecord.setStart_time(today);
                newRecord.setContinue_time(0);
                newRecord.setDevice_code(pcbTaskReq.getDeviceCode());
                newRecord.setDevice_name(pcbTaskReq.getDeviceName());
                newRecord.setProcess_task_status("已完成");
                //newRecord.setDevice_name(processTask.getDevice_name());
                newRecord.setProcess_task_code(processTask.getProcess_task_code());
                newRecord.setProcess_name(processTask.getProcess_name());
                newRecord.setEnd_time(today);
                processTaskStatusHistoryRepository.save(newRecord);

            }
            processTaskDetailDeviceRepository.save(detailDevice);
            List<ProcessTaskDetailDevice> detailDeviceList = processTaskDetailDeviceRepository.findByTaskCodeAndDayTime(processTask.getProcess_task_code(),todayStr);
            Integer sumdevicecount = detailDeviceList.stream().mapToInt(ProcessTaskDetailDevice::getFinish_count).sum();
            detail.setFinish_count(sumdevicecount);
        }
        processTaskDetailRepositoty.save(detail);
        List<ProcessTaskDetail> detailList = processTaskDetailRepositoty.findByProcess_task_code(processTask.getProcess_task_code());
        //因为事务所以这里先去掉detail的完成数再加回来step1
        //detailList.remove(detail);
        Integer sumcount = detailList.stream().mapToInt(ProcessTaskDetail::getFinish_count).sum();
        //step2
       // sumcount = sumcount + detail.getFinish_count();
        processTask.setAmount_completed(sumcount);
         ProcessTaskStatusHistory history = null;
        if(processTask.getCount_type()==1){
            history = processTaskStatusHistoryRepository.findByProcessTaskCodeAndDeviceLastRecord(processTask.getProcess_task_code(),pcbTaskReq.getDeviceCode());
        }else {
            history = processTaskStatusHistoryRepository.findByProcessTaskCodeLastRecord(processTask.getProcess_task_code());
        }
        if(processTask.getAmount_completed()>=processTask.getPcb_quantity()){
            //新增结束工序计划操作记录
            processTask.setFinish_time(today);
            processTask.setIs_now_flag("");
            processTask.setProcess_task_status("已完成");
            //step3:状态不同结束上一条并计算持续时间，新增一条
            if(!deviceDetailFinishFlag){
                history.setEnd_time(today);
                Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                history.setContinue_time(Integer.parseInt(cha+""));
                processTaskStatusHistoryRepository.save(history);
                //新增
                ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                newRecord.setStart_time(today);
                newRecord.setContinue_time(0);
                newRecord.setDevice_code(pcbTaskReq.getDeviceCode());
                newRecord.setDevice_name(pcbTaskReq.getDeviceName());
                newRecord.setProcess_task_status("已完成");
                newRecord.setProcess_task_code(processTask.getProcess_task_code());
                newRecord.setProcess_name(processTask.getProcess_name());
                newRecord.setEnd_time(today);
                processTaskStatusHistoryRepository.save(newRecord);
            }
        }else {
            if(processTask.getCount_type()==1){

                String lastflag = processTask.getIs_now_flag();
                List<String> lastresult = new ArrayList<>();
                if(lastflag!=null&&!lastflag.equals("")){
                    lastresult = new ArrayList(Arrays.asList(lastflag.split(",")));
                }
                lastresult.remove(pcbTaskReq.getDeviceCode());
                String laststr = Joiner.on(",").join(lastresult);
                processTask.setIs_now_flag(laststr);

            }else {
                processTask.setIs_now_flag("");
            }

            //新增暂停工序计划操作记录
            //step2:状态相同则跳过
            if(history!=null&&"暂停".equals(history.getProcess_task_status())){

            }else {
                //step3:状态不同结束上一条并计算持续时间，新增一条
                if(!deviceDetailFinishFlag){
                    if (history!=null){
                        history.setEnd_time(today);
                        Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                        history.setContinue_time(Integer.parseInt(cha+""));
                        processTaskStatusHistoryRepository.save(history);
                    }
                    //新增
                    ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                    newRecord.setContinue_time(0);
                    newRecord.setStart_time(today);
                    newRecord.setDevice_code(pcbTaskReq.getDeviceCode());
                    newRecord.setDevice_name(pcbTaskReq.getDeviceName());
                    newRecord.setProcess_task_status("暂停");
                    newRecord.setProcess_name(processTask.getProcess_name());
                    newRecord.setProcess_task_code(processTask.getProcess_task_code());
                    //newRecord.setEnd_time(today);

                    processTaskStatusHistoryRepository.save(newRecord);
                }
                if(processTask.getCount_type()==0){
                    processTask.setProcess_task_status("暂停");
                }
            }

        }

        processTaskRepository.save(processTask);

        return ResultVoUtil.success("操作成功");
    }


    @Override
    @Transactional
    public ResultVo settlementDetailCountByList(List<PcbTaskReq> pcbTaskReqList) {
        //记录工序任务当天日计划数量，统计并反写入工序计划，低于总计划数量则暂停，否则结束，新增操作记录
        Date today = new Date();
        String todayStr = DateUtil.date2String(today,"");
        Integer amountSum = 0;
        BigDecimal workTimeSum = BigDecimal.ZERO;
        List<ProcessTask> processTaskList = new ArrayList<>();
        for(PcbTaskReq pcbTaskReq : pcbTaskReqList){
            ProcessTask processTask = processTaskRepository.findById(pcbTaskReq.getProcessTaskId()).get();
            ProcessTaskDetail detail = processTaskDetailRepositoty.findAllByProcess_task_codeAndPlan_day_time(processTask.getProcess_task_code(), todayStr);
            detail.setFinish_count(pcbTaskReq.getAmountCompleted());
            boolean deviceDetailFinishFlag = false;
            if(processTask.getCount_type()==1){
                ProcessTaskDetailDevice detailDevice = processTaskDetailDeviceRepository.findByTaskCodeAndDayTimeAndDeviceCode1(processTask.getProcess_task_code(),todayStr,pcbTaskReq.getDeviceCode());
                detailDevice.setFinish_count(pcbTaskReq.getAmountCompleted());
                if(detailDevice.getFinish_count()>=detailDevice.getPlan_count()){
                    deviceDetailFinishFlag = true;
                    detailDevice.setDevice_detail_status("已完成");
                    ProcessTaskStatusHistory history = processTaskStatusHistoryRepository.findByProcessTaskCodeAndDeviceLastRecord(processTask.getProcess_task_code(),pcbTaskReq.getDeviceCode());
                    if (history!= null){
                        history.setEnd_time(today);
                        Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                        history.setContinue_time(Integer.parseInt(cha+""));
                        processTaskStatusHistoryRepository.save(history);
                    }
                    //新增
                    ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                    newRecord.setStart_time(today);
                    newRecord.setContinue_time(0);
                    newRecord.setDevice_code(pcbTaskReq.getDeviceCode());
                    newRecord.setDevice_name(pcbTaskReq.getDeviceName());
                    newRecord.setProcess_task_status("已完成");
                    //newRecord.setDevice_name(processTask.getDevice_name());
                    newRecord.setProcess_task_code(processTask.getProcess_task_code());
                    newRecord.setProcess_name(processTask.getProcess_name());
                    newRecord.setEnd_time(today);
                    processTaskStatusHistoryRepository.save(newRecord);

                }
                processTaskDetailDeviceRepository.save(detailDevice);
                List<ProcessTaskDetailDevice> detailDeviceList = processTaskDetailDeviceRepository.findByTaskCodeAndDayTime(processTask.getProcess_task_code(),todayStr);
                Integer sumdevicecount = detailDeviceList.stream().mapToInt(ProcessTaskDetailDevice::getFinish_count).sum();
                detail.setFinish_count(sumdevicecount);
            }
            processTaskDetailRepositoty.save(detail);
            List<ProcessTaskDetail> detailList = processTaskDetailRepositoty.findByProcess_task_code(processTask.getProcess_task_code());
            //因为事务所以这里先去掉detail的完成数再加回来step1
            //detailList.remove(detail);
            Integer sumcount = detailList.stream().mapToInt(ProcessTaskDetail::getFinish_count).sum();
            //step2
            // sumcount = sumcount + detail.getFinish_count();
            processTask.setAmount_completed(sumcount);
            ProcessTaskStatusHistory history = null;
            if(processTask.getCount_type()==1){
                history = processTaskStatusHistoryRepository.findByProcessTaskCodeAndDeviceLastRecord(processTask.getProcess_task_code(),pcbTaskReq.getDeviceCode());
            }else {
                history = processTaskStatusHistoryRepository.findByProcessTaskCodeLastRecord(processTask.getProcess_task_code());
            }
            if(processTask.getAmount_completed()>=processTask.getPcb_quantity()){
                //新增结束工序计划操作记录
                processTask.setFinish_time(today);
                processTask.setIs_now_flag("");
                processTask.setProcess_task_status("已完成");
                //step3:状态不同结束上一条并计算持续时间，新增一条
                if(!deviceDetailFinishFlag){
                    history.setEnd_time(today);
                    Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                    history.setContinue_time(Integer.parseInt(cha+""));
                    processTaskStatusHistoryRepository.save(history);
                    //新增
                    ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                    newRecord.setStart_time(today);
                    newRecord.setContinue_time(0);
                    newRecord.setDevice_code(pcbTaskReq.getDeviceCode());
                    newRecord.setProcess_task_status("已完成");
                    newRecord.setDevice_name(pcbTaskReq.getDeviceName());
                    newRecord.setProcess_task_code(processTask.getProcess_task_code());
                    newRecord.setProcess_name(processTask.getProcess_name());
                    newRecord.setEnd_time(today);
                    processTaskStatusHistoryRepository.save(newRecord);
                }
            }else {
                if(processTask.getCount_type()==1){

                    String lastflag = processTask.getIs_now_flag();
                    List<String> lastresult = new ArrayList<>();
                    if(lastflag!=null&&!lastflag.equals("")){
                        lastresult = new ArrayList(Arrays.asList(lastflag.split(",")));
                    }
                    lastresult.remove(pcbTaskReq.getDeviceCode());
                    String laststr = Joiner.on(",").join(lastresult);
                    processTask.setIs_now_flag(laststr);

                }else {
                    processTask.setIs_now_flag("");
                }

                //新增暂停工序计划操作记录
                //step2:状态相同则跳过
                if(history!=null&&"暂停".equals(history.getProcess_task_status())){

                }else if (history!=null){
                    //step3:状态不同结束上一条并计算持续时间，新增一条
                    if(!deviceDetailFinishFlag){
                        history.setEnd_time(today);
                        Long cha = (today.getTime()-history.getStart_time().getTime())/(1000*60);
                        history.setContinue_time(Integer.parseInt(cha+""));
                        processTaskStatusHistoryRepository.save(history);
                        //新增
                        ProcessTaskStatusHistory newRecord = new ProcessTaskStatusHistory();
                        newRecord.setContinue_time(0);
                        newRecord.setStart_time(today);
                        newRecord.setDevice_code(pcbTaskReq.getDeviceCode());
                        newRecord.setDevice_name(pcbTaskReq.getDeviceName());
                        newRecord.setProcess_task_status("暂停");
                        newRecord.setProcess_name(processTask.getProcess_name());
                        newRecord.setProcess_task_code(processTask.getProcess_task_code());
                        //newRecord.setEnd_time(today);

                        processTaskStatusHistoryRepository.save(newRecord);
                    }
                    if(processTask.getCount_type()==0){
                        processTask.setProcess_task_status("暂停");
                    }
                }

            }
            Integer finishAomout = processTask.getAmount_completed()==null?0:processTask.getAmount_completed();
            BigDecimal workTime = processTask.getWork_time()==null?BigDecimal.ZERO:processTask.getWork_time();
            amountSum = amountSum + finishAomout;
            workTimeSum = workTimeSum.add(workTime) ;
            processTaskRepository.save(processTask);
        }

        for(PcbTaskReq pcbTaskReq : pcbTaskReqList){
            if (pcbTaskReqList.size() == 1){
                break;
            }
            ProcessTask processTask = processTaskRepository.findById(pcbTaskReq.getProcessTaskId()).get();

            Integer finishAomout = processTask.getAmount_completed()==null?0:processTask.getAmount_completed();
            BigDecimal rate = new BigDecimal(0);
            if (amountSum != 0){
                rate = new BigDecimal((float)finishAomout/amountSum).setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            BigDecimal workTime = workTimeSum.multiply(rate);
            processTask.setWork_time(workTime);
            processTaskRepository.save(processTask);

            List<ProcessTaskStatusHistory> historyList = processTaskStatusHistoryRepository.findByProcessTaskCodeAndStatus(processTask.getProcess_task_code(), "生产中");
            historyList.forEach(h->h.setContinue_time(0));
            processTaskStatusHistoryRepository.saveAll(historyList);

            ProcessTaskStatusHistory history = new ProcessTaskStatusHistory();
            history.setStatus(StatusEnum.OK.getCode());
            history.setProcess_task_code(processTask.getProcess_task_code());
            history.setDevice_code(pcbTaskReq.getDeviceCode());
            Device device = deviceRepository.findbyDeviceCode(pcbTaskReq.getDeviceCode());
            if(device!=null){
                history.setDevice_name(device.getDevice_name());
            }
            history.setStart_time(today);
            history.setEnd_time(today);
            BigDecimal tempWorkTime = workTime.setScale(0,BigDecimal.ROUND_HALF_UP);
            history.setContinue_time(Integer.parseInt(tempWorkTime.toString()));
            history.setProcess_task_status("生产中");
            ProcessTaskStatusHistory history1 = new ProcessTaskStatusHistory();
            BeanUtils.copyProperties(history,history1);
            processTaskStatusHistoryRepository.save(history);
            history1.setProcess_task_status("暂停");
            history1.setContinue_time(0);
            processTaskStatusHistoryRepository.save(history1);

        }

        return ResultVoUtil.success("操作成功");
    }

    @Override
    public ResultVo countProcessTaskAmount(PcbTaskReq pcbTaskReq) {
        ProcessTask processTask = processTaskRepository.findByProcessTaskCode(pcbTaskReq.getProcessTaskCode());
        if(processTask==null){
            return ResultVoUtil.error("该单号不存在");
        }
        Process process = processRepository.findByProcessName(processTask.getProcess_name());
        if(process.getCount_type()==0){
            return ResultVoUtil.error("该工序任务不适用扫板计数");
        }
        if("已完成".equals(processTask.getProcess_task_status())){
            return ResultVoUtil.error("该任务单已完成");
        }
        if("进行中".equals(processTask.getProcess_task_status())||"生产中".equals(processTask.getProcess_task_status())){
            PcbTaskPlateNo pcbTaskPlateNo = pcbTaskPlateNoRepository.findByPlate_no(pcbTaskReq.getPlateNo(),processTask.getProcess_task_code());
            if(pcbTaskPlateNo==null){
                return ResultVoUtil.error("找不到该板编号！");
            }
            if("1".equals(pcbTaskPlateNo.getIs_count())){
                return ResultVoUtil.error("该板编号已计数过！");
            }
            pcbTaskPlateNo.setIs_count("1");
            pcbTaskPlateNo.setUpdate_time(new Date());
            pcbTaskPlateNoRepository.save(pcbTaskPlateNo);
            Integer nowfinish = processTask.getAmount_completed()+1;
            Date today = new Date();
            //查找工序任务详情
            String todaystr = DateUtil.date2String(today,"");
            List<ProcessTaskDetail> detailList = processTaskDetailRepositoty.findByProcess_task_code(processTask.getProcess_task_code());
            Integer detailSumCount = 0;
            ProcessTaskDetail currentDetail = null;
            for(ProcessTaskDetail detail :detailList){
                int flag = DateUtil.compareDate(detail.getPlan_day_time(),today);
                if(flag==-1){
                    detailSumCount = detailSumCount + detail.getFinish_count();
                }
                if(flag==0){
                    currentDetail = detail;
                }
            }
            //当当天没有日计划详情则新增
            if(currentDetail==null){
                currentDetail = new ProcessTaskDetail();
                currentDetail.setPlan_day_time(today);
                currentDetail.setPlan_count(0);
                currentDetail.setFinish_count(1);
                currentDetail.setProcess_task_code(processTask.getProcess_task_code());
                currentDetail.setDetail_type("系统分配");
                currentDetail.setProcess_name(processTask.getProcess_name());
                currentDetail.setStatus(StatusEnum.OK.getCode());
                currentDetail.setUser_name("");
                if(processTask.getCount_type()==1){
                    //同时新增工位计划
                    ProcessTaskDetailDevice detailDevice = new ProcessTaskDetailDevice();
                    detailDevice.setDevice_code(pcbTaskReq.getDeviceCode());
                    detailDevice.setDevice_detail_status("生产中");
                    detailDevice.setPlan_day_time(today);
                    detailDevice.setProcess_task_code(processTask.getProcess_task_code());
                    detailDevice.setPlan_count(0);
                    detailDevice.setFinish_count(1);
                    processTaskDetailDeviceRepository.save(detailDevice);
                }
            }else {
                if(processTask.getCount_type()==1){
                    ProcessTaskDetailDevice detailDevice = processTaskDetailDeviceRepository.findByTaskCodeAndDayTimeAndDeviceCode1(processTask.getProcess_task_code(),todaystr,pcbTaskReq.getDeviceCode());
                    if(detailDevice!=null){
                        detailDevice.setFinish_count(detailDevice.getFinish_count()+1);
                        if(detailDevice.getFinish_count()>=detailDevice.getPlan_count()){
                            detailDevice.setDevice_detail_status("已完成");
                        }
                    }else {
                        detailDevice = new ProcessTaskDetailDevice();
                        detailDevice.setDevice_code(pcbTaskReq.getDeviceCode());
                        detailDevice.setDevice_detail_status("生产中");
                        detailDevice.setPlan_day_time(today);
                        detailDevice.setProcess_task_code(processTask.getProcess_task_code());
                        detailDevice.setPlan_count(0);
                        detailDevice.setFinish_count(1);
                    }
                    processTaskDetailDeviceRepository.save(detailDevice);
                }

                currentDetail.setFinish_count(currentDetail.getFinish_count()+1);
            }
            processTaskDetailRepositoty.save(currentDetail);


            processTask.setAmount_completed(nowfinish);
            //当数量足够自动完成
            if(nowfinish.equals(processTask.getPcb_quantity())){
                Date finishTime = new Date();
                processTask.setFinish_time(finishTime);

                BigDecimal workTime = DateUtil.differTwoDate(finishTime,processTask.getStart_time());
                //processTask.setWork_time(workTime);
                processTask.setProcess_task_status("已完成");
                processTask.setIs_now_flag("");
                //重新计数
                List<ProcessTaskDevice> prl = processTaskDeviceRepository.findByPTCode(processTask.getProcess_task_code());
                for(ProcessTaskDevice de:prl){
                    Device device = deviceRepository.findbyDeviceCode(de.getDevice_code());
                    device.setRe_count("1");
                    deviceRepository.save(device);
                    de.setLast_amount(de.getAmount());
                    processTaskDeviceRepository.save(de);
                }
            }
            processTaskRepository.save(processTask);


            return ResultVoUtil.success("计数成功");
        }
        return ResultVoUtil.error("该任务单未启动");
    }

    @Override
    public ResultVo tempChangeTaskrocess(PcbTaskReq pcbTaskReq) {
        if(pcbTaskReq.getPcbTaskCode()!=null&&!"".equals(pcbTaskReq.getPcbTaskCode())){
            List<PcbTask> pcbTasks = pcbTaskRepository.findAllByPcb_task_code(pcbTaskReq.getPcbTaskCode());
            if(pcbTasks.size()>0){
                PcbTask pcbTask = pcbTasks.get(0);
                if(pcbTaskReq.getStatus()!=null&&!"".equals(pcbTaskReq.getStatus())){
                    pcbTask.setPcb_task_status(pcbTaskReq.getStatus());
                }
                if(pcbTaskReq.getAmountCompleted()!=null&&!"".equals(pcbTaskReq.getAmountCompleted())){
                    pcbTask.setAmount_completed(pcbTaskReq.getAmountCompleted());
                }
                if(pcbTaskReq.getPlanFinishTime()!=null&&!"".equals(pcbTaskReq.getPlanFinishTime())){
                    pcbTask.setProduce_plan_complete_date(pcbTaskReq.getPlanFinishTime());
                }
                if(pcbTaskReq.getFinishTime()!=null&&!"".equals(pcbTaskReq.getFinishTime())){
                    pcbTask.setProduce_complete_date(pcbTaskReq.getFinishTime());
                }
                pcbTaskRepository.save(pcbTask);
            }

        }
        if(pcbTaskReq.getProcessTaskCode()!=null&&!"".equals(pcbTaskReq.getProcessTaskCode())){
            ProcessTask processTask = processTaskRepository.findByProcessTaskCode(pcbTaskReq.getProcessTaskCode());
            //计划结束
            if(pcbTaskReq.getPlanFinishTime()!=null&&!"".equals(pcbTaskReq.getPlanFinishTime())){
                processTask.setPlan_finish_time(pcbTaskReq.getPlanFinishTime());
            }
            if(pcbTaskReq.getAmountCompleted()!=null&&!"".equals(pcbTaskReq.getAmountCompleted())){
                processTask.setAmount_completed(pcbTaskReq.getAmountCompleted());
            }
            if(pcbTaskReq.getWorkTime()!=null&&!"".equals(pcbTaskReq.getWorkTime())){
                processTask.setWork_time(pcbTaskReq.getWorkTime());

            }
            //实际结束
            if(pcbTaskReq.getFinishTime()!=null&&!"".equals(pcbTaskReq.getFinishTime())){
                processTask.setFinish_time(pcbTaskReq.getFinishTime());
            }
            //计划开始
            if(pcbTaskReq.getPlanStartTime()!=null&&!"".equals(pcbTaskReq.getPlanStartTime())){
                processTask.setPlan_start_time(pcbTaskReq.getPlanStartTime());
            }
            //实际开始
            if(pcbTaskReq.getStartTime()!=null&&!"".equals(pcbTaskReq.getStartTime())){
                processTask.setStart_time(pcbTaskReq.getStartTime());
            }
            if(pcbTaskReq.getProcessTaskStatus()!=null&&!"".equals(pcbTaskReq.getProcessTaskStatus())){
                processTask.setProcess_task_status(pcbTaskReq.getProcessTaskStatus());
            }

            processTaskRepository.save(processTask);
        }
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo updateProcessTask(ProcessTaskReq processTaskReq) {

        if(processTaskReq.getProcessId() != null && !"".equals(processTaskReq.getProcessId())){
            ProcessTask processTask = processTaskRepository.findByProcessId(processTaskReq.getProcessId());
            /*if (processTaskReq.getProcessName() != null && !"".equals(processTaskReq.getProcessName())){
                processTask.setProcess_name(processTaskReq.getProcessName());
            }*/
            if (processTaskReq.getPlanStartTime() != null && !"".equals(processTaskReq.getPlanStartTime())){
                processTask.setPlan_start_time(processTaskReq.getPlanStartTime());
            }
            if (processTaskReq.getPlanFinishTime() != null && !"".equals(processTaskReq.getPlanFinishTime())){
                processTask.setPlan_finish_time(processTaskReq.getPlanFinishTime());
            }
            if (processTaskReq.getStartTime() != null && !"".equals(processTaskReq.getStartTime())){
                processTask.setStart_time(processTaskReq.getStartTime());
            }
            if (processTaskReq.getFinishTime() != null && !"".equals(processTaskReq.getFinishTime())){
                processTask.setFinish_time(processTaskReq.getFinishTime());
            }

            if (processTaskReq.getPcbQuantity() != null && !"".equals(processTaskReq.getPcbQuantity())){
                processTask.setPcb_quantity(processTaskReq.getPcbQuantity());
            }
            if (processTaskReq.getAmountCompleted() != null && !"".equals(processTaskReq.getAmountCompleted())){
                processTask.setAmount_completed(processTaskReq.getAmountCompleted());
            }
            if (processTaskReq.getWorkTime() != null && !"".equals(processTaskReq.getWorkTime())){
                processTask.setWork_time(processTaskReq.getWorkTime());
            }
            /*if (processTaskReq.getStatus() != null && !"".equals(processTaskReq.getStatus())){
                processTask.setStatus(processTaskReq.getStatus());
            }*/


            processTaskRepository.save(processTask);
            return ResultVoUtil.success("更新成功");
        }else {
            return ResultVoUtil.error(400,"更新失败");
        }

    }

    @Override
    public ResultVo updateProcess(ProcessTask processTask) {
        if(processTask.getId() != null && !"".equals(processTask.getId())){
            processTask = processTaskRepository.findByProcessId(processTask.getId());
            if (processTask.getProcess_name() != null && !"".equals(processTask.getProcess_name())){
                processTask.setProcess_name(processTask.getProcess_name());
            }
            if (processTask.getPlan_start_time() != null && !"".equals(processTask.getPlan_start_time())){
                processTask.setPlan_start_time(processTask.getPlan_start_time());
            }
            if (processTask.getPlan_finish_time() != null && !"".equals(processTask.getPlan_finish_time())){
                processTask.setPlan_finish_time(processTask.getPlan_finish_time());
            }
            if (processTask.getStart_time() != null && !"".equals(processTask.getStart_time())){
                processTask.setStart_time(processTask.getPlan_finish_time());
            }
            if (processTask.getFinish_time() != null && !"".equals(processTask.getFinish_time())){
                processTask.setFinish_time(processTask.getFinish_time());
            }
            if (processTask.getPcb_quantity() != null && !"".equals(processTask.getPcb_quantity())){
                processTask.setPcb_quantity(processTask.getPcb_quantity());
            }
            if (processTask.getAmount_completed() != null && !"".equals(processTask.getAmount_completed())){
                processTask.setAmount_completed(processTask.getAmount_completed());
            }
            if (processTask.getWork_time() != null && !"".equals(processTask.getWork_time())){
                processTask.setWork_time(processTask.getWork_time());
            }
            if (processTask.getStatus() != null && !"".equals(processTask.getStatus())){
                processTask.setStatus(processTask.getStatus());
            }
            processTaskRepository.save(processTask);
        }
        return ResultVoUtil.success();
    }

    @Override
    public ResultVo recordBadTypeList(PcbTaskReq req) {
        List<BadClassDetail> detailList = new ArrayList<>();
        User user = userRepository.findByCard_sequence(req.getCardSequence());
        String userName = "";

        if(user!=null){
            userName = user.getNickname();
        }

        Date date = new Date();
        for(PcbTaskReq bad : req.getBadNewsList()){
            BadClassDetail badClassDetail = badClassDetailRepository.findByPlatenoAndBadType(req.getPlateNo(),bad.getBadNews());
            if(badClassDetail!=null){
                continue;
            }
            BadClassDetail detail = new BadClassDetail();
            detail.setBad_type(bad.getBadNews());
            detail.setPcb_task_code(req.getPcbTaskCode());
            detail.setRecorder_name(userName);
            detail.setRecord_time(date);
            detail.setPosition(req.getPosition());
            detail.setPlate_no(req.getPlateNo());
            detail.setRecorder_sequence(req.getCardSequence());
            detailList.add(detail);

        }
        badClassDetailRepository.saveAll(detailList);
        return ResultVoUtil.success("录入成功");
    }


    @Override
    public ResultVo badDetailRecordQc(PcbTaskReq req) {
        User user = userRepository.findByCard_sequence(req.getCardSequence());
        String userName = "";

        if(user!=null){
            userName = user.getNickname();
        }
        String [] ids = req.getIds().split(",");
        List<Long> iids = new ArrayList<>();
        Arrays.asList(ids).forEach(s -> iids.add(Long.parseLong(s)));
        Date today = new Date();
        List<BadClassDetail> list = badClassDetailRepository.findByIdIn(iids);
        String finalUserName = userName;
        list.forEach(l->{
            l.setQc_sequence(req.getCardSequence());
            l.setQc_nama(finalUserName);
            l.setQc_time(today);
        });
        badClassDetailRepository.saveAll(list);
        return  ResultVoUtil.success("");
    }

    @Override
    public ResultVo findBadTypeRecordList(PcbTaskReq req) {

        return ResultVoUtil.success(badClassDetailRepository.findByPlate_no(req.getPlateNo()));
    }

    @Override
    public ResultVo findPcbTaskPlateNo(PcbTaskReq req) {
        Integer page = req.getPage()==null?1:req.getPage(); //当前页
        Integer size = req.getSize()==null?10:req.getSize(); //每页条数
        StringBuffer sql = new StringBuffer("select * from(\n" +
                "select *, ROW_NUMBER() OVER(order by  t4.update_time desc ,t4.is_count desc ) row from\n" +
                "(SELECT * FROM produce_pcbtask_plate_no WHERE process_task_code = '" +
                req.getProcessTaskCode() +
                "'  )t4)t3\n" +
                "where t3.Row between " +
                ((page-1)*size+1) +
                " and " +
                (page*size) );

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql.toString());
        StringBuffer allSql = new StringBuffer("select * from produce_pcbtask_plate_no WHERE process_task_code = '" +
                req.getProcessTaskCode() +
                "'");
        List<Map<String,Object>> allList = jdbcTemplate.queryForList(allSql.toString());
        Integer count = allList.size();
        //List<PcbTaskPlateNo> plateNoList = pcbTaskPlateNoRepository.findByPcb_task_code(req.getPcbTaskCode());
        return ResultVoUtil.success("查询成功",mapList,count);
    }

    @Override
    public ResultVo deviceAmountAndworkTime(PcbTaskReq req) {

        ProcessTask processTask = processTaskRepository.findByProcessTaskCode(req.getProcessTaskCode());
        Integer amount = 0;
        Process process = processRepository.findByProcessName(processTask.getProcess_name());
        Map<String,Object> map = new HashMap<>();
        Date today = new Date();
        String ttoday = DateUtil.date2String(today,"");
        //查找不良记录
        List<BadClassDetail> recordList = badClassDetailRepository.findByPcb_task_codeAndAndRecord_time(processTask.getPcb_task_code(), ttoday);
        map.put("badCount",recordList.size());
        map.put("nowPlateNo","");
        //计数方式为0
        if(process.getCount_type()==1){
            //ProcessTaskDevice processTaskDevice = processTaskDeviceRepository.findByPTCodeDeviceCode(req.getDeviceCode(),req.getProcessTaskCode())
            ProcessTaskDetailDevice detailDevice = processTaskDetailDeviceRepository.findByTaskCodeAndDayTimeAndDeviceCode1(req.getProcessTaskCode(),ttoday,req.getDeviceCode());
            if(detailDevice!=null){
                amount = detailDevice.getFinish_count();
            }

        }else {
          /*  //获取日计划的数量
            ProcessTaskDetail detail = processTaskDetailRepositoty.findAllByProcess_task_codeAndPlan_day_time(req.getProcessTaskCode(), ttoday);
            if(detail!=null){
                amount = detail.getFinish_count();
            }else {
                amount = processTask.getAmount_completed();
            }*/
            ProcessTaskDevice processTaskDevice = processTaskDeviceRepository.findByPTCodeDeviceCode(req.getDeviceCode(),req.getProcessTaskCode());
            List<ProcessTaskDetail> details = processTaskDetailRepositoty.findAllByProcess_task_codeAndBefoPlan_day_time(req.getProcessTaskCode(), ttoday);
            if(processTaskDevice!= null){
                Integer detailsumCount = details.stream().mapToInt(ProcessTaskDetail::getFinish_count).sum();
                amount = (processTaskDevice.getAmount() - detailsumCount)>0?(processTaskDevice.getAmount() - detailsumCount):0;
            }else {
                amount = 0;
            }
            List<ProcessTaskRealPlateSort> plateSorts = processTaskRealPlateSortRepository.findByProcess_task_code(processTask.getProcess_task_code());
            if(plateSorts!=null&&plateSorts.size()!=0&&processTaskDevice.getAmount()<plateSorts.size()){
                ProcessTaskRealPlateSort sort = plateSorts.get(processTaskDevice.getAmount());
                map.put("nowPlateNo",sort.getPlate_no());
            }
        }
        BigDecimal workTime = processTask.getWork_time().multiply(new BigDecimal(60));
        User user = ShiroUtil.getSubject();
        if(user!=null){
            UserDeviceHistory userDeviceHistory = userDeviceHistoryRepository.findAllByProcessTaskDateDeviceUser(req.getProcessTaskCode(),ttoday,req.getDeviceCode(),user.getId());
            if(userDeviceHistory!=null){
                long time = ( today.getTime()-userDeviceHistory.getUp_time().getTime() )/1000;
                map.put("workTime",time);
            }else {
                map.put("workTime",0);
            }

        }else {
            map.put("workTime",0);
        }
        map.put("amount",amount);
        return ResultVoUtil.success(map);
    }

    @Override
    public ResultVo addProcessTaskDevice(PcbTaskReq req) {
        ProcessTask processTask = processTaskRepository.findByProcessTaskCode(req.getProcessTaskCode());
        processTask.setDevice_code(req.getDeviceCode());
        processTask.setDevice_name(req.getDeviceName());
        processTaskRepository.save(processTask);
        return ResultVoUtil.success("修改成功");
    }


    /*if(pcbTaskCode!=null&&!"".equals(pcbTaskCode)){
            wheresql.append(" and pcb_task_code = '" +
                    pcbTaskCode +
                    "' ");
        }
        if(pcbId!=null&&!"".equals(pcbId)){
            wheresql.append(" and pcb_id = '" +
                    pcbId +
                    "' ");
        }
        if(pcbName!=null&&!"".equals(pcbName)){
            wheresql.append(" and pcb_name = '" +
                    pcbName +
                    "' ");
        }*/


}
