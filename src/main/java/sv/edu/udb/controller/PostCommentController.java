package sv.edu.udb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.controller.request.PostCommentRequest;
import sv.edu.udb.controller.response.ErrorResponse;
import sv.edu.udb.controller.response.PostCommentResponse;
import sv.edu.udb.service.PostCommentService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "posts/{postId}/comments")
@Tag(
        name = "Comentarios",
        description = "Operaciones para administrar los comentarios asociados a las publicaciones de SocialHub"
)
public class PostCommentController {

    private final PostCommentService postCommentService;

    @Operation(
            summary = "Listar comentarios de una publicación",
            description = "Obtiene todos los comentarios asociados a una publicación específica."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentarios obtenidos correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Publicación no encontrada",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @GetMapping
    public List<PostCommentResponse> findAllCommentsByPost(
            @Parameter(
                    description = "Identificador de la publicación",
                    example = "1"
            )
            @PathVariable Long postId
    ) {
        return postCommentService.findAllByPostId(postId);
    }

    @Operation(
            summary = "Obtener comentario por ID",
            description = "Obtiene un comentario específico perteneciente a una publicación."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentario encontrado"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Publicación o comentario no encontrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @GetMapping(path = "{commentId}")
    public PostCommentResponse findCommentById(
            @Parameter(
                    description = "Identificador de la publicación",
                    example = "1"
            )
            @PathVariable Long postId,

            @Parameter(
                    description = "Identificador del comentario",
                    example = "1"
            )
            @PathVariable Long commentId
    ) {
        return postCommentService.findById(postId, commentId);
    }

    @Operation(
            summary = "Crear comentario",
            description = "Registra un nuevo comentario asociado a una publicación existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Comentario creado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos del comentario inválidos",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Publicación no encontrada",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public PostCommentResponse saveComment(
            @Parameter(
                    description = "Identificador de la publicación",
                    example = "1"
            )
            @PathVariable Long postId,

            @Valid @RequestBody PostCommentRequest request
    ) {
        return postCommentService.save(postId, request);
    }

    @Operation(
            summary = "Actualizar comentario",
            description = "Modifica el usuario y el contenido de un comentario existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comentario actualizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos del comentario inválidos",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Publicación o comentario no encontrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PutMapping(path = "{commentId}")
    public PostCommentResponse updateComment(
            @Parameter(
                    description = "Identificador de la publicación",
                    example = "1"
            )
            @PathVariable Long postId,

            @Parameter(
                    description = "Identificador del comentario",
                    example = "1"
            )
            @PathVariable Long commentId,

            @Valid @RequestBody PostCommentRequest request
    ) {
        return postCommentService.update(
                postId,
                commentId,
                request
        );
    }

    @Operation(
            summary = "Eliminar comentario",
            description = "Elimina un comentario perteneciente a una publicación."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Comentario eliminado correctamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Publicación o comentario no encontrado",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @DeleteMapping(path = "{commentId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(
            @Parameter(
                    description = "Identificador de la publicación",
                    example = "1"
            )
            @PathVariable Long postId,

            @Parameter(
                    description = "Identificador del comentario",
                    example = "1"
            )
            @PathVariable Long commentId
    ) {
        postCommentService.delete(postId, commentId);
    }
}