package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description:
 * 负责处理Dao的返回数据并打包成Dto的章节Service接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 23:44
 */
public interface LikeService {
    ResponseDto changeCurrentCourseLikeState(int uid, int courseId);
    ResponseDto getCurrentCourseLikeState(int uid, int courseId);
    ResponseDto getCourseLikeNum(int courseId);
}
