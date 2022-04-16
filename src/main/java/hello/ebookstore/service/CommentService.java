package hello.ebookstore.service;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
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

    @Transactional
    public void addComment(Comment comment) {


        commentRepository.save(comment);
    }

    public List<Comment> getParentComments(Book book) {
        return commentRepository.findAllParent(book.getId());
    }

    public List<Comment> getChildrenComments(Comment comment) {
        return commentRepository.findChildrenByParent(comment);
    }
}
