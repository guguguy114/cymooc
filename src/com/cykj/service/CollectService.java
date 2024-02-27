package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 20:24
 */
public interface CollectService {
    ResponseDto getCurrentCourseCollectState(int uid, int courseId);
    ResponseDto changeCurrentCourseCollectState(int uid, int courseId);
    ResponseDto getCourseCollectNum(int courseId);
    ResponseDto getUserCollections(int uid, int num, int currentPage);
}
