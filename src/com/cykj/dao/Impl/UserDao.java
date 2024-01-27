/**
 * 这个包专门用来放实现IUserDao的实现类
 */
package com.cykj.dao.Impl;

import com.cykj.annotation.DBTable;
import com.cykj.dao.BaseDao;
import com.cykj.dao.IUserDao;
import com.cykj.pojo.User;
import com.cykj.util.DBConnectPool;
import com.cykj.util.DBConnectUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 专门用于操作用户表
 * @author Guguguy
 * @version 1.0
 * @since 2023/11/29 17:46
 */
public class UserDao extends BaseDao implements IUserDao {
    // 单例设计模式，全局只使用一个userDao对象，不提供公有构造方法，仅在内部创建一个静态对象，并创建一个公有方法供外部调用
    // 这里是饿汉模式，在类加载时就创建了一个Dao对象
    // public static UserDao userDao = new UserDao();
    // 这里是懒汉模式
    public static UserDao userDao;
    private String tableName;
    private Class<User> userPojoClass;
    private UserDao(){
        userPojoClass = User.class;
        tableName = userPojoClass.getAnnotation(DBTable.class).value();
    }
    /**
     * Description:
     * 登录方法返回用户对象
     * @param acc 输入账号
     * @param pwd 输入密码
     * @return com.cykj.pojo.UserPojo
     * @author Guguguy
     * @since 2023/12/24 21:19
     */
    @Override
    public User doLogin(String acc, String pwd){
        // 创建账户polo对象
        // 创建数据库连接等对象，从数据库抓取数据
        // 这里采用预编译方式进行执行sql语句，防止sql语句注入
        String sql = "select * from " + tableName + " where acc = ? and pwd = ?";
        List<Object> params = new ArrayList<>();
        List<Object> dataReturned;
        params.add(acc);
        params.add(pwd);
        dataReturned = query(sql, params, User.class);
        if (!dataReturned.isEmpty()) {
            return (User) dataReturned.get(0);
        } else {
            return null;
        }
    }

    /**
     * Description:
     * 充值方法
     * @param userID 进行充值的账户
     * @param money 需要充值的钱数
     * @return boolean 是否操作成功
     * @author Guguguy
     * @since 2023/11/29 21:16
     */
    @Override
    public boolean charge(int userID, int money) {
        // ServerUtils.printOut("charging!!");
        Connection conn = DBConnectPool.getConn();
        if (conn == null){
            return false;
        }
        String sql = "update " + tableName + " set money = money + ? where id = ?";
        PreparedStatement prep = null;
        try {
            prep = conn.prepareStatement(sql);
            prep.setInt(1, money);
            prep.setInt(2, userID);
            // ServerUtils.printOut(String.valueOf(prep.executeUpdate()));
            return prep.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectPool.giveBackConn(conn);
            DBConnectUtils.closeRes(prep, null);
        }


    }

    /**
     * Description:
     * 查询全部用户信息方法
     * @return java.util.List<Object>
     * @author Guguguy
     * @since 2023/12/24 15:52
     */
    @Override
    public List<Object> getUsers(int start, int pageSize) {
        // 创建账户polo对象
        List<Object> users;
        List<Object> params = new ArrayList<>();
        params.add(start);
        params.add(pageSize);
        String sql = "select * from " + tableName + " limit ? , ?";
        users = query(sql, params, User.class);
        return users;
    }

    @Override
    public int getUsersCount() {
        int res;
        // 创建数据库连接等对象，从数据库抓取数据
        Connection connection = DBConnectPool.getConn();
        if (connection == null){
            return 0;
        }
        PreparedStatement preparedStatement = null;
        // 这里采用预编译方式进行执行sql语句，防止sql语句注入
        String sql = "select count(*) as count from " + tableName;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                res = resultSet.getInt("count");
            }else {
                res = 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectUtils.closeRes(preparedStatement, resultSet);
            DBConnectPool.giveBackConn(connection);
        }
        return res;
    }

    public int register(){
        return 0;
    }

    public synchronized static UserDao getInstance(){// 这里使用同步锁就是为了解决线程安全问题
        if (userDao == null){
            userDao = new UserDao();
        }// 有加if的是懒汉模式，即在需要的时候再new一个对象出来
        // 但是这里有高并发的问题，如果同时多个线程调用此方法可能会出现线程安全问题
        return userDao;
    }
}
