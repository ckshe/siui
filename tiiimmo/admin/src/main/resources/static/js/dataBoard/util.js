//返回链接地址
function host(url, port) {
    var url = url || "http://10.33.10.1:8755";
    var port = port || '8755';
    return url + ':' + port
}
//返回查询data
function returnData(selectStr) {
    var data = {
        "excuteParam": selectStr
    }
    return data
}
//12机台设备信息
var deviceInfo = [
    {
        deviceName:'全自动视觉丝印机1',
        device_code:'B16011'
    },
    {
        deviceName:'自动上板机',
        device_code:'S16001'
    },
    {
        deviceName:'全自动视觉丝印机2',
        device_code:'B15001'
    },
    {
        deviceName:'YS12 贴片机1',
        device_code:'B15002'
    },
    {
        deviceName:'YS12F 贴片机2',
        device_code:'B15003'
    },
    {
        deviceName:'YS12F 贴片机3',
        device_code:'B1902001'
    },
    {
        deviceName:'自动光学检测(AOI)1',
        device_code:'B15004'
    },
    {
        deviceName:'Pyramax100A 无铅热风回流炉',
        device_code:'B16012'
    },
    {
        deviceName:'自动上板机',
        device_code:'S16001'
    },
    {
        deviceName:'自动光学检测(AOI)2',
        device_code:'sB1811015'
    },
    {
        deviceName:'无铅波峰焊接机',
        device_code:'B16013'
    },
    {
        deviceName:'四轴自动焊锡机器人',
        device_code:'B170101'
    }
]

function getweek(dateString){
	var da='';
	if(dateString==undefined){
		var now=new Date();
		var now_m=now.getMonth()+1;
		now_m=(now_m<10)?'0'+now_m:now_m;
		var now_d=now.getDate();
		now_d=(now_d<10)?'0'+now_d:now_d;
		da=now.getFullYear()+'-'+now_m+'-'+now_d;
		console.log('今天系统时间是:'+da);
	}else{
		da=dateString;//日期格式2015-12-30
	}  
    var date1 = new Date(da.substring(0,4), parseInt(da.substring(5,7)) - 1, da.substring(8,10));//当前日期
	var date2 = new Date(da.substring(0,4), 0, 1); //1月1号
	//获取1月1号星期（以周一为第一天，0周一~6周日）
    var dateWeekNum=date2.getDay()-1;
	if(dateWeekNum<0){dateWeekNum=6;}
	if(dateWeekNum<4){
	  //前移日期
	  date2.setDate(date2.getDate()-dateWeekNum);
	}else{
	  //后移日期
	  date2.setDate(date2.getDate()+7-dateWeekNum);
	}
	var d = Math.round((date1.valueOf() - date2.valueOf()) / 86400000);
	if(d<0){
	  var date3 = (date1.getFullYear()-1)+"-12-31";
	  return getYearWeek(date3);
	}else{
	  //得到年数周数
	  var year=date1.getFullYear();
	  var week=Math.ceil((d+1 )/ 7);
	  console.log(year+"年第"+week+"周");
	  return  week;
	}
}
function weekinyear(){
    var newWeek = getweek(),oldWeek=0;
    var now=new Date();
    var now_m=now.getMonth()+1;
    now_m=(now_m<10)?'0'+now_m:now_m;
    var now_d=now.getDate();
    now_d=(now_d<10)?'0'+now_d:now_d;
    da=now.getFullYear()+'-'+now_m+'-'+now_d;
    var firstweekNum = new Date(da.substring(0,4), 0, 1).getDay(); //1月1号
    console.log((now.getFullYear()-1).toString().substr(2, 2))
    var oldYear = (now.getFullYear()-1).toString().substr(2, 2)+'年';
    var nowYear = now.getFullYear().toString().substr(2, 2)+'年';
    if(newWeek<4){
        oldWeek = getweek((now.getFullYear()-1)+"-12-31")
        if(firstweekNum>0){
            oldWeek = oldWeek -1;
        }
        switch(newWeek){
            case 1:
                return [oldYear+(oldWeek-2)+'周',oldYear+(oldWeek-1)+'周',oldYear+oldWeek+'周',nowYear+newWeek+'周']
            break;
            case 2:
                return [oldYear+(oldWeek-1)+'周',oldYear+oldWeek+'周',nowYear+(newWeek-1)+'周',nowYear+newWeek+'周']
            break;
            case 3:
                return [oldYear+oldWeek+'周',nowYear+(newWeek-2)+'周',nowYear+(newWeek-1)+'周',nowYear+newWeek+'周']
            break;
            default:
                console.log('出错了')
            break
        } 
    }else{
        return [nowYear+(newWeek-3)+'周',nowYear+(newWeek-2)+'周',nowYear+(newWeek-1)+'周',nowYear+newWeek+'周']
    }
}