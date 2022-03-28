package hello.ebookstore.controller;

import hello.ebookstore.domain.Book;
import hello.ebookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        bookService.saveBook(book);
        return book;
    }


    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable("bookId") Long bookId) {
        return bookService.findOne(bookId);
    }
}
