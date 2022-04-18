package hello.ebookstore.dto;

import hello.ebookstore.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private String writer;
    private String content;
    
    // 대댓글에는 없음
    private Integer star;
    private int like;
    private int childrenCount;

    private LocalDateTime writeDateTime;

    public CommentResponseDto(Comment comment) {
        commentId = comment.getId();
        writer = comment.getWriter().getLoginId();
        content = comment.getContent();
        writeDateTime = comment.getWriteDateTime();

        // 대댓글에는 없음
        star = comment.getStar();
        like = comment.getLike();
        childrenCount = comment.getChildren().size();
    }
}
