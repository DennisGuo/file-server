package cn.geobeans.server.file.common;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * 工具类
 */
public class U {

    /**
     * 读取 resources 下的文件内容
     * @param fileName
     * @return
     */
    public static String getResourceFile(String fileName) {

        String result = null;

        ClassLoader classLoader = U.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
