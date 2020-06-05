//返回链接地址
function host(url,port){
    var url  = url || "http://10.33.10.1:8755";
    var port = port || '8755';
    return url+':'+port
}
//返回查询data
function returnData(selectStr){
    var data={
        "excuteParam":selectStr
    }
    return data
}