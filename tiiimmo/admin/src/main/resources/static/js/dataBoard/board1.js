
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
        formatter: '{a} <br/>{b} : {c}%',
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
        max:100,
        min:0,
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
        data: ['周一', '周二', '周三', '周四', '周五','周六','周日'],
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
            data: [85, 90, 88, 98, 68,12,0],
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
        data: ['已完成','未完成'],
        // x: 'right',
        textStyle: {
            show: true,
            color: 'rgba(255,255,255,1)',
            fontSize:16
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
                        formatter: function(prams){
                            var value;
                            if(prams.data.value==0){
                                value = ''
                            }else{
                                value = prams.data.value+"%"
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
        data: ['已完成','未完成'],
        // x: 'right',
        textStyle: {
            show: true,
            color: 'rgba(255,255,255,1)',
            fontSize:16
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
                        formatter: function(prams){
                            var value;
                            if(prams.data.value==0){
                                value = ''
                            }else{
                                value = prams.data.value+"%"
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
        data: ['已完成','未完成'],
        // x: 'right',
        textStyle: {
            show: true,
            color: 'rgba(255,255,255,1)',
            fontSize:16
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
                        formatter: function(prams){
                            var value;
                            if(prams.data.value==0){
                                value = ''
                            }else{
                                value = prams.data.value+"%"
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



function setDataBoard1(params) {
    var url = '/open/excute',data='';
    var hsaClassOn = $(".button-span button:first").hasClass("on");
    // var queryStrWeek = "select@@@*@@@from@@@produce_pcb_task@@@where@@@datediff(week,produce_plan_date,'2020-06-10')=0;"
    var queryStrWeek = "select@@@*@@@from@@@produce_pcb_task@@@where@@@datediff(week,produce_plan_date,GETDATE())=0;"
    var queryStrDate = "select@@@*@@@from@@@produce_process_task@@@where@@@DateDiff(dd,plan_start_time,getdate())=0";
    var queryStrRate1 = "select@@@*@@@from@@@view_produce_pcb_task";
    var queryStrRate2 = "select@@@*@@@from@@@view_finish_process_day";
    var queryTiepeiweek = "select@@@*@@@from@@@view_piece_week";
    var queryWeldiweek = "select@@@*@@@from@@@view_weld_week";
    var queryTestiweek = "select@@@*@@@from@@@view_test_week";
    var querytiepeiday = "select@@@*@@@from@@@view_piece_day";
    var queryWeldday = "select@@@*@@@from@@@view_weld_day";
    var queryTestday = "select@@@*@@@from@@@view_test_day";
    var queryTaskFinishWeek = "select@@@*@@@from@@@view_task_finish_week";
    if(hsaClassOn){
        data = returnData(queryStrWeek)
    }else{
        data = returnData(queryStrDate)
    }
    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: url,
        dataType: "json",
        data: JSON.stringify(data),
        success: function (message) {
            addHtml(message,hsaClassOn);
        }
    });
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
                    var sum=0,sumtemp=0 ,weekArr = [0,0,0,0,0,0,0],weekArrdata=[];
                    for(var i=0;i<message.data.length;i++){
                        var index = new Date(message.data[i].produce_plan_complete_date).getDay();
                        weekArr[index] = message.data[i].allcount
                        sum += message.data[i].count;
                    }
                    // console.log(new Date().getDay())
                    for(var j=0;j<new Date().getDay();j++){
                        sumtemp+=weekArr[j];
                        weekArrdata.push(((sumtemp/sum)*100).toFixed(2))
                    }
                    db1P1Option.series[0].data = weekArrdata;
                    db1P1.setOption(db1P1Option);
                }
            });

            $('.box1 .basicInfo  .border-green').html('周任务达成率')
            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryTiepeiweek)),
                success: function (message) {
                    console.log(message)
                    if(message.data[0].sum_piece==0)
                        message.data[0].sum_piece = 1;

                    orderOption1.series[0].data = [{
                        name: '已完成',
                        value: Math.floor((message.data[0].finish_piece/message.data[0].sum_piece)*100)
                    }, {
                        name: '未完成',
                        value: 100-Math.floor((message.data[0].finish_piece/message.data[0].sum_piece)*100)
                    }]
                    order1.setOption(orderOption1);
                }
            });
            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryWeldiweek)),
                success: function (message) {
                    
                    if(message.data[0].sum_weld==0)
                        message.data[0].sum_weld = 1;
                    orderOption2.series[0].data = [{
                        name: '已完成',
                        value: Math.floor((message.data[0].finish_weld/message.data[0].sum_weld)*100)
                    }, {
                        name: '未完成',
                        value: 100-Math.floor((message.data[0].finish_weld/message.data[0].sum_weld)*100)
                    }]
                    order2.setOption(orderOption2);
                }
            });
            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryTestiweek)),
                success: function (message) {
                    
                    if(message.data[0].sum_test==0)
                    message.data[0].sum_test = 1;
                    orderOption3.series[0].data = [{
                        name: '已完成',
                        value:  Math.floor((message.data[0].finish_test/message.data[0].sum_test)*100)
                    }, {
                        name: '未完成',
                        value: 100-Math.floor((message.data[0].finish_test/message.data[0].sum_test)*100)
                    }]
                    order3.setOption(orderOption3);
                }
            });
            $('.box1 .basicInfo  .border-yellow').html('产线周任务达成率')

            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryTaskFinishWeek)),
                success: function (message) {
                    // console.log(message)
                    var weekArr1 = [0,0,0,0,0,0,0];
                    for(var i=0;i<message.data.length;i++){
                        var index = new Date(message.data[i].produce_plan_complete_date).getDay();
                        weekArr1[index] = message.data[i].finish_num;
                        // sum += message.data[i].count;
                    }
                    db1P2Option.series[0].data = weekArr1
                    db1P2.setOption(db1P2Option);
                }
            });

            $('.box1 .basicInfo .border-blue').html('周任务完成数量')

        } else {
            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(queryStrRate2)),
                success: function (message) {
                    var weekArr = [0,0,0,0,0,0,0];
                    for(var i=0;i<message.data.length;i++){
                        var index = new Date(message.data[i].plan_finish_time).getDay();
                        weekArr[index] = Math.floor((message.data[i].finish_count/message.data[i].sum_count)*100)
                        // sum += message.data[i].count;
                    }
                    db1P1Option.series[0].data = weekArr;
                    db1P1.setOption(db1P1Option);
                }
            });
            $('.box1 .basicInfo .border-green').html('日任务达成率')

            db1P2Option.series[0].data = [85, 90, 88, 98, 68,23,0]
            db1P2.setOption(db1P2Option);
            $('.box1 .basicInfo .border-blue').html('各批次完成率')

            $.ajax({
                contentType: 'application/json',
                type: 'POST',
                url: url,
                dataType: "json",
                data: JSON.stringify(returnData(querytiepeiday)),
                success: function (message) {
                    
                    if(message.data[0].sum_piece==0)
                    message.data[0].sum_piece = 1;
                    orderOption1.series[0].data = [{
                        name: '已完成',
                        value:  Math.floor((message.data[0].finish_piece/message.data[0].sum_piece)*100)
                    }, {
                        name: '未完成',
                        value: 100-Math.floor((message.data[0].finish_piece/message.data[0].sum_piece)*100)
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
                    if(message.data[0].sum_weld==0)
                    message.data[0].sum_weld = 1;
                    orderOption2.series[0].data = [{
                        name: '已完成',
                        value:  Math.floor((message.data[0].finish_weld/message.data[0].sum_weld)*100)
                    }, {
                        name: '未完成',
                        value: 100-Math.floor((message.data[0].finish_weld/message.data[0].sum_weld)*100)
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
                    if(message.data[0].sum_test==0)
                    message.data[0].sum_test = 1;
                    orderOption3.series[0].data = [{
                        name: '已完成',
                        value:  Math.floor((message.data[0].finish_test/message.data[0].sum_test)*100)
                    }, {
                        name: '未完成',
                        value: 100-Math.floor((message.data[0].finish_test/message.data[0].sum_test)*100)
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
        if(hsaClassOn){
            data = returnData(queryStrWeek)
        }else{
            data = returnData(queryStrDate)
        }
        //console.log(JSON.stringify(data))
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: url,
            dataType: "json",
            data: JSON.stringify(data),
            success: function (message) {
                addHtml(message,hsaClassOn);
            }
        });
        setOption();
    })
    function addHtml(message,hsaClassOn) {
        // 动态生成模板
        var theadHtml = '', tbodyHtml = '', widthPercent = 1;
        if (hsaClassOn) {
            var theadData = ['生产任务单号', '机型名称', '规格型号', '物料名称', '生产批次', '启动日期', '完成时间', '生产数量', '完成数量', '工单状态'];
            //console.log(message.data)
            var tbodyData = message.data;
            if (theadData.length > 0) {
                widthPercent = (99 / theadData.length).toFixed(2)
            }
            theadHtml = '<div class="StateTit">';
            for (var i = 0; i < theadData.length; i++) {
                theadHtml += '<span style="width:' + widthPercent + '%">' + theadData[i] + '</span>';
            }
            theadHtml += '</div>';
            tbodyHtml = '<div id="FontScroll" class="fontScroll"><ul><div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">';
            var tbodyDataS = tbodyData;
            for (var j = 0; j < tbodyDataS.length; j++) {
                if(tbodyDataS[j].produce_date==null){
                    tbodyDataS[j].produce_date = ""
                }
                if(tbodyDataS[j].amount_completed==null){
                    tbodyDataS[j].amount_completed = 0
                }
                tbodyHtml += '<div class="panel panel-default">' +
				'<div class="panel-heading" role="tab" id="headingOne">' +
					'<h4 class="panel-title">' +
						'<a role="button" data-toggle="collapse" data-parent="#accordion" href="#'+tbodyDataS[j].pcb_task_code+'" aria-expanded="false" aria-controls="collapseOne" class="collapsed">' +
                    '<li>' +
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
                    '</li>'+
                    '</a></h4></div>'+
                    '<div id="'+tbodyDataS[j].pcb_task_code+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">'+
                        '<div class="panel-body">'+
                            '<div class="fontInner clearfix">'+
                                '<span style="width:' + widthPercent + '%">光板号</span>'+
                                '<span style="width:' + widthPercent + '%">通知日期</span>'+
                                '<span style="width:' + widthPercent + '%">厂区</span>'+
                                '<span style="width:' + widthPercent + '%">车间</span>'+
                                '<span style="width:' + widthPercent + '%">板编号</span>'+
                                '<span style="width:' + widthPercent + '%">投料单号</span>'+
                                '<span style="width:' + widthPercent + '%">计划启动时间</span>'+
                                '<span style="width:' + widthPercent + '%">计划完成时间</span>'+
                                '<span style="width:' + widthPercent + '%">优先级</span>'+
                                '<span style="width:' + widthPercent + '%"></span>'+
                            '</div>'+
                            '<div class="fontInner clearfix">'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_plate_id + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].task_sheet_date + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].factory + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].workshop + '</span>'+
                            '<span style="width:9.90%">' + tbodyDataS[j].batch_id + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].feeding_task_code + '</span>'+
                            '<span style="width:9.90%">' + tbodyDataS[j].produce_plan_date.split('T')[0] + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].produce_plan_complete_date.split('T')[0] + '</span>'+
                            '<span style="width:9.90%">' + tbodyDataS[j].priority + '</span>'+
                            '<span style="width:' + widthPercent + '%"></span>'+
                            '</div>'+
                        '</div>'+
                    '</div>'+
                    '</div>';
            }
            tbodyHtml += '</div></ul></div>';
        } else {
            var theadData = ['工序任务号', '工序', '计划生产时间', '实际生产时间', '计划完成时间', '生产完成时间', '计划生产数量', '完成生产数量', '工单状态','工时']
            //console.log(message.data)
            var tbodyData = message.data;

            if (theadData.length > 0) {
                widthPercent = (99 / theadData.length).toFixed(2)
            }
            theadHtml = '<div class="StateTit">';
            for (var i = 0; i < theadData.length; i++) {
                theadHtml += '<span style="width:' + widthPercent + '%">' + theadData[i] + '</span>';
            }
            theadHtml += '</div>';
            tbodyHtml = '<div id="FontScroll" class="fontScroll"><ul><div class="panel-group" id="accordion" role="tablist" aria-multiselectable="false">';
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
                tbodyHtml += '<div class="panel panel-default">' +
				'<div class="panel-heading" role="tab" id="headingOne">' +
					'<h4 class="panel-title">' +
						'<a role="button" data-toggle="collapse" data-parent="#accordion" href="#pcb_'+j+'" aria-expanded="false" aria-controls="collapseOne" class="collapsed">' +
                '<li>' +
                    '<div class="fontInner clearfix">' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_task_code + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].process_name + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].plan_start_time.split("T")[0] + '</span>' +
                    '<span style="width:' + widthPercent + '%"></span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].plan_finish_time.split("T")[0] + '</span>' +
                    '<span style="width:' + widthPercent + '%"></span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_quantity+ '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].amount_completed + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].process_task_status + '</span>' +
                    '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].work_time + '</span>' +
                    '</div>' +
                    '</li>'+
                    '</a></h4></div>'+
                    '<div id="pcb_'+j+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">'+
                        '<div class="panel-body">'+
                            '<div class="fontInner clearfix">'+
                                '<span style="width:' + widthPercent + '%">制造编号</span>'+
                                '<span style="width:' + widthPercent + '%">机型版本</span>'+
                                '<span style="width:' + widthPercent + '%">PCB名称</span>'+
                                '<span style="width:' + widthPercent + '%">pcb编码</span>'+
                                '<span style="width:' + widthPercent + '%">RoHS</span>'+
                                '<span style="width:' + widthPercent + '%">机台名称</span>'+
                                '<span style="width:' + widthPercent + '%">机台编号</span>'+
                                '<span style="width:' + widthPercent + '%">工序单状态</span>'+
                                '<span style="width:' + widthPercent + '%">工序订单编号</span>'+
                                '<span style="width:' + widthPercent + '%">是否当前工单</span>'+
                            '</div>'+
                            '<div class="fontInner clearfix">'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].task_sheet_code + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].model_ver + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_name + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].pcb_code + '</span>'+
                            '<span style="width:9.90%">' + tbodyDataS[j].is_rohs + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].device_name + '</span>'+
                            '<span style="width:9.90%">' + tbodyDataS[j].device_code + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].process_task_status+ '</span>'+
                            '<span style="width:9.90%">' + tbodyDataS[j].process_task_code + '</span>'+
                            '<span style="width:' + widthPercent + '%">' + tbodyDataS[j].is_now_flag + '</span>'+
                            '</div>'+
                        '</div>'+
                    '</div>'+
                    '</div>';
            }
            
            tbodyHtml += '</div></ul></div>';
        }
        $(".firstBoard").html(theadHtml + tbodyHtml);

        //运单状态文字滚动
        if(tbodyData.length>12) {
            $('.fontScroll').FontScroll({time: 3000, num: 1});
        }
    }
}





