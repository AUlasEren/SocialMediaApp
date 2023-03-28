package com.ulas.service;

import com.ulas.dto.request.ActivateStatusDto;
import com.ulas.dto.request.NewCreateUSerRequestDto;
import com.ulas.dto.request.UpdateEmailOrUsernameRequestDto;
import com.ulas.dto.request.UserProfileUpdateRequestDto;
import com.ulas.exception.ErrorType;
import com.ulas.exception.UserServiceException;
import com.ulas.manager.IAuthManager;
import com.ulas.mapper.IUserMapper;
import com.ulas.rabbitmq.model.RegisterModel;
import com.ulas.rabbitmq.producer.RegisterProducer;
import com.ulas.repository.IUserProfileRepository;
import com.ulas.repository.entity.UserProfile;
import com.ulas.repository.enums.EStatus;
import com.ulas.utility.JwtTokenManager;
import com.ulas.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository repository;
    private final JwtTokenManager tokenManager;
    private final IAuthManager authManager;
    private final CacheManager cacheManager;
    private final RegisterProducer registerProducer;

    public UserProfileService(IUserProfileRepository repository, JwtTokenManager tokenManager, IAuthManager authManager, CacheManager cacheManager, RegisterProducer registerProducer) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.authManager = authManager;
        this.cacheManager = cacheManager;
        this.registerProducer = registerProducer;
    }

    public Boolean createUser(NewCreateUSerRequestDto dto) {
        try{
            save(IUserMapper.INSTANCE.toUserProfile(dto));
            return true;
        }catch (Exception e){
            throw new UserServiceException(ErrorType.USER_NOT_CREATED);
        }

    }

    public Boolean createUserWithRabbitMq(RegisterModel model) {
        try{
         UserProfile userProfile=save(IUserMapper.INSTANCE.toUserProfile(model));
            registerProducer.sendNewUser(IUserMapper.INSTANCE.toRegisterElasticModel(userProfile));
            return true;
        }catch (Exception e){
            throw new UserServiceException(ErrorType.USER_NOT_CREATED);
        }

    }

    public Boolean activateStatus(String token) {
        Optional<Long> authId = tokenManager.getIdFromToken(token.substring(7));
        if(authId.isEmpty()){
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if(userProfile.isEmpty()){
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }

    public Boolean update(UserProfileUpdateRequestDto dto){
        Optional<Long> authId = tokenManager.getIdFromToken(dto.getToken());
        if(authId.isEmpty()){
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if(userProfile.isEmpty()){
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
        cacheManager.getCache("findbyusername").evict(userProfile.get().getUsername().toLowerCase());
        if (!dto.getUsername().equals(userProfile.get().getUsername())||!dto.getEmail().equals(userProfile.get().getEmail())){
            userProfile.get().setAbout(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
            UpdateEmailOrUsernameRequestDto updateEmailOrUsernameRequest = IUserMapper.INSTANCE.toUpdateEmailOrUsernameRequest(dto);
            updateEmailOrUsernameRequest.setAutId(authId.get());
            authManager.updateEmailOrUsername("Bearer "+dto.getToken(),updateEmailOrUsernameRequest);
        }

        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAvatar(dto.getAvatar());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setAbout(dto.getAbout());
        update(userProfile.get());
        return true;
    }
    public Boolean delete(Long authId){
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId);
        if(userProfile.isEmpty()){
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.DELETED);
        update(userProfile.get());
        return true;
    }
    @Cacheable(value="findbyusername",key = "#username.toLowerCase()")
    public UserProfile findByUsername(String username){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Optional<UserProfile> userProfile = repository.findOptionalByUsernameIgnoreCase(username);
        if(userProfile.isEmpty()){
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
       return userProfile.get();

    }
    @Cacheable(value="findbyrole",key = "#role.toUpperCase()")
    public List<UserProfile> findByRole(String role,String token){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //ResponseEntity<List<Long>> authIds= authManager.findbyRole(role);
        List<Long> authIds= authManager.findbyRole(token,role).getBody();
        return authIds.stream().map(x-> repository.findOptionalByAuthId(x)
                        .orElseThrow(()->{throw new UserServiceException(ErrorType.USER_NOT_FOUND);}))
                .collect(Collectors.toList());

    }

    public Optional<UserProfile> findByAuthId(Long authId) {
        return repository.findOptionalByAuthId(authId);
    }
}
