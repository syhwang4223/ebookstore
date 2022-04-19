package hello.ebookstore.service;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
import hello.ebookstore.entity.Member;
import hello.ebookstore.exception.BadRequestException;
import hello.ebookstore.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment findById(Long commentId) {
        return commentRepository.findOne(commentId).orElseThrow(() -> new BadRequestException("존재하지 않는 댓글입니다. commentId = " + commentId));
    }

    public List<Comment> getParentComments(Book book) {
        return commentRepository.findAllParent(book.getId());
    }

    public List<Comment> getChildrenComments(Comment comment) {
        return commentRepository.findChildrenByParent(comment);
    }

    @Transactional
    public Long addParentComment(String content, int star, Member writer, Book book) {
        if (commentRepository.findByBookIdMemberId(book, writer).isPresent()) {
            throw new BadRequestException("평점은 한 책에 한 번 씩만 남길 수 있습니다.");
        }
        Comment comment = Comment.createComment(content, star, writer, book);
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public Long addChildrenComment(Comment parent, String content, int star, Member writer, Book book) {
        Comment comment = Comment.createComment(content, star, writer, book);
        parent.addChildren(comment);

        commentRepository.save(comment);
        return comment.getId();
    }
}
