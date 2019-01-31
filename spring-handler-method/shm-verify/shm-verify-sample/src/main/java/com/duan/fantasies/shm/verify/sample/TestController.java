package com.duan.fantasies.shm.verify.sample;

import com.duan.fantasies.shm.verify.annoation.ParamVerifyComposite;
import com.duan.fantasies.shm.verify.annoation.method.RequestParamValueVerify;
import com.duan.fantasies.shm.verify.annoation.method.RequestParamVerify;
import com.duan.fantasies.shm.verify.annoation.method.RequestParamsVerifyComposite;
import com.duan.fantasies.shm.verify.enums.VerifyRule;
import com.duan.fantasies.shm.verify.enums.VerifyValueRule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2018/11/1.
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/user")
    @RequestParamsVerifyComposite({
            @ParamVerifyComposite(valueVerify = @RequestParamValueVerify(param = "password", rule = VerifyValueRule.TEXT_LENGTH_GREATER_THAN, value = "5")),
            @ParamVerifyComposite(valueVerify = @RequestParamValueVerify(param = "password", rule = VerifyValueRule.TEXT_LENGTH_LESS_THAN, value = "8")),
            @ParamVerifyComposite(@RequestParamVerify(param = "name", rule = VerifyRule.NOT_BLANK)),
            @ParamVerifyComposite(valueVerify = @RequestParamValueVerify(param = "age", rule = VerifyValueRule.VALUE_GREATER_THAN, value = "18"))
    })
    public ResultModel addUser(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam Integer age) {

        return null;
    }


}
