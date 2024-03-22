package com.cykj.dao.Impl;

import com.cykj.annotation.DBTable;
import com.cykj.dao.BaseDao;
import com.cykj.dao.IWatchHistoryDao;
import com.cykj.pojo.Comment;
import com.cykj.pojo.WatchHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/12 21:51
 */
public class WatchHistoryDao extends BaseDao implements IWatchHistoryDao {
    private static WatchHistoryDao watchHistoryDao;
    private final String tableName;
    private final Class<WatchHistory> watchHistoryPojoClass;

    private WatchHistoryDao() {
        watchHistoryPojoClass = WatchHistory.class;
        tableName = watchHistoryPojoClass.getAnnotation(DBTable.class).value();
    }

    @Override
    public boolean addWatchHistory(int uid, int courseId, int chapterId) {
        WatchHistory history = getNearestWatchHistory(uid);
        if (history.getChapterId() == chapterId) {
            return false;
        }
        String sql = "insert into " + tableName + " (uid, course_id, chapter_id) values (?, ?, ?)";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        params.add(courseId);
        params.add(chapterId);
        int code = update(sql, params);
        return code == 1;
    }

    @Override
    public int getWatchHistoryNum(int uid) {
        String sql = "select * from " + tableName + " where uid = ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        return queryNum(sql, params);
    }

    @Override
    public List<WatchHistory> getUserWatchHistories(int uid, int limit, int page) {
        int startNum = (page - 1) * limit;
        String sql = "select * from " + tableName + " where uid = ? order by watch_time desc limit ?, ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        params.add(startNum);
        params.add(limit);
        List<Object> dataReturn = query(sql, params, watchHistoryPojoClass);
        List<WatchHistory> historyList = new ArrayList<>();
        for (Object o : dataReturn) {
            historyList.add((WatchHistory) o);
        }
        if (historyList.isEmpty()) {
            return null;
        } else {
            return historyList;
        }
    }

    @Override
    public WatchHistory getNearestWatchHistory(int uid) {
        String sql = "SELECT * FROM " + tableName + " WHERE uid = ? ORDER BY watch_time DESC LIMIT 1";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        List<Object> dataReturn = query(sql, params, watchHistoryPojoClass);
        return (WatchHistory) dataReturn.get(0);
    }

    @Override
    public int getCoursePlayNum(int courseId) {
        String sql = "select * from " + tableName + " where course_id = ? ";
        List<Object> params = new ArrayList<>();
        params.add(courseId);
        return queryNum(sql, params);
    }

    public synchronized static WatchHistoryDao getInstance() {
        if (watchHistoryDao == null) {
            watchHistoryDao = new WatchHistoryDao();
        }
        return watchHistoryDao;
    }
}
