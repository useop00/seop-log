package com.seoplog.repository;

import com.seoplog.domain.post.Post;
import com.seoplog.repository.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void setAuditFields() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .build();

        // when
        Post savedPost = postRepository.save(post);

        // then
        assertThat(savedPost.getCreatedDateTime()).isNotNull();
        assertThat(savedPost.getModifiedDateTime()).isNotNull();
        assertThat(savedPost.getCreatedDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedPost.getModifiedDateTime()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}