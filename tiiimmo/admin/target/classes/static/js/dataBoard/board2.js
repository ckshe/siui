	// 第二看板========================================================================
	//看板二弹框
	var summaryPie1;

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
	function setDataBoard2(params) {
		db2P1.setOption(db2POption1);
		db2P2.setOption(db2POption2);
		db2P3.setOption(db2POption3);
		db2P4.setOption(db2POption4);
		db2P5.setOption(db2POption5);
		db2P6.setOption(db2POption6);
	}
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
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '3%',
			containLabel: true
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
                            return  params.value
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
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '3%',
			containLabel: true
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
                            return  params.value
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
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '3%',
			containLabel: true
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
                            return  params.value
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
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '3%',
			containLabel: true
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
                            return  params.value
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
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '3%',
			containLabel: true
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
                            return  params.value
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
			textStyle:{
				fontSize:22
			}
        },
		grid: {
			left: '3%',
			right: '5%',
			bottom: '3%',
			containLabel: true
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
                            return  params.value
                        }
                    },
                }
            }
		}]
	};

	function setClick(n){
		$('.filterbg').show();
		$('.popup').show();
		$('.popup').width('3px');
		$('.popup').animate({height: '76%'},400,function(){
			$('.popup').animate({width: '82%'},400);
		});
		setTimeout(summaryShow(n),800);

	}
	function summaryShow(n){
		$('.popupClose').css('display','block');
		$('.summary').show();
		if(n==1){
			setCharts1();
		}else{
			setCharts();
		}
		
	};
	$('.popupClose').on('click',function(){
		$('.popupClose').css('display','none');
		$('.summary').hide().empty();
		// try{
		// 	summaryPie1.clear();
		// }catch{

		// }
		$('.popup').animate({width: '3px'},400,function(){
			$('.popup').animate({height: 0},400);
		});
		setTimeout(summaryHide,800);
	});
	function summaryHide(){
		$('.filterbg').hide();
		$('.popup').hide();
		$('.popup').width(0);
	};
	function setCharts1(){
		// 动态生成模板
		var theadHtml='',tbodyHtml='';
		var theadData = ['物料代码','物料名称','规格型号','计划投料数','已领数量','齐套率']
		var tbodyData = [
			['ZC1712120023','电源板1','MK-525523','200','100','50%'],
			['ZC1712120024','电源板2','MK-525523','200','100','50%'],
			['ZC1712120025','电源板3','MK-525523','200','100','50%'],
			['ZC1712120026','电源板4','MK-525523','200','100','50%'],
			['ZC1712120027','电源板5','MK-525523','200','100','50%'],
			['ZC1712120028','电源板6','MK-525523','200','100','50%'],
			['ZC1712120029','电源板7','MK-525523','200','100','50%'],
			['ZC1712120030','电源板8','MK-525523','200','100','50%'],
			['ZC1712120031','电源板9','MK-525523','200','100','50%'],
			['ZC1712120032','电源板10','MK-525523','200','100','50%'],
			['ZC1712120033','电源板11','MK-525523','200','100','50%'],
			['ZC1712120034','电源板12','MK-525523','200','100','50%'],
			['ZC1712120035','电源板13','MK-525523','200','100','50%'],
			['ZC1712120036','电源板14','MK-525523','200','100','50%'],
			['ZC1712120037','电源板15','MK-525523','200','100','50%'],
			['ZC1712120038','电源板16','MK-525523','200','100','50%'],
			['ZC1712120039','电源板17','MK-525523','200','100','50%'],
			['ZC1712120040','电源板18','MK-525523','200','100','50%'],
			['ZC1712120054','电源板19','MK-525523','200','100','50%'],
			['ZC1712120055','电源板20','MK-525523','200','100','50%'],
			['ZC1712120056','电源板21','MK-525523','200','100','50%'],
		]
		addHtml1();
		function addHtml1(){
			theadHtml = '<div class="item billState">'+
			'<div class="itemTit">'+
				'<span class="border-green">贴片生产任务</span>'+
			'</div>'+
			'<div class="itemCon">'+
			'	<div class="StateBox firstBoard" id="firstBoard">'+
			'<div class="StateTit">';

			if(tbodyData.length>0){
				colwidth = (98/theadData.length).toFixed(2)+'%';
			}else{
				colwidth = 'auto';
			}
			for(var i=0;i<theadData.length;i++){
				theadHtml += '<span style="width:'+colwidth+'">'+theadData[i]+'</span>';
			}
			theadHtml += '</div>';
			tbodyHtml = '<div id="FontScroll" class="board2Scroll"><ul>';
			for(var j=0;j<tbodyData.length;j++){
				tbodyHtml += '<li><div class="fontInner clearfix">';
					var spanHtml = '';
					for(var k=0;k<theadData.length;k++){
						spanHtml += '<span style="width:'+colwidth+'">'+tbodyData[j][k]+'</span>';
					}
				tbodyHtml += spanHtml + '</div></li>';
			}
			tbodyHtml  += '<ul></div></div></div></div>';
			$(".summary").html(theadHtml+tbodyHtml).css("display","block");
					//运单状态文字滚动
			$('.board2Scroll').FontScroll({time: 3000,num: 2,styleName:'line2'});
		}
	}
	function setCharts(){
		// 动态生成模板
		var theadHtml='',tbodyHtml='';

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
			'				<span>日制令号:<strong>23234</strong></span>'+
			'				<span>生产任务单号:<strong>s-01</strong></span>'+
			'				<span>车间:<strong>SMT-25</strong></span>'+
			'				<span>机型名称:<strong>abc2-2</strong></span>'+
			'				<span>机型版本:<strong>v2.0</strong></span>'+
			'				<span>PCB编码:<strong>pcbabc</strong></span>'+
			'				<span>PCB名称:<strong>电路板1</strong></span>'+
			'				<span>PCB数量:<strong>100</strong></span>'+
			'				<span>RoHS标志:<strong>RO</strong></span>'+
			'				<span>工序:<strong>贴片</strong></span>'+
			'				<span>生产计划开始时间:<strong>2020-05-28</strong></span>'+
			'				<span>生产计划结束时间:<strong>2020-05-28</strong></span>'+
			'				<span>完成数量:<strong>200</strong></span>'+
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
		summaryPie1.setOption(summaryPie1Option)
	}
	db2P1.on('click', function (params) {
		// window.open('https://www.baidu.com/s?wd=' + encodeURIComponent(params.name));
		setClick(1);
	});
	db2P2.on('click', function (params) {
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