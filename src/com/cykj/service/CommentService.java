package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description:
 * 评论service层接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/1 20:26
 */
public interface CommentService {
    ResponseDto getCourseComment(int courseId, int limitNum, int page);
    ResponseDto getCourseTotalCommentNum(int courseId);
    ResponseDto submitComment(int courseId, int uid, String comment);
    ResponseDto deleteComment(int courseId, int uid, int commentId);
}
