// 第二看板========================================================================
//看板二弹框
var summaryPie1;
var board2Api = {
	mapProcessTypeDayRate: '/ShowBoard/getMapProcessTypeDayRate',//生产进度看板A部分五个柱状图接口
	findByProcessTaskCode: '/ShowBoard/findByProcessTaskCode/',//详情

}
var tiepianTaskArr = [], houhanTaskArr = [], tiaoshiTaskArr = [], zhijianTaskArr = [], rukuTaskArr = [], beiliaoTaskArr = [];
function setDataBoard2(params) {
	board2();
	board2Interval = setInterval(function () {
		console.log(555555)
		// db2P1.clear();
        // db2P2.clear();
        // db2P3.clear();
        // db2P4.clear();
        // db2P5.clear();
        // db2P6.clear();
		board2()
	}, 60000);
	function board2() {
		$.ajax({
			contentType: 'application/json',
			type: 'get',
			url: board2Api.mapProcessTypeDayRate,
			dataType: "json",
			success: function (response) {
				var tiepianArr = [], houhanArr = [], tiaoshiArr = [], zhijianArr = [], rukuArr = [], beiliaoArr = [];
				var tiepianTaskArr1 = [], houhanTaskArr1 = [], tiaoshiTaskArr1 = [], zhijianTaskArr1 = [], rukuTaskArr1 = [], beiliaoTaskArr1 = [];
				tiepianTaskArr = [];
				for (var ia = 0; ia < response.data.tiepian.length; ia++) {
					if(ia>4) continue;
					tiepianTaskArr.push(response.data.tiepian[ia].process_task_code);
					tiepianTaskArr1.push('任务' + (ia + 1));
					tiepianArr.push(Math.round(response.data.tiepian[ia].rate*100)/100);
				}
				db2POption1.yAxis.data = tiepianTaskArr1.reverse();
				tiepianTaskArr = tiepianTaskArr.reverse();
				db2POption1.series[0].data = tiepianArr.reverse();
				db2P1.setOption(db2POption1);
				if (tiepianArr.length > 0) {
					db2P1.getZr().on('click', params => {
						var pointInPixel = [params.offsetX, params.offsetY]
						if (db2P1.containPixel('grid', pointInPixel)) {
							var xIndex = db2P1.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[1]
							setClick(tiepianTaskArr[xIndex], tiepianArr[xIndex]);
						}
					})
				}
				houhanTaskArr = [];
				for (var ib = 0; ib < response.data.houhan.length; ib++) {
					if(ib>4) continue;
					houhanTaskArr.push(response.data.houhan[ib].process_task_code);
					houhanTaskArr1.push('任务' + (ib + 1));
					houhanArr.push(Math.round(response.data.houhan[ib].rate*100)/100);
				}
				db2POption2.yAxis.data = houhanTaskArr1.reverse();
				houhanTaskArr = houhanTaskArr.reverse();
				db2POption2.series[0].data = houhanArr.reverse();
				db2P2.setOption(db2POption2);
				if (houhanArr.length > 0) {
					db2P2.getZr().on('click', params => {
						var pointInPixel = [params.offsetX, params.offsetY]
						if (db2P2.containPixel('grid', pointInPixel)) {
							var xIndex = db2P2.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[1]
							setClick(houhanTaskArr[xIndex], houhanArr[xIndex]);
						}
					})
				}
				tiaoshiTaskArr = [];
				for (var ic = 0; ic < response.data.tiaoshi.length; ic++) {
					if(ic>4) continue;
					tiaoshiTaskArr.push(response.data.tiaoshi[ic].process_task_code);
					tiaoshiTaskArr1.push('任务' + (ic + 1));
					tiaoshiArr.push(Math.round(response.data.tiaoshi[ic].rate*100)/100);
				}
				//console.log(tiaoshiTaskArr1)
				db2POption3.yAxis.data = tiaoshiTaskArr1.reverse();
				tiaoshiTaskArr = tiaoshiTaskArr.reverse();
				db2POption3.series[0].data = tiaoshiArr.reverse();
				db2P3.setOption(db2POption3);
				if (tiaoshiArr.length > 0) {
					db2P3.getZr().on('click', params => {
						var pointInPixel = [params.offsetX, params.offsetY]
						if (db2P3.containPixel('grid', pointInPixel)) {
							var xIndex = db2P3.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[1]
							setClick(tiaoshiTaskArr[xIndex], tiaoshiArr[xIndex]);
						}
					})
				}
				zhijianTaskArr = [];
				for (var id = 0; id < response.data.zhijian.length; id++) {
					if(id>4) continue;
					zhijianTaskArr.push(response.data.zhijian[id].process_task_code);
					zhijianTaskArr1.push('任务' + (id + 1));
					zhijianArr.push(Math.round(response.data.zhijian[id].rate*100)/100);
				}
				db2POption5.yAxis.data = zhijianTaskArr1.reverse();
				zhijianTaskArr = zhijianTaskArr.reverse();
				db2POption5.series[0].data = zhijianArr.reverse();
				db2P5.setOption(db2POption5);
				if (zhijianArr.length > 0) {
					db2P5.getZr().on('click', params => {
						var pointInPixel = [params.offsetX, params.offsetY]
						if (db2P5.containPixel('grid', pointInPixel)) {
							var xIndex = db2P5.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[1]
							setClick(zhijianTaskArr[xIndex], zhijianArr[xIndex]);
						}
					})
				}
				rukuTaskArr = [];
				for (var ie = 0; ie < response.data.ruku.length; ie++) {
					if(ie>4) continue;
					rukuTaskArr.push(response.data.ruku[ie].process_task_code);
					rukuTaskArr1.push('任务' + (ie + 1));
					rukuArr.push(Math.round(response.data.ruku[ie].rate*100)/100);
				}
				db2POption6.yAxis.data = rukuTaskArr1.reverse();
				rukuTaskArr = rukuTaskArr.reverse();
				db2POption6.series[0].data = rukuArr.reverse();
				db2P6.setOption(db2POption6);
				if (rukuArr.length > 0) {
					db2P6.getZr().on('click', params => {
						var pointInPixel = [params.offsetX, params.offsetY]
						if (db2P6.containPixel('grid', pointInPixel)) {
							var xIndex = db2P6.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[1]
							setClick(rukuTaskArr[xIndex], rukuArr[xIndex]);
						}
					})
				}

				beiliaoTaskArr = [];
				for (var ig = 0; ig < response.data.beiliao.length; ig++) {
					if(ig>4) continue;
					beiliaoTaskArr.push(response.data.beiliao[ig].process_task_code);
					beiliaoTaskArr1.push('任务' + (ig + 1));
					beiliaoArr.push(Math.round(response.data.beiliao[ig].rate*100)/100);
				}
				db2POption4.yAxis.data = beiliaoTaskArr1.reverse();
				beiliaoTaskArr = beiliaoTaskArr.reverse();
				db2POption4.series[0].data = beiliaoArr.reverse();
				db2P4.setOption(db2POption4);
				if (beiliaoArr.length > 0) {
					db2P4.getZr().on('click', params => {
						var pointInPixel = [params.offsetX, params.offsetY]
						if (db2P4.containPixel('grid', pointInPixel)) {
							var xIndex = db2P4.convertFromPixel({ seriesIndex: 0 }, [params.offsetX, params.offsetY])[1]
							setClick(beiliaoTaskArr[xIndex], beiliaoArr[xIndex]);
						}
					})
				}
				//齐套率未完成，假的
				// db2POption4.yAxis.data = tiepianTaskArr;
				// db2POption4.series[0].data = tiepianArr;
			}
		});
	}
}
var summaryPie1Option = {
	title: {
	},
	tooltip: {
		trigger: 'item',
		formatter: '{a} <br/>{b} : {c} ({d}%)',
		textStyle: {
			fontSize: 20
		}
	},
	grid: {
		// left:'10%',
		top: '10%',
	},
	legend: {
		data: ['已完成', '未完成'],
		textStyle: {
			color: '#fff',
			fontSize: 22
		}
	},
	series: [
		{
			name: '任务达成率',
			type: 'pie',
			radius: '65%',
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
						fontSize: 22
					}
				}
			}
		}
	]
};

