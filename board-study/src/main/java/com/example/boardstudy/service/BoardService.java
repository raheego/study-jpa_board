package com.example.boardstudy.service;

import com.example.boardstudy.dto.BoardDTO;
import com.example.boardstudy.entity.BoardEntity;
import com.example.boardstudy.entity.BoardFileEntity;
import com.example.boardstudy.repository.BoardFileRepository;
import com.example.boardstudy.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    private final BoardFileRepository boardFileRepository;

    //글작성
    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(boardEntity);
        } else {
            // 첨부 파일 있음.
            /*
            *  1. dto에 담긴 파일을 꺼냄
            *  2. 파일의 이름 가져옴
            *  3. 서버 저장용 이름으로 만든다. 내사진.jpg => 839798375892_내사진.jpg
            *  4. 저장 경로 설정
            *  5. 해당 경로에 파일 저장 ( db는 이름만 저장하는 용도 )
            *  6. board_table에 해당 데이터 save처리
            *  7. board_file_table에 해당 데이터 save 처리
            * */

            MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
            String originalFilename = boardFile.getOriginalFilename(); // 2.
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
            String savePath = "C:/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
            boardFile.transferTo(new File(savePath)); // 5.

            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
        }
    }

    //글조회
    public List<BoardDTO> findAll() {
        //entity로 넘겨온 객체를 dto로 변환해야함
        List<BoardEntity> boardEntityList = boardRepository.findAll();
       //객체 생성, 위 entity가 dto로 넘겨받을
        List<BoardDTO> boardDTOList = new ArrayList<>();

        //반복문을 통해서 entity데이터 꺼내와서 dto로 변환을 하고 dtolist에 담음
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity)); //여기 변환
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

        //주어진 ID에 해당하는 게시글을 찾아와서 해당 게시글이 존재하면 DTO로 변환하여 반환하고, 존재하지 않으면 null을 반환하는 기능
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);

            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO); // update entity변환을 위한 메서드 호출
        boardRepository.save(boardEntity);

        return findById(boardDTO.getId()); //위에 있는 findById 호출해서 넘겨줌
    }

    public void delete(Long id) {
        boardRepository.deleteById(id );
    }

    //페이징넘버
    public Page<BoardDTO> paging(Pageable pageable){
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작하기 때문에 실제 사용자가 요청한 페이지에서 -1뺀 값을 줘야함.
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id"))); //id 는 entity 컬럼명 기준

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }
}
