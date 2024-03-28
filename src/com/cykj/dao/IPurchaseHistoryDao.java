package com.cykj.dao;

import com.cykj.pojo.CoursePurchaseHistory;

import java.util.List;

/**
 * Description:
 * 购买记录dao层接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 23:12
 */
public interface IPurchaseHistoryDao {
    /**
     * Description: 获取当前课程购买状态
     * @param uid 用户id
     * @param courseId 课程id
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:46
     */
    boolean getPurchaseState(int uid, int courseId);
    /**
     * Description: 购买课程
     * @param uid 用户id
     * @param courseId 课程id
     * @return int
     * @author Guguguy
     * @since 2024/3/28 15:47
     */
    int purchaseCourse(int uid, int courseId);
    /**
     * Description: 获取购买历史
     * @param uid 用户id
     * @param limitNum 每页限制数量
     * @param page 需要获取的页面
     * @return java.util.List<com.cykj.pojo.CoursePurchaseHistory>
     * @author Guguguy
     * @since 2024/3/28 15:47
     */
    List<CoursePurchaseHistory> getPurchaseHistories(int uid, int limitNum, int page);
    /**
     * Description: 获取购买总数
     * @param uid 用户id
     * @return int
     * @author Guguguy
     * @since 2024/3/28 15:48
     */
    int getPurchaseHistoryNum(int uid);
}
