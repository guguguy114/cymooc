package com.cykj.dao.Impl;

import com.cykj.annotation.DBTable;
import com.cykj.dao.BaseDao;
import com.cykj.dao.ILikeDao;
import com.cykj.pojo.LikeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 实例化的负责处理点赞的数据库操作的Dao
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 23:46
 */
public class LikeDao extends BaseDao implements ILikeDao {
    private static LikeDao likeDao;
    private final String tableName;
    private final Class<LikeList> likePojoClass;

    public LikeDao() {
        this.likePojoClass = LikeList.class;
        this.tableName = likePojoClass.getAnnotation(DBTable.class).value();
    }

    @Override
    public boolean changeCurrentCourseLikeState(int uid, int courseId) {
        String sql = "select * from " + tableName + " where uid = ? and course_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        params.add(courseId);
        List<Object> dataReturn;
        dataReturn = query(sql, params, likePojoClass);
        if (dataReturn.size() == 1) {
            LikeList likeList = (LikeList) dataReturn.get(0);
            if (likeList.getState()) {
                sql = "update " + tableName + " set state = 0 where uid = ? and course_id = ?";
            } else {
                sql = "update " + tableName + " set state = 1 where uid = ? and course_id = ?";
            }
            int changeNum = update(sql, params);
            return changeNum == 1;
        } else {
            return false;
        }
    }

    @Override
    public int getCurrentCourseLikeState(int uid, int courseId) {
        String sql = "select * from " + tableName + " where uid = ? and course_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        params.add(courseId);
        List<Object> dataReturn = query(sql, params, likePojoClass);
        if (dataReturn.isEmpty()) {
            sql = "insert into " + tableName + " (uid, course_id) values (?, ?)";
            update(sql, params);
            getCurrentCourseLikeState(uid, courseId);
        }
        LikeList likeList = (LikeList) dataReturn.get(0);
        if (dataReturn.size() == 1) {
            if (likeList.getState()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 2;
        }
    }

    @Override
    public int getCourseLikeNum(int courseId) {
        String sql = "select * from " + tableName + " where course_id = ? and state = 1";
        List<Object> params = new ArrayList<>();
        params.add(courseId);
        return queryNum(sql, params);
    }

    public synchronized static LikeDao getInstance(){// 这里使用同步锁就是为了解决线程安全问题
        if (likeDao == null){
            likeDao = new LikeDao();
        }// 有加if的是懒汉模式，即在需要的时候再new一个对象出来
        // 但是这里有高并发的问题，如果同时多个线程调用此方法可能会出现线程安全问题
        return likeDao;
    }
}
