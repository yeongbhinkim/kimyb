package com.kh.app3.web.form.bbs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListForm {
  private Long bbsId;           //게시글 번호 BBS_ID	NUMBER(10,0)
  private String bcategory;     //분류 BCATEGORY	VARCHAR2(11 BYTE)
  private String title;         //제목 TITLE	VARCHAR2(150 BYTE)
  private String nickname;      //별칭 NICKNAME	VARCHAR2(30 BYTE)
  private LocalDateTime cdate;  //생성일 CDATE	TIMESTAMP(6)
  private int hit;              //조회수 HIT	NUMBER(5,0)
  private int bindent;          //답글 들여쓰기 BINDENT	NUMBER(3,0)
  private String email;         //EMAIL	VARCHAR2(50 BYTE)
//  private LocalDateTime udate;  //수정일 UDATE	TIMESTAMP(6)

}
