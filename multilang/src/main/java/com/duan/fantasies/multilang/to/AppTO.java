package com.duan.fantasies.multilang.to;

import lombok.Data;

import java.io.Serializable;

/**
 * Created on 2019/1/16.
 *
 * @author DuanJiaNing
 */
@Data
public class AppTO implements Serializable {

    private Integer id;
    private String appName;

}
