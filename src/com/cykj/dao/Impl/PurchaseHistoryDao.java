package com.cykj.dao.Impl;

import com.cykj.annotation.DBTable;
import com.cykj.dao.BaseDao;
import com.cykj.dao.ICourseDao;
import com.cykj.dao.IPurchaseHistoryDao;
import com.cykj.dao.IUserDao;
import com.cykj.pojo.Course;
import com.cykj.pojo.CoursePurchaseHistory;
import com.cykj.pojo.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 处理购买记录的数据库dao层
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 23:15
 */
public class PurchaseHistoryDao extends BaseDao implements IPurchaseHistoryDao {
    private static PurchaseHistoryDao purchaseHistoryDao;
    private final String tableName;
    private final Class<CoursePurchaseHistory> historyPojoClass;
    private PurchaseHistoryDao() {
        historyPojoClass = CoursePurchaseHistory.class;
        tableName = historyPojoClass.getAnnotation(DBTable.class).value();
    }
    @Override
    public boolean getPurchaseState(int uid, int courseId) {
        String sql = "select * from " + tableName + " where uid = ? and course_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        params.add(courseId);
        List<Object> dataReturn = query(sql, params, historyPojoClass);
        return !dataReturn.isEmpty();
    }

    @Override
    public int purchaseCourse(int uid, int courseId) {
        boolean purchaseState = getPurchaseState(uid, courseId);
        if (purchaseState) {
            return 3;
        } else {
            IUserDao userDao = UserDao.getInstance();
            User user = userDao.getUserInfo(uid);
            BigDecimal balance = user.getBalance();
            ICourseDao courseDao = CourseDao.getInstance();
            Course course = courseDao.getCourse(courseId);
            BigDecimal coursePrice = course.getCoursePrice();
            if (balance.compareTo(coursePrice) > 0) {
                String sql = "insert into " + tableName + " (uid, course_id, cost) values (?, ?, ?)";
                List<Object> params = new ArrayList<>();
                BigDecimal rest = balance.subtract(coursePrice);
                params.add(uid);
                params.add(courseId);
                params.add(coursePrice);
                int result = update(sql, params);
                if (result == 1) {
                    boolean res = userDao.setBalance(uid, rest);
                    if (res){
                        return 1;
                    } else {
                        return 0;
                    }
                } else {
                    return 0;
                }
            } else {
                return 2;
            }
        }
    }

    @Override
    public List<CoursePurchaseHistory> getPurchaseHistories(int uid, int limitNum, int page) {
        int startNum = (page - 1) * limitNum;
        String sql = "select * from " + tableName + " where uid = ? order by purchase_time desc limit ?, ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        params.add(startNum);
        params.add(limitNum);
        List<CoursePurchaseHistory> historyList = new ArrayList<>();
        List<Object> dataReturn = query(sql, params, historyPojoClass);
        for (Object o : dataReturn) {
            historyList.add((CoursePurchaseHistory) o);
        }
        return historyList;
    }

    @Override
    public int getPurchaseHistoryNum(int uid) {
        String sql = "select * from " + tableName + " where uid = ?";
        List<Object> params = new ArrayList<>();
        params.add(uid);
        return queryNum(sql, params);
    }

    public synchronized static PurchaseHistoryDao getInstance() {
        if (purchaseHistoryDao == null) {
            purchaseHistoryDao = new PurchaseHistoryDao();
        }
        return purchaseHistoryDao;
    }
}
