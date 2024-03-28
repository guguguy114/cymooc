package com.cykj.dao;

/**
 * Description:
 * 负责处理点赞的数据库操作的Dao层
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 23:43
 */
public interface ILikeDao {
    /**
     * Description: 改变当前课程喜欢状态，如果当前是喜欢的，处理后就是不喜欢，反之亦然
     * @param uid 用户id
     * @param courseId 课程id
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:43
     */
    boolean changeCurrentCourseLikeState(int uid, int courseId);
    /**
     * Description: 获取当前喜欢状态
     * @param uid 用户id
     * @param courseId 课程id
     * @return int
     * @author Guguguy
     * @since 2024/3/28 15:45
     */
    int getCurrentCourseLikeState(int uid, int courseId);
    /**
     * Description: 获取课程的喜欢总数
     * @param courseId 课程id
     * @return int
     * @author Guguguy
     * @since 2024/3/28 15:46
     */
    int getCourseLikeNum(int courseId);
}
