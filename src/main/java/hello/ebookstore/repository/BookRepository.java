package hello.ebookstore.repository;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final EntityManager em;

    public void save(Book book) {
        em.persist(book);
    }

    public Optional<Book> findOne(Long id) {
        return Optional.of(em.find(Book.class, id));
    }

    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    public List<Book> findByCategory(Category category) {
        return em.createQuery("select b from Book b join b.category c where c.id =: categoryId", Book.class)
                .setParameter("categoryId", category.getId())
                .getResultList();
    }

}
