package hello.ebookstore.controller;

import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.CartItem;
import hello.ebookstore.domain.Category;
import hello.ebookstore.domain.Member;
import hello.ebookstore.dto.BookDto;
import hello.ebookstore.exception.InvalidRequestException;
import hello.ebookstore.repository.CategoryRepository;
import hello.ebookstore.repository.MemberRepository;
import hello.ebookstore.service.BookService;
import hello.ebookstore.service.MemberService;
import hello.ebookstore.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Book> getAllBooks(@RequestParam(required = false, name = "category") String categoryName) {

        if (categoryName != null) {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseThrow(() -> new InvalidRequestException("존재하지 않는 카테고리입니다."));
            return bookService.findByCategory(category);
//            return bookService.findByCategory(category.orElseThrow(() -> new InvalidRequestException("존재하지 않는 카테고리입니다")));
        } else {
            return bookService.findBooks();
        }
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable("bookId") Long bookId) {
        Book book = bookService.findOne(bookId);
        if (book != null) {
            return bookService.findOne(bookId);
        } else {
            throw new InvalidRequestException("존재하지 않는 책입니다: bookId = " + bookId);
        }
    }

}