package com.springmvc4.test.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author 低调式男
 * @email 597926661@qq.com
 * 2017-01
 */
public class TestAspect {

    public void doAfter(JoinPoint jp) {
        System.out.println("测试后置通知：log Ending method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
        if( jp.getArgs() != null && jp.getArgs().length > 0 ){
            System.out.println("测试参数传递："+jp.getArgs()[0].toString());

        }
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        System.out.println("process time: " + time + " ms");
        System.out.println("环绕通知测试");
        return retVal;
    }

    public void doBefore(JoinPoint jp) {
        System.out.println("测试前置通知：log Begining method: " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName());
    }

    public void doThrowing(JoinPoint jp, Throwable ex) {
        System.out.println("method " + jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + " throw exception");
        System.out.println(ex.getMessage());
    }

}
