package com.sims.service;

import com.sims.entity.*;
import com.sims.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityLogService {
    private final ActivityLogRepository repo;

    public void log(User user, String action, String details, String entityType, Long entityId) {
        repo.save(ActivityLog.builder()
                .user(user).action(action).details(details)
                .entityType(entityType).entityId(entityId)
                .build());
    }
}