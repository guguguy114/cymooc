package com.cykj.dao;

/**
 * Description:
 * 负责处理点赞的数据库操作的Dao层
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 23:43
 */
public interface ILikeDao {
    boolean changeCurrentCourseLikeState(int uid, int courseId);
    int getCurrentCourseLikeState(int uid, int courseId);
    int getCourseLikeNum(int courseId);
}
