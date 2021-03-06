
//第一看板=========================================ttt

var db1P1, db1P2, order1, order2, order3;
db1P1 = echarts.init(document.getElementsByClassName('db1P1')[0], 'macarons');
db1P2 = echarts.init(document.getElementsByClassName('db1P2')[0], 'macarons');
// 内页
order1 = echarts.init(document.getElementsByClassName('order1')[0], 'macarons');
order2 = echarts.init(document.getElementsByClassName('order2')[0], 'macarons');
order3 = echarts.init(document.getElementsByClassName('order3')[0], 'macarons');
var db1P1Option = {
    title: {
        text: ''
    },
    tooltip: {
        trigger: 'item',
        formatter: '{b} <br/>{a} : {c}%',
        textStyle: {
            fontSize: 20
        },
        feature: {
            saveAsImage: {
                show: true
            }
        }
    },
    legend: {
        data: ['完成率'],
        x: 'right',
        textStyle: {
            show: true,
            color: 'rgba(255,255,255,1)',
            fontSize: 20
        },
        y: 5
    },
    grid: {
        top: '10%',
        left: '0%',
        right: '3%',
        bottom: '3%',
        containLabel: true,
        backgroundColor: '#fff'
    },
    //x轴
    xAxis: {
        data: [],
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
            }
        },
    },
    //y轴没有显式设置，根据值自动生成y轴
    yAxis: {
        name: '',
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
            },
            formatter: function (value) {
                return value + '%'
            }
        },
        max: 100,
        min: 0,
        // nameLocation:'middle',
        nameTextStyle: {
            color: "#fff",
            fontSize: 20,
            // padding:10
        }
    },
    //数据-data是最终要显示的数据
    series: [{
        name: '完成率',
        type: 'line',
        showSymbol: true,
        symbol: 'circle',     //设定为实心点
        symbolSize: 20,   //设定实心点的大小
        hoverAnimation: false,
        animation: false,
        data: [10, 20, 35, 60, 75, 90]
    }]
};

var db1P2Option = {
    backgroundColor: 'rgba(255, 255, 255, 0)',
    title: {
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
        },
        textStyle: {
            fontSize: 20,
        }
    },
    legend: {

    },
    grid: {
        top: '8%',
        left: '12%',
        right: '5%',
        bottom: '10%',
        // containLabel: true,
        backgroundColor: '#fff'
    },
    xAxis: {
        type: 'category',
        data: [],
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
            },
            // interval: 0,
            // rotate: 18
        },
    },
    yAxis: {
        type: 'value',
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
            },
            // formatter: function (value) {
            //     return value + '%'
            // }
        },
        // minInterval: 10
        // min: 0,
        // max: 100
        // // data: ['TCL-01-09', 'TCL-01-12', 'TCL-01-13'],
    },
    series: [
        {
            name: '任务量',
            type: 'bar',
            barWidth: 60,
            // stack: '总量',
            // label: {
            // 	show: true,
            // 	position: 'insideRight'
            // },
            data: [85, 90, 88, 98, 68, 12, 0],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inside',
                        formatter: function (prams) {
                            return prams.value + "%";
                        },
                        textStyle: {
                            color: 'white',
                            fontSize: 20
                        }
                    },
                    color: color.color1
                }
            }
        }
    ]
};

