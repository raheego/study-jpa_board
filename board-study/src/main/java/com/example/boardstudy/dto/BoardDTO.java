package com.example.boardstudy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO { //data 전 , VO , Bean
    private Long id;
    private String boardWriter; //작성자
    private String boardPass; //비밀번호
    private String boardTitle;
    private String boardContents;
    private int boardHits; //조회수
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
}
