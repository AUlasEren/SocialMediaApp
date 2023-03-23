package com.ulas.service;

import com.ulas.exception.ErrorType;
import com.ulas.exception.UserServiceException;
import com.ulas.mapper.IElasticMapper;
import com.ulas.rabbitmq.model.RegisterElasticModel;
import com.ulas.repository.IUserProfileRepository;
import com.ulas.repository.entity.UserProfile;
import com.ulas.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {
    private final IUserProfileRepository repository;



    public UserProfileService(IUserProfileRepository repository) {
        super(repository);
        this.repository = repository;
    }
    public UserProfile createUserWithRabbitMq(RegisterElasticModel model) {
        try{
            return save(IElasticMapper.INSTANCE.toUserProfile(model));
        }catch (Exception e){
            throw new UserServiceException(ErrorType.USER_NOT_CREATED);
        }

    }

}
