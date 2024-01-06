package com.example.boardstudy.dto;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

}