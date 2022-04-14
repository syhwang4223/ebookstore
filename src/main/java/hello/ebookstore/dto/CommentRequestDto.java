package hello.ebookstore.dto;

import hello.ebookstore.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentRequestDto {

    private int star;
    private String content;

    public Comment toComment() {
        return Comment.builder()
                .star(star)
                .content(content)
                .build();
    }

}
