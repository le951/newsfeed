package org.example.newsfeed.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    // 팔로우 받는 사람
    private User toUser;
    // private User follower;

    @ManyToOne
    // 팔로우 하는 사람
    private User fromUser;
    // private User following;

    public Follow(User toUser, User fromUser) {
        this.toUser = toUser;
        this.fromUser = fromUser;
    }
}
