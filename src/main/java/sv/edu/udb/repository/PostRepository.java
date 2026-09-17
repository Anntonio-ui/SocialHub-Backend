package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.domain.Post;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Buscar por título o contenido
    List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title,
            String content
    );

    // Filtrar publicaciones por fecha
    List<Post> findByPostDate(LocalDate postDate);
}