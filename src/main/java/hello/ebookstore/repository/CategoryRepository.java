package hello.ebookstore.repository;

import hello.ebookstore.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category) {
        em.persist(category);
    }

    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    public Category findOne(Long id) {
        return em.find(Category.class, id);
    }

    public Optional<Category> findByName(String categoryName) {
        return em.createQuery("select c from Category c where c.name = :categoryName", Category.class)
                .setParameter("categoryName", categoryName)
                .getResultList()
                .stream().findAny();
    }

}
