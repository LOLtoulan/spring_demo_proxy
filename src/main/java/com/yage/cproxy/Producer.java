package com.yage.cproxy;

/**
 * @Author LOL_toulan
 * 生产产家需要提供的服务（方法）
 */
public class Producer implements IProducer {

    public void saleProduce(float money){
        System.out.println("销售产品，并拿到钱"+money);
    }

    public void afterServiceProduce(float money){
        System.out.println("提供售后服务，并拿到钱"+money);
    }

}
