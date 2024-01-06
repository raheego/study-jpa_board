package com.example.boardstudy.service;

import com.example.boardstudy.dto.BoardDTO;
import com.example.boardstudy.entity.BoardEntity;
import com.example.boardstudy.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// service 역할
// dto -> entity 로 변환을  하거나 (entity class에서)
// entity -> dto 로 변환을 하거나 (dto class에서)
// 컨트롤로부터 호출을 받을 때는 dto로 넘겨받는데  reopository는 entity로
@Service
@RequiredArgsConstructor
public class BoardService { //비즈니스 로직

    private final BoardRepository boardRepository; //db

    //글작성
    public void save(BoardDTO boardDTO) {
        BoardEntity saveEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(saveEntity);
    }

    //글조회
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(); //entity로 넘겨온 객체를 dto로
       //객체 생성, 위 entity가 dto로 넘겨받을
        List<BoardDTO> boardDTOList = new ArrayList<>();

        //반복문을 통해서 entity데이터 꺼내와서 dto로 변환을 하고 dtolist에 담음
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    //조회수 증가
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id); //db쿼리부분이니깐 repository에 쿼리를 추가한다.
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);

        //주어진 ID에 해당하는 게시글을 찾아와서 해당 게시글이 존재하면 DTO로 변환하여 반환하고, 존재하지 않으면 null을 반환하는 기능
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            return BoardDTO.toBoardDTO(boardEntity);
        } else {
            return null;
        }
    }

}
