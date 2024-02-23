package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/21 17:30
 */
public interface CourseService {
    ResponseDto getRecommendCourse(String func, int num);
    ResponseDto getCourse(int id);
    ResponseDto getCourseChapters(int id);
}
