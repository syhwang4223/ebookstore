package hello.ebookstore.dto;

import hello.ebookstore.domain.Book;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class BookDto {

    private String title;
    private String isbn;
    private String imgUrl;

    private String categoryName;

    private String author;
    private String publisher;
    private Integer price;

    private LocalDate publicationDate;

    public BookDto(Book book) {
        this.title = book.getTitle();
        this.isbn = book.getIsbn();
        this.imgUrl = book.getImgUrl();
        this.author = book.getAuthor();;
        this.publisher = book.getPublisher();;
        this.price = book.getPrice();
        this.publicationDate = book.getPublicationDate();
    }

    public static BookDto of(Book book) {
        return new BookDto(book);
    }
}
