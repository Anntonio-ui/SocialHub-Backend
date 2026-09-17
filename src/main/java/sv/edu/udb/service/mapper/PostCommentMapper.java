package sv.edu.udb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.controller.request.PostCommentRequest;
import sv.edu.udb.controller.response.PostCommentResponse;
import sv.edu.udb.domain.PostComment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostCommentMapper {

    @Mapping(source = "post.id", target = "postId")
    PostCommentResponse toPostCommentResponse(PostComment data);

    List<PostCommentResponse> toPostCommentResponseList(
            List<PostComment> commentList
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    PostComment toPostComment(PostCommentRequest request);
}