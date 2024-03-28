package com.cykj.service.impl;

import com.cykj.dao.IUserDao;
import com.cykj.dao.Impl.UserDao;
import com.cykj.net.ResponseDto;
import com.cykj.pojo.User;
import com.cykj.service.UserService;

/**
 * Description:
 * 用户service层
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/31 18:54
 */
public class UserServiceImpl implements UserService {
    @Override
    public ResponseDto doLogin(String acc, String pwd) {
        IUserDao userDao = UserDao.getInstance();
        ResponseDto responseDto;
        User user = userDao.doLogin(acc, pwd);
        if (user != null) {
            responseDto = new ResponseDto(1, "login successfully", user);
        }else {
            responseDto = new ResponseDto(0, "login failed!", null);
        }
        return responseDto;
    }

    @Override
    public ResponseDto charge(String acc, int chargeNum) {
        IUserDao userDao = UserDao.getInstance();
        ResponseDto responseDTO;
        if (userDao.charge(acc, chargeNum)){
            responseDTO = new ResponseDto(1, "charge success!", chargeNum);
        }else {
            responseDTO = new ResponseDto(0, "charge failed!", 0);
        }
        return responseDTO;
    }

    @Override
    public ResponseDto doRegister(String acc, String pwd) {
        IUserDao userDao = UserDao.getInstance();
        ResponseDto responseDto;
        int code = userDao.doRegister(acc, pwd);
        if (code == 1) {
            responseDto = new ResponseDto(code, "register success!", null);
        } else if (code == 2){
            responseDto = new ResponseDto(code, "account has existed", null);
        } else {
            responseDto = new ResponseDto(code, "register failed!", null);
        }
        return responseDto;
    }

    @Override
    public ResponseDto getUserInfo(int uid) {
        IUserDao userDao = UserDao.getInstance();
        ResponseDto responseDto;
        User user = userDao.getUserInfo(uid);
        if (user != null) {
            responseDto = new ResponseDto(1, "get info successfully", user);
        } else {
            responseDto = new ResponseDto(0, "fail to get userinfo", null);
        }
        return responseDto;
    }

    @Override
    public ResponseDto changeInfo(String infoType, String newInfo, String acc) {
        IUserDao userDao = UserDao.getInstance();
        ResponseDto responseDto;
        if (userDao.changeInfo(infoType, newInfo, acc)) {
            responseDto = new ResponseDto(1, "change successfully!", null);
        } else {
            responseDto = new ResponseDto(0, "fail to change info!", null);
        }
        return responseDto;
    }

    @Override
    public ResponseDto uploadFaceImage(int uid, String imageData) {
        IUserDao userDao = UserDao.getInstance();
        ResponseDto dto;
        if (userDao.uploadFaceImage(uid, imageData)) {
            dto = new ResponseDto(1, "upload face image success", null);
        } else {
            dto = new ResponseDto(0, "fail to upload face image", null);
        }
        return dto;
    }


}
