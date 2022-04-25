package hello.ebookstore.service;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
import hello.ebookstore.entity.Member;
import hello.ebookstore.exception.BadRequestException;
import hello.ebookstore.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment findById(Long commentId) {
        return commentRepository.findOne(commentId).orElseThrow(() -> new BadRequestException("존재하지 않는 댓글입니다. commentId = " + commentId));
    }

    //== 부모 댓글 ==//
    
    // 조회
    public List<Comment> getParentComments(Book book) {
        return commentRepository.findAllParent(book.getId());
    }

    // 등록
    @Transactional
    public Long addParentComment(String content, int star, Member writer, Book book) {
        if (commentRepository.findByBookIdAndMemberId(book, writer).isPresent()) {
            throw new BadRequestException("평점은 한 책에 한 번 씩만 남길 수 있습니다.");
        }
        Comment comment = Comment.createComment(content, star, writer, book);
        commentRepository.save(comment);
        return comment.getId();
    }

    // 수정
    @Transactional
    public void updateComment(Long commentId, Member writer, String content, int star) {
        Comment findComment = commentRepository.findOne(commentId).orElseThrow(() -> new BadRequestException("존재하지 않는 책입니다"));

        if (!findComment.getWriter().getId().equals(writer.getId())) {
            throw new BadRequestException("자신이 작성한 댓글만 수정할 수 있습니다.");
        } else {
            findComment.update(content, star);
        }
    }

    // 삭제
    @Transactional
    public void deleteComment(Long commentId, Member writer) {
        Comment findComment = commentRepository.findOne(commentId).orElseThrow(() -> new BadRequestException("존재하지 않는 책입니다"));
        Book book = findComment.getBook();

        if (!findComment.getWriter().getId().equals(writer.getId())) {
            throw new BadRequestException("자신이 작성한 댓글만 삭제할 수 있습니다.");
        } else {
            commentRepository.delete(findComment);
            book.minusStar(findComment.getStar());
        }
    }



    //== 자식 댓글 ==//

    public List<Comment> getChildrenComments(Comment comment) {
        return commentRepository.findChildrenByParent(comment);
    }

    @Transactional
    public Long addChildrenComment(Comment parent, String content, int star, Member writer, Book book) {
        Comment comment = Comment.createComment(content, star, writer, book);
        parent.addChildren(comment);

        commentRepository.save(comment);
        return comment.getId();
    }

}
