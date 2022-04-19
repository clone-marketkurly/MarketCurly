package com.clone.marketcurly.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String nickname;
    private String address;
}
