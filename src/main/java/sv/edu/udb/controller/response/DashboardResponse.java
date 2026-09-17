package sv.edu.udb.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Long totalPosts;

    private Long totalComments;

    // Publicación con mayor cantidad de comentarios
    private PostResponse mostCommentedPost;

    // Top de publicaciones con mayor cantidad de comentarios
    private List<PostResponse> mostCommentedPosts;

    // Publicación con mayor interacción
    private PostResponse mostInteractivePost;

    // Últimas publicaciones
    private List<PostResponse> latestPosts;
}