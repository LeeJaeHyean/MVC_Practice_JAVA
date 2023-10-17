package com.example.demo.dto;

public class LogInDto {
   
   private String id;
   private String pw;
   private String name;
   private String sex;
   private String brith1;
   
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getPw() {
      return pw;
   }

   public void setPw(String pw) {
      this.pw = pw;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSex() {
      return sex;
   }

   public void setSex(String sex) {
      this.sex = sex;
   }

   public final String getBrith1() {
      return brith1;
   }

   public final void setBrith1(String brith1) {
      this.brith1 = brith1;
   }

   @Override
   public String toString() {
      return "LogInDto [id=" + id + ", pw=" + pw + ", name=" + name + "]";
   }
   
   
}