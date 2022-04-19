package hello.ebookstore.controller;

import hello.ebookstore.dto.CommentRequestDto;
import hello.ebookstore.dto.CommentResponseDto;
import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
import hello.ebookstore.entity.Member;
import hello.ebookstore.jwt.UserAdapter;
import hello.ebookstore.service.BookService;
import hello.ebookstore.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
    public CommentResponseDto addParentComment(@AuthenticationPrincipal UserAdapter adapter,
                                               @PathVariable("bookId") Long bookId,
                                               @RequestBody CommentRequestDto commentRequestDto) {
        Book book = bookService.findOne(bookId);
        System.out.println("book.getTotalStarsSum() = " + book.getTotalStarsSum());
        Long commentId = commentService.addParentComment(commentRequestDto.getContent(), commentRequestDto.getStar(), adapter.getMember(), book);
        Comment comment = commentService.findById(commentId);
        System.out.println("book.getTotalStarsSum() = " + book.getTotalStarsSum());

        return new CommentResponseDto(comment);
    }

    @GetMapping("/{bookId}/{commentId}")
    public CommentListDto getChildrenComments(@PathVariable("bookId") Long bookId, @PathVariable("commentId") Long commentId) {
        Comment comment = commentService.findById(commentId);

        List<CommentResponseDto> comments = commentService.getChildrenComments(comment).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new CommentListDto(comments);

    }


    @Getter
    private static class CommentListDto {

        private int totalCount;
        private List<CommentResponseDto> comments = new ArrayList<>();

        public CommentListDto(List<CommentResponseDto> comments) {
            totalCount = comments.size();
            this.comments = comments;
        }
    }
}
