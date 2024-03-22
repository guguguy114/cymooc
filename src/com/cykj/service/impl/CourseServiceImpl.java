package com.cykj.service.impl;

import com.cykj.dao.ICourseDao;
import com.cykj.dao.Impl.CourseDao;
import com.cykj.net.ResponseDto;
import com.cykj.pojo.Course;
import com.cykj.service.CourseService;

import java.util.List;
import java.util.Set;

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
        List<Long> courseIdList = courseDao.getRecommendCourse(func, num);
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
    public ResponseDto search(String searchWord, int page, int limitNum, String sortMode, String[] types, String[] tags) {
        ICourseDao courseDao = CourseDao.getInstance();
        List<Course> courses = courseDao.search(searchWord, page, limitNum, sortMode, types, tags);
        ResponseDto dto;
        if (!(courses == null)) {
            dto = new ResponseDto(1, "search successfully", courses);
        } else {
            dto = new ResponseDto(0, "no search result", null);
        }
        return dto;
    }

    @Override
    public ResponseDto getSearchNum(String searchWord, String[] types, String[] tags) {
        ICourseDao courseDao = CourseDao.getInstance();
        int num = courseDao.getSearchNum(searchWord, types, tags);
        ResponseDto dto;
        dto = new ResponseDto(1, "get search num successfully", num);
        return dto;
    }

    @Override
    public ResponseDto getSearchResultTags(String searchWord) {
        ICourseDao courseDao = CourseDao.getInstance();
        Set<String> tags = courseDao.getSearchResultTags(searchWord);
        ResponseDto dto;
        if (tags.isEmpty()) {
            dto = new ResponseDto(1, "no tags", null);
        } else {
            dto = new ResponseDto(1, "successfully get course tags", tags);
        }
        return dto;
    }
}
