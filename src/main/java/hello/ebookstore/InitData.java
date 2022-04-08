package hello.ebookstore;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.Category;
import hello.ebookstore.repository.CategoryRepository;
import hello.ebookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitData {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    /**
     * 책, 카테고리 데이터 추가
     */
    @PostConstruct
    public void init() {

        Category category1 = new Category("romance");
        Category category2 = new Category("romance-fantasy");
        Category category3 = new Category("fantasy");
        Category category4 = new Category("bl");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);

        Book book1 = Book.builder()
                .title("해리포터와 마법의 돌")
                .author("J.K.롤링")
                .price(10000)
                .publicationDate(LocalDate.of(1997, 6,26))
                .publisher("문학수첩")
                .isbn("9781781106303")
                .imgUrl("https://ww.namu.la/s/86a3fb2653e762f585b9c0ba596ac65b948a772f00af58144844c11cf61f948f07d0669c91c0b8bceb1f3f717c8d3caa07e11189c5a9aefc6f0fc0338ff942dcdc9f7a0aed522e761a42586b8fab2e8a12cb32be8104b40052d2118e5e021c010b067cb705298dc29ed1366c887e59ab")
                .category(category3)
                .build();


        Book book2 = Book.builder()
                .title("눈물을 마시는 새")
                .author("이영도")
                .price(15000)
                .publicationDate(LocalDate.of(2003, 2,27))
                .publisher("황금가지")
                .isbn("9788960176003")
                .imgUrl("https://img.ridicdn.net/cover/682000457/xxlarge#1")
                .category(category3)
                .build();


        bookService.createBook(book1);
        bookService.createBook(book2);
    }

}
