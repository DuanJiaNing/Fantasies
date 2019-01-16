package com.duan.fantasies.multilang.dto;

import com.duan.fantasies.multilang.bo.MultiLang;
import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2019/1/16.
 *
 * @author DuanJiaNing
 */
@Data
public class AppDTO implements Serializable {

    private Integer id;
    private MultiLang<SimpleMultiLangItemDTO> appName;

}
