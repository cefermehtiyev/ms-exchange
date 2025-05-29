package com.example.msexchange.service;

import com.example.msexchange.dao.entity.UserEntity;
import com.example.msexchange.dao.repository.UserRepository;
import com.example.msexchange.exception.ErrorMessage;
import com.example.msexchange.exception.NotFoundException;
import com.example.msexchange.exception.UnauthorizedException;
import com.example.msexchange.model.enums.UserRole;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.msexchange.exception.ErrorMessage.USER_ALREADY_EXCEPTION;
import static com.example.msexchange.exception.ErrorMessage.USER_NOT_FOUND;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage(), 404));
        if (!user.getRole().equals(UserRole.ADMIN)){
            throw new UnauthorizedException(ErrorMessage.UNAUTHORIZED_EXCEPTION.getMessage(), 402);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}
