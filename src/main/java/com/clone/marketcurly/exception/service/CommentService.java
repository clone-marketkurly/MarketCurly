package com.clone.marketcurly.exception.service;

import com.clone.marketcurly.dto.commentDto.CommentRequestDto;
import com.clone.marketcurly.dto.commentDto.CommentResponseDto;
import com.clone.marketcurly.model.Comment;
import com.clone.marketcurly.model.User;
import com.clone.marketcurly.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    /*리뷰 저장*/
    public void saveReview(Long productId, CommentRequestDto cartRequestDto, User user){

        String comment=cartRequestDto.getComment();
        Comment cart= new Comment(comment, productId, user);
        commentRepository.save(cart);
    }

    /*상품별 리뷰 조회*/
    public List<CommentResponseDto> showReview(Long productId) {
        //주어진 productId로 해당 게시물에 달린 모든 리뷰 정보 조회
        List<Comment> commentList=commentRepository.findAllByProductId(productId);

        //원하는 형태로 리뷰 정보들을 보내줄 list
        List<CommentResponseDto> commentResult=new ArrayList<>();
        for(Comment comments: commentList){
            Long userId=comments.getUser().getId();
            Long commentId=comments.getId();
            String nickname=comments.getUser().getNickname();
            String comment=comments.getComment();
            LocalDateTime createdAt=comments.getCreatedAt();
            //원하는 정보의 형태로 받아주는 중
            CommentResponseDto commentResponseDto= new CommentResponseDto(
                    userId, commentId, nickname,comment,createdAt);
            commentResult.add(commentResponseDto);
        }
        return commentResult;
    }

    /*리뷰 수정*/
    @Transactional
    public void updateReview(Long commentId, CommentRequestDto commentRequestDto,User user) {

        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()-> new NullPointerException("해당 리뷰가 없습니다.")
        );
        //리뷰 작성자만 수정 가능하게끔 하기 위한 예외처리
        if(comment.getUser().getId()!=user.getId()){
            throw new IllegalArgumentException("리뷰 작성자만 수정이 가능합니다");
        }

        String updateComment=commentRequestDto.getComment();
        comment.update(updateComment);
        commentRepository.save(comment);
    }

    /*리뷰 삭제*/
    public void deleteReview(Long commentId, User user) {
        Long userId=user.getId();
        Comment comment=commentRepository.findByIdAndUserId(commentId, userId).orElseThrow(
                ()->new IllegalArgumentException("리뷰 작성자만 삭제가 가능합니다")
        );
        commentRepository.deleteById(commentId);
    }
}
