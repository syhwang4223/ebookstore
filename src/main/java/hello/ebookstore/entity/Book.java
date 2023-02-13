package hello.ebookstore.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

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


    private String imgUrl;
    private String author;
    private String publisher;
    private Integer price;
    private LocalDate publicationDate;

    private int cumulativeSales;
    private int totalStarsSum;
    private int totalRatedCount;


    //== 비즈니스 로직 ==//
    public double getAvgStar() {
        return Math.round((double) totalStarsSum / totalRatedCount * 10) / 10.0;
    }

    public void addStar(int star) {
        totalStarsSum += star;
        totalRatedCount += 1;
    }

    public void minusStar(int star) {
        totalStarsSum -= star;
        totalRatedCount -= 1;
    }




}
