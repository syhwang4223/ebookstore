package hello.ebookstore.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    @Column(unique = true)
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

//    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();

    private String imgUrl;
    private String author;
    private String publisher;
    private Integer price;
    private LocalDate publicationDate;

    private int cumulativeSales;
    private int totalStarsSum;
    private int totalRatedCount;


    //== 연관관계 메서드 ==//
    public void addComment(Comment comment) {
//        comments.add(comment);
        totalStarsSum += comment.getStar();
        totalRatedCount += 1;
    }


    //== 비즈니스 로직 ==//
    public double getAvgStar() {
        return Math.round((double) totalStarsSum / totalRatedCount * 10) / 10.0;
    }


}
