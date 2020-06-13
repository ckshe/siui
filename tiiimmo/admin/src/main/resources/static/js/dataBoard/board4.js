
// 第四看板========================================================================
//看板四弹框

var db4P1, db4P2;
db4P1 = echarts.init(document.getElementById('db4P1'), 'macarons');
db4P2 = echarts.init(document.getElementById('db4P2'), 'macarons');
var db4POption1 = {
	title: {
		text: "上岗率",
		textStyle: {
			// textShadowColor: "#fff",
			// textShadowBlur: 4,
			// textShadowOffsetX: 2,
			// textShadowOffsetY: 2,
			color: '#fff',
			fontSize: 22,
			fontWeight: 'normal',
		},
		top: 'top',
		right: 'center',
	},
	tooltip: {
		textStyle: {
			color: '#fff',
			fontSize: 22,
			fontWeight: 'normal',
		},
		formatter: '{b} <br/>{a} : {c}%',
	},
	grid: {
		top: "10%",
		right: '10%',
		bottom: "10%",
		left: "10%",
		backgroundColor: '#ff330e'
	},
	xAxis: {
		naem: '工序',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			}
		},
	},
	yAxis: {
		name: '',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			},
			formatter:function(value){
                return value+'%'
            }
		},
		axisLine: {
			lineStyle: {
				color: '#fff',
				fontSize: 20
			}
		},
		min: 0,
		max: 100
	},
	series: [{
		name: '上岗率',
		type: 'bar',
		data: [],
		barWidth: 50,
		label: {
			normal: {
				show: true,
				position: 'inside',
				formatter: '{c}%',
				color: '#fff',
				fontSize: 20
			}
		},
	}]
};
var db4POption2 = {
	title: {
		text: "各工序工时利用率",
		textStyle: {
			// textShadowColor: "#fff",
			// textShadowBlur: 4,
			// textShadowOffsetX: 2,
			// textShadowOffsetY: 2,
			color: '#fff',
			fontSize: 22,
			fontWeight: 'normal',
		},
		top: 'top',
		right: 'center',
	},
	tooltip: {
		textStyle: {
			color: '#fff',
			fontSize: 22,
			fontWeight: 'normal',
		},
		formatter: '{b} <br/>{a} : {c}%',
	},
	grid: {
		top: "10%",
		right: '10%',
		bottom: "10%",
		left: "10%",
		backgroundColor: '#ff330e'
	},
	xAxis: {
		naem: '工序',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			}
		},
		data: []
	},
	yAxis: {
		name: '',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			},
			formatter:function(value){
                return value+'%'
            }
		},
		axisLine: {
			lineStyle: {
				color: '#fff',
				fontSize: 20
			}
		},
		min: 0,
		max: 100
	},
	series: [{
		name: '工时利用率',
		type: 'bar',
		data: [],
		barWidth: 50,
		label: {
			normal: {
				show: true,
				position: 'inside',
				formatter: '{c}%',
				color: '#fff',
				fontSize: 20
			}
		},
	}]
};
// 动态生成模板
function addfourBoardHtml(data) {
	var theadHtml = '', tbodyHtml = '',tbodyData = data;
	console.log(tbodyData.length)
	var theadData = ['员工名称', '工位', '所在工序', '任务信息', '当前完成率']
	colwidth = (($("#fourBoard").width() / theadData.length) - 10) + "px";
	theadHtml = '<div class="StateTit">';
	for (var i = 0; i < theadData.length; i++) {
		theadHtml += '<span  style="width:' + colwidth + '">' + theadData[i] + '</span>';
	}
	theadHtml += '</div>';
	tbodyHtml = '<div id="FontScroll" class="board4Scroll"><ul>';
	for (var j = 0; j < tbodyData.length; j++) {
		tbodyHtml += '<li>' +
			'<div class="fontInner clearfix">' +
			'<span  style="width:' + colwidth + '">' + tbodyData[j].user_name + '</span>' +
			'<span  style="width:' + colwidth + '">' + tbodyData[j].device_code + '</span>' +
			'<span  style="width:' + colwidth + '">' + tbodyData[j].process_name + '</span>' +
			'<span  style="width:' + colwidth + '">' + tbodyData[j].process_task_code + '</span>' +
			'<span  style="width:' + colwidth + '">' +tbodyData[j].rate+'%'+
				// '<div class="progress" progress="'+tbodyData[j].rate+'%">' +
				// '	<div class="progressBar">' +
				// '		<span></span>' +
				// '	</div>' +
				// '	<h3><i><h4></h4></i></h3>' +
				// '</div>' +
			 '</span>' +
			
			'</div>' +
			'</li>';
	}
	tbodyHtml += '<ul></div>';
	$("#fourBoard").html(theadHtml + tbodyHtml);
	$(".board4Scroll ul li").off().on('click', tbodyData, function (params) {
		$.ajax({
			contentType: 'application/json',
			type: 'get',
			url: board4Api.findByProcessTaskCode+tbodyData[$(this).index()].process_task_code,
			dataType: "json",
			success: function (response) {
				console.log("abc========",response)
				$('.filterbg').show();
				$('.popup').show();
				$('.popup').width('3px');
				$('.popup').animate({ height: '76%' }, 400, function () {
					$('.popup').animate({ width: '82%' }, 400);
				});
				setTimeout(data4Show(response.data), 800);
			}
		});
	})
	//运单状态文字滚动
	if (tbodyData.length > 16) {
		$('.board4Scroll').FontScroll({ time: 3000, num: 1, styleName: 'line4' });
	}
}
function data4Show(data) {
	$('.popupClose').css('display', 'block');
	$('.summary').show();
	addData4Html(data);
};
function addData4Html(data) {
	if (data.plan_start_time != null) {
		data.plan_start_time = data.plan_start_time.split('T')[0];
	} else {
		data.plan_start_time = ''
	}
	if (data.plan_finish_time != null) {
		data.plan_finish_time = data.plan_finish_time.split('T')[0];
	} else {
		data.plan_finish_time = ''
	}
	var theadHtmlP1 = '<div class="item summaryP1" style="">' +
		'   <div class="itemTit">' +
		'       <span class="border-blue">任务详情</span>' +
		'   </div>' +
		'   <div class="itemCon itembg itembg_popupfirt">' +
		'       <ul class="listStyle">' +
		'           <li class="clearfix">' +	
			'				<span class="col2">生产任务号:<strong>' + data.pcb_task_code + '</strong></span>' +
			'				<span class="col2">机台名称:<strong>' + data.device_name + '</strong></span>' +
			'				<span class="col2">机台编号:<strong>' + data.device_code + '</strong></span>' +
			'				<span class="col2">工序名称:<strong>' + data.process_name + '</strong></span>' +
			'				<span class="col2">工序单状态:<strong>' + data.process_task_status + '</strong></span>' +
			'				<span class="col2">完成数量:<strong>' + data.amount_completed + '</strong></span>' +
			'				<span class="col2">计划开始时间:<strong>' + data.plan_start_time + '</strong></span>' +
			'				<span class="col2">计划结束时间:<strong>' + data.plan_finish_time + '</strong></span>' +
			'				<span class="col2">工时:<strong>' + data.work_time + '</strong></span>' +
			'				<span class="col2">pcb编码:<strong>' + data.pcb_code + '</strong></span>' +
			'				<span class="col2">PCB数量:<strong>' + data.pcb_quantity + '</strong></span>' +
			'				<span class="col2">RoHS标志:<strong>' + data.is_rohs + '</strong></span>' +
			'				<span class="col1">工序订单编号:<strong>' + data.process_task_code + '</strong></span>' +
			'				<span class="col1">PCB名称:<strong>' + data.pcb_name + '</strong></span>' +
			'			</li>'+
		'       </ul>' +
		'   </div>' +
		'</div>';
	$(".summary").html(theadHtmlP1).css("display", "block");
}
var board4Api = {
    staffOnBoard:'/ShowBoard/staffOnBoard',
    findByProcessTaskCode:'/ShowBoard/findByProcessTaskCode/',
    staffTodayOntimeRate:'/ShowBoard/staffTodayOntimeRate',
}
function setDataBoard4(params) {
	// db4P1.setOption(db4POption1);
	// db4P2.setOption(db4POption2);
	$.ajax({
        contentType: 'application/json',
        type: 'get',
        url: board4Api.staffOnBoard,
        dataType: "json",
        success: function (response) {
			addfourBoardHtml(response.data);
        }
    });
	$.ajax({
		contentType: 'application/json',
        type: 'get',
        url: board4Api.staffTodayOntimeRate,
        dataType: "json",
        success: function (response) {
			var kRateArr = [],useRateArr = [],axiskRateArr=[];
			for(var i=0;i<response.data.length;i++){
				kRateArr.push(response.data[i].rate);
				useRateArr.push(response.data[i].useRate);
				axiskRateArr.push(response.data[i].processType);
			}
			console.log(kRateArr,useRateArr,axiskRateArr)
			db4POption1.series[0].data = kRateArr;
			db4POption1.xAxis.data = axiskRateArr;
			db4P1.setOption(db4POption1);

			db4POption2.series[0].data = useRateArr;
			db4POption2.xAxis.data = axiskRateArr;
			db4P2.setOption(db4POption2);

        }
    });


}