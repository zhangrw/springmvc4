package com.springmvc4.test.anoaop;

/**
 * Created by root on 17-1-10.
 */
public class CommonEmployee implements Employee {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void signIn(String str) {
        name = str;
        System.out.println(name+"已经签到了...........");
        System.out.println("打印传递进来的参数："+str);
    }
}
