package com.cykj.service.impl;

import com.cykj.dao.ICourseDao;
import com.cykj.dao.Impl.CourseDao;
import com.cykj.net.ResponseDto;
import com.cykj.pojo.Course;
import com.cykj.service.CourseService;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/21 17:30
 */
public class CourseServiceImpl implements CourseService {
    @Override
    public ResponseDto getRecommendCourse(String func, int num) {
        ICourseDao courseDao = CourseDao.getInstance();
        ResponseDto responseDto;
        List<Integer> courseIdList = courseDao.getRecommendCourse(func, num);
        if (courseIdList != null) {
            responseDto = new ResponseDto(1, "get recommend video successfully!", courseIdList);
        } else {
            responseDto = new ResponseDto(0, "fail to get recommend video!", null);
        }
        return responseDto;
    }

    @Override
    public ResponseDto getCourse(int id) {
        ICourseDao courseDao = CourseDao.getInstance();
        ResponseDto responseDto;
        Course course = courseDao.getCourse(id);
        if (course != null) {
            responseDto = new ResponseDto(1, "get course successfully!", course);
        } else {
            responseDto = new ResponseDto(0, "fail to get course", null);
        }
        return responseDto;
    }

    @Override
    public ResponseDto getCourseChapters(int id) {
        ICourseDao courseDao = CourseDao.getInstance();
        ResponseDto responseDto;
        List<Character> characters = courseDao.getCourseChapters(id);
        if (!characters.isEmpty()) {
            responseDto = new ResponseDto(1, "get chapters successfully!", characters);
        } else {
            responseDto = new ResponseDto(0, "fail to get chapters!", null);
        }
        return responseDto;
    }
}
