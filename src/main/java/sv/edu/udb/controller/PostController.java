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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import sv.edu.udb.controller.request.PostRequest;
import sv.edu.udb.controller.response.ErrorResponse;
import sv.edu.udb.controller.response.PostResponse;
import sv.edu.udb.service.PostService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "posts")
@Tag(
        name = "Posts",
        description = "Operaciones para administrar las publicaciones de SocialHub"
)
public class PostController {

    private final PostService postService;

    // =========================================================
    // LISTAR TODAS LAS PUBLICACIONES
    // =========================================================

    @Operation(
            summary = "Listar publicaciones",
            description = "Obtiene todas las publicaciones registradas en SocialHub."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Publicaciones obtenidas correctamente"
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
    public List<PostResponse> findAllPost() {
        return postService.findAll();
    }

    // =========================================================
    // BUSCAR PUBLICACIONES
    // =========================================================

    @Operation(
            summary = "Buscar publicaciones",
            description = "Busca publicaciones cuyo título o contenido contenga el texto indicado."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Búsqueda realizada correctamente"
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
    @GetMapping(path = "search")
    public List<PostResponse> searchPosts(
            @Parameter(
                    description = "Texto a buscar en el título o contenido de las publicaciones",
                    example = "spring"
            )
            @RequestParam(
                    name = "query",
                    required = false
            ) final String query
    ) {
        return postService.search(query);
    }

    // =========================================================
    // FILTRAR PUBLICACIONES POR FECHA
    // =========================================================

    @Operation(
            summary = "Filtrar publicaciones por fecha",
            description = "Obtiene las publicaciones registradas en una fecha específica."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filtro realizado correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Formato de fecha inválido",
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
    @GetMapping(path = "filter")
    public List<PostResponse> filterPostsByDate(
            @Parameter(
                    description = "Fecha de publicación en formato yyyy-MM-dd",
                    example = "2026-09-17"
            )
            @RequestParam(
                    name = "date",
                    required = false
            )
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            final LocalDate date
    ) {
        return postService.filterByDate(date);
    }

    // =========================================================
    // OBTENER PUBLICACIÓN POR ID
    // =========================================================

    @Operation(
            summary = "Obtener publicación por ID",
            description = "Busca una publicación específica mediante su identificador."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Publicación encontrada"
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
    @GetMapping(path = "{id}")
    public PostResponse findPostById(
            @Parameter(
                    description = "Identificador de la publicación",
                    example = "1"
            )
            @PathVariable(name = "id") final Long id
    ) {
        return postService.findById(id);
    }

    // =========================================================
    // CREAR PUBLICACIÓN
    // =========================================================

    @Operation(
            summary = "Crear publicación",
            description = "Registra una nueva publicación en SocialHub."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Publicación creada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de la publicación inválidos",
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
    public PostResponse savePost(
            @Valid @RequestBody final PostRequest request
    ) {
        return postService.save(request);
    }

    // =========================================================
    // ACTUALIZAR PUBLICACIÓN
    // =========================================================

    @Operation(
            summary = "Actualizar publicación",
            description = "Modifica los datos de una publicación existente."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Publicación actualizada correctamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de la publicación inválidos",
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
    @PutMapping(path = "{id}")
    public PostResponse updatePost(
            @Parameter(
                    description = "Identificador de la publicación a actualizar",
                    example = "1"
            )
            @PathVariable(name = "id") final Long id,

            @Valid @RequestBody final PostRequest request
    ) {
        return postService.update(id, request);
    }

    // =========================================================
    // ELIMINAR PUBLICACIÓN
    // =========================================================

    @Operation(
            summary = "Eliminar publicación",
            description = "Elimina una publicación y sus comentarios asociados."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Publicación eliminada correctamente"
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
    @DeleteMapping(path = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletePost(
            @Parameter(
                    description = "Identificador de la publicación a eliminar",
                    example = "1"
            )
            @PathVariable(name = "id") final Long id
    ) {
        postService.delete(id);
    }
}