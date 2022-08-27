package com.hzh.fitness.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzh.fitness.common.GlobalConstant;
import com.hzh.fitness.common.MyResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author hzh
 */
@RestController
@RequestMapping("/postures")
public class PythonController {

    @RequestMapping("/socket/info")
    public MyResponse<JSONObject> getPythonInfo() throws UnknownHostException {
        JSONObject object = new JSONObject();
        object.put("host", InetAddress.getLocalHost().getHostAddress());
        object.put("port", GlobalConstant.PYTHON_PORT);
        return MyResponse.createResponseBySuccess("success", object);
    }
}
