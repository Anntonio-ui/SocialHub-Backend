package sv.edu.udb.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sv.edu.udb.controller.request.PostRequest;
import sv.edu.udb.controller.response.PostResponse;
import sv.edu.udb.domain.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(
            target = "commentCount",
            expression = "java(data.getComments() != null ? (long) data.getComments().size() : 0L)"
    )
    PostResponse toPostResponse(final Post data);

    List<PostResponse> toPostResponseList(final List<Post> postList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Post toPost(final PostRequest postRequest);
}