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