package com.backend.searchservice.event;

import com.backend.event.NovelDataSenderEvent;
import com.backend.searchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NovelInfoKafkaConsumer {
    SearchService searchService;

    @KafkaListener(topics = "novel-create")
    public void consumeCreate(NovelDataSenderEvent data) {
        searchService.handleCreate(data);
    }
    @KafkaListener(topics = "novel-update")
    public void consumeUpdate(NovelDataSenderEvent data) {
        searchService.handleUpdate(data);
    }

    @KafkaListener(topics = "novel-delete")
    public void consumeDelete(NovelDataSenderEvent data) {
        searchService.handleDelete(data);
    }

}