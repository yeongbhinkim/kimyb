package com.kh.app3.web.form.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
  private Long noticeId;
  private String subject;
  private String cdata;
  private String ctime;
  private Long hit;
}
