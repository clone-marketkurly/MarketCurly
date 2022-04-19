package com.clone.marketcurly.exception.service;

import com.clone.marketcurly.dto.userDto.SignupRequestDto;
import com.clone.marketcurly.model.User;
import com.clone.marketcurly.repository.UserRepository;
import com.clone.marketcurly.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /*회원가입(유효성 검사 진행)*/
    public User register(SignupRequestDto signupRequestDto) {

        String username=signupRequestDto.getUsername();
        String password=signupRequestDto.getPassword();

        //아이디 중복 검사
        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
        //아이디 유효성 검사
        if(!username.matches("^[a-z]{1}[a-z0-9]{5,14}$")){
            throw new IllegalArgumentException("아이디는 6 ~15 자 소문자 영어로 시작, 영어 소문자와 숫자 최소 한가지씩 조합해야합니다");
        }
        //비밀번호 유효성 검사
        if(!password.matches("^(?=.*[a-zA-Z])((?=.*\\d)(?=.*\\W)).{8,16}$")){
            throw new IllegalArgumentException("비밀번호는 8 ~ 16자 영대소문자, 숫자, 특수문자를 최소 한가지씩 조합해야합니다");
        }

        password=passwordEncoder.encode(password);
        signupRequestDto.setPassword(password);
        User user= new User(signupRequestDto);
        userRepository.save(user);
        return user;
    }

    /*아이디 중복검사*/
    public ResponseEntity<Object> isDuplicated(String username) {

        boolean result=userRepository.existsByUsername(username);

        if(result){
            //이미 동일 아이디 존재시
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            //사용 가능한 아이디 일시
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> loginCheck(UserDetailsImpl userDetails) {

        boolean result=userDetails.isEnabled();
        if(result){
            //유효할 시
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            //유호하지 않을 시
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
