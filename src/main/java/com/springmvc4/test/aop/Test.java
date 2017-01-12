package com.springmvc4.test.aop;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 低调式男
 * @email 597926661@qq.com
 * 2017-01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class Test {
    @Autowired
    private AService testa;

    @org.junit.Test
    public void test(){
        testa.barA();
//        testa.fooA("测试");
    }


}
