	
//看板三=====================

function setDataBoard3(params) {
    setTimeout(function(){
        // db3P1.setOption(db3POption1);
        db3P2.setOption(db3POption2);
        db3P3.setOption(db3POption3);
        db3P4.setOption(db3POption4);
        db3P5.setOption(db3POption5);
    },10)
    $('.imagesflex img').off().on('click',function(){
        setdievClick();
    })
}
function setdievClick(){
    $('.filterbg').show();
    $('.popup').show();
    $('.popup').width('3px');
    $('.popup').animate({height: '76%'},400,function(){
        $('.popup').animate({width: '82%'},400);
    });
    setTimeout(deviceShow,800);
}
function deviceShow(){
    $('.popupClose').css('display','block');
    $('.summary').show().css('display','block');
    addHtml();
    setDevice();
    
};
var devicePie1,devicePie3;
var pieData;
function setDevice(){
	devicePie1 = echarts.init(document.getElementById('devicePie1'),'macarons');
	devicePie3 = echarts.init(document.getElementById('devicePie3'),'macarons');
	
	var ww = $(window).width();
	var pieData;
	if(ww>1600){
		pieData = {
			pieTop: '40%',
			pieTop2: '36%',
			titleSize: 20,
			pieRadius: [80, 85],
			itemSize: 32
		}
	}else{
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
			subtextStyle:{
                color: '#fff',
                fontSize:22
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
				detail: {formatter: '{value}%'},
				data: [{value: 60, name: ''}],
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
		textStyle:{
			color:'#fff',
			fontSize:20
		},
		y:10
    },
    grid: {
        left: '5%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    yAxis: {
		type: 'value',
		axisLabel:{
			textStyle:{
				show:true,
				color:'rgba(255,255,255,1)',
				fontSize:20
			}
		},
		min:0,
		max:100
    },
    xAxis: {
        type: 'category',
		data: ['1号','2号','3号','4号','5号','6号'],
		axisLabel:{
			textStyle:{
				show:true,
				color:'rgba(255,255,255,1)',
				fontSize:20
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
			barWidth:50,
			data: [23,20,15,13,15,23],
			itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'inside',
                        formatter:function(params){
                            return  params.value+"%"
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
			barWidth:50,
			data: [13,22,14,13,14,26],
			itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'inside',
                        formatter:function(params){
                            return  params.value+"%"
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
function addHtml(){
    var html = '<div style="height:400px">'+
        '<div class="left" style="width: 40%; height: 320px;">'+
        '   <div class="">'+
        '       <div id="devicePie1" class="threeBoard4 char"></div>'+
        '   </div>'+
        '</div>'+
        '<div class="item summaryBottom" style="">'+
        '   <div class="itemTit">'+
        '       <span class="border-blue">设备任务</span>'+
        '   </div>'+
        '   <div class="itemCon itembg itembg_popupfirt">'+
        '       <ul class="listStyle">'+
        '           <li class="clearfix">'+
        '               <span>日制令号:<strong>s-tesf</strong></span>'+
        '               <span>生产任务单号：<strong>s-52</strong></span>'+
        '               <span>车间：<strong>万吉</strong></span>'+
        '               <span>设备名称：<strong>贴片机1</strong></span>'+
        '               <span>机型版本：<strong>s-955</strong></span>'+
        '               <span>PCB编码：<strong>0asw_23</strong></span>'+
        '               <span>PCB名称：<strong>T100</strong></span>'+
        '               <span>PCB数量：<strong>522</strong></span>'+
        '               <span>RoHS标志：<strong>OPK</strong></span>'+
        '               <span>工序：<strong>贴片</strong></span>'+
        '               <span>计划开始时间:<strong>2020.04.22</strong></span>'+
        '               <span>计划结束时间:<strong>2021.04.25</strong></span>'+
        '               <span>完成数量：<strong>300</strong></span>'+
        '           </li>'+
        '       </ul>'+
        '   </div>'+
        '</div>'+
    '</div>'+
    '<div class="devBottom">'+
        '<div class="item" style="width: 40%;">'+
        '   <div class="itemTit">'+
        '       <span class="border-blue">设备信息</span>'+
        '   </div>'+
        '   <div class="itemCon itembg itembg_popupfirt col2">'+
        '       <ul class="listStyle">'+
        '           <li class="clearfix">'+
        '               <span>车间：<strong>万吉</strong></span>'+
        '               <span>设备编号：<strong>524555wwa</strong></span>'+
        '               <span>设备名称:<strong>贴片机</strong></span>'+
        '               <span>站位:<strong>A1</strong></span>'+
        '               <span>上次检测时间:<strong>2020.04.22</strong></span>'+
        '               <span>下次检测时间:<strong>2021.04.22</strong></span>'+
        '           </li>'+
        '       </ul>'+
        '   </div>'+
        '</div>'+
        '<div class="right" style="width: 40%; height: 380px; float: left;">'+
        '    <div class="itemTit">'+
        '        <span class="border-green">设备日状态信息</span>'+
        '    </div>'+
        '    <div id="devicePie3" class="threeBoard5 char"></div>'+
        '</div>'+
        '<div class="imgDiv"><img src="images/photo.jpg"><p><span>姓名：</span><span>风陵苑主</span></p><p><span>工号：</span><span>965486</span></p></div>'+
    '</div>';
    $('.summary').html(html)
}
var db3P1,db3P2;
// db3P1 = echarts.init(document.getElementById('db3P1'),'macarons');
db3P2 = echarts.init(document.getElementById('db3P2'),'macarons');
db3P3 = echarts.init(document.getElementById('db3P3'),'macarons');
db3P4 = echarts.init(document.getElementById('db3P4'),'macarons');
db3P5 = echarts.init(document.getElementById('db3P5'),'macarons');

// var db3POption1 = {
//     title: {
//         left: 'center'
//     },
//     tooltip: {
//         trigger: 'item',
//         formatter: '{a} <br/>{b} : {c} ({d}%)',
//         textStyle:{
// 			color:'#fff',
// 			fontSize:20
// 		},
//     },
//     legend: {
//         // orient: 'vertical',
//         // data: ['贴片', '后焊',],
//         // type: 'scroll',
//         // // orient: 'vertical',
//         // right: 10,
//         top: 20,
//         // bottom: 20,
//         data: ['待机', '运行'],
// 		textStyle:{
// 			color:'#fff',
// 			fontSize:22
// 		},
//     },
//     series: [
//         {
//             name: '产线日状态',
//             type: 'pie',
//             radius: '55%',
//             center: ['50%', '60%'],
//             data: [
//                 {value: 50, name: '待机'},
//                 {value: 60, name: '运行'},
//             ],
//             emphasis: {
//                 itemStyle: {
//                     shadowBlur: 10,
//                     shadowOffsetX: 0,
//                     shadowColor: 'rgba(0, 0, 0, 0.5)',
//                     fontSize:20
//                 }
//             },
//             itemStyle:{
//                 normal:{
//                     label:{
//                         show:true,
//                         // position:'inner',
//                         formatter:'{b}',
//                         fontSize:20
//                     }
//                 }
//             }
//         }
//     ]
// };
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
        textStyle:{
			color:'#fff',
			fontSize:20
		},
    },
    legend: {
		data: ['计划完成量', '实际完成量'],
		textStyle:{
			color:'#fff',
			fontSize:20
		},
		y:10
    },

    // calculable: true,
    xAxis: [
        {
            type: 'category',
			data: ['1号', '2号', '3号', '4号', '5号', '6号', '7号'],
			axisLabel:{
				textStyle:{
					show:true,
					color:'rgba(255,255,255,1)',
					fontSize:20
				}
			},
        }
    ],
    yAxis: [
        {
			type: 'value',
			axisLabel:{
				textStyle:{
					show:true,
					color:'rgba(255,255,255,1)',
					fontSize:20
				}
			},
        }
    ],
    series: [
        {
            name: '计划完成量',
            type: 'bar',
			data: [90,65, 98,87, 85, 76, 92],
			itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'inside',
                        formatter:function(params){
                            return  params.value
                        }
                    },
                }
            }
        },
        {
            name: '实际完成量',
            type: 'bar',
			data: [87,65, 90,75, 85, 66, 78],
			itemStyle:{
                normal:{
                    label:{
                        show:true,
                        textStyle:{
                            fontSize:20,
                            color:'#fff'
                        },
                        position:'inside',
                        formatter:function(params){
                            return  params.value
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
        textStyle:{
			color:'#fff',
			fontSize:20
		},
    },
	grid: {
		top:'10%',
		left: '3%',
		right: '4%',
		bottom: '3%',
		containLabel: true
	},
	xAxis: {
		axisLabel:{
			textStyle:{
				show:true,
				color:'rgba(255,255,255,1)',
				fontSize:20
			}
		},
		data: ["周一","周二","周三","周四","周五","周六","周日"]
	},
	yAxis: {
		axisLabel:{
			textStyle:{
				show:true,
				color:'rgba(255,255,255,1)',
				fontSize:20
			}
		},
	},
	series: [{
		name: '工作时长',
		type: 'bar',
		data: [117, 123, 22, 78, 98,85,60],
		itemStyle:{
			normal:{
				label:{
					show:true,
					textStyle:{
						fontSize:20,
						color:'#fff'
					},
					position:'inside',
					formatter:function(params){
						return  params.value
					}
				},
			}
		}
	}]
};
    //弹出框调用ECharts折线图
    var db3POption4 = {
        title: {
            x: 'left',
            y: '0',
            text: '',
            textStyle: {
                fontWeight: 'normal',
                color: '#fff',
                fontSize: 20
            },
        },
        tooltip: {
            trigger: 'item',  
            formatter: function(params) {  
                var res = params.name+'号直通育：'+params.data+'%'; 
                return res;  
            },
            textStyle:{
                color:'#fff',
                fontSize:20
            },
        },
        grid: {
            top: '20%',
            left: '0%',
            width: '100%',
            height: '80%',
            containLabel: true
        },
        xAxis: {
            data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30'],
            axisLabel: {
                show: true,
                textStyle: {
                    fontSize: 16,
                    color: '#fff',
                }
                },
                axisLine:{  
                lineStyle:{  
                    color:'#fff',  
                    width:2,
                }  
            }  
        },

        yAxis: {
            axisLabel: {
                show: true,
                textStyle: {
                    fontSize: 16,
                    color: '#fff',
                }
                },
                axisLine:{  
                lineStyle:{  
                    color:'#0e2c52',  
                    width:2, 
                }  
            },
            splitLine:{  
                show:true,
                lineStyle:{  
                    color:'#0e2c52',  
                    width:2, 
                }  
            }  
        },

        series :{
            name: '',
            type: 'line',
            data: ['88','94','83','98','88','98','87','98','78','85','88','83','90','93','92','95','87','89','86','87','86','85','87','97','92','87','89','97','93','85'],
            areaStyle: {
                normal:{
                    color: 'rgba(79,237,247,0.3)',
                }
            },
            itemStyle: {
                normal: {
                    lineStyle: {
                        color: '#00dafb',
                        width: 1,
                    },
                    color: '#00dafb',
                },
            },
            showSymbol: true,
            symbol: 'circle',     //设定为实心点
            symbolSize: 15,   //设定实心点的大小
            hoverAnimation: false,
            animation: false,
        },
    }
    var db3POption5 = {
        title: {
            left: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)',
            textStyle:{
                color:'#fff',
                fontSize:20
            },
        },
        legend: {
            orient: 'vertical',
            data: ['贴片', '后焊', '插件', '调试', '入库'],
            type: 'scroll',
            orient: 'vertical',
            right: 10,
            top: 20,
            bottom: 20,
            textStyle:{
                color:'#fff',
                fontSize:20
            },
        },
        series: [
            {
                name: '不良率',
                type: 'pie',
                radius: '55%',
                center: ['50%', '50%'],
                data: [
                    {value: 50, name: '贴片'},
                    {value: 60, name: '后焊'},
                    {value: 45, name: '插件'},
                    {value: 36, name: '调试'},
                    {value: 95, name: '入库'}
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)',
                        fontSize:25
                    }
                },
                itemStyle:{
                    normal:{
                        label:{
                            show:true,
                            // position:'inner',
                            // formatter:'{b}',
                            fontSize:20
                        }
                    }
                },
                
            }
        ]
    }
   

