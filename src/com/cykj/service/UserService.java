package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description:
 * 用户service层接口
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/30 13:56
 */
public interface UserService {
    ResponseDto doLogin(String acc, String pwd);
    ResponseDto charge(String acc, int chargeNum);
    ResponseDto doRegister(String acc, String pwd);
    ResponseDto getUserInfo(int uid);
    ResponseDto changeInfo(String infoType, String newInfo, String acc);
    ResponseDto uploadFaceImage(int uid, String imageData);
}
