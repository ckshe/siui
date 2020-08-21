package com.linln.admin.device.resultVO;

import com.linln.admin.device.VO.FirstQualityVO;
import lombok.Data;

import java.util.List;

@Data
public class FirstQualityListResultVO {
    private Long total;
    private List<FirstQualityVO> firstQualityVOS;
}
