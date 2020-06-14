
//看板三=====================
var board3Api = {
    deviceUrl: '/ShowBoard/getDeviceStatus',//设备接口
    excute: '/open/excute',//执行数据查询接口
    processBadRate: '/ShowBoard/processBadRate',//不良率
    findProcessTaskByDevice: '/ShowBoard/findProcessTaskByDevice',//根据设备编号查询工序任务
    getDeviceByCode: '/ShowBoard/getDeviceByCode/',//查看设备接口
    getMapProcessThisWeekRate:'/ShowBoard/getMapProcessThisWeekRate',//周每天任务完成数量及百分比
}
function setDataBoard3(params) {
    setTimeout(function () {
        
        $.ajax({
            contentType: 'application/json',
            type: 'get',
            url: board3Api.getMapProcessThisWeekRate,
            dataType: "json",
            success: function (response) {
                var numArr1 = [],axisWeekRateArr=[],numArr2=[];
                for(var i=0;i<response.data.length;i++){
                    axisWeekRateArr.push(response.data[i].theDay)
                    numArr1.push(response.data[i].sumFinishAmount)
                    numArr2.push(response.data[i].allCount)
                }
                console.log(numArr1,numArr2,axisWeekRateArr)
                // axisWeekRateArr = ['周日','周一','周二','周三','周四','周五','周六']
                db3POption2.xAxis[0].data = axisWeekRateArr;
                db3POption2.series[0].data = numArr2;
                db3POption2.series[1].data = numArr1;
                db3P2.setOption(db3POption2);
            }
        });
        db3P3.setOption(db3POption3);


        addDataTaskHtml(); //生产信息 
    }, 10)
    $('.imagesflex div').off().on('click', function () {
        var deviceName = $(this).attr("name");
        setdievClick(deviceName, $(this).index());
    })
    getData(); // 设备状态
    getEnvironmentRecord();//环境
    processBadRate();//不良率
    // 开启定时任务，时间间隔为3000 ms。
    var deviceInterval = setInterval(function () {
        getData();
    }, 5000);
    //1分钟
    setInterval(function () {
        getEnvironmentRecord();
    }, 60000);
}
function getData() {
    $.ajax({
        contentType: 'application/json',
        type: 'get',
        url: board3Api.deviceUrl,
        dataType: "json",
        success: function (response) {
            $(".imagesflex").find('i').each(function (i) {
                if (response.data[i].device_status == 0) {
                    $(this).removeClass('state-gray');
                    $(this).removeClass('state-yellow');
                    $(this).addClass('state-green');
                } else if (response.data[i].device_status == 1) {
                    $(this).removeClass('state-gray');
                    $(this).addClass('state-yellow');
                } else {
                    $(this).removeClass('state-yellow');
                    $(this).addClass('state-gray');
                }
            });
        }
    });
}
//温湿度 
function getEnvironmentRecord() {
    var environment_record = "SELECT@@@*@@@FROM@@@base_environment_record@@@order@@@by@@@update_date@@@desc";
    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: board3Api.excute,
        dataType: "json",
        data: JSON.stringify(returnData(environment_record)),
        success: function (response) {
            if (response.data.length > 0) {
                var temperature = response.data[0].temperature;
                var humidity = response.data[0].humidity;
                console.log(temperature, humidity)
                if (temperature > 40) {
                    $("#temperature i").removeClass('state-blue').addClass('state-red');
                }
                if (humidity > 70) {
                    $("#humidity i").removeClass('state-blue').addClass('state-red');
                }
            }
        }
    });
}
//不良率
function processBadRate() {
    $.ajax({
        contentType: 'application/json',
        type: 'get',
        url: board3Api.processBadRate,
        dataType: "json",
        success: function (response) {
            console.log('我是不良率=', response)
            var badRateArr = [], legendAxisArr = [];
            for (var i = 0; i < response.data.length; i++) {
                if (i == 2) {
                    continue;
                }
                legendAxisArr.push(response.data[i].processType)
                badRateArr.push({ value: response.data[i].rate, name: response.data[i].processType })
            }
            console.log("===========",legendAxisArr)
            // db2POption2.yAxis.data = houhanTaskArr1.reverse();
            db3POption5.legend.data=legendAxisArr
            db3POption5.series[0].data = badRateArr;
            db3P5.setOption(db3POption5);
        }
    });
}
function setdievClick(deviceName, n) {
    $('.filterbg').show();
    $('.popup').show();
    $('.popup').width('3px');
    $('.popup').animate({ height: '76%' }, 400, function () {
        $('.popup').animate({ width: '82%' }, 400);
    });
    setTimeout(deviceShow(deviceName, n), 800);
}
function deviceShow(deviceName, n) {
    $('.popupClose').css('display', 'block');
    $('.summary').show().css('display', 'block');
    var data = {
        "deviceCode": deviceName
    }
    $.ajax({
        contentType: 'application/json',
        type: 'post',
        url: board3Api.findProcessTaskByDevice,
        dataType: "json",
        data: JSON.stringify(data),
        success: function (response) {
            console.log('设备信息=', response)
            var responseData = response.data;
            $.ajax({
                contentType: 'application/json',
                type: 'get',
                url: board3Api.getDeviceByCode+data.deviceCode,
                dataType: "json",
                success: function (response) {
                    var deviceresponse = response.data
                    addHtml(responseData,deviceresponse,deviceName, n);
                    setDevice();
                }
            });
        }
    });


};
var devicePie1, devicePie3;
var pieData;
function setDevice() {
    devicePie1 = echarts.init(document.getElementById('devicePie1'), 'macarons');
    devicePie3 = echarts.init(document.getElementById('devicePie3'), 'macarons');

    var ww = $(window).width();
    var pieData;
    if (ww > 1600) {
        pieData = {
            pieTop: '40%',
            pieTop2: '36%',
            titleSize: 20,
            pieRadius: [80, 85],
            itemSize: 32
        }
    } else {
        pieData = {
            pieTop: '30%',
            pieTop2: '26%',
            titleSize: 16,
            pieRadius: [40, 41],
            itemSize: 20
        }
    };
    //弹出框调用ECharts饼图
    var pieOption1 = {
        title: {
            x: 'center',
            // y: pieData.pieTop,
            text: 'FACE001(巴斯顿4A)',
            textStyle: {
                fontWeight: 'normal',
                color: '#ffd325',
                fontSize: 22,
            },
            subtext: '\n\n\n\n\n\n\n\n\n\n\n\n稼动率',
            subtextStyle: {
                color: '#fff',
                fontSize: 22
            }
        },
        tooltip: {
            formatter: '{a} <br/>{b} : {c}%'
        },
        toolbox: {
            feature: {
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '设备',
                type: 'gauge',
                detail: { formatter: '{value}%' },
                data: [{ value: 0, name: '' }],
                itemStyle: {
                    normal: {
                        color: '#ffffff',
                        shadowColor: '#32ffc7',
                        shadowBlur: 10
                    }
                },
                label: {
                    fontWeight: 'normal',
                    color: '#ffd325',
                    fontSize: pieData.titleSize,
                },
            }
        ]
    }
    var pieOption3 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['停止', '运行'],
            textStyle: {
                color: '#fff',
                fontSize: 20
            },
            y: 10
        },
        grid: {
            left: '5%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        yAxis: {
            type: 'value',
            axisLabel: {
                textStyle: {
                    show: true,
                    color: 'rgba(255,255,255,1)',
                    fontSize: 20
                }
            },
            min: 0,
            max: 100
        },
        xAxis: {
            type: 'category',
            data: ['1号', '2号', '3号', '4号', '5号', '6号'],
            axisLabel: {
                textStyle: {
                    show: true,
                    color: 'rgba(255,255,255,1)',
                    fontSize: 20
                }
            },
        },
        series: [
            {
                name: '停止',
                type: 'bar',
                stack: '总量',
                label: {
                    show: true,
                    position: 'inside'
                },
                barWidth: 50,
                data: [23, 20, 15, 13, 15, 23],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                fontSize: 20,
                                color: '#fff'
                            },
                            position: 'inside',
                            formatter: function (params) {
                                return params.value + "%"
                            }
                        },
                    }
                }
            },
            {
                name: '运行',
                type: 'bar',
                stack: '总量',
                label: {
                    show: true,
                    position: 'inside'
                },
                barWidth: 50,
                data: [13, 22, 14, 13, 14, 26],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                fontSize: 20,
                                color: '#fff'
                            },
                            position: 'inside',
                            formatter: function (params) {
                                return params.value + "%"
                            }
                        },
                    }
                }
            }
        ]
    };
    devicePie1.setOption(pieOption1);
    devicePie3.setOption(pieOption3);
}
function addHtml(responseData,deviceresponse,deviceName,n) {
    var display, summaryWidth,data;
    if (n == 3 || n == 4 || n == 5) {
        display = "block"
        summaryWidth = '60%'
    }else{
        display = "none"
        summaryWidth = '100%'
    }
    if(responseData.data.length>0){
        data = responseData.data[0];
    }else{
        data=responseData.data;
    }
 
    var html = '<div style="height:400px">' +
        '<div class="left" style="width: 40%; height: 320px; display:' + display + '">' +
        '   <div class="">' +
        '       <div id="devicePie1" class="threeBoard4 char"></div>' +
        '   </div>' +
        '</div>' +
        '<div class="item summaryBottom" style=" width:' + summaryWidth + '">' +
        '   <div class="itemTit">' +
        '       <span class="border-blue">设备任务</span>' +
        '   </div>';
        if(data.length != 0){
            if(data.plan_start_time!=null){
                data.plan_start_time = data.plan_start_time.split('T')[0] ;
            }else{
                data.plan_start_time=''
            }
            if(data.plan_finish_time!=null){
                data.plan_finish_time = data.plan_finish_time.split('T')[0] ;
            }else{
                data.plan_finish_time=''
            }
            html += '   <div class="itemCon itembg itembg_popupfirt">' +
            '       <ul class="listStyle">' +
            '           <li class="clearfix">' +
            '				<span class="col2">生产任务号:<strong>' + data.pcb_task_code + '</strong></span>' +
            '				<span class="col2">机台名称:<strong>' + deviceName+ '</strong></span>' +
            // '				<span class="col2">机台编号:<strong>' + data.device_code + '</strong></span>' +
            '				<span class="col2">工序名称:<strong>' + data.process_name + '</strong></span>' +
            '				<span class="col2">工序单状态:<strong>' + data.process_task_status + '</strong></span>' +
            '				<span class="col2">完成数量:<strong>' + data.amount_completed + '</strong></span>' +
            '				<span class="col2">计划开始时间:<strong>' + data.plan_start_time + '</strong></span>' +
            '				<span class="col2">计划结束时间:<strong>' + data.plan_finish_time + '</strong></span>' +
            '				<span class="col2">工时:<strong>' + data.work_time + '</strong></span>' +
            '				<span class="col2">pcb编码:<strong>' + data.pcb_code + '</strong></span>' +
            '				<span class="col2">PCB数量:<strong>' + data.pcb_quantity + '</strong></span>' +
            // '				<span class="col2">RoHS标志:<strong>' + data.is_rohs + '</strong></span>' +
            // '				<span class="col1">工序订单编号:<strong>' + data.process_task_code + '</strong></span>' +
            '				<span class="col1">PCB名称:<strong>' + data.pcb_name + '</strong></span>' +
            '           </li>' +
            '       </ul>' +
            '   </div>';
        }else{
            html += '   <div class="itemCon itembg itembg_popupfirt">' +
            '       <ul class="listStyle">' +
            '           <li class="clearfix">' +
            '				<span class="col2">机台名称:<strong>' + deviceName+ '</strong></span>' +
            '				<span class="col2">任务状态:<strong>无</strong></span>' +
            '           </li>' +
            '       </ul>' +
            '   </div>';
        }
        if(deviceresponse.last_check_time!=null){
            deviceresponse.last_check_time = deviceresponse.last_check_time.split('T')[0] ;
        }else{
            deviceresponse.last_check_time=''
        }
        if(deviceresponse.next_check_time!=null){
            deviceresponse.next_check_time = deviceresponse.next_check_time.split('T')[0] ;
        }else{
            deviceresponse.next_check_time=''
        }

        html += '</div>' +
        '</div>' +
        '<div class="devBottom">' +
        '<div class="item" style="width: 40%;">' +
        '   <div class="itemTit">' +
        '       <span class="border-blue">设备信息</span>' +
        '   </div>' +
        '   <div class="itemCon itembg itembg_popupfirt col2">' +
        '       <ul class="listStyle">' +
        '           <li class="clearfix">' +
        '				<span class="col2">所属厂区:<strong>' + deviceresponse.belong_plant_area + '</strong></span>' +
        '				<span class="col2">所属产线:<strong>' + deviceresponse.belong_line + '</strong></span>' +
        '				<span class="col1">设备名称:<strong>' + deviceresponse.device_name + '</strong></span>' +
        '				<span class="col2">所属工序:<strong>' + deviceresponse.belong_process + '</strong></span>' +
        '				<span class="col2">设备型号:<strong>' + deviceresponse.device_model + '</strong></span>' +
        '				<span class="col2">设备站位:<strong>' + deviceresponse.device_sort + '</strong></span>' +
        '				<span class="col2">设备状态:<strong>' + deviceresponse.device_status + '</strong></span>' +
        '               <span class="col2">上次检测时间:<strong>' + deviceresponse.last_check_time + '</strong></span>' +
        '               <span class="col2">下次检测时间:<strong>' + deviceresponse.next_check_time + '</strong></span>' +
        '           </li>' +
        '       </ul>' +
        '   </div>' +
        '</div>' +
        '<div class="right" style="width: 40%; height: 380px; float: left;">' +
        '    <div class="itemTit">' +
        '        <span class="border-green">设备日状态信息</span>' +
        '    </div>' +
        '    <div id="devicePie3" class="threeBoard5 char"></div>' +
        '</div>' +
        '<div class="imgDiv"><img src="../../images/dataBoard/photo.jpg"><p><span>姓名：</span><span>风陵苑主</span></p><p><span>工号：</span><span>965486</span></p></div>' +
        '</div>';
    $('.summary').html(html)
}
function addDataTaskHtml(data) {
    var data = {
        "id": 6144,
        "batch_id": null,
        "create_date": "2020-06-05T14:28:03.000+0800",
        "factory": "万吉厂区",
        "is_rohs": "否",
        "model_id": null,
        "model_name": "匹配板 DCY2.908.H1343-AG05 版本A",
        "model_ver": "DCY2.908.H1343-AG05-A",
        "patch_pick": null,
        "pcb_id": "DCY2.908.H1343-AG05-A",
        "pcb_is_ab": null,
        "pcb_modify_tag": null,
        "pcb_name": "匹配板 DCY2.908.H1343-AG05 版本A",
        "pcb_plate_id": "H134A200001~H134A200020",
        "pcb_quantity": 20,
        "pcb_task_code": "MDW19062-3",
        "pcb_task_status": "已下达已投产",
        "pcb_update_time": null,
        "plan_complete_date": null,
        "produce_complete_date": null,
        "produce_date": null,
        "produce_plan_complete_date": "2020-02-07T00:00:00.000+0800",
        "produce_plan_date": "2019-11-28T00:00:00.000+0800",
        "quantity": null,
        "remark": null,
        "status": 1,
        "task_sheet_date": "2019-11-28T00:00:00.000+0800",
        "update_date": "2020-06-05T14:29:08.000+0800",
        "workshop": "电路板及工业仪器生产车间",
        "create_by": 1,
        "update_by": 1,
        "amount_completed": 1,
        "feeding_task_code": "PBOM024413",
        "task_sheet_code": "MDW19062",
        "pcb_task_id": null,
        "task_sheet_id": null,
        "priority": 1,
        "plan_finish_time": "2020-06-08T00:00:00.000+0800",
        "finish_count": 10,
        "sum_count": 20
    }
    var theadHtmlPTask = '<div class="item summaryP3" style="">' +
        '   <div class="itemCon itembg itembg_popupfirt">' +
        '       <ul class="listStyle">' +
        '           <li class="clearfix">' +
        '               <span>光板号:<strong>' + data.pcb_plate_id + '</strong></span>' +
        '               <span>通知日期:<strong>' + data.task_sheet_date.split('T')[0] + '</strong></span>' +
        '               <span>厂区:<strong>' + data.factory + '</strong></span>' +
        '               <span>车间:<strong>' + data.workshop + '</strong></span>' +
        '               <span>板编号:<strong>' + data.batch_id + '</strong></span>' +
        '               <span>投料单号:<strong>' + data.feeding_task_code + '</strong></span>' +
        '               <span>计划启动时间:<strong>' + data.produce_plan_date.split('T')[0] + '</strong></span>' +
        '               <span>计划完成时间:<strong>' + data.produce_plan_complete_date.split('T')[0] + '</strong></span>' +
        '               <span>优先级:<strong>' + data.priority + '</strong></span>' +
        '           </li>' +
        '       </ul>' +
        '   </div>' +
        '</div>';
    $("#htmlPTask").html(theadHtmlPTask).css("display", "block");
}
var db3P1, db3P2;
db3P2 = echarts.init(document.getElementById('db3P2'), 'macarons');
db3P3 = echarts.init(document.getElementById('db3P3'), 'macarons');

