package com.clone.marketcurly.model;

import com.clone.marketcurly.dto.userDto.SignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String address;

    public User(SignupRequestDto signupRequestDto){
        this.username=signupRequestDto.getUsername();
        this.nickname=signupRequestDto.getNickname();
        this.password=signupRequestDto.getPassword();
        this.address=signupRequestDto.getAddress();
    }


}
