package com.cykj.util;

import com.cykj.annotation.Servlet;
import com.cykj.servlet.BasicServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Description: TODO
 * 解析servlet的工具类
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/18 15:25
 */
public class ServletUtils {
    private static final HashMap<String, BasicServlet> servletHashMap = new HashMap<>();

    // 解析配置文件
    static {
        Properties properties = new Properties();
        try {
            String path = "./src/com/cykj/servlet";
            File file = new File(path);
            File[] files = file.listFiles();
            if (files != null) {
                for (File fileIn : files) {
                    String servletFileName = fileIn.getName().split("\\.")[0];
                    if (!Objects.equals(servletFileName, "BasicServlet")){
                        Class<?> servletClass = Class.forName("com.cykj.servlet." + servletFileName);
                        String module = servletClass.getAnnotation(Servlet.class).value();
                        servletHashMap.put(module, (BasicServlet) servletClass.newInstance());
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static BasicServlet getServlet(String key){
        return servletHashMap.get(key);
    }
}
