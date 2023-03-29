package com.ulas.service;

import com.ulas.repository.IDislikeRepository;
import com.ulas.repository.ILikeRepository;
import com.ulas.repository.entity.Dislike;
import com.ulas.repository.entity.Like;
import com.ulas.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class DislikeService extends ServiceManager<Dislike,String> {
    private final IDislikeRepository dislikeRepository;

    public DislikeService(IDislikeRepository dislikeRepository) {
        super(dislikeRepository);
        this.dislikeRepository = dislikeRepository;
    }
}
