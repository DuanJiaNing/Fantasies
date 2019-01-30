package com.duan.fantasies.multilang.sample;


import com.duan.fantasies.multilang.core.MultiLang;
import com.duan.fantasies.multilang.sample.dto.SimpleMultiLangItemDTO;
import com.duan.fantasies.multilang.sample.enums.Enabled;
import com.duan.fantasies.multilang.sample.multilang.JSONMultiLangResolver;

/**
 * Created on 2019/1/2.
 *
 * @author DuanJiaNing
 */
public class SimpleMultiLangResolver {

    private static final JSONMultiLangResolver<SimpleMultiLangItemDTO> JSON_MULTI_LANG_RESOLVER =
            new JSONMultiLangResolver<SimpleMultiLangItemDTO>(SimpleMultiLangItemDTO.class);

    public static String resolve(String lang, String jsonStr) {
        SimpleMultiLangItemDTO langItem = JSON_MULTI_LANG_RESOLVER.resolve(lang, jsonStr);
        if (langItem == null || !Enabled.enable(langItem.getStatus())) {
            SimpleMultiLangItemDTO langItemDTO = JSON_MULTI_LANG_RESOLVER.resolveIncludeDefault("zh_CN", jsonStr);
            return langItemDTO == null ? null : langItemDTO.getContent();
        }

        return langItem.getContent();
    }

    public static MultiLang<SimpleMultiLangItemDTO> parse(String jsonStr) {
        return JSON_MULTI_LANG_RESOLVER.parseObject(jsonStr);
    }

    public static String toJSONString(MultiLang<SimpleMultiLangItemDTO> multiLang) {
        return JSON_MULTI_LANG_RESOLVER.parseOriginal(multiLang);
    }
}
