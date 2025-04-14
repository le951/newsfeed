package org.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private Long targetId;

    @Enumerated(EnumType.STRING)
    private LikeType likeTargetType;

    public Like(User user, Long targetId, LikeType likeTargetType){
        this.user = user;
        this.targetId = targetId;
        this.likeTargetType = likeTargetType;
    }
}
