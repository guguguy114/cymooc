package com.cykj.util;

import java.sql.*;
import java.util.HashMap;

/**
 * Description:
 * 连接工具类，获取数据库连接
 * @author Guguguy
 * @version 1.0
 * @since 2023/11/27 18:18
 */
public class DBConnectUtils {
    /**
     * Description:
     * 用于获取数据库连接
     * @return java.sql.Connection 返回数据；连接
     * @author Guguguy
     * @since 2023/11/27 18:34
     */
    static Connection getConn(){
        Connection connection;
        try {
            // 为了软件的维护，我们需要建立配置文件来管理不同环境下的不同系统环境，比如数据库在不同的部署平台上密码和路径可能会有所差异
            // 因此我们需要使用配置文件，首先加载配置文件
            HashMap<String, String> prop = DBPropertiesUtils.getPropertiesMap();
            // 加载驱动的实质是加载静态类文件执行内部的静态方法
            Class.forName(prop.get("driver")); // 反射
            String url = prop.get("url");
            connection = DriverManager.getConnection(url, prop.get("account"), prop.get("password"));
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    /**
     * Description:
     * 用于释放数据库资源
     * @param preparedStatement 传入预编译对象
     * @param resultSet 传入结果集
     * @author Guguguy
     * @since 2023/11/27 18:34
     */
    public static void closeRes(PreparedStatement preparedStatement, ResultSet resultSet){
        // 这里使用两个try-catch是为了使在一个抛异常时另一个资源也能顺利释放
        try {
            if (preparedStatement != null){
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
