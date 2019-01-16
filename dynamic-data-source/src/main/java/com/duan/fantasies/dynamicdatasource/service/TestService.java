package com.duan.fantasies.dynamicdatasource.service;

import com.duan.fantasies.dynamicdatasource.config.datasource.DataSource;
import com.duan.fantasies.dynamicdatasource.config.datasource.DsKey;
import com.duan.fantasies.dynamicdatasource.config.datasource.DynamicDataSource;

/**
 * Created on 2018/11/9.
 *
 * @author DuanJiaNing
 */
public class TestService {


    @DataSource(dsKey = DsKey.DB1)
    public void test() {
        //
    }

    @DataSource(dsKey = DsKey.DB1)
    public void test1() {
        DynamicDataSource.setDataSourceKey(DsKey.DATASOURCE);
        // do somthing
        DynamicDataSource.setDataSourceKey(DsKey.DB1);
    }


}