var db1P3Option = {
    backgroundColor: 'rgba(255, 255, 255, 0)',
    title: {
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
        },
        textStyle: {
            fontSize: 20,
        }
    },
    legend: {

    },
    grid: {
        top: '15%',
        left: '12%',
        right: '5%',
        bottom: '10%',
        // containLabel: true,
        backgroundColor: '#fff'
    },
    xAxis: {
        type: 'category',
        data: [],
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
            },
            // interval: 10,
            // rotate: 18
        },
    },
    yAxis: {
        name: '单位(pcs)',
        type: 'value',
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
            },
            formatter: function (value) {
                return value
            }
        },
        axisLine: {
            lineStyle: {
                color: '#fff',
                // width:8,//这里是为了突出显示加上的
            },
        },
        nameTextStyle: {
            fontSize: 16,
            padding: 10
        },
        // minInterval: 10
        min: 0,
        max: 100
        // // data: ['TCL-01-09', 'TCL-01-12', 'TCL-01-13'],
    },
    series: [
        {
            name: '完成数量',
            type: 'bar',
            barWidth: 55,
            // stack: '总量',
            // label: {
            // 	show: true,
            // 	position: 'insideRight'
            // },
            data: [85, 90, 88, 98, 68, 12, 0],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inside',
                        // formatter: function (prams) {
                        //     return prams.value + "%";
                        // },
                        textStyle: {
                            color: 'white',
                            fontSize: 20
                        }
                    }
                }
            }
        }
    ]
};
var pie = {
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}:{d}%',
        textStyle: {
            fontSize: 20
        }
    },
    legend: {
        data: ['已完成', '未完成'],
        // x: 'right',
        textStyle: {
            show: true,
            color: 'rgba(255,255,255,1)',
            fontSize: 20
        },
        y: 20
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: '80%',
            center: ['50%', '60%'],
            data: [
                { value: 10, name: '已完成' },
                { value: 20, name: '未完成' }
            ],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inner',
                        // formatter: '{d}%',
                        formatter: function (prams) {
                            var value;
                            if (prams.data.value == 0) {
                                value = ''
                            } else {
                                value = prams.data.value + "%"
                            }
                            return value;
                        },
                        fontSize: 20
                    }
                }
            }
        }
    ]
}
//贴片线饼图
var orderOption1 = {
    title: {
    },
    tooltip: pie.tooltip,
    legend: pie.legend,
    series: pie.series
}
//后焊线饼图
var orderOption2 = {
    title: {
    },
    tooltip: pie.tooltip,
    legend: pie.legend,
    series: pie.series
}
//调试线饼图
var orderOption3 = {
    title: {
    },
    tooltip: pie.tooltip,
    legend: pie.legend,
    series: pie.series
}
var board1Api = {
    pcbTaskBoard: '/ShowBoard/pcbTaskBoard',//周生产看板
    findProcessTaskByDate: '/ShowBoard/findProcessTaskByDate',//日生产计划列表
    getMapProcessThisWeekRate: '/ShowBoard/getMapProcessThisWeekRate',//周每天任务完成数量及百分比
    getMapProcessDayRate: '/ShowBoard/getMapProcessDayRate',//产线日任务达成率
}
function board1(hsaClassOn) {
    if (hsaClassOn) {
        $.ajax({
            contentType: 'application/json',
            type: 'get',
            url: board1Api.pcbTaskBoard,
            dataType: "json",
            success: function (response) {
                addHtml(response.data.pcbTasks, hsaClassOn);
                setOption(response.data);
            }
        });
    } else {
        $.ajax({
            contentType: 'application/json',
            type: 'get',
            url: board1Api.findProcessTaskByDate,
            dataType: "json",
            success: function (response) {
                addHtml(response.data, hsaClassOn);
                setOption(response.data);
            }
        });
    }
}

