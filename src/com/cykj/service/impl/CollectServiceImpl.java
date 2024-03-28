package com.cykj.service.impl;

import com.cykj.dao.ICollectDao;
import com.cykj.dao.Impl.CollectDao;
import com.cykj.net.ResponseDto;
import com.cykj.pojo.Course;
import com.cykj.service.CollectService;

import java.util.List;

/**
 * Description:
 * 评论Service层，返回dto
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 20:25
 */
public class CollectServiceImpl implements CollectService {
    @Override
    public ResponseDto getCurrentCourseCollectState(int uid, int courseId) {
        ICollectDao collectDao = CollectDao.getInstance();
        ResponseDto dto;
        int state = collectDao.getCurrentCourseCollectState(uid, courseId);
        if (state == 1) {
            dto = new ResponseDto(1, "get state success", 1);
        } else {
            dto = new ResponseDto(1, "get state success", 0);
        }
        return dto;
    }

    @Override
    public ResponseDto changeCurrentCourseCollectState(int uid, int courseId) {
        ICollectDao collectDao = CollectDao.getInstance();
        ResponseDto dto;
        boolean result = collectDao.changeCurrentCourseCollectState(uid, courseId);
        if (result) {
            dto = new ResponseDto(1, "change collect state success", null);
        } else {
            dto = new ResponseDto(0, "change collect state failed", null);
        }
        return dto;
    }

    @Override
    public ResponseDto getCourseCollectNum(int courseId) {
        ICollectDao collectDao = CollectDao.getInstance();
        ResponseDto dto;
        int collectNum = collectDao.getCourseCollectNum(courseId);
        if (collectNum < 0) {
            dto = new ResponseDto(0, "error", null);
        } else {
            dto = new ResponseDto(1, "successfully", collectNum);
        }
        return dto;
    }

    @Override
    public ResponseDto getUserCollections(int uid) {
        ICollectDao collectDao = CollectDao.getInstance();
        ResponseDto dto;
        List<Course> dataReturn = collectDao.getUserCollections(uid);
        if (!dataReturn.isEmpty()) {
            dto = new ResponseDto(1, "get collection list successfully", dataReturn);
        } else {
            dto = new ResponseDto(0, "fail to get collection list", null);
        }
        return dto;
    }
}
