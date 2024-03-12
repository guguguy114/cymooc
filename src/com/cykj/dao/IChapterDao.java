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
    List<Chapter> getCourseChapters (int courseId);
    Chapter getCourseChapter(int chapterId);
}
