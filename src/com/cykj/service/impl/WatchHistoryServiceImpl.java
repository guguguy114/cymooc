package com.cykj.service.impl;

import com.cykj.dao.IWatchHistoryDao;
import com.cykj.dao.Impl.WatchHistoryDao;
import com.cykj.net.ResponseDto;
import com.cykj.pojo.WatchHistory;
import com.cykj.service.WatchHistoryService;

import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/12 21:50
 */
public class WatchHistoryServiceImpl implements WatchHistoryService {
    @Override
    public ResponseDto addWatchHistory(int uid, int courseId, int chapterId) {
        IWatchHistoryDao watchHistoryDao = WatchHistoryDao.getInstance();
        boolean code = watchHistoryDao.addWatchHistory(uid, courseId, chapterId);
        ResponseDto dto;
        if (code) {
            dto = new ResponseDto(1, "add watch history successfully", null);
        } else {
            dto = new ResponseDto(0, "add watch history failed", null);
        }
        return dto;
    }

    @Override
    public ResponseDto getWatchHistoryNum(int uid) {
        IWatchHistoryDao watchHistoryDao = WatchHistoryDao.getInstance();
        int num = watchHistoryDao.getWatchHistoryNum(uid);
        return new ResponseDto(1, "get watch history successfully", num);
    }

    @Override
    public ResponseDto getUserWatchHistories(int uid, int limit, int page) {
        IWatchHistoryDao watchHistoryDao = WatchHistoryDao.getInstance();
        List<WatchHistory> dataReturn = watchHistoryDao.getUserWatchHistories(uid, limit, page);
        ResponseDto dto;
        if (dataReturn != null) {
            dto = new ResponseDto(1, "get watch history successfully", dataReturn);
        } else {
            dto = new ResponseDto(0, "fail to get watch history", null);
        }
        return dto;
    }
}
