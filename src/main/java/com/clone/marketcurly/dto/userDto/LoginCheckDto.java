package com.clone.marketcurly.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginCheckDto {

    private Long userId;
    private String username;
    private String nickname;
    private String address;

}
