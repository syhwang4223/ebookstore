package hello.ebookstore.controller;

import hello.ebookstore.dto.CommentRequestDto;
import hello.ebookstore.dto.CommentResponseDto;
import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
import hello.ebookstore.entity.Member;
import hello.ebookstore.exception.ResponseMessage;
import hello.ebookstore.jwt.UserAdapter;
import hello.ebookstore.service.BookService;
import hello.ebookstore.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    
    //== 부모 댓글 ==///
    
    // 한 책에 대한 댓글 조회
    @GetMapping("/{bookId}")
    public CommentListDto getParentComments(@PathVariable("bookId") Long bookId) {

        Book book = bookService.findOne(bookId);

        List<CommentResponseDto> comments = commentService.getParentComments(book).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new CommentListDto(comments);

    }

    // 댓글 달기
    @PostMapping("/{bookId}")
    public ResponseEntity<CommentResponseDto> addParentComment(@AuthenticationPrincipal UserAdapter adapter,
                                                              @PathVariable("bookId") Long bookId,
                                                              @RequestBody CommentRequestDto commentRequestDto) {
        log.debug("star = {}, content = {}", commentRequestDto.getStar(), commentRequestDto.getContent());
        Book book = bookService.findOne(bookId);
        Long savedId = commentService.addParentComment(commentRequestDto.getContent(), commentRequestDto.getStar(), adapter.getMember(), book);

        return new ResponseEntity<>(new CommentResponseDto(commentService.findById(savedId)), HttpStatus.CREATED);
    }

    // 댓글 수정
    @PatchMapping("/{bookId}/{commentId}")
    public CommentResponseDto updateParentComment(@AuthenticationPrincipal UserAdapter adapter,
                                                  @PathVariable("bookId") Long bookId, @PathVariable("commentId") Long commentId,
                                                  @RequestBody CommentRequestDto commentRequestDto) {

        commentService.updateComment(commentId, adapter.getMember(), commentRequestDto.getContent(), commentRequestDto.getStar());

        return new CommentResponseDto(commentService.findById(commentId));
    }

    // 댓글 삭제
    @DeleteMapping("/{bookId}/{commentId}")
    public ResponseMessage deleteParentComment(@AuthenticationPrincipal UserAdapter adapter,
                                               @PathVariable("bookId") Long bookId, @PathVariable("commentId") Long commentId) {

        commentService.deleteComment(commentId, adapter.getMember());
        return new ResponseMessage("댓글이 삭제되었습니다");
    }


    //== 자식 댓글 ==///

    // 한 댓글의 대댓글 조회
    @GetMapping("/{bookId}/{commentId}")
    public CommentListDto getChildrenComments(@PathVariable("bookId") Long bookId, @PathVariable("commentId") Long commentId) {
        Comment comment = commentService.findById(commentId);

        List<CommentResponseDto> comments = commentService.getChildrenComments(comment).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new CommentListDto(comments);
    }

    // 대댓글 달기
    @PostMapping("/{bookId}/{commentId}")
    public CommentResponseDto addChildrenComment(@AuthenticationPrincipal UserAdapter adapter,
                                                 @PathVariable("bookId") Long bookId,
                                                 @PathVariable("commentId") Long commentId,
                                                 @RequestBody CommentRequestDto commentRequestDto) {
        Comment parent = commentService.findById(commentId);
        Member writer = adapter.getMember();
        Book book = bookService.findOne(bookId);

        Long savedId = commentService.addChildrenComment(parent, commentRequestDto.getContent(), commentRequestDto.getStar(), writer, book);

        return new CommentResponseDto(commentService.findById(savedId));

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
