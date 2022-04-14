package hello.ebookstore.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
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

    @Column(name = "likes")
    private int like;

//    private int downloadCount;

    @Builder
    public Book (String title, String author, Integer price, String isbn,
                 LocalDate publicationDate, String publisher, String imgUrl,
                 Category category) {
        this.title = title;
        this.isbn = isbn;
        this.imgUrl = imgUrl;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.publicationDate = publicationDate;
        this.category = category;
        like = 0;
//        downloadCount = 0;

    }

    protected Book(){}


}
