package com.duan.fantasies.multilang.multilang;

import com.alibaba.fastjson.JSON;
import com.duan.fantasies.multilang.bo.MultiLang;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created on 2019/1/2.
 * 把多语言数据通过 json 的方式解析为 java 对象
 *
 * @param <T> java 对象类型
 * @author DuanJiaNing
 */
public class JSONMultiLangResolver<T> extends MultiLangResolver<T, String> {

    private final Type type;

    public JSONMultiLangResolver(Type type) {
        this.type = type;
    }

    @Override
    protected boolean checkSource(String original) {
        return !StringUtils.isBlank(original);
    }

    @Override
    public MultiLang<T> parseObject(String original) {
        return JSON.parseObject(original, new ParameterizedType() {

            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{type};
            }

            @Override
            public Type getRawType() {
                return MultiLang.class;
            }

            @Override
            public Type getOwnerType() {
                return MultiLang.class;
            }
        });
    }

    @Override
    public String parseOriginal(MultiLang<T> multiLang) {
        return JSON.toJSONString(multiLang);
    }

}
