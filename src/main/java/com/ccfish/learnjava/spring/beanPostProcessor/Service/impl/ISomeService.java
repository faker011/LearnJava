package com.ccfish.learnjava.spring.beanPostProcessor.Service.impl;

import com.ccfish.learnjava.spring.beanPostProcessor.Service.BaseService;

public class ISomeService implements BaseService {
    @Override
    public String doSome() {
        // 增强效果，doSome返回都为大写
        return "hello mike";
    }
}
