	// 第二看板========================================================================
	//看板二弹框
	var summaryPie1;
	function setDataBoard2(params) {
		var url = '/open/excute',data='';
		var queryStr2view_tiepian ="select@@@*@@@from@@@view_tiepian";
		var queryStr2view_han ="select@@@*@@@from@@@view_danban";
		$.ajax({
			contentType: 'application/json',
			type: 'POST',
			url: url,
			dataType: "json",
			data: JSON.stringify(returnData(queryStr2view_tiepian)),
			success: function (message) {
				console.log(message)
				var sum=0,sumtemp=0 ,weekArr = [],weekArrdata=[];
				for(var i=0;i<message.data.length;i++){
					if(message.data[i].sum_count==0){message.data[i].sum_count = 1}
					weekArr[i] = Math.floor((message.data[i].finish_count/message.data[i].sum_count)*100)
					weekArrdata[i]  = message.data[i].pcb_task_code;
				}
				console.log(weekArr,weekArrdata)
				db2POption1.yAxis.data = weekArrdata;
				db2POption1.series[0].data = weekArr;
				db2P1.setOption(db2POption1);
				db2P1.on('click', function (params) {
					console.log(params.dataIndex,message.data[params.dataIndex])
					setClick(message.data[params.dataIndex]);
				});
			}
		});
		$.ajax({
			contentType: 'application/json',
			type: 'POST',
			url: url,
			dataType: "json",
			data: JSON.stringify(returnData(queryStr2view_han)),
			success: function (message) {
				console.log("========",message)
				var sum=0,sumtemp=0 ,weekArr = [],weekArrdata=[];
				for(var i=0;i<message.data.length;i++){
					if(message.data[i].sum_count==0){message.data[i].sum_count = 1}
					weekArr[i] = Math.floor((message.data[i].finish_count/message.data[i].sum_count)*100)
					weekArrdata[i]  = message.data[i].pcb_task_code;
				}
				console.log(weekArr,weekArrdata)
				db2POption2.yAxis.data = weekArrdata;
				db2POption2.series[0].data = weekArr;
				db2P2.setOption(db2POption2);
				db2P2.on('click', function (params) {
					console.log(params.dataIndex,message.data[params.dataIndex])
					setClick(message.data[params.dataIndex]);
				});
			}
		});
		db2P3.setOption(db2POption3);
		db2P4.setOption(db2POption4);
		db2P5.setOption(db2POption5);
		db2P6.setOption(db2POption6);
	}
	var summaryPie1Option = {
		title: {
		},
		tooltip: {
			trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)',
            textStyle:{
                fontSize:20
            }
		},
		grid:{
			// left:'10%',
			top:'10%',
		},
		legend: {
			data:['已完成','未完成'],
			textStyle:{
				color:'#fff',
				fontSize:22
			}
		},
		series: [
			{
				name: '任务达成率',
				type: 'pie',
				radius: '65%',
				center: ['50%', '50%'],
				data: [
					{value: 10, name: '已完成'},
					{value: 20, name: '未完成'}
				],
                itemStyle:{
                    normal:{
                        label:{
                            show:true,
                            position:'inner',
                            formatter:'{d}%',
                            fontSize:22
                        }
                    }
                }
			}
		]
	};

	var db2P1,db2P2,db2P3,db2P4,db2P5,db2P6;
	db2P1 = echarts.init(document.getElementById('db2P1'),'macarons');
	db2P2 = echarts.init(document.getElementById('db2P2'),'macarons');
	db2P3 = echarts.init(document.getElementById('db2P3'),'macarons');
	db2P4 = echarts.init(document.getElementById('db2P4'),'macarons');
	db2P5 = echarts.init(document.getElementById('db2P5'),'macarons');
	db2P6 = echarts.init(document.getElementById('db2P6'),'macarons');

	var db2POption1 = {
		title: {
			top:'10',
			left:'center',
            text: '贴片线生产进度统计',
            textStyle:{
                color:'#fff',
				fontSize:22,
				fontWeight: 'normal',
            }
		},
		tooltip: {
            trigger:'axis',
            axisPointer: {
                type: 'shadow'
			},
			formatter: '{b}<br/>{a} :{c}%',
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '10%',
			// containLabel: true
		},
		xAxis: {
            type:'value',
			axisLabel:{
				textStyle:{
					show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
                },
                formatter:function(value){
                    return value+'%'
                }
            },
            min:0,
            max:100,
		},
		yAxis: {
			show : false,
            type:'category',
            axisLabel:{
                textStyle:{
                    show:true,
                    color:'rgba(255,255,255,1)',
					fontSize:20,
				}
            },
            data: []
		},
		series: [{
			name: '完成率',
			type: 'bar',
			data: [],
			barWidth: 50,
            itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'right',
                        formatter:function(params){
                            return  params.value+"%"
                        }
                    },
                }
            }
		}]
	};
	var db2POption2 = {
		title: {
			top:'10',
			left:'center',
            text: '后焊线生产进度统计',
            textStyle:{
                color:'#fff',
				fontSize:22,
				fontWeight: 'normal',
            }
		},
		tooltip: {
            trigger:'axis',
            axisPointer: {
                type: 'shadow'
			},
			formatter: '{b}<br/>{a} :{c}%',
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '10%',
			// containLabel: true
		},
		xAxis: {
            type:'value',
			axisLabel:{
				textStyle:{
					show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
                },
                formatter:function(value){
                    return value+'%'
                }
            },
            min:0,
            max:100,
		},
		yAxis: {
			show : false,
            type:'category',
            axisLabel:{
                textStyle:{
                    show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
				}
            },
 
            data: ["任务1","任务2","任务3","任务4","任务5"]
		},
		series: [{
			name: '完成率',
			type: 'bar',
			data: [5, 20, 36, 10, 10],
			barWidth: 50,
            itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'right',
                        formatter:function(params){
                            return  params.value +"%"
                        }
                    },
                }
            }
		}]
	};
	var db2POption3 = {
		title: {
			top:'10',
			left:'center',
            text: '调试情况统计',
            textStyle:{
				color:'#fff',
				fontWeight: 'normal',
                fontSize:22
            }
		},
		tooltip: {
            trigger:'axis',
            axisPointer: {
                type: 'shadow'
			},
			formatter: '{b}<br/>{a} :{c}%',
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '10%',
			// containLabel: true
		},
		xAxis: {
            type:'value',
			axisLabel:{
				textStyle:{
					show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
                },
                formatter:function(value){
                    return value+'%'
                }
            },
            min:0,
            max:100,
		},
		yAxis: {
			show : false,
            type:'category',
            axisLabel:{
                textStyle:{
                    show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
				}
            },
 
            data: ["任务1","任务2","任务3","任务4","任务5"]
		},
		series: [{
			name: '完成率',
			type: 'bar',
			data: [5, 20, 36, 10, 10],
			barWidth: 50,
            itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'right',
                        formatter:function(params){
                            return  params.value +"%"
                        }
                    },
                }
            }
		}]
	};
	var db2POption4 = {
		title: {
			top:'10',
			left:'center',
            text: '物料齐套情况统计',
            textStyle:{
				color:'#fff',
				fontWeight: 'normal',
                fontSize:22
            }
		},
		tooltip: {
            trigger:'axis',
            axisPointer: {
                type: 'shadow'
			},
			formatter: '{b}<br/>{a} :{c}%',
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '10%',
			// containLabel: true
		},
		xAxis: {
            type:'value',
			axisLabel:{
				textStyle:{
					show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
                },
                formatter:function(value){
                    return value+'%'
                }
            },
            min:0,
            max:100,
		},
		yAxis: {
			show : false,
            type:'category',
            axisLabel:{
                textStyle:{
                    show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
				}
            },
 
            data: ["任务1","任务2","任务3","任务4","任务5"]
		},
		series: [{
			name: '齐套率',
			type: 'bar',
			data: [5, 20, 36, 10, 10],
			barWidth: 50,
            itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'right',
                        formatter:function(params){
                            return  params.value+"%"
                        }
                    },
                }
            }
		}]
	};
	var db2POption5 = {
		title: {
			top:'10',
			left:'center',
            text: '质检情况统计',
            textStyle:{
				color:'#fff',
				fontWeight: 'normal',
				fontSize:22
            }
		},
		tooltip: {
            trigger:'axis',
            axisPointer: {
                type: 'shadow'
			},
			formatter: '{b}<br/>{a} :{c}%',
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '10%',
			// containLabel: true
		},
		xAxis: {
            type:'value',
			axisLabel:{
				textStyle:{
					show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
                },
                formatter:function(value){
                    return value+'%'
                }
            },
            min:0,
            max:100,
		},
		yAxis: {
			show : false,
            type:'category',
            axisLabel:{
                textStyle:{
                    show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
				}
            },
 
            data: ["任务1","任务2","任务3","任务4","任务5"]
		},
		series: [{
			name: '质检率',
			type: 'bar',
			data: [5, 20, 36, 10, 10],
			barWidth: 50,
            itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'right',
                        formatter:function(params){
                            return  params.value+"%"
                        }
                    },
                }
            }
		}]
	};
	var db2POption6 = {
		title: {
			top:'10',
			left:'center',
            text: '入库情况统计',
            textStyle:{
				color:'#fff',
				fontWeight: 'normal',
                fontSize:22
            }
		},
		tooltip: {
            trigger:'axis',
            axisPointer: {
                type: 'shadow'
			},
			formatter: '{b}<br/>{a} :{c}%',
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '10%',
			// containLabel: true
		},
		xAxis: {
            type:'value',
			axisLabel:{
				textStyle:{
					show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
                },
                formatter:function(value){
                    return value+'%'
                }
            },
            min:0,
            max:100,
		},
		yAxis: {
			show : false,
            type:'category',
            axisLabel:{
                textStyle:{
                    show:true,
                    color:'rgba(255,255,255,1)',
                    fontSize:20
				}
            },
            data: ["任务1","任务2","任务3","任务4","任务5"]
		},
		series: [{
			name: '入库率',
			type: 'bar',
            data: [5, 20, 36, 10, 10],
            itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'right',
                        formatter:function(params){
                            return  params.value+"%"
                        }
                    },
                }
            }
		}]
	};

	function setClick(data){
		$('.filterbg').show();
		$('.popup').show();
		$('.popup').width('3px');
		$('.popup').animate({height: '76%'},400,function(){
			$('.popup').animate({width: '82%'},400);
		});
		setTimeout(summaryShow(data),800);

	}
	function summaryShow(data){
		$('.popupClose').css('display','block');
		$('.summary').show();
			setCharts(data);
		
	};

	
	function setCharts(data){
		// 动态生成模板
		var theadHtml='';
		addHtml1();
		function addHtml1(){
			//<!--任务信息-->
			theadHtml = '<div class="item" style="width: 50%; height: 760px;">'+
			'<div class="itemTit">'+
			'<span class="border-blue">任务信息</span>'+
			'	</div>'+
			'	<div class="itemCon itembg" style="height: 665px;">'+
			'		<ul class="listStyle dblist2">'+
			'			<li class="clearfix">'+
			'				<span class="col2">工序任务号:<strong>'+data.task_sheet_code+'</strong></span>'+
			'				<span class="col2">生产任务单号:<strong>'+data.pcb_task_code+'</strong></span>'+
			'				<span class="col1">车间:<strong>'+data.workshop+'</strong></span>'+
			'				<span class="col1">机型名称:<strong>'+data.model_name+'</strong></span>'+
			'				<span class="col1">机型型号:<strong>'+data.model_ver+'</strong></span>'+
			'				<span class="col1">PCB编码:<strong>'+data.pcb_id+'</strong></span>'+
			'				<span class="col1">PCB名称:<strong>'+data.pcb_name+'</strong></span>'+
			'				<span class="col2">PCB数量:<strong>'+data.pcb_quantity+'</strong></span>'+
			'				<span class="col2">RoHS标志:<strong>'+data.is_rohs+'</strong></span>'+
			// '				<span>工序:<strong>'+data.pcb_task_code+'</strong></span>'+
			'				<span class="col1">生产计划开始时间:<strong>'+data.produce_plan_date+'</strong></span>'+
			'				<span class="col1">生产计划结束时间:<strong>'+data.produce_plan_complete_date+'</strong></span>'+
			'				<span class="col1">完成数量:<strong>'+data.amount_completed+'</strong></span>'+
			'			</li>'+
			'		</ul>'+
			'	</div>'+
			'</div>'+
			'<div class="item" style="width: 50%; height: 760px;">'+
			'	<div class="itemTit">'+
			'		<span class="border-blue">任务达成率</span>'+
			'	</div>'+
			'	<div class="itemCon ">'+	
			'		<div id="summaryPie1" class="summaryPie db2" style="width:720px;height:665px"></div>'+
			'	</div>'+
			'</div>';
			$(".summary").html(theadHtml).css("display","flex");
		}
		summaryPie1 = echarts.init(document.getElementById('summaryPie1'),'macarons');
		summaryPie1Option.series[0].data =  [
												{value: data.finish_count, name: '已完成'},
												{value: data.sum_count-data.finish_count, name: '未完成'}
											]
		summaryPie1.setOption(summaryPie1Option)
	}

	db2P2.on('click', function (params) {
		console.log(params.dataIndex)
		setClick(2);
	});
	db2P3.on('click', function (params) {
		setClick(3);
	});
	db2P4.on('click', function (params) {
		setClick(4);
	});
	db2P5.on('click', function (params) {
		setClick(5);
	});
	db2P6.on('click', function (params) {
		setClick(6);
	});