package com.ulas.service;

import com.ulas.dto.request.CreateNewPostRequestDto;
import com.ulas.mapper.IPostMapper;
import com.ulas.repository.IPostRepository;
import com.ulas.repository.entity.Post;
import com.ulas.utility.ServiceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService extends ServiceManager<Post,String> {
    private final IPostRepository postRepository;

    public PostService(IPostRepository postRepository) {
        super(postRepository);
        this.postRepository = postRepository;
    }

    public Post createPost(CreateNewPostRequestDto dto) {
        return save(IPostMapper.INSTANCE.toPost(dto));
    }
}
