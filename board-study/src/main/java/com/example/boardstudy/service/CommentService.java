package com.example.boardstudy.service;

import com.example.boardstudy.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
}
