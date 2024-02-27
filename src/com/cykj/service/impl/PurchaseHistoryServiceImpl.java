package com.cykj.service.impl;

import com.cykj.dao.IPurchaseHistoryDao;
import com.cykj.dao.Impl.PurchaseHistoryDao;
import com.cykj.net.ResponseDto;
import com.cykj.service.PurchaseHistoryService;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 23:10
 */
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
    @Override
    public ResponseDto getPurchaseState(int uid, int courseId) {
        IPurchaseHistoryDao purchaseHistoryDao = PurchaseHistoryDao.getInstance();
        boolean state = purchaseHistoryDao.getPurchaseState(uid, courseId);
        ResponseDto dto;
        if (state) {
            dto = new ResponseDto(1, "success unlock", 1);
        } else {
            dto = new ResponseDto(0, "no unlock", 0);
        }
        return dto;
    }

    @Override
    public ResponseDto purchaseCourse(int uid, int courseId) {
        IPurchaseHistoryDao historyDao = PurchaseHistoryDao.getInstance();
        ResponseDto dto;
        int result = historyDao.purchaseCourse(uid, courseId);
        switch (result) {
            case 0:
                dto = new ResponseDto(0, "unknown wrong", 0);
                break;
            case 1:
                dto = new ResponseDto(1, "purchase successfully", 1);
                break;
            case 2:
                dto = new ResponseDto(2, "balance not enough", 2);
                break;
            case 3:
                dto = new ResponseDto(3, "has bought this course", 3);
                break;
            default:
                dto = new ResponseDto(0, "dao wrong", 0);
        }
        return dto;
    }
}
