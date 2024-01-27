package com.cykj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Description: TODO
 * 数据库配置文件解析工具类
 * @author Guguguy
 * @version 1.0
 * @since 2023/11/28 22:06
 */
public class DBPropertiesUtils {
    // 创建JDK配置文件读取工具类
    private static final HashMap<String, String> propertiesMap = new HashMap<>();

    static {
        // 读取配置文件
        File propertiesFile = new File("./config/db.properties");
        FileInputStream fis;
        try {
            fis = new FileInputStream(propertiesFile);
            Scanner sc = new Scanner(fis);
            while (true){
                if (sc.hasNext()){
                    String[] textApart = sc.nextLine().split("=", 2);
                    if (textApart.length == 2){
                        propertiesMap.put(textApart[0].trim(), textApart[1].trim());
                    }
                }else {
                    System.out.println(propertiesMap);
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static HashMap<String, String> getPropertiesMap(){
        return propertiesMap;
    }
}
