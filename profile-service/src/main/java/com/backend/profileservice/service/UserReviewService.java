package com.backend.profileservice.service;

import com.backend.dto.response.PageResponse;
import com.backend.profileservice.dto.request.UserReviewRequest;
import com.backend.profileservice.dto.response.UserReviewResponse;
import com.backend.profileservice.mapper.UserReviewMapper;
import com.backend.profileservice.repository.UserReviewRepository;
import com.backend.utils.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.sql.Time;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserReviewService {
    UserReviewRepository userReviewRepository;
    UserReviewMapper userReviewMapper;
    DateTimeFormatter dateTimeFormatter;
    private final RestClient.Builder builder;

    public UserReviewResponse createReview(UserReviewRequest request) {
        return userReviewMapper.toUserReviewResponse(userReviewRepository.save(userReviewMapper.toUserReview(request)));
    }
    public UserReviewResponse updateReview(UserReviewRequest request) {
        userReviewMapper.updateUserReview(userReviewRepository.findByUserIdAndNovelId(request.getUserId(), request.getNovelId()), request);
        return userReviewMapper.toUserReviewResponse(userReviewRepository.save(userReviewMapper.toUserReview(request)));
    }
    public void deleteReview(UserReviewRequest request) {
        userReviewRepository.deleteByUserIdAndNovelId(request.getUserId(), request.getNovelId());
    }
    public PageResponse<UserReviewResponse> getReviewsByNovelId(Long novelId, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = userReviewRepository.findAllByNovelId(novelId, pageable);
        var novelReviews = pageData.getContent().stream().map(novelReview ->{
            var userReviewResponse = userReviewMapper.toUserReviewResponse(novelReview);
            userReviewResponse.setCreated(dateTimeFormatter.format(novelReview.getCreatedAt()));
            return userReviewResponse;
        }).toList();
        return PageResponse.<UserReviewResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(novelReviews)
                .build();
    }
}
