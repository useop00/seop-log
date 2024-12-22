package com.seoplog.repository.post;

import com.seoplog.domain.post.Post;
import com.seoplog.domain.post.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}