
//第一看板=========================================
// setTimeout(function(){
//     setDataBoard1();
// },500);

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
        // formatter: '{a} <br/>{b} : {c} ({d}%)',
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
        data: ["周一", "周二", "周三", "周四", "周五", "周六"],
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
        data: ['TCL-01-09', 'TCL-01-10', 'TCL-01-11', 'TCL-01-12', 'TCL-01-13'],
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
        min: 0,
        max: 100
        // data: ['TCL-01-09', 'TCL-01-12', 'TCL-01-13'],
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
            data: [85, 90, 88, 98, 68],
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
        formatter: '{a} <br/>{b} : {c} ({d}%)',
        textStyle: {
            fontSize: 18
        }
    },
    legend: {
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: '80%',
            center: ['50%', '50%'],
            data: [
                { value: 10, name: '已完成' },
                { value: 20, name: '未完成' }
            ],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inner',
                        formatter: '{c}',
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
        formatter: '{a} <br/>{b} : {c} ({d}%)',
        textStyle: {
            fontSize: 18
        }
    },
    legend: {
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: '80%',
            center: ['50%', '50%'],
            data: [
                { value: 10, name: '已完成' },
                { value: 20, name: '未完成' }
            ],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inner',
                        formatter: '{c}',
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
        formatter: '{a} <br/>{b} : {c} ({d}%)',
        textStyle: {
            fontSize: 18
        }
    },
    legend: {
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: '80%',
            center: ['50%', '50%'],
            data: [
                { value: 10, name: '已完成' },
                { value: 20, name: '未完成' }
            ],
            itemStyle: {
                normal: {
                    label: {
                        show: true,
                        position: 'inner',
                        formatter: '{c}',
                        fontSize: 20
                    }
                }
            }
        }
    ]
}



