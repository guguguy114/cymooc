package com.cykj.dao.Impl;

import com.cykj.annotation.DBTable;
import com.cykj.dao.BaseDao;
import com.cykj.dao.IChapterDao;
import com.cykj.pojo.Chapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 实例化的处理章节数据库操作的Dao
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 16:55
 */
public class ChapterDao extends BaseDao implements IChapterDao {
    private static ChapterDao chapterDao;
    private final String tableName;
    private final Class<Chapter> chapterClass;

    private ChapterDao() {
        chapterClass = Chapter.class;
        tableName = chapterClass.getAnnotation(DBTable.class).value();
    }

    /**
     * Description: 获取某个课程的全部章节
     *
     * @param courseId 查询的课程id
     * @return java.util.List<java.lang.Character> 返回章节列表
     * @author Guguguy
     * @since 2024/2/23 16:50
     */
    @Override
    public List<Chapter> getCourseChapters(int courseId) {
        String sql = "select * from " + tableName + " where course_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(courseId);
        List<Object> dataReturn = query(sql, params, chapterClass);
        List<Chapter> chapters = new ArrayList<>();
        if (!dataReturn.isEmpty()){
            for (Object o : dataReturn) {
                chapters.add((Chapter) o);
            }
        } else {
            return null;
        }
        return chapters;
    }

    @Override
    public Chapter getCourseChapter(int chapterId) {
        String sql = "select * from " + tableName + " where chapter_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(chapterId);
        List<Object> dataReturn = query(sql, params, chapterClass);
        if (dataReturn.size() == 1) {
            return (Chapter) dataReturn.get(0);
        } else {
            return null;
        }
    }

    public synchronized static ChapterDao getInstance(){// 这里使用同步锁就是为了解决线程安全问题
        if (chapterDao == null){
            chapterDao = new ChapterDao();
        }// 有加if的是懒汉模式，即在需要的时候再new一个对象出来
        // 但是这里有高并发的问题，如果同时多个线程调用此方法可能会出现线程安全问题
        return chapterDao;
    }
}
