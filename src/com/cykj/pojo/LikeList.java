package com.cykj.pojo;


import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;

@DBTable("like_list")
public class LikeList {

  @DBField("like_id")
  private long likeId;
  @DBField("uid")
  private long uid;
  @DBField("course_id")
  private long courseId;
  @DBField("state")
  private boolean state;


  public long getLikeId() {
    return likeId;
  }

  public void setLikeId(long likeId) {
    this.likeId = likeId;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }


  public boolean getState() {
    return state;
  }

  public void setState(boolean state) {
    this.state = state;
  }

}
