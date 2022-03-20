package com.kh.app3.domain.bbs.svc;

import com.kh.app3.domain.bbs.dao.Bbs;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BbsSVC {

    /**
     * 원글작성
     * @param bbs
     * @return 게시글 번호
     */
    Long saveOrigin(Bbs bbs);


    /**
     * 원글작성-첨부파일 있는경우
     * @param bbs
     * @param files 첨파일
     * @return 게시글 번호
     */
    Long saveOrigin(Bbs bbs, List<MultipartFile> files);


    /**
     * 목록
     * @return
     */
    List<Bbs> findAll();

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

    /**전체건수
     *
     * @return 게시글 전체건수
     */
    int totalCount();

  }
