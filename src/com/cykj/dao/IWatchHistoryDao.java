package com.cykj.dao;

import com.cykj.pojo.WatchHistory;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/12 21:50
 */
public interface IWatchHistoryDao {
    boolean addWatchHistory(int uid, int courseId, int chapterId);
    int getWatchHistoryNum(int uid);
    List<WatchHistory> getUserWatchHistories(int uid, int limit, int page);
    WatchHistory getNearestWatchHistory(int uid);
    int getCoursePlayNum(int courseId);
}
