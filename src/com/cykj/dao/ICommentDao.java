package com.cykj.dao;

import com.cykj.pojo.Comment;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/1 20:27
 */
public interface ICommentDao {
    List<Comment> getCourseComment(int courseId, int limitNum, int page);
    int getCourseTotalCommentNum(int courseId);
}
