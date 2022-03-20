package com.kh.app3.domain.notice.dao;

import com.kh.app3.domain.notice.Notice;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class NoticeDAOImplTest {

  @Autowired  //SC(스프링컨테이너)에서 동일타입의 객체를 주입받는다.
  private NoticeDAO noticeDAO;

  @Test
  @DisplayName("공지사항 등록")
  void create() {

    Notice notice = new Notice();
    notice.setSubject("제목3");
    notice.setContent("본문3");
    notice.setAuthor("홍길동3");
    Notice savedNotice = noticeDAO.create(notice);
    Assertions.assertThat(notice.getSubject()).isEqualTo(savedNotice.getSubject());
    log.info("savedNotice={}", savedNotice.getNoticeId());

  }

  @Test
  @DisplayName("공지사항 조회 1건")
  void selectOne() {
    Long noticeId = 1L;
    Notice notice = noticeDAO.selectOne(noticeId);
    Assertions.assertThat(notice).isNotNull();
    log.info("notice = {}", notice);
  }

  @Test
  @DisplayName("공지사항 변경")
  void update() {

    //when
    Long noticeId = 2L;
    Notice notice = noticeDAO.selectOne(noticeId);
    notice.setSubject("수정후 제목2");
    notice.setContent("수정후 본문2");

    //try
    Notice updatedNotice = noticeDAO.update(notice);

    //then
    Assertions.assertThat(updatedNotice.getSubject()).isEqualTo(notice.getSubject());
    Assertions.assertThat(updatedNotice.getContent()).isEqualTo(notice.getContent());

  }

  @Test
  @DisplayName("공지사항 삭제 by 공지ID")
  void delete() {
    //when
    Long noticeId = 3L;
    //try
    int cnt = noticeDAO.delete(noticeId);
    //then
//    Assertions.assertThat(cnt).isEqualTo(1);
    Assertions.assertThat(noticeDAO.selectOne(3L)).isNull();
  }

  @Test
  @DisplayName("조회수 증가")
  void updateHit() {
    //when
    Long noticeId = 2L;
    Notice notice = noticeDAO.selectOne(noticeId);
    Long currentHit = notice.getHit();

    //try
    noticeDAO.updateHit(notice.getNoticeId());

    //then
    Notice noticeAfterHiting = noticeDAO.selectOne(noticeId);
    Assertions.assertThat(noticeAfterHiting.getHit()).isEqualTo(currentHit+1);
  }

  @Test
  @DisplayName("전체조회")
  void selectAll(){
    //when

    //try
    List<Notice> notices = noticeDAO.selectAll();

    //then
    Assertions.assertThat(notices.size()).isEqualTo(2);
    log.info("notices={}", notices);
  }
}