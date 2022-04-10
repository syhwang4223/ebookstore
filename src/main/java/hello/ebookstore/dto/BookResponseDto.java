package hello.ebookstore.dto;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.Category;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class BookResponseDto {

    private String title;
    private String isbn;
    private String imgUrl;

    private String category;

    private String author;
    private String publisher;
    private Integer price;

    private LocalDate publicationDate;

    public BookResponseDto(Book book) {
        this.title = book.getTitle();
        this.isbn = book.getIsbn();
        this.imgUrl = book.getImgUrl();

        this.category = book.getCategory().getName();

        this.author = book.getAuthor();
        ;
        this.publisher = book.getPublisher();
        ;
        this.price = book.getPrice();
        this.publicationDate = book.getPublicationDate();
    }
}
