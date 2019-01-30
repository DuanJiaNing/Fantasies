package com.duan.fantasies.multilang.sample.util;

import com.duan.fantasies.multilang.sample.SimpleMultiLangResolver;
import com.duan.fantasies.multilang.core.MultiLangEntityTranslator;
import com.duan.fantasies.multilang.sample.dto.AppDTO;
import com.duan.fantasies.multilang.sample.to.AppTO;

/**
 * Created on 2019/1/16.
 *
 * @author DuanJiaNing
 */
public class TestMultiLangEntityTranslator implements MultiLangEntityTranslator<AppTO, AppDTO> {

    @Override
    public AppDTO translate(AppTO original) {
        AppDTO dto = new AppDTO();
        dto.setId(original.getId());
        dto.setAppName(SimpleMultiLangResolver.parse(original.getAppName()));

        return dto;
    }

    @Override
    public AppTO parseOriginal(AppDTO entity) {
        AppTO to = new AppTO();
        to.setId(entity.getId());
        to.setAppName(SimpleMultiLangResolver.toJSONString(entity.getAppName()));

        return to;
    }
}
