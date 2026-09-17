package sv.edu.udb.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sv.edu.udb.controller.request.PostRequest;
import sv.edu.udb.controller.response.PostResponse;
import sv.edu.udb.domain.Post;
import sv.edu.udb.repository.PostRepository;
import sv.edu.udb.service.implementation.PostServiceImpl;
import sv.edu.udb.service.mapper.PostMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    private Post post;

    @BeforeEach
    void init() {
        this.post = new Post();

        this.post.setId(1L);
        this.post.setTitle("testing post");
        this.post.setPostDate(LocalDate.of(2024, 9, 28));
    }

    @Test
    @DisplayName("Find all the post when we have data")
    void shouldGetAllPostResponse_When_FindAllThePost() {

        when(postRepository.findAll()).thenReturn(List.of(post));

        PostResponse response = PostResponse.builder()
                .title(post.getTitle())
                .postDate(post.getPostDate())
                .build();

        when(postMapper.toPostResponseList(anyList()))
                .thenReturn(List.of(response));

        final List<PostResponse> postResponseList = postService.findAll();

        assertNotNull(postResponseList);
        assertEquals(1, postResponseList.size());

        verify(postRepository, times(1)).findAll();
        verifyNoMoreInteractions(postRepository);

        verify(postMapper, times(1))
                .toPostResponseList(anyList());
        verifyNoMoreInteractions(postMapper);
    }

    @Test
    @DisplayName("Find Post by id")
    void shouldGetPost_When_ExistPostWithId() {

        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(this.post));

        PostResponse response = PostResponse.builder()
                .title(this.post.getTitle())
                .postDate(this.post.getPostDate())
                .build();

        when(postMapper.toPostResponse(any(Post.class)))
                .thenReturn(response);

        final PostResponse postResponse = postService.findById(1L);

        assertNotNull(postResponse);
        assertEquals("testing post", postResponse.getTitle());

        verify(postRepository, times(1))
                .findById(anyLong());

        verifyNoMoreInteractions(postRepository);

        verify(postMapper, times(1))
                .toPostResponse(any(Post.class));

        verifyNoMoreInteractions(postMapper);
    }

    @Test
    @DisplayName("Find a no existed id then throws an exception")
    void shouldGetEntityNotFoundException_When_NoExistPostWithId() {

        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            postService.findById(2L);
        });

        verify(postRepository, times(1))
                .findById(anyLong());

        verify(postMapper, never())
                .toPostResponse(any(Post.class));

        verifyNoMoreInteractions(postMapper);
    }

    @Test
    @DisplayName("Save Post when the Post request is valid")
    void shouldSavePostEntity_When_PostRequestIsValid() {

        when(postMapper.toPost(any(PostRequest.class)))
                .thenReturn(this.post);

        when(postRepository.save(any(Post.class)))
                .thenReturn(this.post);

        PostResponse response = PostResponse.builder()
                .title(this.post.getTitle())
                .postDate(this.post.getPostDate())
                .build();

        when(postMapper.toPostResponse(any(Post.class)))
                .thenReturn(response);

        PostRequest postRequest = PostRequest.builder()
                .title(this.post.getTitle())
                .postDate(this.post.getPostDate())
                .build();

        final PostResponse postResponse =
                postService.save(postRequest);

        assertNotNull(postResponse);
        assertEquals("testing post", postResponse.getTitle());

        verify(postMapper, times(1))
                .toPost(any(PostRequest.class));

        verify(postRepository, times(1))
                .save(any(Post.class));

        verify(postMapper, times(1))
                .toPostResponse(any(Post.class));

        verifyNoMoreInteractions(postMapper);
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    @DisplayName("Should update Post When Post Request and Id are valid")
    void shouldUpdatePost_When_PostRequestAndIdAreValid() {

        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(this.post));

        String newTitle = "updated post";
        LocalDate newDate = LocalDate.of(2024, 10, 27);

        PostRequest postRequest = PostRequest.builder()
                .title(newTitle)
                .postDate(newDate)
                .build();

        when(postRepository.save(any(Post.class)))
                .thenReturn(this.post);

        PostResponse response = PostResponse.builder()
                .title(newTitle)
                .postDate(newDate)
                .build();

        when(postMapper.toPostResponse(any(Post.class)))
                .thenReturn(response);

        final PostResponse postResponse =
                postService.update(1L, postRequest);

        assertNotNull(postResponse);
        assertEquals(newTitle, postResponse.getTitle());
        assertEquals(newDate, postResponse.getPostDate());

        verify(postRepository, times(1))
                .findById(anyLong());

        verify(postRepository, times(1))
                .save(any(Post.class));

        verifyNoMoreInteractions(postRepository);

        verify(postMapper, times(1))
                .toPostResponse(any(Post.class));

        verifyNoMoreInteractions(postMapper);
    }

    @Test
    @DisplayName("Delete Post when post id exist")
    void shouldDeletePost_When_PostIdExist() {

        doNothing()
                .when(postRepository)
                .deleteById(anyLong());

        postService.delete(1L);

        verify(postRepository, times(1))
                .deleteById(anyLong());

        verifyNoMoreInteractions(postRepository);
    }
}