function setOption(response) {
    // db1P1.clear();
    // db1P2.clear();
    // order1.clear();
    // order2.clear();
    // order3.clear();
    var hsaClassOn = $(".button-span button:first").hasClass("on");
    if (hsaClassOn) {
        var weeks = response.mapWeekRate
        var weekArrdata = [weeks.week1, weeks.week2, weeks.week3, weeks.week4]
        db1P1Option.series[0].data = weekArrdata;
        db1P1Option.xAxis.data = weekinyear();
        db1P1.setOption(db1P1Option);
        $('.box1 .basicInfo  .border-green').html('周任务达成率')
        var mapProcessWeekRate = response.mapProcessWeekRate;
        //贴片
        orderOption1.series[0].data = [{
            name: '已完成',
            value: mapProcessWeekRate.tiepian
        }, {
            name: '未完成',
            value: 100 - mapProcessWeekRate.tiepian
        }]
        order1.setOption(orderOption1);
        //后焊
        orderOption2.series[0].data = [{
            name: '已完成',
            value: mapProcessWeekRate.houhan
        }, {
            name: '未完成',
            value: 100 - mapProcessWeekRate.houhan
        }]
        order2.setOption(orderOption2);
        // 调试
        orderOption3.series[0].data = [{
            name: '已完成',
            value: mapProcessWeekRate.tiaoshi
        }, {
            name: '未完成',
            value: 100 - mapProcessWeekRate.tiaoshi
        }]
        order3.setOption(orderOption3);

        $('.box1 .basicInfo  .border-yellow').html('产线周任务达成率')
        var taskFinishRateAxisArr = [];
        var taskFinishRateArr = [];
        for (var i = 0; i < response.taskFinishRate.length; i++) {
            if (i > 2) continue;
            taskFinishRateAxisArr.push(response.taskFinishRate[i].task_sheet_code);
            // taskFinishRateAxisArr.push('批次'+(i+1));
            taskFinishRateArr.push(response.taskFinishRate[i].rate);
        }
        db1P2Option.xAxis.type = 'value';
        db1P2Option.xAxis.axisLabel.formatter = function (value) {
            return value + '%'
        };
        db1P2Option.xAxis.min = 0;
        db1P2Option.xAxis.max = 100;
        db1P2Option.yAxis.type = 'category';
        db1P2Option.yAxis.data = taskFinishRateAxisArr.reverse();
        db1P2Option.yAxis.axisLabel.formatter = function (params) {
            var newParamsName = "";// 最终拼接成的字符串
            var paramsNameNumber = params.length;// 实际标签的个数
            var provideNumber = 4;// 每行能显示的字的个数
            var rowNumber = Math.ceil(paramsNameNumber / provideNumber);// 换行的话，需要显示几行，向上取整
            /**
             * 判断标签的个数是否大于规定的个数， 如果大于，则进行换行处理 如果不大于，即等于或小于，就返回原标签
             */
            // 条件等同于rowNumber>1
            if (paramsNameNumber > provideNumber) {
                /** 循环每一行,p表示行 */
                for (var p = 0; p < rowNumber; p++) {
                    var tempStr = "";// 表示每一次截取的字符串
                    var start = p * provideNumber;// 开始截取的位置
                    var end = start + provideNumber;// 结束截取的位置
                    // 此处特殊处理最后一行的索引值
                    if (p == rowNumber - 1) {
                        // 最后一次不换行
                        tempStr = params.substring(start, paramsNameNumber);
                    } else {
                        // 每一次拼接字符串并换行
                        tempStr = params.substring(start, end) + "\n";
                    }
                    newParamsName += tempStr;// 最终拼成的字符串
                }

            } else {
                // 将旧标签的值赋给新标签
                newParamsName = params;
            }
            //将最终的字符串返回
            return newParamsName
        }
        db1P2Option.series[0].data = taskFinishRateArr.reverse();
        db1P2Option.series[0].name = '完成率';
        db1P2Option.series[0].itemStyle.normal.label.formatter = function (prams) {
            if (prams.value == 0) {
                return ''
            }
            return prams.value + "%";
        };
        db1P2.setOption(db1P2Option);
        $('.box1 .basicInfo .border-blue').html('各批次完成率')
    } else {
        $.ajax({
            contentType: 'application/json',
            type: 'get',
            url: board1Api.getMapProcessThisWeekRate,
            dataType: "json",
            success: function (response) {
                //测试假数据
                // response = {"code":200,"msg":"成功","data":[
                //     {"theDay":"2020-06-21","allCount":0,"finishCount":0,"rate":10.0000,"sumHHZJFinishAmount":10,"sumPlanAmount":200},
                // {"theDay":"2020-06-22","allCount":5,"finishCount":5,"rate":100.0000,"sumHHZJFinishAmount":256,"sumPlanAmount":500},
                // {"theDay":"2020-06-23","allCount":0,"finishCount":0,"rate":50,"sumHHZJFinishAmount":0,"sumPlanAmount":0},
                // {"theDay":"2020-06-24","allCount":5,"finishCount":5,"rate":88,"sumHHZJFinishAmount":0,"sumPlanAmount":753},
                // {"theDay":"2020-06-25","allCount":1,"finishCount":1,"rate":89,"sumHHZJFinishAmount":0,"sumPlanAmount":300},
                // {"theDay":"2020-06-26","allCount":21,"finishCount":19,"rate":90.0000,"sumHHZJFinishAmount":0,"sumPlanAmount":4300},
                // {"theDay":"2020-06-27","allCount":3,"finishCount":3,"rate":99.0000,"sumHHZJFinishAmount":0,"sumPlanAmount":1050}],
                // "total":null}
                var weekRateArr = [], axisWeekRateArr = [], axisWeekNumArr = [];
                for (var i = 0; i < response.data.length; i++) {
                    // if(i==0) continue;
                    weekRateArr.push(response.data[i].rate)
                    // axisWeekRateArr.push(response.data[i].theDay)
                    axisWeekNumArr.push(response.data[i].sumHHZJFinishAmount)
                }
                // axisWeekRateArr = ['周一', '周二', '周三', '周四', '周五', '周六']
                axisWeekRateArr = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
                db1P1Option.series[0].data = weekRateArr;
                db1P1Option.xAxis.data = axisWeekRateArr;
                db1P1.setOption(db1P1Option);
                db1P3Option.series[0].data = axisWeekNumArr;
                db1P3Option.xAxis.data = axisWeekRateArr;
                db1P3Option.series[0].itemStyle.normal.label.formatter = function (prams) {
                    if (prams.value == 0) {
                        return ''
                    }
                    return prams.value;
                };
                if (axisWeekNumArr.length > 0) {
                    for (var i = 0; i < axisWeekNumArr.length; i++) {
                        if (axisWeekNumArr[i] < 100) continue;
                        db1P3Option.yAxis.max = null;
                        db1P3Option.yAxis.min = null;
                        break;
                    }
                }
                db1P2.setOption(db1P3Option);
            }
        });
        $('.box1 .basicInfo .border-green').html('日任务达成率')
        $('.box1 .basicInfo .border-blue').html('周任务完成数量')
        $.ajax({
            contentType: 'application/json',
            type: 'get',
            url: board1Api.getMapProcessDayRate,
            dataType: "json",
            success: function (response) {
                var mapProcessDayRate = response.data;
                orderOption1.series[0].data = [{
                    name: '已完成',
                    value: mapProcessDayRate.tiepian
                }, {
                    name: '未完成',
                    value: 100 - mapProcessDayRate.tiepian
                }]
                order1.setOption(orderOption1);
                orderOption2.series[0].data = [{
                    name: '已完成',
                    value: mapProcessDayRate.houhan
                }, {
                    name: '未完成',
                    value: 100 - mapProcessDayRate.houhan
                }]
                order2.setOption(orderOption2);
                orderOption3.series[0].data = [{
                    name: '已完成',
                    value: mapProcessDayRate.tiaoshi
                }, {
                    name: '未完成',
                    value: 100 - mapProcessDayRate.tiaoshi
                }]
                order3.setOption(orderOption3);
            }
        });
        $('.box1  .basicInfo .border-yellow').html('产线日任务达成率')
    }
}
function setDataBoard1(params) {
    var hsaClassOn = $(".button-span button:first").hasClass("on");
    if (hsaClassOn) {
        $.ajax({
            contentType: 'application/json',
            type: 'get',
            url: board1Api.pcbTaskBoard,
            dataType: "json",
            success: function (response) {
                addHtml(response.data.pcbTasks, hsaClassOn);
                setOption(response.data);
            }
        });

    } else {
        $.ajax({
            contentType: 'application/json',
            type: 'get',
            url: board1Api.findProcessTaskByDate,
            dataType: "json",
            success: function (response) {
                addHtml(response.data, hsaClassOn);
                setOption(response.data);
            }
        });
    }
    board11Interval = setInterval(function () {
        var hsaClassOn = $(".button-span button:first").hasClass("on");
        board1(hsaClassOn)
    }, 60000);

    $(".button-span button").off().on('click', function () {
        $(this).siblings('button').removeClass('on');
        $(this).addClass('on');
        var hsaClassOn = $(".button-span button:first").hasClass("on");
        if (hsaClassOn) {
            $.ajax({
                contentType: 'application/json',
                type: 'get',
                url: board1Api.pcbTaskBoard,
                dataType: "json",
                success: function (response) {
                    addHtml(response.data.pcbTasks, hsaClassOn);
                    setOption(response.data);
                }
            });
        } else {
            $.ajax({
                contentType: 'application/json',
                type: 'get',
                url: board1Api.findProcessTaskByDate,
                dataType: "json",
                success: function (response) {
                    addHtml(response.data, hsaClassOn);
                    setOption(response.data);
                }
            });
        }
    })
}
function dataShow(data, hsaClassOn) {
    $('.popupClose').css('display', 'block');
    $('.summary').show();
    if (hsaClassOn) {
        addDataHtml(data);
    } else {
        addProcessDataHtml(data);
    }
};
function addDataHtml(data) {
    if (data.pcb_task_status == "已下达未投产") {
        data.pcb_task_status = "未启动"
    }
    if (data.pcb_task_status == "已投产") {
        data.pcb_task_status = "进行中"
    }
    if (data.pcb_task_status == "已下达已完成") {
        data.pcb_task_status = "已完成"
    }
    if (data.produce_plan_date != null) {
        data.produce_plan_date = data.produce_plan_date.split('T')[0];
    }
    if (data.task_sheet_date != null) {
        data.task_sheet_date = data.task_sheet_date.split('T')[0];
    } else {
        data.task_sheet_date = ''
    }
    if (data.plan_complete_date != null) {
        data.plan_complete_date = data.plan_complete_date.split('T')[0];
    } else {
        data.plan_complete_date = '空'
    }
    if (data.produce_plan_complete_date != null) {
        data.produce_plan_complete_date = data.produce_plan_complete_date.split('T')[0];
    } else {
        data.produce_plan_complete_date = '空'
    }
    if (data.pcb_plate_id == null) {
        data.pcb_plate_id = '空'
    }
    if (data.batch_id == null) {
        data.batch_id = '空'
    }

    var theadHtmlP1 = '<div class="item summaryP1" style="">' +
        '   <div class="itemTit">' +
        '       <span class="border-blue">任务详情</span>' +
        '   </div>' +
        '   <div class="itemCon itembg itembg_popupfirt">' +
        '       <ul class="listStyle">' +
        '           <li class="clearfix">' +
        '               <span>生产任务单号:<strong>' + data.pcb_task_code + '</strong></span>' +
        '               <span class="col2">机型名称:<strong>' + data.model_name + '</strong></span>' +
        '               <span>规格型号:<strong>' + data.pcb_id + '</strong></span>' +
        '               <span class="col2">物料名称:<strong>' + data.pcb_name + '</strong></span>' +
        '               <span>生产批次:<strong>' + data.task_sheet_code + '</strong></span>' +
        '               <span class="col2">板编号:<strong>' + data.batch_id + '</strong></span>' +
        '               <span>生产数量:<strong>' + data.pcb_quantity + '</strong></span>' +
        '               <span>完成数量:<strong>' + data.amount_completed + '</strong></span>' +
        '               <span>工单状态:<strong>' + data.pcb_task_status + '</strong></span>' +
        '               <span>优先级:<strong>' + data.priority + '</strong></span>' +
        '               <span>厂区:<strong>' + data.factory + '</strong></span>' +
        '               <span>车间:<strong>' + data.workshop + '</strong></span>' +
        '               <span>通知日期:<strong>' + data.task_sheet_date + '</strong></span>' +
        '               <span>完成时间:<strong>' + data.plan_complete_date + '</strong></span>' +
        '               <span>投料单号:<strong>' + data.feeding_task_code + '</strong></span>' +
        '               <span>计划启动时间:<strong>' + data.produce_plan_date + '</strong></span>' +
        '               <span>计划完成时间:<strong>' + data.produce_plan_complete_date + '</strong></span>' +
        '               <span>光板号:<strong>' + data.pcb_plate_id + '</strong></span>' +
        '           </li>' +
        '       </ul>' +
        '   </div>' +
        '</div>';
    $(".summary").html(theadHtmlP1).css("display", "block");
}
function addProcessDataHtml(data) {
    if (data.plan_start_time != null) {
        data.plan_start_time = data.plan_start_time.split('T')[0];
    } else {
        data.plan_start_time = '空'
    }
    if (data.plan_finish_time != null) {
        data.plan_finish_time = data.plan_finish_time.split('T')[0];
    } else {
        data.plan_finish_time = '空'
    }
    if (data.start_time != null) {
        data.start_time = data.start_time.split('T')[0];
    } else {
        data.start_time = '空'
    }
    if (data.finish_time != null) {
        data.finish_time = data.finish_time.split('T')[0];
    } else {
        data.finish_time = '空'
    }
    var theadHtmlP1 = '<div class="item summaryP1" style="">' +
        '   <div class="itemTit">' +
        '       <span class="border-blue">任务详情</span>' +
        '   </div>' +
        '   <div class="itemCon itembg itembg_popupfirt">' +
        '       <ul class="listStyle">' +
        '           <li class="clearfix">' +
        '               <span>工序任务号:<strong>' + data.process_task_code + '</strong></span>' +
        '               <span>制造编号:<strong>' + data.task_sheet_code + '</strong></span>' +
        '               <span>工序状态:<strong>' + data.process_task_status + '</strong></span>' +
        '               <span>计划量:<strong>' + data.pcb_quantity + '</strong></span>' +
        '               <span>完成量:<strong>' + data.amount_completed + '</strong></span>' +
        // '               <span>设备编号:<strong>' + data.device_code + '</strong></span>' +
        // '               <span>SMT生产线:<strong>' + data.device_name + '</strong></span>' +
        '               <span>工时(分):<strong>' + data.work_time + '</strong></span>' +
        '               <span>工序名称:<strong>' + data.process_name + '</strong></span>' +
        '               <span>计划完成时间:<strong>' + data.plan_finish_time + '</strong></span>' +
        '               <span>计划开始时间:<strong>' + data.plan_start_time + '</strong></span>' +
        '               <span>实际生产时间:<strong>' + data.start_time + '</strong></span>' +
        '               <span>实际完成时间:<strong>' + data.finish_time + '</strong></span>' +
        '               <span>RoHS:<strong>' + data.is_rohs + '</strong></span>' +
        '               <span>规格型号:<strong>' + data.pcb_code + '</strong></span>' +
        '               <span class="col2">物料名称:<strong>' + data.pcb_name + '</strong></span>' +
        '           </li>' +
        '       </ul>' +
        '   </div>' +
        '</div>';
    $(".summary").html(theadHtmlP1).css("display", "block");
}
function addHtml(data, hsaClassOn) {
    // 动态生成模板
    var theadHtml = '', tbodyHtml = '', widthPercent = 1, popData = '';
    var arr = [10, 14, 20, 9, 8, 8, 8, 8, 9], arr1 = [10, 12, 20, 6, 8, 8, 8, 7, 7, 7]
    var widthWW = parseInt($('#firstBoard').css('width'));
    if (hsaClassOn) {
        var theadData = ['生产任务单号', '规格型号', '物料名称', '生产批次', '启动日期', '完成时间', '计划量', '完成量', '工单状态'];
        var tbodyData = data;
        theadHtml = '<div class="StateTit">';
        for (var i = 0; i < theadData.length; i++) {
            if (i > 3) {
                theadHtml += '<span style="width:' + arr[i] + '%;text-align: center;">' + theadData[i] + '</span>';
            } else {
                theadHtml += '<span style="width:' + arr[i] + '%">' + theadData[i] + '</span>';
            }
        }
        theadHtml += '</div>';
        tbodyHtml = '<div id="FontScroll" class="fontScroll"><ul>';
        var tbodyDataS = popData = tbodyData;
        for (var j = 0; j < tbodyDataS.length; j++) {
            if (tbodyDataS[j].produce_date == null) {
                tbodyDataS[j].produce_date = ""
            }
            if (tbodyDataS[j].amount_completed == null) {
                tbodyDataS[j].amount_completed = 0
            }
            if (tbodyDataS[j].pcb_task_status == "已下达未投产") {
                tbodyDataS[j].pcb_task_status = "未启动"
            }
            if (tbodyDataS[j].pcb_task_status == "已下达已投产") {
                tbodyDataS[j].pcb_task_status = "进行中"
            }
            if (tbodyDataS[j].pcb_task_status == "已下达已完成") {
                tbodyDataS[j].pcb_task_status = "已完成"
            }
            if (tbodyDataS[j].produce_plan_date != null) {
                tbodyDataS[j].produce_plan_date = tbodyDataS[j].produce_plan_date.split('T')[0];
            } else {
                tbodyDataS[j].produce_plan_date = ''
            }
            if (tbodyDataS[j].produce_plan_complete_date != null) {
                tbodyDataS[j].produce_plan_complete_date = tbodyDataS[j].produce_plan_complete_date.split('T')[0];
            } else {
                tbodyDataS[j].produce_plan_complete_date = ''
            }
            tbodyHtml +=
                '<li>' +
                '<div class="fontInner clearfix">' +
                '<span style="width:' + arr[0] + '%">' + tbodyDataS[j].pcb_task_code + '</span>' +
                '<span style="width:' + arr[1] + '%">' + tbodyDataS[j].pcb_id + '</span>' +
                '<span style="width:' + arr[2] + '%">' + tbodyDataS[j].pcb_name + '</span>' +
                '<span style="width:' + arr[3] + '%;">' + tbodyDataS[j].task_sheet_code + '</span>' +
                '<span style="width:' + arr[4] + '%;text-align: center;">' + tbodyDataS[j].produce_plan_date + '</span>' +
                '<span style="width:' + arr[5] + '%;text-align: center;">' + tbodyDataS[j].produce_plan_complete_date + '</span>' +
                '<span style="width:' + arr[6] + '%;text-align: center;">' + tbodyDataS[j].pcb_quantity + '</span>' +
                '<span style="width:' + arr[7] + '%;text-align: center;">' + tbodyDataS[j].amount_completed + '</span>' +
                '<span style="width:' + arr[8] + '%;text-align: center;">' + tbodyDataS[j].pcb_task_status + '</span>' +
                '</div>' +
                '</li>';
        }
        tbodyHtml += '</ul></div>';
    } else {
        var theadData = ['工序任务号', '规格型号', '物料名称', '工序', '生产批次', '生产时间', '完成时间', '计划量', '完成量', '工单状态']
        var tbodyData = popData = data;
        theadHtml = '<div class="StateTit">';
        for (var i = 0; i < theadData.length; i++) {
            if (i > 4) {
                theadHtml += '<span style="width:' + arr1[i] + '%;text-align: center;">' + theadData[i] + '</span>';
            } else {
                theadHtml += '<span style="width:' + arr1[i] + '%">' + theadData[i] + '</span>';
            }
        }
        theadHtml += '</div>';
        tbodyHtml = '<div id="FontScroll" class="fontScroll"><ul>';
        var tbodyDataS = tbodyData;
        for (var j = 0; j < tbodyDataS.length; j++) {
            if (tbodyDataS[j].pcb_quantity == null) {
                tbodyDataS[j].pcb_quantity = 0
            }
            if (tbodyDataS[j].amount_completed == null) {
                tbodyDataS[j].amount_completed = 0
            }
            if (tbodyDataS[j].work_time == null) {
                tbodyDataS[j].work_time = 0
            }
            if (tbodyDataS[j].plan_start_time != null) {
                tbodyDataS[j].plan_start_time = tbodyDataS[j].plan_start_time.split('T')[0];
            } else {
                tbodyDataS[j].plan_start_time = ''
            }
            if (tbodyDataS[j].plan_finish_time != null) {
                tbodyDataS[j].plan_finish_time = tbodyDataS[j].plan_finish_time.split('T')[0];
            } else {
                tbodyDataS[j].plan_finish_time = ''
            }
            if (tbodyDataS[j].start_time != null) {
                tbodyDataS[j].start_time = tbodyDataS[j].start_time.split('T')[0];
            } else {
                tbodyDataS[j].start_time = ''
            }
            if (tbodyDataS[j].finish_time != null) {
                tbodyDataS[j].finish_time = tbodyDataS[j].finish_time.split('T')[0];
            } else {
                tbodyDataS[j].finish_time = ''
            }
            if (tbodyDataS[j].amount_completed >= tbodyDataS[j].pcb_quantity) {
                tbodyDataS[j].process_task_status = '已完成'
            } else {
                tbodyDataS[j].process_task_status = '进行中'
            }
            tbodyHtml +=
                '<li>' +
                '<div class="fontInner clearfix">' +
                '<span style="width:' + arr1[0] + '%">' + tbodyDataS[j].process_task_code + '</span>' +
                '<span style="width:' + arr1[1] + '%">' + tbodyDataS[j].pcb_code + '</span>' +
                '<span style="width:' + arr1[2] + '%">' + tbodyDataS[j].pcb_name + '</span>' +
                '<span style="width:' + arr1[3] + '%">' + tbodyDataS[j].process_name + '</span>' +
                '<span style="width:' + arr1[4] + '%">' + tbodyDataS[j].task_sheet_code + '</span>' +
                '<span style="width:' + arr1[5] + '%;text-align: center;">' + tbodyDataS[j].plan_start_time + '</span>' +
                '<span style="width:' + arr1[6] + '%;text-align: center;">' + tbodyDataS[j].plan_finish_time + '</span>' +
                '<span style="width:' + arr1[7] + '%;text-align: center;">' + tbodyDataS[j].pcb_quantity + '</span>' +
                '<span style="width:' + arr1[8] + '%;text-align: center;">' + tbodyDataS[j].amount_completed + '</span>' +
                '<span style="width:' + arr1[9] + '%;text-align: center;">' + tbodyDataS[j].process_task_status + '</span>' +
                '</div>' +
                '</li>';
        }

        tbodyHtml += '</ul></div>';
    }
    $(".firstBoard").html(theadHtml + tbodyHtml);
    $(".firstBoard ul li").off().on('click', popData, function (params) {
        $('.filterbg').show();
        $('.popup').show();
        $('.popup').width('3px');
        $('.popup').animate({ height: '76%' }, 400, function () {
            $('.popup').animate({ width: '82%' }, 400);
        });
        setTimeout(dataShow(popData[$(this).index()], hsaClassOn), 800);
    })
    //运单状态文字滚动
    if (tbodyData.length > 10) {
        $('.fontScroll').FontScroll({ time: 3000, num: 1 });
    }
}

