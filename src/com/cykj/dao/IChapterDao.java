package com.cykj.dao;

import com.cykj.pojo.Chapter;

import java.util.List;

/**
 * Description:
 * 负责处理章节数据库的Dao接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 16:54
 */
public interface IChapterDao {
    /**
     * Description: 获取视频的全部章节
     * @param courseId 课程id
     * @return java.util.List<com.cykj.pojo.Chapter>
     * @author Guguguy
     * @since 2024/3/28 15:30
     */
    List<Chapter> getCourseChapters (int courseId);
    /**
     * Description: 获取课程的某一章节
     * @param chapterId 章节id
     * @return com.cykj.pojo.Chapter
     * @author Guguguy
     * @since 2024/3/28 15:30
     */
    Chapter getCourseChapter(int chapterId);
}
