package hello.ebookstore.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class BookDTO {

    private String title;
    private String isbn;
    private String imgUrl;

    private String categoryName;

    private String author;
    private String publisher;
    private Integer price;

    private LocalDate publicationDate;
}
