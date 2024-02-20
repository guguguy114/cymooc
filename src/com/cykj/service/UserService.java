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
    ResponseDto charge(String acc, int chargeNum);
    ResponseDto doRegister(String acc, String pwd);
    ResponseDto getUserInfo(String acc);
    ResponseDto changeInfo(String infoType, String newInfo, String acc);
}
