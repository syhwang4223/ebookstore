package hello.ebookstore.service;

import hello.ebookstore.entity.Authority;
import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
import hello.ebookstore.entity.Member;
import hello.ebookstore.exception.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired BookService bookService;
    @Autowired EntityManager em;
    
    @Test
    public void 부모댓글작성() throws Exception{
        // given
        String content = "잘읽었습니다";
        int star = 4;
        Book book = bookService.findOne(1L);
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);

        em.persist(book);
        em.persist(member);

        int totalStarsSum = book.getTotalStarsSum();
        int totalRatedCount = book.getTotalRatedCount();
        double avgStar = book.getAvgStar();
        System.out.println("totalRatedCount = " + totalRatedCount);
        System.out.println("totalStarsSum = " + totalStarsSum);
        System.out.println("avgStar = " + avgStar);
        
        // when
        Long commentId = commentService.addParentComment(content, star, member, book);
        Comment comment = commentService.findById(commentId);

        // then
        // 남긴 별점만큼 총 별점 수, 총 평가자 수,  증가해야 함
        // 평균 별점에도 반영되어야 함
        assertThat(book.getTotalStarsSum()).isEqualTo(totalStarsSum + comment.getStar());
        assertThat(book.getTotalRatedCount()).isEqualTo(totalRatedCount + 1);
        assertThat(book.getAvgStar())
                .isEqualTo(Math.round((double) (totalStarsSum + comment.getStar()) / (totalRatedCount+1) * 10) / 10.0);
        
        System.out.println("book.getTotalRatedCount() = " + book.getTotalRatedCount());
        System.out.println("book.getTotalStarsSum() = " + book.getTotalStarsSum());
        System.out.println("book.getAvgStar() = " + book.getAvgStar());
    
    }

    @Test
    public void 같은_책에_두번_평점을_남기면_오류() throws Exception{
        // given
        String content = "잘읽었습니다";
        int star = 4;
        Book book = bookService.findOne(1L);
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);

        em.persist(book);
        em.persist(member);

        Long commentId = commentService.addParentComment(content, star, member, book);
        Comment comment = commentService.findById(commentId);


        // when

        // then
        org.junit.jupiter.api.Assertions.assertThrows(BadRequestException.class, () ->{
            commentService.addParentComment("같은 책에 댓글 또 달기", 1, member, book);
        });


    }
}