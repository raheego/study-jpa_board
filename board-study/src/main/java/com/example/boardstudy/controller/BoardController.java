package com.example.boardstudy.controller;

import com.example.boardstudy.dto.BoardDTO;
import com.example.boardstudy.service.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService; //생성자주입

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) { //@RequestBody 가능
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }

    //글목록
    @GetMapping("/")
    public String findAll(Model model) { //model : db로 부터 데이터를 가져와야 한다. = 보여질 데이터
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    //글상세조회
    @GetMapping("/{id}")
    public String findId(@PathVariable Long id, Model model) {
        // 게시글상세를 누라면 조회수 + 1 ,  detail.html 출력
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id); //게시글 id정보를 찾아야 한다.
        //service에서 id를 받고 dto로 전달해야하니까 dto로 담는다.

        model.addAttribute("board", boardDTO); // "board"라는 이름으로 boardDTO를 모델에 추가

        return "detail";
    }

}









