// IService.aidl
package com.study.alipayservice;

// Declare any non-default types here with import statements

interface IService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int callSafePay(String username, String password, int money);
}
