package com.cykj.service.impl;

import com.cykj.dao.ICommentDao;
import com.cykj.dao.Impl.CommentDao;
import com.cykj.net.ResponseDto;
import com.cykj.pojo.Comment;
import com.cykj.service.CommentService;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/1 20:27
 */
public class CommentServiceImpl implements CommentService {
    @Override
    public ResponseDto getCourseComment(int courseId, int limitNum, int page) {
        ICommentDao commentDao = CommentDao.getInstance();
        List<Comment> commentList = commentDao.getCourseComment(courseId, limitNum, page);
        ResponseDto dto;
        if (commentList != null) {
            dto = new ResponseDto(1, "success get comment", commentList);
        } else {
            dto = new ResponseDto(0, "failed to get comment", null);
        }
        return dto;
    }

    @Override
    public ResponseDto getCourseTotalCommentNum(int courseId) {
        ICommentDao commentDao = CommentDao.getInstance();
        int num = commentDao.getCourseTotalCommentNum(courseId);
        return new ResponseDto(1, "success get comment num", num);
    }
}
