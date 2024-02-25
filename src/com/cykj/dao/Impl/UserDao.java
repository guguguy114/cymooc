/**
 * 这个包专门用来放实现IUserDao的实现类
 */
package com.cykj.dao.Impl;

import com.cykj.annotation.DBTable;
import com.cykj.dao.BaseDao;
import com.cykj.dao.IUserDao;
import com.cykj.pojo.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static UserDao userDao;
    private final String tableName;
    private final Class<User> userPojoClass;
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
        String sql = "select * from " + tableName + " where account = ? and password = ?";
        List<Object> params = new ArrayList<>();
        List<Object> dataReturned;
        params.add(acc);
        // 生成密码md5
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pwd.getBytes());
            pwd = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        params.add(pwd);
        dataReturned = query(sql, params, User.class);
        if (!dataReturned.isEmpty()) {
            return (User) dataReturned.get(0);
        } else {
            return null;
        }
    }

    @Override
    public int doRegister(String acc, String pwd) {
        String sql = "select * from " + tableName + " where account = ?";
        List<Object> params = new ArrayList<>();
        List<Object> dataReturned;
        params.add(acc);
        dataReturned = query(sql, params, User.class);
        if (dataReturned.isEmpty()) {
            sql = "insert into " + tableName + " (account, password, nickname) values (?, ?, ?)";
            pwd = getStringMD5(pwd);
            Random r = new Random();
            String nickname = "user_" + getStringMD5(String.valueOf(r.nextInt(100000)));
            params.add(pwd);
            params.add(nickname);
            boolean code = insert(sql, params);
            if (!code) {
                return 0;
            }
        } else {
            return 2;
        }
        return 1;
    }

    /**
     * Description:
     * 充值方法
     *
     * @param acc 进行充值的账户
     * @param chargeNum  需要充值的钱数
     * @return boolean 是否操作成功
     * @author Guguguy
     * @since 2023/11/29 21:16
     */
    @Override
    public boolean charge(String acc, int chargeNum) {
        String sql = "update " + tableName + " set balance = balance + ? where account = ?";
        List<Object> params = new ArrayList<>();
        int updateNum;
        params.add(chargeNum);
        params.add(acc);
        updateNum = update(sql, params);
        return updateNum == 1;
    }

    /**
     * Description:
     * 查询全部用户信息方法
     *
     * @return java.util.List<Object>
     * @author Guguguy
     * @since 2023/12/24 15:52
     */
    @Override
    public User getUserInfo(String acc) {
        String sql = "select * from " + tableName + " where account = ?";
        List<Object> params = new ArrayList<>();
        List<Object> dataReturned;
        params.add(acc);
        dataReturned = query(sql, params, User.class);
        if (!dataReturned.isEmpty()) {
            return (User) dataReturned.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean changeInfo(String type, String info, String acc) {
        String sql = "update " + tableName + " set " + type + " = ? where account = ?";
        List<Object> params = new ArrayList<>();
        params.add(info);
        params.add(acc);
        int changeNum = update(sql, params);
        return changeNum == 1;
    }

    public synchronized static UserDao getInstance(){// 这里使用同步锁就是为了解决线程安全问题
        if (userDao == null){
            userDao = new UserDao();
        }// 有加if的是懒汉模式，即在需要的时候再new一个对象出来
        // 但是这里有高并发的问题，如果同时多个线程调用此方法可能会出现线程安全问题
        return userDao;
    }
}
