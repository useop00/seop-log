package com.seoplog.service.comment;

import com.seoplog.domain.comment.Comment;
import com.seoplog.domain.comment.request.CommentCreate;
import com.seoplog.domain.comment.response.CommentResponse;
import com.seoplog.domain.post.Post;
import com.seoplog.domain.user.User;
import com.seoplog.repository.comment.CommentRepository;
import com.seoplog.service.EntityFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final EntityFinder entityFinder;

    public CommentResponse write(CommentCreate request, String username) {
        User loginUser = entityFinder.getLoginUser(username);
        Post post = entityFinder.getPostId(request.getPostId());

        Comment comment = request.toEntity(loginUser, post);

        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.of(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAllComment(Long postId) {
        return commentRepository.findAllByPostId(postId)
                .stream()
                .map(CommentResponse::of)
                .toList();
    }

    public void updateComment(Long commentId, String content) {
        Comment comment = entityFinder.getCommentId(commentId);
        comment.updateComment(content);

        CommentResponse.of(comment);
    }

    public void deleteComment(Long id) {
        Comment comment = entityFinder.getCommentId(id);
        commentRepository.delete(comment);
    }
}
