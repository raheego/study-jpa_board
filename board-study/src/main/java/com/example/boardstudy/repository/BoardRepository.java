package com.example.boardstudy.repository;

import com.example.boardstudy.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository  extends JpaRepository<BoardEntity,Long > { // repository 는 entity를 받는다.
}
