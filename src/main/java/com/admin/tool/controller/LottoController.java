package com.admin.tool.controller;

import com.admin.tool.dto.ApiResponse;
import com.admin.tool.dto.LottoGenerationRequest;
import com.admin.tool.dto.LottoNumberResponse;
import com.admin.tool.dto.PageResponse;
import com.admin.tool.entity.LottoHistory;
import com.admin.tool.service.LottoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Lotto Number Generator", description = "로또 번호 생성 API")
@RestController
@RequestMapping("/lotto")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-auth")
public class LottoController {

    private final LottoService lottoService;

    @Operation(summary = "로또 번호 1세트 생성",
               description = "1-45 사이의 숫자 중 6개를 랜덤하게 선택하여 반환합니다 (오름차순 정렬)")
    @GetMapping("/generate")
    public ResponseEntity<ApiResponse<LottoNumberResponse>> generateLottoNumbers() {
        LottoNumberResponse response = lottoService.generateSingleLottoNumbers();
        return ResponseEntity.ok(
                ApiResponse.success("로또 번호가 생성되었습니다", response)
        );
    }

    @Operation(summary = "로또 번호 여러 세트 생성",
               description = "로또 번호를 여러 세트 생성합니다 (최대 10세트)")
    @PostMapping("/generate/multiple")
    public ResponseEntity<ApiResponse<List<LottoNumberResponse>>> generateMultipleLottoNumbers(
            @Valid @RequestBody LottoGenerationRequest request) {
        List<LottoNumberResponse> responses = lottoService.generateMultipleLottoNumbers(request.getCount());
        return ResponseEntity.ok(
                ApiResponse.success(
                        String.format("로또 번호 %d세트가 생성되었습니다", request.getCount()),
                        responses
                )
        );
    }

    @Operation(summary = "로또 번호 여러 세트 생성 (쿼리 파라미터)",
               description = "로또 번호를 여러 세트 생성합니다 (쿼리 파라미터 방식)")
    @GetMapping("/generate/multiple")
    public ResponseEntity<ApiResponse<List<LottoNumberResponse>>> generateMultipleLottoNumbersByQuery(
            @RequestParam(defaultValue = "1") int count) {

        // 범위 검증
        if (count < 1) count = 1;
        if (count > 10) count = 10;

        List<LottoNumberResponse> responses = lottoService.generateMultipleLottoNumbers(count);
        return ResponseEntity.ok(
                ApiResponse.success(
                        String.format("로또 번호 %d세트가 생성되었습니다", count),
                        responses
                )
        );
    }

    @Operation(summary = "로또 번호 생성 (보너스 포함)",
               description = "메인 번호 6개와 보너스 번호 1개를 생성합니다")
    @GetMapping("/generate/bonus")
    public ResponseEntity<ApiResponse<Map<String, Object>>> generateLottoNumbersWithBonus() {
        Map<String, Object> response = lottoService.generateLottoNumbersWithBonus();
        return ResponseEntity.ok(
                ApiResponse.success("로또 번호(보너스 포함)가 생성되었습니다", response)
        );
    }

    @Operation(summary = "내 로또 번호 생성 히스토리 조회",
               description = "현재 로그인한 사용자의 로또 번호 생성 히스토리를 페이징하여 조회합니다")
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<PageResponse<LottoHistory>>> getMyHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "generatedAt"));
        Page<LottoHistory> historyPage = lottoService.getUserHistory(pageable);

        return ResponseEntity.ok(
                ApiResponse.success("히스토리 조회 성공", PageResponse.of(historyPage))
        );
    }

    @Operation(summary = "내 최근 로또 번호 생성 히스토리 10개 조회",
               description = "현재 로그인한 사용자의 최근 로또 번호 생성 히스토리 10개를 조회합니다")
    @GetMapping("/history/recent")
    public ResponseEntity<ApiResponse<List<LottoHistory>>> getRecentHistory() {
        List<LottoHistory> history = lottoService.getRecentHistory();
        return ResponseEntity.ok(
                ApiResponse.success("최근 히스토리 조회 성공", history)
        );
    }
}
