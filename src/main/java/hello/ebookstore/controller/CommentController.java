package hello.ebookstore.controller;

import hello.ebookstore.dto.CommentRequestDto;
import hello.ebookstore.dto.CommentResponseDto;
import hello.ebookstore.entity.Book;
import hello.ebookstore.service.BookService;
import hello.ebookstore.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public CommentListDto getParentComments(@PathVariable("bookId") Long bookId) {
        Book book = bookService.findOne(bookId);
        List<CommentResponseDto> comments = commentService.getParentComments(book).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new CommentListDto(comments);

    }

    @PostMapping("/{bookId}")
    public void addParentComment(@PathVariable("bookId") Long bookId, CommentRequestDto commentRequestDto) {



        Book book = bookService.findOne(bookId);

    }


    @Getter
    private static class CommentListDto {

        private List<CommentResponseDto> comments = new ArrayList<>();
        private int totalCount;
        private float avgStar; // 소수점 한자리까지

        public CommentListDto(List<CommentResponseDto> comments) {
            this.comments = comments;
            totalCount = comments.size();

            if (totalCount > 0) {
                avgStar = Math.round(comments.stream()
                        .mapToDouble(CommentResponseDto::getStar)
                        .average()
                        .getAsDouble() * 10 / 10.0);
            } else {
                avgStar = 0;
            }
        }
    }
}