var db2P1, db2P2, db2P3, db2P4, db2P5, db2P6;
db2P1 = echarts.init(document.getElementById('db2P1'), 'macarons');
db2P2 = echarts.init(document.getElementById('db2P2'), 'macarons');
db2P3 = echarts.init(document.getElementById('db2P3'), 'macarons');
db2P4 = echarts.init(document.getElementById('db2P4'), 'macarons');
db2P5 = echarts.init(document.getElementById('db2P5'), 'macarons');
db2P6 = echarts.init(document.getElementById('db2P6'), 'macarons');

var db2POption1 = {
	title: {
		top: '10',
		left: 'center',
		text: '贴片线生产进度统计',
		textStyle: {
			color: '#fff',
			fontSize: 22,
			fontWeight: 'normal',
		}
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'shadow'
		},
		// formatter: '{b}<br/>{a} :{c}%',
		formatter:function(params){
			return tiepianTaskArr[params[0].dataIndex]+'<br/>'+params[0].seriesName+' : ' +params[0].value+'%'
		},
		textStyle: {
			fontSize: 22
		}
	},
	grid: {
		left: '3%',
		right: '10%',
		bottom: '10%',
		containLabel: true
	},
	xAxis: {
		type: 'value',
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
		min: 0,
		max: 100,
	},
	yAxis: {
		// show: false,
		type: 'category',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20,
			}
		},
		data: []
	},
	series: [{
		name: '完成率',
		type: 'bar',
		data: [],
		barWidth: 50,
		itemStyle: {
			normal: {
				label: {
					show: true,
					textStyle: {
						fontSize: 20,
						color: '#fff'
					},
					position: 'right',
					formatter: function (params) {
						if (params.value == 0) {
							return "";
						}
						return params.value + "%"
					}
				},
			}
		}
	}]
};
var db2POption2 = {
	title: {
		top: '10',
		left: 'center',
		text: '后焊线生产进度统计',
		textStyle: {
			color: '#fff',
			fontSize: 22,
			fontWeight: 'normal',
		}
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'shadow'
		},
		// formatter: '{b}<br/>{a} :{c}%',
		formatter:function(params){
			return houhanTaskArr[params[0].dataIndex]+'<br/>'+params[0].seriesName+' : ' +params[0].value+'%'
		},
		textStyle: {
			fontSize: 22
		}
	},
	grid: {
		left: '3%',
		right: '10%',
		bottom: '10%',
		containLabel: true
	},
	xAxis: {
		type: 'value',
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
		min: 0,
		max: 100,
	},
	yAxis: {
		// show: false,
		type: 'category',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			}
		},

		data: []
	},
	series: [{
		name: '完成率',
		type: 'bar',
		data: [],
		barWidth: 50,
		itemStyle: {
			normal: {
				label: {
					show: true,
					textStyle: {
						fontSize: 20,
						color: '#fff'
					},
					position: 'insideRight',
					formatter: function (params) {
						if (params.value == 0) {
							return "";
						}
						return params.value + "%"
					}
				},
			}
		}
	}]
};
var db2POption3 = {
	title: {
		top: '10',
		left: 'center',
		text: '调试情况统计',
		textStyle: {
			color: '#fff',
			fontWeight: 'normal',
			fontSize: 22
		}
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'shadow'
		},
		// formatter: '{b}<br/>{a} :{c}%',
		formatter:function(params){
			return tiaoshiTaskArr[params[0].dataIndex]+'<br/>'+params[0].seriesName+' : ' +params[0].value+'%'
		},
		textStyle: {
			fontSize: 22
		}
	},
	grid: {
		left: '3%',
		right: '10%',
		bottom: '10%',
		containLabel: true
	},
	xAxis: {
		type: 'value',
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
		min: 0,
		max: 100,
	},
	yAxis: {
		// show: false,
		type: 'category',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			}
		},

		data: []
	},
	series: [{
		name: '完成率',
		type: 'bar',
		data: [5, 20, 36, 10, 10],
		barWidth: 50,
		itemStyle: {
			normal: {
				label: {
					show: true,
					textStyle: {
						fontSize: 20,
						color: '#fff'
					},
					position: 'insideRight',
					formatter: function (params) {
						if (params.value == 0) {
							return "";
						}
						return params.value + "%"
					}
				},
			}
		}
	}]
};
var db2POption4 = {
	title: {
		top: '10',
		left: 'center',
		text: '物料齐套情况统计',
		textStyle: {
			color: '#fff',
			fontWeight: 'normal',
			fontSize: 22
		}
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'shadow'
		},
		// formatter: '{b}<br/>{a} :{c}%',
		formatter:function(params){
			return beiliaoTaskArr[params[0].dataIndex]+'<br/>'+params[0].seriesName+' : ' +params[0].value+'%'
		},
		textStyle: {
			fontSize: 22
		}
	},
	grid: {
		left: '3%',
		right: '10%',
		bottom: '10%',
		containLabel: true
	},
	xAxis: {
		type: 'value',
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
		min: 0,
		max: 100,
	},
	yAxis: {
		// show: false,
		type: 'category',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			}
		},
		data: ['任务1']
	},
	series: [{
		name: '齐套率',
		type: 'bar',
		data: [10],
		barWidth: 50,
		itemStyle: {
			normal: {
				label: {
					show: true,
					textStyle: {
						fontSize: 20,
						color: '#fff'
					},
					position: 'insideRight',
					formatter: function (params) {
						if (params.value == 0) {
							return "";
						}
						return params.value + "%"
					}
				},
			}
		}
	}]
};
var db2POption5 = {
	title: {
		top: '10',
		left: 'center',
		text: '质检情况统计',
		textStyle: {
			color: '#fff',
			fontWeight: 'normal',
			fontSize: 22
		}
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'shadow'
		},
		// formatter: '{b}<br/>{a} :{c}%',
		formatter:function(params){
			return zhijianTaskArr[params[0].dataIndex]+'<br/>'+params[0].seriesName+' : ' +params[0].value+'%'
		},
		textStyle: {
			fontSize: 22
		}
	},
	grid: {
		left: '3%',
		right: '10%',
		bottom: '10%',
		containLabel: true
	},
	xAxis: {
		type: 'value',
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
		min: 0,
		max: 100,
	},
	yAxis: {
		// show: false,
		type: 'category',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			}
		},

		data: []
	},
	series: [{
		name: '质检率',
		type: 'bar',
		data: [5, 20, 36, 10, 10],
		barWidth: 50,
		itemStyle: {
			normal: {
				label: {
					show: true,
					textStyle: {
						fontSize: 20,
						color: '#fff'
					},
					position: 'insideRight',
					formatter: function (params) {
						if (params.value == 0) {
							return "";
						}
						return params.value + "%"
					}
				},
			}
		}
	}]
};
var db2POption6 = {
	title: {
		top: '10',
		left: 'center',
		text: '入库情况统计',
		textStyle: {
			color: '#fff',
			fontWeight: 'normal',
			fontSize: 22
		}
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'shadow'
		},
		// formatter: '{b}<br/>{a} :{c}%',
		formatter:function(params){
			return rukuTaskArr[params[0].dataIndex]+'<br/>'+params[0].seriesName+' : ' +params[0].value+'%'
		},
		textStyle: {
			fontSize: 22
		}
	},
	grid: {
		left: '3%',
		right: '10%',
		bottom: '10%',
		containLabel: true
	},
	xAxis: {
		type: 'value',
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
		min: 0,
		max: 100,
	},
	yAxis: {
		// show: false,
		type: 'category',
		axisLabel: {
			textStyle: {
				show: true,
				color: 'rgba(255,255,255,1)',
				fontSize: 20
			}
		},
		data: []
	},
	series: [{
		name: '入库率',
		type: 'bar',
		barWidth: 50,
		data: [5, 20, 36, 10, 10],
		itemStyle: {
			normal: {
				label: {
					show: true,
					textStyle: {
						fontSize: 20,
						color: '#fff'
					},
					position: 'insideRight',
					formatter: function (params) {
						if (params.value == 0) {
							return "";
						}
						return params.value + "%"
					}
				},
			}
		}
	}]
};

