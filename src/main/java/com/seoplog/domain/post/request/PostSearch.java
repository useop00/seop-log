package com.seoplog.domain.post.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSearch {

    private static final int MAX_SIZE = 2000;

    private final int page;
    private final int size;

    @Builder
    public PostSearch(Integer page, Integer size) {
        this.page = (page == null || page < 1) ? 1 : page; // 기본값 처리
        this.size = (size == null || size < 1) ? 10 : Math.min(size, MAX_SIZE); // 유효성 검사 및 최대값 제한
    }

    public long calculateOffset() {
        return (long) (page - 1) * size;
    }
}
