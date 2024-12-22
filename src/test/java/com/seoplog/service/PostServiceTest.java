package com.seoplog.service;

import com.seoplog.domain.post.Post;
import com.seoplog.domain.post.request.PostCreate;
import com.seoplog.domain.post.request.PostSearch;
import com.seoplog.domain.post.request.PostUpdate;
import com.seoplog.domain.post.response.PostResponse;
import com.seoplog.exception.PostNotFound;
import com.seoplog.repository.post.PostRepository;
import com.seoplog.service.post.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("글 작성")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        //when
        postService.write(request);

        //then
        assertThat(postRepository.count()).isEqualTo(1);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
    }

    @DisplayName("게시글 생성 시 Auditing 테스트")
    @Test
    void auditingTest() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        Post savedPost = postRepository.save(post);

        //when & //then
        assertThat(savedPost.getCreatedDateTime()).isNotNull();
    }

    @Test
    @DisplayName("글 1개 조회")
    void find() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        Post savedPost = postRepository.save(post);

        //when
        PostResponse response = postService.findPost(savedPost.getId());

        //then
        assertThat(post).isNotNull();
        assertThat(response.getTitle()).isEqualTo("제목");
        assertThat(response.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("존재하지 않는 글을 조회하면 예외가 발생한다.")
    void findPost_Exception() throws Exception {
        //given
        Long postId = 1L;

        //when & then
        assertThatThrownBy(() -> postService.findPost(postId))
                .isInstanceOf(PostNotFound.class);
    }

    @Test
    @DisplayName("글을 id값으로 내림차순해서 페이지를 조회한다.")
    void postPageableDesc() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .build())
                .toList();

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        //when
        List<PostResponse> posts = postService.findAll(postSearch);

        //then
        assertThat(posts).hasSize(10);
        assertThat(posts.get(0).getTitle()).isEqualTo("제목 19");
    }

    @Test
    @DisplayName("글 수정")
    void updatePost() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        PostUpdate update = PostUpdate.builder()
                .title(null)
                .content("내용2")
                .build();
        //when
        postService.update(post.getId(), update);

        //then
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용2");
    }

    @Test
    @DisplayName("글 삭제")
    void deletePost() throws Exception {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();
        postRepository.save(post);

        //when
        postService.deletePost(post.getId());

        //then
        assertThat(postRepository.count()).isZero();
    }
}