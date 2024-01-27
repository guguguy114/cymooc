package com.cykj.pojo;

import com.cykj.annotation.DBField;
import com.cykj.annotation.DBTable;

@DBTable("user")
public class User {

  private long uid;
  private String account;
  private String password;
  private String nickname;
  @DBField("face_image")
  private String faceImage;
  private long balance;
  @DBField("register_time")
  private java.sql.Timestamp registerTime;


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getFaceImage() {
    return faceImage;
  }

  public void setFaceImage(String faceImage) {
    this.faceImage = faceImage;
  }


  public long getBalance() {
    return balance;
  }

  public void setBalance(long balance) {
    this.balance = balance;
  }


  public java.sql.Timestamp getRegisterTime() {
    return registerTime;
  }

  public void setRegisterTime(java.sql.Timestamp registerTime) {
    this.registerTime = registerTime;
  }

}
