package com.springmvc4.test.aop;

/**
 * @author 低调式男
 * @email 597926661@qq.com
 * 2017-01
 */
public class AServiceImpl implements AService {

    @Override
    public void barA() {
        System.out.println("AServiceImpl实现类barA()实现方法");
    }

    @Override
    public void fooA(String _msg) {
        System.out.println("AServiceImpl实现类fooA(msg:" + _msg + ")实现方法");
    }
}
