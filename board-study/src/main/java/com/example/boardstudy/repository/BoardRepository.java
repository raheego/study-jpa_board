package com.example.boardstudy.repository;

import com.example.boardstudy.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository  extends JpaRepository<BoardEntity,Long > { // repository 는 entity를 받는다.
    // 게시글 조회수 증가
    // UPDATE board_table SET board_hits = board_hits + 1 WHERE id = ?;
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id") //entity기준으로 함
    void updateHits(@Param("id") Long id);
}
