package com.epam.spring.testingapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDto {
    private String message;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private LocalDateTime time;
}
