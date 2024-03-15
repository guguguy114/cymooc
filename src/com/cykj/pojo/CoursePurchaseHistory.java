package com.cykj.pojo;


import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;

import java.math.BigDecimal;

@DBTable("course_purchase_history")
public class CoursePurchaseHistory {

  @DBField("purchase_id")
  private long purchaseId;
  @DBField("course_id")
  private long courseId;
  private long uid;
  @DBField("purchase_time")
  private java.sql.Timestamp purchaseTime;
  private BigDecimal cost;


  public long getPurchaseId() {
    return purchaseId;
  }

  public void setPurchaseId(long purchaseId) {
    this.purchaseId = purchaseId;
  }


  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public java.sql.Timestamp getPurchaseTime() {
    return purchaseTime;
  }

  public void setPurchaseTime(java.sql.Timestamp purchaseTime) {
    this.purchaseTime = purchaseTime;
  }


  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

}
