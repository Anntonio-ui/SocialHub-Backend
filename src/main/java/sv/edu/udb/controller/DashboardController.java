package sv.edu.udb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sv.edu.udb.controller.response.DashboardResponse;
import sv.edu.udb.controller.response.ErrorResponse;
import sv.edu.udb.service.DashboardService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "dashboard")
@Tag(
        name = "Dashboard",
        description = "Métricas generales e indicadores de SocialHub"
)
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(
            summary = "Obtener métricas del Dashboard",
            description = "Obtiene el total de publicaciones, total de comentarios, " +
                    "publicación con más comentarios, publicación con mayor interacción " +
                    "y las últimas publicaciones registradas."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Métricas obtenidas correctamente"
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
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }
}