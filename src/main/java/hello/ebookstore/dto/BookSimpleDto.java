package hello.ebookstore.dto;

import hello.ebookstore.entity.Book;
import lombok.Getter;

@Getter
public class BookSimpleDto {

    private Long id;

    private String title;
    private String imgUrl;
    private String author;
    private double avgStar;
    private int totalRatedCount; //별점을 매긴 사람 수

    public BookSimpleDto(Book book) {
        id = book.getId();
        title = book.getTitle();
        imgUrl = book.getImgUrl();
        author = book.getAuthor();
        avgStar = book.getAvgStar();
        totalRatedCount = book.getTotalRatedCount();
    }

    public BookSimpleDto() {
    }
}
