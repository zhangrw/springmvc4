package com.springmvc4.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @author 低调式男
 * @email 597926661@qq.com
 * 2017-01
 */
@Aspect
public class TestAnnotationAspect {
    @Pointcut("execution(* com.springmvc4.test.aop.BServiceImpl.fooB())")
    private void pointCutMethod() {
    }

    //声明前置通知
    @Before("pointCutMethod()")
    public void doBefore() {
        System.out.println("前置通知");
    }

    //声明后置通知
    @AfterReturning(pointcut = "pointCutMethod()", returning = "result")
    public void doAfterReturning(String result) {
        System.out.println("后置通知");
        System.out.println("---" + result + "---");
    }

    //声明例外通知
    @AfterThrowing(pointcut = "pointCutMethod()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        System.out.println("例外通知");
        System.out.println(e.getMessage());
    }

    //声明最终通知
    @After("pointCutMethod()")
    public void doAfter() {
        System.out.println("最终通知");
    }

    //声明环绕通知
    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("进入方法---环绕通知");
        Object o = pjp.proceed();
        System.out.println("退出方法---环绕通知");
        return o;
    }
}
