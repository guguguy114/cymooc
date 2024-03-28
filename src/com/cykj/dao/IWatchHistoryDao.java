package com.cykj.dao;

import com.cykj.pojo.WatchHistory;

import java.util.List;

/**
 * Description:
 * 观看记录dao层
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/12 21:50
 */
public interface IWatchHistoryDao {
    /**
     * Description: 添加观看记录
     * @param uid 用户id
     * @param courseId 课程id
     * @param chapterId 章节id
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:52
     */
    boolean addWatchHistory(int uid, int courseId, int chapterId);
    /**
     * Description: 获取观看记录总数
     * @param uid 用户id
     * @return int
     * @author Guguguy
     * @since 2024/3/28 15:52
     */
    int getWatchHistoryNum(int uid);
    /**
     * Description: 获取用户的观看记录对象集合
     * @param uid 用户id
     * @param limit 每页限制数量
     * @param page 当前页面数
     * @return java.util.List<com.cykj.pojo.WatchHistory>
     * @author Guguguy
     * @since 2024/3/28 15:53
     */
    List<WatchHistory> getUserWatchHistories(int uid, int limit, int page);
    /**
     * Description: 获取最新观看记录
     * @param uid 用户id
     * @return com.cykj.pojo.WatchHistory
     * @author Guguguy
     * @since 2024/3/28 15:55
     */
    WatchHistory getNearestWatchHistory(int uid);
    /**
     * Description: 获取课程播放数
     * @param courseId 课程id
     * @return int
     * @author Guguguy
     * @since 2024/3/28 15:55
     */
    int getCoursePlayNum(int courseId);
}
