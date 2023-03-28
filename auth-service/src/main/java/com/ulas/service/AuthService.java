package com.ulas.service;

import com.ulas.dto.request.*;
import com.ulas.dto.response.RegisterResponseDto;
import com.ulas.exception.AuthServiceException;
import com.ulas.exception.ErrorType;
import com.ulas.manager.IUserManager;
import com.ulas.mapper.IAuthMapper;
import com.ulas.rabbitmq.producer.RegisterMailProducer;
import com.ulas.rabbitmq.producer.RegisterProducer;
import com.ulas.repository.IAuthRepository;
import com.ulas.repository.entity.Auth;
import com.ulas.repository.enums.ERoles;
import com.ulas.repository.enums.EStatus;
import com.ulas.utility.CodeGenerator;
import com.ulas.utility.JwtTokenManager;
import com.ulas.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository repository;
    private final IUserManager userManager;
    private final JwtTokenManager tokenManager;
    private final CacheManager cacheManager;
    private final RegisterProducer registerProducer;
    private final RegisterMailProducer mailProducer;

    public AuthService(IAuthRepository repository, IUserManager userManager, JwtTokenManager tokenManager, CacheManager cacheManager, RegisterProducer registerProducer, RegisterMailProducer mailProducer) {
        super(repository);
        this.repository = repository;
        this.userManager= userManager;
        this.tokenManager = tokenManager;
        this.cacheManager = cacheManager;
        this.registerProducer = registerProducer;
        this.mailProducer = mailProducer;
    }
    @Transactional
    public RegisterResponseDto register(RegisterRequestDto dto){
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
                try{
                    save(auth);
                    userManager.createUser(IAuthMapper.INSTANCE.toNewCreateUSerRequestDto(auth));
                    cacheManager.getCache("finbyrole").evict(auth.getRole().toString().toUpperCase());
                }catch (Exception e){

                  //  delete(auth);
                    throw new AuthServiceException(ErrorType.USER_NOT_CREATED);
                }

        return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
    }
    @Transactional
    public RegisterResponseDto registerwithRabbitMq(RegisterRequestDto dto){
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        auth.setActivationCode(CodeGenerator.generateCode());
        try{
            save(auth);
           //rabbit mq ile haberleşme sağlanacak
            registerProducer.sendNewUser(IAuthMapper.INSTANCE.toRegisterModel(auth));
            mailProducer.sendNewMail(IAuthMapper.INSTANCE.toRegisterMailModel(auth));
            cacheManager.getCache("finbyrole").evict(auth.getRole().toString().toUpperCase());
        }catch (Exception e){

            //  delete(auth);
            throw new AuthServiceException(ErrorType.USER_NOT_CREATED);
        }

        return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
    }

    public String doLogin(DoLoginRequestDto dto) {
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(auth.isEmpty()){
           throw new AuthServiceException(ErrorType.LOGIN_ERROR);
        }
        if(!auth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthServiceException(ErrorType.NOT_ACTIVATED_ACCOUNT);
        }
            return tokenManager.createToken(auth.get().getId(),auth.get().getRole())
                    .orElseThrow(()->{throw new AuthServiceException(ErrorType.TOKEN_NOT_CREATED);});
        }

    public Boolean activateStatus(ActivateRequestDto dto) {
        Optional<Auth> auth = findById(dto.getId());
        if (auth.isEmpty()) {
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }
        if (dto.getActivationCode().equals(auth.get().getActivationCode())) {
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            //user service e istek atılacak
            String token = tokenManager.createToken(auth.get().getId(),auth.get().getRole()).get();
                    userManager.activateStatus("Bearer "+token);
            return true;
        } else {
            throw new AuthServiceException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public Boolean updateEmailOrUsername(UpdateEmailOrUsernameRequestDto dto) {
        Optional<Auth> auth = repository.findById(dto.getAutId());
        if(auth.isEmpty()){
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
        update(auth.get());
        return true;
    }

    @Transactional
    public boolean delete(Long id){
        Optional<Auth> auth = findById(id);
        if(auth.isEmpty()){
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setStatus(EStatus.DELETED);
        update(auth.get());
        userManager.delete(id);
        return true;
    }

    public List<Long> findByRole(String role) {
        ERoles myrole;
        try{
            myrole = ERoles.valueOf(role.toUpperCase(Locale.ENGLISH));
        }catch (Exception e){
            throw new AuthServiceException(ErrorType.ROLE_NOT_FOUND);
        }
        return repository.findAllByRole(myrole).stream().map(x->x.getId()).collect(Collectors.toList());
    }
}

