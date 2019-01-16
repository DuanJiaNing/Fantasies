package com.duan.fantasies.multilang.service;

import com.duan.fantasies.multilang.SimpleMultiLangResolver;
import com.duan.fantasies.multilang.dto.AppDTO;
import com.duan.fantasies.multilang.to.AppTO;
import com.duan.fantasies.multilang.util.TestMultiLangEntityTranslator;
import com.duan.fantasies.multilang.vo.AppVO;

/**
 * Created on 2019/1/16.
 *
 * @author DuanJiaNing
 */
public class TestService {

    public AppVO get() {
        AppTO to = new AppTO(); // 假设从数据库中查询到的实体 appName 为包含所有语言数据的 json 字符串
        AppVO vo = new AppVO();
        vo.setId(to.getId());
        vo.setAppName(SimpleMultiLangResolver.resolve("en_US", to.getAppName()));

        return vo;
    }

    public Integer save(AppDTO dto) {
        TestMultiLangEntityTranslator translator = new TestMultiLangEntityTranslator();
        AppTO to = translator.parseOriginal(dto);
        int id = 1; // orm insert: 数据库插入
        return id;
    }

}
