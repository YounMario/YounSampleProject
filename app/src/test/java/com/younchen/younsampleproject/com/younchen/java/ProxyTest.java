package com.younchen.younsampleproject.com.younchen.java;


import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by yinlongquan on 2017/10/19.
 */

public class ProxyTest {

    @Test
    public void textProxy() {

        LogicInterfaceImpl logic = new LogicInterfaceImpl();

        LogicInterface logicInstance = (LogicInterface) Proxy.newProxyInstance(LogicInterfaceImpl.class.getClassLoader(), new Class[]{LogicInterface.class}, new LogicInvocationHandler(logic));

        logicInstance.doSomeThing();
    }
}
