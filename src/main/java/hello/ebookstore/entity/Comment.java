package hello.ebookstore.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private String content;

    private LocalDateTime writeDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;


    /**
     * 별점, 공감, 자식 코멘트는 부모 코멘트 객체만 가지고 있음
     */

    private Integer star;

    @Column(name = "likes")
    private int like;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();


    public static Comment createComment(String content, int star, Member writer, Book book) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setStar(star);
        comment.setWriter(writer);
        comment.setBook(book);
        comment.setLike(0);
        comment.writeDateTime = LocalDateTime.now();

        book.addComment(comment);

        return comment;
    }



}
