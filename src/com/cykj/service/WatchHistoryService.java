package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/12 21:48
 */
public interface WatchHistoryService {
    ResponseDto addWatchHistory(int uid, int courseId, int chapterId);
    ResponseDto getWatchHistoryNum(int uid);
    ResponseDto getUserWatchHistories(int uid, int limit, int page);
    ResponseDto getCoursePlayNum(int courseId);
}
