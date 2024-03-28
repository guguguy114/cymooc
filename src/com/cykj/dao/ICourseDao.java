package com.cykj.dao;

import com.cykj.pojo.Course;

import java.util.List;
import java.util.Set;

/**
 * Description:
 * 课程dao层接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/21 17:17
 */
public interface ICourseDao {
    /**
     * Description: 获取主页推荐视频
     * @param func 排序方法
     * @param num 推荐视频数量
     * @return java.util.List<java.lang.Long>
     * @author Guguguy
     * @since 2024/3/28 15:39
     */
    List<Long> getRecommendCourse(String func, int num);
    /**
     * Description: 获取课程对象
     * @param courseId 课程id
     * @return com.cykj.pojo.Course
     * @author Guguguy
     * @since 2024/3/28 15:40
     */
    Course getCourse(int courseId);
    /**
     * Description: 搜索课程功能
     * @param searchWord 课程关键字
     * @param page 搜索结果列表页数
     * @param limitNum 每页限制数量
     * @param sortMode 分类方式：如评论数、播放数等
     * @param types 分前后端搜索
     * @param tags 分tag搜索
     * @return java.util.List<com.cykj.pojo.Course>
     * @author Guguguy
     * @since 2024/3/28 15:40
     */
    List<Course> search(String searchWord, int page, int limitNum, String sortMode, String[] types, String[] tags);
    /**
     * Description: 获取搜索结果的总数
     * @param searchWord 搜索词
     * @param types 前后端
     * @param tags tag
     * @return int
     * @author Guguguy
     * @since 2024/3/28 15:42
     */
    int getSearchNum(String searchWord, String[] types, String[] tags);
    /**
     * Description: 获取搜索结果的全部tag（不重复）
     * @param searchWord 搜索词
     * @return java.util.Set<java.lang.String>
     * @author Guguguy
     * @since 2024/3/28 15:43
     */
    Set<String> getSearchResultTags(String searchWord);
}
