package com.cykj.dao;

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
}
