package hello.ebookstore.domain;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String imgUrl;
    private String author;
    private String publisher;
    private Integer price;

    private LocalDate publicationDate;

    @Column(name = "likes")
    private Long like;

    public void createBook(String title, String isbn, String imgUrl,
                           String author, String publisher, Integer price,
                           LocalDate publicationDate, Category category) {
        this.title = title;
        this.isbn = isbn;
        this.imgUrl = imgUrl;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.publicationDate = publicationDate;
        this.category = category;
        this.like = 0L;
    }


}
