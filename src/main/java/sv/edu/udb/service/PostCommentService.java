package sv.edu.udb.service;

import sv.edu.udb.controller.request.PostCommentRequest;
import sv.edu.udb.controller.response.PostCommentResponse;

import java.util.List;

public interface PostCommentService {

    List<PostCommentResponse> findAllByPostId(Long postId);

    PostCommentResponse findById(Long postId, Long commentId);

    PostCommentResponse save(
            Long postId,
            PostCommentRequest request
    );

    PostCommentResponse update(
            Long postId,
            Long commentId,
            PostCommentRequest request
    );

    void delete(Long postId, Long commentId);
}