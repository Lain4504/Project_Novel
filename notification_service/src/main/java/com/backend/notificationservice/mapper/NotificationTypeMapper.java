package com.backend.notificationservice.mapper;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Mapper(componentModel = "spring")
public interface NotificationTypeMapper {
}
