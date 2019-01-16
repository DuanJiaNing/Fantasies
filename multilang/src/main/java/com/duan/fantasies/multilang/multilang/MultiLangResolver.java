package com.duan.fantasies.multilang.multilang;

import com.duan.fantasies.multilang.bo.MultiLang;
import com.duan.fantasies.multilang.bo.MultiLangItem;

/**
 * Created on 2019/1/2.
 * 把多语言数据解析为 java 对象
 *
 * @param <T> java 对象类型
 * @param <R> 多语言原始数据
 * @author DuanJiaNing
 */
public abstract class MultiLangResolver<T, R> {

    /**
     * 根据语言解析数据
     *
     * @param lang     语言，为 null 时返回 MultiLang.defaulz
     * @param original 多语言原始数据
     * @return 解析所得 java 对象
     */
    public T resolve(String lang, R original) {
        if (!checkSource(original)) {
            return null;
        }

        if (lang == null) {
            return resolveDefault(original);
        }

        return internalResolve(lang, original);
    }

    /**
     * 根据语言解析数据，把默认语言也作为备选项
     *
     * @param lang     语言，为 null 时返回 MultiLang.defaulz
     * @param original 多语言原始数据
     * @return 解析所得 java 对象
     */
    public T resolveIncludeDefault(String lang, R original) {
        if (!checkSource(original)) {
            return null;
        }

        if (lang == null) {
            return resolveDefault(original);
        }

        return internalResolveIncludeDefault(lang, original);
    }

    protected abstract boolean checkSource(R original);

    private T internalResolveIncludeDefault(String lang, R original) {
        MultiLang<T> multiLang = parseObject(original);
        if (multiLang == null) {
            return null;
        }

        if (multiLang.getDefaulz().getLang().equals(lang))
            return multiLang.getDefaulz().getValue();

        for (MultiLangItem<T> item : multiLang.getOthers()) {
            if (item.getLang().equals(lang))
                return item.getValue();
        }

        return null;
    }

    private T internalResolve(String lang, R original) {
        MultiLang<T> multiLang = parseObject(original);
        if (multiLang == null) {
            return null;
        }

        for (MultiLangItem<T> item : multiLang.getOthers()) {
            if (item.getLang().equals(lang))
                return item.getValue();
        }

        return null;
    }

    public T resolveDefault(R original) {
        MultiLang<T> multiLang = parseObject(original);
        if (multiLang == null) {
            return null;
        }

        MultiLangItem<T> defaulz = multiLang.getDefaulz();
        return defaulz == null ? null : defaulz.getValue();
    }

    public abstract MultiLang<T> parseObject(R original);

    public abstract R parseOriginal(MultiLang<T> multiLang);

}
