
var commonFn = {
	setDataBoard:1,
	setDataBoard2:2,
	
}
$(function(){
	console.log(commonFn.setDataBoard)
	//页面淡入效果
	$(".animsition").animsition({
	    inClass               :   'fade-in',
	    outClass              :   'fade-out',
	    inDuration            :    300,
	    outDuration           :    1000,
	    // e.g. linkElement   :   'a:not([target="_blank"]):not([href^=#])'
	    loading               :    false,
	    loadingParentElement  :   'body', //animsition wrapper element
	    loadingClass          :   'animsition-loading',
	    unSupportCss          : [ 'animation-duration',
	                              '-webkit-animation-duration',
	                              '-o-animation-duration'
	                            ],    
	    overlay               :   false,
	    overlayClass          :   'animsition-overlay-slide',
	    overlayParentElement  :   'body'
  	});
	document.onreadystatechange = subSomething;
	function subSomething() 
	{ 
		if(document.readyState == "complete"){
			$('#loader').hide();
		} 
	} 
	//顶部时间=================
	function getTime(){
		var myDate = new Date();
		var myYear = myDate.getFullYear(); //获取完整的年份(4位,1970-????)
		var myMonth = myDate.getMonth()+1; //获取当前月份(0-11,0代表1月)
		var myToday = myDate.getDate(); //获取当前日(1-31)
		var myDay = myDate.getDay(); //获取当前星期X(0-6,0代表星期天)
		var myHour = myDate.getHours(); //获取当前小时数(0-23)
		var myMinute = myDate.getMinutes(); //获取当前分钟数(0-59)
		var mySecond = myDate.getSeconds(); //获取当前秒数(0-59)
		var week = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
		var nowTime;
		
		nowTime = myYear+'-'+fillZero(myMonth)+'-'+fillZero(myToday)+'&nbsp;&nbsp;'+week[myDay]+'&nbsp;&nbsp;'+fillZero(myHour)+':'+fillZero(myMinute)+':'+fillZero(mySecond);
		$('.topTime').html(nowTime);
	};
	function fillZero(str){
		var realNum;
		if(str<10){
			realNum	= '0'+str;
		}else{
			realNum	= str;
		}
		return realNum;
	}
	setInterval(getTime,1000);
	//============================
	function totalNum(obj,speed){
		var singalNum = 0;
		var timer;
		var totalNum = obj.attr('total');

		if(totalNum){
			timer = setInterval(function(){
				singalNum+=speed;
				if(singalNum>=totalNum){
					singalNum=totalNum;
					clearInterval(timer);
				}
				obj.html(singalNum);
			},1);
		}
	}
	setTimeout(function(){
		$('.progress').each(function(i,ele){
			var PG = $(ele).attr('progress');
			var PGNum = parseInt(PG);
			var zero = 0;
			var speed = 50;
			var timer;
			
			$(ele).find('h4').html(zero+'%');
			if(PGNum<10){
				$(ele).find('.progressBar span').addClass('bg-red');
				$(ele).find('h3 i').addClass('color-red');
			}else if(PGNum>=10 && PGNum<50){
				$(ele).find('.progressBar span').addClass('bg-yellow');
				$(ele).find('h3 i').addClass('color-yellow');
			}else if(PGNum>=50 && PGNum<100){
				$(ele).find('.progressBar span').addClass('bg-blue');
				$(ele).find('h3 i').addClass('color-blue');
			}else{
				$(ele).find('.progressBar span').addClass('bg-green');
				$(ele).find('h3 i').addClass('color-green');
			}
			$(ele).find('.progressBar span').animate({width: PG},PGNum*speed);
			timer = setInterval(function(){
				zero++;
				$(ele).find('h4').html(zero+'%');
				if(zero==PGNum){
					clearInterval(timer);
				}
			},speed);
		});
	},500);
	
	// setDataBoard4();
	// addfourBoardHtml();

	setTimeout(function(){
		setDataBoard1();
		setDataBoard2();
		setDataBoard3();
		setDataBoard4();
	},10)
	//关掉界面
	$('.popupClose').on('click',function(){
		$('.popupClose').css('display','none');
		$('.summary').hide().empty();
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
	$(window).resize(function(){
		// myChart1.resize();
		// try{
		// 	summaryPie1.resize();
		// 	summaryPie2.resize();
		// 	summaryLine.resize();
		// }catch(err){
		// 	return false;
		// }
	});

	// var mySwiper = new Swiper('#container', {
	// 	slidesPerView: 1,
	// 	spaceBetween: 30,
	// 	observer:true,//修改swiper自己或子元素时，自动初始化swiper 
	// 	observeParents:false,//修改swiper的父元素时，自动初始化swiper
	//     loop: true,
	// 	on: {
	// 	transitionEnd:function(){
	// 		// console.log("========="+this.realIndex)
	// 		// if(this.activeIndex==1){
	// 		// 	setDataBoard2();
	// 		// }
	// 		// if(this.activeIndex==2){
	// 		// 	setDataBoard4();
	// 		// 	addfourBoardHtml()
	// 		// }else{
				
				
	// 		// }
	// 		// setDataBoard3();
	// 	},
	// 	slideChangeTransitionEnd: function(){
	// 		if(this.realIndex==0){
	// 			setDataBoard1();
	// 			console.log(this.realIndex)
	// 		}
	// 		if(this.realIndex==1){
	// 			setDataBoard2();
	// 			console.log(this.realIndex)
	// 		}
	// 		if(this.realIndex==2){
	// 			setDataBoard3();
	// 			console.log(this.realIndex)
	// 		}
	// 		if(this.realIndex==3){
	// 			setDataBoard4();
	// 			console.log(this.realIndex)
	// 		}
	// 	}
	// 	},
		
	// 	// onSlideChangeEnd: function(swiper){ 
	// 	// 　　　swiper.update();  
	// 	// 　　　mySwiper.startAutoplay();
	// 	// 　　  mySwiper.reLoop();  
	// 	// },
	// 	pagination: {
	// 	el: '.swiper-pagination',
	// 	clickable: true,
	// 	},
	// 	navigation: {
	// 	nextEl: '.swiper-button-next',
	// 	prevEl: '.swiper-button-prev',
	// 	},
	// });
});