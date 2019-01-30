package com.duan.fantasies.shm.sample;

import com.duan.fantasies.shm.log.ControllerLog;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2018/11/1.
 *
 * @author DuanJiaNing
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/t1")
    @ControllerLog(value = "入参 modelCode 的值是 {model.code} ,结果是 {$},参数 num={num}", data = {"model.code", "num"})
    public String testDeviceType(@RequestBody ResultModel model, @RequestParam String num) {
        return "";
    }

    @GetMapping("/t2")
    @ControllerLog("测试方法2")
    public String testDeviceType1() {
        return "";
    }

    @GetMapping("/t3")
    @ControllerLog("#{(#$.code == 200 ? '成功' : '失败'+': 测试方法 3 ，参数值为' + #num}")
    public ResultModel testDeviceType2(@RequestParam String num) {
        ResultModel model = new ResultModel();
        model.setCode(200);
        return model;
    }


}
