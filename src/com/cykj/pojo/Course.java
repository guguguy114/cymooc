package com.cykj.pojo;

import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;


@DBTable("course")
public class Course {

  @DBField("course_id")
  private long courseId;
  @DBField("course_name")
  private String courseName;
  @DBField("course_img")
  private String courseImg;
  @DBField("updated_time")
  private java.sql.Timestamp updatedTime;
  @DBField("course_price")
  private double coursePrice;
  @DBField("course_description")
  private String courseDescription;


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


  public java.sql.Timestamp getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(java.sql.Timestamp updatedTime) {
    this.updatedTime = updatedTime;
  }


  public double getCoursePrice() {
    return coursePrice;
  }

  public void setCoursePrice(double coursePrice) {
    this.coursePrice = coursePrice;
  }


  public String getCourseDescription() {
    return courseDescription;
  }

  public void setCourseDescription(String courseDescription) {
    this.courseDescription = courseDescription;
  }

}
