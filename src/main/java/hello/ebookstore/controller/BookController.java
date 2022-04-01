package hello.ebookstore.controller;

import hello.ebookstore.dto.BookDTO;
import hello.ebookstore.domain.Book;
import hello.ebookstore.domain.Category;
import hello.ebookstore.repository.CategoryRepository;
import hello.ebookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;

//    @GetMapping
//    public List<Book> getAllBooks(@RequestParam(required = false, name = "category") Long category) {
//
//        if (category != null) {
//            return bookService.findByCategory(categoryRepository.findOne(category));
//        } else {
//            return bookService.findBooks();
//        }
//    }
//
//    @PostMapping
//    public BookDTO createBook(@RequestBody BookDTO dto) {
//        log.debug(dto.toString());
//
//        // 카테고리는 미리 상의해서 정해놓고 거기서만 받는걸로!!!
//        // DB에 없는 카테고리 이름으로 책 등록할 때 예외처리해야 함
//
//        Book book = new Book();
//        Category category = categoryRepository.findByName(dto.getCategoryName());
//        book.createBook(dto.getTitle(), dto.getIsbn(), dto.getImgUrl(), dto.getAuthor(),
//                dto.getPublisher(), dto.getPrice(), dto.getPublicationDate(), category);
//
//        bookService.saveBook(book);
//
//        log.debug(book.toString());
//        return dto;
//    }

//    @GetMapping
//    public List<Book> getAllBooks() {
//        return bookService.findBooks();

//    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable("bookId") Long bookId) {
        return bookService.findOne(bookId);
    }

}
