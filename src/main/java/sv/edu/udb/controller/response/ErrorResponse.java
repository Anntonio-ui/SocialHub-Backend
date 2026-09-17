package sv.edu.udb.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Respuesta estándar utilizada por la API cuando ocurre un error"
)
public class ErrorResponse {

    @Schema(
            description = "Fecha y hora en la que ocurrió el error",
            example = "2026-09-17T10:30:00"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "Código de estado HTTP asociado al error"
    )
    private Integer status;

    @Schema(
            description = "Nombre del error HTTP"
    )
    private String error;

    @Schema(
            description = "Mensaje descriptivo del error"
    )
    private String message;

    @Schema(
            description = "Errores específicos de validación por campo",
            nullable = true
    )
    private Map<String, String> details;
}