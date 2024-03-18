package com.cykj.dao;

import com.cykj.dao.Impl.CourseDao;
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
    List<Course> search(String searchWord, int page, int limitNum, String sortMode);
    int getSearchNum(String searchWord);
}
