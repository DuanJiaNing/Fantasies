package com.duan.fantasies.multilang.core;

import java.io.Serializable;

/**
 * Created on 2019/1/4.
 *
 * @author DuanJiaNing
 */
public class MultiLangItem<T> implements Serializable {

    private String lang;
    private T value;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
