package hello.ebookstore.repository;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByCategory(Category category);

    List<Book> findTop10ByOrderByCumulativeSalesDesc();

}
