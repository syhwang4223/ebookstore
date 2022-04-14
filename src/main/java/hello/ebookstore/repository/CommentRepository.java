package hello.ebookstore.repository;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    // 부모 코멘트 목록 조회
    public List<Comment> findAllParent(Long bookId) {
         return em.createQuery("select c from Comment c join c.book b where b.id = :bookId" +
                                 " and c.parent is null" +
                                 " order by c.writeDateTime desc"
                         , Comment.class)
                 .setParameter("bookId", bookId)
                 .getResultList();
    }
    

}
