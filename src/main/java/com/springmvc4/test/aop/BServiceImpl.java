package com.springmvc4.test.aop;

import org.springframework.stereotype.Service;

/**
 * @author 低调式男
 * @email 597926661@qq.com
 * 2017-01
 */
@Service
public class BServiceImpl {

    public void barB(String _msg, int _type) {
        System.out.println("BServiceImpl.barB(msg:" + _msg + " type:" + _type + ")");
        if (_type == 1)
            throw new IllegalArgumentException("测试异常");
    }

    public void fooB() {
        System.out.println("BServiceImpl.fooB()");
    }

}
