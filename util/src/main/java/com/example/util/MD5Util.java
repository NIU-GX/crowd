package com.example.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author NGX
 * @Date 2020/5/1 21:51
 * @Description
 */
public class MD5Util {
    public static String md5(String source) {
        // 1. 先判断source是否有效
        if (source == null || source.length() == 0) {
            throw new RuntimeException(ConstantUtil.MESSAGE_STRING_INVALIDATE);
        }

        // 2. 获取MessageDigest对象
        try {
            String algorithm = "md5";
            MessageDigest instance = MessageDigest.getInstance(algorithm);

            // 3. 获取明文密码队友的字节数组
            byte[] bytes = source.getBytes();
            // 4. 执行加密
            byte[] digest = instance.digest(bytes);
            // 5. 创建BigInteger
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, digest);
            // 6. 按照16进制转换为字符串
            int radix = 16;
            String result = bigInteger.toString(radix);

            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
