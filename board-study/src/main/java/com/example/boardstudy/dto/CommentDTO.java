package com.example.boardstudy.dto;

import com.example.boardstudy.entity.CommentEntity;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId; //게시글 id
    private LocalDateTime commentCreatedTime;

    //commentservice 참고
    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long boardId) { //엔티티를 dto로 변환
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
        commentDTO.setBoardId(boardId);

        return commentDTO;
    }

}