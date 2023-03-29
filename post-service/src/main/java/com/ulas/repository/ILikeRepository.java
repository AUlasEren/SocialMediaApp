package com.ulas.repository;

import com.ulas.repository.entity.Like;
import com.ulas.repository.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends MongoRepository<Like,String> {
}
