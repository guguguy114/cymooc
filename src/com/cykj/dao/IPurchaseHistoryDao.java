package com.cykj.dao;

import com.cykj.pojo.CoursePurchaseHistory;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 23:12
 */
public interface IPurchaseHistoryDao {
    boolean getPurchaseState(int uid, int courseId);
    int purchaseCourse(int uid, int courseId);
    List<CoursePurchaseHistory> getPurchaseHistories(int uid, int limitNum, int page);
    int getPurchaseHistoryNum(int uid);
}
