package hello.ebookstore.dto;

import hello.ebookstore.entity.Book;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class BookDetailDto {

    private Long id;

    private String title;
    private String isbn;
    private String imgUrl;

    private String category;

    private String author;
    private String publisher;
    private Integer price;

    private LocalDate publicationDate;

    public BookDetailDto(Book book) {
        id = book.getId();
        title = book.getTitle();
        isbn = book.getIsbn();
        imgUrl = book.getImgUrl();
        category = book.getCategory().getName();
        author = book.getAuthor();
        publisher = book.getPublisher();
        price = book.getPrice();
        publicationDate = book.getPublicationDate();
    }
}
