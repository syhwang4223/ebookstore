package hello.ebookstore.dto;

import hello.ebookstore.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String writer;
    private String content;
    private int star;
    private int like;

    private LocalDateTime writeDateTime;

    public CommentResponseDto(Comment comment) {
        commentId = comment.getId();
        writer = comment.getWriter().getLoginId();
        content = comment.getContent();
        star = comment.getStar();
        like = comment.getLike();
        writeDateTime = comment.getWriteDateTime();
    }
}
