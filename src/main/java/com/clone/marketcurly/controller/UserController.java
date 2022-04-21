package com.clone.marketcurly.controller;

import com.clone.marketcurly.dto.userDto.IsDuplicatedRequestDto;
import com.clone.marketcurly.dto.userDto.LoginCheckDto;
import com.clone.marketcurly.dto.userDto.SignupRequestDto;
import com.clone.marketcurly.model.User;
import com.clone.marketcurly.security.UserDetailsImpl;
import com.clone.marketcurly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /*회원가입(유효성 검사포함)*/
    @PostMapping("/api/user/signup")
    public ResponseEntity<User> register(@RequestBody SignupRequestDto signupRequestDto){

        User user=userService.register(signupRequestDto);
        return ResponseEntity.ok(user);
    }

    /*아이디 중복검사*/
    @PostMapping("/api/user/dupliChk")
    public ResponseEntity<Object> isDuplicated(@RequestBody IsDuplicatedRequestDto isDuplicatedRequestDto){

        ResponseEntity<Object> responseEntity=userService.isDuplicated(isDuplicatedRequestDto.getUsername());

        //중복 아이디이면 "BAD_REQUEST" , 사용가능하면 "OK"
        return ResponseEntity.ok(responseEntity.getStatusCode());
    }

    /*로그인 체크(토큰검증)*/
    @GetMapping("/api/user/loginCheck")
    public ResponseEntity<LoginCheckDto> loginCheck(@AuthenticationPrincipal UserDetailsImpl userDetails){

        //헤더 부분
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));

        //http status  코드 도출
        ResponseEntity<Object> result=userService.loginCheck(userDetails);

        //body 부분 (LoginCheckDto)
        User user=userDetails.getUser();
        LoginCheckDto loginCheckDto = new LoginCheckDto(user.getId(), user.getUsername(), user.getNickname(), user.getAddress());

        System.out.println(loginCheckDto.getUsername());
        return new ResponseEntity<LoginCheckDto>(loginCheckDto,headers,HttpStatus.valueOf(result.getStatusCodeValue()));
    }
}
