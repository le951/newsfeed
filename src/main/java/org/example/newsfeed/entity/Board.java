package org.example.newsfeed.entity;


import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

    @Setter
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList;

    public Board(String title, String contents){
        this.title = title;
        this.contents = contents;
    }

    public void updateBoard(String title, String contents){
        this.title = title;
        this.contents = contents;
    }


}