package hello.ebookstore.service;

import hello.ebookstore.entity.Book;
import hello.ebookstore.entity.Comment;
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

    @Transactional
    public void addComment(Comment comment) {


        commentRepository.save(comment);
    }

    public List<Comment> getParentComments(Book book) {
        return commentRepository.findAllParent(book.getId());
    }

}
