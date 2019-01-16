package com.duan.fantasies.multilang.bo;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2019/1/4.
 *
 * @author DuanJiaNing
 */
public class MultiLang<T> implements Serializable {

    private MultiLangItem<T> defaulz;
    private List<MultiLangItem<T>> others;

    public MultiLangItem<T> getDefaulz() {
        return defaulz;
    }

    public void setDefaulz(MultiLangItem<T> defaulz) {
        this.defaulz = defaulz;
    }

    public List<MultiLangItem<T>> getOthers() {
        return others;
    }

    public void setOthers(List<MultiLangItem<T>> others) {
        this.others = others;
    }
}
