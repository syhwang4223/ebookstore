package hello.ebookstore.controller;

import hello.ebookstore.domain.Category;
import hello.ebookstore.dto.BookResponseDto;
import hello.ebookstore.exception.InvalidRequestException;
import hello.ebookstore.repository.CategoryRepository;
import hello.ebookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<BookResponseDto> getAllBooks(@RequestParam(required = false, name = "category") String categoryName) {

        if (categoryName != null) {
            Category category = categoryRepository.findByName(categoryName)
                    .orElseThrow(() -> new InvalidRequestException("존재하지 않는 카테고리입니다."));
            return bookService.findByCategory(category).stream()
                    .map(BookResponseDto::new)
                    .collect(Collectors.toList());
//            return bookService.findByCategory(category.orElseThrow(() -> new InvalidRequestException("존재하지 않는 카테고리입니다")));
        } else {
            return bookService.findBooks().stream()
                    .map(BookResponseDto::new)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable("bookId") Long bookId) {
        BookResponseDto bookResponseDto = new BookResponseDto(bookService.findOne(bookId));

        return ResponseEntity.ok(bookResponseDto);
    }

}