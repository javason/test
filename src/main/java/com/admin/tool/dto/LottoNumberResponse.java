package com.admin.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LottoNumberResponse {

    private List<Integer> numbers;
    private LocalDateTime generatedAt;
    private String message;

    public static LottoNumberResponse of(List<Integer> numbers) {
        return LottoNumberResponse.builder()
                .numbers(numbers)
                .generatedAt(LocalDateTime.now())
                .message("행운의 번호입니다!")
                .build();
    }
}
