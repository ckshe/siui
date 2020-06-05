
	// 第四看板========================================================================
	//看板四弹框
	function setDataBoard4(params) {
		db4P1.setOption(db4POption1);
		db4P2.setOption(db4POption2);
		addfourBoardHtml();
	}
	var db4P1,db4P2;
	db4P1 = echarts.init(document.getElementById('db4P1'),'macarons');
	db4P2 = echarts.init(document.getElementById('db4P2'),'macarons');
	var db4POption1 = {
		title: {
            text: "上岗率",
            textStyle: {
                // textShadowColor: "#fff",
                // textShadowBlur: 4,
                // textShadowOffsetX: 2,
				// textShadowOffsetY: 2,
				color:'#fff',
				fontSize:22,
				fontWeight: 'normal',
            },
			top: 'top',
			right: 'center',
		},
		tooltip: {
            textStyle:{
                color:'#fff',
				fontSize:22,
				fontWeight: 'normal',
            }
		},
		grid:{
			top: "10%",
			right: '10%',
			bottom: "10%",
			left: "10%",
			backgroundColor:'#ff330e'
		},
		xAxis: {
			naem:'工序',
			axisLabel:{
				textStyle:{
					show:true,
					color:'rgba(255,255,255,1)',
					fontSize:20
				}
			},
			data: ["贴片","后焊","插件","调试"]
		},
		yAxis: {
			name:'',
			axisLabel:{
				textStyle:{
					show:true,
					color:'rgba(255,255,255,1)',
					fontSize:20
				}
			},
			axisLine:{
				lineStyle:{
					color:'#fff',
					fontSize:20
				}
			},
			min:0,
			max:100
		},
		series: [{
			name: '',
			type: 'bar',
			data: [50, 79, 66, 92],
			barWidth:50,
			label: {
                normal: {
                    show: true,
                    position: 'inside',
					formatter: '{c}',
					color: '#fff',
					fontSize:20
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
				color:'#fff',
				fontSize:22,
				fontWeight: 'normal',
            },
			top: 'top',
			right: 'center',
		},
		tooltip: {
			textStyle:{
                color:'#fff',
				fontSize:22,
				fontWeight: 'normal',
            }
		},
		grid:{
			top: "10%",
			right: '10%',
			bottom: "10%",
			left: "10%",
			backgroundColor:'#ff330e'
		},
		xAxis: {
			naem:'工序',
			axisLabel:{
				textStyle:{
					show:true,
					color:'rgba(255,255,255,1)',
					fontSize:20
				}
			},
			data: ["贴片","后焊","插件","调试"]
		},
		yAxis: {
			name:'',
			axisLabel:{
				textStyle:{
					show:true,
					color:'rgba(255,255,255,1)',
					fontSize:20
				}
			},
			axisLine:{
				lineStyle:{
					color:'#fff',
					fontSize:20
				}
			},
			min:0,
			max:100
		},
		series: [{
			name: '',
			type: 'bar',
			data: [56, 88, 77, 95],
			barWidth:50,
			label: {
                normal: {
                    show: true,
                    position: 'inside',
					formatter: '{c}',
					color: '#fff',
					fontSize:20
                }
            },
		}]
	};
	// 动态生成模板
    var theadHtml='',tbodyHtml='';
    var theadData = ['员工名称','工位','所在工序','任务信息']
    var tbodyData = [
        ['风陵苑主','A01','贴片','ZC1712120023'],
        ['风陵苑主','A02','贴片','ZC1712120023'],
        ['风陵苑主','A03','贴片','ZC1712120023'],
        ['风陵苑主','A04','贴片','ZC1712120023'],
        ['风陵苑主','A05','贴片','ZC1712120023'],
        ['风陵苑主','A06','贴片','ZC1712120023'],
        ['风陵苑主','A07','贴片','ZC1712120023'],
        ['风陵苑主','A08','贴片','ZC1712120023'],
        ['风陵苑主','A09','贴片','ZC1712120023'],
        ['风陵苑主','A10','贴片','ZC1712120023'],
        ['风陵苑主','A11','贴片','ZC1712120023'],
        ['风陵苑主','A12','贴片','ZC1712120023'],
        ['风陵苑主','A13','贴片','ZC1712120023'],
        ['风陵苑主','A14','贴片','ZC1712120023'],
        ['风陵苑主','A15','贴片','ZC1712120023']
    ]

    function addfourBoardHtml(){
		if(tbodyData.length>0){
			colwidth = (98/theadData.length).toFixed(2)+'%';
		}else{
			colwidth = 'auto';
		}
        theadHtml = '<div class="StateTit">';
        for(var i=0;i<theadData.length;i++){
            theadHtml += '<span  style="width:'+colwidth+'">'+theadData[i]+'</span>';
        }
        theadHtml += '</div>';
        tbodyHtml = '<div id="FontScroll" class="board4Scroll"><ul>';
        for(var j=0;j<tbodyData.length;j++){
            tbodyHtml += '<li>'+
                '<div class="fontInner clearfix">'+
                    '<span  style="width:'+colwidth+'">'+tbodyData[j][0]+'</span>'+
                    '<span  style="width:'+colwidth+'">'+tbodyData[j][1]+'</span>'+
                    '<span  style="width:'+colwidth+'">'+tbodyData[j][2]+'</span>'+
                    '<span  style="width:'+colwidth+'">'+tbodyData[j][3]+'</span>'+
                '</div>'+
            '</li>';
        }
        tbodyHtml  += '<ul></div>';
        $("#fourBoard").html(theadHtml+tbodyHtml);
                //运单状态文字滚动
        $('.board4Scroll').FontScroll({time: 3000,num: 1,styleName:'line4'});
    }