package hello.ebookstore.service;

import hello.ebookstore.entity.Authority;
import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
import hello.ebookstore.entity.Member;
import hello.ebookstore.exception.BadRequestException;
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
        assertThrows(BadRequestException.class, () ->{
            commentService.addParentComment("같은 책에 댓글 또 달기", 1, member, book);
        });
    }
    
    @Test
    public void 직접_작성한_댓글만_수정가능() throws Exception{
        // given
        String content = "잘읽었습니다";
        int star = 4;
        Book book = bookService.findOne(1L);
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);

        em.persist(book);
        em.persist(member);

        Long commentId = commentService.addParentComment(content, star, member, book);


        // when

        // then
        assertThrows(BadRequestException.class, () -> {
            commentService.updateComment(commentId, new Member("2222", "2222", "asasdf@asdf", Authority.ROLE_USER), "수정시도", 3);
        });
    }
    @Test
    public void 댓글수정() throws Exception{
        // given
        String content = "잘읽었습니다";
        int star = 4;
        Book book = bookService.findOne(1L);
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);

        em.persist(book);
        em.persist(member);

        Long commentId = commentService.addParentComment(content, star, member, book);
        int totalStars = book.getTotalStarsSum();

        // when
        commentService.updateComment(commentId, member, "수정한 댓글", 3);

        // then
        Comment updateComment = commentService.findById(commentId);
        assertThat(updateComment.getContent()).isEqualTo("수정한 댓글");
        assertThat(updateComment.getStar()).isEqualTo(3);
        
        // 수정한 별점이 책의 총 별점 수에도 반영되어야 함
        assertThat(book.getTotalStarsSum()).isEqualTo(totalStars - 1);
    }
    
    @Test
    public void 댓글삭제() throws Exception{
        // given
        String content = "잘읽었습니다";
        int star = 4;
        Book book = bookService.findOne(1L);
        Member member = new Member("testMember", "asdf", "sadf@asfd", Authority.ROLE_USER);

        em.persist(book);
        em.persist(member);

        Long commentId = commentService.addParentComment(content, star, member, book);
        int totalStars = book.getTotalStarsSum();
        int totalRatedCount = book.getTotalRatedCount();

        // when
        commentService.deleteComment(commentId, member);
        
        // then
        // 별점 수, 평가 수 감소
        assertThat(book.getTotalStarsSum()).isEqualTo(totalStars - 4);
        assertThat(book.getTotalRatedCount()).isEqualTo(totalRatedCount - 1);

    }

}