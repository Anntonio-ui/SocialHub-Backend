package sv.edu.udb.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sv.edu.udb.domain.Post;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    private Long postId;

    @BeforeEach
    void init() {
        Post newPost = new Post();

        newPost.setTitle("Anything you want to write");
        newPost.setPostDate(LocalDate.of(2024, 8, 24));

        Post savedPost = postRepository.save(newPost);

        postId = savedPost.getId();
    }

    @AfterEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    void shouldHasOnePost_When_FindAll() {

        int expectedPostNumber = 1;

        List<Post> actualPostList = postRepository.findAll();

        assertNotNull(actualPostList);
        assertEquals(expectedPostNumber, actualPostList.size());
    }

    @Test
    void shouldGetPost_When_IdExist() {

        String expectedTitle = "Anything you want to write";
        LocalDate expectedDate = LocalDate.of(2024, 8, 24);

        Post actualPost = postRepository
                .findById(postId)
                .orElse(null);

        assertNotNull(actualPost);
        assertEquals(postId, actualPost.getId());
        assertEquals(expectedTitle, actualPost.getTitle());
        assertEquals(expectedDate, actualPost.getPostDate());
    }

    @Test
    void shouldSavePost_When_PostIsNew() {

        String expectedTitle = "Second post";
        LocalDate expectedDate = LocalDate.of(2024, 8, 24);

        Post newPost = new Post();

        newPost.setTitle(expectedTitle);
        newPost.setPostDate(expectedDate);

        Post savedPost = postRepository.save(newPost);

        assertNotNull(savedPost);
        assertNotNull(savedPost.getId());
        assertEquals(expectedTitle, savedPost.getTitle());
        assertEquals(expectedDate, savedPost.getPostDate());
    }

    @Test
    void shouldDeletePost_When_PostExist() {

        Post actualPost = postRepository
                .findById(postId)
                .orElse(null);

        assertNotNull(actualPost);

        postRepository.deleteById(postId);

        Post deletedPost = postRepository
                .findById(postId)
                .orElse(null);

        assertNull(deletedPost);
    }
}
