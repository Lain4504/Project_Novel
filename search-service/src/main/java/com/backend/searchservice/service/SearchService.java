package com.backend.searchservice.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.backend.event.NovelDataSenderEvent;
import com.backend.searchservice.dto.request.NovelSearchRequest;
import com.backend.searchservice.dto.response.NovelSearchResponse;
import com.backend.searchservice.entity.Novel;
import com.backend.searchservice.repository.SearchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SearchService {
    SearchRepository searchRepository;
    ElasticsearchClient elasticsearchClient;
    ObjectMapper objectMapper;

    public void handleCreate(NovelDataSenderEvent data) {
        Map<String, Object> novelData = (Map<String, Object>) data.getParam().get("data");
        log.info("Novel data: " + novelData);
        Novel novel = mapToNovel(novelData);
        searchRepository.save(novel);
        log.info("Handled novel creation: " + novel);
    }

    public void handleUpdate(NovelDataSenderEvent data) {
        Map<String, Object> novelData = (Map<String, Object>) data.getParam().get("data");
        Novel novel = mapToNovel(novelData);
        searchRepository.save(novel);
        System.out.println("Handled novel update: " + novel);
    }

    public void handleDelete(NovelDataSenderEvent data) {
        Map<String, Object> novelData = (Map<String, Object>) data.getParam().get("data");
        Novel novel = mapToNovel(novelData);
        searchRepository.deleteById(novel.getId());
        System.out.println("Handled novel deletion: " + novel.getId());
    }

    private Novel mapToNovel(Map<String, Object> novelData) {
        return Novel.builder()
                .id((String) novelData.get("id"))
                .title((String) novelData.get("title"))
                .categories(novelData.get("categories") != null ? new HashSet<>((List<String>) novelData.get("categories")) : new HashSet<>())
                .authorId((String) novelData.get("authorId"))
                .authorName((String) novelData.get("authorName"))
                .description((String) novelData.get("description"))
                .score((Integer) novelData.get("score"))
                .bookStatus((Integer) novelData.get("bookStatus"))
                .visitCount(novelData.get("visitCount") != null ? ((Number) novelData.get("visitCount")).longValue() : null)
                .wordCount(novelData.get("wordCount") != null ? ((Number) novelData.get("wordCount")).longValue() : null)
                .commentCount(novelData.get("commentCount") != null ? ((Number) novelData.get("commentCount")).longValue() : null)
                .isVip((Integer) novelData.get("isVip"))
                .imageUrl((String) novelData.get("imageUrl"))
                .chapterCount((Integer) novelData.get("chapterCount"))
                .latestChapterTitle((String) novelData.get("latestChapterTitle"))
                .latestChapterId((String) novelData.get("latestChapterId"))
                .status((String) novelData.get("status"))
                .build();
    }

    public List<NovelSearchResponse> searchNovels(NovelSearchRequest request) throws IOException {
        BoolQuery.Builder boolQuery = QueryBuilders.bool();

        if (request.getKeyword() != null) {
            boolQuery.should(QueryBuilders.match(m -> m.field("title").query(request.getKeyword()).boost(2.0f)));
            boolQuery.should(QueryBuilders.match(m -> m.field("authorName").query(request.getKeyword())));
        }
        if (request.getCategoryId() != null) {
            boolQuery.filter(QueryBuilders.term(t -> t.field("categories").value(request.getCategoryId())));
        }
        if (request.getBookStatus() != null) {
            boolQuery.filter(QueryBuilders.term(t -> t.field("bookStatus").value(request.getBookStatus())));
        }
        if (request.getWordCountMin() != null) {
            boolQuery.filter(QueryBuilders.range(r -> r.field("wordCount").gte(JsonData.of(request.getWordCountMin()))));
        }
        if (request.getWordCountMax() != null) {
            boolQuery.filter(QueryBuilders.range(r -> r.field("wordCount").lte(JsonData.of(request.getWordCountMax()))));
        }
        if (request.getSort() != null) {
            boolQuery.must(QueryBuilders.match(m -> m.field("sort").query(request.getSort())));
        }

        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("novel")
                .query(boolQuery.build()._toQuery())
        );
        log.info("Search request: " + searchRequest);
        SearchResponse<Novel> searchResponse = elasticsearchClient.search(searchRequest, Novel.class);
        log.info("Search response: " + searchResponse);
        return searchResponse.hits().hits().stream()
                .map(Hit::source)
                .map(novel -> NovelSearchResponse.builder()
                        .id(novel.getId())
                        .title(novel.getTitle())
                        .categories(novel.getCategories())
                        .authorId(novel.getAuthorId())
                        .authorName(novel.getAuthorName())
                        .description(novel.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}