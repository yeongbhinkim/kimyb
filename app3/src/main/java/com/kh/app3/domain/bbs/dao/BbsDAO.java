package com.kh.app3.domain.bbs.dao;

import java.util.List;

public interface BbsDAO {
  /**
   * 원글작성
   * @param bbs
   * @return 게시글 번호
   */
  Long saveOrigin(Bbs bbs);

  /**
   * 목록
   * @return
   */
  List<Bbs> findAll();
  List<Bbs> findAll(String category);

  /**
   * 상세조회
   * @param id 게시글번호
   * @return
   */
  Bbs findByBbsId(Long id);

  /**
   * 삭제
   * @param id 게시글번호
   * @return   삭제건수
   */
  int deleteBbsId(Long id);

  /**
   * 수정
   * @param id  게시글번호
   * @param bbs 수정내용
   * @return    수정건수
   */
  int updateByBbsId(Long id,Bbs bbs);

  /**답글작성
   *
   * @param pbbsId
   * @param replybbs
   * @return 답글번호
   */
  Long saverReply(Long pbbsId, Bbs replybbs);

  /**조회수증가
   *
   * @param id 게시글번호
   * @return 수정건수
   */
  int increaseHitCount(Long id);

  /**전체건수
   *
   * @return 게시글 전체건수
   */
  int totalCount();

}
