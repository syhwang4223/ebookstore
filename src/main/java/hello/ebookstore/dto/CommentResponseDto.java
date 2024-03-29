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
    private Long writerId;
    private String content;
    
    // 대댓글에는 없음
    private Integer star;
    private int like;
    private int childrenCount;

    private LocalDateTime writeDateTime;

    public CommentResponseDto(Comment comment) {
        commentId = comment.getId();
        writer = comment.getWriter().getLoginId();
        writerId = comment.getWriter().getId();
        content = comment.getContent();

        // 대댓글에는 없음
        star = comment.getStar();
        like = comment.getLike();
        childrenCount = comment.getChildren().size();
        writeDateTime = comment.getCreatedDateTime();
    }
}
