package com.cykj.dao;

import com.cykj.pojo.Comment;

import java.util.List;

/**
 * Description:
 * 评论dao层接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/1 20:27
 */
public interface ICommentDao {
    /**
     * Description: 获取课程全部评论
     * @param courseId 课程id
     * @param limitNum 评论区评论限制数量
     * @param page 评论区页数
     * @return java.util.List<com.cykj.pojo.Comment> 返回评论集合
     * @author Guguguy
     * @since 2024/3/28 15:34
     */
    List<Comment> getCourseComment(int courseId, int limitNum, int page);
    /**
     * Description: 获取课程的全部评论数
     * @param courseId 课程id
     * @return int 评论总数
     * @author Guguguy
     * @since 2024/3/28 15:35
     */
    int getCourseTotalCommentNum(int courseId);
    /**
     * Description: 发布评论
     * @param courseId 课程id
     * @param uid 用户id
     * @param comment 评论内容
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:38
     */
    boolean submitComment(int courseId, int uid, String comment);
    /**
     * Description: 删除评论
     * @param courseId 课程id
     * @param uid 用户id
     * @param commentId 评论id
     * @return boolean
     * @author Guguguy
     * @since 2024/3/28 15:39
     */
    boolean deleteComment(int courseId, int uid, int commentId);
}
