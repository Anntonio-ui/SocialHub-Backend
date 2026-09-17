package sv.edu.udb.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sv.edu.udb.controller.request.PostCommentRequest;
import sv.edu.udb.controller.response.PostCommentResponse;
import sv.edu.udb.domain.Post;
import sv.edu.udb.domain.PostComment;
import sv.edu.udb.repository.PostCommentRepository;
import sv.edu.udb.repository.PostRepository;
import sv.edu.udb.service.PostCommentService;
import sv.edu.udb.service.mapper.PostCommentMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {

    @NonNull
    private final PostCommentRepository postCommentRepository;

    @NonNull
    private final PostRepository postRepository;

    @NonNull
    private final PostCommentMapper postCommentMapper;

    @Override
    public List<PostCommentResponse> findAllByPostId(Long postId) {

        findPostById(postId);

        return postCommentMapper.toPostCommentResponseList(
                postCommentRepository.findByPostId(postId)
        );
    }

    @Override
    public PostCommentResponse findById(Long postId, Long commentId) {

        findPostById(postId);

        PostComment comment = findCommentById(commentId);

        validateCommentBelongsToPost(comment, postId);

        return postCommentMapper.toPostCommentResponse(comment);
    }

    @Override
    public PostCommentResponse save(
            Long postId,
            PostCommentRequest request
    ) {

        Post post = findPostById(postId);

        PostComment comment =
                postCommentMapper.toPostComment(request);

        comment.setPost(post);

        PostComment savedComment =
                postCommentRepository.save(comment);

        return postCommentMapper.toPostCommentResponse(savedComment);
    }

    @Override
    public PostCommentResponse update(
            Long postId,
            Long commentId,
            PostCommentRequest request
    ) {

        findPostById(postId);

        PostComment comment =
                findCommentById(commentId);

        validateCommentBelongsToPost(comment, postId);

        comment.setUsername(request.getUsername());
        comment.setReview(request.getReview());

        PostComment updatedComment =
                postCommentRepository.save(comment);

        return postCommentMapper.toPostCommentResponse(updatedComment);
    }

    @Override
    public void delete(Long postId, Long commentId) {

        findPostById(postId);

        PostComment comment =
                findCommentById(commentId);

        validateCommentBelongsToPost(comment, postId);

        postCommentRepository.delete(comment);
    }

    private Post findPostById(Long postId) {

        return postRepository.findById(postId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Post not found id " + postId
                        )
                );
    }

    private PostComment findCommentById(Long commentId) {

        return postCommentRepository.findById(commentId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Comment not found id " + commentId
                        )
                );
    }

    private void validateCommentBelongsToPost(
            PostComment comment,
            Long postId
    ) {

        if (comment.getPost() == null ||
                !comment.getPost().getId().equals(postId)) {

            throw new EntityNotFoundException(
                    "Comment not found for post id " + postId
            );
        }
    }
}