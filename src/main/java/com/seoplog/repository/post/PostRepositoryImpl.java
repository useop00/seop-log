package com.seoplog.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seoplog.domain.post.Post;
import com.seoplog.domain.post.request.PostSearch;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.seoplog.domain.post.QPost.post;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return queryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.calculateOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
    
}
