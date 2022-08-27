package com.hzh.fitness.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzh.fitness.common.LoginList;
import com.hzh.fitness.exception.GlobalException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author hzh
 */
@Aspect
@Component
public class ControllerAspect {

    @Autowired
    private LoginList list;

    private static final Log logger = LogFactory.getLog(ControllerAspect.class);

    @Around("execution(* com.hzh.fitness.controller.*.*(..))")
    public Object printInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes sa = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sa.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] paras = joinPoint.getArgs();
        logger.info(request.getMethod() + " \"" + request.getRequestURI() + "\" from " + request.getRemoteAddr() +
                " call to " + signature.toShortString());
        Parameter[] parameters = signature.getMethod().getParameters();
        ArrayList<Object> jsonParas = new ArrayList<>();
        for (int i = 0; i < paras.length; i++) {
            if (parameters[i].getAnnotation(Ignore2Json.class) == null) {
                jsonParas.add(paras[i]);
            } else {
                jsonParas.add(paras[i].getClass().getName());
            }
        }
        logger.info("方法参数: " + JSON.toJSONString(jsonParas));
        return joinPoint.proceed();
    }

    @AfterReturning(
            value = "execution(* com.hzh.fitness.exception.*Handler.*Handler(..)) || execution(* com.hzh.fitness.controller.*.*(..))",
            returning = "response")
    public void printInfo(Object response) {
        logger.info("返回参数: " + JSON.toJSONString(response));
    }

    @Before("execution(* com.hzh.fitness.controller.*.*WithToken(..))")
    public void authentication(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes sa = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sa.getRequest();
        String token = request.getHeader("token");
        if (token == null) {
            throw new GlobalException("have no token");
        }
        if (!list.checkLogin(token)) {
            throw new GlobalException("not permitted");
        }
    }

    private Object[] formatParas(Object[] paras, Type[] types) {
        for (int i = 0; i < paras.length; i++) {
            String name = types[i].getTypeName();
            if (name.equals(HttpServletRequest.class.getTypeName()) || name.equals(HttpServletResponse.class.getTypeName())) {
                paras[i] = name;
            } else if (paras[i] instanceof JSONObject) {
                JSONObject objects = (JSONObject) paras[i];
                Collection<String> keySet = objects.keySet();
                for (String key : keySet) {
                    if (objects.get(key) instanceof byte[]) {
                        objects.put(key, "!BYTE_DATA!");
                    }
                }
                paras[i] = objects;
            }
        }
        return paras;
    }

}
