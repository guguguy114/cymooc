package com.cykj.pojo;

import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;

import java.math.BigDecimal;


@DBTable("course")
public class Course {

  @DBField("course_id")
  private long courseId;
  @DBField("course_name")
  private String courseName;
  @DBField("course_img")
  private String courseImg;
  @DBField("update_time")
  private java.sql.Timestamp updateTime;
  @DBField("course_price")
  private BigDecimal coursePrice;
  @DBField("course_description")
  private String courseDescription;
  @DBField("course_type")
  private String courseType;
  @DBField("course_tag")
  private String courseTag;


  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }


  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }


  public String getCourseImg() {
    return courseImg;
  }

  public void setCourseImg(String courseImg) {
    this.courseImg = courseImg;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }


  public BigDecimal getCoursePrice() {
    return coursePrice;
  }

  public void setCoursePrice(BigDecimal coursePrice) {
    this.coursePrice = coursePrice;
  }


  public String getCourseDescription() {
    return courseDescription;
  }

  public void setCourseDescription(String courseDescription) {
    this.courseDescription = courseDescription;
  }

  public String getCourseType() {
    return courseType;
  }

  public void setCourseType(String courseType) {
    this.courseType = courseType;
  }

  public String getCourseTag() {
    return courseTag;
  }

  public void setCourseTag(String courseTag) {
    this.courseTag = courseTag;
  }
}
