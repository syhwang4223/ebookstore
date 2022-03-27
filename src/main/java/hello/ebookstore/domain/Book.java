package hello.ebookstore.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Book {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String isbn;
    private String author;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Category category;

}
