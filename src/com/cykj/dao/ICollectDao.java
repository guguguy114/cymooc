package com.cykj.dao;

/**
 * Description:
 * 处理收藏的数据库操作的Dao的接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 20:30
 */
public interface ICollectDao {
    int getCurrentCourseCollectState(int uid, int courseId);
    boolean changeCurrentCourseCollectState(int uid, int courseId);
    int getCourseCollectNum(int courseId);
}
