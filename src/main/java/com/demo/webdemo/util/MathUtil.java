package com.demo.webdemo.util;

import java.util.Random;

public class MathUtil {
    /**
     * 获取0到2之间的随机数（不包括2）
     *
     * @return
     */
    public static int getRandom() {
        int limit = 2;
        Random random = new Random();
        int result = random.nextInt(2);
        return result;
    }
}
