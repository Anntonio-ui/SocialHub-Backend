package sv.edu.udb.service.implementation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sv.edu.udb.controller.response.DashboardResponse;
import sv.edu.udb.controller.response.PostResponse;
import sv.edu.udb.domain.Post;
import sv.edu.udb.repository.PostCommentRepository;
import sv.edu.udb.repository.PostRepository;
import sv.edu.udb.service.DashboardService;
import sv.edu.udb.service.mapper.PostMapper;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    @NonNull
    private final PostRepository postRepository;

    @NonNull
    private final PostCommentRepository postCommentRepository;

    @NonNull
    private final PostMapper postMapper;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard() {

        final List<Post> posts = postRepository.findAll();

        final long totalPosts = postRepository.count();
        final long totalComments = postCommentRepository.count();

        /*
         * Ordena las publicaciones de mayor a menor
         * según su cantidad de comentarios.
         */
        final List<Post> postsByComments = posts.stream()
                .sorted(
                        Comparator.comparingInt(
                                (Post post) ->
                                        post.getComments() != null
                                                ? post.getComments().size()
                                                : 0
                        ).reversed()
                )
                .toList();

        /*
         * Publicación con mayor cantidad de comentarios.
         */
        final Post mostCommentedPost = postsByComments.stream()
                .findFirst()
                .orElse(null);

        /*
         * Top 3 publicaciones con mayor cantidad
         * de comentarios.
         */
        final List<PostResponse> mostCommentedPosts =
                postsByComments.stream()
                        .limit(3)
                        .map(postMapper::toPostResponse)
                        .toList();

        /*
         * Últimas 5 publicaciones ordenadas
         * por fecha de publicación.
         */
        final List<PostResponse> latestPosts = posts.stream()
                .sorted(
                        Comparator.comparing(
                                Post::getPostDate,
                                Comparator.nullsLast(
                                        Comparator.reverseOrder()
                                )
                        )
                )
                .limit(5)
                .map(postMapper::toPostResponse)
                .toList();

        final PostResponse mostCommentedPostResponse =
                mostCommentedPost != null
                        ? postMapper.toPostResponse(mostCommentedPost)
                        : null;

        /*
         * Actualmente la interacción de SocialHub
         * está representada por los comentarios.
         * Por ello, la publicación más interactiva
         * corresponde a la más comentada.
         */
        final PostResponse mostInteractivePost =
                mostCommentedPostResponse;

        return DashboardResponse.builder()
                .totalPosts(totalPosts)
                .totalComments(totalComments)
                .mostCommentedPost(mostCommentedPostResponse)
                .mostCommentedPosts(mostCommentedPosts)
                .mostInteractivePost(mostInteractivePost)
                .latestPosts(latestPosts)
                .build();
    }
}