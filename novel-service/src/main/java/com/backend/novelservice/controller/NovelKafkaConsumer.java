package com.backend.novelservice.controller;

import com.backend.event.NovelDataSenderEvent;
import com.backend.novelservice.service.NovelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelKafkaConsumer {
    NovelService novelService;

    @KafkaListener(topics = "novel-rating", groupId = "group_id")
    public void consume(NovelDataSenderEvent event) {
        novelService.updateNovelScore(event);
    }
}
