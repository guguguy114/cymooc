package com.cykj.dao;

import com.cykj.pojo.User;

import java.math.BigDecimal;

/**
 * Description:
 * 接口是用于统一代码规范的工具，主要用于规定某一个功能的方法返回值和参数等标准
 * 以便在后续开发中，不同团队和不同个人间能够使用同样的标准写代码，减少代码的关联性，易于代码后期的重构和维护
 * @author Guguguy
 * @version 1.0
 * @since 2023/11/29 18:32
 */
public interface IUserDao {
    /**
     * Description: 登录
     * @param acc 账号
     * @param pwd 密码
     * @return com.cykj.pojo.User
     * @author Guguguy
     * @since 2024/3/28 15:49
     */
    User doLogin(String acc, String pwd);
    /**
     * Description: 注册
     * @param acc 账号
     * @param pwd 密码
     * @return int
     * @author Guguguy
     * @since 2024/3/28 15:49
     */
    int doRegister(String acc, String pwd);
    /**
     * Description: 充值
     * @param acc 账号
     * @param moneyToCharge 充值金额
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:49
     */
    boolean charge(String acc, int moneyToCharge);
    /**
     * Description: 获取用户
     * @param uid 用户id
     * @return com.cykj.pojo.User
     * @author Guguguy
     * @since 2024/3/28 15:49
     */
    User getUserInfo(int uid);
    /**
     * Description: 修改用户信息
     * @param type 需要修改的信息的字段，如：密码，账号等
     * @param info 修改信息内容
     * @param acc 账号
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:50
     */
    boolean changeInfo(String type, String info, String acc);
    /**
     * Description: 设置余额
     * @param uid 用户id
     * @param newBalance 新的余额
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:51
     */
    boolean setBalance(int uid, BigDecimal newBalance);
    /**
     * Description: 上传头像
     * @param uid 用户id
     * @param imageData 头像base64数据
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:51
     */
    boolean uploadFaceImage(int uid, String imageData);
}
