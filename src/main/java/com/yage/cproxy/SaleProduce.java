package com.yage.cproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author LOL_toulan
 * @Time 2020/2/11 14:31
 * @Message
 */
public class SaleProduce {
    public static void main(String[] args) {

        // 当匿名内部类访问外部成员变量时，外部成员要求是最终的
        // 也就是final修饰的
       final Producer producer = new Producer();

        //卖家直接和买家对接
        //producer.saleProduce(1000f);

        /**
         * 动态代理：
         *   特点：字节码随用随创建，随用随加载
         *   作用：不修改源码的基础上对源码进行加强。
         *   分类：
         *      基于接口的动态代理
         *      基于子类的动态代理
         *  下面是基于接口的动态代理
         *      涉及的类：Proxy
         *      涉及的包：java.lang.reflect.Proxy;JDK官方提供
         *
         *  如何创建代理对象：
         *       proxy的newProxyInstance方法
         *  创建代理对象的要求：
         *      被代理类至少实现一个接口，否则不能使用
         *  newProxyInstance方法的参数：
         *      ClassLoader：类加载器
         *          用于加载代理对象字节码的，写的是被代理对象的类加载器，或者说和被代理对象使用相同的类加载器
         *      Class<T>[] interfaces：字节码数组
         *          它是用于让代理对象和被代理对象有相同的方法。固定写法
         *      InvocationHandler h：用于提供增强的代码
         *          它是让我们如何写代理，我们一般都是写一个该接口的实现类，通常情况下都是匿名内部类，但不是必须的
         *
         */


        IProducer proxyProducer=(IProducer) Proxy.newProxyInstance(producer.getClass().getClassLoader(),
                producer.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 执行代理对象的任何接口方法都会经过该方法
                     * 参数的含义
                     *
                     * @param proxy  代理对象的引用
                     * @param method 当前执行的方法
                     * @param args   当前执行方法所需的参数
                     * @return 和被代理对象有相同的返回值
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //提供增强代码
                        Object returnValue = null;
                        //获取方法的执行参数
                        float money = (Float) args[0];
                        //判断当前方法是不是销售
                        if ("saleProduce".equals(method.getName())) {
                            returnValue =  method.invoke(producer, money*0.8f);
                        }
                        if ("afterServiceProduce".equals(method.getName())) {
                            returnValue =  method.invoke(producer, money);
                        }
                        return returnValue;
                    }
        });
        //调用销售方法
        proxyProducer.saleProduce(10000f);
        //调用售后方法
        proxyProducer.afterServiceProduce(0f);
    }
}
