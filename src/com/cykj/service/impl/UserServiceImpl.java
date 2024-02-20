package com.cykj.service.impl;

import com.cykj.dao.IUserDao;
import com.cykj.dao.Impl.UserDao;
import com.cykj.net.ResponseDto;
import com.cykj.pojo.User;
import com.cykj.service.UserService;

/**
 * Description: TODO
 *
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
            responseDto = new ResponseDto(1, "register success!", null);
        } else if (code == 2){
            responseDto = new ResponseDto(0, "account has existed", null);
        } else {
            responseDto = new ResponseDto(0, "register failed!", null);
        }
        return responseDto;
    }

    @Override
    public ResponseDto getUserInfo(String acc) {
        IUserDao userDao = UserDao.getInstance();
        ResponseDto responseDto;
        User user = userDao.getUserInfo(acc);
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
}
