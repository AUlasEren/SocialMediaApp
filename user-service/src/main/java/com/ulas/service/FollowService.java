package com.ulas.service;

import com.ulas.dto.request.CreateFollowRequestDto;
import com.ulas.exception.ErrorType;
import com.ulas.exception.UserServiceException;
import com.ulas.mapper.IFollowMapper;
import com.ulas.repository.IFollowRepository;
import com.ulas.repository.entity.Follow;
import com.ulas.repository.entity.UserProfile;
import com.ulas.utility.JwtTokenManager;
import com.ulas.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FollowService extends ServiceManager {
    private final IFollowRepository iFollowRepository;
    private final JwtTokenManager jwtTokenManager;
    private final UserProfileService userProfileService;

    public FollowService(IFollowRepository iFollowRepository, JwtTokenManager jwtTokenManager, UserProfileService userProfileService) {
        super(iFollowRepository);
        this.iFollowRepository = iFollowRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.userProfileService = userProfileService;
    }
    @Transactional
    public Boolean createFollow(CreateFollowRequestDto dto) {
        Follow follow;
        Optional<Long> authId=jwtTokenManager.getIdFromToken(dto.getToken());
        if(authId.isEmpty()){
            throw new UserServiceException(ErrorType.INVALID_TOKEN);
        }
        Optional<UserProfile> userProfile =  userProfileService.findByAuthId(authId.get());
        Optional<UserProfile> followUser = userProfileService.findById(dto.getFollowId());
        Optional<Follow> followDb = iFollowRepository.findOptionalByUserIdAndFollowId(userProfile.get().getId(),followUser.get().getId());
        if(followDb.isPresent()){
            throw new UserServiceException(ErrorType.FOLLOW_ALREADY_EXIST);
        }
        if(userProfile.isPresent()&&followUser.isPresent()){
            //follow=Follow.builder().userId(userProfile.get().getId()).followId(followUser.get().getId()).build();
            follow= IFollowMapper.INSTANCE.toFollow(dto,userProfile.get().getId());
            follow = (Follow) save(follow);
            userProfile.get().getFollows().add(followUser.get().getId());
            followUser.get().getFollower().add(userProfile.get().getId());
            userProfileService.update(userProfile.get());
            userProfileService.update(followUser.get());
        }else{
            throw new UserServiceException(ErrorType.USER_NOT_FOUND);
        }
        return true;
    }
}
