package com.linln.admin.fixtureShelf.validator;

import lombok.Data;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

/**
 * @author 风陵苑主
 * @date 2020/05/14
 */
@Data
public class FixtureShelfValid implements Serializable {
    @NotEmpty(message = "货架编号不能为空")
    private String shelfNo;
}