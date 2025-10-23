package com.admin.tool.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LottoGenerationRequest {

    @Min(value = 1, message = "생성 개수는 최소 1개입니다")
    @Max(value = 10, message = "생성 개수는 최대 10개입니다")
    private Integer count = 1;  // 한 번에 생성할 로또 번호 세트 개수
}
