package org.example.newsfeed.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 멤버와 연결
    @ManyToOne
    private User user;

    @ManyToOne
    private Board board;

    // 댓글 내용
    @NotBlank(message = "댓글 내용이 비어있습니다")
    private String comments;

    public Comment(User user, Board board,String comments){
        this.user = user;
        this.board = board;
        this.comments = comments;
    }

    public void updateComment(String comments){
        this.comments = comments;
    }
}
