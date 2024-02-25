package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description:
 * 负责对Dao返回的数据进行处理并打包Dto的Service层的接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 16:53
 */
public interface ChapterService {
    ResponseDto getCourseChapters(int id);
}
