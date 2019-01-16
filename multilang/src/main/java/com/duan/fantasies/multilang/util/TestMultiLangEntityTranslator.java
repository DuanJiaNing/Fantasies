package com.duan.fantasies.multilang.util;

import com.duan.fantasies.multilang.SimpleMultiLangResolver;
import com.duan.fantasies.multilang.dto.AppDTO;
import com.duan.fantasies.multilang.multilang.MultiLangEntityTranslator;
import com.duan.fantasies.multilang.to.AppTO;

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
