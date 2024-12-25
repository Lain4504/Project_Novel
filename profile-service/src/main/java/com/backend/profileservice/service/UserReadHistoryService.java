package com.backend.profileservice.service;

import com.backend.profileservice.entity.UserReadHistory;
import com.backend.profileservice.repository.UserReadHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserReadHistoryService {
    UserReadHistoryRepository userReadHistoryRepository;

    public List<UserReadHistory> getReadHistoryByUserId(String userId, String type) {
        return userReadHistoryRepository.findByUserIdAndType(userId, type);
    }

    public UserReadHistory createBookmark(UserReadHistory userReadHistory) {
        if (!"bookmark".equals(userReadHistory.getType())) {
            return createOrUpdate(userReadHistory);
        }
        Optional<UserReadHistory> existingBookmark = userReadHistoryRepository.findByUserIdAndNovelId(userReadHistory.getUserId(), userReadHistory.getNovelId());
        if (existingBookmark.isPresent()) {
            return existingBookmark.get();
        }
        UserReadHistory bookmark = new UserReadHistory();
        bookmark.setUserId(userReadHistory.getUserId());
        bookmark.setNovelId(userReadHistory.getNovelId());
        bookmark.setNovelTitle(userReadHistory.getNovelTitle());
        bookmark.setNovelChapterId(userReadHistory.getNovelChapterId());
        bookmark.setNovelChapterTitle(userReadHistory.getNovelChapterTitle());
        bookmark.setType("bookmark");
        return userReadHistoryRepository.save(userReadHistory);
    }

    public UserReadHistory createOrUpdate(UserReadHistory userReadHistory) {
        if (!"history".equals(userReadHistory.getType())) {
            return createBookmark(userReadHistory);
        }
        Optional<UserReadHistory> existingHistory = userReadHistoryRepository.findByUserIdAndNovelId(userReadHistory.getUserId(), userReadHistory.getNovelId());
        if (existingHistory.isPresent()) {
            UserReadHistory historyToUpdate = existingHistory.get();
            historyToUpdate.setNovelId(userReadHistory.getNovelId());
            historyToUpdate.setNovelTitle(userReadHistory.getNovelTitle());
            historyToUpdate.setNovelChapterId(userReadHistory.getNovelChapterId());
            historyToUpdate.setNovelChapterTitle(userReadHistory.getNovelChapterTitle());
            historyToUpdate.setModifiedDate(userReadHistory.getModifiedDate());
            historyToUpdate.setType("history");
            return userReadHistoryRepository.save(historyToUpdate);
        } else {
            return userReadHistoryRepository.save(userReadHistory);
        }
    }

    // Delete
    public void delete(String id) {
        userReadHistoryRepository.deleteById(id);
    }

    // Get by id
    public Optional<UserReadHistory> getById(String id) {
        return userReadHistoryRepository.findById(id);
    }

}
