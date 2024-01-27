package com.cykj.util;

import com.cykj.servlet.BasicServlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Description: TODO
 * 解析servlet的工具类
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/18 15:25
 */
public class ServletUtils {
    private static HashMap<String, BasicServlet> servletHashMap = new HashMap<>();

    // 解析配置文件
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("./config/servlet.properties"));
            // 获取所有的key
            // 将key和对应的servlet路径存入hashmap
            Set<Object> keys = properties.keySet();
            Iterator<Object> keyIterator = keys.iterator();
            while (keyIterator.hasNext()){
                String key = (String) keyIterator.next();
                String servletPath = properties.getProperty(key);
                servletPath = "com.cykj.servlet." + servletPath;
                // 通过反射实例化servlet类对象，并由类对象去实例化各个servlet子类实例
                BasicServlet servlet = (BasicServlet) Class.forName(servletPath).newInstance();
                servletHashMap.put(key, servlet);
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static BasicServlet getServlet(String key){
        return servletHashMap.get(key);
    }
}
