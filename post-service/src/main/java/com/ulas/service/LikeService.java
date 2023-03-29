package com.ulas.service;

import com.ulas.repository.ICommentRepository;
import com.ulas.repository.ILikeRepository;
import com.ulas.repository.entity.Comment;
import com.ulas.repository.entity.Like;
import com.ulas.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends ServiceManager<Like,String> {
    private final ILikeRepository likeRepository;

    public LikeService(ILikeRepository likeRepository) {
        super(likeRepository);
        this.likeRepository = likeRepository;
    }
}
