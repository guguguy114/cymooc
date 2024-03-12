package com.cykj.pojo;


import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;

@DBTable("watch_history")
public class WatchHistory {

  @DBField("history_id")
  private long historyId;
  private long uid;
  @DBField("course_id")
  private long courseId;
  @DBField("chapter_id")
  private long chapterId;
  @DBField("watch_time")
  private java.sql.Timestamp watchTime;


  public long getHistoryId() {
    return historyId;
  }

  public void setHistoryId(long historyId) {
    this.historyId = historyId;
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


  public long getChapterId() {
    return chapterId;
  }

  public void setChapterId(long chapterId) {
    this.chapterId = chapterId;
  }


  public java.sql.Timestamp getWatchTime() {
    return watchTime;
  }

  public void setWatchTime(java.sql.Timestamp watchTime) {
    this.watchTime = watchTime;
  }

}
