package com.cykj.pojo;


import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;

@DBTable("chapter")
public class Chapter {

  @DBField("chapter_id")
  private long chapterId;
  @DBField("course_id")
  private long courseId;
  @DBField("chapter_video")
  private String chapterVideo;
  @DBField("chapter_name")
  private String chapterName;
  @DBField("chapter_order")
  private long chapterOrder;

  public long getChapterOrder() {
    return chapterOrder;
  }

  public void setChapterOrder(long chapterOrder) {
    this.chapterOrder = chapterOrder;
  }

  public long getChapterId() {
    return chapterId;
  }

  public void setChapterId(long chapterId) {
    this.chapterId = chapterId;
  }


  public long getCourseId() {
    return courseId;
  }

  public void setCourseId(long courseId) {
    this.courseId = courseId;
  }


  public String getChapterVideo() {
    return chapterVideo;
  }

  public void setChapterVideo(String chapterVideo) {
    this.chapterVideo = chapterVideo;
  }


  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

}
