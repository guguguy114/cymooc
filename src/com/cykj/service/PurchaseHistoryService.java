package com.cykj.service;

import com.cykj.net.ResponseDto;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 23:08
 */
public interface PurchaseHistoryService {
    ResponseDto getPurchaseState(int uid, int courseId);
    ResponseDto purchaseCourse(int uid, int courseId);
    ResponseDto getPurchaseHistories(int uid, int limitNum, int page);
    ResponseDto getPurchaseHistoryNum(int uid);
}
