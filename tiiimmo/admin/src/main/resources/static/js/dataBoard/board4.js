
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
		}
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
		data: ["贴片", "后焊", "插件", "调试"]
	},
	yAxis: {
		name: '',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
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
		name: '',
		type: 'bar',
		data: [50, 79, 66, 92],
		barWidth: 50,
		label: {
			normal: {
				show: true,
				position: 'inside',
				formatter: '{c}',
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
		}
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
		data: ["贴片", "后焊", "插件", "调试"]
	},
	yAxis: {
		name: '',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
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
		name: '',
		type: 'bar',
		data: [56, 88, 77, 95],
		barWidth: 50,
		label: {
			normal: {
				show: true,
				position: 'inside',
				formatter: '{c}',
				color: '#fff',
				fontSize: 20
			}
		},
	}]
};
// 动态生成模板

var task = [{
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
}]

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
			'<span  style="width:' + colwidth + '">' +
				'<div class="progress" progress="'+tbodyData[j].rate+'%">' +
				'	<div class="progressBar">' +
				'		<span></span>' +
				'	</div>' +
				'	<h3><i><h4></h4></i></h3>' +
				'</div>' +
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
	console.log("data===",data.task_sheet_code)
	var theadHtmlP1 = '<div class="item summaryP1" style="">' +
		'   <div class="itemTit">' +
		'       <span class="border-blue">任务详情</span>' +
		'   </div>' +
		'   <div class="itemCon itembg itembg_popupfirt">' +
		'       <ul class="listStyle">' +
		'           <li class="clearfix">' +
			'				<span class="col2">工序任务号:<strong>'+data.task_sheet_code+'</strong></span>'+
			'				<span class="col2">生产任务单号:<strong>'+data.pcb_task_code+'</strong></span>'+
			// '				<span class="col2">车间:<strong>'+data.workshop+'</strong></span>'+
			'				<span class="col2">机型名称:<strong>'+data.device_name+'</strong></span>'+
			'				<span class="col2">机型型号:<strong>'+data.device_code+'</strong></span>'+
			'				<span class="col2">PCB编码:<strong>'+data.pcb_code+'</strong></span>'+
			'				<span class="col2">PCB名称:<strong>'+data.pcb_name+'</strong></span>'+
			'				<span class="col2">PCB数量:<strong>'+data.pcb_quantity+'</strong></span>'+
			'				<span class="col2">RoHS标志:<strong>'+data.is_rohs+'</strong></span>'+
			'				<span class="col2">生产计划开始时间:<strong>'+data.plan_finish_time.split('T')[0] +'</strong></span>'+
			'				<span class="col2">生产计划结束时间:<strong>'+data.plan_finish_time.split('T')[0] +'</strong></span>'+
			'				<span class="col2">任务状态:<strong>'+data.process_task_status+'</strong></span>'+
			'			</li>'+
		'       </ul>' +
		'   </div>' +
		'</div>';
	$(".summary").html(theadHtmlP1).css("display", "block");
}
var board4Api = {
    staffOnBoard:'/ShowBoard/staffOnBoard',
    findByProcessTaskCode:'/ShowBoard/findByProcessTaskCode/',
}
function setDataBoard4(params) {
	db4P1.setOption(db4POption1);
	db4P2.setOption(db4POption2);
	$.ajax({
        contentType: 'application/json',
        type: 'get',
        url: board4Api.staffOnBoard,
        dataType: "json",
        success: function (response) {
			console.log("response===",response)
			addfourBoardHtml(response.data);
        }
    });
}