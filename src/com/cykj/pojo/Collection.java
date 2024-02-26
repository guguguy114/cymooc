package com.cykj.pojo;


import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;

@DBTable("collection")
public class Collection {

  @DBField("collection_id")
  private long collectionId;
  @DBField("uid")
  private long uid;
  @DBField("course_id")
  private long courseId;
  @DBField("state")
  private boolean state;
  @DBField("collect_time")
  private java.sql.Timestamp collectTime;


  public long getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(long collectionId) {
    this.collectionId = collectionId;
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


  public java.sql.Timestamp getCollectTime() {
    return collectTime;
  }

  public void setCollectTime(java.sql.Timestamp collectTime) {
    this.collectTime = collectTime;
  }

}
