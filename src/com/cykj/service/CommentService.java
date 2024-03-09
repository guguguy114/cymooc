package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/1 20:26
 */
public interface CommentService {
    ResponseDto getCourseComment(int courseId, int limitNum, int page);
    ResponseDto getCourseTotalCommentNum(int courseId);
}
