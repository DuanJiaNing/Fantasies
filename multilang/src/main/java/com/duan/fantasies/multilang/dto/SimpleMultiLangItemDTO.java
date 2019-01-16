package com.duan.fantasies.multilang.dto;

import java.io.Serializable;

/**
 * Created on 2019/1/6.
 *
 * @author DuanJiaNing
 */
public class SimpleMultiLangItemDTO implements Serializable {

    private String content;
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
