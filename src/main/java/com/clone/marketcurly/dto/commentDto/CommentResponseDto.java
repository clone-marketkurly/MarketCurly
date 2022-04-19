package com.clone.marketcurly.dto.commentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentResponseDto {

    private Long userId;
    private Long commentId;
    private String nickname;
    private String comment;
    private LocalDateTime createdAt;
}
