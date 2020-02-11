package com.yage.factory;

import com.yage.service.AccountService;
import com.yage.utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author LOL_toulan
 * @Message 用于创建Service对象的代理工厂
 */
public class BeanFactory {

    private AccountService accountService;

    private TransactionManager txManager;

    public void setTxManager(TransactionManager txManager) {
        this.txManager = txManager;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public AccountService getAccountService() {
        return (AccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Object rtValue = null;
                try {
                    //开启事务
                    txManager.beginTransaction();
                    //执行操作,将Service下的所有方法全部代理
                    rtValue = method.invoke(accountService, args);
                    //提交事务
                    txManager.commit();
                    //返回结果
                    return rtValue;
                } catch (Exception e) {
                    //回滚操作
                    txManager.rollback();
                    throw new RuntimeException(e);
                } finally {
                    //释放连接
                    txManager.release();
                }
            }
        });
    }
}
