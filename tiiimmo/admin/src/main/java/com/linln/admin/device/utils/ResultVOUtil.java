package com.linln.admin.device.utils;

import com.linln.admin.device.enums.ResultEnum;
import com.linln.admin.device.resultVO.ResultVO;

public class ResultVOUtil {
    public static ResultVO<Object> success(Object object) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setData(object);
        resultVO.setCode("200");
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO<Object> error(String code, String msg) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static ResultVO<Object> error(ResultEnum resultEnum) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }
}
