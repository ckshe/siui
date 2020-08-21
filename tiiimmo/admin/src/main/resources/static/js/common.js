// 获取URL参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
    //return unescape(r[2]);
        return decodeURI(r[2]);
    return null;
}
// 获取父页面URL参数
function getQueryString2(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = parent.window.location.search.substr(1).match(reg);
    if (r != null)
        return decodeURI(r[2]);
    return null;
}
//时间戳转日期
function timeToDate(timestamp,bool){ //bool为true不展示时分秒
    var date = new Date(timestamp);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0'+ date.getDate() : date.getDate()) + ' ';
    if(bool){
        return Y+M+D;
    }else{
        var h = (date.getHours() < 10 ? '0'+ date.getHours() : date.getHours()) + ':';
        var m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
        var s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
        return Y+M+D+h+m+s;
    }
}


function cacWorkTime(timestamp){
    timestamp = timestamp/1000;
    var h = Math.floor(timestamp/3600)<10?'0'+Math.floor(timestamp/3600):Math.floor(timestamp/3600);
    var m = Math.floor((timestamp%3600)/60)<10?'0'+Math.floor((timestamp%3600)/60):Math.floor((timestamp%3600)/60);
    var s = (timestamp%60)<10?'0'+(timestamp%60):(timestamp%60);
    return h+":"+m+":"+s;
}
//ajax
var http = {
    get: function(url,fn){
        // XMLHttpRequest对象用于在后台与服务器交换数据
        var xhr=new XMLHttpRequest();
        xhr.open('GET',url,true);
        xhr.onreadystatechange=function(){
            // readyState == 4说明请求已完成
            if(xhr.readyState==4){
                if(xhr.status==200 || xhr.status==304){
                    fn(JSON.parse(xhr.responseText));
                }
            }
        }
        xhr.send();
    },

    // data应为'a=a1&b=b1'这种字符串格式，在jq里如果data为对象会自动将对象转成这种字符串格式
    post: function(url,data,fn){
        var xhr=new XMLHttpRequest();
        xhr.open('POST',url,true);
        // 添加http头，发送信息至服务器时内容编码类型
        xhr.setRequestHeader('Content-Type','application/json; charset=utf-8');
        xhr.onreadystatechange=function(){
            if (xhr.readyState==4){
                if (xhr.status==200 || xhr.status==304){
                    fn(JSON.parse(xhr.responseText))
                }
            }
        }
        xhr.send(JSON.stringify(data));
    }
}
function parentLogin() {
    if(parent.document.getElementById('userName').innerHTML=='无'){
        layer.alert('请先上机后操作！',{icon:5});
        return false
    }else{

        return true
    }
}
function isLogin() {
    if(document.getElementById('userName').innerHTML=='无'){
        layer.alert('请先上机后操作！',{icon:5});
        return false
    }else{

        return true
    }
}
//计算两个时间差
function diffTime(startDate,endDate) {
    startDate= new Date(startDate);
    endDate = new Date(endDate);
    var diff=endDate.getTime() - startDate.getTime();//时间差的毫秒数

    //计算出相差天数
    var days=Math.floor(diff/(24*3600*1000));

    //计算出小时数
    var leave1=diff%(24*3600*1000);    //计算天数后剩余的毫秒数
    var hours=Math.floor(leave1/(3600*1000));
    //计算相差分钟数
    var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
    var minutes=Math.floor(leave2/(60*1000));

    //计算相差秒数
    var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
    var seconds=Math.round(leave3/1000);

    var returnStr = '';
    if(minutes>0) {
        returnStr = minutes + "分" + returnStr;
    }
    if(hours>0) {
        returnStr = hours + "时" + returnStr;
    }
    if(days>0) {
        returnStr = days + "天" + returnStr;
    }
    return returnStr;
}
//扫码输入触发事件
function scanCode(domId,func){
    // scanCode('input的id',function () {})
    var func2 = func||function(){}
    var input = document.getElementById(domId);
    input.focus();
    document.onkeydown=function (e) {
        if(e.keyCode === 13){
            if(input == document.activeElement){
                func2()
            }
        }
    };
}
//*****************************form表单模块***********************************************
//普通下拉选择
function Select(obj) {
  this.select = document.getElementById(obj.elemId);
     this.url = obj.url;
    this.type = obj.type||"get";
    this.data = obj.data;
     this.value = obj.value||'id';
    this.text = obj.text||'name';
    this.done = obj.done||function () {};
    this.create();
    this.postData = obj.postData||{};
}
Select.prototype={
    create:function () {
        var that = this;
        if(that.type==='get'){
            http.get(that.url,function (res) {
                var str = "";
                res.data.forEach(function (t) {
                    str+= "<option value='"+t[that.value]+"'>"+t[that.text]+"</option>"
                });
                that.select.innerHTML=str;
                that.done(res.data)
            })
        }else{
            http.post(that.url,that.postData,function (res) {
                var str = "";
                res.data.forEach(function (t) {
                    str+= "<option value='"+t[that.value]+"'>"+t[that.text]+"</option>"
                });
                that.select.innerHTML=str;
                that.done(res.data)
            })
        }
    }
}
//动态多选
function Checkboxs(obj) {
    // new Checkboxs({
    //     url:'/base/badNews/findBadNewsList',
    //     elemId:'badCheck',
    //     value:'bad_name',
    //     title:'bad_name',
    //     done:function () {
    //         form.render()
    //     }
    // });
        this.box = document.getElementById(obj.elemId);
        this.url = obj.url;
       this.type = obj.type||'get';
   this.postData = obj.postData||{};
     this.value = obj.value;
     this.title = obj.title;
       this.done = obj.done||function () {};
       this.create();
     console.log(this)
}
Checkboxs.prototype = {
    create:function () {
        var that = this;
        if(this.type==='get'){
            http.get(that.url,function (res) {
                var str = "";
                res.data.forEach(function (t) {
                    str+='<input type="checkbox" value="'+t[that.value]+'" lay-skin="primary" title="'+t[that.title]+'" >'
                });
                that.box.innerHTML=str;
                that.done(res.data)
            })
        }else{
            http.post(url,that.postData,function (res) {
                var str = "";
                res.data.forEach(function (t) {
                    str+='<input type="checkbox" value="'+t[that.value]+'" lay-skin="primary" title="'+t[that.title]+'" >'
                });
                that.box.innerHTML=str;
                that.done(res.data)
            })
        }
    }
}
//表单元素回车事件触发函数
function Enter(domId,func){
    this.func2 = func||function(){};
    this.input = document.getElementById(domId);
    this.focus();
}
Enter.prototype={
   focus:function () {
      var that = this;
       this.input.onfocus=function () {
           document.onkeydown=function (e) {
               if(e.keyCode === 13){
                   if(that.input == document.activeElement){
                       that.func2()
                   }
               }
           };
       }
   }
};
//获取机台设备名称
function getDeviceName(deviceCode){
    switch(deviceCode){
        case 'B16011':
            return '全自动视觉丝印机1'
            break;
        case 'B15001':
            return '全自动视觉丝印机2'
            break;
        case 'B15002':
            return 'YS12 贴片机1'
            break;
        case 'B15003':
            return 'YS12F 贴片机2'
            break;
        case 'B1902001':
            return 'YS12F 贴片机3'
            break;
        case 'B16012':
            return 'Pyramax100A 无铅热风回流炉'
            break;
        case 'B1811015':
            return '自动光学检测(AOI)1'
            break;
        case 'B15004':
            return '自动光学检测(AOI)2'
            break;
        case 'B16013':
            return '自动下板机'
            break;
        case 'S16002':
            return ''
            break;
        case 'work0022':
            return '备料机台'
            break;
        default:
            return deviceCode
            break;
        
    }
}