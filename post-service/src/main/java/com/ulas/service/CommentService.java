package com.ulas.service;

import com.ulas.repository.ICommentRepository;
import com.ulas.repository.IPostRepository;
import com.ulas.repository.entity.Comment;
import com.ulas.repository.entity.Post;
import com.ulas.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ServiceManager<Comment,String> {
    private final ICommentRepository commentRepository;

    public CommentService(ICommentRepository commentRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
    }
}
