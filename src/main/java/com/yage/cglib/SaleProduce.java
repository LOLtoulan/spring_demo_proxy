package com.yage.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

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
         *  下面是基于子类的动态代理
         *      涉及的类：Enhancer
         *      涉及的包：net.sf.cglib.cproxy.Enhancer;第三方
         *
         *  如何创建代理对象：
         *       Enhancer的create()方法
         *  创建代理对象的要求：
         *      被代理类不能是最终类
         *  create方法的参数：
         *      Class：字节码
         *          它是用于指定被代理对象的字节码
         *      Callback callback：用于提供增强的代码
         *          它是让我们如何写代理，我们一般都是写一个该接口的实现类，通常情况下都是匿名内部类，但不是必须的
         *          我们一般写的都是该接口的子接口实现类：MethodInterceptor()
         */
      Producer cglibProducer = (Producer)Enhancer.create(producer.getClass(), new MethodInterceptor() {
            /**
             * 执行被代理对象的所有方法都会经过该方法
             * @param proxy 代理对象的引用
             * @param method 当前执行的方法
             * @param args   当前执行方法所需参数
             * @param methodProxy 当前执行方法的代理对象
             * @return   和被代理对象有相同的返回值
             * @throws Throwable 异常
             */
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
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

      cglibProducer.saleProduce(10000f);
    }
}
