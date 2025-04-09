package org.example.newsfeed.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@Entity
@Table(name = "boards")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String contents;

    @ManyToOne
    private User user;

    public Board(String title, String contents){
        this.title = title;
        this.contents = contents;
    }

    public void updateBoard(String title, String contents){
        this.title = title;
        this.contents = contents;
    }


    public void setUser(User user){
        this.user = user;
    }
}