function setClick(data, percentage) {
	$.ajax({
		contentType: 'application/json',
		type: 'get',
		url: board2Api.findByProcessTaskCode + data,
		dataType: "json",
		success: function (response) {
			$('.filterbg').show();
			$('.popup').show();
			$('.popup').width('3px');
			$('.popup').animate({ height: '76%' }, 400, function () {
				$('.popup').animate({ width: '82%' }, 400);
			});
			setTimeout(summaryShow(response.data, percentage), 800);
		}
	});
}
function summaryShow(data, percentage) {
	$('.popupClose').css('display', 'block');
	$('.summary').show();
	setCharts(data, percentage);

};


function setCharts(data, percentage) {
	// 动态生成模板
	addHtml1();
	function addHtml1() {
		//<!--任务信息-->
		var theadHtml = '';
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
		theadHtml = '<div class="item" style="width: 50%; height: 760px;">' +
			'<div class="itemTit">' +
			'<span class="border-blue">任务信息</span>' +
			'	</div>' +
			'	<div class="itemCon itembg" style="height: 665px;">' +
			'		<ul class="listStyle dblist2">' +
			'			<li class="clearfix">' +
			'				<span class="col2">生产任务号:<strong>' + data.pcb_task_code + '</strong></span>' +
			// '				<span class="col2">机台名称:<strong>' + data.device_name + '</strong></span>' +
			// '				<span class="col2">机台编号:<strong>' + data.device_code + '</strong></span>' +
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
			'			</li>' +
			'		</ul>' +
			'	</div>' +
			'</div>' +
			'<div class="item" style="width: 50%; height: 760px;">' +
			'	<div class="itemTit">' +
			'		<span class="border-blue">任务达成率</span>' +
			'	</div>' +
			'	<div class="itemCon ">' +
			'		<div id="summaryPie1" class="summaryPie db2" style="width:720px;height:665px"></div>' +
			'	</div>' +
			'</div>';
		$(".summary").html(theadHtml).css("display", "flex");
	}
	summaryPie1 = echarts.init(document.getElementById('summaryPie1'), 'macarons');
	summaryPie1Option.series[0].data = [
		{ value: percentage, name: '已完成' },
		{ value: 100 - percentage, name: '未完成' }
	]
	summaryPie1.setOption(summaryPie1Option)
}
