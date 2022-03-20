package com.kh.app3.domain.bbs.dao;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class BbsDAOImplTest {

  @Autowired  //SC(스프링컨테이너)에서 동일타입의 객체를 주입받는다.
  private BbsDAO bbsDAO;

  @Test
  @DisplayName("등록")
  void saveOrigin() {

    Bbs bbs = new Bbs();
    bbs.setBcategory("B01");
    bbs.setTitle("제목22");
    bbs.setEmail("test1@kh.com");
    bbs.setNickname("별칭22");
    bbs.setBcontent("본문22");

    Long saveOriginId = bbsDAO.saveOrigin(bbs);
    Assertions.assertThat(saveOriginId).isEqualTo(5);
    log.info("saveOriginId={}", saveOriginId);


//    Bbs savedBbs =  bbsDAO.saveOrigin(bbs);
//    Assertions.assertThat(bbs.getTitle()).isEqualTo(savedBbs.getTitle());
//    log.info("savedBbs={}", savedBbs.getBbsId());
  }

  @Test
  @DisplayName("전체조회")
  void findAll() {
    //when

    //try
    List<Bbs> list = bbsDAO.findAll();

    //then
//    Assertions.assertThat(bbs.size()).isEqualTo(5);
//    log.info("bbs={}", bbs);
    Assertions.assertThat(list.get(0).getTitle()).isEqualTo("제목");
    for (Bbs bbs : list){
      log.info(bbs.toString());
    }
  }

  @Test
  @DisplayName("카테고리별 목록")
  void findAllByCategory() {
    String category = "B0104";
    List<Bbs> list = bbsDAO.findAll(category);

    for (Bbs bbs : list) {
      log.info(bbs.toString());
    }
  }

  @Test
  @DisplayName("조회")
  void findByBbsId() {
    Long bbsId = 2L;
    Bbs findByBbsItem = bbsDAO.findByBbsId(bbsId);
    Assertions.assertThat(findByBbsItem.getTitle()).isEqualTo("제목");
  }


  @Test
  @DisplayName("삭제")
  void deleteBbsId() {
    Long bbsId = 23L;
    int deletedBbsItemCount = bbsDAO.deleteBbsId(bbsId);

    Assertions.assertThat(deletedBbsItemCount).isEqualTo(1);

    Bbs findedBbsItem = bbsDAO.findByBbsId(bbsId);
    Assertions.assertThat(findedBbsItem).isNull();
  }

  @Test
  @DisplayName("게시글 수정")
  void updateByBbsId() {
    Long bbsId = 1L;
    //수정전
    Bbs beforeUpdatingItem = bbsDAO.findByBbsId(bbsId);
    beforeUpdatingItem.setBcategory("B0102");
    beforeUpdatingItem.setTitle("수정후 제목");
    beforeUpdatingItem.setBcontent("수정후 본문");
    bbsDAO.updateByBbsId(bbsId,beforeUpdatingItem);

    //수정후
    Bbs afterUpdatingItem = bbsDAO.findByBbsId(bbsId);

    //수정전후 비교
    Assertions.assertThat(beforeUpdatingItem.getBcategory())
        .isEqualTo(afterUpdatingItem.getBcategory());
    Assertions.assertThat(beforeUpdatingItem.getTitle())
        .isEqualTo(afterUpdatingItem.getTitle());
    Assertions.assertThat(beforeUpdatingItem.getBcontent())
        .isEqualTo(afterUpdatingItem.getBcontent());
    Assertions.assertThat(beforeUpdatingItem.getUdate())
        .isNotEqualTo(afterUpdatingItem.getUdate());
  }
  @Test
  @DisplayName("답글작성")
  void saverReply() {
    Long pbbsId = 7L;
    Bbs bbs = new Bbs();
    bbs.setBcategory("B01");
    bbs.setTitle("제목1");
    bbs.setEmail("test1@kh.com");
    bbs.setNickname("별칭1");
    bbs.setBcontent("본문");

    bbsDAO.saverReply(pbbsId,bbs);
  }

  @Test
  @DisplayName("조회수")
  void hitCount() {
    //when
    Long bbsId = 1L;

    //조회전 조회수
    int beforeHitCount = bbsDAO.findByBbsId(bbsId).getHit();

    //try
        bbsDAO.increaseHitCount(1L);

//    bbsDAO.increaseHitCount(bbsId);
//  조회후 조회수
    int afterHitCount = bbsDAO.findByBbsId(bbsId).getHit();
    //then
    Assertions.assertThat(afterHitCount-beforeHitCount).isEqualTo(1);

//    Bbs bbsAfterHiting = bbsDAO.findByBbsId(bbsId);
//    Assertions.assertThat(bbsAfterHiting.getHit()).isEqualTo(12);

  }

  @Test
  @DisplayName("전체건수")
  void totalCount() {
    int size = bbsDAO.findAll().size();
    int totalCtn = bbsDAO.totalCount();

    Assertions.assertThat(totalCtn).isEqualTo(size);
  }


}