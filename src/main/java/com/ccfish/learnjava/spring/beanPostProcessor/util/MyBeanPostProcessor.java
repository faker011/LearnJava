package com.ccfish.learnjava.spring.beanPostProcessor.util;

import com.ccfish.learnjava.spring.beanPostProcessor.Service.impl.ISomeService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
        System.out.println("bean 对象初始化之前");
        return bean;
        // return bean对象监控代理对象
    }
    @Override
    public Object postProcessAfterInitialization(Object beanInstance, String beanName) throws BeansException {
        System.out.println("bean 对象初始化之后");
        Class beanClass  = beanInstance.getClass();
        if (beanClass == ISomeService.class){
            Object proxy = Proxy.newProxyInstance(beanInstance.getClass().getClassLoader(),
                    beanInstance.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        /*
                        * method :doSome
                        * args:doSome执行时接受的实参
                        * proxy:代理监控对象
                        * */
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("ISomeService doSome 被拦截");
                            String result = (String)method.invoke(beanInstance, args); // beanInstance.doSome()
                            return result.toUpperCase();
                        }
                    });
            return proxy;
        }
        return beanInstance;
    }
}
