package com.backend.searchservice.repository;

import com.backend.searchservice.entity.Novel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends ElasticsearchRepository<Novel, String> {

}
