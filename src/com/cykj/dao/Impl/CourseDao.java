package com.cykj.dao.Impl;

import com.cykj.annotation.DBTable;
import com.cykj.dao.BaseDao;
import com.cykj.dao.ICourseDao;
import com.cykj.net.StaticResourceHandler;
import com.cykj.pojo.Course;

import java.util.*;

/**
 * Description: TODO
 * 处理各种课程相关的数据库操作
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/21 17:17
 */
public class CourseDao extends BaseDao implements ICourseDao {
    private static CourseDao courseDao;
    private final String tableName;
    private final Class<Course> coursePojoClass;

    private CourseDao () {
        coursePojoClass = Course.class;
        tableName = coursePojoClass.getAnnotation(DBTable.class).value();
    }

    /**
     * Description: 获取主页的推荐课程列表
     * @param func 排序的依据
     * @param num 获取的列表课程个数
     * @return java.util.List<java.lang.Integer> 返回包含课程id的列表
     * @author Guguguy
     * @since 2024/2/21 21:48
     */
    @Override
    public List<Long> getRecommendCourse(String func, int num) {
        String sql = "select * from " + tableName;
        List<Object> params = new ArrayList<>();
        List<Object> courseList = query(sql, params, coursePojoClass);
        // 前面是课程id，后面是点赞数
        Map<Long, Integer> courseLikeMap = new IdentityHashMap<>();
        for (Object course : courseList) {
            Course c = (Course) course;
            sql = "select * from " + func + " where course_id = ? and state = 1 limit ?";
            params = new ArrayList<>();
            params.add(c.getCourseId());
            params.add(num);
            int likeNum = queryNum(sql, params);
            courseLikeMap.put(c.getCourseId(), likeNum);
        }

        List<Map.Entry<Long, Integer>> list = new ArrayList<>(courseLikeMap.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        List<Long> courseIdList = new ArrayList<>();
        for (Map.Entry<Long, Integer> integerIntegerEntry : list) {
            courseIdList.add(integerIntegerEntry.getKey());
        }
        return courseIdList;
     }

    @Override
    public Course getCourse(int courseId) {
        String sql = "select * from " + tableName + " where course_id = ? ";
        List<Object> params = new ArrayList<>();
        params.add(courseId);
        List<Object> dataReturn = query(sql, params, coursePojoClass);
        if (dataReturn.size() == 1) {
            return (Course) dataReturn.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Course> search(String searchWord, int page, int limitNum, String sortMode, String[] types, String[] tags) {
        int startNum = (page - 1) * limitNum;
        List<Object> params = new ArrayList<>();

        params.add(searchWord);

        String typesSql = " and course_type regexp '(";
        if (types.length == 1) {
            typesSql += types[0];
        } else {
            for (int i = 0; i <= types.length - 1; i++) {
                if (i == 0) {
                    typesSql += types[i];
                } else {
                    typesSql += "|" + types[i];
                }
            }
        }

        typesSql += ")'";


        String tagSql = " and course_tag regexp '(";


        for (int i = 0; i <= tags.length - 1; i++) {
            if (tags.length == 1 || i == 0) {
                String tag = tags[0];
                tagSql += StaticResourceHandler.escapeExprSpecialWord(tag);
            } else {
                String tag = tags[i];
                tagSql += "|" + StaticResourceHandler.escapeExprSpecialWord(tag);
            }
        }

        tagSql += ")'";

        String sql = "select * from " + tableName + " where course_name regexp ? " + typesSql + tagSql + " order by  (SELECT COUNT(" + sortMode + ".course_id) FROM " + sortMode + " WHERE " + sortMode + ".course_id = course.course_id and " + sortMode + ".state = 1) desc limit ?, ?";

        if(sortMode.equals("update-time")) {
            sql = "select * from " + tableName + " where course_name regexp ? " + typesSql + tagSql + " order by update_time desc limit ?, ?";
        }


        params.add(startNum);
        params.add(limitNum);

        List<Course> courses = new ArrayList<>();
        List<Object> dataReturn = query(sql, params, coursePojoClass);
        for (Object o : dataReturn) {
            courses.add((Course) o);
        }
        if (courses.isEmpty()) {
            return null;
        } else {
            return courses;
        }
    }

    @Override
    public int getSearchNum(String searchWord, String[] types, String[] tags) {
        List<Object> params = new ArrayList<>();
        params.add(searchWord);

        String typesSql = " course_type regexp '(";
        if (types.length == 1) {
            typesSql += types[0];
        } else {
            for (int i = 0; i <= types.length - 1; i++) {
                if (i == 0) {
                    typesSql += types[i];
                } else {
                    typesSql += "|" + types[i];
                }
            }
        }

        typesSql += ")'";


        String tagSql = " and course_tag regexp '(";


        for (int i = 0; i <= tags.length - 1; i++) {
            if (tags.length == 1 || i == 0) {
                String tag = tags[0];
                tagSql += StaticResourceHandler.escapeExprSpecialWord(tag);
            } else {
                String tag = tags[i];
                tagSql += "|" + StaticResourceHandler.escapeExprSpecialWord(tag);
            }
        }

        tagSql += ")'";


        String sql = "select * from " + tableName + " where " + typesSql + tagSql + " and course_name regexp ?";
        return queryNum(sql, params);
    }

    @Override
    public Set<String> getSearchResultTags(String searchWord) {
        String sql = "select * from " + tableName + " where course_name regexp ?";
        List<Object> params = new ArrayList<>();
        params.add(searchWord);
        List<Object> dataReturn = query(sql, params, coursePojoClass);
        Set<String> tagList = new HashSet<>();
        for (Object o : dataReturn) {
            Course course = ((Course) o);
            String[] tags = course.getCourseTag().split(",");
            tagList.addAll(Arrays.asList(tags));
        }
        return tagList;
    }

    public synchronized static CourseDao getInstance(){// 这里使用同步锁就是为了解决线程安全问题
        if (courseDao == null){
            courseDao = new CourseDao();
        }// 有加if的是懒汉模式，即在需要的时候再new一个对象出来
        // 但是这里有高并发的问题，如果同时多个线程调用此方法可能会出现线程安全问题
        return courseDao;
    }
}
