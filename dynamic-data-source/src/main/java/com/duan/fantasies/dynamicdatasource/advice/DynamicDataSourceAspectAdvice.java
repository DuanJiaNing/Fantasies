package com.duan.fantasies.dynamicdatasource.advice;

import com.duan.fantasies.dynamicdatasource.config.datasource.DataSource;
import com.duan.fantasies.dynamicdatasource.config.datasource.DsKey;
import com.duan.fantasies.dynamicdatasource.config.datasource.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Created on 2018/9/6.
 *
 * @author DuanJiaNing
 */
@Component
@Order(-10)
@Aspect
public class DynamicDataSourceAspectAdvice {


    @Around(value = "@annotation(dataSource)")
    public Object changeDataSource(ProceedingJoinPoint point, DataSource dataSource) throws Throwable {

        DsKey dsKey = dataSource.dsKey();
        if (!DynamicDataSource.contains(dsKey)) {
            return null;
        }

        DynamicDataSource.setDataSourceKey(dsKey);
        try {
            return point.proceed();
        } finally { // 兼顾事务回滚的情况
            DynamicDataSource.clearDataSourceType(); // 恢复默认
        }
    }

}
