package com.cykj.dao;

import com.cykj.pojo.User;

import java.util.List;

/**
 * Description: TODO
 * 接口是用于统一代码规范的工具，主要用于规定某一个功能的方法返回值和参数等标准
 * 以便在后续开发中，不同团队和不同个人间能够使用同样的标准写代码，减少代码的关联性，易于代码后期的重构和维护
 * @author Guguguy
 * @version 1.0
 * @since 2023/11/29 18:32
 */
public interface IUserDao {
    User doLogin(String acc, String pwd);
    boolean charge(int uid, int moneyToCharge);
    List<Object> getUsers(int start, int pageSize);
    int getUsersCount();
}