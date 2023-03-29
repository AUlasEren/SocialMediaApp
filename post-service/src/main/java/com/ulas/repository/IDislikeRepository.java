package com.ulas.repository;

import com.ulas.repository.entity.Dislike;
import com.ulas.repository.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDislikeRepository extends MongoRepository<Dislike,String> {
}
