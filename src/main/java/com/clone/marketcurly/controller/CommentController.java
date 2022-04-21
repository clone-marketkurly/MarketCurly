package com.clone.marketcurly.controller;

import com.clone.marketcurly.dto.commentDto.CommentRequestDto;
import com.clone.marketcurly.dto.commentDto.CommentResponseDto;
import com.clone.marketcurly.model.User;
import com.clone.marketcurly.security.UserDetailsImpl;
import com.clone.marketcurly.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    /*리뷰 작성*/
    @PostMapping("/api/detail/{productId}/comment")
    public void saveReview(
            @PathVariable Long productId,
            @RequestBody CommentRequestDto cartRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        User user=userDetails.getUser();
        commentService.saveReview(productId, cartRequestDto, user);
    }

    /*리뷰 조회*/
    @GetMapping("/api/detail/{productId}/comment")
    public List<CommentResponseDto> showReview(
            @PathVariable Long productId){

        List<CommentResponseDto> comments=commentService.showReview(productId);
        return comments;
    }

    /*리뷰 수정*/
    @PutMapping("/api/detail/comment/{commentId}")
    public void updateReview(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        User user = userDetails.getUser();
        commentService.updateReview(commentId,commentRequestDto,user);
    }

    /*리뷰 삭제*/
    @DeleteMapping("/api/detail/comment/{commentId}")
    public void deleteReview(@PathVariable Long commentId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user=userDetails.getUser();
        commentService.deleteReview(commentId, user);
    }
}
