package hello.ebookstore.service;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.Category;
import hello.ebookstore.exception.NoSuchCategoryException;
import hello.ebookstore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired BookRepository bookRepository;
    @Autowired BookService bookService;
    @Autowired CategoryService categoryService;


    @Test
    public void 책등록() throws Exception{
        // given
        Category category = Category.createCategory("판타지");

        Book book = new Book();
        book.createBook("눈마새", "12312312", "","이영도",
                "어딜까", 10000, LocalDate.now(), category);

        // when
        Long saveBook = bookService.saveBook(book);

        // then
        assertEquals(book, bookRepository.findOne(saveBook));

    }
    
    @Test
    public void 존재하지않는_카테고리에_책등록시_오류() throws Exception{
        // given
        Book book = new Book();

        // when
        
        
        // then
        assertThrows(NoSuchCategoryException.class, ()->{
            book.createBook("눈마새", "12312312", "","이영도",
                    "어딜까", 10000, LocalDate.now(),
                    categoryService.findByName("판타지"));
        });
    
    }
}