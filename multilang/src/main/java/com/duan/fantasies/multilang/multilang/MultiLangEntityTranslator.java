package com.duan.fantasies.multilang.multilang;

/**
 * Created on 2019/1/4.
 *
 * @param <T> 多语言原始数据
 * @param <R> 转换后的多语言实体
 * @author DuanJiaNing
 */
public interface MultiLangEntityTranslator<T, R> {

    /**
     * 从 entity 中解析获得包含所有语言的实体对象
     *
     * @param original 包含多语言原始数据（对应数据库中的存储结构，如 json）
     * @return 解析后包含所有语言的实体对象
     */
    R translate(T original);

    /**
     * 只获取指定语言对应的实体对象
     *
     * @param entity 包含多语言原始数据（如 json）
     * @param lang   语言限定
     * @return 解析后只包含指定语言的实体对象
     */
//    Object specLang(T entity, String lang);

    /**
     * 将包含多语言数据的实体类解析为原始数据（对应数据库中的存储结构）实体对象
     *
     * @param entity 包含多语言数据的实体类
     * @return 多语言原始数据实体
     */
    T parseOriginal(R entity);
}
