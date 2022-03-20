package com.kh.app3.domain.bbs.dao;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor

public class BbsDAOImpl implements BbsDAO {

  private final JdbcTemplate jdbcTemplate;

  //등록
  @Override
  public Long saveOrigin(Bbs bbs) {
    // SQL 작성
    StringBuffer sql = new StringBuffer();
//    sql.append("insert into bbs (bbs_id,bcategory,title,email,nickname,bcontent,pbbs_id) ");
    sql.append("insert into bbs (bbs_id,bcategory,title,email,nickname,bcontent,bgroup) ");
    sql.append("values (bbs_bbs_id_seq.nextval,?, ? , ? , ? , ?,bbs_bbs_id_seq.currval) ");

    // SQL 실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"bbs_id"} //insert 후 insert 레코드 중 반환할 컬럼명, keyHolder에 저장됨.
        );
        pstmt.setString(1, bbs.getBcategory());
        pstmt.setString(2, bbs.getTitle());
        pstmt.setString(3, bbs.getEmail());
        pstmt.setString(4, bbs.getNickname());
        pstmt.setString(5, bbs.getBcontent());
        return pstmt;
      }
    }, keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("bbs_id").toString());

//    long bbs_id = Long.valueOf(keyHolder.getKeys().get("bbs_id").toString());
//    return findByBbsId(bbs_id);
  }

  //전체조회
  @Override
  public List<Bbs> findAll() {
    StringBuffer sql = new StringBuffer();

    sql.append("select ");
    sql.append("       bbs_id, ");
    sql.append("       bcategory, ");
    sql.append("       title, ");
    sql.append("       email, ");
    sql.append("       nickname, ");
    sql.append("       hit, ");
    sql.append("       bcontent, ");
    sql.append("       pbbs_id, ");
    sql.append("       bgroup, ");
    sql.append("       step, ");
    sql.append("       bindent, ");
    sql.append("       status, ");
    sql.append("       cdate, ");
    sql.append("       udate ");
    sql.append("  from bbs ");
    sql.append(" order by bgroup desc, step asc ");

    List<Bbs> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Bbs.class)
    );

    return list;
  }
  //카테고리별 목록
  @Override
  public List<Bbs> findAll(String category) {
    StringBuffer sql = new StringBuffer();

    sql.append("select ");
    sql.append("       bbs_id, ");
    sql.append("       bcategory, ");
    sql.append("       title, ");
    sql.append("       email, ");
    sql.append("       nickname, ");
    sql.append("       hit, ");
    sql.append("       bcontent, ");
    sql.append("       pbbs_id, ");
    sql.append("       bgroup, ");
    sql.append("       step, ");
    sql.append("       bindent, ");
    sql.append("       status, ");
    sql.append("       cdate, ");
    sql.append("       udate ");
    sql.append("  from bbs ");
    sql.append(" where bcategory = ? ");
    sql.append(" order by bgroup desc, step asc ");

    List<Bbs> list = jdbcTemplate.query(
        sql.toString(),
        new BeanPropertyRowMapper<>(Bbs.class),category);

    return list;
  }

  //조회
  @Override
  public Bbs findByBbsId(Long id) {

    StringBuffer sql = new StringBuffer();

    sql.append(" SELECT ");
    sql.append("     bbs_id, ");
    sql.append("     bcategory, ");
    sql.append("     title, ");
    sql.append("     email, ");
    sql.append("     nickname, ");
    sql.append("     hit, ");
    sql.append("     bcontent, ");
    sql.append("     pbbs_id, ");
    sql.append("     bgroup, ");
    sql.append("     step, ");
    sql.append("     bindent, ");
    sql.append("     status, ");
    sql.append("     cdate, ");
    sql.append("     udate ");
    sql.append(" FROM ");
    sql.append("     bbs ");
    sql.append(" where bbs_id = ? ");

    Bbs bbsItem = null;
    try {
      bbsItem = jdbcTemplate.queryForObject(
          sql.toString(),
          new BeanPropertyRowMapper<>(Bbs.class),
          id);
    } catch (Exception e) { //1건을 못찾으면
      bbsItem = null;
    }

    return bbsItem;
  }

  //삭제
  @Override
  public int deleteBbsId(Long id) {
    StringBuffer sql = new StringBuffer();
    sql.append(" DELETE FROM bbs ");
    sql.append(" WHERE  bbs_id = ? ");

    int updateItemCount = jdbcTemplate.update(sql.toString(), id);

    return updateItemCount;
  }

  //수정
  @Override
  public int updateByBbsId(Long id, Bbs bbs) {
    StringBuffer sql = new StringBuffer();

    sql.append(" update bbs ");
    sql.append(" set bcategory = ?, ");
    sql.append("     title = ?, ");
    sql.append("     bcontent = ?, ");
    sql.append("     udate = systimestamp ");
    sql.append(" where bbs_id = ? ");

    int updatedItemCount = jdbcTemplate.update(
        sql.toString(),
        bbs.getBcategory(),
        bbs.getTitle(),
        bbs.getBcontent(),
        id
    );

    return updatedItemCount;
  }

  //답글작성
  @Override
  public Long saverReply(Long pbbsId, Bbs replyBbs) {

    //부모글 참조반영
    Bbs bbs = addInfoOfParnetToChild(pbbsId,replyBbs);

    StringBuffer sql = new StringBuffer();
    sql.append("insert into bbs (bbs_id,bcategory,title,email,nickname,bcontent,pbbs_id,bgroup,step,bindent) ");
    sql.append("values (bbs_bbs_id_seq.nextval, ?, ? , ? , ? , ?, ?, ?, ?, ?) ");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(
            sql.toString(),
            new String[]{"bbs_id"} //insert 후 insert 레코드 중 반환할 컬럼명, keyHolder에 저장됨.
        );
        pstmt.setString(1, bbs.getBcategory());
        pstmt.setString(2, bbs.getTitle());
        pstmt.setString(3, bbs.getEmail());
        pstmt.setString(4, bbs.getNickname());
        pstmt.setString(5, bbs.getBcontent());
        pstmt.setLong(6, bbs.getPbbsId());
        pstmt.setLong(7, bbs.getBgroup());
        pstmt.setInt(8, bbs.getStep());
        pstmt.setInt(9, bbs.getBindent());
        
        return pstmt;
      }
    }, keyHolder);

    return Long.valueOf(keyHolder.getKeys().get("bbs_id").toString());
  }

  private Bbs addInfoOfParnetToChild(Long pbbsId, Bbs replyBbs) {
    //부모글
    Bbs bbs = findByBbsId(pbbsId);
    
    //부모글의 카테고리 가져오기
    replyBbs.setBcategory(bbs.getBcategory());

    //bgroup 로직
    // 답글의 Bgroup = 부모글의 Bgroup
    replyBbs.setBgroup(bbs.getBgroup());

    //step 로직
    //1) 부모글의 Bgroup값고 동일한 게시글중 부모글의 Bstep보다 큰 게시글의 bstep을 1씩 증가
    int affectedRows = updateBstep(bbs);

    //2) 답글의 Bstep값은 부모들의 Bstep값 + 1
    replyBbs.setStep(bbs.getStep() + 1);

    //Bindent 로직
    //답글의 Bindent = 부모글의 Bindent+1
    replyBbs.setBindent(bbs.getBindent() + 1);

    replyBbs.setPbbsId(pbbsId);

    return replyBbs;
  }
  //부모글과 동일한 그룹 bstep반영
  private int updateBstep(Bbs bbs) {
  StringBuffer sql = new StringBuffer();

   sql.append(" update bbs ");
   sql.append(" set step = step + 1 ");
   sql.append(" where bgroup = ? ");
   sql.append(" and step > ? ");

    int affectedRows = jdbcTemplate.update(sql.toString(), bbs.getBgroup(), bbs.getStep());

    return affectedRows;
  }

  //조회수
  @Override
  public int increaseHitCount(Long id) {
    StringBuffer sql = new StringBuffer();

    sql.append("update bbs ");
    sql.append("set hit = hit + 1 ");
    sql.append("where bbs_id = ? ");

    int affectedRows = jdbcTemplate.update(sql.toString(), id);

    return affectedRows;
  }

  //전체건수
  @Override
  public int totalCount() {
//    StringBuffer sql = new StringBuffer();
//
//    sql.append(" select count(*) from bbs ");
//    int totalCtn = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

    String sql = " select count(*) from bbs ";
//queryForObject 1개 초과시 예외 발생
    Integer totalCtn = jdbcTemplate.queryForObject(sql, Integer.class);

    return totalCtn;
  }
}
