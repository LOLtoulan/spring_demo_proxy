package com.yage.cproxy;

/**
 * @Author LOL_toulan
 * @Time 2020/2/11 14:42
 * @Message 对生产厂家要求的接口
 */
public interface IProducer{

     void saleProduce(float money);

     void afterServiceProduce(float money);
}
