package com.clone.marketcurly.model;

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
public class Comment extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Comment(String comment, Long productId, User user){
        this.comment=comment;
        this.productId=productId;
        this.user=user;
    }

    public void update(String comment){
        this.comment=comment;
    }


}
