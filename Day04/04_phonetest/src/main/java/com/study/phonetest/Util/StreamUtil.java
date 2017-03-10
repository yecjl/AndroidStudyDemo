package com.study.phonetest.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 功能：处理流的工具类
 * Created by danke on 2017/3/6.
 */

public class StreamUtil {
    /**
     * 从输入流中获取字符串
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static String readStream(InputStream is) throws IOException {
        // 内存输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        is.close();
        bos.close();
        return bos.toString();
    }
}
