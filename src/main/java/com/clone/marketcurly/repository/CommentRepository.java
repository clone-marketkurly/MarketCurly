package com.clone.marketcurly.repository;

import com.clone.marketcurly.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /*해당 음식에 대한 리뷰들을 조회할 때 사용*/
    List<Comment> findAllByProductId(Long productId);

    /*리뷰 삭제시 리뷰 게시자와 로그인 유저가 동일한지 체크*/
    Optional<Comment> findByIdAndUserId(Long id, Long userId);
}
