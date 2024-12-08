package com.backend.novelservice.service;

import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.entity.Novel;
import com.backend.novelservice.entity.NovelVolume;
import com.backend.novelservice.mapper.NovelVolumeMapper;
import com.backend.novelservice.repository.NovelRepository;
import com.backend.novelservice.repository.NovelVolumeRepository;
import com.backend.utils.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelVolumeService {
    NovelVolumeRepository novelVolumeRepository;
    NovelVolumeMapper novelVolumeMapper;
    DateTimeFormatter dateTimeFormatter;
    NovelRepository novelRepository;

    public NovelVolumeResponse createNovelVolume(String novelId, NovelVolumeRequest request) {
        // Retrieve the Novel by ID
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new RuntimeException("Novel not found"));

        // Map the request to a NovelVolume entity
        NovelVolume novelVolume = novelVolumeMapper.toNovelVolume(request);
        novelVolume.setNovel(novel);

        // Save the new volume
        NovelVolume savedVolume = novelVolumeRepository.save(novelVolume);

        // Ensure the volumes list is initialized
        List<NovelVolume> volumes = novel.getVolumes();
        if (volumes == null) {
            volumes = new ArrayList<>(); // Initialize if null
        }

        // Add the new volume to the list
        volumes.add(savedVolume);
        novel.setVolumes(volumes);
        // Save the updated novel with the added volume
        novelRepository.save(novel);
        // Map and return the response
        return novelVolumeMapper.toNovelVolumeResponse(novelVolume);
    }


    public List<NovelVolumeResponse> getVolumesByNovelId(String novelId) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new RuntimeException("Novel not found"));

        List<NovelVolume> volumes = novel.getVolumes();
        if (volumes == null) {
            volumes = new ArrayList<>();
        }

        return volumes.stream()
                .map(novelVolumeMapper::toNovelVolumeResponse)
                .collect(Collectors.toList());
    }

    public NovelVolumeResponse updateNovelVolume(String novelVolumeId, NovelVolumeRequest request){
        NovelVolume novelVolume = novelVolumeRepository.findById(novelVolumeId).orElseThrow(() -> new RuntimeException("Novel Volume not found"));
        novelVolumeMapper.updateNovelVolume(novelVolume, request);
        novelVolumeRepository.save(novelVolume);
        return novelVolumeMapper.toNovelVolumeResponse(novelVolume);
    }

    public void deleteNovelVolume(String novelVolumeId) {
        NovelVolume novelVolume = novelVolumeRepository.findById(novelVolumeId).orElseThrow(() -> new RuntimeException("Novel Volume not found"));
        novelVolumeRepository.delete(novelVolume);
    }

    public NovelVolumeResponse getNovelVolume(String novelVolumeId) {
        NovelVolume novelVolume = novelVolumeRepository.findById(novelVolumeId).orElseThrow(() -> new RuntimeException("Novel Volume not found"));
        return novelVolumeMapper.toNovelVolumeResponse(novelVolume);
    }

    public List<NovelVolumeResponse> getNovelVolumes() {
        return novelVolumeRepository.findAll().stream().map(novelVolumeMapper::toNovelVolumeResponse).collect(toList());
    }
}