function setDataBoard1(params) {
    var url = '/open/excute',data='';
    var hsaClassOn = $(".button-span button:first").hasClass("on");
    var queryStrWeek = "select@@@*@@@from@@@produce_pcb_task@@@where@@@datediff(week,produce_plan_date,'2020-06-10')=0;"
    // var queryStrWeek = "select@@@*@@@from@@@produce_pcb_task@@@where@@@datediff(week,produce_plan_date,GETDATE())=0;"
    var queryStrDate = "select@@@*@@@from@@@produce_process_task@@@where@@@DateDiff(dd,plan_start_time,getdate())=0";
    var queryStrRate1 = "select@@@*@@@from@@@view_produce_pcb_task"
    if(hsaClassOn){
        data = returnData(queryStrWeek)
    }else{
        data = returnData(queryStrDate)
    }
    console.log(JSON.stringify(data))
    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: url,
        dataType: "json",
        data: JSON.stringify(data),
        success: function (message) {
            console.log(message)
            addHtml(message,hsaClassOn);
        }
    });
    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: url,
        dataType: "json",
        data: JSON.stringify(returnData(queryStrRate1)),
        success: function (message) {
            console.log("百分比==",message)
            for(var i=0;i<message.data.length;i++){
                console.log(new Date(message.data[i].produce_plan_complete_date).getDay())
            }

        }
    });

    console.log("进入DataBoard133553")
    setOption();
    function setOption() {
        db1P1.clear();
        db1P2.clear();
        order1.clear();
        order2.clear();
        order3.clear();
        var hsaClassOn = $(".button-span button:first").hasClass("on");
        if (hsaClassOn) {
            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryStrRate1)),
                success: function (message) {
                    console.log("百分比==",message)
                    for(var i=0;i<message.data.length;i++){
                        console.log(new Date(message.data[i].produce_plan_complete_date).getDay())
                    }
                    db1P1Option.series[0].data = [10, 20, 35, 60, 75, 20]
                    db1P1.setOption(db1P1Option);
                }
            });

            $('.box1 .basicInfo  .border-green').html('周任务达成率')
            orderOption1.series[0].data = [{
                name: '已完成',
                value: 10
            }, {
                name: '未完成',
                value: 20
            }]
            orderOption2.series[0].data = [{
                name: '已完成',
                value: 10
            }, {
                name: '未完成',
                value: 20
            }]
            orderOption3.series[0].data = [{
                name: '已完成',
                value: 10
            }, {
                name: '未完成',
                value: 20
            }]
            order1.setOption(orderOption1);
            order2.setOption(orderOption2);
            order3.setOption(orderOption3);
            $('.box1 .basicInfo  .border-yellow').html('产线周任务达成率')

            db1P2Option.series[0].data = [85, 90, 88, 98, 68]
            db1P2.setOption(db1P2Option);
            $('.box1 .basicInfo .border-blue').html('各批次完成率')
        } else {
            db1P1Option.series[0].data = [30, 30, 45, 60, 75, 80]
            db1P1.setOption(db1P1Option);
            $('.box1 .basicInfo .border-green').html('日任务达成率')
            db1P2Option.series[0].data = [85, 75, 88, 86, 81]
            db1P2.setOption(db1P2Option);
            $('.box1 .basicInfo .border-blue').html('周任务完成数量')
            orderOption1.series[0].data = [{
                name: '已完成',
                value: 60
            }, {
                name: '未完成',
                value: 80
            }]
            orderOption2.series[0].data = [{
                name: '已完成',
                value: 74
            }, {
                name: '未完成',
                value: 98
            }]
            orderOption3.series[0].data = [{
                name: '已完成',
                value: 33
            }, {
                name: '未完成',
                value: 54
            }]
            $('.box1  .basicInfo .border-yellow').html('产线日任务达成率')
            order1.setOption(orderOption1);
            order2.setOption(orderOption2);
            order3.setOption(orderOption3);
        }
    }

    $(".button-span button").off().on('click', function () {
        $(this).siblings('button').removeClass('on');
        $(this).addClass('on');
        var hsaClassOn = $(".button-span button:first").hasClass("on");
        if(hsaClassOn){
            data = returnData(queryStrWeek)
        }else{
            data = returnData(queryStrDate)
        }
        console.log(JSON.stringify(data))
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: url,
            dataType: "json",
            data: JSON.stringify(data),
            success: function (message) {
                console.log(message)
                addHtml(message,hsaClassOn);
            }
        });
        setOption();
    })
    function addHtml(message,hsaClassOn) {
        // 动态生成模板
        var theadHtml = '', tbodyHtml = '', widthPercent = 1;
        if (hsaClassOn) {
            var theadData = ['生产任务单号', '机型名称', '规格型号', '物料名称', '生产批次', '计划启动日期', '计划完成时间', '计划生产数量', '完成数量', '工单状态'];
            console.log(message.data)
            var tbodyData = message.data;
            if (theadData.length > 0) {
                widthPercent = (99 / theadData.length).toFixed(2)
            }
            theadHtml = '<div class="StateTit">';
            for (var i = 0; i < theadData.length; i++) {
                theadHtml += '<span style="width:' + widthPercent + '%">' + theadData[i] + '</span>';
            }
            theadHtml += '</div>';
            tbodyHtml = '<div id="FontScroll" class="fontScroll"><ul>';
            var tbodyDataS = tbodyData;
            for (var j = 0; j < tbodyDataS.length; j++) {
                if(tbodyDataS[j].produce_date==null){
                    tbodyDataS[j].produce_date = ""
                }
                if(tbodyDataS[j].amount_completed==null){
                    tbodyDataS[j].amount_completed = 0
                }
                tbodyHtml += '<li>' +
                    '<div class="fontInner clearfix">' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_task_code + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].model_name + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].model_ver + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_name + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].task_sheet_code + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].produce_plan_date.split('T')[0] + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].produce_date + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_quantity + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].amount_completed + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_task_status + '</span>' +
                    '</div>' +
                    '</li>';
            }
            tbodyHtml += '<ul></div>';
        } else {
            var theadData = ['工序任务号', '规格型号', '物料名称', '生产任务单号', '生产批次', '工序', '计划生产时间', '计划生产数量', '完成生产数量', '工时', '工单状态']
            console.log(message.data)
            var tbodyData = message.data;

            if (theadData.length > 0) {
                widthPercent = (99 / theadData.length).toFixed(2)
            }
            theadHtml = '<div class="StateTit">';
            for (var i = 0; i < theadData.length; i++) {
                theadHtml += '<span style="width:' + widthPercent + '%">' + theadData[i] + '</span>';
            }
            theadHtml += '</div>';
            tbodyHtml = '<div id="FontScroll" class="fontScroll"><ul>';
            var tbodyDataS = tbodyData;
            for (var j = 0; j < tbodyDataS.length; j++) {
                if(tbodyDataS[j].pcb_quantity==null){
                    tbodyDataS[j].pcb_quantity = 0
                }
                if(tbodyDataS[j].amount_completed==null){
                    tbodyDataS[j].amount_completed = 0
                }
                if(tbodyDataS[j].work_time==null){
                    tbodyDataS[j].work_time = 0
                }
                tbodyHtml += '<li>' +
                    '<div class="fontInner clearfix">' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].task_sheet_code + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_code + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_name + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_task_code + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].process_task_code + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].process_name + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].plan_start_time.split("T")[0] + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_quantity + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].amount_completed + '</span>' +
                    '<span style="width:' + widthPercent + '%">' +  tbodyDataS[j].work_time + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].process_task_status + '</span>' +
                    '</div>' +
                    '</li>';
            }
            tbodyHtml += '<ul></div>';
        }
        $(".firstBoard").html(theadHtml + tbodyHtml);

        //运单状态文字滚动
        if(tbodyData.length>12) {
            $('.fontScroll').FontScroll({time: 3000, num: 1});
        }
    }
}





