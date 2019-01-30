package com.duan.fantasies.shm.log;

import com.duan.fantasies.shm.core.HandlerMethodPostProcessor;
import com.duan.fantasies.shm.log.core.LogExpressionParser;
import com.duan.fantasies.shm.log.core.OperatorLogDataParser;
import com.duan.fantasies.shm.log.core.OperatorLogExpressionParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2018/11/7.
 *
 * @author DuanJiaNing
 */
@Component
public class ControllerLogHandler implements HandlerMethodPostProcessor {

    @Autowired
    private ControllerLogCallback callback;

    @Override
    public void postProcessorAfterInvoke(Object result, ServletInvocableHandlerMethod handlerMethod, Object... args) {
        ControllerLog annotation = handlerMethod.getMethodAnnotation(ControllerLog.class);
        if (annotation == null) {
            return;
        } else {

            String expression = annotation.value();
            String content = null;

            Map<String, Object> map = getParamNameValueMap(handlerMethod.getMethodParameters(), args);
            OperatorLogDataParser dataParser = new OperatorLogDataParser(map, result);

            if (StringUtils.isBlank(expression)) {
                callback.handleDefaultLog(result, handlerMethod, args);
            } else if (expression.startsWith("#{")) { // SpEL 表达式
                // @ControllerLog("#{'pageNum='+#criteria.pageNum+' resultCode=' + #$.code+' method='+#th}")

                StandardEvaluationContext context = new StandardEvaluationContext();
                context.setVariables(map);
                context.setVariable("$", result);
                context.setVariable("method", handlerMethod);

                ExpressionParser parser = new SpelExpressionParser();
                Object value = parser.parseExpression(expression, new TemplateParserContext()).getValue(context);
                content = String.valueOf(value);

            } else { // 自定义的写法
                // @ControllerLog("pageNum={criteria.pageNum} resultCode={$.code}")

                LogExpressionParser logExpressionParser = new OperatorLogExpressionParser(map, dataParser, result, handlerMethod);
                content = logExpressionParser.parse(expression);
            }

            Object[] datas = null;
            if (annotation.data().length > 0) {
                datas = new Object[annotation.data().length];
                for (int i = 0; i < annotation.data().length; i++) {
                    datas[i] = dataParser.parseValue(annotation.data()[i]);
                }
            }

            callback.handleCustomLog(content, annotation.tags(), datas, result, handlerMethod, args);

        }
    }

    private Map<String, Object> getParamNameValueMap(MethodParameter[] methodParameters, Object[] args) {
        Map<String, Object> map = new HashMap<>(args.length);
        for (int i = 0; i < args.length; i++) {
            MethodParameter parameter = methodParameters[i];
            String name = parameter.getParameterName();
            map.put(name, args[i]);
        }

        return map;
    }

}
