package sv.edu.udb.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@FieldNameConstants
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDate postDate;

    private Long commentCount;
}