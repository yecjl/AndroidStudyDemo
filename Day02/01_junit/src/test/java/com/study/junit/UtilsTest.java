package com.study.junit;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 功能：
 * Created by 佳露 on 2017/2/5.
 */
public class UtilsTest {

    @Test
    public void testAdd() throws Exception {

        Utils utils = new Utils();
        assertEquals(4, utils.add(5, 3));
    }
}