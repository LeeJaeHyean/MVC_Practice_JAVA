package com.example.demo.dto;


public class BoardDto {
   private int no; 
   private String id;
   private String title;
   private String contents;
   
   
   public final int getNo() {
      return no;
   }
   public final void setNo(int no) {
      this.no = no;
   }
   public final String getId() {
      return id;
   }
   public final void setId(String id) {
      this.id = id;
   }
   public final String getTitle() {
      return title;
   }
   public final void setTitle(String title) {
      this.title = title;
   }
   public final String getContents() {
      return contents;
   }
   public final void setContents(String contents) {
      this.contents = contents;
   }
@Override
public String toString() {
	return "BoardDto [no=" + no + ", id=" + id + ", title=" + title + ", contents=" + contents + "]";
}
   
  
   
}