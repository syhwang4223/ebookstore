package hello.ebookstore.dto;


import lombok.Getter;

@Getter
public class CommentRequestDto {

    private int star;
    private String content;

//    public CommentRequestDto(int star, String content) {
//        this.star = star;
//        this.content = content;
//    }
//
//    protected CommentRequestDto() {
//    }
}
