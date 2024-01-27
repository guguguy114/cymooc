package com.cykj.dao;

import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;
import com.cykj.util.DBConnectPool;
import com.cykj.util.DBConnectUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/27 12:00
 */
public class BaseDao {
    /**
     * Description: 通用查询数据表方法
     * @param pojoClass 查询的表的pojo
     * @return void
     * @author Guguguy
     * @since 2024/1/27 22:50
     */
    public List<Object> getAll(Class<?> pojoClass) {
        List<Object> data = new ArrayList<>();
        Connection conn = DBConnectPool.getConn();
        String sql = "select * from " + pojoClass.getAnnotation(DBTable.class).value();
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            prep = conn.prepareStatement(sql);
            rs = prep.executeQuery();
            createPojoList(rs, data, pojoClass);
            System.out.println(data);
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectPool.giveBackConn(conn);
            DBConnectUtils.closeRes(prep, rs);
        }
    }

    public List<Object> query(String sql, List<Object> params, Class<?> pojoClass) {
        List<Object> data = new ArrayList<>();
        Connection conn = DBConnectPool.getConn();
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            prep = conn.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                prep.setObject((i + 1), params.get(i));
            }
            rs = prep.executeQuery();
            createPojoList(rs, data, pojoClass);
            return data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnectPool.giveBackConn(conn);
            DBConnectUtils.closeRes(prep, rs);
        }
    }

    private void createPojoList(ResultSet rs, List<Object> data, Class<?> pojoClass) {
        try {
            while (rs.next()) {
                Object o = pojoClass.newInstance();
                Field[] fields = pojoClass.getDeclaredFields();
                for (Field field : fields) {
                    String dbFieldName;
                    DBField dbField = field.getAnnotation(DBField.class);
                    if (dbField == null){
                        dbFieldName = field.getName();
                    }else {
                        dbFieldName = dbField.value();
                    }
                    field.setAccessible(true);
                    field.set(o, rs.getObject(dbFieldName));
                }
                data.add(o);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
