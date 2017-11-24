package com.younchen.younsampleproject.com.younchen.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by yinlongquan on 2017/10/19.
 */

public class LogicInvocationHandler implements InvocationHandler {


    LogicInterface mLogicInterface;

    public LogicInvocationHandler(LogicInterface logicInterface) {
        mLogicInterface = logicInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start");
        Object result = method.invoke(mLogicInterface, args);
        System.out.println("end");
        return result;
    }
}