db3P5 = echarts.init(document.getElementById('db3P5'), 'macarons');

var db3POption2 = {
    grid: {
        left: '5%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    title: {
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} 件',
        textStyle: {
            color: '#fff',
            fontSize: 20
        },
    },
    legend: {
        data: ['计划完成量', '实际完成量'],
        textStyle: {
            color: '#fff',
            fontSize: 20
        },
        y: 10
    },

    // calculable: true,
    xAxis: [
        {
            type: 'category',
            data: [],
            axisLabel: {
                textStyle: {
                    show: true,
                    color: 'rgba(255,255,255,1)',
                    fontSize: 20
                }
            },
        }
    ],
    yAxis: [
        {
            type: 'value',
            axisLabel: {
                textStyle: {
                    show: true,
                    color: 'rgba(255,255,255,1)',
                    fontSize: 20
                }
            },
        }
    ],
    series: [
        {
            name: '计划完成量',
            barGap: 0,
            barWidth: 50,
            type: 'bar',
            data: [90, 65, 98, 87, 85, 76, 92],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            fontSize: 20,
                            color: '#fff'
                        },
                        position: 'inside',
                        formatter: function (params) {
                            return params.value
                        }
                    },
                }
            }
        },
        {
            name: '实际完成量',
            type: 'bar',
            barWidth: 50,
            data: [87, 65, 90, 75, 85, 66, 78],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        textStyle: {
                            fontSize: 20,
                            color: '#fff'
                        },
                        position: 'inside',
                        formatter: function (params) {
                            return params.value
                        }
                    },
                }
            }
        }
    ]
};
var db3POption3 = {
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} H',
        textStyle: {
            color: '#fff',
            fontSize: 20
        },
    },
    grid: {
        top: '10%',
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
            }
        },
        data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
    },
    yAxis: {
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
            }
        },
    },
    series: [{
        name: '工作时长',
        type: 'bar',
        data: [117, 123, 22, 78, 98, 85, 60],
        itemStyle: {
            normal: {
                label: {
                    show: true,
                    textStyle: {
                        fontSize: 20,
                        color: '#fff'
                    },
                    position: 'inside',
                    formatter: function (params) {
                        return params.value
                    }
                },
            }
        }
    }]
};

var db3POption5 = {
    title: {
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
        textStyle: {
            color: '#fff',
            fontSize: 20
        },
    },
    legend: {
        orient: 'vertical',
        data: ['贴片', '后焊', '调试', '调试', '入库'],
        type: 'scroll',
        orient: 'vertical',
        right: 10,
        top: 20,
        bottom: 20,
        textStyle: {
            color: '#fff',
            fontSize: 20
        },
    },
    series: [
        {
            name: '不良率',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: [
                { value: 50, name: '贴片' },
                { value: 60, name: '后焊' },
                { value: 45, name: '调试' },
                { value: 45, name: '质检' },
                { value: 45, name: '入库' },
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)',
                    fontSize: 25
                }
            },
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        // position:'inner',
                        // formatter:'{b}',
                        fontSize: 20
                    }
                }
            },

        }
    ]
}


