package com.springmvc4.test.anoaop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 低调式男 on 上午11:25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class JunitTest {

    @Autowired
    private Employee e;

    @Test
    public void test(){
//        ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Employee e = (Employee)act.getBean("employee");
        e.setName("测试输入的参数");
        e.signIn("原始参数");
    }


}
