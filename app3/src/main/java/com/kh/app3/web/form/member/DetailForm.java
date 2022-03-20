package com.kh.app3.web.form.member;

import lombok.Data;

import java.util.List;

@Data
public class DetailForm {

  private String email;
  private String passwd;
  private String nickname;
  private Gender gender;        // 남, 여
  private List<String> hobby;   // 취미
  private String region;        // 지역
  
}
