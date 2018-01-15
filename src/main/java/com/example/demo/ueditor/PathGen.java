package com.example.demo.ueditor;

import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 文件路径生成器
 * Created by yangting on 2018/1/6.
 */
public class PathGen {


    /**
     * 编码字典
     */
    private final static char[] ENC = "0123456789ABCDEFGHIJKLMNOPQRSTUV".toCharArray();

    /**
     * 默认实例
     */
    public static final PathGen DEFAULT = new PathGen("D", new DecimalFormat("000").format(Math.random() * 1000));

    /**
     * 类别编号
     */
    private final char[] type;

    /**
     * 服务器编号
     */
    private final char[] server;

    /**
     * 启动编号
     */
    private final char[] startup;

    /**
     * 序列号
     */
    private final AtomicInteger seq = new AtomicInteger(0);

    /**
     * 生成文件路径
     *
     * @param originName
     * @return
     */
    public static String getPath(String originName) {
        if (null == originName || originName.indexOf('.') < 0) {
            return "";
        }
        String extension = originName.substring(originName.lastIndexOf('.'));
        String fileName = PathGen.DEFAULT.gen() + extension;
        int hash = fileName.hashCode();
        StringBuilder sb = new StringBuilder("/fmsf");
        sb.append("/").append(ENC[hash >>> 29 & 0x7]).append(ENC[hash >>> 24 & 0x1f]);
        sb.append("/").append(ENC[hash >>> 21 & 0x7]).append(ENC[hash >>> 16 & 0x1f]);
        sb.append("/").append(ENC[hash >>> 13 & 0x7]).append(ENC[hash >>> 8 & 0x1f]);
        sb.append("/").append(ENC[hash >>> 5 & 0x7]).append(ENC[hash & 0x1f]);
        sb.append("/").append(fileName);
        return sb.toString();
    }

    /**
     * 创建ID生成器实例
     *
     * @param type
     *            类别编号，1位，^([0-9]|[A-V]){1}$
     * @param server
     *            服务编号，3位，^([0-9]|[A-V]){3}$
     */
    private PathGen(String type, String server) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        if (server == null) {
            throw new NullPointerException("server");
        }
        if (check(type, 1) == false) {
            throw new IllegalArgumentException("type must be \"^([0-9]|[A-V]){1}$\"");
        }
        if (check(server, 3) == false) {
            throw new IllegalArgumentException("server must be \"^([0-9]|[A-V]){3}$\"");
        }
        this.type = type.toCharArray();
        this.server = server.toCharArray();
        int s;
        try {
            // 首选进程号
            s = Math.abs(Integer.parseInt(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]) % 1024);
        } catch (Exception e) {
            // 进程号失败,使用随机数
            s = new SecureRandom().nextInt(1024);
        }
        this.startup = new char[2];
        this.startup[0] = ENC[s >>> 5];
        this.startup[1] = ENC[s & 0x1f];
    }

    /**
     * 生成ID
     *
     * @return ID
     */
    private String gen() {
        // 时间为秒，正最大可支持到2038年，之后变为负数，可支持到2106年
        return gen((int) (System.currentTimeMillis() / 1000L), seq.getAndIncrement());
    }

    /**
     * 生成ID
     */
    private String gen(final int time, final int seq) {
        char[] cs = new char[22];
        cs[0] = type[0];
        cs[1] = server[0];
        cs[2] = server[1];
        cs[3] = server[2];
        cs[4] = startup[0];
        cs[5] = startup[1];
        cs[6] = ENC[time >>> 30 & 0x1f];
        cs[7] = ENC[time >>> 25 & 0x1f];
        cs[8] = ENC[time >>> 20 & 0x1f];
        cs[9] = ENC[time >>> 15 & 0x1f];
        cs[10] = ENC[time >>> 10 & 0x1f];
        cs[11] = ENC[time >>> 5 & 0x1f];
        cs[12] = ENC[time & 0x1f];
        cs[13] = ENC[seq >>> 30 & 0x1f];
        cs[14] = ENC[seq >>> 25 & 0x1f];
        cs[15] = ENC[seq >>> 20 & 0x1f];
        cs[16] = ENC[seq >>> 15 & 0x1f];
        cs[17] = ENC[seq >>> 10 & 0x1f];
        cs[18] = ENC[seq >>> 5 & 0x1f];
        cs[19] = ENC[seq & 0x1f];
        cs[20] = ENC[(cs[0] + cs[2] * 2 + cs[4] * 3 + cs[6] * 4 + cs[8] * 5 + cs[10] * 6 + cs[12] * 7 + cs[14] * 8
                + cs[17] * 9 + cs[19] * 10) & 0x1f];
        cs[21] = ENC[(cs[1] + cs[3] * 2 + cs[5] * 3 + cs[7] * 4 + cs[9] * 5 + cs[11] * 6 + cs[13] * 7 + cs[15] * 8
                + cs[16] * 9 + cs[18] * 10) & 0x1f];
        return new String(cs);
    }

    /**
     * 合法性检查
     */
    private boolean check(String str, int len) {
        if (str.length() != len) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!((c >= 'A' && c <= 'V') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }
        return true;
    }

}
