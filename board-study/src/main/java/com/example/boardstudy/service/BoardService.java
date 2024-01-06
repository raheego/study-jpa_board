package com.example.boardstudy.service;

import com.example.boardstudy.dto.BoardDTO;
import com.example.boardstudy.entity.BoardEntity;
import com.example.boardstudy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


// service 역할
// dto -> entity 로 변환을  하거나 (entity class에서)
// entity -> dto 로 변환을 하거나 (dto class에서)
// 컨트롤로부터 호출을 받을 때는 dto로 넘겨받는데  reopository는 entity로
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository; //db
    public void save(BoardDTO boardDTO) {
        BoardEntity saveEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(saveEntity);
    }
}
