package com.cykj.service.impl;

import com.cykj.dao.ILikeDao;
import com.cykj.dao.Impl.LikeDao;
import com.cykj.net.ResponseDto;
import com.cykj.service.LikeService;

/**
 * Description:
 * 实例化的点赞的Service层
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 23:46
 */
public class LikeServiceImpl implements LikeService {
    @Override
    public ResponseDto changeCurrentCourseLikeState(int uid, int courseId) {
        ILikeDao likeDao = LikeDao.getInstance();
        ResponseDto dto;
        boolean result = likeDao.changeCurrentCourseLikeState(uid, courseId);
        if (!result) {
            dto = new ResponseDto(0, "fail", null);
        } else {
            dto = new ResponseDto(1, "success", null);
        }
        return dto;
    }

    @Override
    public ResponseDto getCurrentCourseLikeState(int uid, int courseId) {
        ILikeDao likeDao = LikeDao.getInstance();
        ResponseDto dto;
        int result = likeDao.getCurrentCourseLikeState(uid, courseId);
        if (result == 1) {
            dto = new ResponseDto(1, "success", 1);
        } else if (result == 0) {
            dto = new ResponseDto(1, "success", 0);
        } else {
            dto = new ResponseDto(0, "fail", null);
        }
        return dto;
    }

    @Override
    public ResponseDto getCourseLikeNum(int courseId) {
        ILikeDao likeDao = LikeDao.getInstance();
        ResponseDto dto;
        int likeNum = likeDao.getCourseLikeNum(courseId);
        if (likeNum < 0) {
            dto = new ResponseDto(0, "error", null);
        } else {
            dto = new ResponseDto(1, "successfully", likeNum);
        }
        return dto;
    }
}
