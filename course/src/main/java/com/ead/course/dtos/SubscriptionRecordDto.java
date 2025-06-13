package com.ead.course.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SubscriptionRecordDto(@NotNull(message = "User is Mandatory")UUID userId) {}
