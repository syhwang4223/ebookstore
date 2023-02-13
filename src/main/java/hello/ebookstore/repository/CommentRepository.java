package hello.ebookstore.repository;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
import hello.ebookstore.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 부모 코멘트 목록 조회
    List<Comment> findByBookAndParentIsNull(Book book);

    // 한 부모 코멘트에 대한 자식 코멘트 목록 조회
    List<Comment> findByParent(Comment parent);

    // 이미 작성한 댓글이 있는 조회
    Optional<Comment> findByBookAndWriter(Book book, Member writer);

}
