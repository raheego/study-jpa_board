package com.example.boardstudy.controller;

import com.example.boardstudy.dto.BoardDTO;
import com.example.boardstudy.service.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public String findById(@PathVariable Long id, Model model) {
        // 게시글상세를 누라면 조회수 + 1 ,  detail.html 출력
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id); //게시글 id정보를 찾아야 한다.service에서 id를 받고 dto로 전달해야하니까 dto로 담는다.
        model.addAttribute("board", boardDTO); // "board"라는 이름으로 boardDTO를 모델에 추가

        return "detail";
    }

    //글수정
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);

        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
       BoardDTO board = boardService.update(boardDTO);
       model.addAttribute("board", board);

       return "detail ";
    }

    //삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/";
    }

    //페이징넘버
    // /board/paging?page=3
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1)Pageable pageable, Model model) {
//        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable); //이는 Page가 BoardDTO 객체의 컬렉션을 보유할 것임

        int blockLimit = 3; // 한번에 보여지는 페이지번호 개수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";

    }
}









