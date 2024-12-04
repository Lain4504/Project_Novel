package com.backend.novelservice.service;

import com.backend.dto.response.PageResponse;
import com.backend.novelservice.dto.request.NovelVolumeRequest;
import com.backend.novelservice.dto.response.NovelVolumeResponse;
import com.backend.novelservice.entity.NovelVolume;
import com.backend.novelservice.mapper.NovelVolumeMapper;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NovelVolumeService {
    NovelVolumeRepository novelVolumeRepository;
    NovelVolumeMapper novelVolumeMapper;
    DateTimeFormatter dateTimeFormatter;
    public NovelVolumeResponse createNovelVolume(NovelVolumeRequest request) {
        NovelVolume novelVolume = novelVolumeMapper.toNovelVolume(request);
        novelVolumeRepository.save(novelVolume);
        return novelVolumeMapper.toNovelVolumeResponse(novelVolume);
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
    public PageResponse<NovelVolumeResponse> getNovelVolumes(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = novelVolumeRepository.findAll(pageable);
        var volumeList = pageData.getContent().stream().map(novelVolume -> {
            var volumeResponse = novelVolumeMapper.toNovelVolumeResponse(novelVolume);
            volumeResponse.setCreatedDate(LocalDateTime.parse(dateTimeFormatter.format(Instant.from(novelVolume.getCreatedDate()))));
            return volumeResponse;
        }).toList();
        return PageResponse.<NovelVolumeResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(volumeList)
                .build();
    }
}
