package com.study.bindservice;

/**
 * 功能：抽取接口
 * 接口为了屏蔽应用程序内部实现的细节，暴露什么样的接口，对调用者而言就只能看到什么样的方法
 * Created by danke on 2017/4/1.
 */

public interface IService {
    void callMethodInService(String name);
}
