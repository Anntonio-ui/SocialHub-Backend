package sv.edu.udb.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "PostRequest",
        description = "Datos necesarios para crear o actualizar una publicación"
)
public class PostRequest {

    @Schema(
            description = "Título de la publicación",
            example = "Introducción a Spring Boot",
            minLength = 3,
            maxLength = 150
    )
    @NotBlank(message = "El título es obligatorio")
    @Size(
            min = 3,
            max = 150,
            message = "El título debe tener entre 3 y 150 caracteres"
    )
    private String title;

    @Schema(
            description = "Contenido de la publicación",
            example = "Aprendiendo a desarrollar APIs REST con Spring Boot.",
            minLength = 5,
            maxLength = 2000
    )
    @NotBlank(message = "El contenido es obligatorio")
    @Size(
            min = 5,
            max = 2000,
            message = "El contenido debe tener entre 5 y 2000 caracteres"
    )
    private String content;

    @Schema(
            description = "Fecha de publicación en formato dd/MM/yyyy",
            example = "17/09/2026",
            type = "string",
            pattern = "dd/MM/yyyy"
    )
    @NotNull(message = "La fecha de publicación es obligatoria")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy"
    )
    @FutureOrPresent(
            message = "La fecha no puede estar en el pasado"
    )
    private LocalDate postDate;
}