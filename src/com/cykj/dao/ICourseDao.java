package com.cykj.dao;

import com.cykj.pojo.Course;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/21 17:17
 */
public interface ICourseDao {
    List<Long> getRecommendCourse(String func, int num);
    Course getCourse(int courseId);
}
