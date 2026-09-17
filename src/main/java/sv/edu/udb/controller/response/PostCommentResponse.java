package sv.edu.udb.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@Builder(toBuilder = true)
@FieldNameConstants
public class PostCommentResponse {

    private Long id;

    private String username;

    private String review;

    private Long postId;
}