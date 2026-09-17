package sv.edu.udb.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCommentRequest {

    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 2, max = 80,
            message = "El usuario debe tener entre 2 y 80 caracteres")
    private String username;

    @NotBlank(message = "El comentario es obligatorio")
    @Size(min = 2, max = 500,
            message = "El comentario debe tener entre 2 y 500 caracteres")
    private String review;
}