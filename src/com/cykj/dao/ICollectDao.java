package com.cykj.dao;

import com.cykj.pojo.Course;

import java.util.List;

/**
 * Description:
 * 处理收藏的数据库操作的Dao的接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 20:30
 */
public interface ICollectDao {
    /**
     * Description: 获取当前用户的该课程收藏状态
     * @param uid 用户id
     * @param courseId 课程id
     * @return int 一个状态码
     * @author Guguguy
     * @since 2024/3/28 15:31
     */
    int getCurrentCourseCollectState(int uid, int courseId);
    /**
     * Description: 转换当前收藏状态
     * @param uid 用户id
     * @param courseId 课程id
     * @return boolean true表示更改成功
     * @author Guguguy
     * @since 2024/3/28 15:31
     */
    boolean changeCurrentCourseCollectState(int uid, int courseId);
    /**
     * Description: 获取该课程的总收藏数
     * @param courseId 课程id
     * @return int 总收藏数
     * @author Guguguy
     * @since 2024/3/28 15:32
     */
    int getCourseCollectNum(int courseId);
    /**
     * Description: 获取用户的全部收藏课程
     * @param uid 用户id
     * @return java.util.List<com.cykj.pojo.Course> 返回收藏pojo集合
     * @author Guguguy
     * @since 2024/3/28 15:33
     */
    List<Course> getUserCollections(int uid);
}
