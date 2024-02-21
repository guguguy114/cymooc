package com.cykj.dao.Impl;

import com.cykj.annotation.DBTable;
import com.cykj.dao.BaseDao;
import com.cykj.dao.ICourseDao;
import com.cykj.pojo.Course;

import java.util.*;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/21 17:17
 */
public class CourseDao extends BaseDao implements ICourseDao {
    public static CourseDao courseDao;
    private final String tableName;
    private final Class<Course> coursePojoClass;

    private CourseDao () {
        coursePojoClass = Course.class;
        tableName = coursePojoClass.getAnnotation(DBTable.class).value();
    }
    @Override
    public List<Integer> getRecommendCourse(String func, int num) {
        String sql = "select * from " + tableName;
        List<Object> params = new ArrayList<>();
        List<Object> courseList = query(sql, params, coursePojoClass);
        // 前面是课程id，后面是点赞数
        Map<Integer, Integer> courseLikeMap = new IdentityHashMap<>();
        for (Object course : courseList) {
            Course c = (Course) course;
            sql = "select * from " + func + " where course_id = ? and current_like_state = 1";
            params = new ArrayList<>();
            params.add(c.getCourseId());
            int likeNum = queryNum(sql, params);
            courseLikeMap.put((int)c.getCourseId(), likeNum);
        }

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(courseLikeMap.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        List<Integer> courseIdList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> integerIntegerEntry : list) {
            courseIdList.add(integerIntegerEntry.getKey());
        }
        return courseIdList;
     }

    @Override
    public Course getCourse(int courseId) {
        return null;
    }

    public synchronized static CourseDao getInstance(){// 这里使用同步锁就是为了解决线程安全问题
        if (courseDao == null){
            courseDao = new CourseDao();
        }// 有加if的是懒汉模式，即在需要的时候再new一个对象出来
        // 但是这里有高并发的问题，如果同时多个线程调用此方法可能会出现线程安全问题
        return courseDao;
    }
}
