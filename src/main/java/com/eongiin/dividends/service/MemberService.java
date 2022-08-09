package com.eongiin.dividends.service;

import com.eongiin.dividends.exception.impl.AlreadyExistUserException;
import com.eongiin.dividends.exception.impl.NoUserException;
import com.eongiin.dividends.exception.impl.NotMatchPasswordException;
import com.eongiin.dividends.model.Auth;
import com.eongiin.dividends.persist.MemberRepository;
import com.eongiin.dividends.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(NoUserException::new);

    }

    public MemberEntity register(Auth.SignUp member) {
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if (exists) {
            throw new AlreadyExistUserException();
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        return this.memberRepository.save(member.toEntity());
    }

    public MemberEntity authenticate(Auth.SignIn member) {
        MemberEntity user = this.memberRepository.findByUsername(member.getUsername())
                .orElseThrow(NoUserException::new);

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new NotMatchPasswordException();

        }
        return user;
    }
}
