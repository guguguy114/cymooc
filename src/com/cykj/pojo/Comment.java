package com.cykj.pojo;


import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;

@DBTable("comment")
public class Comment {

  @DBField("comment_id")
  private long commentId;
  @DBField("comment_body")
  private String commentBody;
  @DBField("comment_time")
  private java.sql.Timestamp commentTime;
  private long uid;
  @DBField("course_id")
  private long courseId;
  private boolean state;


  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(long commentId) {
    this.commentId = commentId;
  }


  public String getCommentBody() {
    return commentBody;
  }

  public void setCommentBody(String commentBody) {
    this.commentBody = commentBody;
  }


  public java.sql.Timestamp getCommentTime() {
    return commentTime;
  }

  public void setCommentTime(java.sql.Timestamp commentTime) {
    this.commentTime = commentTime;
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
