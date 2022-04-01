package hello.ebookstore.service;

import hello.ebookstore.domain.Category;
import hello.ebookstore.exception.NoSuchCategoryException;
import hello.ebookstore.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired CategoryService categoryService;
    @Autowired CategoryRepository categoryRepository;
    @Autowired EntityManager em;

    @Test
    public void 카테고리_등록() {
        // given
        Category category = Category.createCategory("SF");

        // when
        Long savedId = categoryService.saveCategory(category);

        // then
        em.flush();
        assertThat(category).isEqualTo(categoryService.findOne(savedId));

    }

    @Test
    public void 중복카테고리_예외() throws Exception{
        // given
        Category category1 = Category.createCategory("SF");
        Category category2 = Category.createCategory("SF");

        // when
        categoryService.saveCategory(category1);

        // then
        assertThrows(IllegalStateException.class, () -> {
            categoryService.saveCategory(category2);
        });
    }
}