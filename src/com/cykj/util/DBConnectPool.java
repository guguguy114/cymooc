package com.cykj.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Description:
 * 这里是连接池
 * @author Guguguy
 * @version 1.0
 * @since 2023/11/27 18:43
 */
public class DBConnectPool {
    private static final int size = 10;// MySQL 默认支持99个连接，可以通过配置文件改变
//    private ArrayList<Connection> pool = new ArrayList<>();
    private static final LinkedList<Connection> pool = new LinkedList<>();

    static {
        for (int i = 0; i < size; i++) {
            Connection conn = DBConnectUtils.getConn();
            pool.add(conn);
        }
    }

    /**
     * Description:
     * 取出连接，将连接从集合中取出并删除，这里需要同步锁，否则会出现大量报错
     * @return java.sql.Connection
     * @author Guguguy
     * @since 2023/11/28 21:27
     */
    public synchronized static Connection getConn() {
        if (!pool.isEmpty()){
            Connection conn = pool.pop();// 从最后一个取出
            // 从集合中去除并删除
            if (conn == null) {
                ServerConsoleUtils.printOut("connection run out! a new connection will be given", ServerConsoleUtils.GREEN);
                return DBConnectUtils.getConn();
            }
            // return pool.poll();// 从第一个取出
            ServerConsoleUtils.printOut("A connection is out! " + conn, ServerConsoleUtils.GREEN);
            return conn;
        }else {
            // 新创建的要写在这个地方才对
            return DBConnectUtils.getConn();
        }
    }


    /**
     * Description:
     * 归还连接，将连接重新添加进集合里
     * @param conn 需要归还回来的连接
     * @author Guguguy
     * @since 2023/11/28 21:27
     */
    public synchronized static void giveBackConn(Connection conn) {
        if (pool.size() < size){
            pool.add(conn);
            ServerConsoleUtils.printOut("A connection is back! " + conn, ServerConsoleUtils.GREEN);
        }else {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ServerConsoleUtils.printOut("A connection is back, but not into list again! ", ServerConsoleUtils.GREEN);
        }
        ServerConsoleUtils.printOut("Now we have " + pool.size() + " connections!");
    }
}
