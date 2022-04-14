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

    //== 연관관계 메서드 ==//
    public void addChildrenComment(Comment comment) {
        children.add(comment);
        comment.setParent(this);
    }

    @Builder
    public Comment(Member writer, String content, int star) {
        this.writer = writer;
        this.content = content;
        this.star = star;
        like = 0;
        writeDateTime = LocalDateTime.now();
    }

}
