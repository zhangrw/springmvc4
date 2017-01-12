package com.springmvc4.test.anoaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Date;

@Aspect
public class AspectJLogger {
    /**
     * 必须为final String类型的,注解里要使用的变量只能是静态常量类型的
     */
    public static final String EDP = "execution(* com.springmvc4.test.anoaop.CommonEmployee.sign*(..))";

    @Before(EDP)    //spring中Before通知
    public void logBefore(JoinPoint jp) {
        System.out.println("前置通知获取原始参数："+jp.getArgs()[0]);
        jp.getArgs()[0] ="前置通知篡改的参数";
        System.out.println("前置通知处理方法:现在时间是:"+new Date().getTime()+jp.getArgs()[0]);
    }

    @After(EDP)    //spring中After通知
    public void logAfter(JoinPoint jp) {
        System.out.println("后置通知获取函数参数："+jp.getArgs()[0]);
        System.out.println("后置通知处理方法:现在时间是:"+new Date().getTime());
    }

    @Around(EDP)   //spring中Around通知
    public Object logAround(ProceedingJoinPoint joinPoint) {
        System.out.println("环绕通知获取函数参数："+joinPoint.getArgs()[0]);
        System.out.println("环绕通知处理方法:现在时间是:"+new Date().getTime()); //方法执行前的代理处理
        Object[] args = joinPoint.getArgs();
        Object obj = null;
        args[0] = "篡改参数";
        try {
            obj = joinPoint.proceed(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("环绕通知结束:现在时间是:"+new Date().getTime());  //方法执行后的代理处理
        return obj;
    }
}
