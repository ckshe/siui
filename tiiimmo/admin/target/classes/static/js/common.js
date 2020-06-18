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