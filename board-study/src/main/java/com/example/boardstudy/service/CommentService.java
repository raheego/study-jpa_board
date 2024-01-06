package com.example.boardstudy.service;

import com.example.boardstudy.dto.CommentDTO;
import com.example.boardstudy.entity.BoardEntity;
import com.example.boardstudy.entity.CommentEntity;
import com.example.boardstudy.repository.BoardRepository;
import com.example.boardstudy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());

        if (optionalBoardEntity.isPresent()) { // 부모엔티티 조회여부에 따른 로직 처리, 게시글 id 정보값
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);

            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }


}
