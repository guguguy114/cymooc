package com.cykj.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: TODO
 * 本类为静态资源处理类实现对静态资源的处理
 * 该类中主要实现两个方法，分别是获取文件字节内容和获取文件类型
 * @author Guguguy
 * @version 1.0
 * @since 2023/11/21 14:26
 */
public class StaticResourceHandler {
    /**
     * Description: TODO
     * 读取文件字节数组，返回文件字节数组
     * @return byte[]
     * @author Guguguy
     * @since 2023/11/21 19:26
     */
    public static byte[] getContentBytes(String filePath) {
        // 创建输入流，读取文件
        File file = new File(filePath);
        byte[] fileBytes;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // 根据文件的大小创建对应大小的字节数组
            fileBytes = new byte[fis.available()];
            int length =  fis.read(fileBytes);
            fis.close();
        } catch (IOException e) {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
        return fileBytes;
    }

    /**
     * Description: TODO
     * 读取输入文件的文件类型，并返回字符串
     * @return java.lang.String
     * @author Guguguy
     * @since 2023/11/21 20:45
     */
    public static String getMimeType(String filePath){
        try {
            return Files.probeContentType(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> getUploadDataDetail(String data) {
        Map<String, String> resMap = new HashMap<>();
        String[] dataRes = data.split(";");
        String dataType = dataRes[0].split(":")[1];
        String dataCode = dataRes[1].split(",")[0];
        String dataS = dataRes[1].split(",")[1];
        resMap.put("dataType", dataType);
        resMap.put("dataCode", dataCode);
        resMap.put("data", dataS);
        System.out.println(dataS);
        return resMap;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!keyword.isEmpty()) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, ("\\" + "\\") + key);
                }
            }
        }
        return keyword;
    }
}
