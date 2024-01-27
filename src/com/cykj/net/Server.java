package com.cykj.net;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/28 0:10
 */
public class Server {
    Server() {
        try {
            Class.forName("com.cykj.util.DBPropertiesUtils");
            Class.forName("com.cykj.util.DBConnectPool");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        new Tomcat();
    }
}
