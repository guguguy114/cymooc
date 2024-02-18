package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/30 13:56
 */
public interface UserService {
    ResponseDto doLogin(String acc, String pwd);
    ResponseDto charge(int uid, int moneyToCharge);
    ResponseDto doRegister(String acc, String pwd);
    ResponseDto getUsers(int pageCurr, int pageSize);
}
