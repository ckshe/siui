
//第一看板=========================================

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
            fontSize: 18
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
        y: 10
    },
    grid: {
        top: '20%',
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
        name: '百分比',
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 20
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
        symbolSize: 15,   //设定实心点的大小
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
            fontSize: 18,
        }
    },
    legend: {

    },
    grid: {
        top: '10%',
        left: '3%',
        right: '3%',
        bottom: '3%',
        containLabel: true,
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
            }
        },
    },
    yAxis: {
        type: 'value',
        axisLabel: {
            textStyle: {
                show: true,
                color: 'rgba(255,255,255,1)',
                fontSize: 18
            }
        },
        // min: 0,
        // max: 100
        // // data: ['TCL-01-09', 'TCL-01-12', 'TCL-01-13'],
    },
    series: [
        {
            name: '任务量',
            type: 'bar',
            barWidth: 50,
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
                        position: 'top',
                        textStyle: {
                            color: 'white',
                            fontSize: 18
                        }
                    }
                }
            }
        }
    ]
};
var labelTop = function (color, textColor, position) {//未开始/已结束状态下，颜色为gray
    return {
        normal: {
            color: color,
            label: {
                show: true,//标签是否显示
                position: 'center',
                // formatter: '{b}',
                textStyle: {
                    baseline: position || 'bottom',
                    color: textColor
                }
            },
            labelLine: {
                show: false
            }
        }
    };
};
//弹出框调用ECharts饼图
var orderOption1 = {
    title: {
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}:({d}%)',
        textStyle: {
            fontSize: 18
        }
    },
    legend: {
        data: ['已完成', '未完成'],
        // x: 'right',
        textStyle: {
            show: true,
            color: 'rgba(255,255,255,1)',
            fontSize: 16
        },
        y: 10
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
//弹出框调用ECharts饼图
var orderOption2 = {
    title: {
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : ({d}%)',
        textStyle: {
            fontSize: 18
        }
    },
    legend: {
        data: ['已完成', '未完成'],
        // x: 'right',
        textStyle: {
            show: true,
            color: 'rgba(255,255,255,1)',
            fontSize: 16
        },
        y: 10
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
//弹出框调用ECharts饼图
var orderOption3 = {
    title: {
    },
    tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : ({d}%)',
        textStyle: {
            fontSize: 18
        }
    },
    legend: {
        data: ['已完成', '未完成'],
        // x: 'right',
        textStyle: {
            show: true,
            color: 'rgba(255,255,255,1)',
            fontSize: 16
        },
        y: 10
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
var board1Api = {
    pcbTaskBoard:'/ShowBoard/pcbTaskBoard',
    findProcessTaskByDate:'/ShowBoard/findProcessTaskByDate',
    getMapProcessThisWeekRate:'/ShowBoard/getMapProcessThisWeekRate',
}
function setDataBoard1(params) {
    // console.log(board1Api.pcbTaskBoard)
    var url = '/open/excute', data = '';
    var hsaClassOn = $(".button-span button:first").hasClass("on");
    var queryStrDate = "select@@@*@@@from@@@produce_process_task@@@where@@@DateDiff(dd,plan_start_time,getdate())=0";
    var queryStrRate2 = "select@@@*@@@from@@@view_finish_process_day";
    var querytiepeiday = "select@@@*@@@from@@@view_piece_day";
    var queryWeldday = "select@@@*@@@from@@@view_weld_day";
    var queryTestday = "select@@@*@@@from@@@view_test_day";
    var queryTaskFinishWeek = "select@@@*@@@from@@@view_task_finish_week";
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
                console.log("abc==",response)
                addHtml(response.data.pcbTasks, hsaClassOn);
                setOption(response.data);
            }
        });
    }
    function setOption(response) {
        db1P1.clear();
        db1P2.clear();
        order1.clear();
        order2.clear();
        order3.clear();
        var hsaClassOn = $(".button-span button:first").hasClass("on");
        if (hsaClassOn) {
            var weeks = response.mapWeekRate
            var weekArrdata = [weeks.week1, weeks.week2, weeks.week3, weeks.week4]
            db1P1Option.series[0].data = weekArrdata;
            db1P1Option.xAxis.data =["第一周", "第二周", "第三周", "第四周"]
            db1P1.setOption(db1P1Option);
            $('.box1 .basicInfo  .border-green').html('周任务达成率')
            var mapProcessWeekRate = response.mapProcessWeekRate;
            mapProcessWeekRate.tiepian = 0;
            mapProcessWeekRate.houhan = 0;
            mapProcessWeekRate.tiaoshi = 0;
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
            var taskFinishRateArr =[];
            for(var i=0;i<response.taskFinishRate.length;i++){
                taskFinishRateAxisArr.push(response.taskFinishRate[i].task_sheet_code);
                taskFinishRateArr.push(response.taskFinishRate[i].rate);
            }

            db1P2Option.xAxis.data = taskFinishRateAxisArr;
            db1P2Option.yAxis.max = 100;
            db1P2Option.yAxis.min = 0;
            db1P2Option.series[0].data = taskFinishRateArr;
            db1P2.setOption(db1P2Option);
            $('.box1 .basicInfo .border-blue').html('各批次完成率')
        } else {
            $.ajax({
                contentType: 'application/json',
                type: 'get',
                url: board1Api.getMapProcessThisWeekRate,
                dataType: "json",
                success: function (response) {
                    console.log("cds==",response)
                    var weekRateArr = [],axisWeekRateArr=[];
                    for(var i=0;i<response.data.length;i++){
                        weekRateArr.push(response.data[i].rate)
                        axisWeekRateArr.push(response.data[i].theDay)
                    }
                    db1P1Option.series[0].data = weekRateArr;
                    console.log(weekRateArr)
                    axisWeekRateArr = ['周一','周二','周三','周四','周五','周六']
                    db1P1Option.xAxis.data = axisWeekRateArr;
                    db1P1.setOption(db1P1Option);
                }
            });
            $('.box1 .basicInfo .border-green').html('日任务达成率')

            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryTaskFinishWeek)),
                success: function (message) {
                    // console.log(message)
                    var weekArr1 = [0, 0, 0, 0, 0, 0, 0];
                    for (var i = 0; i < message.data.length; i++) {
                        var index = new Date(message.data[i].produce_plan_complete_date).getDay();
                        weekArr1[index] = message.data[i].finish_num;
                        // sum += message.data[i].count;
                    }
                    db1P2Option.series[0].data = weekArr1
                    db1P2.setOption(db1P2Option);
                }
            });

            $('.box1 .basicInfo .border-blue').html('周任务完成数量')

            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(querytiepeiday)),
                success: function (message) {

                    if (message.data[0].sum_piece == 0)
                        message.data[0].sum_piece = 1;
                    orderOption1.series[0].data = [{
                        name: '已完成',
                        value: Math.floor((message.data[0].finish_piece / message.data[0].sum_piece) * 100)
                    }, {
                        name: '未完成',
                        value: 100 - Math.floor((message.data[0].finish_piece / message.data[0].sum_piece) * 100)
                    }]
                    order1.setOption(orderOption1);
                }
            });
            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryWeldday)),
                success: function (message) {
                    // console.log(message)
                    if (message.data[0].sum_weld == 0)
                        message.data[0].sum_weld = 1;
                    orderOption2.series[0].data = [{
                        name: '已完成',
                        value: Math.floor((message.data[0].finish_weld / message.data[0].sum_weld) * 100)
                    }, {
                        name: '未完成',
                        value: 100 - Math.floor((message.data[0].finish_weld / message.data[0].sum_weld) * 100)
                    }]
                    order2.setOption(orderOption2);
                }
            });
            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryTestday)),
                success: function (message) {
                    // console.log(message)
                    if (message.data[0].sum_test == 0)
                        message.data[0].sum_test = 1;
                    orderOption3.series[0].data = [{
                        name: '已完成',
                        value: Math.floor((message.data[0].finish_test / message.data[0].sum_test) * 100)
                    }, {
                        name: '未完成',
                        value: 100 - Math.floor((message.data[0].finish_test / message.data[0].sum_test) * 100)
                    }]
                    order3.setOption(orderOption3);
                }
            });
            $('.box1  .basicInfo .border-yellow').html('产线日任务达成率')
        }
    }

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
                    // console.log("message===", response)
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
                    console.log("abc==",response)
                    addHtml(response.data, hsaClassOn);
                    setOption(response.data);
                }
            });
        }
    })
    function addHtml(data, hsaClassOn) {
        // 动态生成模板
        var theadHtml = '', tbodyHtml = '', widthPercent = 1, popData = '';
        var widthWW = parseInt($('#firstBoard').css('width'));
        if (hsaClassOn) {
            var theadData = ['生产任务单号', '机型名称', '规格型号', '物料名称', '生产批次', '启动日期', '完成时间', '生产数量', '完成数量', '工单状态'];
            var tbodyData = data;
            if (theadData.length > 0) {
                widthPercent = ((widthWW / theadData.length).toFixed(1) - 11) + "px"
            }
            theadHtml = '<div class="StateTit">';
            for (var i = 0; i < theadData.length; i++) {
                theadHtml += '<span style="width:' + widthPercent + '">' + theadData[i] + '</span>';
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
                tbodyHtml +=
                    '<li>' +
                    '<div class="fontInner clearfix">' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].pcb_task_code + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].model_name + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].model_ver + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].pcb_name + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].task_sheet_code + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].produce_plan_date.split('T')[0] + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].produce_date + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].pcb_quantity + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].amount_completed + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].pcb_task_status + '</span>' +
                    '</div>' +
                    '</li>';
            }
            tbodyHtml += '</ul></div>';
        } else {
            var theadData = ['工序任务号', '工序', '计划生产时间', '实际生产时间', '计划完成时间', '生产完成时间', '计划生产数量', '完成生产数量', '工单状态', '工时 (分)']
            console.log(data)
            var tbodyData = popData = data;
            if (theadData.length > 0) {
                widthPercent = ((widthWW / theadData.length).toFixed(1) - 11) + "px"
            }
            theadHtml = '<div class="StateTit">';
            for (var i = 0; i < theadData.length; i++) {
                theadHtml += '<span style="width:' + widthPercent + '">' + theadData[i] + '</span>';
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
                tbodyHtml +=
                    '<li>' +
                    '<div class="fontInner clearfix">' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].pcb_task_code + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].process_name + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].plan_start_time.split("T")[0] + '</span>' +
                    '<span style="width:' + widthPercent + '"></span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].plan_finish_time.split("T")[0] + '</span>' +
                    '<span style="width:' + widthPercent + '"></span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].pcb_quantity + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].amount_completed + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].process_task_status + '</span>' +
                    '<span style="width:' + widthPercent + '">' + tbodyDataS[j].work_time + '</span>' +
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
        if (tbodyData.length > 12) {
            $('.fontScroll').FontScroll({ time: 3000, num: 1 });
        }
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
        if (data.pcb_task_status == "已下达已投产") {
            data.pcb_task_status = "进行中"
        }
        if (data.pcb_task_status == "已下达已完成") {
            data.pcb_task_status = "已完成"
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
            '               <span>规格型号:<strong>' + data.model_ver + '</strong></span>' +
            '               <span class="col2">物料名称:<strong>' + data.pcb_name + '</strong></span>' +
            '               <span>生产批次:<strong>' + data.task_sheet_code + '</strong></span>' +
            '               <span>启动日期:<strong>' + data.produce_plan_date.split('T')[0] + '</strong></span>' +
            '               <span>完成时间:<strong>' + data.produce_date + '</strong></span>' +
            '               <span>生产数量:<strong>' + data.pcb_quantity + '</strong></span>' +
            '               <span>完成数量:<strong>' + data.amount_completed + '</strong></span>' +
            '               <span>工单状态:<strong>' + data.pcb_task_status + '</strong></span>' +
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
        $(".summary").html(theadHtmlP1).css("display", "block");
    }
    function addProcessDataHtml(data) {
        var theadHtmlP1 = '<div class="item summaryP1" style="">' +
            '   <div class="itemTit">' +
            '       <span class="border-blue">任务详情</span>' +
            '   </div>' +
            '   <div class="itemCon itembg itembg_popupfirt">' +
            '       <ul class="listStyle">' +
            '           <li class="clearfix">' +
            '               <span>制造编号:<strong>' + data.task_sheet_code + '</strong></span>' +
            '               <span>机型版本:<strong>' + data.pcb_name + '</strong></span>' +
            '               <span>pcb编码:<strong>' + data.pcb_code + '</strong></span>' +
            '               <span>RoHS:<strong>' + data.is_rohs + '</strong></span>' +
            '               <span>机台名称:<strong>' + data.device_name + '</strong></span>' +
            '               <span>机台编号:<strong>' + data.device_code + '</strong></span>' +
            '               <span>工序单状态:<strong>' + data.process_task_status + '</strong></span>' +
            '               <span>工序订单编号:<strong>' + data.process_task_code + '</strong></span>' +
            '               <span>是否当前工单:<strong>' + data.is_now_flag + '</strong></span>' +
            '               <span class="col2">PCB名称:<strong>' + data.pcb_name + '</strong></span>' +
            '           </li>' +
            '       </ul>' +
            '   </div>' +
            '</div>';
        $(".summary").html(theadHtmlP1).css("display", "block");
    }
}





