package com.example.boardstudy.controller;

import com.example.boardstudy.dto.BoardDTO;
import com.example.boardstudy.service.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



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
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }
}









