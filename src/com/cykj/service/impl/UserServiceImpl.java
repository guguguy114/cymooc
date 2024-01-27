package com.cykj.service.impl;

import com.cykj.dao.IUserDao;
import com.cykj.dao.Impl.UserDao;
import com.cykj.net.ResponseDto;
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
        return null;
    }

    @Override
    public ResponseDto charge(int uid, int moneyToCharge) {
        IUserDao userDao; userDao = UserDao.getInstance();
        ResponseDto responseDTO;
        if (userDao.charge(uid, moneyToCharge)){
            responseDTO = new ResponseDto(1, "charge success!", moneyToCharge);
        }else {
            responseDTO = new ResponseDto(0, "charge failed!", 0);
        }
        return responseDTO;
    }

    @Override
    public ResponseDto doRegister() {
        return null;
    }

    @Override
    public ResponseDto getUsers(int pageCurr, int pageSize) {
        return null;
    }
}
